package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExcluirSenhaFrame extends JFrame {
    private JTextField numeroSenhaField;

    public ExcluirSenhaFrame() {
        setTitle("Excluir Senha");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        JLabel numeroSenhaLabel = new JLabel("Número da senha para excluir:");
        numeroSenhaLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Ajustando tamanho da fonte do label

        numeroSenhaField = new JTextField(20);
        numeroSenhaField.setFont(new Font("Arial", Font.PLAIN, 14)); // Ajustando tamanho da fonte do campo de texto
        numeroSenhaField.setPreferredSize(new Dimension(200, 40)); // Ajustando a altura do campo de texto

        JButton excluirButton = new JButton("Excluir");
        excluirButton.setFont(new Font("Arial", Font.BOLD, 14)); // Ajustando tamanho da fonte do botão

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 10); // Espaçamento entre os componentes
        gbc.anchor = GridBagConstraints.WEST;

        add(numeroSenhaLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(numeroSenhaField, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(excluirButton, gbc);

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirSenha();
            }
        });
    }

    private void excluirSenha() {
        String numeroDaSenhaStr = numeroSenhaField.getText();
        if (!numeroDaSenhaStr.isEmpty()) {
            int numeroDaSenha = Integer.parseInt(numeroDaSenhaStr);
            int qtdSenhas = GerenciaSenhas.contaLinhas();

            if (numeroDaSenha > qtdSenhas || numeroDaSenha <= 0) {
                JOptionPane.showMessageDialog(this, "Número de senha inválido!");
            } else {
                try {
                    GerenciaSenhas.excluiSenha(numeroDaSenha);
                    JOptionPane.showMessageDialog(this, "Senha excluída com sucesso!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erro ao excluir senha.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, insira o número da senha.");
        }
    }

}
