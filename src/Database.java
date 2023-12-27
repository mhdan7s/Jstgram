import java.sql.*;

/**
 * This class is called Database, which is used to interact with a MySQL
 * database. It provides methods to store and retrieve user information and
 * posts.
 * 
 * @mmohammedanas2 11/15/2023
 * @hzheng9 11/15/2023
 * 
 */

public class Database {
	// JDBC driver parameters
	public final static String hostname = "cse-linux-01.unl.edu";
	public final static String username = "mmohammedanas2"; // database username
	public final static String password = "aephie5ooQui"; // database password
	public final static String url = "jdbc:mysql://" + hostname + "/" + username;

	/**
	 * This method returns the numbers of users in the database.
	 * 
	 * @return
	 */
	public int getCount() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		// variable to store the number of users
		int count = 0;
		// sql query to select the number of users
		String s = "select count(*) from Users;";
		PreparedStatement prep = null;
		ResultSet rs = null;
		try {
			prep = conn.prepareStatement(s);
			rs = prep.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return the number of users
		return count;
	}

	/**
	 * This method stores a new user in the database.
	 * 
	 * @param userName username of the new user.
	 * @param passWord password of the new user.
	 * 
	 * @mmohammedanas2 11/17/2023
	 * @hzheng9 11/17/2023
	 */

	public void storeUser(String userName, String passWord) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		// sql query to get user information according to the username
		String checkName = "Select * from Users where username = ?;";
		PreparedStatement checkPrep = null;
		ResultSet rs = null;
		try {
			checkPrep = conn.prepareStatement(checkName);
			checkPrep.setString(1, userName);
			rs = checkPrep.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!rs.next()) {
				String s = "INSERT INTO Users (username, password) VALUES (?, ?);";
				PreparedStatement prep = null;
				try {
					prep = conn.prepareStatement(s);
					prep.setString(1, userName);
					prep.setString(2, passWord);
					prep.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method checks if a user's login credentials are correct. if the user
	 * does not exist, is registers the user.
	 * 
	 * @param userName username of the new user.
	 * @param passWord password of the new user.
	 * @return true if login successful, false otherwise.
	 * 
	 * @mmohammedanas2 11/18/2023
	 * @hzheng9 11/18/2023
	 */
	public boolean loginUser(String userName, String passWord) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		// sql query to select password of the current user
		String s = "SELECT password FROM Users where username = ?;";
		PreparedStatement Prep = null;
		ResultSet rs = null;
		String password;
		try {
			Prep = conn.prepareStatement(s);
			Prep.setString(1, userName);
			rs = Prep.executeQuery();
			if (rs.next()) {
				password = rs.getString("password");
				if (password.equals(passWord)) {
					Views.loginWindow();
					return true;
				} else {
					Views.passwordWindow();
				}
			} else {
				storeUser(userName, passWord);
				Views.registerWindow();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method stores the posts made by the user.
	 * 
	 * @param userName username of the new user.
	 * @param posts    the new post made by the user.
	 * @param time     the time when the post was made.
	 * 
	 * @mmohammedanas2 11/19/2023
	 * @hzheng9 11/19/2023
	 */
	public void storePosts(String userName, String posts, String time) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String getId = "SELECT userID FROM Users WHERE username = ?;";
		PreparedStatement prep = null;
		ResultSet rs = null;
		int userId = 0;
		try {
			prep = conn.prepareStatement(getId);
			prep.setString(1, userName);
			rs = prep.executeQuery();
			while (rs.next()) {
				userId = rs.getInt("userID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// sql query to insert information in the table of the current user
		String insertPost = "INSERT INTO Posts (visibleID, postText, postTime) VALUES (?, ?, ?);";
		PreparedStatement insertPrep = null;
		try {
			insertPrep = conn.prepareStatement(insertPost);
			insertPrep.setInt(1, userId);
			insertPrep.setString(2, posts);
			insertPrep.setString(3, time);
			insertPrep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String string = "SELECT userID FROM Users WHERE username = ?";
		PreparedStatement prepString = null;
		ResultSet resultSet = null;
		int addId = 0;
		try {
			prepString = conn.prepareStatement(string);
			prepString.setString(1, userName);
			resultSet = prepString.executeQuery();
			if (resultSet.next()) {
				addId = resultSet.getInt("userID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// sql query to add a user to the visibility list
		String inserting = "INSERT INTO Visibility (userID, visibleID) VALUES (?, ?)";
		PreparedStatement prepareIt = null;
		try {
			prepareIt = conn.prepareStatement(inserting);
			prepareIt.setInt(1, userId);
			prepareIt.setInt(2, addId);
			prepareIt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method retrieves all posts visible to a user.
	 * 
	 * @param userName the username of the current user.
	 * 
	 * @mmohammedanas2 11/20/2023
	 * @hzheng9 11/20/2023
	 */
	public void retrievePosts(String userName) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// sql query to get all the posts related to the current user
		String s = "SELECT Users.username, Posts.postText, Posts.postTime FROM Posts INNER JOIN Users ON Posts.visibleID = Users.userID WHERE Posts.visibleID IN (SELECT userID FROM Visibility WHERE visibleID = (SELECT userID FROM Users WHERE username = ?))";
		PreparedStatement prep = null;
		ResultSet rs = null;

		try {
			prep = conn.prepareStatement(s);
			prep.setString(1, userName);
			rs = prep.executeQuery();

			while (rs.next()) {
				String username = rs.getString("username");
				String postText = rs.getString("postText");
				String postTime = rs.getString("postTime");
				System.out.printf("|   %-37s|\n", postText);
				System.out.printf("|%15s,%-20s|\n", username, postTime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method prints the list of usernames that are visible to the current
	 * user.
	 * 
	 * @param userName username of the user.
	 * 
	 * @mmohammedanas2 11/25/2023
	 * @hzheng9 11/25/2023
	 */
	public void visibleList(String userName) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// sql query to select username of users who can see the current users post
		String getId = " select Users.username from Users inner join(select visibleID from Visibility natural left join Users Where username = ?) as users on Users.userID = users.visibleID;";
		PreparedStatement prep = null;
		ResultSet rs = null;
		try {
			prep = conn.prepareStatement(getId);
			prep.setString(1, userName);
			rs = prep.executeQuery();
			while (rs.next()) {
				String names = rs.getString("username");
				System.out.printf("|   %-40s|\n", names);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method adds a user to the visiblity list of another user.
	 * 
	 * @param userName    the username of user who wants to add another user.
	 * @param addUsername the username of the user to be added in the visibility
	 *                    list.
	 * 
	 * @mmohammedanas2 11/26/2023
	 * @hzheng9 11/26/2023
	 */
	public void addUser(String userName, String addUsername) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String s = "SELECT userID FROM Users WHERE username = ?";
		PreparedStatement prep = null;
		ResultSet rs = null;
		int userId = 0;
		try {
			prep = conn.prepareStatement(s);
			prep.setString(1, userName);
			rs = prep.executeQuery();
			if (rs.next()) {
				userId = rs.getInt("userID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String string = "SELECT userID FROM Users WHERE username = ?";
		PreparedStatement prepString = null;
		ResultSet resultSet = null;
		int addId = 0;
		try {
			prepString = conn.prepareStatement(string);
			prepString.setString(1, addUsername);
			resultSet = prepString.executeQuery();
			if (resultSet.next()) {
				addId = resultSet.getInt("userID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// sql query to add a user to the visibility list
		String inserting = "INSERT INTO Visibility (userID, visibleID) VALUES (?, ?)";
		PreparedStatement prepare = null;
		try {
			prepare = conn.prepareStatement(inserting);
			prepare.setInt(1, userId);
			prepare.setInt(2, addId);
			prepare.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method removes a user from the visibility list of another user.
	 * 
	 * @param userName       the username of the user who wants to remove another
	 *                       user.
	 * @param deleteUsername the username of the user to be removed from the
	 *                       visibility list.
	 * 
	 * @mmohammedanas2 11/26/2023
	 * @hzheng9 11/26/2023
	 */
	public void deleteUser(String userName, String deleteUsername) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String s = "SELECT userID FROM Users WHERE username = ?";
		PreparedStatement prep = null;
		ResultSet rs = null;
		int userId = 0;
		try {
			prep = conn.prepareStatement(s);
			prep.setString(1, userName);
			rs = prep.executeQuery();
			if (rs.next()) {
				userId = rs.getInt("userID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String string = "SELECT userID FROM Users WHERE username = ?";
		PreparedStatement prepString = null;
		ResultSet resultSet = null;
		int deleteId = 0;
		try {
			prepString = conn.prepareStatement(string);
			prepString.setString(1, deleteUsername);
			resultSet = prepString.executeQuery();
			if (resultSet.next()) {
				deleteId = resultSet.getInt("userID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// sql query to delete a user from the visibility list of the current user
		String deleting = " delete from Visibility where userId = ? && visibleID = ?;";
		PreparedStatement prepare = null;
		try {
			prepare = conn.prepareStatement(deleting);
			prepare.setInt(1, userId);
			prepare.setInt(2, deleteId);
			prepare.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method removes a user's complete existance from a database including posts, visible list and other things.
	 * 
	 * @param userName	// username of the user whose account has to be deleted
	 * @param passWord	// password of the user whose account has to be deleted 
	 * @return returns true/ false according to the user inputs.
	 * 
	 * @mohammedanas2 12/04/2023
	 * @hzheng9 12/04/2023
	 * 
	 */
	public boolean closeAccount(String userName, String passWord) {
		// Initialize connection, prepared statement, and result set objects
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;

		try {
			// Establish a connection to the database
			conn = DriverManager.getConnection(url, username, password);

			// Prepare a SQL query to retrieve the password of the user
			String query = "SELECT password FROM Users WHERE username = ?";
			prep = conn.prepareStatement(query);
			prep.setString(1, userName);
			rs = prep.executeQuery();

			// If the user exists and the password matches
			if (rs.next() && rs.getString("password").equals(passWord)) {

				// Close the previous statement and result set
				prep.close();
				rs.close();

				// Prepare a SQL query to delete the user's posts
				String deletePosts = "DELETE FROM Posts WHERE visibleID IN (SELECT userID FROM Users WHERE username = ?)";
				prep = conn.prepareStatement(deletePosts);
				prep.setString(1, userName);
				prep.executeUpdate();

				// Prepare a SQL query to delete the user's visibility entries
				String deleteVisibility = "DELETE FROM Visibility WHERE userID IN (SELECT userID FROM Users WHERE username = ?) OR visibleID IN (SELECT userID FROM Users WHERE username = ?)";
				prep = conn.prepareStatement(deleteVisibility);
				prep.setString(1, userName);
				prep.setString(2, userName);
				prep.executeUpdate();

				// Prepare a SQL query to delete the user
				String deleteUser = "DELETE FROM Users WHERE username = ?";
				prep = conn.prepareStatement(deleteUser);
				prep.setString(1, userName);
				prep.executeUpdate();
				// Return true indicating the account was successfully closed
				return true;
			} else {
				// Return false if the password does not match
				return false;
			}
		} catch (SQLException e) {
			// Print the stack trace for any SQL exception
			e.printStackTrace();
		} finally {
			// Close the result set, prepared statement, and connection objects
			try {
				if (rs != null)
					rs.close();
				if (prep != null)
					prep.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Return false if the account could not be closed
		return false;
	}
}