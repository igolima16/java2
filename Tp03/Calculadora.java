
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calculadora extends JFrame {
    private JTextField display;
    private double num1, num2, result;
    private char operator;

    public Calculadora() {
        setTitle("Calculadora");
        setSize(250, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);

        JButton clearButton = new JButton("C");
        clearButton.addActionListener(e -> {
            display.setText("");
            num1 = num2 = result = 0;
            operator = '\0';
        });
        add(clearButton, BorderLayout.SOUTH);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            try {
                if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
                    display.setText(display.getText() + command);
                } else if (command.equals("=")) {
                    num2 = Double.parseDouble(display.getText());
                    switch (operator) {
                        case '+': result = num1 + num2; break;
                        case '-': result = num1 - num2; break;
                        case '*': result = num1 * num2; break;
                        case '/': 
                            if (num2 == 0) throw new ArithmeticException("Divisão por zero");
                            result = num1 / num2; 
                            break;
                    }
                    display.setText(String.valueOf(result));
                    operator = '\0';
                } else {
                    if (!display.getText().isEmpty()) {
                        num1 = Double.parseDouble(display.getText());
                        operator = command.charAt(0);
                        display.setText("");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Erro de formatação de número.", "Erro", JOptionPane.ERROR_MESSAGE);
                display.setText("");
            } catch (ArithmeticException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                display.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado.", "Erro", JOptionPane.ERROR_MESSAGE);
            } finally {
                System.out.println("Operação processada.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculadora().setVisible(true);
        });
    }
}
