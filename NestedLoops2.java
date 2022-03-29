package lab8;
import java.util.Scanner;
public class NestedLoops2 {

	public static void main(String[] args) {
		Scanner userInt = new Scanner(System.in);
		System.out.println("Choose an integer for the amount of rows in the program: ");
		int userInteger = userInt.nextInt();
		for (int counter = 1; counter <= userInteger; counter++) {
			
			for (int counter2 = 1; counter2 <= userInteger - counter; counter2++) {
				System.out.print("////");
			}
			for (int counter4 = 1; counter4 < counter * 2 - 1; counter4++) {
				System.out.print("****");
			}
			for (int counter3 = 1; counter3 <= userInteger - counter; counter3++) {
				System.out.print("\\\\\\\\");
			}
			System.out.println();
		}
	}

}
