package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.ProdutoBean;
import connection.SingleConnection;

public class DaoProduto {
	
private Connection connection;
	
	public DaoProduto() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvarProduto(ProdutoBean produto)  {
		String sql = "insert into produto(nome, quantidade, valor) values (?, ?, ?)";
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, produto.getNome());
			insert.setDouble(2, produto.getQuantidade());
			insert.setDouble(3, produto.getValor());
			insert.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public List<ProdutoBean> listar() throws SQLException{
		List<ProdutoBean> produtos = new ArrayList<>();
		
		String sql = "select * from produto";
		PreparedStatement stm = connection.prepareStatement(sql);
		ResultSet resultSet = stm.executeQuery();
		while (resultSet.next()) {
			ProdutoBean produto = new ProdutoBean();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQuantidade(Double.parseDouble(resultSet.getString("quantidade")));
			produto.setValor(Double.parseDouble(resultSet.getString("valor")));
			produtos.add(produto);
		}
		return produtos;
	}
	
	public void delete(String id) {
		
		try {
			String sql = "delete from produto where id = '"+ id +"'";
			PreparedStatement stm = connection.prepareStatement(sql);
			stm.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public ProdutoBean consultar(String id) throws SQLException {
		String sql = "select * from produto where id= '"+ id + "'";
		
		PreparedStatement stm = connection.prepareStatement(sql);
		ResultSet resultSet = stm.executeQuery();
		if (resultSet.next()) {
			ProdutoBean produto = new ProdutoBean();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQuantidade(Double.parseDouble(resultSet.getString("quantidade")));
			produto.setValor(Double.parseDouble(resultSet.getString("valor")));
			return produto;
		}
		
		return null;
	}
	
	public boolean validarNome(String nome) throws SQLException {
		String sql = "select count(1) as qtd from produto where nome= '"+ nome + "'";
		PreparedStatement stm = connection.prepareStatement(sql);
		ResultSet resultSet = stm.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		}
		
		return false;
	}
	
	public void atualizar(ProdutoBean produto) {
		try {
			String sql = "update produto set nome = ?, quantidade = ?, valor=? where id ="+ produto.getId();
			PreparedStatement stm = connection.prepareStatement(sql);
			stm.setString(1, produto.getNome());
			stm.setDouble(2, produto.getQuantidade());
			stm.setDouble(3, produto.getValor());
			stm.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}


}
