package in.ravidsrk.sample;

public class Calculator {

    public int add(int op1, int op2) {
        return op1 + op2;
    }

    public int diff(int op1, int op2) {
        return op1 - op2;
    }

    public double div(int op1, int op2) {
        // if (op2 == 0) return 0;
        return op1 / op2;
    }
}
