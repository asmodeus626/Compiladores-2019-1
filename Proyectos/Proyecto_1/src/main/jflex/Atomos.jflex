/********************************************************************************
**  @author Diana Montes                                                       **
**  @about Proyecto 1: Analizador léxico para p, subconjunto de Python.        **
*********************************************************************************/
package lexico;
import java.util.Stack;

%%
%{
public String resultado = ""; //Resultado del análisis léxico.
public int num_espacios = 0; //Cuenta el número de espacios.
public Stack<Integer> pila = new Stack();

public void cerrar(){
    System.out.print("\n"+resultado);
    System.exit(0);
}

public void reset(){ //Se va a ejecutar cada vez que se lea un caracter diferente al espacio en una nueva línea.
    if(yystate()==I && num_espacios>0){ //Si estaba en estado I (indentación).
        if(pila.isEmpty()){
            pila.push(num_espacios);
            resultado+="INDENTA("+num_espacios+")"; //Se imprime el número de indentación.
        }else{
            if(pila.peek()<num_espacios){
                pila.push(num_espacios);
                resultado+="INDENTA("+num_espacios+")"; //Se imprime el número de indentación.
            }else{
                while(!pila.isEmpty() && pila.peek()>num_espacios){
                    resultado+="DEINDENTA("+pila.pop()+")\n";
                }
            }
        }
    }else{
        if(yystate()==I){
            while(!pila.isEmpty()){
                resultado+="DEINDENTA("+pila.pop()+")\n";
            }
        }
    }

    yybegin(YYINITIAL);
    num_espacios = 0;
}
%}

%eof{

while(!pila.isEmpty()){
    resultado+="DEINDENTA("+pila.pop()+")\n";
}

System.out.print("\n"+resultado);
%eof}

%public
%class Alexico
%unicode
%standalone
%states I

BOOLEANO = "True" | "False"
PALABRA_RESERVADA = "and" | "or" | "not" | "while" | "if" | "else" | "elif" | "print"
IDENTIFICADOR = [a-zA-Z]([a-zA-Z]|_|[0-9])*
ENTERO = [1-9][0-9]* | 0
REAL =  ([1-9][0-9]* | 0)\.[0-9]*
CADENA = \" ~\"
OPERADOR = "+"|"-"|"*"|"/"|"%"|"<"|">"|">="|"<="|"="|"!"|"=="|"="
LINEA_VACIA = [ ]+\n | \t+\n | "\n"
SALTO = \r|\n|\r\n
COMENTARIO = #~\n

%%
{COMENTARIO}         {yybegin(I);}
{BOOLEANO}           {reset(); resultado+="BOOLEANO("+yytext()+")";}
{PALABRA_RESERVADA}  {reset(); resultado+="PALABRA_RESERVADA("+yytext()+")";}
{IDENTIFICADOR}      {reset(); resultado+="IDENTIFICADOR("+yytext()+")";}
{REAL} {reset();     resultado+="REAL("+yytext()+")";}
{ENTERO} {reset();   resultado+="ENTERO("+yytext()+")";}
{CADENA} {reset();   resultado+="CADENA("+yytext()+")";}
{OPERADOR} {reset(); resultado+="OPERADOR("+yytext()+")";}
\( | \)              {reset();}
":"                  {reset();        resultado+="SEPARADOR(:)";}
<I> {LINEA_VACIA} {}
<I> [ ] {num_espacios++;}
<I> \t {num_espacios+=4;}
{SALTO}              {resultado+="SALTO\n"; yybegin(I);}
<YYINITIAL> [ ] {}
<YYINITIAL> \t {}
.                    {System.out.print("ERROR DE SINTÁXIS, EL LEXEMA \""+yytext()+"\" NO FUE ENCONTRADO. :@\n");}
