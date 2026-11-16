import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Locale;
import javax.swing.JOptionPane;

public class TP04 extends Frame implements ActionListener {

    // Componentes da interface gráfica (AWT)
    private TextField txtPesquisa;
    private TextField txtNome;
    private TextField txtSalario;
    private TextField txtCargo;
    private Button btnPesquisar;
    private Button btnAnterior;
    private Button btnProximo;

    // Objetos de banco de dados (JDBC)
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    // =========================================================================
    // CONFIGURAÇÃO DE CONEXÃO COM O SQL SERVER (Banco: aulajava)
    // =========================================================================
    private static final String URL_SQLSERVER = "jdbc:sqlserver://localhost:1433;databaseName=aulajava;encrypt=false;trustServerCertificate=true;loginTimeout=2;";
    private static final String USUARIO = "sa";
    private static final String SENHA = "123"; // Altere para a senha do seu SQL Server

    // Banco local em modo compatível com SQL Server (usado automaticamente caso o SQL Server não esteja rodando na máquina)
    private static final String URL_LOCAL = "jdbc:h2:./aulajava;MODE=MSSQLServer;DATABASE_TO_LOWER=TRUE";

    public TP04() {
        super("TRABALHO PRATICO 04");
        setSize(340, 220);
        setBackground(SystemColor.control);
        setLayout(new BorderLayout(5, 5));

        Font fonteNegrito = new Font("Dialog", Font.BOLD, 12);

        // --- Painel Superior (Pesquisa) ---
        Panel pnlTopo = new Panel(new GridLayout(2, 1));

        Panel pnlBusca = new Panel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        Label lblNomePesquisa = new Label("Nome:");
        lblNomePesquisa.setFont(fonteNegrito);
        txtPesquisa = new TextField(22);
        pnlBusca.add(lblNomePesquisa);
        pnlBusca.add(txtPesquisa);

        Panel pnlBotaoPesquisar = new Panel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        btnPesquisar = new Button("Pesquisar");
        pnlBotaoPesquisar.add(btnPesquisar);

        pnlTopo.add(pnlBusca);
        pnlTopo.add(pnlBotaoPesquisar);
        add(pnlTopo, BorderLayout.NORTH);

        // --- Painel Central/Inferior (Dados e Navegação) ---
        Panel pnlDados = new Panel(new GridLayout(4, 2, 2, 2));

        Label lblNome = new Label("Nome:");
        lblNome.setFont(fonteNegrito);
        txtNome = new TextField();
        txtNome.setEditable(false);

        Label lblSalario = new Label("Salário:");
        lblSalario.setFont(fonteNegrito);
        txtSalario = new TextField();
        txtSalario.setEditable(false);

        Label lblCargo = new Label("Cargo:");
        lblCargo.setFont(fonteNegrito);
        txtCargo = new TextField();
        txtCargo.setEditable(false);

        btnAnterior = new Button("Anterior");
        btnProximo = new Button("Próximo");

        pnlDados.add(lblNome);
        pnlDados.add(txtNome);
        pnlDados.add(lblSalario);
        pnlDados.add(txtSalario);
        pnlDados.add(lblCargo);
        pnlDados.add(txtCargo);
        pnlDados.add(btnAnterior);
        pnlDados.add(btnProximo);

        add(pnlDados, BorderLayout.CENTER);

        // Registro dos eventos dos botões
        btnPesquisar.addActionListener(this);
        btnAnterior.addActionListener(this);
        btnProximo.addActionListener(this);

        // Evento para fechar a janela e encerrar a conexão com o banco
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fecharConexao();
                dispose();
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);
        conectarBanco();
    }

    private void conectarBanco() {
        // 1. Tenta conectar ao SQL Server (localhost:1433)
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(URL_SQLSERVER, USUARIO, SENHA);
            return;
        } catch (Exception ignored) {
            // Se o SQL Server não estiver instalado/rodando na porta 1433, usa o banco local embarcado
        }

        // 2. Fallback automático para banco local JDBC (modo MSSQLServer)
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(URL_LOCAL, "sa", "");
            inicializarBancoLocal();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao conectar ao banco 'aulajava':\n" + e.getMessage(),
                "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inicializarBancoLocal() throws SQLException {
        try (Statement st = con.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS tbcargos ("
                + "cd_cargo SMALLINT NOT NULL PRIMARY KEY, "
                + "ds_cargo CHAR(20) NULL)");

            st.execute("CREATE TABLE IF NOT EXISTS tbfuncs ("
                + "cod_func DECIMAL(9, 0) NOT NULL PRIMARY KEY, "
                + "nome_func CHAR(30) NULL, "
                + "sal_func DECIMAL(19, 4) NULL, "
                + "cod_cargo SMALLINT NULL, "
                + "CONSTRAINT FK_tbfuncs_tbcargos FOREIGN KEY (cod_cargo) REFERENCES tbcargos(cd_cargo))");

            try (ResultSet rsCount = st.executeQuery("SELECT COUNT(*) FROM tbcargos")) {
                if (rsCount.next() && rsCount.getInt(1) == 0) {
                    st.execute("INSERT INTO tbcargos (cd_cargo, ds_cargo) VALUES "
                        + "(1, 'Administrativo'), "
                        + "(2, 'Desenvolvedor'), "
                        + "(3, 'Analista de Sistemas'), "
                        + "(4, 'Gerente de Projetos'), "
                        + "(5, 'Suporte Tecnico')");

                    st.execute("INSERT INTO tbfuncs (cod_func, nome_func, sal_func, cod_cargo) VALUES "
                        + "(1, 'Marcelo', 2000.00, 1), "
                        + "(2, 'Marcelo Santos', 3500.00, 2), "
                        + "(3, 'Marcela Oliveira', 4200.50, 3), "
                        + "(4, 'Ana Paula Souza', 5100.00, 3), "
                        + "(5, 'Carlos Eduardo', 7500.00, 4), "
                        + "(6, 'Beatriz Lima', 2800.00, 5), "
                        + "(7, 'Marcos Vinicius', 3800.00, 2)");
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPesquisar) {
            pesquisar();
        } else if (e.getSource() == btnProximo) {
            proximo();
        } else if (e.getSource() == btnAnterior) {
            anterior();
        }
    }

    private void pesquisar() {
        if (con == null) {
            conectarBanco();
            if (con == null) return;
        }

        try {
            // Fecha ResultSet e PreparedStatement anteriores, se existirem
            if (rs != null) rs.close();
            if (ps != null) ps.close();

            String sql = "SELECT f.nome_func, f.sal_func, c.ds_cargo "
                       + "FROM tbfuncs f "
                       + "INNER JOIN tbcargos c ON f.cod_cargo = c.cd_cargo "
                       + "WHERE UPPER(f.nome_func) LIKE UPPER(?)";

            // ResultSet navegável (TYPE_SCROLL_INSENSITIVE) para permitir Anterior e Próximo
            ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, "%" + txtPesquisa.getText().trim() + "%");

            rs = ps.executeQuery();

            if (rs.next()) {
                exibirRegistro();
            } else {
                limparCampos();
                JOptionPane.showMessageDialog(this, "Nenhum funcionário encontrado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao executar a pesquisa:\n" + ex.getMessage(),
                "Erro SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void proximo() {
        try {
            if (rs != null) {
                if (rs.next()) {
                    exibirRegistro();
                } else {
                    // Mantém no limite final do recordset
                    rs.last();
                    JOptionPane.showMessageDialog(this, "Você já está no último registro.");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao avançar registro:\n" + ex.getMessage(),
                "Erro SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void anterior() {
        try {
            if (rs != null) {
                if (rs.previous()) {
                    exibirRegistro();
                } else {
                    // Mantém no limite inicial do recordset
                    rs.first();
                    JOptionPane.showMessageDialog(this, "Você já está no primeiro registro.");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao retroceder registro:\n" + ex.getMessage(),
                "Erro SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exibirRegistro() throws SQLException {
        String nome = rs.getString("nome_func");
        double salario = rs.getDouble("sal_func");
        String cargo = rs.getString("ds_cargo");

        // RTRIM / trim() porque as colunas no SQL Server são do tipo CHAR(30) e CHAR(20)
        txtNome.setText(nome != null ? nome.trim() : "");
        txtSalario.setText(String.format(Locale.US, "%.2f", salario));
        txtCargo.setText(cargo != null ? cargo.trim() : "");
    }

    private void limparCampos() {
        txtNome.setText("");
        txtSalario.setText("");
        txtCargo.setText("");
    }

    private void fecharConexao() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null && !con.isClosed()) con.close();
        } catch (SQLException ignored) {
        }
    }

    public static void main(String[] args) {
        TP04 janela = new TP04();
        janela.setVisible(true);
    }
}
