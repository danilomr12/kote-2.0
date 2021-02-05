package br.com.kote.cotacao.estados

import br.com.analise.service.ICompraService
import br.com.kote.cotacao.EstadoCotacao
import br.com.kote.cotacao.ICotacao
import br.com.kote.counter.AbstractCounter
import br.com.kote.counter.RespostaCounter
import br.com.kote.resposta.EstadoResposta
import br.com.kote.resposta.IResposta
import br.com.kote.resposta.Resposta
import br.com.kote.resposta.RespostaFactory
import br.com.kote.resposta.estados.RespostaNovaCotacao
import br.com.kote.resposta.estados.RespostaRespondendo
import br.com.kote.usuario.Representante
import grails.core.GrailsApplication

class CotacaoAguardandoRespostas extends EstadoCotacao {

    GrailsApplication grailsApplication
    def ctx = grailsApplication.mainContext
    def dataService = ctx.dataService
    def backgroundService = ctx.backgroundService
    ICompraService compraService = (ICompraService) ctx.getBean("compraService")


    void analisar(ICotacao cotacao) {
        this.descarteRespostasNaoConcluidasEConcluaRespostasRespondendo(cotacao)
        cotacao.mudeEstadoPara(EM_ANALISE)
        cotacao.save()
        compraService.updateEstadoCompraByCotacaoId(cotacao.id, EstadoCotacao.EM_ANALISE)
        Resposta respostas = cotacao.respostas
        respostas.findAll { Resposta resposta ->
            resposta.codigoEstado != EstadoResposta.CANCELADA &&
                    resposta.codigoEstado != EstadoResposta.PERDIDA &&
                    resposta.codigoEstado != EstadoResposta.RECUSADA
        }.each { Resposta resposta ->
            resposta.mudeEstadoPara(EstadoResposta.EM_ANALISE)
            resposta.save()
            compraService.updateRespostaCompra(resposta.id, EstadoResposta.EM_ANALISE)
        }
    }

    void aguardeAnalise(ICotacao cotacao) {
        try {
            cotacao.mudeEstadoPara(EstadoCotacao.PRONTA_PARA_ANALISE)
            compraService.updateEstadoCompraByCotacaoId(cotacao.id, EstadoCotacao.PRONTA_PARA_ANALISE)
            cotacao.save()
            cotacao.respostas.each { Resposta resposta ->
                if (resposta.codigoEstado != EstadoResposta.CANCELADA &&
                        resposta.codigoEstado != EstadoResposta.RECUSADA &&
                        resposta.codigoEstado != EstadoResposta.PERDIDA) {
                    resposta.mudeEstadoPara(EstadoResposta.AGUARDANDO_ANALISE)
                    compraService.updateRespostaCompra(resposta.id, EstadoResposta.AGUARDANDO_ANALISE)
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace()
            cotacao.mudeEstadoPara(EstadoCotacao.AGUARDANDO_RESPOSTAS)
            compraService.updateEstadoCompraByCotacaoId(cotacao.id, EstadoCotacao.AGUARDANDO_RESPOSTAS)
            cotacao.respostas.each { Resposta resposta ->
                if (resposta.codigoEstado != EstadoResposta.CANCELADA &&
                        resposta.codigoEstado != EstadoResposta.RECUSADA &&
                        resposta.codigoEstado != EstadoResposta.PERDIDA &&
                        resposta.codigoEstado == EstadoResposta.AGUARDANDO_ANALISE) {

                    resposta.mudeEstadoPara(EstadoResposta.AGUARDANDO_OUTRAS_RESPOSTAS)
                    resposta.save()
                    compraService.updateRespostaCompra(resposta.id, EstadoResposta.AGUARDANDO_OUTRAS_RESPOSTAS)

                }

            }
        }
    }

    boolean cancele(ICotacao cotacao) {
        cotacao.mudeEstadoPara(EstadoCotacao.CANCELADA)
        compraService.updateEstadoCompraByCotacaoId(cotacao.id, EstadoCotacao.CANCELADA)
        cotacao.respostas.each { IResposta resposta ->
            resposta.cancele()
        }
        return cotacao.save()
    }

    boolean salve(ICotacao cotacao) {
        if (cotacao.save())
            return true
        return false
    }

    private void doBackgroungWork(List idRepresentantes, Long idCotacao) {
        backgroundService.execute("Criando respostas", {
            new RespostaFactory().crie(idRepresentantes, idCotacao)
        })
    }

    boolean envie(ICotacao cotacao, List<Representante> representantes) {
        boolean resultado = true
        try {
            dataService.refreshSession()
            def idsRepresentantes = []
            representantes.each {
                if (!it.possuiRespostaDaCotacao(cotacao)) {
                    idsRepresentantes << it.id
                }
            }
            cotacao.mudeEstadoPara(EstadoCotacao.PROCESSANDO_ENVIO_RESPOSTAS)
            compraService.updateEstadoCompraByCotacaoId(cotacao.id, EstadoCotacao.PROCESSANDO_ENVIO_RESPOSTAS)
            cotacao.save()
            doBackgroungWork(idsRepresentantes, cotacao.id)
        }
        catch (Exception e) {
            e.printStackTrace()
            resultado = false
        }
        return resultado
    }

    AbstractCounter makeRespostaCounter(ICotacao cotacao) {
        return new RespostaCounter(cotacao)
    }

    private def descarteRespostasNaoConcluidasEConcluaRespostasRespondendo(ICotacao cotacao) {
        cotacao.respostas.findAll {
            (it.estado instanceof RespostaNovaCotacao)
        }.each { Resposta resposta ->
            resposta.mudeEstadoPara(EstadoResposta.PERDIDA)
            resposta.save(flush: true)
            compraService.updateRespostaCompra(resposta.id, EstadoResposta.PERDIDA)
        }
        cotacao.respostas.findAll {
            (it.estado instanceof RespostaRespondendo)
        }.each { Resposta resposta ->
            resposta.envie()
        }
    }
}