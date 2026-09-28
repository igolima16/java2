import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FormularioAluno extends JFrame {
    
    private List<Aluno> listaAlunos;
    
    private JTextField txtNome, txtIdade, txtEndereco;
    private JButton btnOk, btnLimpar, btnMostrar, btnSair;

    public FormularioAluno() {
       
        listaAlunos = new ArrayList<>();
        
        setTitle("TP02 - LP214");
        setSize(400, 180); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); 
        
        JPanel painelSuperior = new JPanel(new GridLayout(3, 2, 10, 10)); // Grid 3x2 com hgap/vgap 10
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margem visual
        
        painelSuperior.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelSuperior.add(txtNome);
        
        painelSuperior.add(new JLabel("Idade:"));
        txtIdade = new JTextField();
        painelSuperior.add(txtIdade);
        
        painelSuperior.add(new JLabel("Endereço:"));
        txtEndereco = new JTextField();
        painelSuperior.add(txtEndereco);
        
        JPanel painelInferior = new JPanel(new GridLayout(1, 4, 5, 0)); // 4 botões em linha
        painelInferior.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        btnOk = new JButton("Ok");
        btnLimpar = new JButton("Limpar");
        btnMostrar = new JButton("Mostrar");
        btnSair = new JButton("Sair");
        
        painelInferior.add(btnOk);
        painelInferior.add(btnLimpar);
        painelInferior.add(btnMostrar);
        painelInferior.add(btnSair);
        
        add(painelSuperior, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
        
        configurarEventos();
    }
    
    private void configurarEventos() {
        
        btnOk.addActionListener(e -> {
            try {
                Aluno aluno = new Aluno();
                aluno.setNome(txtNome.getText());
                aluno.setIdade(Integer.parseInt(txtIdade.getText()));
                aluno.setEndereco(txtEndereco.getText());
                aluno.setUuid(UUID.randomUUID()); 
                
                listaAlunos.add(aluno);
                JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, insira uma idade válida (número inteiro).");
            }
        });
        
        btnLimpar.addActionListener(e -> {
            txtNome.setText("");
            txtIdade.setText("");
            txtEndereco.setText("");
        });
        
        btnMostrar.addActionListener(e -> {
            StringBuilder mensagem = new StringBuilder("Resultado\n");
            for (Aluno aluno : listaAlunos) {
                mensagem.append("Id: ").append(aluno.getUuid())
                        .append(" Nome: ").append(aluno.getNome()).append("\n");
            }
            JOptionPane.showMessageDialog(this, mensagem.toString(), "Mensagem", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnSair.addActionListener(e -> {
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FormularioAluno form = new FormularioAluno();
            form.setLocationRelativeTo(null); 
            form.setVisible(true);
        });
    }
}