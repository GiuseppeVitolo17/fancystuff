/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seconda.prova.corretta;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antonioferrigno
 */
public class OTPThread implements Runnable{
    
    private Random number = new Random(0);
    private int valore=0;

    public int getValore() {
        return valore;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }
    
    @Override
    public void run() {
        while(true){
            try(PrintWriter r = new PrintWriter(new BufferedWriter(new FileWriter("otp.txt")))){
                setValore(number.nextInt(500));
                r.write("Il codice di sblocco OTP è: " + valore);
            } catch (IOException ex) {
                Logger.getLogger(FXMLMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try{
                Thread.sleep(20000);
            }catch(InterruptedException ex){
                System.out.println(ex);
            }
        }
    }
    
}
