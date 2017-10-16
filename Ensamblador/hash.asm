lim 10 0
lim 0 0
lim 2 1
lim 3 0
lim 4 0
lim 5 0
lim 6 0
lim 7 0
lim 8 0
addr 11 13 14
addr 1 13 14
hashpatron:
lbm 7 15 0
mulr 8 7 2
addr 3 3 8
sllr 2 2 1
addinm 0 0 1
beqi 0 11 continua1
jump hashpatron
continua1:
lim 2 1
hashinicialtexto:
lbm 7 1 12
mulr 8 7 2
addr 5 5 8
sllr 2 2 1
addinm 1 1 1
addinm 6 6 1
beqi 6 11 continua2
jump hashinicialtexto
continua2:
srlr 2 2 1
iteraciongeneral:
beqr 3 5 matched2
calcularnuevohash:
lbm 8 1 12
addinm 1 1 1
beqzero 8 end2
lbm 7 11 12
addinm 11 11 1
subr 5 5 7
divri 5 5 2
mulr 9 8 2
addr 5 5 9
jump iteraciongeneral
matched2:
addinm 4 4 1
jump calcularnuevohash
end2:
lim 10 1