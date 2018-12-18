/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopproject;

/**
 *
 * @author JustALemon
 */
public class keeper {
    private String text;
    private int line;
    private int station;
    
    public keeper(){}
    
    public void setKeep(String keep){
        this.text = keep;
    }
    public String getKeep(){
        return this.text;
    }
    public void setLine(int line){
        this.line = line;
    }
    public int getLine(){
        return this.line;
    }
    public void setStation(int num){
        this.station = num;
    }
    public int getStation(){
        return this.station;
    }
}
