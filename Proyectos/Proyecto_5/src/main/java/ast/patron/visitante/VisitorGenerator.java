package ast.patron.visitante;

import ast.patron.compuesto.*;
import ast.patron.registros.Registros;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class VisitorGenerator implements Visitor {
    Registros reg = new Registros();
    String data = ""; //Todo lo relacionado con declaración de variables
    String text = ""; //Todo lo relacionado con código ejecutable
    int numCadena = 1;
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
        String res = opcode+" "+objetivo+", "+siguientes[0]+", "+siguientes[1]+"\n";
        text+=res;
        System.out.println(res);
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
        String res = opcode+" "+objetivo+", "+siguientes[0]+", "+siguientes[1]+"\n";
        text+=res;
        System.out.println(res);
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
        text+="\nli "+objetivo+",";
        System.out.print("\nli "+objetivo+",");
        hijoDerecho.accept(this);
        int tipo = hijoDerecho.getType();
        text+="\nsw "+objetivo+",";
        System.out.print("\nsw "+objetivo+",");
        hijoIzquierdo.accept(this);
        nombre = hijoIzquierdo.getNombre();
        
        if(nombre != null && tipo == 1) {
            String opcode = "sw";
            //text += opcode+" "+siguientes[1]+ ", "+nombre+"\n";
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
           text += "lw " + siguientes[0] + ", " + nombre + "\n";
        }

        // Subárbol derecho
        reg.setObjetivo(siguientes[1]);
        hd.accept(this);
        nombre = hd.getNombre();
        if(nombre != null){
           text += "lw " + siguientes[1] + ", " + nombre + "\n";
        }

        String opcode =  tipo==2 ? "div.s" : "div";

        text += opcode  + " " +
                            siguientes[0] + ", " + siguientes[1] + "\n";
        
        text += "mflo " + objetivo + "\n";
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
           text += "lw " + siguientes[0] + ", " + nombre + "\n";
        }

        // Genero el código del subárbol derecho
        reg.setObjetivo(siguientes[1]);
        hijoDerecho.accept(this);
        nombre = hijoDerecho.getNombre();
        if(nombre != null){
           text += "lw " + siguientes[1] + ", " + nombre + "\n";
        }

        String opcode =  tipo==2 ? "mult.s" : "mult" ;

        text += opcode  + " " +
                            siguientes[0] + ", " + siguientes[1] + "\n"; 
        
        text +="mflo " + objetivo + "\n";
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
        text+=n.getNombre();
        System.out.print(n.getNombre());
    }

    @Override
    public void visit(IntHoja n) {
        //String[] siguientes = reg.getNSiguientes(1, true);
        //text += "li ," + siguientes[0] + " , " + n.getValor().ival + "\n";
        text+=n.getValor().ival;
        System.out.print(n.getValor().ival);
    }

    @Override
    public void visit(RealHoja n) {
        text+=n.getValor().dval;
        System.out.print(n.getValor().dval);
    }

    @Override
    public void visit(CadenaHoja n) {
        data+="\ncadena"+numCadena+" .asciiz "+n.getValor().sval;
        numCadena++;
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
        for(Iterator i = n.getHijos().iterator(); i.hasNext(); )  {
            Nodo hijo =(Nodo) i.next();
            if(hijo != null) {
                hijo.accept(this);    
            }    
        }
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
           text +="lw " + objetivo + ", " + nombre + "\n";
        }
        
        text +="li $v0,1\n";
        text +="add  $a0, $zero, " + objetivo + "\n";
        text +="syscall" + "\n";
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
    
    public void escribeCodigo(VisitorType vt){
        data+=variables(vt.tabla_de_tipos);
        text+="\nli $v0, 10\nsyscall";
        text+="\n#Subrutinas#############################################################";
        try{
            FileWriter fw1 = new FileWriter("salida.asm");
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write(".data");
            bw1.newLine();
            bw1.write(data);
            bw1.newLine();
            bw1.write(".text");
            bw1.newLine();
            bw1.write(text);
            bw1.newLine();
            
            bw1.write(rutina_comparador("igualigual"));
            bw1.newLine();
            bw1.write(rutina_comparador("diferente"));
            bw1.newLine();
            bw1.write(rutina_comparador("menor"));
            bw1.newLine();
            bw1.write(rutina_comparador("mayor"));
            bw1.newLine();
            bw1.write(rutina_comparador("menorigual"));
            bw1.newLine();
            bw1.write(rutina_comparador("mayorigual"));
            bw1.newLine();
            bw1.close();
        }catch(Exception ex){}
    }
}