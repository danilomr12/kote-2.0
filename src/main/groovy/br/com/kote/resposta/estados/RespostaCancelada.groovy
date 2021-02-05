package br.com.kote.resposta.estados

import br.com.analise.domain.Compra
import br.com.analise.service.ICompraService
import br.com.kote.cotacao.Cotacao
import br.com.kote.cotacao.EstadoCotacao
import br.com.kote.resposta.EstadoResposta
import br.com.kote.resposta.IResposta
import grails.core.GrailsApplication

class RespostaCancelada extends EstadoResposta {

    GrailsApplication application
    def ctx = application.mainContext
    def compraService = (ICompraService) ctx.getBean("compraService")

    IResposta salve(IResposta resposta) {
        return resposta.save()
    }

    IResposta ressuscite(IResposta resposta) {
        Compra compra = compraService.getCompraByCotacaoId(resposta.cotacao.id)
        if (compra.estado == EstadoCotacao.EM_ANALISE
                || compra.estado == EstadoCotacao.AGUARDANDO_RESPOSTAS
                || compra.estado == EstadoCotacao.PRONTA_PARA_ANALISE) {
            Cotacao cotacao = Cotacao.get(resposta.cotacao.id)
            cotacao.mudeEstadoPara(EstadoCotacao.AGUARDANDO_RESPOSTAS)
            compraService.updateEstadoCompraByCotacaoId(resposta.cotacao.id, EstadoCotacao.AGUARDANDO_RESPOSTAS)
            resposta.mudeEstadoPara(EstadoResposta.RESPONDENDO)
            compraService.updateRespostaCompra(resposta.id, EstadoResposta.RESPONDENDO)
            return resposta
        }
        return null
    }
}