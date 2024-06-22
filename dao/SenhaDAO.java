package dao;

import java.util.List;

import models.Senha;

public interface SenhaDAO
{
	public List<Senha> getAllSenhas();
	public int getQuantidadeSenhas();
	public Senha getSenha(int id);
	public void updateSenha(Senha senha);
	public void deleteSenha(Senha senha);
	public void insertSenha(Senha senha);

}