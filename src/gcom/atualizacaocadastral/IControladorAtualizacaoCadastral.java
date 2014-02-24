package gcom.atualizacaocadastral;

import gcom.cadastro.imovel.IImovel;
import gcom.cadastro.imovel.ImovelAtualizacaoCadastral;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;

import java.util.Collection;
import java.util.Date;

public interface IControladorAtualizacaoCadastral {
	
	public Collection<IImovel> obterImoveisParaAtualizar() throws Exception;
	
	public void atualizarImoveisAprovados(Integer idFuncionalidade, Usuario usuarioLogado) throws Exception;
	
	public void apagarInformacoesRetornoImovelAtualizacaoCadastral(Integer idImovel) throws Exception;
	
	public Collection<Integer> pesquisarImoveisPorSituacaoPeriodo(Date dataInicial, Date dataFinal, Integer idSituacaoCadastral) throws ControladorException;
	
	public Integer recuperaValorSequenceImovelRetorno() throws Exception;
	
	public Collection<ImovelSubcategoria> pesquisarImovelSubcategoriaAtualizacaoCadastral(Integer idImovel, Integer idSubcategoria,Integer idCategoria)
		throws ControladorException;
		
	public ImovelAtualizacaoCadastral pesquisarImovelAtualizacaoCadastral(Integer idImovel)	throws ControladorException;
}
