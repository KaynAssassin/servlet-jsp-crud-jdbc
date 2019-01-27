<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>
</head>
<body>
	<script type="text/javascript">
	
		function validaFormulario() {
			if (document.getElementById('login').value == '') {
				alert('informe o Login');
				return false;
			}else if (document.getElementById('senha').value == '') {
				alert('informe o Senha');
				return false;
			}else if (document.getElementById('nome').value == '') {
				alert('informe o Nome');
				return false;
			}else if (document.getElementById('telefone').value == '') {
				alert('informe o Telefone');
				return false;
			}
			return true;
		}
		
		function ConsultaCep() {
			debugger;
			var cep = $("#cep").val();
			
			 $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {
                 if (!("erro" in dados)) {
                     //Atualiza os campos com os valores da consulta.
                     $("#rua").val(dados.logradouro);
                     $("#bairro").val(dados.bairro);
                     $("#cidade").val(dados.localidade);
                     $("#estado").val(dados.uf);
                     $("#ibge").val(dados.ibge);
                 } //end if.
                 else {
                     //CEP pesquisado não foi encontrado.
                     limpa_formulário_cep();
                     alert("CEP não encontrado.");
                 }
             });
		}
	
	</script>
	<a href="acessoliberado.jsp">Inicio</a>
	<a href="index.jsp">Sair</a>
	<center>
		<h1>Cadastro de Usuário</h1>
	<h3 style="color: orange;">${msg}</h3>
	</center>
	
	<form action="salvarUsuario" method="post" id="formUser" onsubmit="return validaFormulario() ? true : false">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text"  readonly="readonly" id="id" name="id" value="${user.id}" class="field-long"></td>
					</tr>
					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login" value="${user.login}"></td>
					</tr>
					<tr>
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha" value="${user.senha}"></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome" value="${user.nome}"></td>
					</tr>
					<tr>
						<td>Telefone:</td>
						<td><input type="text" id="telefone" name="telefone" value="${user.telefone}"></td>
					</tr>
					<tr>
						<td>Cep:</td>
						<td><input type="text" id="cep" name="cep" onblur="ConsultaCep()" value="${user.cep}"></td>
					</tr>
					<tr>
						<td>Rua:</td>
						<td><input type="text" id="rua" name="rua" readonly="readonly" value="${user.rua}"></td>
					</tr>
					<tr>
						<td>Bairro:</td>
						<td><input type="text" id="bairro" name="bairro" readonly="readonly" value="${user.bairro}"></td>
					</tr>
					<tr>
						<td>Cidade:</td>
						<td><input type="text" id="cidade" name="cidade" readonly="readonly" value="${user.cidade}"></td>
					</tr>
					<tr>
						<td>Estado:</td>
						<td><input type="text" id="estado" name="estado" readonly="readonly" value="${user.estado}"></td>
					</tr>
					<tr>
						<td>IBGE:</td>
						<td><input type="text" id="ibge" name="ibge" readonly="readonly" value="${user.ibge}"></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"> <input type="submit" 
						 value="Cancelar" onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'"></td>
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
				<th align="center">Login</th>
				<th align="center">Nome</th>
				<th align="center">Telefone</th>
				<th align="center">Delete</th>
				<th align="center">Editar</th>
				<th align="center">Te.</th>
			</tr>
			<c:forEach items="${usuarios}" var="user">
				<tr>
					<td style="width: 150px"><c:out value="${user.id}"></c:out></td>
					<td style="width: 150px"><c:out value="${user.login}"></c:out></td>
					<td><c:out value="${user.nome}"></c:out></td>
					<td><c:out value="${user.telefone}"></c:out></td>

					<td><a href="salvarUsuario?acao=delete&user=${user.id}"><img src="resources/img/excluir.png" alt="excluir" title="Excluir"
							width="20px" height="20px"> </a></td>
					<td><a href="salvarUsuario?acao=update&user=${user.id}"><img alt="Editar" title="Editar" src="resources/img/editar.png"
							width="20px" height="20px"></a></td>
					<td><a href="salvarTelefone?acao=listar&user=${user.id}"><img alt="Telefones" title="Telefones" src="resources/img/telefone.png"
							width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>