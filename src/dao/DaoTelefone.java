package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.TelefoneBean;
import connection.SingleConnection;

public class DaoTelefone {

	private Connection connection;

	public DaoTelefone() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(TelefoneBean telefone) {
		String sql = "insert into telefone (numero, tipo, usuario) values (?, ?, ?)";
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());
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

	public List<TelefoneBean> listar(Long user) throws SQLException{
		List<TelefoneBean> telefones = new ArrayList<>();
		
		String sql = "select * from telefone where usuario = "+ user;
		PreparedStatement stm = connection.prepareStatement(sql);
		ResultSet resultSet = stm.executeQuery();
		while (resultSet.next()) {
			TelefoneBean telefone = new TelefoneBean();
			telefone.setId(resultSet.getLong("id"));
			telefone.setNumero(resultSet.getString("numero"));
			telefone.setTipo(resultSet.getString("tipo"));
			telefone.setUsuario(resultSet.getLong("usuario"));
			telefones.add(telefone);
		}
		return telefones;
	}

	public void delete(String id) {

		try {
			String sql = "delete from telefone where id = '" + id + "'";
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

}
