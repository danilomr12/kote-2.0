package br.com.kote.empresa

import br.com.kote.usuario.Comprador

class Cliente extends Empresa {
    Comprador comprador

    static constraints = {
        comprador(nullable: true)
    }
    Set<Atendimento> getAtendimentos(){
        return Atendimento.findAllByCliente(this)
    }

    String toString(){
        super.toString()
    }
}
