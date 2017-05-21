#include <stdio.h>
#include "calcula.h"

int* add_110_svc(operandos *ops, struct svc_req *rqstp) {
	static int  result;
    printf("Requisição de adicao para %d e %d.\n", ops->x, ops->y);
    result = ops->x + ops->y;
	return &result;
}

int* sub_110_svc(operandos *ops, struct svc_req *rqstp) {
	static int  result;
    printf("Requisição de subtracao para %d e %d\n", ops->x, ops->y);
    result = ops->x - ops->y;
	return &result;
}

float* mult_110_svc(operandos *ops, struct svc_req *rqstp) {
	static float  result;
    printf("Requisição de multiplicacao para %d e %d\n", ops->x, ops->y);
    float x = (float) ops->x;
    float y = (float) ops->y;
    result = x * y;
	return &result;
}

float* div_110_svc(operandos *ops, struct svc_req *rqstp) {
	static float  result;
    printf("Requisição de divisao para %d e %d\n", ops->x, ops->y);
	if (ops->x == 0 || ops->y == 0) {
        result = 0.0D;
    } else {
        float x = (float) ops->x;
        float y = (float) ops->y;
        result = x / y;
    }
	return &result;
}
