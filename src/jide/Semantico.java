package jide;

import java.util.Stack;
import java.util.HashMap;

public class Semantico {

    public String error = "";
    public int type;
    private int semTable[][] = {
        {0, 1, -1},
        {1, 1, -1},
        {-1, -1, -1}
    };
    private HashMap<String, HashMap> sTable = new HashMap<>();
    private HashMap<Integer, String> datatypes = new HashMap<>();
    private Stack<String> semStack;
    private Stack<String> stackOp;

    public Semantico() {
        semStack = new Stack();
        stackOp = new Stack();
        datatypes.put(0, "int");
        datatypes.put(1, "float");
        datatypes.put(2, "char");
        semStack.push("$");
        stackOp.push("$");
    }

    public void AddSymbol(String id, String value, int line) {
        HashMap<String, String> symbol = new HashMap<>();
        symbol.put("tipo", "" + type);
        symbol.put("valor", value);
        symbol.put("linea", "" + line);
        //System.out.println(id);
        sTable.put(id, symbol);
        //System.out.println(sTable);
    }

    public void AddSemStack(String token, String originalToken, int line) {
        switch (token) {
            case "num":
                semStack.push("" + RecognizeNumber(originalToken));
                System.out.println(semStack);
                break;
            default:
                if (sTable.containsKey(originalToken)) {
                    semStack.push("" + sTable.get(originalToken).get("tipo"));
                } else {
                    error += "Error semantico en la linea " + line + " el elemento " + token + " no se ha declarado.\n";
                }
        }
        //System.out.println(semStack);
    }

    private int RecognizeNumber(String number) {
        return (number.matches("^[+-]?\\d*\\.\\d+([eE][+-]?\\d+)?$")) ? 1 : 0;
    }
}
