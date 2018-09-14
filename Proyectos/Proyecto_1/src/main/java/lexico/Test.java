package lexico;

public class Test {

    public static void main (String[] args){
        AnalizadorLexico al = new AnalizadorLexico("src/main/resources/test.txt");
        al.analiza();
    }
}