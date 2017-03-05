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
    private JPanel mainPanel, textPanel, keyPanel, funcPanel;
    
    // numeric keys + result string
    private JButton[] keys = new JButton[10];
    private String key_text;
    
    // function keys
    private JButton AC_but, plusMinus_but, percent_but;
    
    // math operation keys
    private JButton div_but, mult_but, sub_but, add_but, equals_but, point_but;
    
    // label, strings and color
    private JLabel result_lbl;
    private Color backgroundCol = Color.decode("#616161");
    private double check;
    
    // strings and variables
    private String runningNumber = "";
    private String leftValStr = "";
    private String rightValStr = "";
    private String result = "";
    private String new_val;
    private int k = 0;
    private int pointCount = 0;

    // operation
    private Operation currentOp = Operation.empty;

    // operation enum (add/div/mult/sub)
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
    }

    
    // constructor
    public Calculator(int width, int height)
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
        result_lbl.setFont(new Font("San Francisco",Font.PLAIN,22));
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
        calc.setSize(width,height);
        calc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calc.setResizable(true);
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
        
        // clear button
        if (e.getSource() == AC_but)
        {
            leftValStr = "";
            rightValStr = "";
            result = "";
            runningNumber = "";
            currentOp = Operation.empty;
            pointCount = 0;
            result_lbl.setText("0");
        }
        
        // convert to +/-
        if (e.getSource() == plusMinus_but)
        {
            // invert sign of runningNumber
            if ((runningNumber != "0" && runningNumber != "") && (result != "0"))
            {
                runningNumber = Double.toString(0 - Double.parseDouble(runningNumber));
                
                if (isInteger(Double.parseDouble(runningNumber)))
                    runningNumber = Integer.toString((int)(Double.parseDouble(runningNumber)));

                result_lbl.setText(runningNumber);
            }
        }
        
        // convert to % (/100)
        if (e.getSource() == percent_but)
        {
            runningNumber = Double.toString(Double.parseDouble(runningNumber) / 100);
            
            if (isInteger(Double.parseDouble(runningNumber)))
                runningNumber = Integer.toString((int)(Double.parseDouble(runningNumber)));
            
            result = runningNumber;
            
            result_lbl.setText(result);
        }
        
        // add decimal point
        if (e.getSource() == point_but)
        {
            if (pointCount < 1)
                runningNumber = Integer.toString((int)(Double.parseDouble(runningNumber))) + ".";
            
            result_lbl.setText(runningNumber);
            pointCount++;
        }
        
        // divide
        if (e.getSource() == div_but)
        {
            performFunction(Operation.div);
            pointCount = 0;
        }
        
        // multiply
        if (e.getSource() == mult_but)
        {
            performFunction(Operation.mult);
            pointCount = 0;
        }
        
        // subtract
        if (e.getSource() == sub_but)
        {
            performFunction(Operation.sub);
            pointCount = 0;
        }

        // add
        if (e.getSource() == add_but)
        {
            performFunction(Operation.add);
            pointCount = 0;
        }

        // equals
        if (e.getSource() == equals_but)
        {
            performFunction(currentOp);
            pointCount = 0;
        }
    }
    
    
    // perform the arithmetic function selected
    public void performFunction(Operation op)
    {
        // if there is an operation selected
        if (currentOp != Operation.empty)
        {
            // perform the maths
            // user selected an operator but then selected another operator without selecting a number
            if (runningNumber != "")
            {
                rightValStr = runningNumber;
                runningNumber = "";
                
                if (currentOp == Operation.mult)
                {
                    if (leftValStr != "")
                        result = Double.toString(Double.parseDouble(leftValStr) * Double.parseDouble(rightValStr));
                    else
                        result = "0";
                }
                
                else if (currentOp == Operation.div)
                {
                    if (leftValStr != "")
                        result = Double.toString(Double.parseDouble(leftValStr) / Double.parseDouble(rightValStr));
                    else
                        result = "0";
                }
                
                else if (currentOp == Operation.sub)
                {
                    if (leftValStr != "")
                        result = Double.toString(Double.parseDouble(leftValStr) - Double.parseDouble(rightValStr));
                    else
                        result = Double.toString(0 - Double.parseDouble(rightValStr));
                }
                
                else if (currentOp == Operation.add)
                {
                    if (leftValStr != "")
                        result = Double.toString(Double.parseDouble(leftValStr) + Double.parseDouble(rightValStr));
                    else
                        result = Double.toString(0 + Double.parseDouble(rightValStr));
                }
                
                else
                    result = "0";
                
                // check if double is whole number - if so, convert to int
                if (isInteger(Double.parseDouble(result)))
                    result = Integer.toString((int)(Double.parseDouble(result)));
                
                leftValStr = result;
                result_lbl.setText(result);
            }
            currentOp = op;
            
        }
        // first time operator has been pressed
        else
        {
            leftValStr = runningNumber;
            runningNumber = "";
            currentOp = op;
        }
        
        pointCount = 0;
    }
    
    
    // check if double is a whole number
    public boolean isInteger(double value)
    {
        return (Math.ceil(value) == Math.floor(value));
    }
}

// doesnt' work for: pressing percent button AND +/- button on new result value on screen





