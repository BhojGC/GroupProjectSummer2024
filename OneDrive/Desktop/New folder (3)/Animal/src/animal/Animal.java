/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package animal;

/**
 *
 * @author gcbho
 */
public class Animal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Dog d = new Dog();
        d.setName("lucky");
        d.setAge(1);
        
        System.out.println(d.getName());
    }
    
}
