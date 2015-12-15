/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package window;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import web.Sequences;
import web.reccurantWeb;

/**
 *
 * @author NafanyaVictorovna
 */
public class Window extends JFrame{
    
    private JButton predict;
    private JCheckBox zero;
    private JCheckBox maxIterNum;
    private JCheckBox maxStep;
    private JCheckBox printEveryLay;
    
    private JCheckBox fibonacchi;
    private JCheckBox factorial;
    private JCheckBox periodic;
    private JCheckBox degree;
    
    private ButtonGroup functions;
    
    private JTextArea result;
    private JPanel buttonPanel;
    private JPanel functionPanel;
    private JPanel textPanel;
    private JPanel motherPane;
    
    private GridLayout layer;
    
    public Window(String name){
        super(name);
        init();
        listener();
    }
    
    private void init(){
        buttonPanel = new JPanel();
        functionPanel = new JPanel();
        textPanel = new JPanel();
        motherPane = new JPanel();
        
        result = new JTextArea();
        result.setSize(50, 70);
        
        layer = new GridLayout(0,2);
        motherPane.setLayout(layer);
        
        predict = new JButton("predict next value");
        
        zero = new JCheckBox("context neurons to zero");
        maxIterNum = new JCheckBox("number of maximal iteration");
        maxStep = new JCheckBox("maximal studying step");
        printEveryLay = new JCheckBox("print weight matrixes & border");
        
        fibonacchi = new JCheckBox("fibbonachi series");
        factorial = new JCheckBox("x!");
        periodic = new JCheckBox("cos x");
        degree = new JCheckBox("x in 2 degree");
        
        functions = new ButtonGroup();
        functions.add(fibonacchi);
        functions.add(factorial);
        functions.add(periodic);
        functions.add(degree);
        
        buttonPanel.setLayout(new GridLayout(0,1));
        textPanel.setLayout(new GridLayout(0,1));
        
        layer.addLayoutComponent("buttonPanel", buttonPanel);
        layer.addLayoutComponent("textPanel", textPanel);
        
        textPanel.add(result);
        functionPanel.add(fibonacchi);
        functionPanel.add(factorial);
        functionPanel.add(periodic);
        functionPanel.add(degree);
        //set BORDER!!!!!!!!!
        functionPanel.setBorder(null);
        buttonPanel.setBorder(null);
        //
        buttonPanel.add(zero);
        buttonPanel.add(maxIterNum);
        buttonPanel.add(maxStep);
        buttonPanel.add(printEveryLay);
        buttonPanel.add(functionPanel);
        buttonPanel.add(predict);
        
        motherPane.add(buttonPanel);
        motherPane.add(textPanel);
        setContentPane(motherPane);        
    }
    
    private void listener(){
        predict.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String n_value = JOptionPane.showInputDialog(null, "Input size of numeric series", JOptionPane.QUESTION_MESSAGE);
                int n = Integer.parseInt(n_value);
                int p = 0, m =0; 
                boolean flag = true;
                while(flag){
                String p_value = JOptionPane.showInputDialog(null, "Input size of studying area less "+n, JOptionPane.QUESTION_MESSAGE);
                p = Integer.parseInt(p_value);
                if(p<n){flag = false;}
                }
                flag = true;
                while(flag){
                String m_value = JOptionPane.showInputDialog(null, "Input count of neurons on inside layer, no more "+2*n, JOptionPane.QUESTION_MESSAGE);
                m = Integer.parseInt(m_value);
                if(m<=2*n){flag = false;}
                }
                reccurantWeb neuron = new reccurantWeb(n+1,m);
                //Sequences seq = new Sequences(n);
                //set name of active checkbox - problem!
                //seq.chooseSequence();
                if(fibonacchi.isSelected()) {
                  neuron.fibbonachi(n+1);
                  
                } else
                if(factorial.isSelected()){
                  neuron.factorial(n+1);
                    
                } else
                if(periodic.isSelected()){
                    neuron.periodic(n+1);
                    
                } else
                if(degree.isSelected()){
                    neuron.degree(n+1);
                    
                } else{
                    JOptionPane.showMessageDialog(null, "You should choose sequence");
                }
//                flag = true;
                double error = 0, E = 0;
//                while(flag){
                String e_value = JOptionPane.showInputDialog(null, "Input maximal error"/* <= 0.1"*/, JOptionPane.QUESTION_MESSAGE);
                error = Double.parseDouble(e_value);
//                if(error <= 0.1){flag = false;}
//                }
                double[] res = new double[n+1];
                int L = n - p + 1;
                neuron.createWmatrixes(n, m);
                E = 100000;
                while(E > error){
                    E = 0;
                    int begin = 0, end = p;                    
                    for(int j=0; j<L; j++){                       
                        E += neuron.calculateError(begin, end, m);                        
                        end++;
                    }
                    System.out.println("summary error: "+E+" Y value: "+neuron.Z());
                }
                for(int j=0; j<p; j++){
                    res[j] = (double)neuron.returnX()[j]; 
                   }
                flag = true;
                int sNum = 0;
                while(flag){
                String s_value = JOptionPane.showInputDialog(null, "Input count of predictable elements", JOptionPane.QUESTION_MESSAGE);
                sNum = Integer.parseInt(s_value);
                if(sNum > 1){flag = false;}
                else { JOptionPane.showMessageDialog(null, "You should input number of predictable elements"); }
                }
                int num = 0;
//                while(num<sNum){
                for(int i=0; i<sNum; i++){
                    neuron.predict(res, m, n, num, p+num);
                    res[p+num] = neuron.Z();
                    System.out.println("result: " +Math.round(res[num+p]));   
                    num++;
                }                    
                System.out.println("user error: " +error);
                System.out.println("Error: " +E);                
            }
        });
                
        
    };
}
