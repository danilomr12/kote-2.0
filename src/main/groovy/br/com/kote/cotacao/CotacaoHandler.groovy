package br.com.kote.cotacao


import br.com.kote.resposta.IResposta

class CotacaoHandler {

    private static CotacaoHandler instance

    public static final String ENVIO_RESPOSTA = "trateEnvieResposta"
    public static final String RECUSA_RESPOSTA = "trateRecuseResposta"
    public static final String ENVIO_PEDIDO = "trateEnvioPedido"

    private CotacaoHandler() {
    }

    static CotacaoHandler getInstance() {
        if (!instance) {
            instance = new CotacaoHandler()
        }
        return instance
    }

    def dispatch(String eventType, Object... eventObjects) {
        this.getMetaClass().invokeMethod(this, eventType, eventObjects)
    }

    private void trateRecuseResposta(IResposta resposta) {
        resposta
    }
/*

    private def trateEnvieResposta(IResposta resposta, Boolean analiseAutomatica) {
        ICotacao cotacao = Cotacao.get(resposta.cotacao.id)

        RespostaCounter respostaCounter = cotacao.makeRespostasCounter()

        if (analiseAutomatica) {
        //    def stopWatch = new LoggingStopWatch()
                new AnaliseFactory().crieItensRespostaCompra(resposta.id)
            stopWatch.stop("Tempo criação itens resposta fact")
        }
        if (respostaCounter.faltando == 0){
                cotacao.aguardeAnalise()
        }
    }

    private def trateEnvioPedido(IResposta resposta){
        ICotacao cotacao = Cotacao.get(resposta.cotacao.id)

        PedidoCounter pedidoCounter = cotacao.buildPedidosCounter()
        if(pedidoCounter.faltando == 0)
            cotacao.finalize()
    }
*/

}
