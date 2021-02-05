package br.com.kote.empresa

import br.com.kote.usuario.Usuario

class Fornecedor extends Empresa{
    /** Futuraimplementacao
    List<Usuario> getSupervisores(){
        Supervisor.findAllByEmpresa(this)
    }*/

    List<Usuario> getRepresentantes(){
        Representante.findAllByEmpresa(this)
    }

    static constraints = {
        email(email: true,unique: true, nullable: true, blank: true)
        endereco(nullable: true)
        usuarios(nullable: true, size: 0..10)
        categorias(nullable: true, size: 0..10)
    }

    String toString(){
        super.toString()
    }
}
