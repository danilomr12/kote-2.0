package br.com.kote.resposta.estados

import br.com.analise.service.ICompraService
import br.com.kote.cotacao.CotacaoHandler
import br.com.kote.resposta.EstadoResposta
import br.com.kote.resposta.IResposta
import grails.core.GrailsApplication

class RespostaNovaCotacao extends EstadoResposta {

    CotacaoHandler cotacaoHandler
    def ctx = GrailsApplication.mainContext
    def compraService = (ICompraService) ctx.getBean("compraService")

    /*boolean cancele(IResposta resposta) {
        resposta.lida = false
        resposta.mudeEstadoPara(EstadoResposta.CANCELADA)
        compraService.updateRespostaCompra(resposta.id, EstadoResposta.CANCELADA)
        resposta.estado instanceof RespostaCancelada
    }*/

    void descarte(IResposta resposta) {
        compraService.updateRespostaCompra(resposta.id, EstadoResposta.PERDIDA)
        resposta.mudeEstadoPara(EstadoResposta.PERDIDA)
    }

    /*void recuse(IResposta resposta) {
        resposta.lida = false
        resposta.mudeEstadoPara(EstadoResposta.RECUSADA)
        compraService.updateRespostaCompra(resposta.id, EstadoResposta.RECUSADA)
        cotacaoHandler.dispatch(CotacaoHandler.RECUSA_RESPOSTA, resposta)
    }*/

    void aceite(IResposta resposta) {
        resposta.lida = false
        resposta.mudeEstadoPara(EstadoResposta.RESPONDENDO)
        compraService.updateRespostaCompra(resposta.id, EstadoResposta.RESPONDENDO)
    }

    IResposta salve(IResposta resposta) {
        return resposta.save()
    }


}