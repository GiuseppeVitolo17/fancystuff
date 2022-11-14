/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop2018.esercitazione20181126;

import java.util.Iterator;

/**
 *
 * @author Gennaro
 */
public class TestStudent {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        StudentQueue sq = new StudentQueue(5);
        
        sq.add(new Student("Luca Rossi", "0123", 5, 23.7));
        sq.add(new Student("Maria Bianchi", "1341", 12, 27.9));
        sq.add(new Student("Roberto Verdi", "0192", 2, 26.3));
        sq.add(new Student("Serena Bruno", "4321", 6, 25.1));
        
        System.out.println("Size = " + sq.size());
        
 //       
        //Iterazione forward
        System.out.println("\n----  Forward iteration ----");
        Student s;
        Iterator<Student> i = sq.iterator();
        while(i.hasNext()){
            s = i.next();
            System.out.println(s);
        }
        
        //Iterazione backward
        System.out.println("\n---- Backward iteration ----");
        sq.setForward(false);
        for(Student a: sq)
            System.out.println(a);
       
        
        //Test fail-fast
        System.out.println("\n---- Test fail-fast ----");
        i = sq.iterator();
        while(i.hasNext()){
            s = i.next();
            sq.setForward(true);
            System.out.println(s);
        }
        
    }
    
}
