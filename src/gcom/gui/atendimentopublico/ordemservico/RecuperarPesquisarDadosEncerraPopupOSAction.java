package gcom.gui.atendimentopublico.ordemservico;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action auxiliar respons�vel por n�o perder as informa��es preenchidas no
 * *.jsp na hora de adicionar solicita��es especifica��es
 * 
 * @author S�vio Luiz
 * @created 28 de Julho de 2006
 */
public class RecuperarPesquisarDadosEncerraPopupOSAction extends GcomAction {
	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("exibirPesquisarServicoTipo");

		// N�O � PARA SER COMENTADO
		@SuppressWarnings("unused")
		EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm = 
			(EncerrarOrdemServicoActionForm) actionForm;

		// recupera o caminho de retorno passado como parametro no jsp
		// que
		// chama
		// essa funcionalidade
		if (httpServletRequest
				.getParameter("caminhoRetornoTelaPesquisaServicoTipo") != null) {
			sessao
					.setAttribute(
							"caminhoRetornoTelaPesquisaServicoTipo",
							httpServletRequest
									.getParameter("caminhoRetornoTelaPesquisaServicoTipo"));
		}
		return retorno;
	}
}
