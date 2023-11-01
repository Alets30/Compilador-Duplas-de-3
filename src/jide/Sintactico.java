package jide;

import java.util.Stack;

public class Sintactico {

    private int ip, it;
    private boolean asign = false;
    private String result;
    public String error, originalToken;
    private String tnt[] = {"id", "num", "int", "float", "char", ",", ";", "+", "-", "*", "/", "=", "(", ")", "$", "P", "Tipo", "V", "A", "EXP", "E", "TERM", "T", "F"};
    private String productions[] = {"P'>P", "P>Tipo id V", "P>A", "Tipo>int", "Tipo>float", "Tipo>char", "V>, id V", "V>; P", "A>id = EXP ;", "EXP>+ TERM E", "EXP>- TERM E", "EXP>TERM E", "E>+ TERM E", "E>- TERM E", "E>vacia", "TERM>F T", "T>* F T", "T>/ F T", "T>vacia", "F>id", "F>num", "F>( EXP )"};
    private String table[][] = {
        {"I7", "", "I4", "I5", "I6", "", "", "", "", "", "", "", "", "", "", "I1", "I2", "", "I3", "", "", "", "", ""},
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "P0", "", "", "", "", "", "", "", "", ""},
        {"I8", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "P2", "", "", "", "", "", "", "", "", ""},
        {"P3", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"P4", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"P5", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", "", "", "", "", "I9", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "I11", "I12", "", "", "", "", "", "", "", "", "", "", "I10", "", "", "", "", "", ""},
        {"I18", "I19", "", "", "", "", "", "I14", "I15", "", "", "", "I20", "", "", "", "", "", "", "I13", "", "I16", "", "I17"},
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "P1", "", "", "", "", "", "", "", "", ""},
        {"I21", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"I7", "", "I4", "I5", "I6", "", "", "", "", "", "", "", "", "", "", "I22", "I2", "", "I3", "", "", "", "", ""},
        {"", "", "", "", "", "", "I23", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"I18", "I19", "", "", "", "", "", "", "", "", "", "", "I20", "", "", "", "", "", "", "", "", "I24", "", "I17"},
        {"I18", "I19", "", "", "", "", "", "", "", "", "", "", "I20", "", "", "", "", "", "", "", "", "I25", "", "I17"},
        {"", "", "", "", "", "", "P14", "I27", "I28", "", "", "", "", "P14", "", "", "", "", "", "", "I26", "", "", ""},
        {"", "", "", "", "", "", "P18", "P18", "P18", "I30", "I31", "", "", "P18", "", "", "", "", "", "", "", "", "I29", ""},
        {"", "", "", "", "", "", "P19", "P19", "P19", "P19", "P19", "", "", "P19", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "P20", "P20", "P20", "P20", "P20", "", "", "P20", "", "", "", "", "", "", "", "", "", ""},
        {"I18", "I19", "", "", "", "", "", "I14", "I15", "", "", "", "", "", "", "", "", "", "", "I32", "", "I16", "", "I17"},
        {"", "", "", "", "", "I11", "I12", "", "", "", "", "", "", "", "", "", "", "I33", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "P7", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "P8", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "P14", "I27", "I28", "", "", "", "", "P14", "", "", "", "", "", "", "I34", "", "", ""},
        {"", "", "", "", "", "", "P14", "I27", "I28", "", "", "", "", "P14", "", "", "", "", "", "", "I35", "", "", ""},
        {"", "", "", "", "", "", "P11", "", "", "", "", "", "", "P11", "", "", "", "", "", "", "", "", "", ""},
        {"I18", "I19", "", "", "", "", "", "", "", "", "", "", "I20", "", "", "", "", "", "", "", "", "I36", "", "I17"},
        {"I18", "I19", "", "", "", "", "", "", "", "", "", "", "I20", "", "", "", "", "", "", "", "", "I37", "", "I17"},
        {"", "", "", "", "", "", "P15", "P15", "P15", "", "", "", "", "P15", "", "", "", "", "", "", "", "", "", ""},
        {"I18", "I19", "", "", "", "", "", "", "", "", "", "", "I20", "", "", "", "", "", "", "", "", "", "", "I38"},
        {"I18", "I19", "", "", "", "", "", "", "", "", "", "", "I20", "", "", "", "", "", "", "", "", "", "", "I39"},
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "I40", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "P6", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "P9", "", "", "", "", "", "", "P9", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "P10", "", "", "", "", "", "", "P10", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "P14", "I27", "I28", "", "", "", "", "P14", "", "", "", "", "", "", "I41", "", "", ""},
        {"", "", "", "", "", "", "P14", "I27", "I28", "", "", "", "", "P14", "", "", "", "", "", "", "I42", "", "", ""},
        {"", "", "", "", "", "", "P18", "P18", "P18", "I30", "I31", "", "", "P18", "", "", "", "", "", "", "", "", "I43", ""},
        {"", "", "", "", "", "", "P18", "P18", "P18", "I30", "I31", "", "", "P18", "", "", "", "", "", "", "", "", "I44", ""},
        {"", "", "", "", "", "", "P21", "P21", "P21", "P21", "P21", "", "", "P21", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "P12", "", "", "", "", "", "", "P12", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "P13", "", "", "", "", "", "", "P13", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "P16", "P16", "P16", "", "", "", "", "P16", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "P17", "P17", "P17", "", "", "", "", "P17", "", "", "", "", "", "", "", "", "", ""}};
    private Stack<String> stack;
    private Stack<String> auxStack;
    private Semantico sem = new Semantico();

    public Sintactico() {
        stack = new Stack();
        auxStack = new Stack();
        stack.push("$");
        stack.push("I0");
        auxStack.push("z");
        result = "$ I0 \n";
        error = "";
    }

    public void Analisis(String token, int linea) {
        BuscarElemento(token, linea);
    }

    private void BuscarElemento(String token, int linea) {
        //System.out.println(token);
        for (it = 0; it < tnt.length; it++) {
            if (tnt[it].equals(token)) {
                //System.out.println(table[Integer.parseInt(stack.peek().split("I")[1])][it]);
                if (table[Integer.parseInt(stack.peek().split("I")[1])][it].equals("")) {
                    //result += " error ";
                    error += "Error sintáctico en la línea " + linea + " esperaba " + Error(Integer.parseInt(stack.peek().split("I")[1])) + "\n";
                    return;
                } else if (table[Integer.parseInt(stack.peek().split("I")[1])][it].startsWith("I")) {
                    //System.out.println(token);
                    Desplazamiento(token, table[Integer.parseInt(stack.peek().split("I")[1])][it]);
                } else {
                    Reduccion(token, Integer.parseInt(table[Integer.parseInt(stack.peek().split("I")[1])][it].split("P")[1]), linea);
                }
            }
        }
    }

    private void Desplazamiento(String token, String estado) {
        stack.push(token);
        stack.push(estado);
        switch (estado) {
            case "I4":
                sem.type = 0;
                break;
            case "I5":
                sem.type = 1;
                break;
            case "I6":
                sem.type = 2;
                break;
            case "I8", "I21":
                sem.AgregarSimbolo(originalToken, "");
                break;
        }
        AñadirResultado();
        //System.out.println(result);
    }

    private void Reduccion(String token, int production, int line) {
        int state;
        while (!productions[production].split(">")[1].equals("vacia")) {
            //System.out.println(productions[production].split(">")[1].split(" ")[0]);
            if (stack.pop().equals(productions[production].split(">")[1].split(" ")[0])) {
                break;
            }
        }
        for (ip = 0; ip < tnt.length; ip++) {
            if (tnt[ip].equals(productions[production].split(">")[0])) {
                state = Integer.parseInt(stack.peek().split("I")[1]);
                if (table[state][ip].equals("")) {
                    return;
                }
                stack.push(productions[production].split(">")[0]);
                //System.out.println(productions[production]);
                stack.push(table[state][ip]);
                AñadirResultado();
                //System.out.println(result);
                BuscarElemento(token, line);
            }
        }
        if (productions[production].equals(productions[0])) {
            result += productions[0] + " Acepta la cadena";
        }
    }

    private void AñadirResultado() {
        while (!stack.peek().equals("$")) {
            auxStack.push(stack.pop());
        }
        result += "$";
        while (!auxStack.peek().equals("z")) {
            result += " " + stack.push(auxStack.pop());
        }
        result += "\n";
    }

    public String Resultado() {
        return result;
    }

    public String Error(int I) {
        int i, it;
        String expected = "";
        for (i = 0; i <= 15; i++) {
            if (!table[I][i].equals("")) {
                expected += tnt[i] + ", ";
            }
        }
        return expected;
    }
}
