package br.com.kote

import org.apache.commons.lang.builder.HashCodeBuilder

class Telefone {
    static final String CELULAR = "CELULAR"
    static final String FAX = "FAX"
    static final String COMERCIAL = "COMERCIAL"
    static final String RESIDENCIAL = "RESIDENCIAL"

    static final Set tipos = [CELULAR, FAX, COMERCIAL, RESIDENCIAL]

    String tipo
    String ddd
    String numero

    static mapping = {
        id generator: 'identity'
    }
    static constraints = {
        ddd(nullable: false, blank: false)
        numero(nullable: false, blank: false)
        tipo(inList: tipos)
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (ddd) builder.append(this.ddd)
        if (numero) builder.append(this.numero)
        if (tipo) builder.append(this.tipo)
        builder.toHashCode()
    }

    boolean equals(Object object) {
        if (this.is(object))
            return true
        if ((object == null) || !(object instanceof Telefone))
            return false

        Telefone telefoneToCompare = object as Telefone
        if(this.id.is(telefoneToCompare.id))
            return true
        if((this.id == null) || (telefoneToCompare.id == null))
            return false
        return this.id.equals(object.id)
    }

    String toString(){
        if (this.numero == null && this.ddd == null && this.tipo == null)
            return ""
        return "(${this.ddd})${this.numero} : ${this.tipo?.toLowerCase()}"
    }

    static Telefone buildFrom(String numero){
        if((numero ==~ /[0-9]{2}-[0-9]{8}/) || (numero ==~ /[0-9]{2}-[0-9]{7}/))
            return new Telefone(ddd:numero.substring(0,2), numero:numero.substring(3))
        return null
    }
}
