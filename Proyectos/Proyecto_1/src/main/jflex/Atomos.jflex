/********************************************************************************
**  @author Diana Montes                                                       **
**  @about Proyecto 1: Analizador léxico para p, subconjunto de Python.        **
*********************************************************************************/
package lexico;

%%
%{
public String resultado; //Resultado del análisis léxico.
%}

%public
%class Alexico
%unicode
%standalone

BOOLEANO = "True" | "False"
IDENTIFICADOR = [a-zA-Z]([a-zA-Z]|_|[0-9])*
ENTERO = [1-9][0-9]* | 0
REAL =  ([1-9][0-9]* | 0)\.[0-9]*
CADENA = \" ~\"
PALABRA_RESERVADA = "and" | "or" | "not" | "while" | "if" | "else" | "elif" | "print"
OPERADOR = "+"|"-"|"*"|"/"|"%"|"<"|">"|">="|"<="|"="|"!"|"=="|"="

%%
#.*       {System.out.print("COMENTARIO");}
{BOOLEANO} {System.out.print("BOOLEANO("+yytext()+")");}
{IDENTIFICADOR}  {System.out.print("IDENTIFICADOR("+yytext()+")");}
{REAL} {System.out.print("REAL("+yytext()+")");}
{ENTERO} {System.out.print("ENTERO("+yytext()+")");}
{CADENA} {System.out.print("CADENA("+yytext()+")");}
{PALABRA_RESERVADA} {System.out.print("PALABRA_RESERVADA("+yytext()+")");}
{OPERADOR} {System.out.print("OPERADOR("+yytext()+")");}