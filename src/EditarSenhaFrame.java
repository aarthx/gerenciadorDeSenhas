package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarSenhaFrame extends JFrame {
    private JTextField numeroSenhaField;
    private JTextField novaSenhaField;

    public EditarSenhaFrame() {
        setTitle("Editar Senha");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        JLabel numeroSenhaLabel = new JLabel("Número da senha para atualizar:");
        numeroSenhaLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Ajustando tamanho da fonte do label

        numeroSenhaField = new JTextField(20);
        numeroSenhaField.setFont(new Font("Arial", Font.PLAIN, 14)); // Ajustando tamanho da fonte do campo de texto
        numeroSenhaField.setPreferredSize(new Dimension(200, 30)); // Ajustando a altura do campo de texto

        JLabel novaSenhaLabel = new JLabel("Digite a nova senha:");
        novaSenhaLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Ajustando tamanho da fonte do label

        novaSenhaField = new JTextField(20);
        novaSenhaField.setFont(new Font("Arial", Font.PLAIN, 14)); // Ajustando tamanho da fonte do campo de texto
        novaSenhaField.setPreferredSize(new Dimension(200, 30)); // Ajustando a altura do campo de texto

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.setFont(new Font("Arial", Font.BOLD, 14)); // Ajustando tamanho da fonte do botão

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
        add(novaSenhaLabel, gbc);

        gbc.gridy = 3;
        add(novaSenhaField, gbc);

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        add(atualizarButton, gbc);

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarSenha();
            }
        });
    }

    private void atualizarSenha() {
        String numeroDaSenhaStr = numeroSenhaField.getText();
        String novaSenha = novaSenhaField.getText();
        if (!numeroDaSenhaStr.isEmpty() && !novaSenha.isEmpty()) {
            int numeroDaSenha = Integer.parseInt(numeroDaSenhaStr);
            int qtdSenhas = GerenciaSenhas.contaLinhas();
            if (numeroDaSenha > qtdSenhas || numeroDaSenha <= 0) {
                JOptionPane.showMessageDialog(this, "Número de senha inválido!");
            } else {
                try {
                    GerenciaSenhas.atualizaSenhas(novaSenha, numeroDaSenha);
                    JOptionPane.showMessageDialog(this, "Senha atualizada com sucesso!");
                    numeroSenhaField.setText("");
                    novaSenhaField.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar senha.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, insira o número da senha e a nova senha.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EditarSenhaFrame frame = new EditarSenhaFrame();
                frame.setVisible(true);
            }
        });
    }
}
