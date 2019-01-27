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
import Bean.TelefoneBean;
import dao.DaoTelefone;
import dao.DaoUsuario;

@WebServlet("/salvarTelefone")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TelefoneServlet() {
		super();
	}

	DaoUsuario daoUsuario = new DaoUsuario();
	DaoTelefone daoTelefone = new DaoTelefone();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		try {
			String acao = request.getParameter("acao");
			
			if (acao.equalsIgnoreCase("listar")) {
				String user = request.getParameter("user");
				BeanCursoJsp usuario = daoUsuario.consultar(user);
				//Seta usuario na sessao
				request.getSession().setAttribute("selectedUser", usuario);
				
				request.setAttribute("selectedUser", usuario);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroTelefone.jsp");
				request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("delete")) {
				daoTelefone.delete(request.getParameter("user"));
				//regata o usuario da sessao
				BeanCursoJsp usuarioSessao = (BeanCursoJsp) request.getSession().getAttribute("selectedUser");
				RequestDispatcher view = request.getRequestDispatcher("/cadastroTelefone.jsp");
//				request.setAttribute("selectedUser", usuarioSessao);
				request.setAttribute("msg", "Deletado com sucesso");
				request.setAttribute("telefones", daoTelefone.listar(usuarioSessao.getId()));
				view.forward(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("selectedUser");

			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");

			TelefoneBean telefone = new TelefoneBean();
			telefone.setNumero(numero);
			telefone.setTipo(tipo);
			telefone.setUsuario(beanCursoJsp.getId());

			daoTelefone.salvar(telefone);

			request.getSession().setAttribute("selectedUser", beanCursoJsp);
			request.setAttribute("selectedUser", beanCursoJsp);

			RequestDispatcher view = request.getRequestDispatcher("/cadastroTelefone.jsp");
			request.setAttribute("telefones", daoTelefone.listar(beanCursoJsp.getId()));
			request.setAttribute("msg", "Salvo com sucesso!");
			view.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
