package appserver.job.impl;

import appserver.job.Tool;

/**
 *
 * @author peter
 */
public class Fibonacci implements Tool {
    FibonacciAux fibonacciHelper = null;
    
    @Override
    public Object go(Object parameters) {
        
        fibonacciHelper = new FibonacciAux ((Integer) parameters);
        return fibonacciHelper.getResult();
    }
}
