package gcom.gui.faturamento.conta;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class InserirMensagemContaActionForm extends ValidatorActionForm {
	private static final long serialVersionUID = 1L;
	private String referenciaFaturamento;
	private String mensagemConta01;
	private String mensagemConta02;
	private String mensagemConta03;
	private String grupoFaturamento;
	private String gerenciaRegional;
	private String localidade;
	private String localidadeDescricao;
	private String[] setorComercial;
	private String setorComercialDescricao;
	
	private String qualidadeAgua;
	private String indiceInicial;
	private String indiceFinal;
	
	private String[] quadra;
	
	public String getGerenciaRegional() {
		return gerenciaRegional;
	}
	public void setGerenciaRegional(String gerenciaRegional) {
		this.gerenciaRegional = gerenciaRegional;
	}
	public String getGrupoFaturamento() {
		return grupoFaturamento;
	}
	public void setGrupoFaturamento(String grupoFaturamento) {
		this.grupoFaturamento = grupoFaturamento;
	}
	public String getLocalidade() {
		return localidade;
	}
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	public String getLocalidadeDescricao() {
		return localidadeDescricao;
	}
	public void setLocalidadeDescricao(String localidadeDescricao) {
		this.localidadeDescricao = localidadeDescricao;
	}
	public String getMensagemConta01() {
		return mensagemConta01;
	}
	public void setMensagemConta01(String mensagemConta01) {
		this.mensagemConta01 = mensagemConta01;
	}
	public String getMensagemConta02() {
		return mensagemConta02;
	}
	public void setMensagemConta02(String mensagemConta02) {
		this.mensagemConta02 = mensagemConta02;
	}
	public String getMensagemConta03() {
		return mensagemConta03;
	}
	public void setMensagemConta03(String mensagemConta03) {
		this.mensagemConta03 = mensagemConta03;
	}
	public String getReferenciaFaturamento() {
		return referenciaFaturamento;
	}
	public void setReferenciaFaturamento(String referenciaFaturamento) {
		this.referenciaFaturamento = referenciaFaturamento;
	}
	
	public String[] getSetorComercial() {
		return setorComercial;
	}
	public void setSetorComercial(String[] setorComercial) {
		this.setorComercial = setorComercial;
	}
	public String getSetorComercialDescricao() {
		return setorComercialDescricao;
	}
	public void setSetorComercialDescricao(String setorComercialDescricao) {
		this.setorComercialDescricao = setorComercialDescricao;
	}
	public String getIndiceFinal() {
		return indiceFinal;
	}
	public void setIndiceFinal(String indiceFinal) {
		this.indiceFinal = indiceFinal;
	}
	public String getIndiceInicial() {
		return indiceInicial;
	}
	public void setIndiceInicial(String indiceInicial) {
		this.indiceInicial = indiceInicial;
	}
	public String getQualidadeAgua() {
		return qualidadeAgua;
	}
	public void setQualidadeAgua(String qualidadeAgua) {
		this.qualidadeAgua = qualidadeAgua;
	}
	public String[] getQuadra() {
		return quadra;
	}
	public void setQuadra(String[] quadra) {
		this.quadra = quadra;
	}
	
	

}
