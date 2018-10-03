
%{
  import java.io.*;
%}

%token SALTO
%token INDENTA
%token DEINDENTA
%token IDENTIFICADOR
%token ENTERO
%token REAL
%token CADENA
%token BOOLEANO
%token AND OR NOT WHILE IF ELSE ELIF PRINT
%token IGUAL MAIGUAL MEIGUAL DIF EXP DIV
     
%%

file_input : file_input SALTO {System.out.println("Está bien hecho.");}
           | file_input stmt
           | SALTO
           | stmt
           ;

stmt : simple_stmt
     | compound_stmt
     ;

simple_stmt : small_stmt SALTO
            ;

small_stmt : expr_stmt 
           | print_stmt
           ;

expr_stmt : test '=' test
          | test
          ;

print_stmt := PRINT test
           ;

compound_stmt := if_stmt
              | while_stmt
              ;

if_stmt := IF test ':' suite ELSE ':' suite
        |  IF test ':' suite
        ;

while_stmt := WHILE test ':' suite
           ;

suite : simple_stmt
      | SALTO INDENTA stmt2 DEINDENTA
      ;

stmt2 : stmt2 stmt
      | stmt
      ;

test : or_test
     ;

or_test : or_test OR and_test
        |   and_test
        ;

and_test : and_test AND not_test
         | not_test
         ;

not_test : NOT not_test
         | comparison
         ;

comparison : expr comp_op comparison
           | expr
           ;

comp_op : '<'
        | '>'
        | IGUAL
        | MAIGUAL
        | MEIGUAL
        | DIF
        ;

expr : expr '+' term
     | expr '-' term
     | term
     ;

term : term '*' factor
     | term '/' factor
     | term '%' factor
     | term DIV factor
     | factor

factor : '+' factor
       | '-' factor
       | power
       ;

power : atom EXP factor
      | atom
      ;

atom : IDENTIFICADOR
     | ENTERO 
     | CADENA
     | REAL 
     | BOOLEANO 
     | '(' test ')'
     ;

%%

  private Atomos lexer;
  public static short linea = 0 ;

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }

    public void yyerror (String error) {
        System.err.println ("[ERROR] " + error);
    }


    public Parser(Reader r) {
        lexer = new Atomos(r, this);
    }

  public static void main(String args[]) throws IOException {
    Parser yyparser;
    yyparser = new Parser(new FileReader("src/main/resources/test.txt"));
    yyparser.yydebug = false; //true para que imprima el proceso.
    int condicion = yyparser.yyparse();

    if(condicion != 0){
      linea++;
      System.err.print ("[ERROR] ");
      yyparser.yyerror("La expresión aritmética no esta bien formada. en la línea " + linea);
    }
  }
