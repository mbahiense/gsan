package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.cliente.ClienteEndereco;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.IClienteFone;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ExibirReiterarRegistroAtendimentoAction
 * 
 * @author Vivianne Sousa
 * @date   09/05/2011
 * 
 */
public class ExibirReiterarRegistroAtendimentoAction extends GcomAction {
	/**
	 * Exibe a Tela para Reiterar o RA
	 * 
	 * @author Vivianne Sousa
	 * @date   09/05/2011
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("reiterarRegistroAtendimento");
		ReiterarRegistroAtendimentoActionForm form = (ReiterarRegistroAtendimentoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		//OBTENDO PROTOCOLO DE ATENDIMENTO
		String protocoloAtendimento = this.getFachada().obterProtocoloAtendimento();
		sessao.setAttribute("protocoloAtendimento", protocoloAtendimento);
		
		Fachada fachada = Fachada.getInstancia();
		validacoesRa( form,  fachada, usuarioLogado);
		obterDadosGeraisRa(form, fachada);
		
		//Verifica se o usu�rio deseja desfezar as altera��es efetuadas
		if (httpServletRequest.getParameter("desfazer") != null ||
				httpServletRequest.getParameter("limparDadosSolicitante") != null ||
				httpServletRequest.getParameter("resetarReiteracao") != null) {
			
			this.limparCliente(sessao);
			this.limparNomeSolicitante(sessao);
			this.limparUnidadeSolicitante(sessao);
//			form.setPontoReferencia("");
			form.setNomeSolicitante("");
			form.setIdClienteSolicitante("");
			form.setIdUnidadeSolicitante("");
			form.setObservacao("");
			sessao.removeAttribute("colecaoEnderecos");
 			sessao.removeAttribute("colecaoFonesSolicitante");
		}
		
    	String pesquisarCliente = httpServletRequest.getParameter("pesquisarCliente");
    	if (pesquisarCliente != null && !pesquisarCliente.equalsIgnoreCase("")) {
    		String idCliente = httpServletRequest.getParameter("cliente");
    		pesquisarDadosCliente(httpServletRequest, form, sessao, fachada, idCliente);
    	}
		
    	String pesquisarUnidade = httpServletRequest.getParameter("pesquisarUnidade");
    	if (pesquisarUnidade != null && !pesquisarUnidade.equalsIgnoreCase("")) {
    		
    		String idUnidade = httpServletRequest.getParameter("unidade");
    
    		if (idUnidade != null){
//    			sessao.setAttribute("enderecoPertenceCliente", "OK");
    			String nomeSolicitante = form.getNomeSolicitante();
    			form.setNomeSolicitante("");
    			
    			fachada.verificarExistenciaUnidadeSolicitanteDataAtual(
                new Integer(form.getNumeroRA()), new Integer(idUnidade), nomeSolicitante);
    					
    			form.setNomeSolicitante(nomeSolicitante);
    			form.setIdUnidadeSolicitante(idUnidade);
    			this.limparCliente(sessao);
    			this.limparNomeSolicitante(sessao);
    				
    			sessao.setAttribute("desabilitarDadosSolicitanteCliente", "OK");
    			sessao.setAttribute("desabilitarDadosSolicitanteNome", "OK");
    			sessao.setAttribute("habilitarAlteracaoEndereco", "SIM");
    			sessao.setAttribute("habilitarAlteracaoEnderecoAbaSolicitante", "NAO");
       			sessao.setAttribute("habilitarAlteracaoEnderecoSolicitante", "SIM");
    				
    		}
    		
    	}
		
    	String informadoNomeSolicitante = httpServletRequest.getParameter("informadoNomeSolicitante");
    	if (informadoNomeSolicitante != null && !informadoNomeSolicitante.equalsIgnoreCase("")){
    			
    		this.limparCliente(sessao);
    		this.limparUnidadeSolicitante(sessao);
    			
    		sessao.setAttribute("desabilitarDadosSolicitanteCliente", "OK");
    		sessao.setAttribute("desabilitarDadosSolicitanteUnidade", "OK");
    		sessao.setAttribute("habilitarAlteracaoEndereco", "SIM");
    			
    	}
    	
    	String limparNomeSolicitante = httpServletRequest.getParameter("limparNomeSolicitante");
    	if (limparNomeSolicitante != null && !limparNomeSolicitante.equalsIgnoreCase("")){
    		this.limparNomeSolicitante(sessao);
    	}
    	
    	/*
		 * Removendo endere�o
		 */
		String removerEndereco = httpServletRequest.getParameter("removerEndereco");

		if (removerEndereco != null && !removerEndereco.trim().equalsIgnoreCase("")) {

			if (sessao.getAttribute("colecaoEnderecos") != null) {

				Collection enderecos = (Collection) sessao
						.getAttribute("colecaoEnderecos");

				if (!enderecos.isEmpty()) {
					enderecos.remove(enderecos.iterator().next());
				}
			}
		}
		
		/*
		 * Adicionar Fone
		 */
		String adicionarFone = httpServletRequest.getParameter("adicionarFone");

		if (adicionarFone != null&& !adicionarFone.trim().equalsIgnoreCase("")) {
			
			retorno = actionMapping.findForward("informarFone");
		}
		
		/*
		 * Remover Fone
		 */
    	String removerFone = httpServletRequest.getParameter("removerFone");
    	
    	if (removerFone != null && !removerFone.equalsIgnoreCase("")){
    		
    		long objetoRemocao = (Long.valueOf(httpServletRequest.getParameter("removerFone"))).longValue();
    		Collection colecaoFones = (Collection) sessao.getAttribute("colecaoFonesSolicitante");
    		Iterator iteratorColecaoFones = colecaoFones.iterator();
    		IClienteFone clienteFone = null;
    		
    		while (iteratorColecaoFones.hasNext()){
    			clienteFone = (IClienteFone) iteratorColecaoFones.next();
    			
    			if (obterTimestampIdObjeto(clienteFone) == objetoRemocao){
    				colecaoFones.remove(clienteFone);
    				break;
    			}
    		}
    		
    	}
    	
		if(httpServletRequest.getParameter("retornoEndereco")!= null){
			sessao.setAttribute("habilitarAlteracaoEndereco", "SIM");
			
			
			if (sessao.getAttribute("colecaoEnderecos") != null) {

				Collection enderecos = (Collection) sessao
						.getAttribute("colecaoEnderecos");

				if (!enderecos.isEmpty()) {
					ClienteEndereco endereco = (ClienteEndereco) enderecos.iterator().next();
					endereco.setIndicadorEnderecoCorrespondencia(ConstantesSistema.SIM);
				}
			}
			
			
		}
    	
		return retorno;
	}

	private void limparNomeSolicitante(HttpSession sessao){
 		
 		sessao.removeAttribute("desabilitarDadosSolicitanteCliente");
 		sessao.removeAttribute("desabilitarDadosSolicitanteUnidade");
 		sessao.removeAttribute("habilitarAlteracaoEndereco");
 	
	}
	
	private void limparCliente(HttpSession sessao){
 		
 		if (sessao.getAttribute("enderecoPertenceCliente") != null){
 			sessao.removeAttribute("colecaoEnderecos");
 			sessao.removeAttribute("colecaoFonesSolicitante");
 		}
 		
 		sessao.removeAttribute("enderecoPertenceCliente");
 		sessao.removeAttribute("desabilitarDadosSolicitanteUnidade");
 		sessao.removeAttribute("desabilitarDadosSolicitanteNome");
 		sessao.removeAttribute("habilitarAlteracaoEndereco");
 		sessao.removeAttribute("habilitarAlteracaoEnderecoSolicitante");
 	
    }
	private void limparUnidadeSolicitante(HttpSession sessao){
 		
 		sessao.removeAttribute("desabilitarDadosSolicitanteCliente");
 		sessao.removeAttribute("desabilitarDadosSolicitanteNome");
 		sessao.removeAttribute("habilitarAlteracaoEndereco");
		sessao.removeAttribute("habilitarAlteracaoEnderecoSolicitante");
 	
    }
	
	private void pesquisarDadosCliente(HttpServletRequest httpServletRequest, 
			ReiterarRegistroAtendimentoActionForm form, HttpSession sessao, 
			Fachada fachada, String idCliente) {
		
		if (idCliente != null) {
			String nomeSolicitante = form.getNomeSolicitante();
			form.setNomeSolicitante("");
			
			fachada.verificarExistenciaClienteSolicitanteDataAtual(
			new Integer(form.getNumeroRA()), new Integer(idCliente), nomeSolicitante);
			
			form.setNomeSolicitante(nomeSolicitante);
			form.setIdClienteSolicitante(idCliente);
			Collection colecaoEnderecos = fachada.pesquisarEnderecosClienteAbreviado(new Integer(idCliente));
				
			if (colecaoEnderecos != null && !colecaoEnderecos.isEmpty()){
					
				Iterator endCorrespondencia = colecaoEnderecos.iterator();
				ClienteEndereco endereco = null;
					
				while(endCorrespondencia.hasNext()){
						
					endereco = (ClienteEndereco) endCorrespondencia.next();
						
					if (endereco.getIndicadorEnderecoCorrespondencia() != null &&
						endereco.getIndicadorEnderecoCorrespondencia().equals(ConstantesSistema.INDICADOR_ENDERECO_CORRESPONDENCIA)){
							
						form.setClienteEnderecoSelected(endereco.getId().toString());
						break;
					}
				}
					
				sessao.setAttribute("colecaoEnderecos", colecaoEnderecos);
				sessao.setAttribute("enderecoPertenceCliente", "OK");
 
				
			}
				
			Collection colecaoFones = fachada.pesquisarClienteFone(new Integer(idCliente));
				
			if (colecaoFones != null && !colecaoFones.isEmpty()){
					
				Iterator fonePrincipal = colecaoFones.iterator();
				ClienteFone fone = null;
					
				while(fonePrincipal.hasNext()){
						
					fone = (ClienteFone) fonePrincipal.next();
						
					if (fone.getIndicadorTelefonePadrao() != null &&
						fone.getIndicadorTelefonePadrao()
						.equals(ConstantesSistema.INDICADOR_TELEFONE_PRINCIPAL)){
							
						form.setClienteFoneSelected(fone.getId().toString());
						break;
					}
				}
					
				sessao.setAttribute("colecaoFonesSolicitante", colecaoFones);
			}
		
			this.limparUnidadeSolicitante(sessao);
			this.limparNomeSolicitante(sessao);
				
			sessao.setAttribute("desabilitarDadosSolicitanteUnidade", "OK");
			sessao.setAttribute("desabilitarDadosSolicitanteNome", "OK");
			sessao.setAttribute("habilitarAlteracaoEndereco", "NAO");
   			sessao.setAttribute("habilitarAlteracaoEnderecoSolicitante", "NAO");
				
		}
	}
	
	
	/**
	 * @author Vivianne Sousa
	 * @date 10/05/2011
	 */	
	private void obterDadosGeraisRa(ReiterarRegistroAtendimentoActionForm form, Fachada fachada) {
		
		RegistroAtendimento ra = fachada.pesquisarDadosRegistroAtendimentoParaReiteracao(
				new Integer(form.getNumeroRA()));

		form.setDataPrevista(Util.formatarData(ra.getDataPrevistaAtual()));
		form.setIdTipoSolicitacao(ra.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getId().toString());
		form.setDescTipoSolicitacao(ra.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getDescricao());
		form.setIdEspecificacao(ra.getSolicitacaoTipoEspecificacao().getId().toString());
		form.setDescEspecificacao(ra.getSolicitacaoTipoEspecificacao().getDescricao());
		
		//[UC0418 � Obter Unidade Atual do RA] 
		UnidadeOrganizacional unidadeAtual = fachada.obterUnidadeAtualRA(new Integer(form.getNumeroRA()));
		if(unidadeAtual != null){
			form.setIdUnidadeAtual(unidadeAtual.getId().toString());
			form.setDescUnidadeAtual(unidadeAtual.getDescricao());
		}
	}
	
	/**
	 * @author Vivianne Sousa
	 * @date 24/05/2011
	 */	
	private void validacoesRa(ReiterarRegistroAtendimentoActionForm form, Fachada fachada,Usuario usuarioLogado) {
		
		RegistroAtendimento ra = fachada.pesquisarDadosRegistroAtendimentoParaReiteracao(
				new Integer(form.getNumeroRA()));
		
		//[FS0001] - Verificar se o RA est� cancelado ou bloqueado
		
		/*
		 * Caso o usu�rio esteja tentando reiterar o registro de atendimento
		 * e o mesmo esteja com a situa��o de cancelado ou bloqueado, o
		 * sistema dever� exibir a mensagem: "Este R.A. j� est� encerrado ou
		 * bloqueado. n�o poss�vel reiter�-lo."
		 */
		if (RegistroAtendimento.SITUACAO_ENCERRADO.equals(ra.getCodigoSituacao())
				|| RegistroAtendimento.SITUACAO_BLOQUEADO.equals(ra.getCodigoSituacao())) {
			throw new ActionServletException(
			"atencao.registro_atendimento.cancelado_bloqueado");
		}
		
		// [FS0002] - Verificar se o RA est� no prazo de atendimento
		
		/*
		 * Caso o usu�rio esteja tentando reiterar o registro de atendimento
		 * e o mesmo ainda esteja no prazo de atendimento, o sistema dever�
		 * exibir a mensagem: "Prazo previsto para o atendimento ainda n�o
		 * expirou. n�o poss�vel reiter�-lo."
		 */
		Date dataAtual = new Date();
		if (Util.compararDiaMesAno(ra.getDataPrevistaAtual(), dataAtual) > 0) {
			throw new ActionServletException(
			"atencao.registro_atendimento.prazo_nao_expirado");
		}

		// [FS0004] - Verificar se a unidade organizacional pode reiterar a
		// RA
		
		UnidadeOrganizacional unidadeAtualRA = fachada.obterUnidadeAtualRA(new Integer(form.getNumeroRA()));
		
		/*
		 * Caso o indicador de autoriza��o de manuten��o do R.A. esteja com
		 * n�o, o sistema dever� exibir a mensagem: "Este R.A. foi
		 * registrado por outra Unidade Organizacional, reitera��o
		 * impedida."
		 */
		Short indicadorAutorizacaoManutencaoRA = fachada.obterIndicadorAutorizacaoManutencaoRA(
				unidadeAtualRA.getId(), usuarioLogado.getId());
		
		if (ConstantesSistema.NAO.equals(indicadorAutorizacaoManutencaoRA)) {
			throw new ActionServletException(
			"atencao.registro_atendimento.manutencao_nao_autorizada");
		}
	}

}
