/**
 * Part 2
 * @author Adam Pine, Ben Uleau, Josh McMillen
 * 
 */
public class part2 {
	public static void main(String[] args) {
		double value = 0;
		for (int i = -4; i < 4; i++){
			//division by zero will cause an error/unexpected behavior at a specific count through the for loop.
			//the actual value should be 1, because the limit of t -> 0 for sin(t)/t = 1.
			value = doFunction(i);
			System.out.println(value);
		}
	}	
	public static double doFunction(double t){
		return Math.sin(t)/t;
	}
}
