/**
 
 * @author Adam Pine
 *
 */
public class part1 {
	public static void main(String[] args) {
		int variableOverflow = 0;
		for (int i = 0; i<2000; i++){
			variableOverflow = fibonacciLoop(i);
			System.out.println(variableOverflow);
		}
	}

    // Java program for Fibonacci number using Loop.
    public static int fibonacciLoop(int number){
        if(number == 1 || number == 2){
            return 1;
        }
        int fibo1=1, fibo2=1, fibonacci=1;
        for(int i= 3; i<= number; i++){
            fibonacci = fibo1 + fibo2; //Fibonacci number is sum of previous two Fibonacci number
            fibo1 = fibo2;
            fibo2 = fibonacci;
 
        }
        return fibonacci; //Fibonacci number
    }   
}
