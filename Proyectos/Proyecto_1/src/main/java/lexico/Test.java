package lexico;

public class Test {

    public static void main (String[] args){
        AnalizadorLexico al = new AnalizadorLexico("src/main/resources/fizzbuzz.py");
        al.analiza();
    }
}