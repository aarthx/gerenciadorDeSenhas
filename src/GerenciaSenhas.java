package src;
import javax.crypto.SecretKey;

import dao.ChaveDAO;
import dao.ChaveDaoImpl;
import dao.SenhaDAO;
import dao.SenhaDaoImpl;
import models.Chave;
import models.Senha;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GerenciaSenhas {
    
    private static List<SenhaListener> listeners = new CopyOnWriteArrayList<>();
    public static void addSenhaListener(SenhaListener listener) {
        listeners.add(listener);
    }
    public static void removeSenhaListener(SenhaListener listener) {
        listeners.remove(listener);
    }
    private static void notifySenhasAtualizadas() {
        for (SenhaListener listener : listeners) {
            listener.senhasAtualizadas();
        }
    }

    public static int contaLinhas() {
        int qtdSenhas = 0;
        try {
            SenhaDAO senhaDao = new SenhaDaoImpl();
            qtdSenhas = senhaDao.getQuantidadeSenhas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qtdSenhas;
    }

    public static void adicionaSenha(String novaSenha) {
        try {
            SenhaDAO senhaDao = new SenhaDaoImpl();
            ChaveDAO chaveDao = new ChaveDaoImpl();
            SecretKey key = Criptografador.generateKey();
            int idAtual = -1;
            try {
                Chave novaChave = new Chave();
                novaChave.setChave(Criptografador.keyToString(key));
                chaveDao.insertChave(novaChave);
                idAtual = novaChave.getId();
            } catch(Exception e) {
                e.printStackTrace();
            }

            String encryptedPassword = Criptografador.encrypt(novaSenha, key);

            try {
                Senha novaSenhaBD = new Senha();
                novaSenhaBD.setSenha(encryptedPassword);
                if(idAtual != -1) {
                    novaSenhaBD.setFK_idChave(idAtual);
                }
                senhaDao.insertSenha(novaSenhaBD);
                notifySenhasAtualizadas();
            } catch(Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> mostraSenhas() {
        SenhaDAO senhaDao = new SenhaDaoImpl();
        ChaveDAO chaveDao = new ChaveDaoImpl();
        List<Senha> senhasBD = senhaDao.getAllSenhas();
        List<String> senhas = new ArrayList<>();
        int contador = 1;
        try {
            for(Senha senha : senhasBD) {
                SecretKey key = null;
                try {
                    int idDaChave = senha.getFK_idChave();
                    Chave chaveAtual = chaveDao.getChave(idDaChave);
                    String base64key = chaveAtual.getChave();
                    key = Criptografador.stringToKey(base64key);
                    String senhaDecriptada = Criptografador.decrypt(senha.getSenha(), key);
                    senhas.add("Senha " + contador + " - " + senhaDecriptada);
                    contador++;
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return senhas;
    }

    public static void excluiSenha(int numeroDaSenha) {
        if (numeroDaSenha > GerenciaSenhas.contaLinhas() || numeroDaSenha <= 0) {
            System.out.println("Número de senha inválido!");
            return;
        }
        SenhaDAO senhaDao = new SenhaDaoImpl();
        ChaveDAO chaveDao = new ChaveDaoImpl();

        try {  
            int idSenhaADeletar = senhaDao.mapeiaSenha(numeroDaSenha);
            Senha senhaADeletar = senhaDao.getSenha(idSenhaADeletar);
            Chave chaveADeletar = chaveDao.getChave(senhaADeletar.getFK_idChave());
            senhaDao.deleteSenha(senhaADeletar);
            chaveDao.deleteChave(chaveADeletar);
            notifySenhasAtualizadas();
        } catch (Exception e) {
            System.err.println("Erro ao manipular arquivo: " + e.getMessage());
        }
    }

    public static void atualizaSenhas(String novaSenha, int numeroDaSenha) {
        if (numeroDaSenha > GerenciaSenhas.contaLinhas() || numeroDaSenha <= 0) {
            System.out.println("Número de senha inválido!");
            return;
        }
        SenhaDAO senhaDao = new SenhaDaoImpl();
        ChaveDAO chaveDao = new ChaveDaoImpl();

        try {
            int idSenhaAEditar = senhaDao.mapeiaSenha(numeroDaSenha);
            Senha senhaAEditar = senhaDao.getSenha(idSenhaAEditar);
            int idChaveAEditar = senhaAEditar.getFK_idChave();
            Chave chaveAEditar = chaveDao.getChave(idChaveAEditar);
            
            SecretKey key = Criptografador.generateKey();
            String novaChave = Criptografador.keyToString(key);
            chaveAEditar.setChave(novaChave);
            chaveDao.updateChave(chaveAEditar);

            String senhaEditada = Criptografador.encrypt(novaSenha, key);
            senhaAEditar.setSenha(senhaEditada);
            senhaDao.updateSenha(senhaAEditar);
            notifySenhasAtualizadas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}