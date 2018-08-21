// Clase de analizador léxico Haskell para comentarios, enteros e // identificadores.

%%

%public
%class AL   // Nombre a la clase AL.
%unicode
%standalone // Crea una función.

SALUDO = hola
COMENTARIO_1 = --~\n
COMENTARIO_2 = \{-~-\}
ENTERO = 0 | [1-9][0-9]*

%% // Aquí se definen las macros.
{COMENTARIO_1} {System.out.println("COMENTARIO_1");}
{COMENTARIO_2} {System.out.println("COMENTARIO_2");}
{ENTERO}       {System.out.println("ENTERO ("+yytext()+")");}