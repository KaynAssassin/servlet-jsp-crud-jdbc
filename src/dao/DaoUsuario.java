package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.BeanCursoJsp;
import connection.SingleConnection;

public class DaoUsuario {
	
	private Connection connection;
	
	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvarUsuario(BeanCursoJsp usuario)  {
		String sql = "insert into usuario(login, senha, nome, telefone, cep, rua, bairro, cidade, estado, ibge) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, usuario.getLogin());
			insert.setString(2, usuario.getSenha());
			insert.setString(3, usuario.getNome());
			insert.setString(4, usuario.getTelefone());
			insert.setString(5, usuario.getCep());
			insert.setString(6, usuario.getRua());
			insert.setString(7, usuario.getBairro());
			insert.setString(8, usuario.getCidade());
			insert.setString(9, usuario.getEstado());
			insert.setString(10, usuario.getIbge());
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
	
	public List<BeanCursoJsp> listar() throws SQLException{
		List<BeanCursoJsp> usuarios = new ArrayList<>();
		
		String sql = "select * from usuario";
		PreparedStatement stm = connection.prepareStatement(sql);
		ResultSet resultSet = stm.executeQuery();
		while (resultSet.next()) {
			BeanCursoJsp usuario = new BeanCursoJsp();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setTelefone(resultSet.getString("telefone"));
			usuario.setCep(resultSet.getString("cep"));
			usuario.setRua(resultSet.getString("rua"));
			usuario.setBairro(resultSet.getString("bairro"));
			usuario.setCidade(resultSet.getString("cidade"));
			usuario.setEstado(resultSet.getString("estado"));
			usuario.setIbge(resultSet.getString("ibge"));
			usuarios.add(usuario);
		}
		return usuarios;
	}
	
	public void delete(String id) {
		
		try {
			String sql = "delete from usuario where id = '"+ id +"'";
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

	public BeanCursoJsp consultar(String id) throws SQLException {
		String sql = "select * from usuario where id= '"+ id + "'";
		
		PreparedStatement stm = connection.prepareStatement(sql);
		ResultSet resultSet = stm.executeQuery();
		if (resultSet.next()) {
			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();
			beanCursoJsp.setId(resultSet.getLong("id"));
			beanCursoJsp.setLogin(resultSet.getString("login"));
			beanCursoJsp.setSenha(resultSet.getString("senha"));
			beanCursoJsp.setNome(resultSet.getString("nome"));
			beanCursoJsp.setTelefone(resultSet.getString("telefone"));
			beanCursoJsp.setCep(resultSet.getString("cep"));
			beanCursoJsp.setRua(resultSet.getString("rua"));
			beanCursoJsp.setBairro(resultSet.getString("bairro"));
			beanCursoJsp.setCidade(resultSet.getString("cidade"));
			beanCursoJsp.setEstado(resultSet.getString("estado"));
			beanCursoJsp.setIbge(resultSet.getString("ibge"));
			return beanCursoJsp;
		}
		
		return null;
	}
	
	public boolean validarLogin(String login) throws SQLException {
		String sql = "select count(1) as qtd from usuario where login= '"+ login + "'";
		PreparedStatement stm = connection.prepareStatement(sql);
		ResultSet resultSet = stm.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		}
		
		return false;
	}
	
	public boolean validarSenha(String senha) throws SQLException {
		String sql = "select count(1) as qtd from usuario where senha= '"+ senha + "'";
		PreparedStatement stm = connection.prepareStatement(sql);
		ResultSet resultSet = stm.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		}
		
		return false;
	}

	public void atualizar(BeanCursoJsp usuario) {
		try {
			String sql = "update usuario set login = ?, senha = ?, nome=?, telefone=?, cep=?, rua=?, bairro=?, cidade=?, estado=?, ibge=? where id ="+ usuario.getId();
			PreparedStatement stm = connection.prepareStatement(sql);
			stm.setString(1, usuario.getLogin());
			stm.setString(2, usuario.getSenha());
			stm.setString(3, usuario.getNome());
			stm.setString(4, usuario.getTelefone());
			stm.setString(5, usuario.getCep());
			stm.setString(6, usuario.getRua());
			stm.setString(7, usuario.getBairro());
			stm.setString(8, usuario.getCidade());
			stm.setString(9, usuario.getEstado());
			stm.setString(10, usuario.getIbge());
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
