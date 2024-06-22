package dao;

import java.util.List;

import models.Chave;

public interface ChaveDAO
{
	public List<Chave> getAllChaves();
	public Chave getChave(int id);
	public void updateChave(Chave chave);
	public void deleteChave(Chave chave);

}