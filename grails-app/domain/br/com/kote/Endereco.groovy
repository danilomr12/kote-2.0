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

    static final estados = ["AC": "Acre",
                            "AL": "Alagoas",
                            "AP": "Amapá",
                            "AM": "Amazonas",
                            "BA": "Bahia",
                            "CE": "Ceará",
                            "DF": "Distrito Federal",
                            "ES": "Espírito Santo",
                            "GO": "Goiás",
                            "MA": "Maranhão",
                            "MT": "Mato Grosso",
                            "MS": "Mato Grosso do Sul",
                            "MG": "Minas Gerais",
                            "PA": "Pará",
                            "PB": "Paraíba",
                            "PR": "Paraná",
                            "PE": "Pernambuco",
                            "PI": "Piauí",
                            "RJ": "Rio de Janeiro",
                            "RN": "Rio Grande do Norte",
                            "RS": "Rio Grande do Sul",
                            "RO": "Rondônia",
                            "RR": "Roraima",
                            "SC": "Santa Catarina",
                            "SP": "São Paulo",
                            "SE": "Sergipe",
                            "TO": "Tocantins"]

    static belongsTo = [empresa: Empresa]

    // TODO Criar custom validators para alguns campos @Alberto
    static constraints = {
        logradouro nullable: false, blank: false
        estado nullable: false, blank: false, inList: estados.keySet() as List
        cidade nullable: false, blank: false
        bairro nullable: false, blank: false
        cep nullable: true, blank: true
        numero nullable: true, blank: true
        complemento nullable: true, blank: true
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
