package com.mycompany.proyecto_2;

import java.util.Stack;
import java.io.FileWriter;
import java.io.BufferedWriter;

%%

%{
//Aquí empieza el código de Java
public String resultado = ""; //Resultado del análisis léxico.
public int num_espacios = 0; //Cuenta el número de espacios.
public Stack<Integer> pila = new Stack();
public int num_linea = 1;
public boolean dperr = false;
private Parser yyparser;

public Atomos(java.io.Reader r, Parser yyparser) {
  this(r);
  this.yyparser = yyparser;
}

public void agrRes(String s){
    if(!dperr){
        resultado+=s;
    }
}

public void reset(){ //Se va a ejecutar cada vez que se lea un caracter diferente al espacio en una nueva línea.
    if(yystate()==I && num_espacios>0){ //Si estaba en estado I (indentación).
        if(pila.isEmpty()){
            pila.push(num_espacios);
            agrRes("INDENTA("+num_espacios+")"); //Se imprime el número de indentación.
        }else{
            if(pila.peek()<num_espacios){
                pila.push(num_espacios);
                agrRes("INDENTA("+num_espacios+")"); //Se imprime el número de indentación.
            }else{
                while(!pila.isEmpty() && pila.peek() > num_espacios){
                    agrRes("DEINDENTA("+pila.pop()+")\n");
                }

                if(pila.isEmpty() || pila.peek() < num_espacios ){ //En este punto el tope de la pila debería ser igual a num_espacios.
                    agrRes("Error de indentación en la línea "+num_linea);
                    dperr = true;
                }
            }
        }
    }else{
        if(yystate()==I){ //Si el número de espacios era 0
            while(!pila.isEmpty()){
                agrRes("DEINDENTA("+pila.pop()+")\n");
            }
        }
    }

    yybegin(YYINITIAL);
    num_espacios = 0;
}

//Función que cambia el operador por un token para el parser
int operador(String op){
    switch(op){
        
    }
}

//Aquí termina el código de Java
%}

%eof{

while(!pila.isEmpty()){
    agrRes("DEINDENTA("+pila.pop()+")\n");
}

System.out.println("\n"+resultado);

%eof}

%public
%class Atomos
%unicode
%standalone
%states I
%byaccj

BOOLEANO = "True" | "False"
PALABRA_RESERVADA = "and" | "or" | "not" | "while" | "if" | "else" | "elif" | "print"
IDENTIFICADOR = [a-zA-Z]([a-zA-Z]|_|[0-9])*
ENTERO = [1-9][0-9]* | 0
REAL =  ([1-9][0-9]* | 0)\.[0-9]*
CADENA = \" ~\"
ERROR_CADENA = \" 
OPERADOR = "+"|"-"|"**"|"*"|"//"|"/"|"%"|"<"|">"|">="|"<="|"="|"!"|"=="|"="
LINEA_VACIA = ([ ] | \t)+\n | "\n"
SALTO = \r|\n|\r\n
COMENTARIO = #~\n

%%
{COMENTARIO}         {yybegin(I); num_linea++;}
{BOOLEANO}           {reset(); agrRes("BOOLEANO("+yytext()+")"); return Parser.BOOLEANO;}
{PALABRA_RESERVADA}  {reset(); agrRes("PALABRA_RESERVADA("+yytext()+")"); return reservada(yytext());}
{IDENTIFICADOR}      {reset(); agrRes("IDENTIFICADOR("+yytext()+")"); return Parser.IDENTIFICADOR}
{REAL}               {reset(); agrRes("REAL("+yytext()+")"); return Parser.REAL}
{ENTERO}             {reset(); agrRes("ENTERO("+yytext()+")"); return Parser.ENTERO}
{CADENA}             {reset(); agrRes("CADENA("+yytext()+")"); return Parser.CADENA}
{ERROR_CADENA}       {agrRes("\nError de cadena en linea "+num_linea); dperr = true;}
{OPERADOR}           {reset(); agrRes("OPERADOR("+yytext()+")"); return operador(yytext());}
\( | \)              {reset();}
":"                  {reset(); agrRes("SEPARADOR(:)"); return (int) yycharat(0);}
<I> {LINEA_VACIA} {num_linea++;}
<I> [ ] {num_espacios++;}
<I> \t {num_espacios+=4;}
{SALTO}              {agrRes("SALTO\n"); yybegin(I); num_linea++; return Parser.SALTO;}
<YYINITIAL> [ ] {}
<YYINITIAL> \t {}
.                    {agrRes("\nError léxico en línea "+num_linea); dperr = true;}
