package br.com.kote

import br.com.kote.empresa.Empresa

class Endereco {

    // Informações em http://www.correios.com.br/servicos/cep/cep_formas.cfm

    String logradouro
    String numero
    String complemento
    String cidade
    String estado
    String bairro
    String cep

    static belongsTo = [empresa: Empresa]

    // TODO Criar custom validators para alguns campos @Alberto
    static constraints = {
        logradouro nullable: false, blank: false
        estado nullable: false, blank: false
        cidade nullable: false, blank: false
        cep nullable: true, blank: true
        numero nullable: true, blank: true
        complemento nullable: true, blank: true
        bairro nullable: false, blank: false
        empresa nullable: true
    }

    int hashCode() {
        if (this.id == null)
            return super.hashCode()
        return this.id.hashCode()
    }

    boolean equals(Object object) {
        if (this.is(object))
            return true
        if ((object == null) || !(object instanceof Endereco))
            return false

        Endereco enderecoToCompare = object as Endereco
        if (this.id.is(enderecoToCompare.id))
            return true
        if ((this.id == null) || (enderecoToCompare.id == null))
            return false
        return this.id.equals(object.id)
    }

    String toString() {
        return "${logradouro}, ${numero}. ${complemento}\n${bairro}\n${cidade} - ${estado}\n${cep}"
    }
}
