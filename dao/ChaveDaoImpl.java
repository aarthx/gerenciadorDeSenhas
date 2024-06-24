package dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet; 
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;

import bd.DBConnection;
import models.Chave;


public class ChaveDaoImpl implements ChaveDAO {

	private Connection conn;

	public ChaveDaoImpl() {
		conn = DBConnection.getConnection();
	}

	@Override
	public List<Chave> getAllChaves() {
		List<Chave> chaves = new ArrayList<Chave>();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM chaves");
			
			while(rs.next()) {
				Chave chave = new Chave();
				chave.setId(rs.getInt("idChave"));
				chave.setChave(rs.getString("chave"));
				chaves.add(chave);
	
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}

		return chaves;
	}

	@Override
	public Chave getChave(int id) {
		Chave chave = new Chave();
	
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM chaves WHERE idChave=?");
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{
				chave.setId(rs.getInt("idChave"));
				chave.setChave(rs.getString("chave"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return chave;
	}

	@Override
	public void updateChave(Chave chave) {
		
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE chaves SET chave=? WHERE idChave=?");
			ps.setString(1,chave.getChave());
			ps.setInt(2,chave.getId());
			ps.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteChave(Chave chave) {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM chaves WHERE idChave=?");
			ps.setInt(1, chave.getId());
			ps.executeUpdate();

		} catch(SQLException e) {	
			e.printStackTrace();
		}
	}

	@Override
	public void insertChave(Chave chave) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO chaves (Chave) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, chave.getChave());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				chave.setId(id);
			}

		} catch(SQLException e) {    
			e.printStackTrace();
		}
	}


}