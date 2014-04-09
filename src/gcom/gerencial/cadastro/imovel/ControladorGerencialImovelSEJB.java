package gcom.gerencial.cadastro.imovel;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 * @created 7 de Junho de 2004
 */
public class ControladorGerencialImovelSEJB implements SessionBean {
	private static final long serialVersionUID = 1L;
	SessionContext sessionContext;

	//private IRepositorioGerencialImovel gRepositorioImovel;

	//private IRepositorioCategoria repositorioCategoria;


	/**
	 * Retorna o valor de controladorCliente
	 * 
	 * @return O valor de controladorCliente
	 */
	/*
	private ControladorClienteLocal getControladorCliente() {

		ControladorClienteLocalHome localHome = null;
		ControladorClienteLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try {
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorClienteLocalHome) locator
					.getLocalHome(ConstantesJNDI.CONTROLADOR_CLIENTE_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
			// objetos remotamente
			local = localHome.create();

			return local;
		} catch (CreateException e) {
			throw new SistemaException(e);
		} catch (ServiceLocatorException e) {
			throw new SistemaException(e);
		}
	}
	*/

	/**
	 * Retorna o valor de controladorAcesso
	 * 
	 * @return O valor de controladorAcesso
	 */
	/*
	private ControladorAcessoLocal getControladorAcesso() {
		ControladorAcessoLocalHome localHome = null;
		ControladorAcessoLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try {
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorAcessoLocalHome) locator
					.getLocalHome(ConstantesJNDI.CONTROLADOR_ACESSO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
			// objetos remotamente
			local = localHome.create();

			return local;
		} catch (CreateException e) {
			throw new SistemaException(e);
		} catch (ServiceLocatorException e) {
			throw new SistemaException(e);
		}
	}
	*/

	/**
	 * Retorna o valor de controladorCobranca
	 * 
	 * @return O valor de controladorCobranca
	 */
	/*
	private ControladorCobrancaLocal getControladorCobranca() {

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try {
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator
					.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
			// objetos remotamente
			local = localHome.create();

			return local;
		} catch (CreateException e) {
			throw new SistemaException(e);
		} catch (ServiceLocatorException e) {
			throw new SistemaException(e);
		}
	}
	*/

	/**
	 * Retorna o valor de controladorCobranca
	 * 
	 * @return O valor de controladorCobranca
	 */
	/*
	private ControladorFaturamentoLocal getControladorFaturamento() {

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try {
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator
					.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
			// objetos remotamente
			local = localHome.create();

			return local;
		} catch (CreateException e) {
			throw new SistemaException(e);
		} catch (ServiceLocatorException e) {
			throw new SistemaException(e);
		}
	}
	*/

	/**
	 * Retorna o valor de controladorEndereco
	 * 
	 * @return O valor de controladorEndereco
	 */
	/*
	private ControladorEnderecoLocal getControladorEndereco() {

		ControladorEnderecoLocalHome localHome = null;
		ControladorEnderecoLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try {
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorEnderecoLocalHome) locator
					.getLocalHome(ConstantesJNDI.CONTROLADOR_ENDERECO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
			// objetos remotamente
			local = localHome.create();

			return local;
		} catch (CreateException e) {
			throw new SistemaException(e);
		} catch (ServiceLocatorException e) {
			throw new SistemaException(e);
		}
	}
	*/

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @exception CreateException
	 *                Descri��o da exce��o
	 */
	public void ejbCreate() throws CreateException {

		//gRepositorioImovel = RepositorioGerencialImovelHBM.getInstancia();

		//repositorioCategoria = RepositorioCategoriaHBM.getInstancia();

	}

	/**
	 * M�todo chamado no momento que o bean � removido do contexto do container
	 */
	public void ejbRemove() {
	}

	/**
	 * M�todo chamado no momento que o bean � ativado no container
	 */
	public void ejbActivate() {
	}

	/**
	 * M�todo chamado no momento que o bean � passivado no container
	 */
	public void ejbPassivate() {
	}

	/**
	 * * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */

	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

	/*
	 * --- INICIO DOS METODOS DE NEGOCIO DO SESSION BEAN ---
	 * 
	 */

	/**
	 * Obt�m a principal categoria do im�vel
	 * 
	 * [UC0306] Obter Principal Categoria do Im�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 18/04/2006
	 * 
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	/*public GCategoria obterPrincipalCategoriaImovel(Integer idImovel) throws ControladorException {
		// Cria a vari�vel que vai armazenar a categoria principal do im�vel
		GCategoria categoriaPrincipal = null;

		// Cria a cole��o que vai armazenar as categorias com maiorquantidade de
		// economias
		Collection<GCategoria> colecaoCategoriasComMaiorQtdEconomias = new ArrayList();

		// [UC0108] Obt�m a quantidade de economias por categoria
		Collection<GCategoria> colecaoCategoriasImovel = this.obterQuantidadeEconomiasCategoria(idImovel);

		// Inicializa a quantidade de categoria
		int quantidadeCategoria = -1;

		// Caso a cole��o de categorias do im�vel n�o esteja nula
		if (colecaoCategoriasImovel != null) {
			
			// La�o para verificar qual a categoria com maior quantidade de economia
			for (GCategoria categoriaImovel : colecaoCategoriasImovel) {
				
				if (quantidadeCategoria < categoriaImovel.getQuantidadeEconomiasCategoria().intValue()) {
					
					quantidadeCategoria = categoriaImovel.getQuantidadeEconomiasCategoria();

					colecaoCategoriasComMaiorQtdEconomias = new ArrayList();
					colecaoCategoriasComMaiorQtdEconomias.add(categoriaImovel);
				} else if (quantidadeCategoria == categoriaImovel.getQuantidadeEconomiasCategoria().intValue()) {
					colecaoCategoriasComMaiorQtdEconomias.add(categoriaImovel);
				}
			}
		}

		// [FS0001]Verificar mais de uma categoria com a maior quantidade de economias
		// Caso s� exista um objeto na cole��o, recupera a categoria E atribui a
		// categoria principal
		// Caso contr�rio recupera a categoria com o menor id
		if (colecaoCategoriasComMaiorQtdEconomias.size() == 1) {
			
			categoriaPrincipal = colecaoCategoriasComMaiorQtdEconomias.iterator().next();
			
		} else if (colecaoCategoriasComMaiorQtdEconomias.size() > 1) {
			
			for (GCategoria categoriaImovel : colecaoCategoriasComMaiorQtdEconomias) {
				int idTemp = -1;
				if (idTemp < categoriaImovel.getId().intValue()) {
					idTemp = categoriaImovel.getId().intValue();
					categoriaPrincipal = categoriaImovel;
				}
			}
			
		}

		// Retorna a categoria principal
		return categoriaPrincipal;
	}*/

	
	/**
	 * <Breve descri��o sobre o caso de uso>
	 *
	 * <Identificador e nome do caso de uso>
	 *
	 * <Breve descri��o sobre o subfluxo>
	 *
	 * <Identificador e nome do subfluxo>	
	 *
	 * <Breve descri��o sobre o fluxo secund�rio>
	 *
	 * <Identificador e nome do fluxo secund�rio> 
	 *
	 * @author Administrador
	 * @date 27/04/2007
	 *
	 * @param idCategoria
	 * @return
	 * @throws ControladorException
	 *//*
	public ImovelSubcategoria obterPrincipalSubcategoria(Integer idCategoria) throws ControladorException {

		try {
			Collection<ImovelSubcategoria> colSubCategorias = repositorioImovel
					.obterSubCategoriasPorCategoria(idCategoria);

			ImovelSubcategoria subcategoriaPrincipal = null;

			// Selecionamos o de maior quantidade de economias
			if (colSubCategorias != null && colSubCategorias.size() > 0) {
				for (ImovelSubcategoria sub : colSubCategorias) {
					if (subcategoriaPrincipal == null
							|| subcategoriaPrincipal.getQuantidadeEconomias() < sub
									.getQuantidadeEconomias())
						subcategoriaPrincipal = sub;
				}
			}

			return subcategoriaPrincipal;
		} catch (ErroRepositorioException ex) {
			throw new ControladorException("erro.sistema", ex);
		}
	}
*/
	
	
	/**
	 * <Breve descri��o sobre o caso de uso>
	 *
	 * <Identificador e nome do caso de uso>
	 *
	 * @author Pedro Alexandre
	 * @date 27/04/2007
	 *
	 * @param imovel
	 * @return
	 * @throws ControladorException
	 */
	/*public Collection obterQuantidadeEconomiasCategoria(Integer imovel)	throws ControladorException {

		Collection colecaoCategoria = new ArrayList();
		Collection colecaoImovelSubCategoriaArray = null;

		try {
			colecaoImovelSubCategoriaArray = gRepositorioImovel.pesquisarObterQuantidadeEconomiasCategoria(imovel);
		} catch (ErroRepositorioException ex) {
			throw new ControladorException("erro.sistema", ex);
		}

		if (colecaoImovelSubCategoriaArray != null && !colecaoImovelSubCategoriaArray.isEmpty()) {

			Iterator colecaoImovelSubCategoriaArrayIterator = colecaoImovelSubCategoriaArray.iterator();

			while (colecaoImovelSubCategoriaArrayIterator.hasNext()) {

				// Obt�m o im�vel subcategoria
				Object[] imovelSubcategoriaArray = (Object[]) colecaoImovelSubCategoriaArrayIterator.next();

				// Cria os objetos categoria
				Categoria categoria = new Categoria();

				// Seta a categoria
				categoria.setId((Integer) imovelSubcategoriaArray[0]);

				// Seta a descri��o
				categoria.setDescricao(String.valueOf(imovelSubcategoriaArray[1]));

				// Seta o consumo estouro
				categoria.setConsumoEstouro((Integer) imovelSubcategoriaArray[2]);
				
				// Seta n�mero de vezes m�dia estouro
				categoria.setVezesMediaEstouro((BigDecimal) imovelSubcategoriaArray[3]);
				
				// Seta a quantidade de economias por categoria
				categoria.setQuantidadeEconomiasCategoria(((Short) imovelSubcategoriaArray[4]).intValue());
				
				// Seta o consumo alto
				categoria.setConsumoAlto((Integer) imovelSubcategoriaArray[6]);

				// Seta a m�dia baixo consumo
				categoria.setMediaBaixoConsumo((Integer) imovelSubcategoriaArray[7]);
				
				// Seta o n�mero de vezes m�dia consumo alto
				categoria.setVezesMediaAltoConsumo((BigDecimal) imovelSubcategoriaArray[8]);
				
				// Seta o percentual da m�dia baixo consumo
				categoria.setPorcentagemMediaBaixoConsumo((BigDecimal) imovelSubcategoriaArray[9]);

				// Seta a descricao abreviada
				if ((String) imovelSubcategoriaArray[10] != null) {
					categoria.setDescricaoAbreviada((String) imovelSubcategoriaArray[10]);
				}
				categoria.setNumeroConsumoMaximoEc((Integer) imovelSubcategoriaArray[11]);

				categoria.setIndicadorCobrancaAcrescimos((Short) imovelSubcategoriaArray[12]);

				colecaoCategoria.add(categoria);
			}

		} else {
			// Caso a cole��o n�o tenha retornado objetos
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.nao_cadastrado.imovel_subcategoria", null);
		}

		return colecaoCategoria;
	}*/

}
