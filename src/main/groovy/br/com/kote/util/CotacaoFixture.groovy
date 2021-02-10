package br.com.kote.util

import br.com.kote.cotacao.CotacaoFactory
import br.com.kote.cotacao.ICotacao
import br.com.kote.empresa.Cliente

class CotacaoFixture {

    static ICotacao crieCotacao() {
        ICotacao cotacao = CotacaoFactory.crie("cotacao de teste", "mensagem", new Date(2010, 10, 10),
                new Date(2010, 10, 10), "35", Cliente.build(), crieEndereco())

        10.times { int index ->
            cotacao.addToItens(crieItemCotacao(index + 1))
        }

        cotacao.salve()
        return cotacao
    }
}
