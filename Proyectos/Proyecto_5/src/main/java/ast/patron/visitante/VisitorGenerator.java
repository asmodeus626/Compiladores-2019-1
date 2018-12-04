package ast.patron.visitante;

import ast.patron.compuesto.*;
import ast.patron.registros.Registros;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class VisitorGenerator implements Visitor {
    Registros reg = new Registros();
    public String salida = "";

    @Override
    public void visit(DifNodo n) {
        Nodo hijoIzquierdo = n.getPrimerHijo();
        Nodo hijoDerecho = n.getUltimoHijo();

        // Obtenemos el tipo del registro objetivo.
        boolean entero = n.getType() != 2;

        String objetivo = reg.getObjetivo(entero);
        String[] siguientes = reg.getNSiguientes(2,entero);

        // Genero el código del subárbol izquierdo.
        reg.setObjetivo(siguientes[0]);
        hijoIzquierdo.accept(this);

        // Genero el código del subárbol derecho
        reg.setObjetivo(siguientes[1]);
        hijoDerecho.accept(this);

        String opcode = "sub";

        System.out.println(opcode+" "+objetivo+", "+siguientes[0]+", "+siguientes[1]);
    }

    @Override
    public void visit(AddNodo n) {
        Nodo hijoIzquierdo = n.getPrimerHijo();
        Nodo hijoDerecho = n.getUltimoHijo();

        // Obtenemos el tipo del registro objetivo.
        boolean entero = n.getType() != 2;

        String objetivo = reg.getObjetivo(entero);
        String[] siguientes = reg.getNSiguientes(2,entero);

        // Genero el código del subárbol izquierdo.
        reg.setObjetivo(siguientes[0]);
        hijoIzquierdo.accept(this);

        // Genero el código del subárbol derecho
        reg.setObjetivo(siguientes[1]);
        hijoDerecho.accept(this);

        String opcode = "add";

        System.out.println(opcode+" "+objetivo+", "+siguientes[0]+", "+siguientes[1]);
    }

    @Override
    public void visit(AsigNodo n) {
        Nodo hijoIzquierdo = n.getPrimerHijo();
        Nodo hijoDerecho = n.getUltimoHijo();

        String nombre;
        // Tipo del registro objetivo.
        boolean entero = n.getType() != 2;

        String objetivo = reg.getObjetivo(entero);
        String[] siguientes = reg.getNSiguientes(2, entero);
        
        System.out.print("li "+objetivo+",");
        hijoDerecho.accept(this);
        int tipo = hijoDerecho.getType();

        System.out.print("\nsw "+objetivo+",");
        hijoIzquierdo.accept(this);
        nombre = hijoIzquierdo.getNombre();
        
        if(nombre != null && tipo == 1) {
            String opcode = "sw";
            salida += opcode+" "+siguientes[1]+ ", "+nombre+"\n";
        }
    }

    @Override
    public void visit(DivNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(DivisionEnteraNodo n) {
        Nodo hi = n.getPrimerHijo();
        Nodo hd = n.getUltimoHijo();

        // Registro objetivo
        int tipo = n.getType();
        boolean entero =  tipo!=2;

        String objetivo = reg.getObjetivo(entero);
        String[] siguientes = reg.getNSiguientes(2,entero);

        // Subárbol izquierdo
        reg.setObjetivo(siguientes[0]);
        hi.accept(this);
        String nombre = hi.getNombre();
        if(nombre != null){
           salida += "lw " + siguientes[0] + ", " + nombre + "\n";
        }

        // Subárbol derecho
        reg.setObjetivo(siguientes[1]);
        hd.accept(this);
        nombre = hd.getNombre();
        if(nombre != null){
           salida += "lw " + siguientes[1] + ", " + nombre + "\n";
        }

        String opcode =  tipo==2 ? "div.s" : "div";

        salida += opcode  + " " +
                            siguientes[0] + ", " + siguientes[1] + "\n";
        
        salida += "mflo " + objetivo + "\n";
    }

    @Override
    public void visit(ModuloNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(PorNodo n) {
        Nodo hijoIzquierdo = n.getPrimerHijo();
        Nodo hijoDerecho = n.getUltimoHijo();

        // Tipo de registro objetivo
        int tipo = n.getType();
        boolean entero =  tipo!=2;

        String objetivo = reg.getObjetivo(entero);
        String[] siguientes = reg.getNSiguientes(2,entero);
        
        // Genero el código del subárbol izquiero
        reg.setObjetivo(siguientes[0]);
        hijoIzquierdo.accept(this);
        String nombre = hijoIzquierdo.getNombre();
        if(nombre != null){
           salida += "lw " + siguientes[0] + ", " + nombre + "\n";
        }

        // Genero el código del subárbol derecho
        reg.setObjetivo(siguientes[1]);
        hijoDerecho.accept(this);
        nombre = hijoDerecho.getNombre();
        if(nombre != null){
           salida += "lw " + siguientes[1] + ", " + nombre + "\n";
        }

        String opcode =  tipo==2 ? "mult.s" : "mult" ;

        salida += opcode  + " " +
                            siguientes[0] + ", " + siguientes[1] + "\n"; 
        
        salida +="mflo " + objetivo + "\n";
    }

    @Override
    public void visit(PowNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(AndNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(OrNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(DiferenteNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(IgualIgualNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(MenorIgualNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(MayorIgualNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(MenorNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(MayorNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(NotNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(IdentifierHoja n) {
        System.out.print(n.getNombre());
    }

    @Override
    public void visit(IntHoja n) {
        String[] siguientes = reg.getNSiguientes(1, true);
        salida += "li ," + siguientes[0] + " , " + n.getValor().ival + "\n";
    }

    @Override
    public void visit(RealHoja n) {
        System.out.print(n.getValor().dval);
    }

    @Override
    public void visit(CadenaHoja n) {
        
    }

    @Override
    public void visit(BooleanHoja n) {
        boolean valor = n.getValor().bval;
        if(valor){
            System.out.print(1);
        }else{
            System.out.print(0);
        }
    }

    @Override
    public void visit(Nodo n) {
        for (Iterator i = n.getHijos().iterator(); i.hasNext();) {
            Nodo hijo = (Nodo) i.next();
            if( hijo != null) {
                hijo.accept(this);    
            }    
        }
    }

    @Override
    public void visit(NodoStmts n) {
        VisitorType auxiliar = new VisitorType();
        salida +=".data" + "\n";
        for (String x : auxiliar.tabla_de_tipos.keySet()){
            if(auxiliar.tabla_de_tipos.get(x) == 1){
                salida += x + ":" + "\t.word 0" + "\n";
            }
            
        }
        salida +=".text" + "\n";
        
        for (Iterator i = n.getHijos().iterator(); i.hasNext(); ) {
            Nodo hijo = (Nodo) i.next();
            if ( hijo != null){
                hijo.accept(this);    
            }    
        }
        salida += "nop \n";
        this.escribeCodigo();
    }

    @Override
    public void visit(IfStmts n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(IfNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(NodoPrint n) {
        Nodo nodo = n.getPrimerHijo();

        // Registro objetivo
        int tipo = n.getType();
        boolean entero =  tipo!=2;

        String objetivo = reg.getObjetivo(entero);
        String[] siguientes = reg.getNSiguientes(1,entero);

        // Codigo hijo
        reg.setObjetivo(siguientes[0]);
        nodo.accept(this);
        String nombre = nodo.getNombre();
        if(nombre != null){
           salida +="lw " + objetivo + ", " + nombre + "\n";
        }
        
        salida +="li $v0,1\n";
        salida +="add  $a0, $zero, " + objetivo + "\n";
        salida +="syscall" + "\n";
    }

    @Override
    public void visit(WhileNodo n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Servirá para declarar las variables.
     * @param hm Tabla de símbolos
     * @return 
     */
    public String variables(HashMap<String, Integer> hm){
        Set<String> vars = hm.keySet(); //conjunto de variables
        String ret = "";
        for(String s:vars){
            switch(hm.get(s)){
                case 1: ret+="\n"+s+" .word 0";//Entero
                break;
                case 2: ret+="\n"+s+" .float 0";//Real
                break;
                case 3: ret+="\n"+s+" .byte 0";//Booleano
                break;
                case 4: ret+="\n"+s+" .space 1024";//Cadena
            }
        }
        System.out.println(ret);
        return ret;
    }
    
        /**
     * Imprime y devuelve la subrutina asociada al comparador que recibe.
     * @param comp: comparador
     * == : igualigual
     * != : diferente
     * <  : menor
     * >  : mayor
     * <= : menorigual
     * >= : mayorigual
     * @return subrutina
     */
    public String rutina_comparador(String comp){
        String salto=""; //Se le asocia un salto diferente dependiendo del comparador
        
        switch(comp){
            case "igualigual": salto = "beq";
            break;
            case "diferente": salto = "bne";
            break;
            case "menor": salto = "blt";
            break;
            case "mayor": salto = "bgt";
            break;
            case "menorigual": salto = "ble";
            break;
            case "mayorigual": salto = "bge";
            break;
            default: salto = "b";
        }
        
        String ret="";
        ret+="\n"+ comp +":";
        ret+="\n    "+ salto +" $a0, $a1, "+ comp +"_true";
        ret+="\n    b "+ comp +"_false";
        ret+="\n"+ comp +"_true:";
        ret+="\n    li $v0, 1";
        ret+="\n    b fin_"+ comp +"";
        ret+="\n"+ comp +"_false:";
        ret+="\n    li $v0, 0";
        ret+="\nfin_"+ comp +":";
        ret+="\n    jr $ra";
        System.out.println(ret);
        return ret;
    }
    
    private void escribeCodigo(){
        // TODO: ARREGLAR COSAS CON EL COMPILADOR PARA PODER HACER ESTE
        // METODO
    }
}