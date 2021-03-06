//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package ast;



//#line 3 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
  import ast.Flexer;
  import ast.patron.compuesto.*;
  import java.io.*;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Nodo
String   yytext;//user variable to return contextual strings
Nodo yyval; //used to return semantic vals from action routines
Nodo yylval;//the 'lval' (result) I got from yylex()
Nodo valstk[] = new Nodo[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Nodo();
  yylval=new Nodo();
  valptr=-1;
}
final void val_push(Nodo val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Nodo[] newstack = new Nodo[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Nodo val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Nodo val_peek(int relative)
{
  return valstk[valptr-relative];
}
final Nodo dup_yyval(Nodo val)
{
  return val;
}
//#### end semantic value section ####
public final static short SALTO=257;
public final static short INDENTA=258;
public final static short DEINDENTA=259;
public final static short IDENTIFICADOR=260;
public final static short ENTERO=261;
public final static short REAL=262;
public final static short CADENA=263;
public final static short BOOLEANO=264;
public final static short AND=265;
public final static short OR=266;
public final static short NOT=267;
public final static short WHILE=268;
public final static short IF=269;
public final static short ELSE=270;
public final static short ELIF=271;
public final static short PRINT=272;
public final static short IGUAL=273;
public final static short MAIGUAL=274;
public final static short MEIGUAL=275;
public final static short DIF=276;
public final static short EXP=277;
public final static short DIV=278;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    1,    2,    2,    3,    5,
    5,    6,    6,    7,    4,    4,    9,    9,   10,   11,
   11,   12,   12,    8,   13,   13,   14,   14,   15,   15,
   16,   16,   18,   18,   18,   18,   18,   18,   17,   17,
   17,   19,   19,   19,   19,   19,   20,   20,   20,   21,
   21,   22,   22,   22,   22,   22,   22,
};
final static short yylen[] = {                            2,
    0,    1,    2,    2,    1,    1,    1,    1,    2,    1,
    1,    3,    1,    2,    1,    1,    7,    4,    4,    1,
    4,    2,    1,    1,    3,    1,    3,    1,    2,    1,
    3,    1,    1,    1,    1,    1,    1,    1,    3,    3,
    1,    3,    3,    3,    3,    1,    2,    2,    1,    3,
    1,    1,    1,    1,    1,    1,    3,
};
final static short yydefred[] = {                         0,
    5,   52,   53,   55,   54,   56,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    6,    7,    8,    0,   10,
   11,    0,   15,   16,    0,    0,   28,   30,    0,    0,
   46,   49,    0,   29,    0,    0,   14,   47,   48,    0,
    3,    4,    9,    0,    0,    0,   35,   36,   37,   38,
   33,   34,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   57,   12,    0,   27,    0,    0,   31,   45,
   42,   43,   44,   50,    0,   20,   19,    0,    0,    0,
   23,    0,    0,   21,   22,   17,
};
final static short yydgoto[] = {                         14,
   15,   16,   17,   18,   19,   20,   21,   22,   23,   24,
   77,   82,   25,   26,   27,   28,   29,   55,   30,   31,
   32,   33,
};
final static short yysindex[] = {                        59,
    0,    0,    0,    0,    0,    0,  -11,  -11,  -11,  -11,
  125,  125,  -11,    0,   72,    0,    0,    0, -246,    0,
    0,  -48,    0,    0, -251, -248,    0,    0,  -29,  -35,
    0,    0, -258,    0,  -36,  -32,    0,    0,    0,   -2,
    0,    0,    0,  -11,  -11,  -11,    0,    0,    0,    0,
    0,    0,  125,  125,  125,  125,  125,  125,  125,  125,
  112,  112,    0,    0, -248,    0,  -35,  -35,    0,    0,
    0,    0,    0,    0, -218,    0,    0, -227,   99,  -13,
    0,   86,  112,    0,    0,    0,
};
final static short yyrindex[] = {                        51,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   53,    0,    0,    0,    0,    0,
    0, -210,    0,    0,  -23,   47,    0,    0,   42,    9,
    0,    0,  -37,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   48,    0,   14,   36,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    5,  -34,    0,    0,    0,    0,  110,    0,    0,
  -53,    0,    0,    3,   -4,    6,    0,    0,  -17,  102,
    0,    0,
};
final static int YYTABLESIZE=389;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         51,
   18,   59,   34,   51,   51,   51,   57,   51,   78,   51,
   43,   58,   44,   53,   45,   54,   46,   24,   60,   42,
   51,   61,   51,   51,   51,   62,   76,   76,   13,   86,
   51,   11,   52,   12,   24,   67,   68,   24,   63,   79,
   18,   66,   80,   18,   83,   18,   13,   65,   76,   41,
    1,   41,    2,   41,   39,    0,   39,    0,   39,    0,
   69,    0,    0,    0,    0,    0,   41,    0,   41,   41,
   41,   39,    0,   39,   39,   39,   40,    0,   40,    0,
   40,    0,   32,   81,    0,    0,   85,   26,   25,    0,
    0,    0,    0,   40,    0,   40,   40,   40,   13,   32,
    0,   11,   32,   12,   26,   25,    0,   26,   25,    0,
    0,   13,   38,   39,   11,    0,   12,   35,   36,   37,
    0,    0,   40,    0,    0,   13,    0,    0,   11,    0,
   12,    0,    0,    0,    0,    0,    0,    0,   13,    0,
    0,   11,    0,   12,    0,    0,    0,    0,    0,    0,
    0,   13,    0,   64,   11,    0,   12,   70,   71,   72,
   73,   74,    0,    0,   13,    0,    0,   11,    0,   12,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   51,
    0,    0,    0,    0,    0,    0,    0,   51,   51,    0,
    0,    0,    0,   24,    0,   51,   51,   51,   51,    0,
   51,    0,   56,   47,   48,   49,   50,    0,    2,    3,
    4,    5,    6,    0,    0,    7,    0,   18,    0,   18,
   18,   18,   18,   18,   18,   41,    0,   18,   18,   18,
   39,    0,   18,   41,   41,    0,    0,    0,   39,   39,
    0,   41,   41,   41,   41,    0,   39,   39,   39,   39,
    0,    0,   40,    0,    0,    0,    0,    0,   32,    0,
   40,   40,    0,   26,   25,    0,   32,   32,   40,   40,
   40,   40,   26,   25,    0,    1,    0,    0,    2,    3,
    4,    5,    6,    0,    0,    7,    8,    9,   41,    0,
   10,    2,    3,    4,    5,    6,    0,    0,    7,    8,
    9,    0,    0,   10,   84,    2,    3,    4,    5,    6,
    0,    0,    7,    8,    9,    0,    0,   10,    2,    3,
    4,    5,    6,    0,    0,    7,    8,    9,   75,    0,
   10,    2,    3,    4,    5,    6,    0,    0,    7,    0,
    0,    0,    0,   10,    2,    3,    4,    5,    6,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
    0,   37,    7,   41,   42,   43,   42,   45,   62,   47,
  257,   47,   61,   43,  266,   45,  265,   41,  277,   15,
   58,   58,   60,   61,   62,   58,   61,   62,   40,   83,
   60,   43,   62,   45,   58,   53,   54,   61,   41,  258,
   40,   46,  270,   43,   58,   45,  257,   45,   83,   41,
    0,   43,    0,   45,   41,   -1,   43,   -1,   45,   -1,
   55,   -1,   -1,   -1,   -1,   -1,   58,   -1,   60,   61,
   62,   58,   -1,   60,   61,   62,   41,   -1,   43,   -1,
   45,   -1,   41,   79,   -1,   -1,   82,   41,   41,   -1,
   -1,   -1,   -1,   58,   -1,   60,   61,   62,   40,   58,
   -1,   43,   61,   45,   58,   58,   -1,   61,   61,   -1,
   -1,   40,   11,   12,   43,   -1,   45,    8,    9,   10,
   -1,   -1,   13,   -1,   -1,   40,   -1,   -1,   43,   -1,
   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   40,   -1,
   -1,   43,   -1,   45,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   40,   -1,   44,   43,   -1,   45,   56,   57,   58,
   59,   60,   -1,   -1,   40,   -1,   -1,   43,   -1,   45,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  265,  266,   -1,
   -1,   -1,   -1,  257,   -1,  273,  274,  275,  276,   -1,
  278,   -1,  278,  273,  274,  275,  276,   -1,  260,  261,
  262,  263,  264,   -1,   -1,  267,   -1,  257,   -1,  259,
  260,  261,  262,  263,  264,  257,   -1,  267,  268,  269,
  257,   -1,  272,  265,  266,   -1,   -1,   -1,  265,  266,
   -1,  273,  274,  275,  276,   -1,  273,  274,  275,  276,
   -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,  257,   -1,
  265,  266,   -1,  257,  257,   -1,  265,  266,  273,  274,
  275,  276,  266,  266,   -1,  257,   -1,   -1,  260,  261,
  262,  263,  264,   -1,   -1,  267,  268,  269,  257,   -1,
  272,  260,  261,  262,  263,  264,   -1,   -1,  267,  268,
  269,   -1,   -1,  272,  259,  260,  261,  262,  263,  264,
   -1,   -1,  267,  268,  269,   -1,   -1,  272,  260,  261,
  262,  263,  264,   -1,   -1,  267,  268,  269,  257,   -1,
  272,  260,  261,  262,  263,  264,   -1,   -1,  267,   -1,
   -1,   -1,   -1,  272,  260,  261,  262,  263,  264,
};
}
final static short YYFINAL=14;
final static short YYMAXTOKEN=278;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",null,
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'",null,
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"SALTO","INDENTA","DEINDENTA","IDENTIFICADOR",
"ENTERO","REAL","CADENA","BOOLEANO","AND","OR","NOT","WHILE","IF","ELSE","ELIF",
"PRINT","IGUAL","MAIGUAL","MEIGUAL","DIF","EXP","DIV",
};
final static String yyrule[] = {
"$accept : input",
"input :",
"input : file_input",
"file_input : file_input SALTO",
"file_input : file_input stmt",
"file_input : SALTO",
"file_input : stmt",
"stmt : simple_stmt",
"stmt : compound_stmt",
"simple_stmt : small_stmt SALTO",
"small_stmt : expr_stmt",
"small_stmt : print_stmt",
"expr_stmt : test '=' test",
"expr_stmt : test",
"print_stmt : PRINT test",
"compound_stmt : if_stmt",
"compound_stmt : while_stmt",
"if_stmt : IF test ':' suite ELSE ':' suite",
"if_stmt : IF test ':' suite",
"while_stmt : WHILE test ':' suite",
"suite : simple_stmt",
"suite : SALTO INDENTA stmt2 DEINDENTA",
"stmt2 : stmt2 stmt",
"stmt2 : stmt",
"test : or_test",
"or_test : or_test OR and_test",
"or_test : and_test",
"and_test : and_test AND not_test",
"and_test : not_test",
"not_test : NOT not_test",
"not_test : comparison",
"comparison : expr comp_op comparison",
"comparison : expr",
"comp_op : '<'",
"comp_op : '>'",
"comp_op : IGUAL",
"comp_op : MAIGUAL",
"comp_op : MEIGUAL",
"comp_op : DIF",
"expr : expr '+' term",
"expr : expr '-' term",
"expr : term",
"term : term '*' factor",
"term : term '/' factor",
"term : term '%' factor",
"term : term DIV factor",
"term : factor",
"factor : '+' factor",
"factor : '-' factor",
"factor : power",
"power : atom EXP factor",
"power : atom",
"atom : IDENTIFICADOR",
"atom : ENTERO",
"atom : CADENA",
"atom : REAL",
"atom : BOOLEANO",
"atom : '(' test ')'",
};

//#line 115 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"

  private Flexer lexer;
  public Nodo raiz;
  public static short linea = 0 ;

  private int yylex () {
    int yyl_return = -1;
    try {
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
        lexer = new Flexer(r, this);
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
    }else{
      System.out.println("Expresión bien formada");
    }
  }
//#line 387 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 13 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{System.out.println("Reconocimiento exitoso"); raiz = val_peek(0);}
break;
case 3:
//#line 16 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(1);}
break;
case 4:
//#line 17 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{val_peek(1).agregaHijoFinal(val_peek(0)); yyval = val_peek(1);}
break;
case 6:
//#line 19 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new NodoStmts(val_peek(0));}
break;
case 7:
//#line 22 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 8:
//#line 23 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 9:
//#line 26 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{linea++; yyval = val_peek(1);}
break;
case 10:
//#line 29 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 11:
//#line 30 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 12:
//#line 33 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new AsigNodo(val_peek(2) , val_peek(0));}
break;
case 13:
//#line 34 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 14:
//#line 37 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new NodoPrint(val_peek(0));}
break;
case 15:
//#line 40 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 16:
//#line 41 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 17:
//#line 44 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new IfNodo(val_peek(5) , val_peek(3) , val_peek(0));}
break;
case 18:
//#line 45 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new IfNodo(val_peek(2) , val_peek(0));}
break;
case 19:
//#line 48 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new WhileNodo(val_peek(2) , val_peek(0));}
break;
case 20:
//#line 51 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 21:
//#line 52 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(1);}
break;
case 22:
//#line 55 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{val_peek(1).agregaHijoFinal(val_peek(0)); yyval = val_peek(1);}
break;
case 23:
//#line 56 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new NodoStmts(val_peek(0));}
break;
case 24:
//#line 59 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 25:
//#line 62 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new OrNodo(val_peek(2) , val_peek(0));}
break;
case 26:
//#line 63 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 27:
//#line 66 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new AndNodo(val_peek(2) , val_peek(0));}
break;
case 28:
//#line 67 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 29:
//#line 70 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new NotNodo(val_peek(0));}
break;
case 30:
//#line 71 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 31:
//#line 74 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{val_peek(1).agregaHijoPrincipio(val_peek(2)); val_peek(1).agregaHijoFinal(val_peek(0)); yyval = val_peek(1);}
break;
case 32:
//#line 75 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 33:
//#line 78 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new MenorNodo(null , null);}
break;
case 34:
//#line 79 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new MayorNodo(null , null);}
break;
case 35:
//#line 80 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new IgualIgualNodo(null , null);}
break;
case 36:
//#line 81 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new MayorIgualNodo(null , null);}
break;
case 37:
//#line 82 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new MenorIgualNodo(null , null);}
break;
case 38:
//#line 83 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new DiferenteNodo(null , null);}
break;
case 39:
//#line 86 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new AddNodo(val_peek(2),val_peek(0));}
break;
case 40:
//#line 87 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new DifNodo(val_peek(2),val_peek(0));}
break;
case 41:
//#line 88 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 42:
//#line 91 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new PorNodo(val_peek(2) , val_peek(0));}
break;
case 43:
//#line 92 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new DivNodo(val_peek(2) , val_peek(0));}
break;
case 44:
//#line 93 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new ModuloNodo(val_peek(2) , val_peek(0));}
break;
case 45:
//#line 94 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new DivisionEnteraNodo(val_peek(2) , val_peek(0));}
break;
case 46:
//#line 95 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 47:
//#line 97 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new AddNodo(null,val_peek(0));}
break;
case 48:
//#line 98 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new DifNodo(null, val_peek(0));}
break;
case 49:
//#line 99 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 50:
//#line 102 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = new PowNodo(val_peek(2), val_peek(0));}
break;
case 51:
//#line 103 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 52:
//#line 106 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 53:
//#line 107 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 54:
//#line 108 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 55:
//#line 109 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 56:
//#line 110 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(0);}
break;
case 57:
//#line 111 "/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_4/src/main/byaccj/gramatica2.y"
{yyval = val_peek(1);}
break;
//#line 756 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
