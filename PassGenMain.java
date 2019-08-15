/*
 * Author: Brendon Baughn
 * Version: 1.3.5
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

	public static PrivateInfo readNextSet(Scanner scan, PrivateInfo pi) {
//		PrivateInfo pi = new PrivateInfo("", "", "", "");

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

	public static String checkName(Scanner in, String name, int nameType) {
		String type = "";
		if (nameType == 0) {
			type = "firstname";
		} else {
			type = "lastname";
		}
		while (!Character.isUpperCase(name.charAt(0))) {
			System.out.println("You must start your name with a capital letter!");
			System.out.print("Enter your " +  type +" name: ");
			name = in.nextLine();
		}

		return name;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		List<PrivateInfo> list = new ArrayList<PrivateInfo>();
		Crypto crypto = new basicCrypto();

		System.out.print("Are you a new user? [Y/N]: ");
		String newUser = in.nextLine().toUpperCase();

		while (newUser.isEmpty()) {
			System.out.print("Please Enter a Y or N: ");
			newUser = in.nextLine().toUpperCase();
		}
		newUser = newUser.substring(0, 1);

		if (newUser.equals("N")) {

			File inFile = new File("Text.txt");
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
					pi = readNextSet(inScan, pi);
					list.add(pi);
				}
				
				int count = 0;
				for (int i = 0; i < list.size(); ++i) {
					PrivateInfo myPI = list.get(i);
					String dec = new String(crypto.decrypt(myPI.getPass().getBytes()));

					if (user.equals(myPI.getUser())) { // If username is correct
						if (pass.equals(dec)) { // if username and password is correct
							System.out.println("Welcome " + myPI.getName() + "!");
							++count;
						} else { // if username is correct but password is wrong
							System.out.println("Username and/or password does not exist.");
							++count;
						}
					} else if (!user.equals(myPI.getUser())) { // if username is incorrect
						if (i == list.size() - 1 && count == 0) {
							System.out.println("Username and password is incorrect");
						}
					}
				}
			}
			inScan.close();
		} else if (newUser.equals("Y")) {
			System.out.print("Enter your first name: ");
			String fname = in.nextLine();

			fname = checkName(in, fname, 0);

			System.out.print("Enter your last name: ");
			String lname = in.nextLine();

			lname = checkName(in, lname, 1);

			System.out.print("Username: ");
			String username = in.nextLine();

			System.out.print("Password: ");
			String password = in.nextLine();

			try {
				File textFile = new File("Text.txt");

				FileWriter pw = new FileWriter(textFile, true);

				String enc = new String(crypto.encrypt(password.getBytes()));

				pw.write(fname + " " + lname + "\n" + username + "\n" + enc + "\n" + "-1" + "\n");

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
