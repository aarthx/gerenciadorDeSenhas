package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Gerenciador de Senhas");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null); // Centraliza a janela na tela
    }

    private void initUI() {
        Font fonteDosBotoes = new Font("Monospaced", Font.BOLD, 24);
        Font fonteDosTitulos = new Font("Monospaced", Font.BOLD, 36);

        String imagePath = "/assets/key.png";
        ImageIcon originalIcon = new ImageIcon(MainFrame.class.getResource(imagePath));
        int newWidth = 200;
        int originalWidth = originalIcon.getIconWidth();
        int originalHeight = originalIcon.getIconHeight();
        int newHeight = (int) Math.round((double) originalHeight / originalWidth * newWidth);
        Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);


        JLabel headerLabel = new JLabel("Gerenciador de Senhas");
        headerLabel.setFont(fonteDosTitulos);

        JButton adicionarButton = new JButton("Adicionar Senha");
        adicionarButton.setPreferredSize(new Dimension(150, 30));
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdicionarSenhaFrame adicionarSenhaFrame = new AdicionarSenhaFrame();
                adicionarSenhaFrame.setVisible(true);
            }
        });

        JButton visualizarButton = new JButton("Visualizar Senhas");
        visualizarButton.setPreferredSize(new Dimension(150, 30));
        visualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisualizarSenhasFrame visualizarSenhasFrame = new VisualizarSenhasFrame();
                visualizarSenhasFrame.setVisible(true);
            }
        });

        JButton editarButton = new JButton("Editar Senha");
        editarButton.setPreferredSize(new Dimension(150, 30));
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditarSenhaFrame editarSenhaFrame = new EditarSenhaFrame();
                editarSenhaFrame.setVisible(true);
            }
        });

        JButton excluirButton = new JButton("Excluir Senha");
        excluirButton.setPreferredSize(new Dimension(150, 30));
        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExcluirSenhaFrame excluirSenhaFrame = new ExcluirSenhaFrame();
                excluirSenhaFrame.setVisible(true);
            }
        });

        JButton sairButton = new JButton("Sair");
        sairButton.setPreferredSize(new Dimension(150, 30));
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        adicionarButton.setFont(fonteDosBotoes);
        visualizarButton.setFont(fonteDosBotoes);
        editarButton.setFont(fonteDosBotoes);
        excluirButton.setFont(fonteDosBotoes);
        sairButton.setFont(fonteDosBotoes);

        panel.add(imageLabel);
        panel.add(headerLabel, gbc);
        panel.add(adicionarButton, gbc);
        panel.add(visualizarButton, gbc);
        panel.add(editarButton, gbc);
        panel.add(excluirButton, gbc);
        panel.add(sairButton, gbc);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });
    }
}
