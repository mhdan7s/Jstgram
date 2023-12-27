/**
 * This class containes several windows for jstgram app.
 *
 * @mmohammedanas2 11/15/2023
 * @hzheng9 11/15/2023
 */

public class Views extends Main {
	// ANSI escape code for resetting color
	public static final String ANSI_RESET = "\u001B[0m";
	// ANSI escape code for green color
	public static final String ANSI_Green = "\u001B[32m";
	// ANSI escape code for yellow color
	public static final String ANSI_Yellow = "\u001B[33m";
	// ANSI escape code for blue color
	public static final String ANSI_Blue = "\u001B[34m";
	// ANSI escape code for cyan color
    public static final String ANSI_Cyan = "\u001B[36m";
	
	// window to display the main window
	public static void mainWindow() {
		// creates a new Database object
		Database database = new Database();
		// variable to store the number of users in the database
		int count = database.getCount();
		System.out.println(ANSI_Green + " ========================================");
		System.out.println("|         Welcome to Jstgram 2.0!        |");
		System.out.println("|                                        |");
		System.out.println("|              *************             |");
		System.out.println("|                    *                   |");
		System.out.println("|                    *                   |");
		System.out.println("|                    *                   |");
		System.out.println("|                    *                   |");
		System.out.println("|              *     *                   |");
		System.out.println("|              *******                   |");
		System.out.println("|                                        |");
		System.out.println(String.format("|  Current number of user in database: %d |", count));
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.println(" ========================================" + ANSI_RESET);
	}

	// window to display the account window with parameter username
	public static void accountWindow(String userName) {
		System.out.println(ANSI_Blue + " ========================================");
		System.out.println("|                                        |");
		System.out.println("|   (P) Posts                            |");
		System.out.println("|   (V) Post Visibility                  |");
		System.out.println("|   (Q) Quit                             |");
		System.out.println("|   (L) Logout                           |");
		System.out.println("|   (C) Close Account                    |");
		System.out.println("|                                        |");
		System.out.printf("|   Current user:%-24s|\n", userName);
		System.out.println("|                                        |");
		System.out.println(" ========================================" + ANSI_RESET);
	}

	// window to display the post window with parameter username
	public static void postWindow(String userName) {
		Database database = new Database();
		System.out.println(ANSI_Cyan + " ========================================");
		System.out.println("|   My posts and visible posts           |");
		database.retrievePosts(userName);
		System.out.println("|                                        |");
		System.out.println("|   (+) Publish a new post               |");
		System.out.println("|   (B) Back                             |");
		System.out.println("|                                        |");
		System.out.printf("|   Current user:%-24s|\n", userName);
		System.out.println(" ========================================" + ANSI_RESET);
	}
	
	// window to display visibility window with parameter username
	public static void visibilityWindow(String userName) {
		Database database = new Database();
		System.out.println(ANSI_Cyan + " ===========================================");
		System.out.println("|   My posts are visible to following users |");
		System.out.println("|                                           |");
		database.visibleList(userName);
		System.out.println("|                                           |");
		System.out.println("|   (+) Add a user                          |");
		System.out.println("|   (-) Delete a user                       |");
		System.out.println("|   (B) Back                                |");
		System.out.println("|                                           |");
		System.out.printf("|   Current user:%-27s|\n", userName);
		System.out.println(" ===========================================" + ANSI_RESET);
	}
	
	// window to display when a new user is registered
	public static void registerWindow() {
		System.out.println(ANSI_Yellow + " ========================================");
		System.out.println("|                                        |");
		System.out.println("|           Account Registered           |");
		System.out.println("|                                        |");
		System.out.println(" ========================================" + ANSI_RESET);
	}
	
	// window to display when an existing user logs in successfully
	public static void loginWindow() {
		System.out.println(ANSI_Yellow + " ========================================");
		System.out.println("|                                        |");
		System.out.println("|         Logged In Successfully         |");
		System.out.println("|                                        |");
		System.out.println(" ========================================" + ANSI_RESET);
	}
	
	// window to display when an existing user logs out successfully
		public static void logoutWindow() {
			System.out.println(ANSI_Yellow + " ========================================");
			System.out.println("|                                        |");
			System.out.println("|         Logged Out Successfully        |");
			System.out.println("|                                        |");
			System.out.println(" ========================================" + ANSI_RESET);
		}
	
	public static void passwordWindow() {
		System.out.println(ANSI_Yellow + " ========================================");
		System.out.println("|                                        |");
		System.out.println("|           Incorrect Password           |");
		System.out.println("|                                        |");
		System.out.println(" ========================================" + ANSI_RESET);
	}
	
	// window to display when an existing user closes their account
	public static void accountCloseWindow() {
		System.out.println(ANSI_Yellow + " ========================================");
		System.out.println("|                                        |");
		System.out.println("|      Account Closed Successfully       |");
		System.out.println("|                                        |");
		System.out.println(" ========================================" + ANSI_RESET);
	}
	
	public static void errorWindow() {
		System.out.println(ANSI_Yellow + " ========================================");
		System.out.println("|                                        |");
		System.out.println("|         Account Closure Failed         |");
		System.out.println("|                                        |");
		System.out.println(" ========================================" + ANSI_RESET);
	}
	
	// window to display when the user quits the program
	public static void quitWindow() {
		System.out.println(ANSI_Yellow + " ========================================");
		System.out.println("|                                        |");
		System.out.println("|    Thank You For Using Our System!     |");
		System.out.println("|                                        |");
		System.out.println(" ========================================" + ANSI_RESET);
	}

}
