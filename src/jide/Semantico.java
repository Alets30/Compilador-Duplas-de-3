package jide;

import java.util.Stack;
import java.util.HashMap;

public class Semantico {

    public String error = "";
    public int type;
    public String asign = "";
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
    private Stack<String> expPosf;

    public Semantico() {
        semStack = new Stack();
        stackOp = new Stack();
        expPosf = new Stack();
        datatypes.put(0, "int");
        datatypes.put(1, "float");
        datatypes.put(2, "char");
        semStack.push("$");
        stackOp.push("$");
        expPosf.push("$");
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
                expPosf.push(originalToken);
                //System.out.println(semStack);
                break;
            default:
                if (sTable.containsKey(originalToken)) {
                    semStack.push("" + sTable.get(originalToken).get("tipo"));
                    expPosf.push(originalToken);
                } else {
                    error += "Error semantico en la linea " + line + " el elemento " + originalToken + " no se ha declarado.\n";
                }
        }
    }

    public void AddOpStack(String token, int line) {
        int semTableResult = -1;
        //System.out.println(stackOp);
        switch (token) {
            case "+", "-":
                if (semStack.peek().equals("$") || semStack.peek().equals("(")) {
                    return;
                }
                if (stackOp.peek().equals("*") || stackOp.peek().equals("/") || stackOp.peek().equals("$") || stackOp.peek().equals("(")) {
                    stackOp.push(token);
                } else {
                    //System.out.println(semStack + " " + token);
                    expPosf.push(stackOp.pop());
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
                    stackOp.push(token);
                    return;
                }
                //System.out.println(stackOp.peek());
                if (stackOp.peek().equals("-") || stackOp.peek().equals("+") || stackOp.peek().equals("$") || stackOp.peek().equals("(")) {
                    stackOp.push(token);
                } else {
                    //System.out.println(semStack + " " + token);
                    expPosf.push(stackOp.pop());
                    semTableResult = semTable[Integer.parseInt(semStack.pop())][Integer.parseInt(semStack.pop())];
                    System.out.println(semTableResult);
                    if (semTableResult != -1) {
                        semStack.push("" + semTableResult);
                        System.out.println(semStack.size());
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
                    if (stackOp.peek().equals("(")) {
                        stackOp.pop();
                        break;
                    }
                    expPosf.push(stackOp.pop());
                    //System.out.println(semStack);

                    semTableResult = semTable[Integer.parseInt(semStack.pop())][Integer.parseInt(semStack.pop())];
                    if (semTableResult != -1) {
                        semStack.push("" + semTableResult);
                        
                        System.out.println(semTableResult);
                    } else {
                        error += "Error semantico en la linea " + line + " tipos de dato incompatibles.\n";
                        return;
                    }
                }
                if (semStack.size() == 2) {
                    if (semTable[Integer.parseInt("" + sTable.get(asign).get("tipo"))][Integer.parseInt(semStack.peek())] == -1) {
                        error += "Error de tipo en la línea " + line + " tipos de dato incompatibles.";
                    }
                }
                //System.out.println(semStack);
                //System.out.println(stackOp);
                System.out.println(expPosf);
                break;
        }
    }

    public void AddExpPos(String token) {
        expPosf.push(token);
    }

    private int RecognizeNumber(String number) {
        return (number.matches("^[+-]?\\d*\\.\\d+([eE][+-]?\\d+)?$")) ? 1 : 0;
    }
}
