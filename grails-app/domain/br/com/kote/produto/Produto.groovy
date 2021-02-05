package br.com.kote.produto


//@Searchable
class Produto {

   // @SearchableId
    Long id
    Integer version

   // @SearchableProperty
    String descricao

   // @SearchableProperty
    String categoria

   // @SearchableProperty
    String barCode

   // @SearchableProperty
    String fabricante

    static transients = ['possuiDataDelecao', 'possuiDataModificacao']
   // @SearchableProperty
    boolean possuiDataDelecao
   // @SearchableProperty
    boolean possuiDataModificacao

   // @SearchableProperty
    String marca

    static embedded = ['embalagem']

   // @SearchableComponent
    EmbalagemVenda embalagem

   // @SearchableProperty
    Double peso

   // @SearchableProperty
    Integer qtdMaster = 1

  //  @SearchableProperty
    String empresaId

    String embalagemMaster

    Date dataDelecao
    Date dataModificacao

    static mapping = {
        id generator:'identity'
        descricao index:'descricao_index'
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
        empresaId(blank: false)
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

    Integer calculeQuantidadeDaEmbalagem(){
        this.embalagem.toString().getAt(3..6).toInteger()
    }

    String toString(){
        return this.id + " - " + this.barCode + " - " + this.descricao + " - " + this.embalagem.toString()
    }
}
