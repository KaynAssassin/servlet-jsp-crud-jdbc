<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Telefone</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>
	<script type="text/javascript">
		function validaFormulario() {
			if (document.getElementById('numero').value == '') {
				alert('informe o Numero');
				return false;
			}else if (document.getElementById('tipo').value == '') {
				alert('informe o Tipo');
				return false;
			}
			return true;
		}
	</script>
	<a href="acessoliberado.jsp">Inicio</a>
	<a href="index.jsp">Sair</a>
	<center>
		<h1>Cadastro de Telefone</h1>
		<h3 style="color: orange;">${msg}</h3>
	</center>

	<form action="salvarTelefone" method="post" id="formUser"
		onsubmit="return validaFormulario() ? true : false">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código Usuario:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${selectedUser.id}" class="field-long"></td>
						<td><input type="text" readonly="readonly" id="nome"
							name="nome" value="${selectedUser.nome}" class="field-long"></td>
					</tr>
					<tr>
						<td>Numero:</td>
						<td><input type="text"  id="numero"
							name="numero" class="field-long"></td>
						<td><select id="tipo" name="tipo">
								<option>Casa</option>
								<option>Trabalho</option>
								<option>Celular</option>
						</select></td>
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Usuários cadastrados</caption>
			<tr>
				<th align="center">Id</th>
				<th align="center">Numero</th>
				<th align="center">Tipo</th>
				<th align="center">Excluir</th>
			</tr>
			<c:forEach items="${telefones}" var="fone">
				<tr>
					<td style="width: 150px"><c:out value="${fone.id}"></c:out></td>
					<td style="width: 150px"><c:out value="${fone.numero}"></c:out></td>
					<td><c:out value="${fone.tipo}"></c:out></td>


					<td><a href="salvarTelefone?acao=delete&user=${fone.id}"><img
							src="resources/img/excluir.png" alt="excluir" title="Excluir"
							width="20px" height="20px"> </a></td>

				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>