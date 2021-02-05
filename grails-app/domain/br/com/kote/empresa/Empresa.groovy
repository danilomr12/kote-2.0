package br.com.kote.empresa

import br.com.kote.Endereco
import br.com.kote.Telefone
import br.com.kote.usuario.Usuario

class Empresa {

    static final String FORNECEDOR = "Fornecedor"
    static final String CLIENTE = "Cliente"
    static final String KOTE = "KOTE"

    protected Empresa() {}

    String nomeFantasia
    String razaoSocial
    String cnpj
    String email

    Endereco endereco

    Ramo ramo
    static embedded = ['ramo']

    static hasMany = [telefones: Telefone, usuarios: Usuario]
    static hasOne = [endereco: Endereco]
    static mapping = {
        id generator: 'identity'
        telefones column: 'empresa_id', cascade: 'all-delete-orphan', lazy: "false"
        usuarios cascade: "all"
    }

    static transients = ['tipo']

    static constraints = {
        nomeFantasia(nullable: false, blank: false)
        razaoSocial(nullable: true, blank: true)
        cnpj(nullable: true, blank: true)
        email(email: true, nullable: true, blank: true)
        ramo(nullable: true)
        //categorias(nullable: true, size: 0..10)
        endereco(nullable: true)
        telefones(nullable: true, size: 0..10)
        usuarios()
    }

    int hashCode() {
        if (this.id == null)
            return super.hashCode()
        return this.id.hashCode()
    }

    boolean equals(Object object) {
        if (this.is(object))
            return true
        if ((object == null) || !(object instanceof Empresa))
            return false

        Empresa empresaToCompare = object as Empresa
        if (this.id.is(empresaToCompare.id))
            return true
        if ((this.id == null) || (empresaToCompare.id == null))
            return false
        return this.id.equals(object.id)
    }

    String getTipo(){
        if(this.instanceOf(Fornecedor))
            return FORNECEDOR
        else if (this.instanceOf(Cliente))
            return CLIENTE
        else
            return KOTE
    }

    Boolean addUsuario(Usuario usuario) {
        if (!usuario.validate()) return false
        this.addToUsuarios usuario
        return usuarios.contains(usuario)
    }

    Boolean addTelefone(Telefone telefone) {
        if (!telefone.validate()) return false
        this.addToTelefones telefone
        return telefones.contains(telefone)
    }

    String toString(){
        return this.id + " - " + this.tipo + " - " + this.nomeFantasia
    }

}

class Ramo {

    static final String SUPERMERCADO = 'Supermercado'
    static final String FERRAGISTA = 'Ferragista'
    static final String FARMACIA = 'Farmácia'
    static final String CONSTRUCAO = 'Construção'

    String tipo

    static mapping = {
        tipo column: 'ramo'
    }
    static constraints = {
        tipo nullabble: false, inList: [
                this.SUPERMERCADO,
                this.FARMACIA,
                this.FERRAGISTA,
                this.CONSTRUCAO
        ]
    }
}
