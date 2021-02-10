package br.com.kote.util

import br.com.kote.produto.EmbalagemVenda
import br.com.kote.produto.Produto
import br.com.kote.produto.TipoEmbalagem

class ProdutoFixture {

    static Produto crieProduto() {
        return new Produto(descricao: 'Produto Dummy',
                categoria: 'Peso Pesado',
                barCode: 'Codigo Dummy',
                fabricante: 'Fabricante Dummy',
                marca: "Dummy brand",
                peso: 12.5,
                embalagem: new EmbalagemVenda(
                        tipoEmbalagemDeVenda: new TipoEmbalagem(descricao: TipoEmbalagem.CX),
                        tipoEmbalagemUnidade: new TipoEmbalagem(descricao: "UN"),
                        qtdeDeUnidadesNaEmbalagemDeVenda: 12)
        )
    }

    static def crieProduto(int i) {
        return new Produto(
                barCode: "${i + 1}12${i}345${i}",
                descricao: "produto de teste " + i,
                categoria: "categoria" + i,
                fabricante: "fabricante" + i,
                marca: "marca1" + 1,
                embalagem: crieEmbalagemVenda(i)).save(flush: true)
    }

    static def crieEmbalagemVenda(Integer i) {
        return new EmbalagemVenda(tipoEmbalagemDeVenda: new TipoEmbalagem(descricao: "CX"),
                qtdeDeUnidadesNaEmbalagemDeVenda: i,
                tipoEmbalagemUnidade: new TipoEmbalagem(descricao: TipoEmbalagem.UN))
    }

    static List<Produto> crieListaDeProdutosDiferentesBarCode(int amount) {
        List<Produto> produtos = new ArrayList<Produto>()
        amount.times {
            Produto produto = crieProduto()
            produto.barCode = "789${it}${new Random().nextDouble().toInteger().toString()}"
            produtos.add(produto)
        }
        return produtos
    }

}
