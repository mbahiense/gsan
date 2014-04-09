package gcom.gui.cadastro.imovel;

import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Action que remove o objeto selecionado de cliente imovel
 * 
 * @author Administrador
 * @created 20 de Maio de 2004
 */
public class RemoverInserirImovelPrincipalAction extends GcomAction {

    /**
     * < <Descri��o do m�todo>>
     * 
     * @param actionMapping
     *            Descri��o do par�metro
     * @param actionForm
     *            Descri��o do par�metro
     * @param httpServletRequest
     *            Descri��o do par�metro
     * @param httpServletResponse
     *            Descri��o do par�metro
     * @return Descri��o do retorno
     */
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping
                .findForward("inserirImovelConclusao");

        //obtendo uma instancia da sessao
        HttpSession sessao = httpServletRequest.getSession(false);

        DynaValidatorForm inserirImovelClienteActionForm = (DynaValidatorForm) actionForm;

        //Cria variaveis
        Collection imoveisPrincipaisNovos = (Collection) sessao
                .getAttribute("imoveisPrincipal");

        //atribui os valores q vem pelo request as variaveis
       // String[] clientesImoveis = httpServletRequest
       //         .getParameterValues("idRemocaoPrincipalImovel");

        //instancia cliente
       // Imovel imovel = null;

        if (imoveisPrincipaisNovos != null
                && !imoveisPrincipaisNovos.equals("")) {

            Iterator ImovelPrincipalIterator = imoveisPrincipaisNovos
                    .iterator();

            while (ImovelPrincipalIterator.hasNext()) {
                /*imovel = (Imovel)*/ ImovelPrincipalIterator.next();
                //for (int i = 0; i < clientesImoveis.length; i++) {
                    //if (imovel.getId().toString().trim().equalsIgnoreCase(
                      //      clientesImoveis[i])) {
                        ImovelPrincipalIterator.remove();
                        inserirImovelClienteActionForm.set("idImovel", "");
                        //verifica se o tipo do cliente � usu�rio
                    //}
                //}
            }

        }
        return retorno;
    }

}
