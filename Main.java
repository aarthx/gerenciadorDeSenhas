import java.util.List;

import dao.SenhaDAO;
import dao.SenhaDaoImpl;
import models.Senha;

public class Main
{

	public static void main(String[] args)
	{
		SenhaDAO senhaDao = new SenhaDaoImpl();

		List<Senha> senhas = senhaDao.getAllSenhas();
		for(Senha senha : senhas)
		{
			System.out.println("ID: " + senha.getId() + ", Senha: " + senha.getSenha());
		}

		Senha senha = senhaDao.getSenha(1);
		System.out.println("ID: "+ senha.getId() + ", Senha: "+senha.getSenha());

		// senha.setSenha("Jubileu123");
		// senhaDao.updateSenha(senha);

		//chaveDao.deleteSenha(senha);

	}
}