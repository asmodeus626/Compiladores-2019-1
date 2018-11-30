/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.patron.visitante;

import ast.patron.compuesto.DifNodo;
import ast.patron.compuesto.Nodo;
import ast.patron.registros.Registros;

// FALTA QUE IMPLEMENTE AL VISITANTE
public class VisitorGenerator {
  Registros reg = new Registros();

  public void visit(DifNodo n){
      Nodo hi = n.getPrimerHijo();
      Nodo hd = n.getUltimoHijo();

      //String objetivo = reg.getObjetivo();
      String[] siguientes = reg.getNsiguientes(2);

      // Genero el código del subárbol izquiero
      //reg.setObjetivo(siguiente[0]);
      //hi.accept(this);

      // Genero el código del subárbol derecho
      //reg.setObjetivo(siguiente[1]);
      //hd.accept(this);

      String opcdode =  "sub";

      //System.out.println(opcode + " " + objetivo + ", " +
      //                    siguientes[0] + ", " + siguientes[1]);
  }
}