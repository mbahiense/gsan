package gcom.cobranca;

import gcom.util.filtro.Filtro;


/**
 * O filtro � respons�vel por armazenar os par�metros de pesquisa de pagamentos 
 *
 * @author Pedro Alexandre
 * @date 21/03/2006
 */
public class FiltroDocumentoCobranca extends Filtro {
	private static final long serialVersionUID = 1L;

    /**
     * Description of the Field
     */
    public final static String ID = "id";

    public final static String IMOVEL_ID = "imovel.id";
    
    public final static String CLIENTE_ID = "cliente.id";
    
    public final static String AVISO_BANCARIO_ID = "avisoBancario.id";
    
    public final static String ANO_MES_REFERENCIA_ARRECADACAO = "anoMesReferenciaArrecadacao";
    
    public final static String DATA_PAGAMENTO = "dataPagamento";
    
    public final static String GUIA_PAGAMENTO_DOCUMENTO_TIPO_ID = "guiaPagamento.documentoTipo.id";
    
    public final static String PAGAMENTO_ARRECADACAO_FORMA = "arrecadacaoForma.id";
    
    public final static String PAGAMENTO_SITUACAO_ATUAL_ID = "pagamentoSituacaoAtual.id";
    
    public final static String PAGAMENTO_GUIA_PAGAMENTO_CONTA_CLIENTE_CONTAS_CLIENTE_RELACAO_TIPO_ID = "guiaPagamento.conta.clienteContas.clienteRelacaoTipo.id";
    
    public final static String PAGAMENTO_GUIA_PAGAMENTO_CONTA_CLIENTE_CONTAS_CLIENTE_ID = "guiaPagamento.conta.clienteContas.cliente.id";
    
    public final static String PAGAMENTO_IMOVEL_CLIENTE_IMOVEIS_CLIENTE_RELACAO_TIPO_ID = "imovel.clienteImoveis.clienteRelacaoTipo.id";
    
    public final static String PAGAMENTO_IMOVEL_CLIENTE_IMOVEIS_CLIENTE_ID = "imovel.clienteImoveis.cliente.id";
    
    //public final static String DEVOLUCAO_GUIA_DEVOLUCAO_GUIA_PAGAMENTO_CLIENTE_GUIA_PAGAMENTO_CLIENTE_RELACAO_TIPO_ID = "guiaPagamento.guiaPagamento.clientesGuiaPagamento.clienteRelacaoTipo.id";
    
    
   
    
	public final static String PAGAMENTO_GUIA_PAGAMENTO_CLIENTE_GUIA_PAGAMENTO_CLIENTE_RELACAO_TIPO_ID = "guiaPagamento.clientesGuiaPagamento.clienteRelacaoTipo.id";

	/**
	 * Description of the Field
	 */	
	public final static String PAGAMENTO_GUIA_PAGAMENTO_CLIENTE_GUIA_PAGAMENTO_CLIENTE_ID = "guiaPagamento.clientesGuiaPagamento.cliente.id";
	
	/**
	 * Description of the Field
	 */	
	public final static String PAGAMENTO_GUIA_PAGAMENTO_CLIENTE_GUIA_PAGAMENTO_GUIA_PAGAMENTO_ID = "guiaPagamento.clientesGuiaPagamento.guiaPagamento.id";
    
   
    
    public final static String AVISO_BANCARIO_ARRECADADOR = "avisoBancario.arrecadador";
	
	/**
	 * Description of the Field
	 */
	public final static String DEBITO_TIPO = "debitoTipo.id";

	/**
	 * Description of the Field
	 */
	public final static String PAGAMENTO_SITUACAO_ATUAL_DESCRICAO = "pagamentoSituacaoAtual.descricaoPagamentoSituacao";
	
	/**
	 * Description of the Field
	 */
	public final static String PAGAMENTO_SITUACAO_ANTERIOR_DESCRICAO = "pagamentoSituacaoAnterior.descricaoPagamentoSituacao";
	
	/**
	 * Description of the Field
	 */
	public final static String GUIA_PAGAMENTO_ID = "guiaPagamento.id";

    
    /**
     * Construtor de FiltroPagamento 
     * 
     */
    public FiltroDocumentoCobranca() {
    }

    /**
     * Construtor da classe FiltroPagamento
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroDocumentoCobranca(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }
}
