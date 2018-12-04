.data

x .word 0
y .word 0
z .word 0
.text

li $t0,5
sw $t0,x
li $t0,6
sw $t0,y
li $t0,54sub $t0, $t0, $t1

sw $t0,zli $v0, 10
syscall

igualigual:
    beq $a0, $a1, igualigual_true
    b igualigual_false
igualigual_true:
    li $v0, 1
    b fin_igualigual
igualigual_false:
    li $v0, 0
fin_igualigual:
    jr $ra

diferente:
    bne $a0, $a1, diferente_true
    b diferente_false
diferente_true:
    li $v0, 1
    b fin_diferente
diferente_false:
    li $v0, 0
fin_diferente:
    jr $ra

menor:
    blt $a0, $a1, menor_true
    b menor_false
menor_true:
    li $v0, 1
    b fin_menor
menor_false:
    li $v0, 0
fin_menor:
    jr $ra

mayor:
    bgt $a0, $a1, mayor_true
    b mayor_false
mayor_true:
    li $v0, 1
    b fin_mayor
mayor_false:
    li $v0, 0
fin_mayor:
    jr $ra

menorigual:
    ble $a0, $a1, menorigual_true
    b menorigual_false
menorigual_true:
    li $v0, 1
    b fin_menorigual
menorigual_false:
    li $v0, 0
fin_menorigual:
    jr $ra

mayorigual:
    bge $a0, $a1, mayorigual_true
    b mayorigual_false
mayorigual_true:
    li $v0, 1
    b fin_mayorigual
mayorigual_false:
    li $v0, 0
fin_mayorigual:
    jr $ra
