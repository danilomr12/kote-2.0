package br.com.kote.resposta

import br.com.analise.domain.RespostaCompra
import br.com.analise.service.ICompraService
import br.com.kote.cotacao.Cotacao
import br.com.kote.cotacao.ItemCotacao
import br.com.kote.usuario.Representante

class RespostaFactory {

    private static int BATCH_SIZE = 50
    private static final int MAIL_SEND_RATE_PER_SECOND = 500

    //def sessionFactory = ctx.sessionFactory
    //NotifierService notifierService
    // From middleware jar
    ICompraService compraService = (ICompraService) ctx.getBean("compraService")

    void crie(List representanteIds, Long cotacaoId) {
        log.debug("Iniciando criação de respostas para cotação - id: ${cotacaoId} enviada")
        /*StatelessSession newSession = sessionFactory.openStatelessSession();
        Transaction tx = newSession.beginTransaction();

        try{*/
        Cotacao sessionCotacao = Cotacao.createCriteria()
                .get {
                    eq 'id', cotacaoId
                    fetchMode "empresa", FetchMode.EAGER
                    fetchMode "itens", FetchMode.EAGER
                }
        Set<Resposta> respostas = []
        def respostasCompra = []
        //def stopWatch = new LoggingStopWatch()

        Representante.getAll(representanteIds).each { Representante representante ->
            def newResposta = new Resposta(
                    dataCriacao: new Date(),
                    dataSalva: new Date(),
                    representante: representante,
                    codigoEstado: EstadoResposta.NOVA_COTACAO,
                    cotacao: sessionCotacao)
                    .save()

            log.debug("Criada resposta - id: ${newResposta.id} do Representante: ${representante}")
            respostas << newResposta

            //Criação das respostas compras para atualizar a compra pelo serviço RMI
            respostasCompra << new RespostaCompra(
                    nomeRepresentante: representante.nome,
                    estadoResposta: newResposta.codigoEstado,
                    nomeFantasiaEmpresa: representante?.empresa?.nomeFantasia,
                    telefones: representante?.telefones?.collect { it.toString() },
                    idRepresentante: representante.id,
                    idResposta: newResposta.id,
                    emailRepresentante: representante.email
            )

            log.debug("Criada respostaCompra: ${respostasCompra.find { RespostaCompra it -> it.idRepresentante == representante.id }} correspondente à resposta ${newResposta}")

            if (respostas.size() != respostasCompra.size()) {
                log.error "respostaCompras não criadas. \nRespostas: ${respostas} \nRespostasCompra: ${respostasCompra}"
            }

            //Criação de itens de resposta para resposta correspondente
            sessionCotacao.itens.eachWithIndex { ItemCotacao itemCotacao, int i ->
                ItemResposta itemResposta = new ItemResposta(preco: new Preco())
                itemResposta.itemCotacao = itemCotacao
                itemResposta.resposta = newResposta
                itemResposta.save()
            }
            log.debug("Criados itens de resposta da resposta ${newResposta}")
        }
        compraService.saveRespostaCompraAndUpdateCompraByCotacaoId(respostasCompra, cotacaoId)
        //stopWatch.stop("criadas respostas")

        /*sessionCotacao.itens.eachWithIndex { ItemCotacao itemCotacao, int i ->
            respostas.each { Resposta resposta ->
                ItemResposta itemResposta = new ItemResposta(preco: new Preco())
                itemResposta.itemCotacao = itemCotacao
                itemResposta.resposta = resposta
                itemResposta.save()
            }
        }
        //stopWatch.stop("criados itens de resposta")*/

        respostasCompra.each { RespostaCompra resposta ->

            Thread.currentThread().sleep(MAIL_SEND_RATE_PER_SECOND)
            def emailComprador = sessionCotacao.empresa.comprador.email
            def mensagem = sessionCotacao.mensagem
            log.debug("Iniciando notificação de resposta: ${resposta} ao representante: ${resposta.emailRepresentante}")

            /*notifierService.notifiqueNovaCotacao(emailComprador, resposta.nomeRepresentante,
                    resposta.emailRepresentante, resposta.idResposta, mensagem, sessionCotacao.empresa.nomeFantasia)
*/
            log.debug("Finalizando notificação de resposta: ${resposta} ao representante: ${resposta.emailRepresentante}")
        }

        log.debug("Atualizando estado da compra com id de cotação: ${sessionCotacao.id} e estado ${sessionCotacao.estado} via midleware ")

        compraService.updateEstadoCompraByCotacaoId(sessionCotacao.id, EstadoCotacao.AGUARDANDO_RESPOSTAS)

        log.debug("Atualizado estado da compra com id de cotação: ${sessionCotacao.id} via midleware para ${EstadoCotacao.descricao[EstadoCotacao.AGUARDANDO_RESPOSTAS]}")
        log.debug("Atualizando estado da cotação ${sessionCotacao} via hql para ${EstadoCotacao.descricao[EstadoCotacao.AGUARDANDO_RESPOSTAS]}")

        Cotacao.executeUpdate("update Cotacao b set b.codigoEstadoCotacao='${EstadoCotacao.AGUARDANDO_RESPOSTAS}'" +
                " where b.id='${cotacaoId}'")

        log.debug("Atualizado estado da cotação ${sessionCotacao} via hql para ${EstadoCotacao.descricao[EstadoCotacao.AGUARDANDO_RESPOSTAS]}")

        def compraId = compraService.getCompraIdByCotacaoId(sessionCotacao.id)

        log.debug("atualizando id de compra: ${compraId} na cotação: ${cotacaoId} via hql")

        Cotacao.executeUpdate("update Cotacao c set c.compraId='${compraId}' where c.id='${cotacaoId}'")

        log.debug("atualizado id de compra: ${compraId} na cotação: ${cotacaoId} via hql")
        /*}
        catch (Exception e){
            e.printStackTrace();
            tx.rollback()
        }
        finally {
            tx.commit();
            newSession.close();
        }*/
    }

    /*private def cleanUpGorm() {
        def session = sessionFactory.getCurrentSession()
        session.flush()
        session.clear()
        DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP.get().clear()
    }*/

}
