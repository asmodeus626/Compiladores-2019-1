public class Registros {

    int objetivoEntero;

    // Todos los registros enteros disponibles
    String[] E_registros = {"$t0", ... ,"$t9"};

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
     * Recupera el registro en el que se espera que sea guardado el resultado de la operación principal.
     * @return objetivoEntero
     */
    public int getObjetivo() {
        return objetivoEntero;
    }

    /**
     * Recupera los n registros siguientes que pueden ser usados de manera 
     * auxiliar para llevar a cabo los cálculos, es decir "disponibles".
     * @param n
     * @return siguientes
     */
    public String[] getNSiguientes(int n) {
        String[] siguientes = new String[n];
        for(int i = 0; i < n; i++) {
            siguientes[i] = E_registros[(objetivoEntero + i) % E_registros.length];
        }
        return siguientes;
}