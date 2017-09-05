##Algoritmo de Rabin Karp.
##Explicación: https://drive.google.com/drive/folders/0Bz2kEXMj0MxCTjFUYzRNVGFjR2s


	.data
inicio: .word 0
texto: .asciiz "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eget metus eget risus sagittis scelerisque eu eget magna. Integer rhoncus velit in aliquam posuere. Phasellus varius et lorem vitae dictum. Aenean pulvinar elit sit amet euismod elementum. Aenean in mollis nibh, sed pretium odio. Nam sit amet magna quis sem tempus sodales hendrerit ut justo. Nullam ultrices nisl vel nulla tincidunt dapibus. Quisque rhoncus vestibulum elit eu gravida. Quisque et blandit leo. Pellentesque consequat sit amet odio nec varius. Donec luctus, lorem et iaculis rhoncus, turpis purus porttitor elit, nec auctor ipsum nibh nec metus. Suspendisse et felis sed urna auctor gravida a ut felis. Praesent ullamcorper porta purus, quis porttitor lorem laoreet quis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vel dolor quis diam eleifend laoreet. Mauris iaculis ex cursus lectus molestie laoreet. Vestibulum ultricies, enim sed ultrices ultricies, nibh turpis interdum erat, vel vehicula nulla risus pellentesque eros. Quisque vitae tellus scelerisque, aliquam felis id, ornare nibh. Sed gravida commodo volutpat. Nam elementum faucibus urna, vitae egestas massa finibus in. Phasellus ut nulla orci. Phasellus quis tristique magna. Aenean vitae lacinia risus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed facilisis a massa in vehicula. Nulla posuere arcu metus, vel convallis lectus porttitor eget. Vestibulum luctus nisi et est vulputate, et pharetra justo tincidunt. Aliquam ante quam, aliquet et placerat scelerisque, blandit in eros. Aenean ut ultrices mi, et auctor magna. Morbi sed dolor rhoncus neque convallis aliquam. Duis id orci id leo tempus congue. Proin faucibus, diam et lobortis ornare, tortor ligula vestibulum mauris, quis venenatis lorem est ut tellus. Vestibulum et mi ac nibh consequat luctus in id sapien. Duis et vehicula libero, sit amet tincidunt libero. Maecenas iaculis libero augue, non vulputate mi accumsan eu. Suspendisse dapibus diam ac augue placerat, ut fermentum est consequat. Proin sed turpis elit. In interdum sit amet nulla at cursus. Pellentesque malesuada lectus ligula, nec lobortis leo cursus ut."  
match: .asciiz "Lorem ipsum"
	.text
	
	li $t6, 2
	lw $t2, inicio
	li $t4, 0
	li $t3, 1
	lb $t9, match($zero)
	la $s1, match + 1
  	
hash1:
	
	lb $t8, ($s1)
	beq $t6, 2048, finhash1
	mul $t5, $t8, $t6
	add $t9, $t9, $t5
	addi $s1, $s1, 1
	sll $t6,$t6,1
	j hash1
finhash1:	
	beq $t3, 0, finhash2
	add $t7,$t9,0
hash2:	
        li $t6, 2
        lb $t9, texto($t2)
        la $s1, texto + 1($t2)
        li $t3,0
	j hash1
finhash2:	
        beq $t7, $t9, comparacion
        j nuevohash
        	
	
	
comparacion:

	li $t3, 0
	add $t6,$t2,0
loopinterno:
	lb $t8, texto($t6)
	lb $t1, match($t3)
	beqz $t1, matched
	beqz $t8, end
	addi $t6, $t6, 1
	bne $t8, $t1, nomatch
	addi $t3, $t3, 1
	j loopinterno

		
	
matched:
	addi $t4, $t4, 1
	add $t2,$t2,11
	j hash2
	
nuevohash:
	lb $t1, texto($t2)
	beqz $t1, end
	sub $t9,$t9,$t1
	div $t9, $t9, 2
	li $t5, 1024
	add $t1, $t2, 11
	lb $t1, texto($t1)
	beqz $t1, end
	mul $t5, $t5, $t1
	add $t9, $t9, $t5 
nomatch:	
	add $t2,$t2, 1
	j finhash2


end:
	
	
