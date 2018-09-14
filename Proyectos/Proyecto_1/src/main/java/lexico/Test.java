package lexico;

public class Test {

    public static void main (String[] args){
        AnalizadorLexico al1 = new AnalizadorLexico("src/main/resources/fizzbuzz.py");
        al1.analiza();
        AnalizadorLexico al2 = new AnalizadorLexico("src/main/resources/error_cadena.py");
        al2.analiza();
        AnalizadorLexico al3 = new AnalizadorLexico("src/main/resources/error_identacion.py");
        al3.analiza();
        AnalizadorLexico al4 = new AnalizadorLexico("src/main/resources/error_lexema.py");
        al4.analiza();
    }
}
