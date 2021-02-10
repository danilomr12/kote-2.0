package br.com.kote.produto

import grails.rest.Resource

@Resource(uri = '/produto', formats = ['json'])
class Produto {

    Long id
    Integer version
    String descricao
    String categoria
    String barCode
    String fabricante
    String marca
    EmbalagemVenda embalagem
    Double peso
    Integer qtdMaster = 1
    String empresaId
    String embalagemMaster
    Date dataDelecao
    Date dataModificacao

    static transients = ['possuiDataDelecao', 'possuiDataModificacao']
    boolean possuiDataDelecao
    boolean possuiDataModificacao

    static embedded = ['embalagem']

    static mapping = {
        id generator: 'identity'
        descricao index: 'descricao_index'
    }

    static constraints = {
        barCode(nullable: true, blank: true)
        descricao(nullable: false, blank: false)
        categoria(nullable: true, blank: true)
        fabricante(nullable: true)
        marca(nullable: true, blank: true)
        peso(nullable: true)
        embalagem(nullable: true)
        qtdMaster(nullable: true, min:1)
        embalagemMaster(nullable: true, blank: true)
        dataDelecao(nullable: true)
        dataModificacao(nullable: true)
        empresaId(blank: true, nullable: true)
    }

    def beforeInsert = {
        if(this.embalagem == null) {
            this.embalagem = new EmbalagemVenda(tipoEmbalagemDeVenda: new TipoEmbalagem(descricao: TipoEmbalagem.CX),
                    qtdeDeUnidadesNaEmbalagemDeVenda: 1, tipoEmbalagemUnidade: new TipoEmbalagem(descricao: TipoEmbalagem.UN))
        }
    }

    void setDataDelecao( val ){
        this.dataDelecao = val
        this.possuiDataDelecao = null != val
    }

    void setDataModificacao( val ){
        this.dataModificacao = val
        this.possuiDataModificacao = null != val
    }

    int hashCode() {
        if (this.id == null)
            return super.hashCode()
        return this.id.hashCode()
    }

    boolean equals(Object object) {
        if (this.is(object))
            return true
        if ((object == null) || !(object instanceof Produto))
            return false

        Produto produtoToCompare = object as Produto
        if(this.id.is(produtoToCompare.id))
            return true
        if((this.id == null) || (produtoToCompare.id == null))
            return false
        return this.id.equals(object.id)
    }

    boolean equalsPropriedades(Produto produtoAComparar) {
        if(this.barCode != produtoAComparar.barCode)
            return false
        if(this.descricao != produtoAComparar.descricao)
            return false
        if(this.categoria != produtoAComparar.categoria)
            return false
        if(this?.embalagem != produtoAComparar?.embalagem)
            return false
        if(this.marca != produtoAComparar.marca)
            return false
        if(this.fabricante != produtoAComparar.fabricante)
            return false
        if(this.peso != produtoAComparar.peso)
            return false
        return true
    }

    Integer calculeQuantidadeDaEmbalagem() {
        this.embalagem.toString().getAt(3..6).toInteger()
    }

    String toString() {
        return this.id + " - " + this.barCode + " - " + this.descricao + " - " + this.embalagem.toString()
    }

    void setEmbalagem(EmbalagemVenda embalagemVenda) {
        this.embalagem = embalagemVenda
        embalagemVenda.produto = this
    }
}
