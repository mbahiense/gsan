package gcom.cadastro.imovel;

import gcom.util.filtro.Filtro;

/**
 * Filtro Imovel Tipo Constru��o
 * 
 * @author Administrador
 */
public class FiltroImovelTipoConstrucao extends Filtro {
	
	private static final long serialVersionUID = 1L;

    /**
     * Construtor da classe FiltroDespejo
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroImovelTipoConstrucao(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * Construtor da classe FiltroDespejo
     */
    public FiltroImovelTipoConstrucao() {
    }

    /**
     * Description of the Field
     */
    public final static String ID = "id";

    /**
     * Description of the Field
     */
    public final static String INDICADOR_USO = "indicadorUso";
    
    public final static String DESCRICAO = "descricao";

}
