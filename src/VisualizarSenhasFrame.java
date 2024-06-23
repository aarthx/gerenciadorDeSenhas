package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VisualizarSenhasFrame extends JFrame implements SenhaListener{
    private JTextArea senhasArea;

    public VisualizarSenhasFrame() {
        setTitle("Visualizar Senhas");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        senhasArea = new JTextArea();
        senhasArea.setEditable(false);
        senhasArea.setFont(new Font("Monospaced", Font.BOLD, 16)); // Ajustando tamanho da fonte da área de texto

        JScrollPane scrollPane = new JScrollPane(senhasArea);
        scrollPane.setPreferredSize(new Dimension(380, 250)); // Ajustando tamanho do scrollPane

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre os componentes

        add(scrollPane, gbc);

        GerenciaSenhas.addSenhaListener(this);
        visualizarSenhas();
    }

    private void visualizarSenhas() {
        int qtdSenhas = GerenciaSenhas.contaLinhas();
        if (qtdSenhas != 0) {
            try {
                List<String> senhas = GerenciaSenhas.mostraSenhas();
                senhasArea.setText("");
                for (String senha : senhas) {
                    senhasArea.append(senha + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao visualizar senhas.");
            }
        } else {
            senhasArea.setText("Arquivo sem senhas ainda!");
        }
    }

    @Override
    public void senhasAtualizadas() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                visualizarSenhas();
            }
        });
    }
}