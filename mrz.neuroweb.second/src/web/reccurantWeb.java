/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

/** print:
 * source data
 * weight matrix after studying at first layer
 * weight matrix after studying at second layer
 * count of iteration
 * error
 * answers for all sequences
 *
 * @author NafanyaVictorovna
 */
public class reccurantWeb {
    
    private int[] X;                      //studying sequence; input 
    private double[][] Wji;               //X -> H
    private double[] Wki;                 //C -> H    num of result = 1
    private double[] Vij;                 //H -> Y
    private double[] H;                   //result after H
    private double[] S;                   //param of activation function
    private double[] Y;                   //output
    private double/*[]*/ Ty;                  //Y border
    private double[] Ts;                 //S border  
    private double y;
    //private Sequences sequence;
    
    
    public reccurantWeb(int n, int m){
        X = new int[n];
        //sequence = new Sequences(n);
        Y = new double[n];
        H = new double[m];
        S = new double[m];
        Vij = new double[m];            //j = 1 = k
        Wki = new double[m];            //k = 1
        Wji = new double[n][m];
        //Ty = new double[n];
        Ts = new double[m];
    }
    
    public int[] returnX(){
        return X;
    } 
    
    public void fibbonachi(int n){
        X[0] = X[1] = 1;
        for(int i=2; i<n; i++){
            X[i] = X[i-1] + X[i-2];
        }
    }
    
    public void factorial(int n){
        for(int i=0; i<n; i++){
            X[i] = 1;
            if(i > 1){
                for(int j=i; j>1; j--){
                    X[i] *= j;
                }
            }
        }
    }
    
    public void periodic(int n){
        boolean f = true;
        for(int i=0; i<n; i++){
            if(i % 2 != 0){ X[i] = 0;}
            else{ 
                if(f){ 
                    X[i] = 1;
                    f = false;
                } else{
                    X[i] = -1; 
                    f = true;
                }
           }
        }
    }
    
    public void degree(int n){
        for(int i=0; i<n; i++){
            X[i] = (int)Math.pow(i,2);
       }
    }
    
    public void createWmatrixes(int n, int m){
        for(int j=0; j<n+1; j++){
            for(int i=0; i<m; i++){
                Wji[j][i] = Math.random()*2 - 1;
            }
        }
        this.createVandWki(m);
        this.createBordersWeights(m);
        y = 0;
    }
    
    private void createVandWki(int m){
        for(int i=0; i<m; i++){
            Vij[i] = Wki[i] = Math.random()*2 - 1;
        }
    }
    
    private void createBordersWeights(int m){
        Ty = Math.random()*2 - 1;
        for(int i=0; i<m; i++){
            Ts[i] = Math.random()*2 - 1;
        }
    }
   
    private void studying(int m, int n){
        for(int i=0; i<m; i++){
            double s = 0, hidden_out;
            for(int j=0; j<n+1; j++){
                s += Wji[j][i]*X[j];
            }
            s += Wki[i]*y - Ts[i];
            S[i] = s;
            //activation function sin(arctg(x))
            hidden_out = s/(Math.sqrt(1+s*s));
            H[i] = hidden_out;
        }  
        y = 0;
        for(int i=0; i<m; i++){
            y += Vij[i]*H[i]; 
        }
        y -= Ty;
    }
    
    public double Z(){  
        return y;
    }
    
    public void predict(double[] Y, int m, int n, int begin, int end){
        for(int i=begin; i<end+1; i++){ 
           if(i<end){
              y  = Y[i]; 
           }    
           this.studying(m,n);
        }
        System.out.println("prediction");
    }
 
    public void correctWeights(int m, int n, int coord){
        double alpha = 0.0005;
        double[] derP = new double[m];
        //correct coeffients Vij, Wki (k = 1), Ts
        for(int i=0; i<m; i++){  
            Vij[i] -= alpha*(y - X[coord])*H[i];        
            derP[i] = 1./(Math.pow((1+S[i]*S[i]),3./2));            
            Wki[i] -= alpha*(y - X[coord])*Vij[i]*derP[i]*y;
            Ts[i] += alpha*(y - X[coord])*Vij[i]*derP[i];                                    
        }
        //correct Wji
        for(int j=0; j<n; j++){
            for(int i=0; i<m; i++){
                Wji[j][i] -= alpha*(y - X[coord])*Vij[i]*derP[i]*X[j]; 
            }
        }
        //and Ty
        Ty += alpha*(y - X[coord]);
    }

    public double calculateError(int begin, int end, int m){ 
        for(int coord = 0; coord < end; coord++){
        this.studying(m, end);            
        this.correctWeights(m, end, coord); 
        Y[coord] = y;
        //coord++;
        }
        double e = 0;        
        for(int i=begin; i<end; i++){  
            e += Math.pow((Y[i] - X[i]),2);           
        }
        return e/2;
    }    
}
