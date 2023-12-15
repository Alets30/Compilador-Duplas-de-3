package jide;

import java.util.Stack;
import java.util.HashMap;

public class Semantico {

    public String error = "";
    public int type;
    public String asign = "";
    public String middleCode = "";
    //Tabla de operaciones semánticas
    private final int semTable[][] = {
        {0, 1, -1},
        {1, 1, -1},
        {-1, -1, -1}
    };
    private final HashMap<String, HashMap> sTable = new HashMap<>();
    public final HashMap<Integer, String> datatypes = new HashMap<>();
    private final Stack<String> semStack;
    private final Stack<String> stackOp;
    private final Stack<String> expPosf;
    private final Stack<String> middleCodeStack;

    public Semantico() {
        semStack = new Stack();
        stackOp = new Stack();
        expPosf = new Stack();
        middleCodeStack = new Stack();
        datatypes.put(0, "int");
        datatypes.put(1, "float");
        datatypes.put(2, "char");
        semStack.push("$");
        stackOp.push("$");
        expPosf.push("$");
        middleCodeStack.push("$");
    }

    public void AddSymbol(String id, String value, int line) {
        HashMap<String, String> symbol = new HashMap<>();
        symbol.put("tipo", "" + type);
        symbol.put("valor", value);
        symbol.put("linea", "" + line);
        //System.out.println(id);
        sTable.put(id, symbol);
        middleCode += " " + id + ";\n";
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
                if (semStack.peek().equals("$") && stackOp.peek().equals("(")) {
                    return;
                }
                if (stackOp.peek().equals("$") || stackOp.peek().equals("(")) {
                    stackOp.push(token);
                } else {
                    System.out.println(stackOp);
                    do {
                        expPosf.push(stackOp.pop());
                    } while (stackOp.peek().equals("-") || stackOp.peek().equals("+"));
                    System.out.println(semTableResult);
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
                //System.out.println(stackOp.peek());
                if (stackOp.peek().equals("-") || stackOp.peek().equals("+") || stackOp.peek().equals("$") || stackOp.peek().equals("(")) {
                    stackOp.push(token);
                } else {
                    //System.out.println(semStack + " " + token);
                    do {
                        expPosf.push(stackOp.pop());
                    } while (!stackOp.peek().equals("-") && !stackOp.peek().equals("+"));
                    System.out.println(stackOp);

                    semTableResult = semTable[Integer.parseInt(semStack.pop())][Integer.parseInt(semStack.pop())];
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

                        //System.out.println(semTableResult);
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
                if (token.equals(";")) {
                    //System.out.println(expPosf);
                    FlipStack();
                    //System.out.println(expPosf);
                    //System.out.println(middleCodeStack);
                    GenerateMiddleCode();
                    System.out.println(middleCode);
                    //System.out.println(expPosf);
                }
                //System.out.println(semStack);
                //System.out.println(stackOp);
                //
                //System.out.println(middleCode);
                //System.out.println(middleCodeStack);
                break;
        }
    }

    public void AddExpPos(String token) {
        expPosf.push(token);
    }

    private int RecognizeNumber(String number) {
        return (number.matches("^[+-]?\\d*\\.\\d+([eE][+-]?\\d+)?$")) ? 1 : 0;
    }

    private void FlipStack() {
        while (!expPosf.peek().equals("$")) {
            System.out.println(middleCodeStack.push(expPosf.pop()));
        }
    }

    private void GenerateMiddleCode() {
        String middleCodeStackItem, variableString;
        while (!middleCodeStack.peek().equals("$")) {
            middleCodeStackItem = middleCodeStack.pop();
            //System.out.println(expPosf.size());
            if ("*+-/".contains(middleCodeStackItem)) {
                expPosf.pop();
                expPosf.pop();
                variableString = "V" + expPosf.size() + " = " + "V" + expPosf.size() + " " + middleCodeStackItem + " " + "V" + (expPosf.size() + 1) + "\n";
                middleCode += variableString;
                expPosf.push(variableString);
            } else {
                middleCode += "V" + expPosf.size() + " = " + middleCodeStackItem + "\n";
                expPosf.push(middleCodeStackItem);
            }
        }
        middleCode += asign + " = " + "V1";
    }
}
