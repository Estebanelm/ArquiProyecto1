lb 2 13 12
li 4 0
loopexterno:
li 3 0
loopinterno:
lb 0 14 2
beqz 0 end
lb 1 15 3
beqz 1 matched
addi 2 2 1
bne 0 1 loopexterno
addi 3 3 1
j loopinterno
matched:
addi 4 4 1
j loopexterno
end:

inicio: .word 5
texto: .asciiz "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eget metus eget risus sagittis scelerisque eu eget magna. Integer rhoncus velit in aliquam posuere. Phasellus varius et lorem vitae dictum. Aenean pulvinar elit sit amet euismod elementum. Aenean in mollis nibh, sed pretium odio. Nam sit amet magna quis sem tempus sodales hendrerit ut justo. Nullam ultrices nisl vel nulla tincidunt dapibus. Quisque rhoncus vestibulum elit eu gravida. Quisque et blandit leo. Pellentesque consequat sit amet odio nec varius. Donec luctus, lorem et iaculis rhoncus, turpis purus porttitor elit, nec auctor ipsum nibh nec metus. Suspendisse et felis sed urna auctor gravida a ut felis. Praesent ullamcorper porta purus, quis porttitor lorem laoreet quis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vel dolor quis diam eleifend laoreet. Mauris iaculis ex cursus lectus molestie laoreet. Vestibulum ultricies, enim sed ultrices ultricies, nibh turpis interdum erat, vel vehicula nulla risus pellentesque eros. Quisque vitae tellus scelerisque, aliquam felis id, ornare nibh. Sed gravida commodo volutpat. Nam elementum faucibus urna, vitae egestas massa finibus in. Phasellus ut nulla orci. Phasellus quis tristique magna. Aenean vitae lacinia risus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed facilisis a massa in vehicula. Nulla posuere arcu metus, vel convallis lectus porttitor eget. Vestibulum luctus nisi et est vulputate, et pharetra justo tincidunt. Aliquam ante quam, aliquet et placerat scelerisque, blandit in eros. Aenean ut ultrices mi, et auctor magna. Morbi sed dolor rhoncus neque convallis aliquam. Duis id orci id leo tempus congue. Proin faucibus, diam et lobortis ornare, tortor ligula vestibulum mauris, quis venenatis lorem est ut tellus. Vestibulum et mi ac nibh consequat luctus in id sapien. Duis et vehicula libero, sit amet tincidunt libero. Maecenas iaculis libero augue, non vulputate mi accumsan eu. Suspendisse dapibus diam ac augue placerat, ut fermentum est consequat. Proin sed turpis elit. In interdum sit amet nulla at cursus. Pellentesque malesuada lectus ligula, nec lobortis leo cursus ut."
match: .asciiz "Lorem ipsum"
	.text
	
//Notas
--El valor de inicio se guardará en el registro 13
--El valor inicial del texto se guarda en el registro 14
--El valor inicial del texto match se guarda en el registro 15
--El registro 12 contiene 00000000 en todo momento.