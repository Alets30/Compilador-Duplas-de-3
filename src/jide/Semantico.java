package jide;

import java.util.Stack;
import java.util.HashMap;

public class Semantico {

    private String error;
    public int type;
    private int semTable[][] = {
        {0, 1, -1},
        {1, 1, -1},
        {-1, -1, -1}
    };
    private HashMap<String, HashMap> sTable = new HashMap<>();
    private HashMap<Integer, String> datatypes = new HashMap<>();
    private Stack<String> stackOp;

    public Semantico() {
        datatypes.put(0, "int");
        datatypes.put(1, "float");
        datatypes.put(2, "char");
        //stackOp.push("$");
    }

    public void AgregarSimbolo(String id, String value) {
        HashMap<String, String> symbol = new HashMap<>();
        symbol.put("tipo", "" + type);
        symbol.put("valor", value);
        //System.out.println(id);
        sTable.put(id, symbol);
        System.out.println(sTable);
    }
}
