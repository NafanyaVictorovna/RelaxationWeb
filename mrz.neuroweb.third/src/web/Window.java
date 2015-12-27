package web;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author NafanyaVictorovna
 */
public class Window extends JFrame{
    
    private BufferedImage src;
    private Convertions result;
    
    private JPanel common;
    private JPanel text;
    private JPanel button;
    private JPanel srcImagePanel;
    private JPanel resultImagePanel;
    
    private JLabel srcLabel;
    private JLabel resultLabel;
    
    private JTextArea field;
    
    private JButton detection;
    
    private FlowLayout lay;
    
    public Window(String title){
        super(title);
        init();
        listener();
    }
    
    private void init(){
        common = new JPanel();
        text = new JPanel();
        button = new JPanel();
        srcImagePanel = new JPanel();
        resultImagePanel = new JPanel();
        
        srcLabel = new JLabel();
        resultLabel = new JLabel();
        
        field = new JTextArea();
        detection = new JButton("start relax");
        
        field.setSize(50, 70);
        field.setText("Hi. It's relaxation web lab");
        
        lay = new FlowLayout(2);
        common.setLayout(lay);
        
        text.add(field);
        button.add(detection);
        srcImagePanel.add(srcLabel);
        resultImagePanel.add(resultLabel);
        common.add(text);
        common.add(button);
        common.add(srcImagePanel);
        common.add(resultImagePanel);
        setContentPane(common);
    }
    
    private void openImage(String filename){
        try{
            src = ImageIO.read(new File(filename));
        } catch(IOException e){
            
        }
    }
        
    private void listener(){
        detection.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //fix - you must read array of images, and iterate k - img count
                int k = 2;
                result = new Convertions(k);
                for(int i=1; i<=k; i++){
                    openImage("src/images/" + i + ".png");
                    result.convertToVector(src);
                }                      
                RelaxationWeb web = new RelaxationWeb(result.returnXVector());
                web.toWeb(result.returnXVector());
//                result.setResultVector(web.fromWeb());
//                System.out.println("start");
//                web.check();
//                System.out.println("finish");
                web.createW();
//                web.getW();
                web.train(); 
                result.clearXVector();
                //I decide that will detect one image always
                openImage("src/images/2bad.png");
                result.convertToVector(src);
                web.toWeb(result.returnXVector());
                web.train(); 
                result.setResultVector(web.fromWeb());
                resultLabel.setIcon(new ImageIcon(result.convertToImage()));
                result.clearXVector();
                common.add(resultImagePanel.add(resultLabel));
                common.updateUI();
                System.out.println("the end");
            }
        });
    };
    
}