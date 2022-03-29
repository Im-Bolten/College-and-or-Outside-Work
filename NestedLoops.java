package lab7;
import java.util.Scanner;
public class NestedLoops {
	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		System.out.println("Please enter a positive integer (number of rows): ");
		int userNum = userInput.nextInt();
		while (userNum <= 0) {
			System.out.println("Please enter a positive integer... again (number of rows): ");
			userNum = userInput.nextInt();
		}
		System.out.println("Part 1 of the lab");
		for (int count1 = userNum; count1 > 0; count1--) {
			for (int count2 = count1; count2 > 0; count2--)
				System.out.print("*");
			System.out.println("");
		}
		System.out.print("\n");
		System.out.println("Part 2 of the lab");
		// Part 2 of the lab
		for (int count1 = 1; count1 <= userNum; count1++) {
			for (int count2 = 1; count2 < count1; count2++)
				System.out.print(" ");
			for (int count4 = 1; count4 <= userNum - count1 + 1; count4++)
				System.out.print(count4);
			System.out.println("");
		}
	}
}
