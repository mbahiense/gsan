package gcom.gui.faturamento.consumotarifa;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirManterInserirCategoriaConsumoTarifaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("inserirCategoriaConsumoTarifa");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguranša
		HttpSession sessao = httpServletRequest.getSession(false);

		// ----------------FILTRO CATEGORIAS DE ESTABELECIMENTO - PROPULAR
		// DROPDOWN ------
		
		if (httpServletRequest.getAttribute("parametroVigencia") != null){
			String Vigencia = (String) httpServletRequest.getAttribute("parametroVigencia");
			sessao.setAttribute("Vigencia", Vigencia);
		}
		
		FiltroCategoria filtroCategoria = new FiltroCategoria();

		
		if ((httpServletRequest.getParameter("limpa") != null)
				&& (httpServletRequest.getParameter("limpa").equals("1"))) {
			sessao.removeAttribute("InserirCategoriaConsumoTarifaActionForm");
			sessao.removeAttribute("valorMinimo");
			sessao.removeAttribute("consumoMinimo");
			sessao.removeAttribute("colecaoFaixa");
		}
		
		InserirCategoriaConsumoTarifaActionForm inserirCategoriaConsumoTarifaActionForm = new InserirCategoriaConsumoTarifaActionForm();
		
		if ((sessao.getAttribute("valorMinimo") != null)
				&& (! sessao.getAttribute("valorMinimo").equals(""))) {
			inserirCategoriaConsumoTarifaActionForm.setValorTarifaMinima((String) sessao.getAttribute("valorMinimo"));
		}
		
		if ((sessao.getAttribute("consumoMinimo") != null)
				&& (! sessao.getAttribute("consumoMinimo").equals(""))) {
			inserirCategoriaConsumoTarifaActionForm.setConsumoMinimo((String) sessao.getAttribute("consumoMinimo"));
		}
		
		filtroCategoria.adicionarParametro(new ParametroSimples(
				FiltroCategoria.INDICADOR_USO,
				ConstantesSistema.INDICADOR_USO_ATIVO));
		
		sessao.setAttribute("InserirCategoriaConsumoTarifaActionForm", inserirCategoriaConsumoTarifaActionForm);
		Collection colecaoCategoria = fachada.pesquisar(filtroCategoria,
				Categoria.class.getName());

		sessao.setAttribute("colecaoCategoria", colecaoCategoria);

		return retorno;
	}

}
