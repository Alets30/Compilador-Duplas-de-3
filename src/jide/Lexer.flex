package jide;
import java.io.*;
import static jide.Tokens.*;


%%
%class Lexer
%type Tokens
%line
%column
L=[a-zA-Z_]+
D=-?[0-9]+|-?(([0-9]+[.]?[0-9]*)|([.][0-9]+))(e[+-]?[0-9]+)?
CA=\"(\\.|[^\"])*\"
CAR='([^'\\]|\\\\[btnfr"'\\"\\\\])'
espacio=[ ]+
espa=[\t]+
esp=[\r]+
salto=[\n]
%{
    public String lexeme;
Analisis c=new Analisis();
int estado = 0;
int nu=0;
%}
%%


"//" .* { /* Ignore */ }
"/*" [^*] ~"*/" | "/*" "*"+ "/" { /* Ignore */ }
{espacio} {/*Ignore*/}
{espa} {/*Ignore*/}
{esp} {/*Ignore*/}
{salto} {/*Ignore*/}
<YYINITIAL> "programa" {estado = 1;c.linea=yyline; lexeme=yytext();return programa;}
<YYINITIAL> "procedimiento" {estado = 2;c.linea=yyline; lexeme=yytext();return procedimiento;}
<YYINITIAL> "funcion" {estado = 3;c.linea=yyline; lexeme=yytext();return funcion;}
<YYINITIAL> "ent" {c.linea=yyline; lexeme=yytext();return ent;}
<YYINITIAL> "flot" {c.linea=yyline; lexeme=yytext();return flot;}
<YYINITIAL> "car" {c.linea=yyline; lexeme=yytext();return car;}
<YYINITIAL> "cad" {c.linea=yyline; lexeme=yytext();return cad;}
<YYINITIAL> "booleano" {c.linea=yyline; lexeme=yytext();return booleano;}
<YYINITIAL> "imprimir" {c.linea=yyline; lexeme=yytext();return imprimir;}
<YYINITIAL> "leer" {c.linea=yyline; lexeme=yytext();return leer;}
<YYINITIAL> "si" {c.linea=yyline; lexeme=yytext();return si;}
<YYINITIAL> "entonces" {c.linea=yyline; lexeme=yytext();return entonces;}
<YYINITIAL> "sn" {c.linea=yyline; lexeme=yytext();return sn;}
<YYINITIAL> "para" {c.linea=yyline; lexeme=yytext();return para;}
<YYINITIAL> "incremento" {c.linea=yyline; lexeme=yytext();return incremento;}
<YYINITIAL> "decremento" {c.linea=yyline; lexeme=yytext();return decremento;}
<YYINITIAL> "paso" {c.linea=yyline; lexeme=yytext();return paso;}
<YYINITIAL> "verdadero" {c.linea=yyline; lexeme=yytext();return verdadero;}
<YYINITIAL> "falso" {c.linea=yyline; lexeme=yytext();return falso;}
<YYINITIAL> ";" {c.linea=yyline; lexeme=yytext();return  punto_coma;}
<YYINITIAL> "." {c.linea=yyline; lexeme=yytext();return punto;}
<YYINITIAL> "," {c.linea=yyline; lexeme=yytext();return coma;}
<YYINITIAL> ":" {c.linea=yyline; lexeme=yytext();return dos_puntos;}
<YYINITIAL> "(" {c.linea=yyline; lexeme=yytext();return parentesis_abre;}
<YYINITIAL> ")" {c.linea=yyline; lexeme=yytext();return parentesis_cierra;}
<YYINITIAL> "[" {nu=1;c.linea=yyline; lexeme=yytext();return corchete_abre;}
<YYINITIAL> "]" {c.linea=yyline; lexeme=yytext();return corchete_cierra;}
<YYINITIAL> "{" {c.linea=yyline; lexeme=yytext();return llave_abre;}
<YYINITIAL> "}" {c.linea=yyline; lexeme=yytext();return llave_cierra;}
<YYINITIAL> "=" {c.linea=yyline; lexeme=yytext();return igual;}
<YYINITIAL> "+" {c.linea=yyline; lexeme=yytext();return mas;}
<YYINITIAL> "-" {c.linea=yyline; lexeme=yytext();return menos;}
<YYINITIAL> "++" {c.linea=yyline; lexeme=yytext();return incre;}
<YYINITIAL> "--" {c.linea=yyline; lexeme=yytext();return decre;}
<YYINITIAL> "/" {c.linea=yyline; lexeme=yytext();return division;}
<YYINITIAL> "*" {c.linea=yyline; lexeme=yytext();return multiplicacion;}
<YYINITIAL> "%" {c.linea=yyline; lexeme=yytext();return modulo;}
<YYINITIAL> "<" {c.linea=yyline; lexeme=yytext();return menor_que;}
<YYINITIAL> ">" {c.linea=yyline; lexeme=yytext();return mayor_que;}
<YYINITIAL> "<=" {c.linea=yyline; lexeme=yytext();return menorigual;}
<YYINITIAL> ">=" {c.linea=yyline; lexeme=yytext();return mayorigual;}
<YYINITIAL> "!" {c.linea=yyline; lexeme=yytext();return negacion;}
<YYINITIAL> "!=" {c.linea=yyline; lexeme=yytext();return diferente_de;}
<YYINITIAL> "==" {c.linea=yyline; lexeme=yytext();return comparacion;}
<YYINITIAL> "||" {c.linea=yyline; lexeme=yytext();return or;}
<YYINITIAL> "&&" {c.linea=yyline; lexeme=yytext();return and;}
<YYINITIAL> {D} {c.linea=yyline; lexeme=yytext();return Numero;}
<YYINITIAL> {L}({L}{D})* {if (estado == 1) { // Si se encontró la palabra clave "Programa"
        c.linea = yyline;
        lexeme = yytext();
        estado = 0;
        return IDP; 
    }else if(estado == 2){
        c.linea = yyline;
        lexeme = yytext();
        estado = 0;
        return IDK; 
    }else if(estado == 3){
        c.linea = yyline;
        lexeme = yytext();
        estado = 0;
        return IDF; 
    } else { // Si no se ha encontrado "Programa"
        c.linea = yyline;
        lexeme = yytext();
        return ID; // Token para identificador normal
    }
}
<YYINITIAL> "f."{L}({L}{D})* {c.linea=yyline; lexeme=yytext();return IDF;}
<YYINITIAL> "p."{L}({L}{D})* {c.linea=yyline; lexeme=yytext();return IDK;}
<YYINITIAL> {CA}+ {c.linea=yyline; lexeme=yytext();return cadena;}
<YYINITIAL> {CAR}+ {c.linea=yyline; lexeme=yytext();return caracter;}
. {c.linea=yyline; lexeme=yytext();return Error;}

/*Este código es un archivo de especificación Flex para un lexer (analizador léxico) en Java. El lexer se utiliza para reconocer tokens en un programa escrito en un lenguaje de programación específico.
 En este caso, el lexer está diseñado para reconocer tokens en un lenguaje de programación llamado JIDE.
La especificación comienza definiendo algunos patrones, utilizando expresiones regulares, que se utilizarán para reconocer tokens. 
Por ejemplo, el patrón L se define como un conjunto de caracteres que incluye letras mayúsculas y minúsculas y guiones bajos, mientras que el patrón D se define como un
conjunto de caracteres que incluye dígitos del 0 al 9. Otros patrones se definen para reconocer flotantes, cadenas de caracteres y caracteres.
Luego, se define una serie de reglas de producción, que se utilizan para definir cómo se reconocen los tokens en el código fuente. 
Cada regla consta de una expresión regular y un bloque de acción que se ejecuta cuando se reconoce la expresión regular. La acción generalmente establece el valor del atributo lexeme y devuelve el token correspondiente.
Por ejemplo, una regla para reconocer la palabra clave "ent" se define como sigue:
<YYINITIAL> "ent" {c.linea=yyline; lexeme=yytext();return RESERVADA;}
La regla comienza con <YYINITIAL>, que indica que se debe aplicar en el estado inicial del lexer. 
Luego, se especifica la cadena "ent", que se utiliza para reconocer la palabra clave. El bloque de acción establece el valor del atributo lexeme en el texto de entrada y devuelve el token RESERVADA.
El archivo de especificación también contiene algunas reglas para ignorar comentarios, espacios en blanco y caracteres de salto de línea.
En general, estos se utilizan para descartar cualquier texto que no sea relevante para el análisis léxico del programa.
En resumen, este archivo de especificación es un componente clave de un lexer diseñado para reconocer tokens en un programa escrito en el lenguaje de programación JIDE.
*/