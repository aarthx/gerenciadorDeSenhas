package models;

public class Senha
{

	private int id;
	private String senha;
	private int FK_idChave;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getFK_idChave() {
		return FK_idChave;
	}
	public void setFK_idChave(int fK_idChave) {
		FK_idChave = fK_idChave;
	}

}