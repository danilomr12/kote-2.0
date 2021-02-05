import br.com.kote.cotacao.CotacaoRascunho

// Place your Spring DSL code here
beans = {
    cotacaoRascunho(CotacaoRascunho) { bean ->
        bean.autowire = true

    }
}
