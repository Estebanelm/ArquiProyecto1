##Algoritmo de fuerza bruta.
##Explicaci�n:
##Se utilizan 2 contadores para recorrer ambos strings.
##No se utiliza la longitud de los strings como punto final,
##se utiliza el valor NULL en ascii (00). Esto facilita el poder
##ingresar strings de cualquier tama�o sin tener que contarlas.
##El recorrido se realiza aumentando el contador y utilizando
##las etiquetas como punto inicial y se les suma el contador para
##obtener la direcci�n de b�squeda.
##Se va cargando los valores por byte (caracter) y se comparan
##si no son iguales se devuelve al inicio y se carga otro caracter;
##si son iguales se agrega en 1 el contador del segundo string y se
##contin�a el proceso. Si el segundo string llega al caracter 00, se
##suma en 1 el contador de matches encontrados y se reinicia el proceso.
##Registros: $t0 caracter 1, $t1 caracter 2, $t2 contador 1
## 	     $t3 contador 2, $t4 contador de matches

	.data
inicio: .word 5
texto: .asciiz "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eget metus eget risus sagittis scelerisque eu eget magna. Integer rhoncus velit in aliquam posuere. Phasellus varius et lorem vitae dictum. Aenean pulvinar elit sit amet euismod elementum. Aenean in mollis nibh, sed pretium odio. Nam sit amet magna quis sem tempus sodales hendrerit ut justo. Nullam ultrices nisl vel nulla tincidunt dapibus. Quisque rhoncus vestibulum elit eu gravida. Quisque et blandit leo. Pellentesque consequat sit amet odio nec varius. Donec luctus, lorem et iaculis rhoncus, turpis purus porttitor elit, nec auctor ipsum nibh nec metus. Suspendisse et felis sed urna auctor gravida a ut felis. Praesent ullamcorper porta purus, quis porttitor lorem laoreet quis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vel dolor quis diam eleifend laoreet. Mauris iaculis ex cursus lectus molestie laoreet. Vestibulum ultricies, enim sed ultrices ultricies, nibh turpis interdum erat, vel vehicula nulla risus pellentesque eros. Quisque vitae tellus scelerisque, aliquam felis id, ornare nibh. Sed gravida commodo volutpat. Nam elementum faucibus urna, vitae egestas massa finibus in. Phasellus ut nulla orci. Phasellus quis tristique magna. Aenean vitae lacinia risus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed facilisis a massa in vehicula. Nulla posuere arcu metus, vel convallis lectus porttitor eget. Vestibulum luctus nisi et est vulputate, et pharetra justo tincidunt. Aliquam ante quam, aliquet et placerat scelerisque, blandit in eros. Aenean ut ultrices mi, et auctor magna. Morbi sed dolor rhoncus neque convallis aliquam. Duis id orci id leo tempus congue. Proin faucibus, diam et lobortis ornare, tortor ligula vestibulum mauris, quis venenatis lorem est ut tellus. Vestibulum et mi ac nibh consequat luctus in id sapien. Duis et vehicula libero, sit amet tincidunt libero. Maecenas iaculis libero augue, non vulputate mi accumsan eu. Suspendisse dapibus diam ac augue placerat, ut fermentum est consequat. Proin sed turpis elit. In interdum sit amet nulla at cursus. Pellentesque malesuada lectus ligula, nec lobortis leo cursus ut."
match: .asciiz "Lorem ipsum"
	.text
	lw $t2, inicio
	li $t4, 0
loopexterno:
	li $t3, 0
loopinterno:
	lb $t0, texto($t2)
	beqz $t0, end
	lb $t1, match($t3)
	beqz $t1, matched
	addi $t2, $t2, 1
	bne $t0, $t1, loopexterno
	addi $t3, $t3, 1
	j loopinterno
matched:
	addi $t4, $t4, 1
	j loopexterno
end:
	add $t0, $zero, 1 ##Esta linea est� solo para poder hacer un breakpoint a la hora de debuguear
	
