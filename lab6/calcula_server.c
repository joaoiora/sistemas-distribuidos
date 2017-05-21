#include "calcula.h"

int* add_110_svc(operandos *ops, struct svc_req *rqstp) {
	static int  result;
    printf("Processando adição entre %d e %d.\n", ops->x, ops->y);
    result = ops->x + ops->y;
	return &result;
}

int* sub_110_svc(operandos *ops, struct svc_req *rqstp) {
	static int  result;
    printf("Processando subtração entre %d e %d.\n", ops->x, ops->y);
    result = ops->x - ops->y;
	return &result;
}

int* mult_110_svc(operandos *ops, struct svc_req *rqstp) {
	static int  result;
    printf("Processando multiplicação entre %d e %d.\n", ops->x, ops->y);
    result = ops->x * ops->y;
	return &result;
}

int* div_110_svc(operandos *ops, struct svc_req *rqstp) {
	static int  result;
    printf("Processando divisão entre %d e %d.\n", ops->x, ops->y);
    int x = ops->x, y = ops->y;
    if (x == 0 || y == 0) result = 0;
    else result = x / y;
	return &result;
}
