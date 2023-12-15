/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jide;

import java.io.File;

/**
 *
 * @author juanp
 */
public class JIDE {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String ruta = "C:\\Users\\Ettos\\OneDrive\\Documents\\Alejandro\\7mo Semestre\\Lenguajes y automatas II\\Compilador Felices los 4\\src\\jide\\Alexer.flex";
        ide IDE = new ide();

        generarLexer(ruta);
    }

    public static void generarLexer(String ruta) {
        File archivo = new File(ruta);
        //System.out.println(archivo);
        JFlex.Main.generate(archivo);
    }

}
