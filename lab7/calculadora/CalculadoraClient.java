package br.ucb.joaoiora.client;

import br.ucb.joaoiora.model.Calculadora;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;

/**
 * Created by joaocarlos on 22/05/17.
 */
public class CalculadoraClient {

    /**
     *
     */
    private static final int MIN_ARGS = 3;

    /**
     *
     */
    private static final String[] OPERACOES = {"soma", "sub", "mult", "div"};

    private static final String FLG_ALL = "all";

    /**
     *
     */
    private String host;

    /**
     *
     */
    private String operacao;

    /**
     *
     */
    private int x;

    /**
     *
     */
    private int y;

    /**
     * usage: CalculadoraClient [host] [x <int>] [y <int>]
     * usage: CalculadoraClient [host] [operacao (soma|sub|mult|div)] [x <int>] [y <int>]
     *
     * @param args
     */
    public static void main(String[] args) {
        CalculadoraClient client = new CalculadoraClient();
        client.handleArguments(args);
        client.requestOperation();
    }

    /**
     * @param args
     */
    private void handleArguments(String[] args) {
        if (args.length < MIN_ARGS) {
            System.out.println("usage: CalculadoraClient [host] [operacao (soma|sub|mult|div)] [x <int>] [y <int>]");
            System.out.println("Se a operacao nao e informada, todas serao executadas.");
            System.exit(-1);
        }
        this.host = args[0];
        if (!Arrays.asList(OPERACOES).contains(args[1])) {
            this.operacao = FLG_ALL;
            this.x = Integer.parseInt(args[1]);
            this.y = Integer.parseInt(args[2]);
        } else {
            this.operacao = args[1];
            this.x = Integer.parseInt(args[2]);
            this.y = Integer.parseInt(args[3]);
        }
    }

    /**
     *
     */
    private void requestOperation() {
        try {
            Calculadora calculadora = (Calculadora) Naming.lookup("rmi://" + host + "/Calculadora");
            doOperation(calculadora);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param calculadora
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void doOperation(final Calculadora calculadora) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (operacao.equals(FLG_ALL)) {
            for (String op : OPERACOES) {
                executeMethod(getMethod(calculadora, op), op, calculadora);
            }
        } else {
            executeMethod(getMethod(calculadora, operacao), operacao, calculadora);
        }
    }

    /**
     * @param calculadora
     * @param methodName
     * @return
     * @throws NoSuchMethodException
     */
    private Method getMethod(final Calculadora calculadora, final String methodName) throws NoSuchMethodException {
        return calculadora.getClass().getMethod(methodName, int.class, int.class);
    }

    /**
     * @param method
     * @param operacao
     * @param calculadora
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void executeMethod(Method method, String operacao, Calculadora calculadora) throws InvocationTargetException, IllegalAccessException {
        int result = (int) method.invoke(calculadora, this.x, this.y);
        System.out.println("Operação '" + operacao + "' entre " + this.x + " e " + this.y + " resultou no valor: " + result);
    }

}
