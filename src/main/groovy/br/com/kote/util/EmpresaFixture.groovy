package br.com.kote.util

import br.com.kote.Endereco
import br.com.kote.Telefone
import br.com.kote.empresa.Empresa
import br.com.kote.empresa.Fornecedor

class EmpresaFixture {

    static def crieFornnecedor() {
        Empresa empresa = new Fornecedor(

                email: "abc@terraatacado.com",
                nomeFantasia: "Lider Atacadista",
                razaoSocial: "Terra Atacado Distribuidor LTDA")
        empresa.addTelefone crieTelefone()
        empresa.endereco = crieEndereco()
        return empresa
    }

    static def crieTelefone() {
        return new Telefone(ddd: "123", tipo: Telefone.FAX, numero: "98754546")
    }

    static def crieEndereco() {
        return new Endereco(logradouro: "Avenida assis chateaubriand",
                cidade: "Goiania", estado: "GO", numero: 123, bairro: "Setor oeste")
    }

}
