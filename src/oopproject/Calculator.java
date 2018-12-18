/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopproject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.objects.NativeMath.min;

/**
 *
 * @author JustALemon
 */
public class Calculator {
    private int startline;
    private int start;
    private int stopline;
    private int stop;
    private double fee;
    private ReadExcel reader = new ReadExcel();
    private String message;
    private TimeCalculator timer = new TimeCalculator();
    private int time = 0;
    public double calculate(int startline, int start, int stopline, int stop) throws IOException{
        this.startline = startline;
        this.start = start;
        this.stopline = stopline;
        this.stop = stop;
        this.fee = 0;
        if(this.startline == this.stopline){
            this.message = "none";
            try {
                this.fee = reader.read(this.startline, this.start, this.stop);
                this.time = timer.calculateTime(this.startline, this.start, this.stop);
                return this.fee;
            } catch (IOException ex) {
                Logger.getLogger(Calculator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(this.startline == 0){
            if(this.stopline == 1){
                this.fee = this.btstomrt1();
                return this.fee;
            }else if(this.stopline == 2){
                this.fee = this.btstoairlink();
                return this.fee;
            }else if(this.stopline == 3){
                this.fee =this.btstomrt2();
                return this.fee;
            }
        }else if(this.startline == 1){
            if(this.stopline == 0){
                this.fee = this.mrt1tobts();
                return this.fee;
            }else if(this.stopline == 2){
                this.fee = this.mrt1toairlink();
                return this.fee;
            }else if(this.stopline == 3){
                this.fee = this.mrt1tomrt2();
                return this.fee;
            }
        }else if(this.startline == 2){
            if(this.stopline == 0){
                this.fee = this.airlinktobts();
                return this.fee;
            }else if(this.stopline == 1){
                this.fee = this.airlinktomrt1();
                return this.fee;
            }else if(this.stopline == 3){
                this.fee = this.airlinktomrt2();
                return this.fee;
            }
        }else if(this.startline == 3){
            if(this.stopline == 0){
                this.fee = this.mrt2tobts();
                return this.fee;
            }else if(this.stopline == 1){
                this.fee = this.mrt2tomrt1();
                return this.fee;
            }else if(this.stopline == 2){
                this.fee = this.mrt2toairlink();
                return this.fee;
            }
        }
        return this.fee;
    }
    public double btstomrt1() throws IOException{
        double firstway = reader.read(this.startline, this.start, 12)+reader.read(1, 7, this.stop);
        double secondway = reader.read(this.startline, this.start, 26)+reader.read(1, 3, this.stop);
        double thirdway = reader.read(this.startline, this.start, 1)+reader.read(1, 16, this.stop);
        double forthway = reader.read(this.startline, this.start, 6)+reader.read(2, 8, 6)+reader.read(1, 8, this.stop);
        double minimum = min(50000, firstway, secondway, thirdway, forthway);
        if(minimum == firstway){
            this.message = "ไปลงที่สถานี อโศก จากนั้นเปลี่ยนไป mrt";
            this.time = timer.calculateTime(this.startline, this.start, 12)+timer.calculateTime(1, 7, this.stop);
        }else if(minimum == secondway){
            this.message = "ไปลงที่สถานี ศาลาแดง จากนั้นเปลี่ยนไป mrt";
            this.time = timer.calculateTime(this.startline, this.start, 26)+timer.calculateTime(1, 3, this.stop);
        }else if(minimum == thirdway){
            this.message = "ไปลงที่สถานี หมอชิต จากนั้นเปลี่ยนไป mrt";
            this.time = timer.calculateTime(this.startline, this.start, 1)+timer.calculateTime(1, 16, this.stop);
        }else if(minimum == forthway){
            this.message = "ไปลงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink นั่งต่อไปจนถึงสถานี มักกะสัน จากนั้นเปลี่ยนไป mrt";
            this.time = timer.calculateTime(this.startline, this.start, 6)+timer.calculateTime(2, 8, 6)+timer.calculateTime(1, 8, this.stop);
        }
        return minimum;
    }
    
    public double btstoairlink() throws IOException{
        double firstway = reader.read(this.startline, this.start, 6)+reader.read(2, 8, this.stop);
        double secondway = reader.read(this.startline, this.start, 12)+reader.read(1, 7, 8)+reader.read(2, 6, this.stop);
        double thirdway = reader.read(this.startline, this.start, 26)+reader.read(1, 3, 8)+reader.read(2, 6, this.stop);
        double forthway = reader.read(this.startline, this.start, 1)+reader.read(1, 16, 8)+reader.read(2, 6, this.stop);
        double minimum = min(50000,firstway, secondway, thirdway, forthway);
        if(minimum == forthway){
            this.message = "ไปลงที่สถานี หมอชิต จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink";
            this.time = timer.calculateTime(this.startline, this.start, 1)+timer.calculateTime(1, 16, 8)+timer.calculateTime(2, 6, this.stop);
        }else if(minimum == secondway){
            this.message = "ไปลงที่สถานี อโศก จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink";
            this.time = timer.calculateTime(this.startline, this.start, 12)+timer.calculateTime(1, 7, 8)+timer.calculateTime(2, 6, this.stop);
        }else if(minimum == thirdway){
            this.message = "ไปลงที่สถานี ศาลาแดง จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink";
            this.time = timer.calculateTime(this.startline, this.start, 26)+timer.calculateTime(1, 3, 8)+timer.calculateTime(2, 6, this.stop);
        }else if(minimum == firstway){
            this.message = "ไปลงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
            this.time = timer.calculateTime(this.startline, this.start, 6)+timer.calculateTime(2, 8, this.stop);
        }
        return minimum;
    }
    
    public double btstomrt2() throws IOException{
        double firstway = reader.read(this.startline, this.start, 12)+reader.read(1, 7, 19)+reader.read(3, 1, this.stop);
        double secondway = reader.read(this.startline, this.start, 26)+reader.read(1, 3, 19)+reader.read(3, 1, this.stop);
        double thirdway = reader.read(this.startline, this.start, 1)+reader.read(1, 16, 19)+reader.read(3, 1, this.stop);
        double forthway = reader.read(this.startline, this.start, 6)+reader.read(2, 8, 6)+reader.read(1, 8, 19)+reader.read(3, 1, this.stop);
        double minimum = min(50000,firstway, secondway, thirdway, forthway);
        if(minimum == firstway){
            this.message = "ไปลงที่สถานี อโศก จากนั้นเปลี่ยนไป mrt เมื่อถึง เตาปูน ให้เปลี่ยนไปสายสีม่วง";
            this.time = timer.calculateTime(this.startline, this.start, 12)+timer.calculateTime(1, 7, 19)+timer.calculateTime(3, 1, this.stop);
        }else if(minimum == secondway){
            this.message = "ไปลงที่สถานี ศาลาแดง จากนั้นเปลี่ยนไป mrt เมื่อถึง เตาปูน ให้เปลี่ยนไปสายสีม่วง";
            this.time = timer.calculateTime(this.startline, this.start, 26)+timer.calculateTime(1, 3, 19)+timer.calculateTime(3, 1, this.stop);
        }else if(minimum == thirdway){
            this.message = "ไปลงที่สถานี หมอชิต จากนั้นเปลี่ยนไป mrt เมื่อถึง เตาปูน ให้เปลี่ยนไปสายสีม่วง";
            this.time = timer.calculateTime(this.startline, this.start, 1)+timer.calculateTime(1, 16, 19)+timer.calculateTime(3, 1, this.stop);
        }else if(minimum == forthway){
            this.message = "ไปลงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink นั่งต่อไปจนถึงสถานี มักกะสัน จากนั้นเปลี่ยนไป mrt เมื่อถึง เตาปูน ให้เปลี่ยนไปสายสีม่วง";
            this.time = timer.calculateTime(this.startline, this.start, 6)+timer.calculateTime(2, 8, 6)+timer.calculateTime(1, 8, 19)+timer.calculateTime(3, 1, this.stop);
        }
        return minimum;
    }
    
    public double mrt1tomrt2() throws IOException{
        double send = reader.read(this.startline, this.start, 19)+reader.read(3, 1, this.stop);
        this.message = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีม่วง";
        this.time = timer.calculateTime(this.startline, this.start, 19)+timer.calculateTime(3, 1, this.stop);
        return send;
    }
    
    public double mrt1tobts() throws IOException{
        double firstway = reader.read(this.startline, this.start, 7)+reader.read(0, 12, this.stop);
        double secondway = reader.read(this.startline, this.start, 3)+reader.read(0, 26, this.stop);
        double thirdway = reader.read(this.startline, this.start, 16)+reader.read(0, 1, this.stop);
        double forthway = reader.read(this.startline, this.start, 8)+reader.read(2, 6, 8)+reader.read(0, 6, this.stop);
        double minimum = min(50000,firstway, secondway, thirdway, forthway);
        if(minimum == firstway){
            this.message = "ไปลงที่สถานี สุขุมวิท จากนั้นเปลี่ยนไป bts";
            this.time = timer.calculateTime(this.startline, this.start, 7)+timer.calculateTime(0, 12, this.stop);
        }else if(minimum == secondway){
            this.message = "ไปลงที่สถานี สีลม จากนั้นเปลี่ยนไป bts";
            this.time = timer.calculateTime(this.startline, this.start, 3)+timer.calculateTime(0, 26, this.stop);
        }else if(minimum == thirdway){
            this.message = "ไปลงที่สถานี สวนจตุจักร จากนั้นเปลี่ยนไป bts";
            this.time = timer.calculateTime(this.startline, this.start, 16)+timer.calculateTime(0, 1, this.stop);
        }else if(minimum == forthway){
            this.message = "ไปลงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป bts";
            this.time = timer.calculateTime(this.startline, this.start, 8)+timer.calculateTime(2, 6, 8)+timer.calculateTime(0, 6, this.stop);
        }
        return minimum;
    }
    
    public double mrt1toairlink() throws IOException{
        double firstway = reader.read(this.startline, this.start, 7)+reader.read(0, 12, 6)+reader.read(2, 8, this.stop);
        double secondway = reader.read(this.startline, this.start, 3)+reader.read(0, 26, 6)+reader.read(2, 8, this.stop);
        double thirdway = reader.read(this.startline, this.start, 16)+reader.read(0, 1, 6)+reader.read(2, 8, this.stop);
        double forthway = reader.read(this.startline, this.start, 8)+reader.read(2, 6, this.stop);
        double minimum = min(50000,firstway, secondway, thirdway, forthway);
        if(minimum == firstway){
            this.message = "ไปลงที่สถานี สุขุมวิท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
            this.time = timer.calculateTime(this.startline, this.start, 7)+timer.calculateTime(0, 12, 6)+timer.calculateTime(2, 8, this.stop);
        }else if(minimum == secondway){
            this.message = "ไปลงที่สถานี สีลม จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
            this.time = timer.calculateTime(this.startline, this.start, 3)+timer.calculateTime(0, 26, 6)+timer.calculateTime(2, 8, this.stop);
        }else if(minimum == thirdway){
            this.message = "ไปลงที่สถานี สวนจตุจักร จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
            this.time = timer.calculateTime(this.startline, this.start, 16)+timer.calculateTime(0, 1, 6)+timer.calculateTime(2, 8, this.stop);
        }else if(minimum == forthway){
            this.message = "ไปลงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink";
            this.time = timer.calculateTime(this.startline, this.start, 8)+timer.calculateTime(2, 6, this.stop);
        }
        return minimum;
    }
    
    public double mrt2tomrt1() throws IOException{
        double send = reader.read(this.startline, this.start, 1)+reader.read(1, 19, this.stop);
        this.message = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า";
        this.time = timer.calculateTime(this.startline, this.start, 1)+timer.calculateTime(1, 19, this.stop);
        return send;
    }
    
    public double mrt2tobts() throws IOException{
        double firstway = reader.read(this.startline, this.start, 1)+reader.read(1, 19, 7)+reader.read(0, 12, this.stop);
        double secondway = reader.read(this.startline, this.start, 1)+reader.read(1, 19, 3)+reader.read(0, 26, this.stop);
        double thirdway = reader.read(this.startline, this.start, 1)+reader.read(1, 19, 16)+reader.read(0, 1, this.stop);
        double forthway = reader.read(this.startline, this.start, 1)+reader.read(1, 19, 8)+reader.read(2, 6, 8)+reader.read(0, 6, this.stop);
        double minimum = min(50000,firstway, secondway, thirdway, forthway);
        if(minimum == firstway){
            this.message = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี สุขุมวิท จากนั้นเปลี่ยนไป bts";
            this.time = timer.calculateTime(this.startline, this.start, 1)+timer.calculateTime(1, 19, 7)+timer.calculateTime(0, 12, this.stop);
        }else if(minimum == secondway){
            this.message = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี สีลม จากนั้นเปลี่ยนไป bts";
            this.time = timer.calculateTime(this.startline, this.start, 1)+timer.calculateTime(1, 19, 3)+timer.calculateTime(0, 26, this.stop);
        }else if(minimum == thirdway){
            this.message = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี สวนจตุจักร จากนั้นเปลี่ยนไป bts";
            this.time = timer.calculateTime(this.startline, this.start, 1)+timer.calculateTime(1, 19, 16)+timer.calculateTime(0, 1, this.stop);
        }else if(minimum == forthway){
            this.message = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป bts";
            this.time = timer.calculateTime(this.startline, this.start, 1)+timer.calculateTime(1, 19, 8)+timer.calculateTime(2, 6, 8)+timer.calculateTime(0, 6, this.stop);
        }
        return minimum;
    }
    
    public double mrt2toairlink() throws IOException{
        double firstway = reader.read(this.startline, this.start, 1)+reader.read(1, 19, 7)+reader.read(0, 12, 6)+reader.read(2, 8, this.stop);
        double secondway = reader.read(this.startline, this.start, 1)+reader.read(1, 19, 3)+reader.read(0, 26, 6)+reader.read(2, 8, this.stop);
        double thirdway = reader.read(this.startline, this.start, 1)+reader.read(1, 19, 16)+reader.read(0, 1, 6)+reader.read(2, 8, this.stop);
        double forthway = reader.read(this.startline, this.start, 1)+reader.read(1, 19, 8)+reader.read(2, 6, this.stop);
        double minimum = min(50000,firstway, secondway, thirdway, forthway);
        if(minimum == firstway){
            this.message = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี สุขุมวิท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
            this.time = timer.calculateTime(this.startline, this.start, 1)+timer.calculateTime(1, 19, 7)+timer.calculateTime(0, 12, 6)+timer.calculateTime(2, 8, this.stop);
        }else if(minimum == secondway){
            this.message = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี สีลม จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
            this.time = timer.calculateTime(this.startline, this.start, 1)+timer.calculateTime(1, 19, 3)+timer.calculateTime(0, 26, 6)+timer.calculateTime(2, 8, this.stop);
        }else if(minimum == thirdway){
            this.message = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี สวนจตุจักร จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี พญาไท จากนั้นเปลี่ยนไป airportlink";
            this.time = timer.calculateTime(this.startline, this.start, 1)+timer.calculateTime(1, 19, 16)+timer.calculateTime(0, 1, 6)+timer.calculateTime(2, 8, this.stop);
        }else if(minimum == forthway){
            this.message = "ไปลงที่สถานี เตาปูน จากนั้นเปลี่ยนไป สายสีฟ้า นั่งต่อไปจนถึงสถานี เพชรบุรี จากนั้นเปลี่ยนไป airportlink";
            this.time = timer.calculateTime(this.startline, this.start, 1)+timer.calculateTime(1, 19, 8)+timer.calculateTime(2, 6, this.stop);
        }
        return minimum;
    }

    public double airlinktobts() throws IOException{
        double firstway = reader.read(this.startline, this.start, 8)+reader.read(0, 6, this.stop);
        double secondway = reader.read(this.startline, this.start, 6)+reader.read(1, 8, 3)+reader.read(0, 26, this.stop);
        double thirdway = reader.read(this.startline, this.start, 6)+reader.read(1, 8, 7)+reader.read(0, 12, this.stop);
        double forthway = reader.read(this.startline, this.start, 6)+reader.read(1, 8, 16)+reader.read(0, 1, this.stop);
        double minimum = min(50000,firstway, secondway, thirdway, forthway);
        if(minimum == firstway){
            this.message = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts";
            this.time = timer.calculateTime(this.startline, this.start, 8)+timer.calculateTime(0, 6, this.stop);
        }else if(minimum == secondway){
            this.message = "ไปลงที่สถานี มักกะสัน จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี สีลม จากนั้นเปลี่ยนไป bts";
            this.time = timer.calculateTime(this.startline, this.start, 6)+timer.calculateTime(1, 8, 3)+timer.calculateTime(0, 26, this.stop);
        }else if(minimum == thirdway){
            this.message = "ไปลงที่สถานี มักกะสัน จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี สุขุมวิท จากนั้นเปลี่ยนไป bts";
            this.time = timer.calculateTime(this.startline, this.start, 6)+timer.calculateTime(1, 8, 7)+timer.calculateTime(0, 12, this.stop);
        }else if(minimum == forthway){
            this.message = "ไปลงที่สถานี มักกะสัน จากนั้นเปลี่ยนไป mrt นั่งต่อไปจนถึงสถานี สวนจตุจักร จากนั้นเปลี่ยนไป bts";
            this.time = timer.calculateTime(this.startline, this.start, 6)+timer.calculateTime(1, 8, 16)+timer.calculateTime(0, 1, this.stop);
        }
        return minimum;
    }
    
    public double airlinktomrt1() throws IOException{
        double firstway = reader.read(this.startline, this.start, 6)+reader.read(1, 8, this.stop);
        double secondway = reader.read(this.startline, this.start, 8)+reader.read(0, 6, 1)+reader.read(1, 16, this.stop);
        double thirdway = reader.read(this.startline, this.start, 8)+reader.read(0, 6, 12)+reader.read(1, 7, this.stop);
        double forthway = reader.read(this.startline, this.start, 8)+reader.read(0, 6, 26)+reader.read(1, 3, this.stop);
        double minimum = min(50000,firstway, secondway, thirdway, forthway);
        if(minimum == firstway){
            this.message = "ไปลงที่สถานี มักกะสัน จากนั้นเปลี่ยนไป mrt";
            this.time = timer.calculateTime(this.startline, this.start, 6)+timer.calculateTime(1, 8, this.stop);
        }else if(minimum == secondway){
            this.message = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี หมอชิต จากนั้นเปลี่ยนไป mrt";
            this.time = timer.calculateTime(this.startline, this.start, 8)+timer.calculateTime(0, 6, 1)+timer.calculateTime(1, 16, this.stop);
        }else if(minimum == thirdway){
            this.message = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี อโศก จากนั้นเปลี่ยนไป mrt";
            this.time = timer.calculateTime(this.startline, this.start, 8)+timer.calculateTime(0, 6, 12)+timer.calculateTime(1, 7, this.stop);
        }else if(minimum == forthway){
            this.message = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี ศาลาแดง จากนั้นเปลี่ยนไป mrt";
            this.time = timer.calculateTime(this.startline, this.start, 8)+timer.calculateTime(0, 6, 26)+timer.calculateTime(1, 3, this.stop);
        }
        return minimum;
    }
    
    public double airlinktomrt2() throws IOException{
        double firstway = reader.read(this.startline, this.start, 6)+reader.read(1, 8, 19)+reader.read(3, 1, this.stop);
        double secondway = reader.read(this.startline, this.start, 8)+reader.read(0, 6, 1)+reader.read(1, 16, 19)+reader.read(3, 1, this.stop);
        double thirdway = reader.read(this.startline, this.start, 8)+reader.read(0, 6, 12)+reader.read(1, 7, 19)+reader.read(3, 1, this.stop);
        double forthway = reader.read(this.startline, this.start, 8)+reader.read(0, 6, 26)+reader.read(1, 3, 19)+reader.read(3, 1, this.stop);
        double minimum = min(50000,firstway, secondway, thirdway, forthway);
        if(minimum == firstway){
            this.message = "ไปลงที่สถานี มักกะสัน จากนั้นเปลี่ยนไป mrt";
            this.time = timer.calculateTime(this.startline, this.start, 6)+timer.calculateTime(1, 8, 19)+timer.calculateTime(3, 1, this.stop);
        }else if(minimum == secondway){
            this.message = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี หมอชิต จากนั้นเปลี่ยนไป mrt นั่งไปต่อจนถึงสถานี เตาปูน จากนั้นเปลี่ยนไป สายสีม่วง";
            this.time = timer.calculateTime(this.startline, this.start, 8)+timer.calculateTime(0, 6, 1)+timer.calculateTime(1, 16, 19)+timer.calculateTime(3, 1, this.stop);
        }else if(minimum == thirdway){
            this.message = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี อโศก จากนั้นเปลี่ยนไป mrt นั่งไปต่อจนถึงสถานี เตาปูน จากนั้นเปลี่ยนไป สายสีม่วง";
            this.time = timer.calculateTime(this.startline, this.start, 8)+timer.calculateTime(0, 6, 12)+timer.calculateTime(1, 7, 19)+timer.calculateTime(3, 1, this.stop);
        }else if(minimum == forthway){
            this.message = "ไปลงที่สถานี พญาไท จากนั้นเปลี่ยนไป bts นั่งต่อไปจนถึงสถานี ศาลาแดง จากนั้นเปลี่ยนไป mrt นั่งไปต่อจนถึงสถานี เตาปูน จากนั้นเปลี่ยนไป สายสีม่วง";
            this.time = timer.calculateTime(this.startline, this.start, 8)+timer.calculateTime(0, 6, 26)+timer.calculateTime(1, 3, 19)+timer.calculateTime(3, 1, this.stop);
        }
        return minimum;
    }
    
    public void setTime(int time){
        this.time = time;
    }
    
    public int getTime(){
        return this.time;
    }
    
    public String getMessage(){
        return this.message;
    }
}
