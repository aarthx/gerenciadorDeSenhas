package src;
import javax.crypto.SecretKey;

import dao.ChaveDAO;
import dao.ChaveDaoImpl;
import dao.SenhaDAO;
import dao.SenhaDaoImpl;
import models.Chave;
import models.Senha;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciaSenhas {
    private static final String PASSWORD_FILE = "senhas.txt";
    private static final String KEY_FILE = "chaves.txt";
    
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
            } catch(Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> mostraSenhas(String KEY_FILE, String PASSWORD_FILE, int qtdSenhas) {
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

    public static void excluiSenha(int numeroDaSenha, String KEY_FILE, String PASSWORD_FILE, int qtdSenhas) {
        if (numeroDaSenha > qtdSenhas || numeroDaSenha <= 0) {
            System.out.println("Número de senha inválido!");
            return;
        }
    
        try {
            // Remover a linha correspondente da chave
            List<String> linhasChaves = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(KEY_FILE))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    linhasChaves.add(linha + '\n');
                }
            }
            linhasChaves.remove(numeroDaSenha - 1); // Remove a linha da chave
    
            try (FileWriter escritorDeChaves = new FileWriter(KEY_FILE)) {
                for (String linha : linhasChaves) {
                    escritorDeChaves.write(linha);
                }
            }
    
            // Remover a linha correspondente da senha
            List<String> linhasSenhas = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(PASSWORD_FILE))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    linhasSenhas.add(linha + '\n');
                }
            }
            linhasSenhas.remove(numeroDaSenha - 1); // Remove a linha da senha
    
            try (FileWriter escritorDeSenhas = new FileWriter(PASSWORD_FILE)) {
                for (String linha : linhasSenhas) {
                    escritorDeSenhas.write(linha);
                }
            }
    
            // Reordenar os números das senhas restantes
            for (int i = numeroDaSenha; i <= qtdSenhas - 1; i++) {
                // Atualiza as chaves
                String linhaChave = linhasChaves.get(i - 1).replaceFirst("Chave " + (i + 1), "Chave " + i);
                linhasChaves.set(i - 1, linhaChave);
    
                // Atualiza as senhas
                String linhaSenha = linhasSenhas.get(i - 1).replaceFirst("Senha " + (i + 1), "Senha " + i);
                linhasSenhas.set(i - 1, linhaSenha);
            }
            qtdSenhas--;
            // Escrever de volta no arquivo de chaves
            try (FileWriter escritorDeChaves = new FileWriter(KEY_FILE)) {
                for (String linha : linhasChaves) {
                    escritorDeChaves.write(linha);
                }
            }
    
            // Escrever de volta no arquivo de senhas
            try (FileWriter escritorDeSenhas = new FileWriter(PASSWORD_FILE)) {
                for (String linha : linhasSenhas) {
                    escritorDeSenhas.write(linha);
                }
            }
    
        } catch (IOException e) {
            System.err.println("Erro ao manipular arquivo: " + e.getMessage());
        }
    }

    public static void atualizaSenhas(String novaSenha, String KEY_FILE, String PASSWORD_FILE, int numeroDaSenha, int qtdSenhas) {
        if (numeroDaSenha <= qtdSenhas || qtdSenhas != 0) {
            try {
                SecretKey key = Criptografador.generateKey();
                List<String> linhas = new ArrayList<>();

                try (BufferedReader reader = new BufferedReader(new FileReader(KEY_FILE))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        linhas.add(linha + '\n');
                    }
                }

                String novaLinha = "Chave " + numeroDaSenha + " - " + Criptografador.keyToString(key) + '\n';
                linhas.set(numeroDaSenha - 1, novaLinha);
                try (FileWriter escritorDeChaves = new FileWriter(KEY_FILE)) {
                    for (String linha : linhas) {
                        escritorDeChaves.write(linha);
                    }
                }

                linhas.clear();

                try (BufferedReader reader = new BufferedReader(new FileReader(PASSWORD_FILE))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        linhas.add(linha + '\n');
                    }
                }

                novaLinha = "Senha " + numeroDaSenha + " - " + Criptografador.encrypt(novaSenha, key) + '\n';
                linhas.set(numeroDaSenha - 1, novaLinha);
                try (FileWriter escritorDeChaves = new FileWriter(PASSWORD_FILE)) {
                    for (String linha : linhas) {
                        escritorDeChaves.write(linha);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Ainda não há senhas, ou número inválido de senha!");
        }
    }
}