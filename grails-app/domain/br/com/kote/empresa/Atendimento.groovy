package br.com.kote.empresa

import br.com.kote.usuario.Representante

class Atendimento {

    Cliente cliente
    Fornecedor fornecedor
    Representante representante

    static mapping = {
        cliente index:'atendimento_cliente_index'
    }
    static constraints = {
        representante(nullable:true, validator: { Representante representante, Atendimento atendimento ->
            if(!atendimento?.fornecedor && !representante)
                return false
            else if(atendimento?.fornecedor && representante)
                return representante?.empresa as Fornecedor == atendimento?.fornecedor
            return true
        })
        cliente nullable:false
        fornecedor nullable:true
    }
}
