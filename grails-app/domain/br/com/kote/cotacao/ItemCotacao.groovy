package br.com.kote.cotacao

import br.com.kote.produto.Produto
import br.com.kote.resposta.ItemResposta

class ItemCotacao {

    //static hasMany = [itensResposta: ItemResposta]

    Integer quantidade
    Produto produto

    static belongsTo = [cotacao: Cotacao]

    static mapping = {
        id generator:'identity'
        cache:true
        produto fetch:'join'
    }
    static constraints = {
        cotacao(nullable:false)
        produto(nullable:false)
    }

   /* List<ItemResposta> getItensRespostaDeItemCotacao(){
        return this.itensResposta
    }

    List<ItemResposta> getItensRespostaOrdenados(){
        return itensResposta.sort {ItemResposta first, ItemResposta sec ->
            if( first.preco.embalagem == null )
                return 1
            if( sec.preco.embalagem == null)
                return -1
            return first.preco.embalagem <=> sec.preco.embalagem
        }
    }

    void ordeneRespostas(){
        this.itensResposta = getItensRespostaOrdenados()
    }

    List getItensRespostaDeRespostasConcluidas() {
        return this.itensResposta.findAll { it.resposta.concluida() } as List
    }

    boolean addItemResposta(ItemResposta itemResposta){
        if(!itemResposta.validate())
            return false
        this.addToItensResposta(itemResposta)
        return this.itensResposta.contains(itemResposta)
    }
*/
    int hashCode() {
        if (this.id == null)
            return super.hashCode()
        return this.id.hashCode()
    }

    boolean equals(Object object) {
        if (this.is(object))
            return true
        if ((object == null) || !(object instanceof ItemCotacao))
            return false

        ItemCotacao itemToCompare = object as ItemCotacao
        if(this.id.is(itemToCompare.id))
            return true
        if((this.id == null) || (itemToCompare.id == null))
            return false
        return this.id.equals(object.id)
    }

    String toString(){
        this.id + " - " + this.quantidade + " - " + this.produto.descricao
    }
}