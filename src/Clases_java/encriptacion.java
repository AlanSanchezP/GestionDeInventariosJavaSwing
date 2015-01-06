/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases_java;

/**
 *
 * @author Familia
 */
public class encriptacion {
    
    private char abcoriginal[];
    private char abccifrado[];
    
    public encriptacion(){
        abcoriginal = new char[]{'A','a','B','b','C','c','D','d',
                       'E','e','F','f','G','g','H','h',
                       'I','i','J','j','K','k','L','l',
                       'M','m','N','n','Ñ','ñ','O','o',
                       'P','p','Q','q','R','r','S','s',
                       'T','t','U','u','V','v','W','w',
                       'X','x','Y','y','Z','z','0','1',
                       '2','3','4','5','6','7','8','9'}; 
        abccifrado  = new char[]{'T','t','B','b','K','k','X','x',
                       'U','u','C','c','M','m','Y','y',
                       'L','l','D','d','Ñ','ñ','Z','z',
                       'I','i','E','e','Q','q','P','p',
                       'F','f','R','r','A','a','G','g',
                       'S','s','N','n','H','h','V','v',
                       'O','o','J','j','W','w','6','8',
                       '0','3','1','2','9','5','7','4'}; 
        
    }
    
    public String cifrar(String pass){
        String original = pass;
        String nuevo = "";
        for(int i = 0; i < original.length(); i++){
            int no_coincide = 1;
            for(int o = 0; o < 64; o++){
                if(original.charAt(i) == abcoriginal[o]){
                    nuevo += abccifrado[o];
                    no_coincide = 0;
                    break;
                }
            }
            if(no_coincide == 1){
                nuevo += original.charAt(i);
            }
        }
    
        return nuevo;
    }
    
    public String descifrar(String pass){
        String cifrado = pass;
        String original = "";
        for(int i = 0; i < cifrado.length(); i++){
            int no_coincide = 1;
            for(int o = 0; o < 64; o++){
                if(cifrado.charAt(i) == abccifrado[o]){
                    original += abcoriginal[o];
                    no_coincide = 0;
                    break;
                }
            }
            if(no_coincide == 1){
                original += cifrado.charAt(i);
            }
        }
        return original;
    }
}
