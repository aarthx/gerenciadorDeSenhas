package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdicionarSenhaFrame extends JFrame {
    private JTextField senhaField;

    public AdicionarSenhaFrame() {
        setTitle("Adicionar Senha");
        setSize(310, 200);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        JLabel senhaLabel = new JLabel("Digite a senha que deseja guardar:");
        senhaLabel.setFont(new Font("Arial", Font.BOLD, 16)); 

        senhaField = new JTextField(20);
        senhaField.setFont(new Font("Arial", Font.PLAIN, 14)); 
        senhaField.setPreferredSize(new Dimension(200, 40)); 

        JButton adicionarButton = new JButton("Adicionar");
        adicionarButton.setFont(new Font("Arial", Font.BOLD, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        add(senhaLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(senhaField, gbc);

        gbc.fill = GridBagConstraints.NONE;
        add(adicionarButton, gbc);

        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarSenha();
            }
        });
    }

    private void adicionarSenha() {
        String novaSenha = senhaField.getText();
        if (!novaSenha.isEmpty()) {
            try {
                GerenciaSenhas.adicionaSenha(novaSenha);
                JOptionPane.showMessageDialog(this, "Senha adicionada com sucesso!");
                senhaField.setText("");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao adicionar senha.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, insira uma senha.");
        }
    }

}
