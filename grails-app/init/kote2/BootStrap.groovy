package kote2

import br.com.kote.Endereco
import br.com.kote.Telefone
import br.com.kote.empresa.Empresa
import br.com.kote.empresa.EmpresaKote
import br.com.kote.usuario.Administrador
import br.com.kote.usuario.Usuario
import br.com.kote.util.ProdutoFixture

class BootStrap {

    def init = { servletContext ->
        Empresa koteAdmin = new EmpresaKote(
                nomeFantasia: "Kote cotações inteligentes",
                razaoSocial: "KOTE LTDA",
                telefones: [new Telefone(ddd: "062", tipo: Telefone.CELULAR, numero: "9802-3311")],
                endereco: new Endereco(
                        logradouro: "Rua VV-5",
                        cidade: "Goiania",
                        estado: "GO",
                        bairro: "Village Veneza",
                        complemento: "cond. Invent Joy, apto 1501, Torre C1"
                )
        ).save()

        Usuario admin = new Administrador(
                nome: "Danilo Marques",
                telefones: koteAdmin.telefones.collect { new Telefone(it.properties) },
                email: "danilo@kote.com.br",
                habilitado: true,
                password: 123456)
        admin.save()

        ProdutoFixture.crieListaDeProdutosDiferentesBarCode(100).each {
            it.save()
        }
    }
    def destroy = {
    }
}
