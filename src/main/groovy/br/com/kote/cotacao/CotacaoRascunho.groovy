package br.com.kote.cotacao

import br.com.analise.service.ICompraService
import br.com.kote.resposta.RespostaFactory
import br.com.kote.usuario.Representante

class CotacaoRascunho extends EstadoCotacao {

    def dataService
    def backgroundService
    def sessionFactory
    ICompraService compraService

    boolean envie(ICotacao cotacao, List<Representante> representantes) {
        boolean resultado = true
        try {
            log.debug("Enviando cotação - id: ${cotacao.id}}")
            dataService.refreshSession()
            cotacao.mudeEstadoPara(PROCESSANDO_ENVIO_RESPOSTAS)
            compraService.updateEstadoCompraByCotacaoId(cotacao.id, PROCESSANDO_ENVIO_RESPOSTAS)
            cotacao.save()
            def session = sessionFactory.getCurrentSession()
            session.flush()
            session.clear()
            doBackgroungWork(representantes.collect { it.id }, cotacao.id)
            log.debug("disparado background work de cotação - id: ${cotacao.id}}")
        }
        catch (Exception e) {
            e.printStackTrace()
            log.error("Erro ao enviar cotação - " + " id: {$cotacao?.id}" + e.toString())
        }
        return resultado
    }

    private void doBackgroungWork(List idRepresentantes, Long idCotacao) {
        backgroundService.execute("Criando Analise e respostas de cotação - id: ${idCotacao}", {
            //new AnaliseFactory().crie(idCotacao)
            new RespostaFactory().crie(idRepresentantes, idCotacao)
        })
    }

    boolean salve(ICotacao cotacao) {
        if (cotacao.save())
            return true
        return false
    }

    boolean cancele(Cotacao cotacao) {
        def idCotacao = cotacao.id
        if (cotacao.itens.size() <= 0) {
            cotacao.delete()
            return Cotacao.get(idCotacao) == null
        }
        cotacao.mudeEstadoPara(CANCELADA)
        compraService.updateEstadoCompraByCotacaoId(cotacao.id, CANCELADA)
        if (cotacao.save())
            return true
        false
    }
}
