package dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet; 
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;

import bd.DBConnection;
import models.Senha;


public class SenhaDaoImpl implements SenhaDAO {

	private Connection conn;

	public SenhaDaoImpl() {
		conn = DBConnection.getConnection();
	}

	@Override
	public List<Senha> getAllSenhas() {
		List<Senha> senhas = new ArrayList<Senha>();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Senhas");
			
			while(rs.next())
			{
				Senha senha = new Senha();
				senha.setId(rs.getInt("idSenha"));
				senha.setSenha(rs.getString("senha"));
				senha.setFK_idChave(rs.getInt("FK_idChave"));
				senhas.add(senha);
	
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}

		return senhas;
	}

	@Override
	public Senha getSenha(int id) {
		Senha senha = new Senha();
	
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Senha WHERE idSenha=?");
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{
				senha.setId(rs.getInt("idSenha"));
				senha.setSenha(rs.getString("senha"));
				senha.setFK_idChave(rs.getInt("FK_idChave"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return senha;
	}

	@Override
	public void updateSenha(Senha senha) {
		
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE Senha SET senha=?, WHERE idSenha=?");
			ps.setString(1,senha.getSenha());
			ps.setInt(2,senha.getId());
			ps.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteSenha(Senha senha) {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM USER WHERE idSenha=?");
			ps.setInt(1, senha.getId());
			ps.executeUpdate();

		} catch(SQLException e) {	
			e.printStackTrace();
		}
	}


}