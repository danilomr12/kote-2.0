package br.com.kote

import br.com.kote.usuario.Comprador
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CompradorSpec extends Specification implements DomainUnitTest<Comprador> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
