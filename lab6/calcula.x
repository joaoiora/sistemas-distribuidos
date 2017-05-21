struct operandos {
    int x;
    int y;
};

program PROG {
    version VERSAO {
		int ADD(operandos) = 1;
		int SUB(operandos) = 2;
        int MULT(operandos) = 3;
        int DIV(operandos) = 4;
	} = 110;
} = 55555555;
