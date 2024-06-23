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
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Senhas WHERE idSenha=?");
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
			PreparedStatement ps = conn.prepareStatement("UPDATE Senhas SET senha=? WHERE idSenha=?");
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
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Senhas WHERE idSenha=?");
			ps.setInt(1, senha.getId());
			ps.executeUpdate();

		} catch(SQLException e) {	
			e.printStackTrace();
		}
	}

	@Override
	public void insertSenha(Senha senha) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Senhas (senha, FK_idChave) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, senha.getSenha());
			ps.setInt(2, senha.getFK_idChave());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				senha.setId(id); // Definir o ID gerado no objeto Chave
			}

		} catch(SQLException e) {    
			e.printStackTrace();
		}
	}

	@Override
    public int getQuantidadeSenhas() {
        int quantidade = 0;

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS quantidade FROM Senhas");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                quantidade = rs.getInt("quantidade");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quantidade;
    }

	@Override
	public int mapeiaSenha(int num) {
		int idSenhaADeletar = -1;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT idSenha, senha FROM Senhas");
			ResultSet rs = ps.executeQuery();
				
			ArrayList<Integer> idSenhas = new ArrayList<>();
			ArrayList<String> senhas = new ArrayList<>();
			
			while (rs.next()) {
				int idSenha = rs.getInt("idSenha");
				idSenhas.add(idSenha);
				String senha = rs.getString("senha");
				senhas.add(senha);
			}
			idSenhaADeletar = idSenhas.get(num - 1);
			
			
		} catch (SQLException e) {
            e.printStackTrace();
        }
		
		return idSenhaADeletar;
	}

}