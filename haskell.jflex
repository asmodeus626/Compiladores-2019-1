// Clase de analizador léxico Haskell para comentarios, enteros e // identificadores.

%%

%public
%class AL   // Nombre a la clase AL.
%unicode
%standalone // Crea una función.

SALUDO = hola
DIGITO = [0,9]

%% // Aquí se definen las macros.
1*		 {System.out.println("UNOS");}
{DIGITO} {System.out.println("DIGITO");}