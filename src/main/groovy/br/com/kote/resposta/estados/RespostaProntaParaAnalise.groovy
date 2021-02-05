package br.com.kote.resposta.estados

import br.com.analise.domain.Compra
import br.com.analise.service.ICompraService
import br.com.kote.cotacao.AnaliseFactory
import br.com.kote.cotacao.Cotacao
import br.com.kote.cotacao.EstadoCotacao
import br.com.kote.resposta.EstadoResposta
import br.com.kote.resposta.IResposta
import br.com.kote.resposta.Resposta
import grails.core.GrailsApplication
import org.grails.datastore.mapping.transactions.Transaction
import org.hibernate.StatelessSession

class RespostaProntaParaAnalise extends EstadoResposta {

    GrailsApplication application
    def ctx = application.mainContext
    def sessionFactory = ctx.sessionFactory
    def compraService = (ICompraService) ctx.getBean("compraService")


    IResposta salve(IResposta resposta) {
        return resposta.save()
    }

    boolean cancele(IResposta resposta) {
        StatelessSession newSession = sessionFactory.openStatelessSession()
        Transaction tx = newSession.beginTransaction()
        Resposta sessionResposta = Resposta.get(resposta.id)
        try {
            sessionResposta.mudeEstadoPara(EstadoResposta.CANCELADA)
            compraService.updateRespostaCompra(resposta.id, EstadoResposta.CANCELADA)
            compraService.removeRespostasFromItens(resposta.cotacao.id, sessionResposta.id)
        } catch (Exception e) {
            sessionResposta.mudeEstadoPara(EstadoResposta.AGUARDANDO_ANALISE)
            compraService.updateRespostaCompra(resposta.id, EstadoResposta.AGUARDANDO_ANALISE)
            new AnaliseFactory().crieItensRespostaCompra(resposta.id)
            log.debug e
        } finally {
            tx.commit()
            newSession.close()
        }
        return resposta.estado instanceof RespostaCancelada
    }

    IResposta ressuscite(IResposta resposta) {
        StatelessSession newSession = sessionFactory.openStatelessSession()
        Transaction tx = newSession.beginTransaction()
        Resposta sessionResposta = Resposta.get(resposta.id)
        try {
            sessionResposta.mudeEstadoPara(EstadoResposta.RESPONDENDO)
            compraService.updateRespostaCompra(resposta.id, EstadoResposta.RESPONDENDO)
            sessionResposta.lida = false
            Compra compra = compraService.getCompraByCotacaoId(sessionResposta.cotacaoId)
            compraService.removeRespostasFromItens(cotacao.id, sessionResposta.id)

            if (compra.estado != EstadoCotacao.AGUARDANDO_RESPOSTAS) {
                Cotacao cotacao = Cotacao.get(sessionResposta.cotacaoId)
                cotacao.mudeEstadoPara(EstadoCotacao.AGUARDANDO_RESPOSTAS)
                compraService
                        .updateRespostaCompra(
                                sessionResposta.cotacaoId,
                                EstadoCotacao.AGUARDANDO_RESPOSTAS
                        )
            }
        } catch (Exception e) {
            sessionResposta.mudeEstadoPara(EstadoResposta.AGUARDANDO_ANALISE)
            compraService.updateRespostaCompra(resposta.id, EstadoResposta.AGUARDANDO_ANALISE)
            log.debug e
        } finally {
            tx.commit()
            newSession.close()
        }
        return resposta
    }

}