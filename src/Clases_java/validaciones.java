/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases_java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Familia
 */
public class validaciones {
    public validaciones(){
        
    }
    
    
    public static boolean validaEmail (String email) {
        Pattern p = Pattern.compile("[-\\w\\.]+@[\\.\\w]+\\.\\w+");
        Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean validaNombres (String nombre) {
        Pattern p = Pattern.compile("[a-zA-ZñÑ\\s]{2,50}");
        Matcher m = p.matcher(nombre);
        return m.matches();
    }
    
    public static boolean validaPassword (String password) {
        return password.length() >= 6;
    }
    
    public static boolean validaNumeros(String avalidar){
        if (!(avalidar.length() >= 1)){
            return false;
        }
        for(int i = 0; i < avalidar.length(); i++){
            if(!((int)avalidar.charAt(i) >= 0 && (int)avalidar.charAt(i) <= 9)){
                return false;
            }
        }
        return true;
    }
    
    public static boolean validaLetras(String avalidar){
        if (!(avalidar.length() >= 1)){
            return false;
        }
        for(int i = 0; i < avalidar.length(); i++){
            if(!(avalidar.charAt(i) >= 65 && avalidar.charAt(i) <= 90) || !(avalidar.charAt(i) >= 97 && avalidar.charAt(i) <= 122)){
                return false;
            }
        }
        return true;
    }
    
    public static boolean validaURL(String url){
        Pattern p = Pattern.compile(" /^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,3})$/");
        Matcher m = p.matcher(url);
        return m.matches();
    }
}
