package gcom.gui.faturamento;

import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.cliente.FiltroEsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.CategoriaTipo;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroCategoriaTipo;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.faturamento.ConsumoFaixaLigacao;
import gcom.faturamento.FiltroConsumoFaixaLigacao;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC00601] Informar Dados para Emiss�o do Histograma de Esgoto
 * 
 * @author Rafael Pinto
 *
 * @date 05/11/2007
 */
public class EmissaoHistogramaEsgotoDadosInformarAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("emissaoHistogramaEsgotoDadosInformar");
		
		// Form
		EmissaoHistogramaEsgotoDadosInformarActionForm form = 
			(EmissaoHistogramaEsgotoDadosInformarActionForm) actionForm;
		
		String acao = httpServletRequest.getParameter("acao");
		
		if(acao != null && !acao.equals("")){
			
			String tipoConsumo = httpServletRequest.getParameter("tipoConsumo");
			
			//Inserir Faixa - Mostrar Popup
			if(acao.equals("I")){
				
				form.setTipoConsumo(tipoConsumo);
				
				if(tipoConsumo.equals("M")){
					form.setTituloFaixaConsumo("Medido");
					form.setSubTituloFaixaConsumo("medido");
				}else{
					form.setTituloFaixaConsumo("N�o Medido");
					form.setSubTituloFaixaConsumo("n�o medido");
				}
				form.setLimiteInferiorFaixa(null);
				form.setLimiteSuperiorFaixa(null);
				retorno = actionMapping.findForward("emissaoHistogramaEsgotoDadosInformarFaixa");

			//Remover Faixa	
			}else if(acao.equals("R")) {
				
				String idRemover = httpServletRequest.getParameter("idRemover");
				
				form.setTipoConsumo(tipoConsumo);
				this.removeConsumoFaixaLigacao(form,Integer.parseInt(idRemover));
				
			//Inserir Faixa - Insere na colecao
			}else if(acao.equals("A")) {
				
				this.validarInclusaoConsumoFaixaLigacao(form);
			}
			
			this.ordenaColecaoFaixaConsumoLigacao(form);
		} else {

			// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
			String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");
	
			//[UC0018] - Pesquisar Elo
			if (objetoConsulta != null && !objetoConsulta.trim().equals("") && 
				objetoConsulta.trim().equals("1")) {
	
				// Faz a consulta do Elo P�lo
				this.pesquisarEloPolo(form);
			}
	
			//[UC0023] - Pesquisar Localidade
			if (objetoConsulta != null && !objetoConsulta.trim().equals("") && 
					objetoConsulta.trim().equals("2")) {
	
				// Faz a consulta de Localidade
				this.pesquisarLocalidade(form);
			}
			
			//[UC0142] - Pesquisar Setor Comercial
			if (objetoConsulta != null && !objetoConsulta.trim().equals("") && 
					objetoConsulta.trim().equals("3")) {
	
				// Faz a consulta de Setor Comercial
				this.pesquisarSetorComercial(form);
			}
	
			//[UC0143] - Pesquisar Quadra
			if (objetoConsulta != null && !objetoConsulta.trim().equals("") && 
					objetoConsulta.trim().equals("4")) {
	
				// Faz a consulta de Quadra
				this.pesquisarQuadra(httpServletRequest,form);
			}
			if ( objetoConsulta != null && !objetoConsulta.trim().equals( "" ) ){
				this.pesquisarSubcategoria( httpServletRequest,form );
			}
		}
		
		//Monta os select�s do jsp
		this.pesquisarGerenciaRegional(httpServletRequest);
		this.pesquisarUnidadeNegocio(httpServletRequest,form);
		this.pesquisarCategoriaTipo(httpServletRequest);
		this.pesquisarCategoria(httpServletRequest,form);
		this.pesquisarConsumoTarifa(httpServletRequest);
		this.pesquisarPerfilImovel(httpServletRequest);
		this.pesquisarEsferaPoder(httpServletRequest);
		this.pesquisarLigacaoEsgotoSituacao(httpServletRequest);
		this.pesquisarLigacaoEsgotoPerfil(httpServletRequest);
		
		//Seta os request�s encontrados
		this.setaRequest(httpServletRequest,form);
		
		if ( form.getIndicadorTarifaCategoria() == null ||
	             form.getIndicadorTarifaCategoria().equals( "" ) ){
				form.setIndicadorTarifaCategoria( "1" );
			}
			
		httpServletRequest.setAttribute( "indicadorTarifaCategoria", form.getIndicadorTarifaCategoria() );
		
		//Consulta o grip de Consumo Faixa Ligacao Medido e N�o Medido
		if(httpServletRequest.getParameter("menu") != null){
			
			this.pesquisarConsumoFaixaLigacaoMedido(form,true);
			this.pesquisarConsumoFaixaLigacaoMedido(form,false);
		}
		
		return retorno;
	}
	/**
	 * Pesquisa Elo P�lo
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarEloPolo(EmissaoHistogramaEsgotoDadosInformarActionForm form) {


		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(
				new ParametroSimples(FiltroLocalidade.ID_ELO, 
				form.getEloPolo()));
		
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
		
		// Recupera Elo P�lo
		Collection colecaoEloPolo = 
			this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());
	
		if (colecaoEloPolo != null && !colecaoEloPolo.isEmpty()) {

			Localidade localidade = 
				(Localidade) Util.retonarObjetoDeColecao(colecaoEloPolo);
			
			localidade = localidade.getLocalidade();
			
			form.setEloPolo(localidade.getId().toString());
			form.setNomeEloPolo(localidade.getDescricao());
			
		} else {
			form.setEloPolo(null);
			form.setNomeEloPolo("Localidade inexistente");
		}
	}	
	/**
	 * Pesquisa Localidade
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarLocalidade(EmissaoHistogramaEsgotoDadosInformarActionForm form) {


		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(
				new ParametroSimples(FiltroLocalidade.ID, 
				form.getLocalidade()));
		
		// Recupera Localidade
		Collection colecaoLocalidade = 
			this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());
	
		if (colecaoLocalidade != null && !colecaoLocalidade.isEmpty()) {

			Localidade localidade = 
				(Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
			
			form.setLocalidade(localidade.getId().toString());
			form.setNomeLocalidade(localidade.getDescricao());
			
		} else {
			form.setLocalidade(null);
			form.setNomeLocalidade("Localidade inexistente");
		}
	}

	/**
	 * Pesquisa Setor comercial
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarSetorComercial(EmissaoHistogramaEsgotoDadosInformarActionForm form) {


		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(
			new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, 
				form.getSetorComercial()));
		
		filtroSetorComercial.adicionarParametro(
			new ParametroSimples(FiltroSetorComercial.LOCALIDADE, 
				form.getLocalidade()));
		
		// Recupera Setor Comercial
		Collection colecaoSetorComercial = 
			this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());
	
		if (colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()) {

			SetorComercial setorComercial = 
				(SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);
			
			form.setSetorComercial(""+setorComercial.getCodigo());
			form.setNomeSetorComercial(setorComercial.getDescricao());
			
		} else {
			form.setSetorComercial(null);
			form.setNomeSetorComercial("Setor Comercial inexistente");
		}
	}
	
	/**
	 * Pesquisa Quadra
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarQuadra(HttpServletRequest httpServletRequest,
		EmissaoHistogramaEsgotoDadosInformarActionForm form) {


		FiltroQuadra filtroQuadra = new FiltroQuadra();
		filtroQuadra.adicionarParametro(
			new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, 
			form.getQuadra()));
		
		filtroQuadra.adicionarParametro(
			new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, 
			form.getSetorComercial()));		
		
		// Recupera Quadra
		Collection colecaoQuadra = 
			this.getFachada().pesquisar(filtroQuadra, Quadra.class.getName());
	
		if (colecaoQuadra != null && !colecaoQuadra.isEmpty()) {

			Quadra quadra = 
				(Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);
			
			form.setQuadra(""+quadra.getNumeroQuadra());
			//form.setNomeQuadra(quadra.getDescricao());
			
		} else {
			form.setQuadra(null);
			httpServletRequest.setAttribute("msgQuadra", "QUADRA INEXISTENTE");

		}
	}

	/**
	 * Seta os request com os id encontrados 
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void setaRequest(HttpServletRequest httpServletRequest,
			EmissaoHistogramaEsgotoDadosInformarActionForm form){
		
		//Elo Polo
		if(form.getEloPolo() != null && 
			!form.getEloPolo().equals("") && 
			form.getNomeEloPolo() != null && 
			!form.getNomeEloPolo().equals("")){
			
			httpServletRequest.setAttribute("eloPoloEncontrado","true");			
		}

		//Localidade
		if(form.getLocalidade() != null && 
			!form.getLocalidade().equals("") && 
			form.getNomeLocalidade() != null && 
			!form.getNomeLocalidade().equals("")){
					
			httpServletRequest.setAttribute("localidadeEncontrada","true");
		}
		
		//Setor Comercial
		if(form.getSetorComercial() != null && 
			!form.getSetorComercial().equals("") && 
			form.getNomeSetorComercial() != null && 
			!form.getNomeSetorComercial().equals("")){
					
			httpServletRequest.setAttribute("setorComercialEncontrado","true");
		}
		
		//Quadra
		if(form.getNomeQuadra() != null && !form.getNomeQuadra().equals("")){
			httpServletRequest.setAttribute("quadraNaoEncontrada","true");
		}
	}
	
	/**
	 * Pesquisa Gerencial Regional 
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarGerenciaRegional(HttpServletRequest httpServletRequest){
		
		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
		
		filtroGerenciaRegional.setConsultaSemLimites(true);
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);
		filtroGerenciaRegional.adicionarParametro(
				new ParametroSimples(FiltroQuadra.INDICADORUSO, 
				ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoGerenciaRegional = 
			this.getFachada().pesquisar(filtroGerenciaRegional,GerenciaRegional.class.getName());


		if (colecaoGerenciaRegional == null || colecaoGerenciaRegional.isEmpty()) {
			throw new ActionServletException("atencao.naocadastrado", null,"Ger�ncia Regional");
		} else {
			httpServletRequest.setAttribute("colecaoGerenciaRegional",colecaoGerenciaRegional);
		}
	}
	
	/**
	 * Pesquisa Unidade Negocio
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarUnidadeNegocio(HttpServletRequest httpServletRequest,
		EmissaoHistogramaEsgotoDadosInformarActionForm form){
		
		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
		
		filtroUnidadeNegocio.setConsultaSemLimites(true);
		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME);
		
		if(form.getGerenciaRegional() != null && 
			!form.getGerenciaRegional().equals(""+ConstantesSistema.NUMERO_NAO_INFORMADO)){
			
			filtroUnidadeNegocio.adicionarParametro(
				new ParametroSimples(FiltroUnidadeNegocio.ID_GERENCIA, 
					form.getGerenciaRegional()));		
		}

		filtroUnidadeNegocio.adicionarParametro(
				new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO, 
				ConstantesSistema.INDICADOR_USO_ATIVO));		

		Collection colecaoUnidadeNegocio = 
			this.getFachada().pesquisar(filtroUnidadeNegocio,UnidadeNegocio.class.getName());


		if (colecaoUnidadeNegocio == null || colecaoUnidadeNegocio.isEmpty()) {
			throw new ActionServletException("atencao.naocadastrado", null,"Unidade de Neg�cio");
		} else {
			httpServletRequest.setAttribute("colecaoUnidadeNegocio",colecaoUnidadeNegocio);
		}
	}

	/**
	 * Pesquisa Categoria Tipo
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarCategoriaTipo(HttpServletRequest httpServletRequest){
		
		FiltroCategoriaTipo filtroCategoriaTipo = new FiltroCategoriaTipo();
		
		filtroCategoriaTipo.setConsultaSemLimites(true);
		filtroCategoriaTipo.setCampoOrderBy(FiltroCategoriaTipo.DESCRICAO);

		Collection colecaoTipoCategoria = 
			this.getFachada().pesquisar(filtroCategoriaTipo,CategoriaTipo.class.getName());


		if (colecaoTipoCategoria == null || colecaoTipoCategoria.isEmpty()) {
			throw new ActionServletException("atencao.naocadastrado", null,"Categoria Tipo");
		} else {
			httpServletRequest.setAttribute("colecaoTipoCategoria",colecaoTipoCategoria);
		}
	}
	
	/**
	 * Pesquisa Categoria
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarCategoria(HttpServletRequest httpServletRequest,
		EmissaoHistogramaEsgotoDadosInformarActionForm form){
		
		FiltroCategoria filtroCategoria = new FiltroCategoria();
		
		filtroCategoria.setConsultaSemLimites(true);
		filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);
		filtroCategoria.adicionarParametro(
			new ParametroSimples(FiltroCategoria.INDICADOR_USO, 
			ConstantesSistema.INDICADOR_USO_ATIVO));
		
		if(form.getTipoCategoria() != null && 
			!form.getTipoCategoria().equals(""+ConstantesSistema.NUMERO_NAO_INFORMADO)){
			
			filtroCategoria.adicionarParametro(
					new ParametroSimples(FiltroCategoria.TIPO_CATEGORIA, 
					form.getTipoCategoria()));		
		}
		
		
		Collection colecaoCategoria = 
			this.getFachada().pesquisar(filtroCategoria,Categoria.class.getName());


		if (colecaoCategoria == null || colecaoCategoria.isEmpty()) {
			throw new ActionServletException("atencao.naocadastrado", null,"Categoria");
		} else {
			httpServletRequest.setAttribute("colecaoCategoria",colecaoCategoria);
		}
	}	
	
	
	/**
	 * Pesquisa Consumo Tarifa
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarConsumoTarifa(HttpServletRequest httpServletRequest){
		
		FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();
		
		filtroConsumoTarifa.setConsultaSemLimites(true);
		filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
		filtroConsumoTarifa.adicionarParametro(
				new ParametroSimples(FiltroConsumoTarifa.INDICADOR_USO, 
				ConstantesSistema.INDICADOR_USO_ATIVO));
		
		Collection colecaoTarifa = 
			this.getFachada().pesquisar(filtroConsumoTarifa,ConsumoTarifa.class.getName());


		if (colecaoTarifa == null || colecaoTarifa.isEmpty()) {
			throw new ActionServletException("atencao.naocadastrado", null,"Tarifa");
		} else {
			httpServletRequest.setAttribute("colecaoTarifa",colecaoTarifa);
		}
	}
	
	/**
	 * Pesquisa Perfil Imovel
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarPerfilImovel(HttpServletRequest httpServletRequest){
		
		FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
		
		filtroImovelPerfil.setConsultaSemLimites(true);
		filtroImovelPerfil.setCampoOrderBy(FiltroImovelPerfil.DESCRICAO);
		filtroImovelPerfil.adicionarParametro(
				new ParametroSimples(FiltroImovelPerfil.INDICADOR_USO, 
				ConstantesSistema.INDICADOR_USO_ATIVO));
		
		Collection colecaoPerfilImovel = 
			this.getFachada().pesquisar(filtroImovelPerfil,ImovelPerfil.class.getName());


		if (colecaoPerfilImovel == null || colecaoPerfilImovel.isEmpty()) {
			throw new ActionServletException("atencao.naocadastrado", null,"Perfil do Im�vel");
		} else {
			httpServletRequest.setAttribute("colecaoPerfilImovel",colecaoPerfilImovel);
		}
	}	
	
	/**
	 * Pesquisa Esfera Poder
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarEsferaPoder(HttpServletRequest httpServletRequest){
		
		FiltroEsferaPoder filtroEsferaPoder = new FiltroEsferaPoder();
		
		filtroEsferaPoder.setConsultaSemLimites(true);
		filtroEsferaPoder.setCampoOrderBy(FiltroEsferaPoder.DESCRICAO);
		filtroEsferaPoder.adicionarParametro(
				new ParametroSimples(FiltroEsferaPoder.INDICADOR_USO, 
				ConstantesSistema.INDICADOR_USO_ATIVO));
		
		Collection colecaoEsferaPoder = 
			this.getFachada().pesquisar(filtroEsferaPoder,EsferaPoder.class.getName());


		if (colecaoEsferaPoder == null || colecaoEsferaPoder.isEmpty()) {
			throw new ActionServletException("atencao.naocadastrado", null,"Esfera Poder");
		} else {
			httpServletRequest.setAttribute("colecaoEsferaPoder",colecaoEsferaPoder);
		}
	}	
	
	/**
	 * Pesquisa Situacao Ligacao Esgoto
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarLigacaoEsgotoSituacao(HttpServletRequest httpServletRequest){
		
		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
		
		filtroLigacaoEsgotoSituacao.setConsultaSemLimites(true);
		filtroLigacaoEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);
		filtroLigacaoEsgotoSituacao.adicionarParametro(
				new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO, 
				ConstantesSistema.INDICADOR_USO_ATIVO));
		
		Collection colecaoSituacaoLigacaoEsgoto = 
			this.getFachada().pesquisar(filtroLigacaoEsgotoSituacao,LigacaoEsgotoSituacao.class.getName());


		if (colecaoSituacaoLigacaoEsgoto == null || colecaoSituacaoLigacaoEsgoto.isEmpty()) {
			throw new ActionServletException("atencao.naocadastrado", null,"Situa��o da Liga��o de Esgoto");
		} else {
			httpServletRequest.setAttribute("colecaoSituacaoLigacaoEsgoto",colecaoSituacaoLigacaoEsgoto);
		}
	}

	/**
	 * Pesquisa Perfil Ligacao Esgoto
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarLigacaoEsgotoPerfil(HttpServletRequest httpServletRequest){
		
		FiltroLigacaoEsgotoPerfil filtroLigacaoEsgotoPerfil = new FiltroLigacaoEsgotoPerfil();
		
		filtroLigacaoEsgotoPerfil.setConsultaSemLimites(true);
		filtroLigacaoEsgotoPerfil.setCampoOrderBy(FiltroLigacaoEsgotoPerfil.PERCENTUAL);
		filtroLigacaoEsgotoPerfil.adicionarParametro(
			new ParametroSimples(FiltroLigacaoEsgotoPerfil.INDICADOR_USO, 
				ConstantesSistema.INDICADOR_USO_ATIVO));
		
		Collection colecaoPerfilLigacaoEsgoto = 
			this.getFachada().pesquisar(filtroLigacaoEsgotoPerfil,LigacaoEsgotoPerfil.class.getName());


		if (colecaoPerfilLigacaoEsgoto == null || colecaoPerfilLigacaoEsgoto.isEmpty()) {
			throw new ActionServletException("atencao.naocadastrado", null,"Perfil da Liga��o de Esgoto");
		} else {
			httpServletRequest.setAttribute("colecaoPerfilLigacaoEsgoto",
				this.montarColecaoUnicaPerfilLigacaoEsgoto(colecaoPerfilLigacaoEsgoto));
		}
	}
	
	/**
	 * Criar uma colecao de perfil somente com um unico percentual
	 * Ele pega o primeiro que vier
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private Collection montarColecaoUnicaPerfilLigacaoEsgoto(Collection colecaoPerfilLigacaoEsgoto){
		
		LinkedHashMap map = new LinkedHashMap();
		
		if (colecaoPerfilLigacaoEsgoto != null && !colecaoPerfilLigacaoEsgoto.isEmpty()) {
		
			Iterator itera = colecaoPerfilLigacaoEsgoto.iterator();

			while (itera.hasNext()) {
				LigacaoEsgotoPerfil element = (LigacaoEsgotoPerfil) itera.next();
				map.put(element.getPercentualEsgotoConsumidaColetada(),element);
			}
		}

		return map.values();
	}
	
	/**
	 * Pesquisa Consumo Faixa Ligacao Medido
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 */
	private void pesquisarConsumoFaixaLigacaoMedido(EmissaoHistogramaEsgotoDadosInformarActionForm form,
		boolean ehMedido){
		
		FiltroConsumoFaixaLigacao filtro = new FiltroConsumoFaixaLigacao();
		
		filtro.setConsultaSemLimites(true);
		filtro.setCampoOrderBy(FiltroConsumoFaixaLigacao.NUMERO_FAIXA_INICIO);
		
		Short indicador = ConstantesSistema.INDICADOR_USO_ATIVO;
		if(!ehMedido){
			indicador = ConstantesSistema.INDICADOR_USO_DESATIVO;
		}
		
		filtro.adicionarParametro(
			new ParametroSimples(FiltroConsumoFaixaLigacao.INDICADOR_HIDROMETRO,indicador));
		
		Collection colecaoConsumoFaixaLigacao = 
			this.getFachada().pesquisar(filtro,ConsumoFaixaLigacao.class.getName());
		
		if(!ehMedido){
			form.setColecaoConsumoFaixaLigacaoNaoMedido(colecaoConsumoFaixaLigacao);
			form.setTamanhoColecaoNaoMedido(colecaoConsumoFaixaLigacao.size()+"");
		}else{
			form.setColecaoConsumoFaixaLigacaoMedido(colecaoConsumoFaixaLigacao);
			form.setTamanhoColecaoMedido(colecaoConsumoFaixaLigacao.size()+"");
		}
	}	
	
	
	/**
	 * Remove Consumo Faixa Ligacao da Cole��o 
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 *
	 * @param inserirEquipeActionForm
	 * @param equipeComponentes
	 */
	private void removeConsumoFaixaLigacao(EmissaoHistogramaEsgotoDadosInformarActionForm form,int idRemover) {
		
		Collection<ConsumoFaixaLigacao> novaColecaoConsumoFaixaLigacao = new ArrayList<ConsumoFaixaLigacao>();
		Collection<ConsumoFaixaLigacao> colecaoParaIterar = null;
		
		String tipoConsumo = form.getTipoConsumo();
		
		if(tipoConsumo.equals("M")){
			colecaoParaIterar = form.getColecaoConsumoFaixaLigacaoMedido();
		}else{
			colecaoParaIterar = form.getColecaoConsumoFaixaLigacaoNaoMedido();
		}
		
		int index = 0;
		for (Iterator iter = colecaoParaIterar.iterator(); iter.hasNext();) {
			
			index++;
			
			ConsumoFaixaLigacao element = (ConsumoFaixaLigacao) iter.next();
			
			if (index != idRemover) {
				novaColecaoConsumoFaixaLigacao.add(element);
			}
		}
		
		if(tipoConsumo.equals("M")){
			form.setColecaoConsumoFaixaLigacaoMedido(novaColecaoConsumoFaixaLigacao);
			form.setTamanhoColecaoMedido(novaColecaoConsumoFaixaLigacao.size()+"");
		}else{
			form.setColecaoConsumoFaixaLigacaoNaoMedido(novaColecaoConsumoFaixaLigacao);
			form.setTamanhoColecaoNaoMedido(novaColecaoConsumoFaixaLigacao.size()+"");
		}
		
	}
	
	/**
	 * Valida a faixa se ele j� existe outra faixa no intervalo 
	 *
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 *
	 * @param EmissaoHistogramaEsgotoDadosInformarActionForm
	 */	
	
	private void validarInclusaoConsumoFaixaLigacao(EmissaoHistogramaEsgotoDadosInformarActionForm form){
		boolean ehValido = true;
		
		Collection<ConsumoFaixaLigacao> colecaoFaixas = null;
		
		if(form.getTipoConsumo().equals("M")){
			colecaoFaixas = form.getColecaoConsumoFaixaLigacaoMedido();
		}else{
			colecaoFaixas = form.getColecaoConsumoFaixaLigacaoNaoMedido();
		}

		int numeroFaixaInicio = Integer.parseInt(form.getLimiteInferiorFaixa());
		int numeroFaixaFim = Integer.parseInt(form.getLimiteSuperiorFaixa());
		
		Iterator itera = colecaoFaixas.iterator();
		while (itera.hasNext()) {
			ConsumoFaixaLigacao consumoExistente = (ConsumoFaixaLigacao) itera.next();

			int numeroFaixaInicioExistente = consumoExistente.getNumeroFaixaInicio();
			int numeroFaixaFimExistente = consumoExistente.getNumeroFaixaFim();

			if((numeroFaixaInicio >= numeroFaixaInicioExistente && numeroFaixaInicio <= numeroFaixaFimExistente) ||
			   (numeroFaixaFim >= numeroFaixaInicioExistente && numeroFaixaInicio <= numeroFaixaFimExistente) ){
				
				ehValido = false;
			}
		}
		
		if(ehValido){
			ConsumoFaixaLigacao consumoFaixaLigacao = new ConsumoFaixaLigacao();
			
			consumoFaixaLigacao.setNumeroFaixaInicio(numeroFaixaInicio);
			consumoFaixaLigacao.setNumeroFaixaFim(numeroFaixaFim);
			
			colecaoFaixas.add(consumoFaixaLigacao);
			
			if(form.getTipoConsumo().equals("M")){
				form.setTamanhoColecaoMedido(colecaoFaixas.size()+"");
			}else{
				form.setTamanhoColecaoNaoMedido(colecaoFaixas.size()+"");
			}
			

		}else{
			throw new ActionServletException("atencao.consumo_faixa_ja_informada");
		}
	}
	
	
	/**
	 * Ordena a colecao de consumo faixa ligacao
	 * 
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 *
	 * @param EmissaoHistogramaEsgotoDadosInformarActionForm
	 */
	private void ordenaColecaoFaixaConsumoLigacao(EmissaoHistogramaEsgotoDadosInformarActionForm form) {

		Collection colecaoMedido = form.getColecaoConsumoFaixaLigacaoMedido();
		Collection colecaoNaoMedido = form.getColecaoConsumoFaixaLigacaoNaoMedido(); 

		Collections.sort((List) colecaoMedido, 
			new Comparator() {
			
				public int compare(Object a, Object b) {
				
					ConsumoFaixaLigacao consumo1 = (ConsumoFaixaLigacao) a;
					ConsumoFaixaLigacao consumo2 = (ConsumoFaixaLigacao) b;
					
					Integer faixaInicio1 = consumo1.getNumeroFaixaInicio();
					Integer faixaInicio2 = consumo2.getNumeroFaixaInicio();
					
					return faixaInicio1.compareTo(faixaInicio2);
				}
			}
		);
		
		Collections.sort((List) colecaoNaoMedido, 
			new Comparator() {
				
				public int compare(Object a, Object b) {
					
					ConsumoFaixaLigacao consumo1 = (ConsumoFaixaLigacao) a;
					ConsumoFaixaLigacao consumo2 = (ConsumoFaixaLigacao) b;
					
					Integer faixaInicio1 = consumo1.getNumeroFaixaInicio();
					Integer faixaInicio2 = consumo2.getNumeroFaixaInicio();
					
					return faixaInicio1.compareTo(faixaInicio2);
				}
			}
		);
		
	}

	/**
	 * Pesquisa as subcategorias da categoria informada
	 *
	 * @author Bruno Barros
	 * @date 11/11/2010
	 */
	private void pesquisarSubcategoria(HttpServletRequest httpServletRequest,
			EmissaoHistogramaEsgotoDadosInformarActionForm form) {
		
		FiltroSubCategoria filtro = new FiltroSubCategoria();
		filtro.adicionarParametro( new ParametroSimples( FiltroSubCategoria.CATEGORIA_ID, form.getCategoria()[0] ) );
		filtro.adicionarParametro( new ParametroSimples( FiltroSubCategoria.INDICADOR_TARIFA_CONSUMO, ConstantesSistema.SIM ) );
		filtro.setCampoOrderBy( FiltroSubCategoria.DESCRICAO );
		
		Collection colecaoSubcategoria = this.getFachada().pesquisar( filtro, Subcategoria.class.getName() );
		
		httpServletRequest.setAttribute("colecaoSubcategoria", colecaoSubcategoria);
	}

}
