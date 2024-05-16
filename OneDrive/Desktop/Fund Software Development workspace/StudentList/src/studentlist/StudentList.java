/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package studentlist;

/**
 *
 * @author gcbho
 */
public class StudentList {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Student s1 =  new Student();
        s1.setName("Bhoj");
        s1.setStudentID(991494063);
        Student s2 = new Student();
        s2.setName("Hana");
        s2.setStudentID(991494064);
        Student s3 = new Student();
        s3.setName("Sienna");
        s3.setStudentID(991494065);
        
        Student [] studentsList = new Student[3];
        studentsList[0] = s1;
        studentsList[1] = s2;
        studentsList[2] = s3;
        
        for (int i =0; i< studentsList.length; i++){
            System.out.println(studentsList[i].getName()+" "+studentsList[i].getStudentID());
        }
    }
    
}
