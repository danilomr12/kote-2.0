package br.com.kote.resposta.estados

import br.com.analise.service.ICompraService
import br.com.kote.resposta.EstadoResposta
import br.com.kote.resposta.IResposta
import grails.core.GrailsApplication


/**Sem o "import import br.com.cotecom.domain.cotacao.CotacaoHandler"
 * o atributo cotacaoHandler não funciona mesmo estando na superclasse EstadoResposta
 * */

class RespostaRespondendo extends EstadoResposta {

    def ctx = GrailsApplication.getMainContext()
    def compraService = (ICompraService) ctx.getBean("compraService")


    /*boolean cancele(IResposta resposta) {
        resposta.lida = false
        resposta.mudeEstadoPara(EstadoResposta.CANCELADA)
        compraService.updateRespostaCompra(resposta.id, EstadoResposta.CANCELADA)
        return resposta.estado instanceof RespostaCancelada
    }*/

    void recuse(IResposta resposta) {
        resposta.lida = false
        resposta.mudeEstadoPara(EstadoResposta.RECUSADA)
        compraService.updateRespostaCompra(resposta.id, EstadoResposta.RECUSADA)
        cotacaoHandler.dispatch(CotacaoHandler.RECUSA_RESPOSTA, resposta)
    }

    boolean envie(IResposta resposta) {
        resposta.lida = false
        resposta.mudeEstadoPara(EstadoResposta.AGUARDANDO_OUTRAS_RESPOSTAS)
        if (resposta.salve()) {
            cotacaoHandler.dispatch(CotacaoHandler.ENVIO_RESPOSTA, resposta, true)
            return true
        }
        return false
    }

    IResposta salve(IResposta resposta) {
        return resposta.save()
    }

    /* String criePlanilhaParaPreenchimentoOffLine(IResposta resposta) {
         return new ExcelExport().exporteFormularioRespostaCotacao(resposta)
     }

     def importePlanilhaResposta(Resposta resposta, String pathPlanilha) {
         new ExcelImport(new ExcelFile(pathPlanilha)).importePlanilhaResposta(resposta)
     }*/
}