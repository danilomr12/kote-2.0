package br.com.kote.resposta

import br.com.kote.cotacao.Cotacao
import br.com.kote.usuario.Representante

class Resposta implements IResposta, ITransicaoEstadoResposta {

    static belongsTo = [cotacao: Cotacao]

    Representante representante

    Integer codigoEstado
    EstadoResposta estado
    static transients = ['estado']

    Date dataCriacao
    Date dataSalva

    Boolean lida = false

    String respostaUrlDigest

    //List<Pedido> pedidos = new ArrayList<Pedido>()
    static hasMany = [/*pedidos: Pedido,*/ itens: ItemResposta]

    static mapping = {
        id generator: 'identity'
        respostaUrlDigest column: 'resposta_digest'
    }
    static constraints = {
        representante(nullable: false)
        codigoEstado(nullable: false)
        respostaUrlDigest(nullable: true)
        //pedidos(nullable: true, minSize: 0)
    }

    def beforeUpdate = {
        if (!this.respostaUrlDigest)
            this.respostaUrlDigest = getDigest()
        if (!this.dataCriacao)
            this.dataCriacao = new Date()
        this.dataSalva = new Date()
    }

    def beforeInsert = {
        if (!this.dataCriacao)
            this.dataCriacao = new Date()
        if (!this.respostaUrlDigest)
            this.respostaUrlDigest = getDigest()
        this.dataSalva = new Date()
    }

    private def getDigest() {
        return (this.dataCriacao.time.toString()).encodeAsUrlDigest()
    }

    //legado, provavelmente não será mais usado
     static IResposta getRespostaForDigest(String respostaDigest) {
        Resposta.find "from Resposta where resposta_digest =:respostaDigest",[respostaDigest:respostaDigest]
    }

     void setCodigoEstado(Integer codigoEstado) {
        this.codigoEstado = codigoEstado
        this.estado = EstadoResposta.estado.get(codigoEstado) as EstadoResposta
    }

     boolean addItemResposta(ItemResposta itemResposta) {
        if (itemResposta.validate())
            this.addToItens(itemResposta)
        return this.itens?.contains(itemResposta)
    }

/*
    boolean addPedido(Pedido pedido) {
        if(pedido.validate())
            this.addToPedidos(pedido)
        return this.pedidos?.contains(pedido)
    }
*/

     boolean concluida() {
        return (this.codigoEstado == EstadoResposta.PEDIDO_FATURADO)
    }

     boolean envie() {
        this.estado.envie(this)
    }

     void descarte() {
        this.estado.descarte(this)
    }

     boolean cancele() {
        this.estado.cancele(this)
    }

     void recuse() {
        this.estado.recuse(this)
    }

     void aceite() {
        this.estado.aceite(this)
    }

     Boolean faturePedido(){
        this.estado.faturePedido(this)
    }

     IResposta ressuscite() {
        this.estado.ressuscite(this)
    }

     IResposta salve() {
        this.estado.salve(this)
    }

    String criePlanilhaParaPreenchimentoOffLine() {
        this.estado.criePlanilhaParaPreenchimentoOffLine(this)
    }

     void mudeEstadoPara(Integer codigoEstado) {
        this.setCodigoEstado(codigoEstado)
        this.lida = false
        this.save(flush: true)
    }

     int hashCode() {
        if (this.id == null)
            return super.hashCode()
        return this.id.hashCode()
    }

     boolean equals(Object object) {
        if (this.is(object))
            return true
        if ((object == null) || !(object instanceof IResposta))
            return false

        IResposta respostaToCompare = object as IResposta
        if (this.id.is(respostaToCompare.id))
            return true
        if ((this.id == null) || (respostaToCompare.id == null))
            return false
        return this.id.equals(object.id)
    }

     double calculePrecoPedido() {
        return 0  //To change body of implemented methods use File | Settings | File Templates.
        //TODO implementar metodo que fornece o valor total do pedido
    }

     int calculeNumItensFaltantes() {
        return 0  //To change body of implemented methods use File | Settings | File Templates.
        //TODO implementar metodo que recupera qtdPedida de itens faltantes
    }

     def importePlanilhaResposta(Resposta resposta, String pathPlanilha) {
        estado.importePlanilhaResposta(resposta, pathPlanilha)
    }

     String toString(){
        return this.id + " - " + this.representante.nome + " - " + EstadoResposta.descricao[this.codigoEstado]
    }
}