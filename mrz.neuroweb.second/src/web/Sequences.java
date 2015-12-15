/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

/**
 *
 * @author NafanyaVictorovna
 */
public class Sequences {
    
    private int[] X;                      //studying sequence; input
    private int n;
    
    public Sequences(int n){
        this.n = n+1;
        X = new int[n];
    }
    
    private void fibbonachi(){
        X[0] = X[1] = 1;
        for(int i=2; i<n; i++){
            X[i] = X[i-1] + X[i-2];
        }
    }
    
    private void factorial(){
        for(int i=0; i<n; i++){
            X[i] = 1;
            if(i > 1){
                for(int j=i; j>1; j--){
                    X[i] *= j;
                }
            }
        }
    }
    
    private void periodic(){
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
    
    private void degree(){
        for(int i=0; i<n; i++){
            X[i] = (int)Math.pow(i,2);
       }
    }
    
    public int[] chooseSequence(String seqName){
        if(seqName.equals("fibbonachi")){this.fibbonachi();}
        if(seqName.equals("factorial")){this.factorial();}       
        if(seqName.equals("periodic")){this.periodic();}
        if(seqName.equals("degree")){this.degree();}
        return X;
    }
}
