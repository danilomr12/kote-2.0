package br.com.kote.usuario

import br.com.kote.cotacao.ICotacao
import br.com.kote.resposta.Resposta
import grails.rest.Resource

@Resource(uri = '/representante', formats = ['json', 'xml'])
class Representante extends Usuario {

    def possuiRespostaDaCotacao(ICotacao cotacao) {
        String sql = "from Resposta where cotacao_id = '${cotacao.id}' and representante_id = '${this.id}'"
        def result = Resposta.executeQuery(sql)
        if (result == null || result.size() == 0)
            return false
        return true
    }

    String toString() {
        super.toString()
    }
}
