package br.com.kote.cotacao

import br.com.kote.Endereco
import br.com.kote.counter.AbstractCounter
import br.com.kote.empresa.Cliente
import br.com.kote.resposta.IResposta
import br.com.kote.resposta.Resposta
import br.com.kote.usuario.Representante

class Cotacao implements ICotacao, ITransicaoEstadoCotacao {

    String titulo
    String mensagem

    Date dataCriacao
    Date dataEntrega
    Date dataValidade
    Date dataSalva
    String prazoPagamento
    Cliente empresa
    Endereco enderecoEntrega
    String compraId

    Integer codigoEstadoCotacao
    EstadoCotacao estado
    static transients = ['estado', 'analise']

    static hasMany = [itens: ItemCotacao, respostas: Resposta]

    static mapping = {
        id generator: 'identity'
        cache: true
        enderecoEntrega cascade: 'all-delete-orphan'
        respostas cascade: 'delete'
    }

    static constraints = {
        titulo nullable: true
        dataCriacao nullable: false
        dataEntrega nullable: true
        dataValidade nullable: true
        dataSalva nullable: false
        prazoPagamento nullable: true
        empresa nullable: false
        enderecoEntrega nullable: true
        codigoEstadoCotacao nullable: false
        mensagem(nullable: true, blank: true)
        compraId(nullable: true, blank: true)
    }

    def beforeUpdate = {
        if (!this.dataCriacao)
            this.dataCriacao = new Date()
        this.dataSalva = new Date()
    }

    def beforeInsert = {
        if (!this.dataCriacao)
            this.dataCriacao = new Date()
        this.dataSalva = new Date()
    }

    void setCodigoEstadoCotacao(Integer codigoEstado) {
        this.codigoEstadoCotacao = codigoEstado
        this.estado = EstadoCotacao.estado.get(codigoEstado)
    }

    ItemCotacao addItemCotacao(ItemCotacao itemCotacao) {
        if (itemCotacao.validate())
            this.addToItens(itemCotacao)
        if (this.itens.contains(itemCotacao))
            return itemCotacao
        return null
    }

    IResposta addResposta(IResposta resposta) {
        if (resposta.validate())
            this.addToRespostas(resposta)
        if (this.respostas.contains(resposta))
            return resposta
        return null
    }

    Set<IResposta> getRespostas() {
        this.respostas
    }

    AbstractCounter makeRespostasCounter() {
        return estado.makeRespostaCounter(this)
    }

    boolean salve() {
        return estado.salve(this)
    }

    void analisar() {
        estado.analisar(this)
    }

    void aguardeAnalise() {
        estado.aguardeAnalise(this)
    }

    void finalize() {
        estado.finalize(this)
    }

    boolean cancele() {
        return estado.cancele(this)
    }

    void gereFalta() {
        estado.gereFalta(this)
    }

    AbstractCounter buildPedidosCounter() {
        // return new PedidoCounter(this)
        return null
    }

    void gereCotacaoDeFalta() {
        estado.gereCotacaoDeFalta(this)
    }

    boolean envie(List<Representante> representantes) {
        return estado.envie(this, representantes)
    }

    Boolean isRascunho() {
        return estado.equals(EstadoCotacao.RASCUNHO)
    }

    Boolean isAguardandoRespostas() {
        return estado.equals(EstadoCotacao.AGUARDANDO_RESPOSTAS)
    }

    Boolean isProntaParaAnalise() {
        return estado.equals(EstadoCotacao.PRONTA_PARA_ANALISE)
    }

    Boolean isEmAnalise() {
        return estado.equals(EstadoCotacao.EM_ANALISE)
    }

    Boolean isAguardandoPedidos() {
        return estado.equals(EstadoCotacao.AGUARDANDO_PEDIDOS)
    }

    Boolean isProntaParaAnaliseFaltas() {
        return estado.equals(EstadoCotacao.PRONTA_PARA_ANALISE_FALTAS)
    }

    Boolean isFinalizada() {
        return estado.equals(EstadoCotacao.FINALIZADA)
    }

    Boolean isCancelada() {
        return estado.equals(EstadoCotacao.CANCELADA)
    }

    void mudeEstadoPara(Integer codigoEstadoCotacao) {
        this.setCodigoEstadoCotacao(codigoEstadoCotacao)
    }

    def canceleResposta(Resposta resposta) {
        resposta.mudeEstadoPara(EstadoResposta.CANCELADA)
    }

    /*List<Pedido>*/

    def gerePedidosPrimeiraOrdem() {
        estado.gerePedidosPrimeiraOrdem(this)
    }

    String exporteExcelAnalise() {
        estado.exporteExcelAnalise(this)
    }

    int hashCode() {
        if (this.id == null)
            return super.hashCode()
        return this.id.hashCode()
    }

    boolean equals(Object object) {
        if (this.is(object))
            return true
        if ((object == null) || !(object instanceof ICotacao))
            return false

        ICotacao cotacaoToCompare = object as ICotacao
        if (this.id.is(cotacaoToCompare.id))
            return true
        if ((this.id == null) || (cotacaoToCompare.id == null))
            return false
        return this.id.equals(object.id)
    }

    String toString() {
        return this.id + "- " + this.titulo + " - " + EstadoCotacao.descricao[this.codigoEstadoCotacao]
    }
}
