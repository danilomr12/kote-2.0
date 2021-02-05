package br.com.kote.cotacao

import br.com.kote.counter.AbstractCounter
import br.com.kote.resposta.IResposta
import br.com.kote.resposta.Resposta
import br.com.kote.usuario.Representante

interface ICotacao {

    ItemCotacao addItemCotacao(ItemCotacao itemCotacao)

    IResposta addResposta(IResposta resposta)

    AbstractCounter buildPedidosCounter()

    AbstractCounter makeRespostasCounter()

    boolean envie(List<Representante> representantes)

    void gereCotacaoDeFalta()

    void gereFalta()

    def gerePedidosPrimeiraOrdem()

    String getCompraId()

    String exporteExcelAnalise()

    boolean salve()

    boolean cancele()

    void finalize()

    void aguardeAnalise()

    void analisar()

    def canceleResposta(Resposta resposta)

    void mudeEstadoPara(Integer codidoEstadoCotacao)

    Boolean isAguardandoPedidos()

    Boolean isAguardandoRespostas()

    Boolean isCancelada()

    Boolean isEmAnalise()

    Boolean isFinalizada()

    Boolean isProntaParaAnalise()

    Boolean isProntaParaAnaliseFaltas()

    Boolean isRascunho()

}