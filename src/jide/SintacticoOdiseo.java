package jide;

import java.util.Stack;

public class SintacticoOdiseo {

    private int is, itf;
    private String resultado;
    public String error;
    /* private String simbolos[] = {"(",")","ID","Numero","car","cad","+","-","*","/","%","$"};
    private String terminales[] = {"E", "E'", "T", "T'", "F"};
    private String tabla[][] = {{"T E'","saltar","T E'","T E'","T E'","T E'","saltar","saltar","saltar","saltar","saltar","sacar"},
                                {"saltar","ϵ","saltar","saltar","saltar","saltar","+ T E'","- T E'","ϵ","ϵ","ϵ","ϵ"},
                                {"F T'","saltar","F T'","F T'","F T'","F T'","saltar","saltar","saltar","saltar","saltar","sacar"},
                                {"saltar","ϵ","saltar","saltar","saltar","saltar","ϵ","ϵ","* F T'","/ F T'","% F T'","ϵ"},
                                {"( E )","saltar","ID","Numero","car","cad","saltar","saltar","saltar","saltar","saltar","sacar"}};*/
    private String simbolos[] = {"programa", "IDP", "procedimiento", "IDK", "funcion", "IDF", "{", "}", "ent", "flot", "cad", "car", "booleano", "imprimir", "leer", "si", "entonces", "sn", "para", "incremento", "decremento", "paso", "(", ")", "ID", "Numero", "caracter", "cadena", "verdadero", "falso", "+", "-", "*", "/", "%", "<", ">", "D", "N", "M", "==", "&&", "||", "!", "=", ":", ";", ",", "$"};
    private String terminales[] = {"Program", "modulo", "bloque", "lista_arg", "siglista", "tipo", "Sentencias", "SenSimple", "Dec", "restid", "Asig", "lis_para", "Sigpara", "SenComp", "Si", "restsi", "Para", "restpara", "Variable", "L", "L'", "R", "R'", "E", "E'", "T", "T'", "F"};

    private String tabla[][] = {{"programa IDP ; Dec modulo bloque", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar"},
    {"saltar", "saltar", "procedimiento IDK ( lista_arg ) Dec bloque modulo", "saltar", "funcion IDF ( lista_arg ) : tipo ; Dec bloque modulo", "saltar", "saltar", "vacia", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "{ Sentencias }", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "saltar", "saltar", "saltar", "Sacar", "Sacar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "ent ID siglista", "flot ID siglista", "cad ID siglista", "car ID siglista", "booleano ID siglista", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", ", lista_arg", "saltar",},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "ent", "flot", "cad", "car", "booleano", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "saltar"},
    {"saltar", "saltar", "Sacar", "SenSimple Sentencias", "Sacar", "saltar", "saltar", "vacia", "saltar", "saltar", "saltar", "saltar", "saltar", "SenSimple Sentencias", "saltar", "SenComp Sentencias", "saltar", "saltar", "SenComp Sentencias", "saltar", "saltar", "saltar", "Sacar", "saltar", "SenSimple Sentencias", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "Sacar"},
    {"saltar", "saltar", "saltar", "IDK ( lis_para ) ;", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "imprimir ( lis_para ) ;", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Asig ;", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar"},
    {"saltar", "saltar", "vacia", "saltar", "vacia", "saltar", "vacia", "saltar", "tipo ID restid ; Dec", "tipo ID restid ; Dec", "tipo ID restid ; Dec", "tipo ID restid ; Dec", "tipo ID restid ; Dec", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar"},
    {"saltar", "saltar", "vacia", "saltar", "vacia", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia", ", ID restid", "saltar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "ID = L", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "L Sigpara", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "L Sigpara", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "L Sigpara", "vacia", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "L Sigpara", "saltar", "saltar", "saltar", "saltar", "Sacar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", ", L Sigpara", "saltar", "saltar", "saltar", ", L Sigpara", "Sacar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Si", "saltar", "saltar", "Para", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "si ( L ) entonces bloque restsi", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "saltar", "saltar", "saltar", "sn bloque", "vacia", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "para ( ID = L ) Variable ( L ) bloque restpara", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar"},
    {"saltar", "saltar", "Sacar", "saltar", "Sacar", "saltar", "saltar", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "saltar", "vacia", "saltar", "saltar", "vacia", "saltar", "saltar", "paso ( L )", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "incremento", "decremento", "saltar", "Sacar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "R L'", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "R L'", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "R L'", "saltar", "R L'", "R L'", "R L'", "R L'", "R L'", "R L'", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "! L", "saltar", "saltar", "Sacar", "saltar", "Sacar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "&& R L'", "|| R L'", "vacia", "saltar", "saltar", "vacia", "Sacar", "vacia"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "E R'", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "E R'", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "E R'", "saltar", "E R'", "E R'", "E R'", "E R'", "E R'", "E R'", "saltar", "saltar", "saltar", "saltar", "saltar", "E R'", "E R'", "E R'", "E R'", "E R'", "E R'", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "Sacar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia", "vacia", "vacia", "vacia", "vacia", "< E", "E >", "D E", "N E", "M E", "== E", "vacia", "vacia", "vacia", "saltar", "saltar", "vacia", "Sacar", "vacia"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "T E'", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "T E'", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "T E'", "saltar", "T E'", "T E'", "T E'", "T E'", "T E'", "T E'", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "Sacar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "+ T E'", "- T E'", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "saltar", "saltar", "vacia", "Sacar", "vacia"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "F T'", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "F T'", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "F T'", "Sacar", "F T'", "F T'", "F T'", "F T'", "F T'", "F T'", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "Sacar"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "vacia", "vacia", "* F T'", "/ F T'", "% F T'", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "vacia", "saltar", "saltar", "vacia", "Sacar", "vacia"},
    {"saltar", "saltar", "saltar", "saltar", "saltar", "IDF ( lis_para )", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "leer ( lis_para )", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "( L )", "saltar", "ID", "Numero", "caracter", "cadena", "verdadero", "falso", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "saltar", "Sacar", "saltar", "Sacar"}};
    private Stack<String> pila;
    private Stack<String> pilaux;

    public SintacticoOdiseo() {
        pila = new Stack();
        pilaux = new Stack();
        pila.push("$");
        pila.push("Program");
        pilaux.push("z");
        resultado = "$ Program\n";
        error = "";
    }

    public void Analisis(String token, int linea) {
        for (is = 0; is < simbolos.length; is++) {
            if (simbolos[is].equals(token)) {
                this.Accion(token, linea);
            }
        }
    }

    private void Accion(String token, int linea) {
        while (true) {
            for (itf = 0; itf < terminales.length; itf++) {
                if (pila.peek().equals(terminales[itf])) {
                    if (tabla[itf][is].contains(" ")) {
                        pila.pop();
                        for (String cadena : tabla[itf][is].split(" ")) {
                            pilaux.push(cadena);
                        }
                        añadirResultado();
                        break;
                    } else {
                        if (!tabla[itf][is].equals("saltar") && !tabla[itf][is].equals("Sacar")) {
                            pila.pop();
                            pila.push(tabla[itf][is]);
                            if (!pila.peek().equals("vacia")) {
                                añadirResultado();
                            }
                        }
                        if (pila.peek().equals("vacia")) {
                            pila.pop();
                            añadirResultado();
                            break;
                        } else if (tabla[itf][is].equals("saltar")) {
                            error += (pila.peek().contains("'")) ? "Error sintáctico en la linea " + linea + " esperaba operador.\n" : "Error sintáctico en la linea " + linea + " esperaba operando.\n";
                            return;
                        } else if (tabla[itf][is].equals("Sacar")) {
                            error += (pila.peek().contains("'")) ? "Error sintáctico en la linea " + linea + " esperaba operador " + pila.pop() + ".\n" : "Error sintácticos en la linea " + linea + " esperaba operando " + pila.pop() + ".\n";
                            añadirResultado();
                            break;
                        }
                    }
                }
            }
            if (itf >= terminales.length) {
                for (itf = 0; itf < simbolos.length; itf++) {
                    if (pila.peek().equals(simbolos[itf])) {
                        if (pila.peek().equals("$") && !token.equals("$")) {
                            error += (pila.peek().contains("'")) ? "Error sintáctico en la linea " + linea + " esperaba operador $.\n" : "Error sintáctico en la linea " + linea + " esperaba operando $.\n";
                            return;
                        }
                        if (pila.peek().equals(token)) {
                            if (pila.peek().equals("$")) {
                                return;
                            } else {
                                pila.pop();
                            }
                            añadirResultado();
                            return;
                        } else {
                            error += "Error sintáctico en la linea " + linea + " esperaba " + pila.pop() + "\n";
                            añadirResultado();
                        }
                    }
                }
            }
        }
    }

    private void añadirResultado() {
        while (!pila.peek().equals("$")) {
            pilaux.push(pila.pop());
        }
        resultado += "$";
        while (!pilaux.peek().equals("z")) {
            resultado += " " + pila.push(pilaux.pop());
        }
        resultado += "\n";
    }

    public String Resultado() {
        return resultado;
    }
}
