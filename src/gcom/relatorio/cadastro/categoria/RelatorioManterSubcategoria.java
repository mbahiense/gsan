package gcom.relatorio.cadastro.categoria;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de bairro manter de �gua
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class RelatorioManterSubcategoria extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterSubcategoria(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_SUBCATEGORIA_MANTER);
	}
	
	@Deprecated
	public RelatorioManterSubcategoria() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */
	public Object executar() throws TarefaException {

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroSubCategoria filtroSubCategoria = (FiltroSubCategoria) getParametro("filtroSubcategoria");
		Subcategoria subcategoriaParametros = (Subcategoria) getParametro("subcategoriaParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterSubcategoriaBean relatorioBean = null;

		filtroSubCategoria
				.adicionarCaminhoParaCarregamentoEntidade("categoria");
		filtroSubCategoria.limparCamposOrderBy();
		filtroSubCategoria.setCampoOrderBy(FiltroSubCategoria.CATEGORIA_ID,
				FiltroSubCategoria.CODIGO);

		filtroSubCategoria.setConsultaSemLimites(true);

		Collection colecaoSubcategorias = fachada.pesquisar(filtroSubCategoria,
				Subcategoria.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoSubcategorias != null && !colecaoSubcategorias.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoSubcategoriasIterator = colecaoSubcategorias
					.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoSubcategoriasIterator.hasNext()) {

				Subcategoria subcategoria = (Subcategoria) colecaoSubcategoriasIterator
						.next();

				relatorioBean = new RelatorioManterSubcategoriaBean(
						// C�digo
						"" + subcategoria.getCodigo(),

						// Descri��o
						subcategoria.getDescricao(),

						// Categoria
						subcategoria.getCategoria().getDescricao(),

						// Indicador de Uso
						subcategoria.getIndicadorUso().toString());

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if (subcategoriaParametros.getCategoria() != null) {
			parametros.put("categoria", subcategoriaParametros.getCategoria()
					.getDescricao());
		} else {
			parametros.put("categoria", "");
		}

		parametros.put("descricao", subcategoriaParametros.getDescricao());

		String indicadorUso = "";

		if (subcategoriaParametros.getIndicadorUso() != null
				&& !subcategoriaParametros.getIndicadorUso().equals("")) {
			if (subcategoriaParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (subcategoriaParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(
				ConstantesRelatorios.RELATORIO_SUBCATEGORIA_MANTER, parametros,
				ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_SUBCATEGORIA,
					idFuncionalidadeIniciada);
		} catch (ControladorException e) {
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 0;

		retorno = Fachada.getInstancia().totalRegistrosPesquisa(
				(FiltroSubCategoria) getParametro("filtroSubcategoria"),
				Subcategoria.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterSubcategoria", this);
	}
}
