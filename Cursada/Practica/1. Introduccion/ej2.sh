#!/bin/bash

# la ventaja de sendfile es que, al no tener copias redundantes entre el espacio de kernel y de user,
# ahorra ciclos de CPU haciendo copias a partir de DMAs.
# aparte, sendfile puede llamarse con otras syscalls, reduciendo el tiempo de syscalls...

for i in 0 1 512 1024 2048 4096; do
	echo archivo-out$i
	time ./copy archivo-in archivo-out$i $i
done
