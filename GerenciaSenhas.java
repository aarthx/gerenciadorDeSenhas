import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class GerenciaSenhas {

    public static void main(String[] args) {
        boolean continuar = true;
        int opcao = 0;
        int qtdSenhas = 0;
        Scanner scanner = new Scanner(System.in, "UTF-8");
        String nomeArquivoDeSenhas = "senhas.txt";
        String ultimaLinha = null;

        // Verifica qual a ultima linha do arquivo e guarda em uma variavel
        try (BufferedReader arquivo = new BufferedReader(new FileReader(nomeArquivoDeSenhas))) {
            String linha;
            while ((linha = arquivo.readLine()) != null) {
                ultimaLinha = linha; // Atualiza a última linha lida
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        //Verifica a quantidade de senhas já presentes no arquivo
        if(ultimaLinha != null) {
            qtdSenhas = Integer.parseInt(ultimaLinha.split(" ")[0]); 
        }

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
                    scanner.nextLine();
                    novaSenha = scanner.nextLine();
                    try (BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nomeArquivoDeSenhas, true), "UTF-8"))) {
                        qtdSenhas++;
                        escritor.write(qtdSenhas + " - ");
                        escritor.write(novaSenha);
                        escritor.newLine(); // Escreve uma nova linha
                        System.out.println("Arquivo escrito com sucesso!");
                    } catch (IOException e) {
                        System.err.println("Ocorreu um erro ao escrever no arquivo: " + e.getMessage());
                    }
                    break;
                case 2:
                    try (BufferedReader arquivo = new BufferedReader(new FileReader(nomeArquivoDeSenhas))) {
                        String linha;
                        System.out.println("Lista de linhas:");
                        while ((linha = arquivo.readLine()) != null) {
                            System.out.println(linha);
                        }
                    } catch (IOException e) {
                        System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                    }
                    break;
                default:
                    break;
            }

        } while (continuar);
        scanner.close();
    }
}