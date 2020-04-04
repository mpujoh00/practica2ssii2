/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import DAO.CategoriasDAO;
import DAO.TrabajadorDAO;
import clases.Categorias;
import clases.Empresas;
import java.util.Scanner;
import clases.Trabajadorbbdd;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author maybeitsmica
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String res = validaLetra("44435629K");
        
        if(res == null){
            System.out.println("El NIF es correcto");
        }
        else if(res == "inc"){
            System.out.println("El formato del NIF es incorrecto");
        }
        else{
            System.out.println("El NIF no es correcto, la letra correcta es: " + res);
        }
        
    }
        
    private static String validaLetra(String nif){
        
        String res = null;
        String letra = null;
        
        if(nif.length() != 9){
            res = "inc";
        }
        else{
            String firstChar = nif.substring(0,1);
            String n = null;
            
            if(firstChar == "X"){
                n = "0";
                nif = n.concat(nif.substring(1));
            }
            else if(firstChar == "Y"){
                n = "1";
                nif = n.concat(nif.substring(1));
            }
            else if(firstChar == "Z"){
                n = "2";
                nif = n.concat(nif.substring(1));
            }
            
            String numNif = nif.substring(0,8);
            
            int num = Integer.parseInt(numNif);
            int resto = num%23;
            
            switch(resto){
                case 0:
                    letra = "T";
                    break;
                case 1:
                    letra = "R";
                    break;
                case 2:
                    letra = "W";
                    break;
                case 3:
                    letra = "A";
                    break;
                case 4:
                    letra = "G";
                    break;
                case 5:
                    letra = "M";
                    break;
                case 6:
                    letra = "Y";
                    break;
                case 7:
                    letra = "F";
                    break;
                case 8:
                    letra = "P";
                    break;
                case 9:
                    letra = "D";
                    break;
                case 10:
                    letra = "X";
                    break;
                case 11:
                    letra = "B";
                    break;
                case 12:
                    letra = "N";
                    break;
                case 13:
                    letra = "J";
                    break;
                case 14:
                    letra = "Z";
                    break;
                case 15:
                    letra = "S";
                    break;
                case 16:
                    letra = "Q";
                    break;
                case 17:
                    letra = "V";
                    break;
                case 18:
                    letra = "H";
                    break;
                case 19:
                    letra = "L";
                    break;
                case 20:
                    letra = "C";
                    break;
                case 21:
                    letra = "K";
                    break;
                case 22:
                    letra = "E";
                    break;
            }
            
            if(nif.substring(8) != letra){
                res = letra;
            }
        }
        
        return res;
    }

}