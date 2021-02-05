package br.com.kote.resposta.estados

import br.com.analise.service.ICompraService
import br.com.kote.cotacao.AnaliseFactory
import br.com.kote.resposta.EstadoResposta
import br.com.kote.resposta.IResposta
import br.com.kote.resposta.Resposta
import grails.core.GrailsApplication
import org.grails.datastore.mapping.transactions.Transaction
import org.hibernate.StatelessSession

class RespostaEmAnalise extends EstadoResposta {

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
            sessionResposta.mudeEstadoPara(EstadoResposta.EM_ANALISE)
            compraService.updateRespostaCompra(resposta.id, EstadoResposta.EM_ANALISE)
            new AnaliseFactory().crieItensRespostaCompra(resposta.id)
            log.debug e
        } finally {
            tx.commit()
            newSession.close()
        }
        return resposta.estado instanceof RespostaCancelada
    }

}