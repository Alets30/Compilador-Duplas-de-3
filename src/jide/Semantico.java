package jide;

import java.util.Stack;
import java.util.HashMap;

public class Semantico {

    public String error = "";
    public int type;
    //Tabla de operaciones semánticas
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
                //System.out.println(semStack);
                break;
            default:
                if (sTable.containsKey(originalToken)) {
                    semStack.push("" + sTable.get(originalToken).get("tipo"));
                } else {
                    error += "Error semantico en la linea " + line + " el elemento " + originalToken + " no se ha declarado.\n";
                }
        }
    }

    public void AddOpStack(String token, int line) {
        int semTableResult = -1;
        switch (token) {
            case "+", "-":
                if (semStack.peek().equals("$") || semStack.peek().equals("(")) {
                    return;
                }
                if (token.equals("*") || token.equals("/") || stackOp.peek().equals("$") || stackOp.peek().equals("(")) {
                    stackOp.push(token);
                } else {
                    //System.out.println(semStack + " " + token);
                    stackOp.pop();
                    semTableResult = semTable[Integer.parseInt(semStack.pop())][Integer.parseInt(semStack.pop())];
                    if (semTableResult != -1) {
                        semStack.push("" + semTableResult);
                        stackOp.push(token);
                    } else {
                        error += "Error semantico en la linea " + line + " tipos de dato incompatibles.\n";
                    }
                }
                //System.out.println(stackOp);
                break;
            case "*", "/":
                if (semStack.peek().equals("$") || semStack.peek().equals("(")) {
                    return;
                }
                if (token.equals("-") || token.equals("+") || stackOp.peek().equals("$") || stackOp.peek().equals("(")) {
                    stackOp.push(token);
                } else {
                    //System.out.println(semStack + " " + token);
                    stackOp.pop();
                    semTableResult = semTable[Integer.parseInt(semStack.pop())][Integer.parseInt(semStack.pop())];
                    if (semTableResult != -1) {
                        semStack.push("" + semTableResult);
                        stackOp.push(token);
                    } else {
                        error += "Error semantico en la linea " + line + " tipos de dato incompatibles.\n";
                    }
                }
                break;
            case "(":
                stackOp.push(token);
                //System.out.println(stackOp);
                break;
            case ")", ";":
                while (!stackOp.peek().equals("$") && !semStack.peek().equals("$")) {
                    //System.out.println(stackOp);
                    stackOp.pop();
                    semTableResult = semTable[Integer.parseInt(semStack.pop())][Integer.parseInt(semStack.pop())];
                    if (semTableResult != -1) {
                        semStack.push("" + semTableResult);
                    } else {
                        error += "Error semantico en la linea " + line + " tipos de dato incompatibles.\n";
                        return;
                    }
                    if (stackOp.peek().equals("(")) {
                        stackOp.pop();
                        break;
                    }
                }
                System.out.println(semStack);
                System.out.println(stackOp);
                break;
        }
    }

    private int RecognizeNumber(String number) {
        return (number.matches("^[+-]?\\d*\\.\\d+([eE][+-]?\\d+)?$")) ? 1 : 0;
    }
}
