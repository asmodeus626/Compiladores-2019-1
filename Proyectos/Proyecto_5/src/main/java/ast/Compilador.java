package ast;

import java.io.*;
import ast.patron.compuesto.*;
import ast.patron.visitante.*;

public class Compilador {

    Parser parser;
    Nodo raízAST;
    VisitorPrint v_print;
    VisitorGenerator v_generate;
    VisitorType v_type;

    Compilador(Reader fuente) {
        parser  = new Parser(fuente);
        v_print = new VisitorPrint();
        v_type  = new VisitorType();
        v_generate = new VisitorGenerator();
    }

    public void ConstruyeAST(boolean debug) {
        parser.yydebug = debug;
        parser.yyparse(); // análisis léxico, sintáctio y constucción del AST
        raízAST = parser.raiz;
    }

    public void imprimeAST() {
        parser.raiz.accept(v_print);
    }

    public void verificaTiposAST() {
        parser.raiz.accept(v_type);
    }

    private void genera() {
        parser.raiz.accept(v_generate);
    }

    public static void main(String[] args) {
            String archivo = "src/main/resources/test.txt";
        try{
            Reader a = new FileReader(archivo);
            Compilador c  = new Compilador(a);
            c.ConstruyeAST(true);
            c.imprimeAST();
            c.verificaTiposAST();
            /* TODO: Ver que onda con la generacion de codigo */ 
            //c.genera();
        }catch(FileNotFoundException e) {
            System.err.println("El archivo " + archivo +" no fue encontrado. ");
        }catch(ArrayIndexOutOfBoundsException e) {
            System.err.println("Uso: java Compilador [archivo.p]: ");
        }
    }
}
