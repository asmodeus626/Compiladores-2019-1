package ast.patron.registros;

import java.util.Arrays;

/**
 * Clase para facilitar la asignación de registros, que abstrae el manejo de 
 * los registros.
 * @author rodd
 */
public class Registros {

    int objetivoEntero;
    int objetivoFlotante;

    // Todos los registros enteros disponibles.
    String[] E_registros = {"$t0", "$t1","$t2", "$t3", "$t4", "$t5", "$t6", "$t7", "$t8", "$t9", "$s0", "$s1","$s2","$s3","$s4","$s5","$s6","$s7"};
    // Todos los registros flotantes disponibles.
    String[] F_registros = {"$f4", "$f5", "$f6", "$f7", "$f8", "$f9", "$f10"};
    /**
     * 
     * @param o
     */
    public void setObjetivo(int o) {
        objetivoEntero = o % E_registros.length;
    }

    /**
     * 
     * @param o
     */
    public void setObjetivo(String o) {
        int nvo_objetivo = Arrays.asList(E_registros).indexOf(o);
        setObjetivo(nvo_objetivo);
    }

    /**
     * Recupera el registro en el que se espera que sea
     * guardado el resultado de la operación principal.
     * @return objetivoEntero
     */
    public int getObjetivo() {
        return objetivoEntero;
    }

    /**
     * 
     * @param entero
     * @return 
     */
    public String getObjetivo(boolean entero) {
        return entero ? E_registros[objetivoEntero] : F_registros[objetivoFlotante];
    }

    /**
     * Recupera los n registros siguientes que pueden ser usados de manera 
     * auxiliar para llevar a cabo los cálculos, es decir "disponibles".
     * @param n
     * @return siguientes
     */
    public String[] getNSiguientes(int n, boolean entero) {
        if(entero){
            String[] siguientes = new String[n];
            for(int i = 0; i < n; i++) {
                siguientes[i] = E_registros[(objetivoEntero + i) % E_registros.length];
            }
            return siguientes;
        }else{
            String[] siguientes = new String[n];
            for(int i = 0; i < n; i++) {
                siguientes[i] = F_registros[(objetivoEntero + i) % F_registros.length];
            }
            return siguientes;
        }
        
    }
}