package appserver.job.impl;

/**
 *
 * @author peter
 */
public class FibonacciAux {
    
    Integer number = null;
    
    public FibonacciAux (Integer number) {
        this.number = number;
    }
    
    public Integer getResult () {
        return fib(number);
    }
    
    private static int fib(int n) {
        if (n == 1)
            return 0;
        if (n == 2)
            return 1;
 
        return fib(n - 1) + fib(n - 2);
    }
}
