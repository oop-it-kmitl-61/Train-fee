/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopproject;

import java.io.IOException;

/**
 *
 * @author JustALemon
 */
public class TimeCalculator {
    private int line;
    private int start;
    private int stop;
    private ReadExcel reader = new ReadExcel();
    public int calculateTime(int line, int start, int stop) throws IOException{
        this.line = line;
        this.start = start;
        this.stop = stop;
        int total = 0;
        if(line == 0){
            if(this.start>23 || this.stop>23){
                if(this.start>23 && this.stop>23){
                    if(this.start>=this.stop){
                        this.line += 1;
                        this.start += 2;
                        if(this.stop != 24){
                            total -= 2;
                        }
                        this.stop += 1;
                        for(int i=this.start-1;i>=this.stop;i--){
                            total += reader.read(4, this.line, i);
                        }
                        return total;
                    }else{
                        this.line += 1;
                        if(this.start != 24){
                            total -= 2;
                        }
                        this.start += 1;
                        this.stop += 2;
                        for(int i=this.start;i<this.stop;i++){
                            total += reader.read(4, this.line, i);
                        }
                        return total;
                    }
                }else if(this.start>23 && this.stop<=23){
                    total = new TimeCalculator().calculateTime(0, this.start, 24)+new TimeCalculator().calculateTime(0, 8, this.stop);
                    if(this.start>24){
                        total -= 2;
                    }
                    return total;
                }else if(this.start<=23 && this.stop>23){
                    total = new TimeCalculator().calculateTime(0, this.start, 8)+new TimeCalculator().calculateTime(0, 24, this.stop);
                    if(this.stop>24){
                        total -= 2;
                    }
                    return total;
                }
            }else{
                if(this.start>this.stop){
                    for(int i=this.start-1;i>=this.stop;i--){
                        total += reader.read(4, this.line, i);
                    }
                    return total;
                }else{
                    for(int i=this.start;i<this.stop;i++){
                        total += reader.read(4, this.line, i);
                    }
                    return total;
                }
            }
        }else{
            this.line += 1;
            if(this.start>this.stop){
                for(int i=this.start-1;i>=this.stop;i--){
                    total += reader.read(4, this.line, i);
                }
                return total;
            }else{
                for(int i=this.start;i<this.stop;i++){
                    total += reader.read(4, this.line, i);
                }
                return total;
            }
        }
        return total;
    }
}
