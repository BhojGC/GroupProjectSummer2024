/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author gcbho
 */
public class PasswordValidator {
    public boolean checkLength(String password){
        if(password.length() >= 8){
            return true;
        }
        return false;
    }
    
    public boolean checkUpperCase(String password){
        
        for(int i = 0 ; i < password.length(); i++){
            if(Character.isUpperCase(password.charAt(i))){
                return true;
            }            
        }
        return false;       
    }
    
    public boolean checkSpecialCharacter(String password){
        
        for(int i = 0; i < password.length(); i++){
            if(!Character.isLetterOrDigit(password.charAt(i))){
                return true;
            }
        }
        return false;
    }
    
}
