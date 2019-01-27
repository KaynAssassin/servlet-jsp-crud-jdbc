package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.ProdutoBean;
import dao.DaoProduto;

@WebServlet("/salvarProduto")
public class ProdutoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	DaoProduto daoProduto = new DaoProduto();

	public ProdutoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			String produtoId = request.getParameter("produtoId");

			if (acao.equalsIgnoreCase("delete")) {
				daoProduto.delete(produtoId);
				RequestDispatcher view = request.getRequestDispatcher("/cadastrarProduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("update")) {
				ProdutoBean beanProduto = daoProduto.consultar(produtoId);
				RequestDispatcher view = request.getRequestDispatcher("/cadastrarProduto.jsp");
				request.setAttribute("produto", beanProduto);
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("listartodos")) {
				RequestDispatcher view = request.getRequestDispatcher("/cadastrarProduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);
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
				RequestDispatcher view = request.getRequestDispatcher("/cadastrarProduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}

		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String quantidade = request.getParameter("quantidade");
		String valor = request.getParameter("valor");

		ProdutoBean produto = new ProdutoBean();
		produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
		produto.setNome(nome);
		produto.setNome(nome);

		// valida se a quantidade esta preenchido
		if (quantidade != null && !quantidade.isEmpty()) {
			produto.setQuantidade(Double.parseDouble(quantidade));
		}
		// valida se o valor esta preenchido
		if (valor != null && !valor.isEmpty()) {
			produto.setValor(Double.parseDouble(valor));
		}

		try {
			boolean podeSalvar = true;
			String msg = null;
			// valida se o nome foi preenchido
			if (nome == null || nome.isEmpty()) {
				msg = "O NOME deve ser informado";
				podeSalvar = false;
				// valida se a quantidade foi preenchida
			} else if (quantidade == null || quantidade.isEmpty()) {
				msg = "A QUANTIDADE deve ser informada";
				podeSalvar = false;
				// valida se o valor foi preenchido
			} else if (valor == null || valor.isEmpty()) {
				msg = "O VALOR  deve ser informado";
				podeSalvar = false;
				// Exibe menssagem caso o usuario já cadastrado
			} else if (id == null || id.isEmpty() && !daoProduto.validarNome(nome)) {
				msg = "Produto já existente com o mesmo Login";
				podeSalvar = false;
			}

			if (!podeSalvar) {
				RequestDispatcher view = request.getRequestDispatcher("/cadastrarProduto.jsp");
				request.setAttribute("produto", produto);
				request.setAttribute("msg", msg);
				view.forward(request, response);
			}

			if (id == null || id.isEmpty() && podeSalvar) {
				daoProduto.salvarProduto(produto);
			} else if (id != null && !id.isEmpty() && podeSalvar) {
				daoProduto.atualizar(produto);
			}

			RequestDispatcher view = request.getRequestDispatcher("/cadastrarProduto.jsp");
			request.setAttribute("produtos", daoProduto.listar());
			view.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
