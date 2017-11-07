#include <stdio.h>
#include "calcula.h"

void prog_110(char* host, operandos ops);
int adicao(CLIENT* clnt, operandos ops);
int subtracao(CLIENT* clnt, operandos ops);
float multiplicacao(CLIENT* clnt, operandos ops);
float divisao(CLIENT* clnt, operandos ops);

int main (int argc, char *argv[]) {
	char *host = NULL;
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

void prog_110(char* host, operandos ops) {
	CLIENT* clnt;
#ifndef	DEBUG
	clnt = clnt_create (host, PROG, VERSAO, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */

	printf("%d + %d = %d\n", ops.x, ops.y, adicao(clnt, ops));
	printf("%d - %d = %d\n", ops.x, ops.y, subtracao(clnt, ops));
	printf("%d * %d = %.2f\n", ops.x, ops.y, multiplicacao(clnt, ops));
	printf("%d / %d = %.2f\n", ops.x, ops.y, divisao(clnt, ops));

#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */

}

int adicao(CLIENT *clnt, operandos ops) {
    int *result = add_110(&ops, clnt);
    if (result == (int *) NULL) {
        fprintf(stderr, "Erro na chamada RPC da função add().");
        exit(0);
    }
    return *result;
}

int subtracao(CLIENT *clnt, operandos ops) {
    int *result = sub_110(&ops, clnt);
    if (result == (int *)NULL) {
        fprintf(stderr, "Erro na chamada RPC da função add().");
        exit(0);
    }
    return *result;
}

float multiplicacao(CLIENT *clnt, operandos ops) {
	float *result = mult_110(&ops, clnt);
    if (result == (float *)NULL) {
        fprintf(stderr, "Erro na chamada RPC da função add().");
        exit(0);
    }
    return *result;
}

float divisao(CLIENT *clnt, operandos ops) {
	float *result = div_110(&ops, clnt);
    if (result == (float *)NULL) {
        fprintf(stderr, "Erro na chamada RPC da função add().");
        exit(0);
    }
    return *result;
}
