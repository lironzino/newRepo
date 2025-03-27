import java.util.Scanner;

public class TestThreadCheckArray {
	public static void main(String[] args) {
		try (Scanner input = new Scanner(System.in)) {
			Thread thread1, thread2;
			System.out.println("Enter array size");
			int num  = input.nextInt();
			int [] array = new int[num];
			System.out.println("Enter numbers for array");
			
			for (int index = 0; index < num; index++) 
				array[index] = input.nextInt();
			
			System.out.println("Enter number");
			num = input.nextInt();
			
			SharedData sd = new SharedData(array, num);
			
			ThreadCheckArray tca1 = new ThreadCheckArray(sd);
			ThreadCheckArray tca2 = new ThreadCheckArray(sd);
			thread1 = new Thread(tca1, "thread1");
			thread2 = new Thread(tca2, "thread2");
			thread1.start();
			thread2.start();
			try 
			{
				thread1.join();
				thread2.join();
				if (tca1.isWinner())
					System.out.println("thread1 found the solution first!");
				else if (tca2.isWinner())
					System.out.println("thread2 found the solution first!");
				else
					System.out.println("No thread found the solution.");
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			if (!sd.getFlag())
			{
				System.out.println("Sorry");
				return;
			}
			System.out.println("Solution for b : " + sd.getB() + ",n = " + sd.getArray().length);
			System.out.print("I:    ");
			for(int index = 0; index < sd.getArray().length ; index++)
				System.out.print(index + "    ");
			System.out.println();
			System.out.print("A:    ");
			for (int index : sd.getArray())
			{
				System.out.print(index);
				int counter = 5;
				while (true)
				{
					index = index / 10;
					counter--;
					if (index == 0)
						break;
				}
				for (int i = 0; i < counter; i++)
					System.out.print(" ");
			}

			System.out.println();
			System.out.print("C:    ");
			for (boolean index : sd.getWinArray())
			{
				if (index)
					System.out.print("1    ");
				else
					System.out.print("0    ");	
			}

			System.out.println();

			System.out.print(sd.getB() + " = ");
			boolean first = true;
			for (int i = 0; i < sd.getArray().length; i++) {
				if (sd.getWinArray()[i]) {
					if (!first)
						System.out.print(" + ");
					System.out.print(sd.getArray()[i]);
					first = false;
				}
			}
			System.out.println();
		}
	}

}
