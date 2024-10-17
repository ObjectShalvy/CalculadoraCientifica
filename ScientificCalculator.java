package independiente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ScientificCalculator extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2262017446464441368L;
	private JTextField display;
    private double result = 0;
    private String lastCommand = "=";
    private boolean start = true;
    private List<String> memory = new ArrayList<>();
    private int memoryIndex = -1;

    public ScientificCalculator() {
        setTitle("ScientificCalculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 450);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setBackground(new Color(255, 255, 255));
        display.setForeground(new Color(50, 50, 50));
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 4, 5, 5));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
            "MC", "MR", "M+", "M-",
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "sin", "cos", "tan", "√",
            "C", "⌫", "↑", "↓"
        };

        for (String button : buttons) {
            addButton(panel, button);
        }

        add(panel, BorderLayout.CENTER);
    }

    private void addButton(Container c, String s) {
        JButton button = new JButton(s);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(new Color(50, 50, 50));
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        c.add(button);
        button.addActionListener(new ButtonListener());
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            if (start && "0123456789.".indexOf(command) != -1) {
                display.setText("");
                start = false;
            }
            switch (command) {
                case "⌫":
                    if (!start) {
                        String text = display.getText();
                        if (text.length() > 0) {
                            display.setText(text.substring(0, text.length() - 1));
                            if (display.getText().isEmpty()) {
                                display.setText("0");
                                start = true;
                            }
                        }
                    }
                    break;
                case "C":
                    display.setText("0");
                    start = true;
                    result = 0;
                    lastCommand = "=";
                    break;
                case "MC":
                    memory.clear();
                    memoryIndex = -1;
                    break;
                case "MR":
                    if (!memory.isEmpty()) {
                        display.setText(memory.get(memoryIndex));
                        start = false;
                    }
                    break;
                case "M+":
                    memory.add(display.getText());
                    memoryIndex = memory.size() - 1;
                    break;
                case "M-":
                    if (!memory.isEmpty()) {
                        memory.remove(memoryIndex);
                        memoryIndex = memory.isEmpty() ? -1 : memory.size() - 1;
                    }
                    break;
                case "↑":
                    if (!memory.isEmpty() && memoryIndex < memory.size() - 1) {
                        memoryIndex++;
                        display.setText(memory.get(memoryIndex));
                    }
                    break;
                case "↓":
                    if (!memory.isEmpty() && memoryIndex > 0) {
                        memoryIndex--;
                        display.setText(memory.get(memoryIndex));
                    }
                    break;
                default:
                    if ("0123456789.".indexOf(command) != -1) {
                        display.setText(display.getText() + command);
                    } else {
                        calculate(command);
                    }
            }
        }
    }

    public void calculate(String command) {
        double x = Double.parseDouble(display.getText());
        switch (command) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "=":
                if (lastCommand.equals("=")) {
                    result = x;
                } else {
                    switch (lastCommand) {
                        case "+": result += x; break;
                        case "-": result -= x; break;
                        case "*": result *= x; break;
                        case "/": result /= x; break;
                    }
                }
                display.setText("" + result);
                start = true;
                break;
            case "sin": display.setText("" + Math.sin(Math.toRadians(x))); break;
            case "cos": display.setText("" + Math.cos(Math.toRadians(x))); break;
            case "tan": display.setText("" + Math.tan(Math.toRadians(x))); break;
            case "√": display.setText("" + Math.sqrt(x)); break;
        }
        lastCommand = command;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ScientificCalculator().setVisible(true);
        });
    }
}