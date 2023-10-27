package jide;

import java.util.Stack;
import java.util.HashMap;

public class Semantico {

    private String error;
    private int tablaSem[][] = {
        {0, 1, -1},
        {1, 1, -1},
        {-1, -1, -1}
    };
    private HashMap<String, HashMap> tablaSimbolos = new HashMap<>();
    private HashMap<Integer, String> tiposDatos = new HashMap<>();
    private Stack<Integer> pilaOp;

    public Semantico() {
        tiposDatos.put(0, "int");
        tiposDatos.put(1, "float");
        tiposDatos.put(2, "char");
        pilaOp.push(-2);
    }
}
