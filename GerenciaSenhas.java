import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.crypto.SecretKey;

public class GerenciaSenhas {

    public static void main(String[] args) {
        final String PASSWORD_FILE = "senhas.txt";
        final String KEY_FILE = "chaves.txt";
        Scanner scanner = new Scanner(System.in, "CP850");
        boolean continuar = true;
        int opcao = 0;
        
        int qtdSenhas = contaLinhas(PASSWORD_FILE);
        
        do {
            opcao = 0;
            System.out.println("Bem vindo ao gerenciador de senhas, selecione uma opção:");
            System.out.println("1 - Adicionar senha");
            System.out.println("2 - Visualizar senhas");
            System.out.println("3 - Atualizar senha");
            System.out.println("0 - Sair");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    String novaSenha = "";
                    System.out.println("Digite a senha que deseja guardar: ");
                    scanner.nextLine(); // Limpa buffer
                    novaSenha = scanner.nextLine();
                    qtdSenhas++;
                    adicionaSenha(novaSenha, KEY_FILE, PASSWORD_FILE, qtdSenhas);
                    break;
                case 2:
                    if (qtdSenhas != 0) {
                        mostraSenhas(KEY_FILE, PASSWORD_FILE, qtdSenhas);
                    } else {
                        System.out.println("Arquivo sem senhas ainda!");
                    }
                    break;
                case 3:
                    if (qtdSenhas != 0) {
                        mostraSenhas(KEY_FILE, PASSWORD_FILE, qtdSenhas);
                    } else {
                        System.out.println("Arquivo sem senhas ainda!");
                        break;
                    }

                    System.out.println("Selecione qual senha deseja atualizar pelo numero dela: ");
                    int numeroDaSenha = scanner.nextInt();
                    scanner.nextLine(); // Limpa buffer
                    System.out.println("Digite a nova senha: ");
                    String senhaNova = scanner.nextLine(); 

                    if (numeroDaSenha > qtdSenhas || numeroDaSenha <= 0) {
                        System.out.println("Número de senha inválido!");
                        break;
                    } else {
                        atualizaSenhas(senhaNova, KEY_FILE, PASSWORD_FILE, numeroDaSenha, qtdSenhas);
                    }
                    break;
                case 0:
                    continuar = false;
                    break;
                default:
                    continuar = false;
                    break;
            }

        } while (continuar);
        scanner.close();
    }

    public static int contaLinhas(String PASSWORD_FILE) {
        // Verifica qual a ultima linha do arquivo e guarda em uma variavel
        String ultimaLinha = null;
        int qtdSenhas = 0;

        try (BufferedReader arquivo = new BufferedReader(new FileReader(PASSWORD_FILE))) {
            String linha;
            while ((linha = arquivo.readLine()) != null) {
                ultimaLinha = linha; // Atualiza a última linha lida
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        // Verifica a quantidade de senhas já presentes no arquivo
        if (ultimaLinha != null) {
            qtdSenhas = Integer.parseInt(ultimaLinha.split(" ")[1]);
        }
        return qtdSenhas;
    }

    public static void adicionaSenha(String novaSenha, String KEY_FILE, String PASSWORD_FILE, int qtdSenhas) {
        // Senha é criptografada
        try {
            // Gerar e salvar a chave de criptografia se ela ainda não existir
            SecretKey key;

            key = Criptografador.generateKey();
            try (FileWriter escritorDeChaves = new FileWriter(KEY_FILE, true)) {
                escritorDeChaves.write("Chave " + qtdSenhas + " - ");
                escritorDeChaves.write(Criptografador.keyToString(key));
                escritorDeChaves.write('\n'); // Escreve uma nova linha
            }

            // Criptografar e salvar a senha
            String encryptedPassword = Criptografador.encrypt(novaSenha, key);
            try (BufferedWriter escritor = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(PASSWORD_FILE, true), "UTF-8"))) {
                escritor.write("Senha " + qtdSenhas + " - ");
                escritor.write(encryptedPassword);
                escritor.newLine(); // Escreve uma nova linha
                System.out.println("Arquivo escrito com sucesso!");
            } catch (IOException e) {
                System.err.println("Ocorreu um erro ao escrever no arquivo: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mostraSenhas(String KEY_FILE, String PASSWORD_FILE, int qtdSenhas) {
            for (int i = 1; i <= qtdSenhas; i++) {
                SecretKey key = null;
                try (BufferedReader reader = new BufferedReader(new FileReader(KEY_FILE))) {
                    String base64key;
                    for (int j = 1; j < i; j++) {
                        reader.readLine();
                    }
                    base64key = reader.readLine().split(" ")[3];
                    key = Criptografador.stringToKey(base64key);
                } catch (Exception e) {
                    System.err.println(e);
                }

                try (BufferedReader reader = new BufferedReader(new FileReader(PASSWORD_FILE))) {
                    for (int j = 1; j < i; j++) {
                        reader.readLine();
                    }
                    String senhaEncriptada = reader.readLine().split(" ")[3];
                    if (key != null) {
                        String senhaDecriptada = Criptografador.decrypt(senhaEncriptada, key);
                        System.out.println("Senha " + i + " - " + senhaDecriptada);
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao ler arquivo");
                }
            }
    }

    public static void atualizaSenhas(String novaSenha, String KEY_FILE, String PASSWORD_FILE, int numeroDaSenha, int qtdSenhas) {
         if(numeroDaSenha <= qtdSenhas || qtdSenhas != 0) {
             try {
                // Gerar e salvar a chave de criptografia se ela ainda não existir
                SecretKey key;
                key = Criptografador.generateKey();
                List<String> linhas = new ArrayList<>();
                
                // Le as linhas da antigo arquivo para substituir a linha correta, se fara o mesmo no caso das senhas
                try (BufferedReader reader = new BufferedReader(new FileReader(KEY_FILE))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        linhas.add(linha + '\n');
                    }
                }
                String novaLinha = "Chave " + numeroDaSenha + " - " + Criptografador.keyToString(key) + '\n';
                linhas.set(numeroDaSenha - 1 , novaLinha);
                try (FileWriter escritorDeChaves = new FileWriter(KEY_FILE)) {
                    for(String linha : linhas) {
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
                linhas.set(numeroDaSenha - 1 , novaLinha);
                try (FileWriter escritorDeChaves = new FileWriter(PASSWORD_FILE)) {
                    for(String linha : linhas) {
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