/*
 * Author: Brendon Baughn
 * Version: 1.2
 * 
 * A viable password authenticator that is able to add new users
 */
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
public class PassGenMain {

	public static PrivateInfo readNextSet(Scanner scan) {
		PrivateInfo pi = new PrivateInfo("", "", "", "");

		boolean boo = false;
		while (boo == false) {
			String name = scan.nextLine();
			int space = name.indexOf(" ");
			String firstname = name.substring(0, space);
			String lastname = name.substring(space + 1);
			String username = scan.nextLine();
			String password = scan.nextLine();

			pi = new PrivateInfo(firstname, lastname, username, password);

			String shouldBeNeg = scan.nextLine();

			if (shouldBeNeg.equals("-1")) {
				boo = true;
			}
		}

		return pi;
	}
	
	public static String checkName(Scanner in, String name) {
		
		while (!Character.isUpperCase(name.charAt(0))) {
			System.out.println("You must start your name with a capital letter!");
			System.out.print("Enter your first name: ");
			name = in.nextLine();
		}
		
		return name;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		List<PrivateInfo> list = new ArrayList<PrivateInfo>();

		System.out.print("Are you a new user? [Y/N]: ");
		String newUser = in.nextLine().toUpperCase();

		while (newUser.isEmpty()) {
			System.out.print("Please Enter a Y or N: ");
			newUser = in.nextLine().toUpperCase();
		}
		newUser = newUser.substring(0, 1);

		if (newUser.equals("N")) {

			File inFile = new File("Text.txt"); // Your external file
			Scanner inScan = new Scanner(inFile);
			PrivateInfo pi = new PrivateInfo("", "", "", "");

			if (!inScan.hasNext()) {
				System.out.println("Sorry! No Users Recorded!");
			} else {

				System.out.print("Enter Your Username: ");
				String user = in.nextLine();

				System.out.print("Enter Your Password: ");
				String pass = in.nextLine();


				while (inScan.hasNext()) {
					pi = readNextSet(inScan);
					list.add(pi);
				}

				for (int i = 0; i < list.size(); ++i) {
					PrivateInfo myPI = list.get(i);

					if (user.equals(myPI.getUser())) { // If username is correct
						if (pass.equals(myPI.getPass())) { // if username and password is correct
							System.out.println("Welcome " + myPI.getName() + "!");
						} else { // if username is correct but password is wrong
							System.out.println("Username and/or password does not exist.");
						}
					} else if (!user.equals(myPI.getUser()) && pass.equals(myPI.getPass())) { // if username is wrong but password is correct
						System.out.println("Username and/or password does not exist.");
					}
				}
			}
			inScan.close();
		} else if (newUser.equals("Y")) {
			System.out.print("Enter your first name: ");
			String fname = in.nextLine();

			fname = checkName(in, fname);

			System.out.print("Enter your last name: ");
			String lname = in.nextLine();

			lname = checkName(in, lname);

			System.out.print("Username: ");
			String username = in.nextLine();

			System.out.print("Password: ");
			String password = in.nextLine();

			try {
				File textFile = new File("Text.txt");
				
				// Use FileWriter instead of PrintWriter. The former will add to a file, the latter will overwrite a file
				FileWriter pw = new FileWriter(textFile, true);

				pw.write(fname + " " + lname); // This line will go into the empty space
				pw.write("\n");
				pw.write(username);
				pw.write("\n");
				pw.write(password);
				pw.write("\n");
				pw.write("-1");
				pw.write("\n"); // Indicates a new line for a new user

				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println();
			System.out.println("Thank You " + fname + " " + lname + "!");
		} else {
			System.out.println("Not a valid argument. Goodbye!");
		}

		in.close();
	}

}
