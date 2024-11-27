import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import services.Biblioteca;
import exceptions.LivroNaoEncontradoException;
import models.Livro;
import models.LivroFisico;
import models.LivroDigital;

public class App extends JFrame {
    private Biblioteca biblioteca;

    public App() {
        biblioteca = new Biblioteca("livros.txt");
        initUI();
    }

    private void initUI() {
        setTitle("Biblioteca");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(5, 1, 10, 10));

        JButton addBookButton = new JButton("Adicionar Livro");
        JButton listBooksButton = new JButton("Listar Livros");
        JButton borrowBookButton = new JButton("Emprestar Livro");
        JButton searchBookButton = new JButton("Buscar Livro");
        JButton exitButton = new JButton("Sair");

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = JOptionPane.showInputDialog(App.this, "Digite o título do livro:");
                String autor = JOptionPane.showInputDialog(App.this, "Digite o autor do livro:");
                String[] tipos = {"Físico", "Digital"};
                String tipo = (String) JOptionPane.showInputDialog(App.this, "Selecione o tipo do livro:", "Tipo do Livro", JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

                if (titulo != null && autor != null && tipo != null) {
                    Livro livro;
                    if (tipo.equals("Físico")) {
                        livro = new LivroFisico(titulo, autor);
                    } else {
                        livro = new LivroDigital(titulo, autor);
                    }
                    biblioteca.adicionarLivro(livro);
                    JOptionPane.showMessageDialog(App.this, "Livro adicionado com sucesso!");
                }
            }
        });

        listBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder livros = new StringBuilder();
                for (Livro livro : biblioteca.listarLivros()) {
                    livros.append(livro.getTitulo()).append(" - ")
                            .append(livro.getAutor()).append(" - ")
                            .append(livro.getTipo()).append("\n");
                }
                JOptionPane.showMessageDialog(App.this, livros.length() == 0 ? "Nenhum livro encontrado." : livros.toString());
            }
        });

        borrowBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = JOptionPane.showInputDialog(App.this, "Digite o título do livro para emprestar:");

                if (titulo != null) {
                    try {
                        biblioteca.emprestarLivro(titulo);
                        JOptionPane.showMessageDialog(App.this, "Livro emprestado com sucesso!");
                    } catch (LivroNaoEncontradoException ex) {
                        JOptionPane.showMessageDialog(App.this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        searchBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String criterio = JOptionPane.showInputDialog(App.this, "Digite o critério de busca:");

                if (criterio != null) {
                    biblioteca.buscar(criterio);
                }
            }
        });

        exitButton.addActionListener(e -> System.exit(0));

        add(addBookButton);
        add(listBooksButton);
        add(borrowBookButton);
        add(searchBookButton);
        add(exitButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
