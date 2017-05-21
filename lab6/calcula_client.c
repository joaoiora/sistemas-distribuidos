#include <stdio.h>
#include "calcula.h"

void prog_110(char* host, operandos ops);
int adicao(CLIENT* clnt, operandos ops);
int subtracao(CLIENT* clnt, operandos ops);
int multiplicacao(CLIENT* clnt, operandos ops);
int divisao(CLIENT* clnt, operandos ops);

int main (int argc, char *argv[]) {
	char *host;
	if (argc != 4) {
		printf ("usage: %s <hostname> <num1> <num2>\n", argv[0]);
		exit(1);
	}
	host = argv[1];
    operandos ops;
    ops.x = atoi(argv[2]);
    ops.y = atoi(argv[3]);
	prog_110(host, ops);
    exit(0);
}

void prog_110(char *host, operandos ops) {
	CLIENT *clnt;

	clnt = clnt_create (host, PROG, VERSAO, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}

    printf("%d + %d = %d\n", ops.x, ops.y, adicao(clnt, ops));
    printf("%d - %d = %d\n", ops.x, ops.y, subtracao(clnt, ops));
    printf("%d x %d = %d\n", ops.x, ops.y, multiplicacao(clnt, ops));
    printf("%d / %d = %fd\n", ops.x, ops.y, divisao(clnt, ops));

	clnt_destroy (clnt);
}

int adicao(CLIENT* clnt, operandos ops) {
    int* result = add_110(&ops, clnt);
    if (result == NULL) {
        fprintf(stderr, "Problema na chamada RPC da função add().\n");
        exit(1);
    }
    return *result;
}

int subtracao(CLIENT* clnt, operandos ops) {
    int* result = sub_110(&ops, clnt);
    if (result == NULL) {
        fprintf(stderr, "Problema na chamada RPC da função sub().\n");
        exit(1);
    }
    return *result;
}

int multiplicacao(CLIENT* clnt, operandos ops) {
    int* result = mult_110(&ops, clnt);
    if (result == NULL) {
        fprintf(stderr, "Problema na chamada RPC da função mult().\n");
        exit(1);
    }
    return *result;
}

int divisao(CLIENT* clnt, operandos ops) {
    int* result = div_110(&ops, clnt);
    if (result == NULL) {
        fprintf(stderr, "Problema na chamada RPC da função div().\n");
        exit(1);
    }
    return *result;
}
