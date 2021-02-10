package br.com.kote.produto

import br.com.kote.empresa.Empresa
import br.com.kote.util.EmpresaFixture
import br.com.kote.util.ProdutoFixture
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.hibernate.SessionFactory
import spock.lang.Specification

@Integration
@Rollback
class ProdutoServiceSpec extends Specification {

    ProdutoService produtoService
    SessionFactory sessionFactory
    Produto produto

    private Long setupData() {
        Empresa empresa = EmpresaFixture.crieFornnecedor()
        empresa.save()
        produto = new Produto(
                descricao: 'Produto Dummy', empresaId: empresa.id,
                categoria: 'Peso Pesado', barCode: 'Codigo Dummy',
                fabricante: 'Fabricante Dummy',
                marca: "Paraguai",
                embalagem: new EmbalagemVenda(
                        tipoEmbalagemDeVenda: new TipoEmbalagem(descricao: TipoEmbalagem.CX),
                        tipoEmbalagemUnidade: new TipoEmbalagem(descricao: "DU"),
                        qtdeDeUnidadesNaEmbalagemDeVenda: 12),
                peso: 12.5).save(flush: true, failOnError: true)
        //new Produto(...).save(flush: true, failOnError: true)
        //Produto produto = new Produto(...).save(flush: true, failOnError: true)
        //new Produto(...).save(flush: true, failOnError: true)
        //new Produto(...).save(flush: true, failOnError: true)
        assert true, "TODO: Provide a setupData() implementation for this generated test suite"
        produto.id
    }

    void "test get"() {
        setupData()

        expect:
        produtoService.get(produto.id) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Produto> produtoListOffset1 = produtoService.list(max: 2, offset: 1)
        List<Produto> produtoListOffset0 = produtoService.list(max: 2, offset: 0)

        then:
        produtoListOffset1.size() == 0
        produtoListOffset0.size() == 1
        //assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        produtoService.count() == 1
    }

    void "test delete"() {
        Long produtoId = setupData()

        expect:
        produtoService.count() == 1

        when:
        produtoService.delete(produtoId)
        sessionFactory.currentSession.flush()

        then:
        produtoService.count() == 0
    }

    void "test save"() {
        when:
        Produto produto = new Produto(
                descricao: 'Produto Dummy',
                empresaId: EmpresaFixture.crieFornnecedor().save().id,
                categoria: 'Peso Pesado', barCode: 'Codigo Dummy',
                fabricante: 'Fabricante Dummy',
                marca: "Paraguai",
                embalagem: new EmbalagemVenda(
                        tipoEmbalagemDeVenda: new TipoEmbalagem(descricao: TipoEmbalagem.CX),
                        tipoEmbalagemUnidade: new TipoEmbalagem(descricao: "DU"),
                        qtdeDeUnidadesNaEmbalagemDeVenda: 12),
                peso: 12.5)
        produtoService.save(produto)

        then:
        produto.id != null
    }

    void "test save list of products and search"() {
        def produtos = ProdutoFixture.crieListaDeProdutosDiferentesBarCode(50).each {
            produtoService.save(it)
        }


        expect:
        produtos.size() == 50
        produtoService.count() == 50
        produtoService.list().every { it.descricao.contains("Produto Dummy") }
    }
}
