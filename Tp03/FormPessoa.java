
import java.awt.*;
import javax.swing.*;

public class FormPessoa extends JFrame {
    protected JTextField txtNumero, txtNome, txtSexo, txtIdade;
    protected JButton btnOK, btnLimpar, btnMostrar, btnSair;
    protected Pessoa umaPessoa;

    public FormPessoa() {
        setTitle("Exercício 01 - Pessoa");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Numero:"));
        txtNumero = new JTextField();
        txtNumero.setEditable(false);
        add(txtNumero);

        add(new JLabel("Nome:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("Sexo (M/F):"));
        txtSexo = new JTextField();
        add(txtSexo);

        add(new JLabel("Idade:"));
        txtIdade = new JTextField();
        add(txtIdade);

        btnOK = new JButton("OK");
        btnLimpar = new JButton("Limpar");
        btnMostrar = new JButton("Mostrar");
        btnSair = new JButton("Sair");

        add(btnOK);
        add(btnLimpar);
        add(btnMostrar);
        add(btnSair);

        btnOK.addActionListener(e -> acaoOK());
        btnLimpar.addActionListener(e -> acaoLimpar());
        btnMostrar.addActionListener(e -> acaoMostrar());
        btnSair.addActionListener(e -> System.exit(0));
    }

    protected void acaoOK() {
        try {
            if (txtNome.getText().isEmpty() || txtSexo.getText().isEmpty() || txtIdade.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
                return;
            }
            
            char sexo = txtSexo.getText().toUpperCase().charAt(0);
            if (sexo != 'M' && sexo != 'F') {
                JOptionPane.showMessageDialog(this, "Sexo deve ser M ou F.");
                return;
            }

            int idade = Integer.parseInt(txtIdade.getText());
            umaPessoa = new Pessoa(txtNome.getText(), sexo, idade);
            txtNumero.setText(String.valueOf(umaPessoa.getKp()));
            JOptionPane.showMessageDialog(this, "Dados armazenados com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número inteiro.");
        }
    }

    protected void acaoLimpar() {
        txtNome.setText("");
        txtSexo.setText("");
        txtIdade.setText("");
        txtNumero.setText("");
    }

    protected void acaoMostrar() {
        if (umaPessoa != null) {
            JOptionPane.showMessageDialog(this, 
                "KP (Contador): " + umaPessoa.getKp() + "\n" +
                "Nome: " + umaPessoa.getNome() + "\n" +
                "Sexo: " + umaPessoa.getSexo() + "\n" +
                "Idade: " + umaPessoa.getIdade());
        } else {
            JOptionPane.showMessageDialog(this, "Nenhuma pessoa cadastrada.");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPessoa().setVisible(true));
    }
}
