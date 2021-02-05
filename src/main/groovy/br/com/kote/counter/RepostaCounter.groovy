package br.com.kote.counter

import br.com.kote.resposta.EstadoResposta
import br.com.kote.resposta.IResposta

class RespostaCounter extends AbstractCounter {

    RespostaCounter(cotacao) {
        this.cotacao = cotacao
        List<IResposta> respostasRecebidas = cotacao.respostas.findAll {
            it.codigoEstado != EstadoResposta.NOVA_COTACAO && it.codigoEstado != EstadoResposta.RESPONDENDO
        } as List
        if (!respostasRecebidas)
            this.recebido = 0
        else
            this.recebido = respostasRecebidas.size()
        this.faltando = cotacao.respostas.size() - recebido
    }
}