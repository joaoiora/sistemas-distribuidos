struct operandos {
    int x;
    int y;
};

program PROG {
    version VERSAO {
		int ADD(operandos) = 1;
		int SUB(operandos) = 2;
        float MULT(operandos) = 3;
        float DIV(operandos) = 4;
	} = 110;
} = 55555555;
