<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="gcom.util.ConstantesSistema"%>

<html:html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="FiltrarLigacaoEsgotoEsgotamentoActionForm" dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

	function validarForm(formulario){	
			if(validateFiltrarLigacaoEsgotoEsgotamentoActionForm(formulario)){    		
	    		if(validateInteger(formulario)){	     
		  			submeterFormPadrao(formulario);
		  		}
		}
	}
	

	function IntegerValidations () {
		this.aa = new Array("id", "O campo C�digo deve conter apenas n�meros.", new Function ("varName", " return this[varName];"));
	}

    function verificarChecado(valor){
		form = document.forms[0];
		if(valor == "1"){
		 	form.indicadorAtualizar.checked = true;
		 }else{
		 	form.indicadorAtualizar.checked = false;
		}
	}
	
	
	function limparForm() {
		var form = document.AtualizarLigacaoEsgotoEsgotamentoActionForm;
		form.descricao.value = "";
		form.quantidadeMesesSituacaoEspecial.value ="";
	}
	
</script>

</head>

<body leftmargin="5" topmargin="5">
<html:form action="/filtrarLigacaoEsgotoEsgotamentoAction"
	name="FiltrarLigacaoEsgotoEsgotamentoActionForm"
	type="gcom.gui.atendimentopublico.FiltrarLigacaoEsgotoEsgotamentoActionForm"
	method="post"
	onsubmit="return validateFiltrarLigacaoEsgotoEsgotamentoActionForm(this);">


	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
				<div align="center">
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
			<%@ include file="/jsp/util/mensagens.jsp"%>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				</div>
			</td>	
			<td width="615" valign="top" class="centercoltext">
				<table height="100%">
					<tr>
						<td></td>
					</tr>
				</table>
	<!--In�cio Tabela Reference a P�gina��o da Tela de Processo-->
				<table>
					<tr>
						<td></td>
					</tr>
				</table>


				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
						<td class="parabg">Filtrar Liga��o de Esgoto Esgotamento</td>
						<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
					</tr>
				</table>
				<p>&nbsp;</p>		
				<table width="100%" border="0">
				<tr>

						<td colspan="2">Para filtrar a(s) liga��o(oes) de esgoto esgotamento, informe o dado abaixo:</td>
						<td width="100" align="left" colspan="2"><html:checkbox
						property="indicadorAtualizar" value="1" /><strong>Atualizar</strong></td>
				</tr>
					
				<tr>
					<td><strong>C&oacute;digo:</strong></td>
					<td><html:text property="id" size="5" maxlength="5" /><font
						size="1">&nbsp;(somente n�meros)</font> <br>
					<font color="red"><html:errors property="id" /></font></td>
				</tr>
				
				<tr>
					<td width="70%"><strong>Descri&ccedil;&atilde;o:</strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="descricao" size="20" maxlength="20" /> </span></td>
			   		<td width="15%">&nbsp;</td>
			   </tr>
				<tr>
					<td>&nbsp;</td>
					<td><html:radio property="tipoPesquisa" tabindex="5"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
					Iniciando pelo texto <html:radio property="tipoPesquisa"
						tabindex="6"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
					Contendo o texto</td>
					<td>&nbsp;</td>
				</tr>
			
				<tr>
					<td><strong>Tipo de Situa��o Especial de Faturamento:</strong></td>
					<td><html:select property="faturamentoSituacaoTipo">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoFaturamentoSituacaoTipo"
							labelProperty="descricao" property="id" />
					</html:select> <font size="1">&nbsp; </font></td>
				</tr>
				
				<tr>
					<td><strong>Motivo da Situa��o Especial de Faturamento:</strong></td>
					<td><html:select property="faturamentoSituacaoMotivo">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoFaturamentoSituacaoMotivo"
							labelProperty="descricao" property="id" />
					</html:select> <font size="1">&nbsp; </font></td>
				</tr>
				
				<tr>
					<td><strong>Quantidade de Meses para Situa��o Esoecial de Faturamento:</strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="quantidadeMesesSituacaoEspecial" size="2" maxlength="2" /> </span></td>
			   		<td width="15%">&nbsp;</td>
			   </tr>
			   
			   <tr>
					<td><strong>Indicador de Uso:</strong></td>
					<td><html:radio property="indicadorUso" tabindex="2" value="1"><strong>Ativo</strong></html:radio>
					<html:radio property="indicadorUso" tabindex="3" value="2" ><strong>Inativo</strong></html:radio>
					<html:radio property="indicadorUso" tabindex="8" value="" /><strong>Todos</strong>
					</td>
					<td>&nbsp;</td>
				</tr>
				
				<tr>
					<td><input name="Button" type="button" class="bottonRightCol"
						value="Limpar" align="left"
						onclick="window.location.href='/gsan/exibirFiltrarLigacaoEsgotoEsgotamentoAction.do?menu=sim'"
						tabindex="8"></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td width="65" align="right"><input name="Button2" type="button"
						class="bottonRightCol" value="Filtrar" align="right"
						onClick="javascript:validarForm(document.forms[0]);" tabindex="9"/></td>
				</tr>	
	
				</table>
			</td>
		</tr>
</table>
	
			
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
