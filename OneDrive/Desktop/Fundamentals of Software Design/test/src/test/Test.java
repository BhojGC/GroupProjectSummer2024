/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package test;

/**
 *
 * @author gcbho
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Persons p1 = new Persons();       
       p1.setName("Hana");
       p1.setAge(6);
       
       Persons p2 = new Persons();
       p2.setName("Bhoj");
       p2.setAge(39);
       
       Persons[] p3 =  new Persons[2];
       p3[0] = p1;
       p3[1] = p2;
       
       for(int i =0; i< p3.length; i++){
           System.out.println(p3[i].toString());
       
       
    }
    
}
}
