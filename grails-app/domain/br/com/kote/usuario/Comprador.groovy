package br.com.kote.usuario

import br.com.kote.empresa.Cliente

class Comprador extends Usuario{

    static hasMany = [clientes: Cliente]

    static mappedBy = [clientes: 'comprador']

    static mapping = {
        clientes column: 'comprador_empresa_id'
    }

    String toString(){
        super.toString()
    }
}
