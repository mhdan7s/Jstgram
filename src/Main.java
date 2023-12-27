import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * The main class through which all the functions and methods are called.
 * 
 * @mmohammedanas2 11/15/2023
 * @hzheng9 11/15/2023
 */
public class Main {
	public static void main(String[] args) {
		// variable to store the postTime
		LocalDateTime postTime;
		// creating new Database object
		Database database = new Database();
		// infinite while loop
		while (true) {
			// Displaying the main window
			Views.mainWindow();
			// prompting the user to enter username
			System.out.println("Type your username:");
			// creating a new scanner object.
			Scanner input = new Scanner(System.in);
			// reading the username from the user input.
			String username = input.nextLine();
			// prompting the user to enter password
			System.out.println("Type your password:");
			// reading the password from user input.
			String password = input.nextLine();
			// logging in the user
			boolean loggedIn = database.loginUser(username, password);
			// running the loop until the user is logged in.
			while (loggedIn) {
				// displaying the account window with parameter username.
				Views.accountWindow(username);
				// prompting the user to make a choice
				System.out.print("Your choice:");
				// reading the user's choice.
				String choice = input.nextLine();
				// if condition to check if the user chooses to post a post.
				if (choice.equals("P") || (choice.equals("p"))) {
					// displaying the postWindow with parameter as username.
					Views.postWindow(username);
					// prompting the user to make choice.
					System.out.print("Your choice:");
					// reading the post from user input/
					String post = input.nextLine();
					// if the user chooses to add a new post
					if (post.equals("+")) {
						// prompting the user to enter a new post.
						System.out.println("Your Post (Less than 40 characters):");
						// reading the post from user input.
						String Posts = input.nextLine();
						// varibale that stores current date and time.
						postTime = LocalDateTime.now();
						// formatting the date and time.
						String s = postTime.format(DateTimeFormatter.ofPattern("hh:mm:ssa, MMM dd, yyyy", Locale.US));
						// storing the post in the database with parameters as username, the post to add
						// and the formatted time string.
						database.storePosts(username, Posts, s);
						// executed if the user chooses to go back.
					} else if ((post.equals("B")) || (post.equals("b"))) {
						// continue to the next iteration of the loop
						continue;
					}
					// if the user chooses to view who can see their posts.
				} else if (choice.equals("V") || choice.equals("v")) {
					// displaying the visibility window with parameter as username.
					Views.visibilityWindow(username);
					// prompting the user to make a choice.
					System.out.print("Your choice:");
					// reading the visibility choice from the user input.
					String visibility = input.nextLine();
					// if the user chooses to add a new user to their visibility list.
					if (visibility.equals("+")) {
						// prompting the user to enter the username to add.
						System.out.println("The username to add:");
						// reading the username to add from the user input.
						String addUser = input.nextLine();
						// adding the user to the visibility list with parameters username and the
						// username to add.
						database.addUser(username, addUser);
						// if the user chooses to delete an existing user from their visibility list.
					} else if (visibility.equals("-")) {
						// prompting the user to enter the username to delete.
						System.out.print("The username to delete:");
						// reading the username to delete from the user input.
						String deleteUser = input.nextLine();
						// deleting the user from the visibility list with parameters username and the
						// username to delete.
						database.deleteUser(username, deleteUser);
						// if the user chooses to go back.
					} else if (visibility.equals("B") || visibility.equals("b")) {
						// continue to the next iteration of the loop.
						continue;
					}
					// if the user chooses to quit.
				} else if (choice.equals("Q")) {
					// exit the program.
					Views.quitWindow();
					input.close();
					return;
				}
				// if the user chooses to close their account
				else if(choice.equals("C") || choice.equals("c")) {
					System.out.println("Enter your password to close your Account:");
					// reading the password to check if it matches
					String closePassword = input.nextLine();
					// calling the method and storing it in a bool variable
					boolean isClosed = database.closeAccount(username, closePassword);
					// if true appropriate message is displayed
					if (isClosed) {
						Views.accountCloseWindow();
						// sets loggedIn to false
						loggedIn = false;
						break;
						// if false appropriate message is displayed
					} else {
						Views.errorWindow();
						continue;
					}
				}
				// if statement to break the loop when the user decides to log out.
				else if(choice.equals("L") || choice.equals("l")) {
					Views.logoutWindow();
					break;
				}
			}
		}
	}

}