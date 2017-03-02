import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/* Name: Calculator
 * Author: Harry Baines
 *
 * Modelling a simple calculator using the Swing package
 *
 */

public class Calculator implements ActionListener
{
    // frame and panels
    private JFrame calc;
    private JPanel textPanel;
    private JPanel mainPanel;
    private JPanel keyPanel;
    private JPanel funcPanel;
    
    // numeric keys
    private JButton[] keys = new JButton[10];
    private String key_text;
    
    // function keys
    private JButton AC_but, plusMinus_but, percent_but;
    
    // math operation keys
    private JButton div_but, mult_but, sub_but, add_but, equals_but, point_but;
    
    // label, strings and color
    private JLabel result_lbl;
    private Color backgroundCol = Color.decode("#616161");
    private String current_val = "0";
    private String new_val;
    private double check;
    private double[] results = new double[3];
    private String current_result;
    private double newDoub;
    
    private boolean div = false, mult = false, add = false, sub = false;
    
    public enum Operation {
        div("/"),
        mult("*"),
        sub("-"),
        add("+"),
        empty("Empty");
        
        private String op;
        
        Operation(String op) {
            this.op = op;
        }
        
        public String op() {
            return op;
        }
    }
    
    private String runningNumber = "";
    private String leftValStr = "";
    private String rightValStr = "";
    private Operation currentOp = Operation.empty;
    private String result = "";
    
    // constructor
    public Calculator()
    {
        // frames and layouts
        calc = new JFrame();
        mainPanel = new JPanel(new BorderLayout());
        textPanel = new JPanel(new BorderLayout());
        keyPanel = new JPanel(new GridLayout(5,4));
        funcPanel = new JPanel( new GridLayout(5,1));
        
        calc.setContentPane(mainPanel);
        textPanel.setBackground(backgroundCol);
        
        // add panels to mainPanel
        mainPanel.add(textPanel, BorderLayout.NORTH);
        mainPanel.add(keyPanel, BorderLayout.WEST);
        mainPanel.add(funcPanel, BorderLayout.EAST);
        
        // numeric key buttons
        for (int i = 0; i < 10; i++)
        {
            key_text = Integer.toString(i);
            keys[i] = new JButton(key_text);
            keys[i].addActionListener(this);
        }
        
        // calculator function buttons
        AC_but = new JButton("AC");
        plusMinus_but = new JButton("+/-");
        percent_but = new JButton("%");
        point_but = new JButton(".");
        equals_but = new JButton("=");
        div_but = new JButton("/");
        mult_but = new JButton("X");
        sub_but = new JButton("-");
        add_but = new JButton("+");
        
        // action listeners for function buttons
        AC_but.addActionListener(this);
        plusMinus_but.addActionListener(this);
        percent_but.addActionListener(this);
        point_but.addActionListener(this);
        equals_but.addActionListener(this);
        div_but.addActionListener(this);
        mult_but.addActionListener(this);
        sub_but.addActionListener(this);
        add_but.addActionListener(this);

        // result label
        result_lbl = new JLabel("0");
        result_lbl.setFont(new Font("San Francisco",Font.PLAIN,40));
        result_lbl.setForeground(Color.white);
        
        // add components to screen in this layout
        keyPanel.add(AC_but);
        keyPanel.add(plusMinus_but);
        keyPanel.add(percent_but);
        funcPanel.add(div_but);
        keyPanel.add(keys[7]);
        keyPanel.add(keys[8]);
        keyPanel.add(keys[9]);
        funcPanel.add(mult_but);
        keyPanel.add(keys[4]);
        keyPanel.add(keys[5]);
        keyPanel.add(keys[6]);
        funcPanel.add(sub_but);
        keyPanel.add(keys[1]);
        keyPanel.add(keys[2]);
        keyPanel.add(keys[3]);
        funcPanel.add(add_but);
        keyPanel.add(keys[0]);
        keyPanel.add(point_but);
        funcPanel.add(equals_but);
        textPanel.add(result_lbl, BorderLayout.EAST);
        
        // frame details
        calc.setTitle("Calculator");
        calc.setSize(300,400);
        calc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calc.setResizable(false);
        calc.setVisible(true);
    }
    
    // action listeners
    public void actionPerformed(ActionEvent e)
    {
        // button 0
        if (e.getSource() == keys[0])
        {
            runningNumber += "0";
            result_lbl.setText(runningNumber);
        }
        
        // buttons 1-9
        for (int j = 1; j <= 9; j++)
        {
            if (e.getSource() == keys[j])
            {
                runningNumber += Integer.toString(j);
                result_lbl.setText(runningNumber);
            }
        }
        
        // calculator function keys
        if (e.getSource() == AC_but)
        {
            leftValStr = "";
            rightValStr = "";
            result = "0";
            runningNumber = "0";
            currentOp = Operation.empty;
            result_lbl.setText(result);
        }
        
        if (e.getSource() == plusMinus_but)
        {
            // string to int, convert to -ve if > 0, +ve if < 0
            
        }
        
        if (e.getSource() == percent_but)
        {
            // percentage (/100)
            result_lbl.setText(current_val);
        }
        
        if (e.getSource() == div_but)
            performFunction(Operation.div);
        
        if (e.getSource() == mult_but)
            performFunction(Operation.mult);
        
        if (e.getSource() == sub_but)
            performFunction(Operation.sub);
        
        if (e.getSource() == add_but)
            performFunction(Operation.add);
        
        if (e.getSource() == equals_but)
            performFunction(currentOp);
        
        if (e.getSource() == point_but)
        {
            
        }
    }
    
    public void performFunction(Operation op)
    {
        
        if (currentOp != Operation.empty)
        {
            // Run some maths
            // user selected an operator but then selected another operator without selecting a number
            if (runningNumber != "")
            {
                rightValStr = runningNumber;
                runningNumber = "";
                
                if (currentOp == Operation.mult) {
                    result = Double.toString(Double.parseDouble(leftValStr) * Double.parseDouble(rightValStr));
                } else if (currentOp == Operation.div) {
                    result = Double.toString(Double.parseDouble(leftValStr) / Double.parseDouble(rightValStr));
                } else if (currentOp == Operation.sub) {
                    result = Double.toString(Double.parseDouble(leftValStr) - Double.parseDouble(rightValStr));
                } else if (currentOp == Operation.add) {
                    result = Double.toString(Double.parseDouble(leftValStr) + Double.parseDouble(rightValStr));
                }
                
                leftValStr = result;
                result_lbl.setText(result);
            }
            
            currentOp = op;
            
        } else {
            //First time operator has been pressed
            leftValStr = runningNumber;
            runningNumber = "";
            currentOp = op;
        }
    }
    
    // removes leading zeros from a number
    public void removeLeadingZeros()
    {
        try
        {
            Integer newInt = Integer.parseInt(current_val);
            current_val = newInt.toString();
        }
        catch (Exception e)
        {
            System.out.println("Calculator Error");
            System.exit(0);
        }
    }
}






