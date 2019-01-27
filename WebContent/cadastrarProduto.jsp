<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>

		<script type="text/javascript">
	
		function validaFormulario() {
			if (document.getElementById('nome').value == '') {
				alert('informe o Nome');
				return false;
			}else if (document.getElementById('quantidade').value == '') {
				alert('informe o Quantidade');
				return false;
			}else if (document.getElementById('valor').value == '') {
				alert('informe o Valor');
				return false;
			}
			return true;
		}
	
	</script>
	<a href="acessoliberado.jsp">Inicio</a>
	<a href="index.jsp">Sair</a>
	<center>
		<h1>Cadastro de Produto</h1>
	<h3 style="color: orange;">${msg}</h3>
	</center>
	<form action="salvarProduto" method="post" id="formUser" onsubmit="return validaFormulario() ? true : false">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" value="${produto.id}" class="field-long"></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="login" name="nome" value="${produto.nome}"></td>
					</tr>

					<tr>
						<td>Quantidade:</td>
						<td><input type="text" id="senha" name="quantidade" value="${produto.quantidade}"></td>
					</tr>
					<tr>
						<td>Valor:</td>
						<td><input type="text" id="nome" name="valor" value="${produto.valor}"></td>
					</tr>
					
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"> <input type="submit" 
						 value="Cancelar" onclick="document.getElementById('formUser').action = 'salvarProduto?acao=reset'"></td>
					</tr>
				</table>

			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Produtos cadastrados</caption>
			<tr>
				<th align="center">Id</th>
				<th align="center">Nome</th>
				<th align="center">Quantidade</th>
				<th align="center">Valor</th>
				<th align="center">Delete</th>
				<th align="center">Editar</th>
			</tr>
			<c:forEach items="${produtos}" var="produto">
				<tr>
					<td style="width: 150px"><c:out value="${produto.id}"></c:out></td>
					<td style="width: 150px"><c:out value="${produto.nome}"></c:out></td>
					<td><c:out value="${produto.quantidade}"></c:out></td>
					<td><c:out value="${produto.valor}"></c:out></td>

					<td><a href="salvarProduto?acao=delete&produtoId=${produto.id}"><img src="resources/img/excluir.png" alt="excluir" title="Excluir"
							width="20px" height="20px"> </a></td>
					<td><a href="salvarProduto?acao=update&produtoId=${produto.id}"><img alt="Editar" title="Editar" src="resources/img/editar.png"
							width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>