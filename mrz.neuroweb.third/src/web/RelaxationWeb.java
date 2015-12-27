package web;

import java.util.ArrayList;

/**
 *
 * @author NafanyaVictorovna
 */
public class RelaxationWeb {
    private ArrayList<int[]> Y;
    private ArrayList<int[]> YT;
    private double[][] W;
    private final int n;
    private int p;
    
    
    public RelaxationWeb(ArrayList<int[]> XVector){      
      p = XVector.size();
      n = XVector.get(0).length;
      YT = new ArrayList<>();
      W = new double[n][n]; 
    }
    
    public void toWeb(ArrayList<int[]> XVector){
      Y = new ArrayList<>(XVector);
      p = XVector.size();
    }
    
    private void formYT(){
        int[] yList;
        for(int i=0; i<n; i++){
            yList = new int[p];
            for(int k=0; k<p; k++){
                //n-ый элемент в p-ом векторе
                yList[k % p] = Y.get(k)[i];
                if(k % p == 1){
                    YT.add(yList);
                }
            }
        }   
    }
 
    public void createW(){
        this.formYT();
        for(int k=0; k<p; k++){
            double[] WYT = new double[n];
            double[] YW = new double[n];
            double[][] VS = new double[n][n];
            double YYT = 0, YWYT = 0;
            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    WYT[i] += (double)W[i][j]*YT.get(j)[k];
                    YW[i] += (double)Y.get(k)[j]*W[j][i];
                }
                YYT += (double)Y.get(k)[i]*YT.get(i)[k];
                YWYT += (double)YW[i]*YT.get(i)[k];
            }
            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                   VS[i][j] = (WYT[i] -  YT.get(i)[k])*(YW[j] - Y.get(k)[j]);
                }
            }
            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    if(i == j){
                        W[i][j] = 0;
                    } else {
                        W[i][j] = W[j][i] = (double)(W[i][j] + (VS[i][j]/(YYT - YWYT)));
                    }   
                }
            }
        }
    }
        
    private void calculateY(int k, int number){
        double Si = 0;
        for(int j=0; j<n; j++){
            Si += Y.get(k)[j]*W[j][number];
        }
        //async change Yi = F(Si)
        Y.get(k)[number] = sign(Si, k, number);
    }
    
    private int calculateDelta(int k, int number){
        int Sj, Sum = 0, prevY;
            for(int j=0; j<n; j++){
                Sj = 0;
                if(j != number){
                    for(int l=0; l<n; l++){
                        Sj += W[l][j]*Y.get(k)[j];
                    }
                    Sum += Sj*Y.get(k)[j];
                }
            }
            prevY = Y.get(k)[number];
            calculateY(k, number);    
        return (prevY - Y.get(k)[number])*Sum;
    }
    
    public void train(){ 
        for(int k=0; k<p; k++){    //every image           
            double energy;
            do{
                energy = 0;
                for(int i=0; i<n; i++){
                    energy += calculateDelta(k, i);
                }
                System.out.println("energy " + energy);
            } while(energy != 0);
        }    
    }
    
    public ArrayList<int[]> fromWeb(){
        return Y;
    }

    private int sign(double value, int k, int i){
       if(value > 0) {
           return 1;
       } else if(value < 0){
           return -1;
       } else {
           return Y.get(k)[i];
       }
    }
    //end of class
}
     