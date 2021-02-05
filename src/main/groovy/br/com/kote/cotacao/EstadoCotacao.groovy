package br.com.kote.cotacao

import br.com.kote.counter.AbstractCounter
import br.com.kote.usuario.Representante

abstract class EstadoCotacao {

    static final Integer RASCUNHO = 0
    static final Integer AGUARDANDO_RESPOSTAS = 1
    static final Integer PRONTA_PARA_ANALISE = 2
    static final Integer EM_ANALISE = 3
    static final Integer PRONTA_PARA_ENVIO_PEDIDOS = 4
    static final Integer AGUARDANDO_PEDIDOS = 5
    static final Integer PRONTA_PARA_ANALISE_FALTAS = 6
    static final Integer CANCELADA = 7
    static final Integer FINALIZADA = 8
    static final Integer PROCESSANDO_ENVIO_RESPOSTAS = 9

    static final Map descricao = [0: "Rascunho", 1: "Aguardando respostas", 2: "Pronta para análise", 3: "Cotação em análise",
                                  4: "Pronta para envio de pedidos", 5: "Aguardando pedidos", 6: "Pronta para análise de faltas", 7: "Cancelada",
                                  8: "Finalizada", 9: "Processando envio de respostas"]

    static final Map estado = [:]/*[0: new CotacaoRascunho(), 1: new CotacaoAguardandoRespostas(), 2: new CotacaoProntaParaAnalise(),
                                      3: new CotacaoEmAnalise(), 5: new CotacaoAguardandoPedidos(),
                                      6: new CotacaoProntaParaAnaliseFaltas(), 7: new CotacaoCancelada(), 8:  new CotacaoFinalizada(), 9: new CotacaoProcessandoEnvioDeRespostas()]*/

    void analisar(ICotacao cotacao) {
        throw new UnsupportedOperationException()
    }

    /*Analise getAnalise(ICotacao cotacao) {
        throw new UnsupportedOperationException()
    }*/

    boolean salve(ICotacao cotacao) {
        throw new UnsupportedOperationException()
    }

    void gereFalta(ICotacao cotacao) {
        throw new UnsupportedOperationException()
    }

    boolean cancele(ICotacao cotacao) {
        throw new UnsupportedOperationException()
    }

    /*List<Pedido> gerePedidosPrimeiraOrdem(ICotacao cotacao) {
        throw new UnsupportedOperationException()
    }*/

    String exporteExcelAnalise(ICotacao cotacao) {
        throw new UnsupportedOperationException()
    }

    boolean envie(ICotacao cotacao, List<Representante> representantes) {
        throw new UnsupportedOperationException()
    }

    void finalize(ICotacao cotacao) {
        throw new UnsupportedOperationException()
    }

    void gereCotacaoDeFalta(ICotacao cotacao) {
        throw new UnsupportedOperationException()
    }

    void aguardeAnalise(ICotacao cotacao) {
        throw new UnsupportedOperationException()
    }

    AbstractCounter makeRespostaCounter(ICotacao cotacao) {
        throw new UnsupportedOperationException()
    }

    String getCompraId(ICotacao cotacao) {
        throw new UnsupportedOperationException()
    }

    /**
     * Um estado são diferenciado do outro pelo nome da classe, já que os estados são "stateless" isto é
     * não possuem campos.
     */
    final boolean equals(Object object) {
        if (this.is(object))
            return true
        if (object == null)
            return false
        return getClass().getName().equals(object.getClass().getName())
    }

    final int hashCode() {
        return getClass().getName().hashCode()
    }

}