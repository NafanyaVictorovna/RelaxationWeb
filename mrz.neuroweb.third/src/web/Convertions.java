package web;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 *
 * @author NafanyaVictorovna
 */
public class Convertions {
    //-1 - black
    //1 - white
    private BufferedImage image;
    private ArrayList<int[]> XVector;
    
    public Convertions(int k){
        XVector = new ArrayList<>();
    };
    
    public void convertToVector(BufferedImage src){
        this.image = src;
        int h = image.getHeight();
        int w = image.getWidth();
        int[] X = new int[h*w];
        int ti = 0;
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                if(image.getRGB(i, j) == -1){
                    X[ti] = 1;
                } else {
                    X[ti] = -1;
                } 
                    ti++;
            }
        }    
        XVector.add(X);
    }
    
    public ArrayList<int[]> returnXVector(){
        return XVector;
    }
    
    public void clearXVector(){
        XVector.clear();
    }
    
    public void setResultVector(ArrayList<int[]> ResultVector){
        this.XVector = (ArrayList<int[]>)ResultVector.clone();
    }
    
    public BufferedImage convertToImage(){
        int h = this.image.getHeight();
        int w = this.image.getWidth();
        int index = 0;
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                //little hard code
                if(XVector.get(0)[index] == 1){
                    Color color = new Color(255, 255, 255);
                    image.setRGB(i, j, color.getRGB());
                } else {
                    Color color = new Color(0, 0, 0);
                    image.setRGB(i, j, color.getRGB());
                }
                index++;
            }
        }
        return image;
    }
   //end of class 
}
