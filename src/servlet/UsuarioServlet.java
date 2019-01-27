package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.BeanCursoJsp;
import dao.DaoUsuario;

/**
 * Servlet implementation class Usuario
 */
@WebServlet("/salvarUsuario")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DaoUsuario daoUsuario = new DaoUsuario();

	public UsuarioServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");

			if (acao.equalsIgnoreCase("delete")) {
				daoUsuario.delete(user);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("update")) {
				BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("user", beanCursoJsp);
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("listartodos")) {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				try {
					view.forward(request, response);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}
		String id = request.getParameter("id");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String nome = request.getParameter("nome");
		String telefone = request.getParameter("telefone");
		String cep = request.getParameter("cep");
		String rua = request.getParameter("rua");
		String bairro = request.getParameter("bairro");
		String cidade = request.getParameter("cidade");
		String estado = request.getParameter("estado");
		String ibge = request.getParameter("ibge");


		BeanCursoJsp usuario = new BeanCursoJsp();
		usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);
		usuario.setLogin(login);
		usuario.setSenha(senha);
		usuario.setNome(nome);
		usuario.setTelefone(telefone);
		usuario.setCep(cep);
		usuario.setRua(rua);
		usuario.setBairro(bairro);
		usuario.setCidade(cidade);
		usuario.setEstado(estado);
		usuario.setIbge(ibge);

		boolean podeSalvar = true;
		String msg = null;

		try {
			
			//valida se o campo usuario esta preenchido
			if (login == null || login.isEmpty()) {
				msg = "O USUARIO deve ser informado";
				podeSalvar = false;
			//valida se o campo senha esta preenchido	
			} else if (senha == null || senha.isEmpty()) {
				msg = "A SENHA deve ser informada";
				podeSalvar = false;
			//valida se o campo nome esta preenchido
			}else if (nome == null || nome.isEmpty()) {
				msg = "O NOME deve ser informado";
				podeSalvar = false;
			//valida se o campo telefone esta preenchido
			}else if (telefone == null || telefone.isEmpty()) {
				msg = "O TELEFONE deve ser informado";
				podeSalvar = false;
			// Exibe menssagem caso o usuario já cadastrado
			}else if (id == null || id.isEmpty() && !daoUsuario.validarLogin(login)) {
				msg = "Usuario já existente com o mesmo Login";
				podeSalvar = false;
			// Exibe menssagem caso a senha já cadastrad
			}else if (id == null || id.isEmpty() && !daoUsuario.validarSenha(senha)) {
				msg = "SENHA ja cadastrada";
				podeSalvar = false;
			}
			//seta os dados preenchidos no formulario e seta a menssagem
			if (!podeSalvar) {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				msg = "Cadastrado com sucesso!";
				request.setAttribute("user", usuario);
				request.setAttribute("msg", msg);
				view.forward(request, response);
			}

			if (id == null || id.isEmpty() && podeSalvar) {
				daoUsuario.salvarUsuario(usuario);
			} else if (id != null && !id.isEmpty() && podeSalvar) {
//				msg = "Cadastrado com sucesso!";
//				request.setAttribute("msg", msg);
				daoUsuario.atualizar(usuario);
			}

			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
