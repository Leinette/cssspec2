/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSPROG3L;

/**
 *
 * @author juanluwi the great
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Calc extends JFrame implements ActionListener {

    private final String[] labels
            = {"7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"};
    private final JButton[] keys = new JButton[labels.length];
    private JButton on;
    private JButton off;
    private JTextArea screen;
    private ArrayList<Double> operands = new ArrayList();
    private ArrayList<String> operators = new ArrayList();
    private boolean isLastOperator = false;
    private boolean finishedOperation = false;
    private double total = 0;

    public Calc() {
        super("Calculator");
        setSize(300, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initComponents();
        addComponents();
    }

    private void initComponents() {
        for (int i = 0; i < labels.length; i++) {
            keys[i] = new JButton(labels[i]);
            keys[i].addActionListener(this);
        }
        on = new JButton("On");
        on.addActionListener(this);
        off = new JButton("Off");
        off.addActionListener(this);
        screen = new JTextArea();
    }

    private void addComponents() {
        JPanel northPanel, northPanel2, centerPanel;
        northPanel = new JPanel(new GridLayout(2, 1));
        northPanel2 = new JPanel(new GridLayout(1, 2));
        centerPanel = new JPanel(new GridLayout(4, 4));

        northPanel2.add(on);
        northPanel2.add(off);
        northPanel.add(screen);
        northPanel.add(northPanel2);

        for (JButton button : keys) {
            centerPanel.add(button);
        }

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object temp = e.getSource();
        if (temp.equals(on)) {
            screen.setText("");
            total = 0;
            operands.clear();
            operators.clear();
        } else if (temp.equals(off)) {
            System.exit(0);
        } else {
            for (JButton key : keys) {
                if (temp.equals(key)) {
                    if (finishedOperation){
                        screen.setText("");
                        finishedOperation = false;
                    }
                    if (!isOperator(key.getText())) {
                        if (!operators.isEmpty() && isOperator(screen.getText())) {
                            screen.setText("");
                        }
                        screen.append(key.getText());
                        isLastOperator = false;
                    } else {
                        if (key.getText().equals("=")) {
                            if (isLastOperator) {
                                JOptionPane.showMessageDialog(null, "Invalid equation!");
                                total = 0;
                                operands.clear();
                                operators.clear();
                            } else {
                                operands.add(Double.parseDouble(screen.getText()));
                                screen.setText("");
                                compute();
                            }
                        } else {
                            operands.add(Double.parseDouble(screen.getText()));
                            screen.setText("");
                            operators.add(key.getText());
                            screen.append(key.getText());
                            isLastOperator = true;
                        }
                    }
                }
            }
        }
    }

    private void compute() {
        total = operands.get(0);

        for (int i = 1; i < operands.size(); i++) {
            String s = operators.get(i - 1);
            switch (s) {
                case "+":
                    total += operands.get(i);
                    break;
                case "-":
                    total -= operands.get(i);
                    break;
                case "*":
                    total *= operands.get(i);
                    break;
                case "/":
                    total /= operands.get(i);
                    break;
            }
        }

        screen.setText("" + total);
        total = 0;
        operands.clear();
        operators.clear();
        finishedOperation = true;
    }

    private boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("=");
    }

    public static void main(String[] args) {
        new Calc().setVisible(true);
    }
}
