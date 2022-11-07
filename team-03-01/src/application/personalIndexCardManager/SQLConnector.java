package application.personalIndexCardManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * Connects to the database and extracts data
 * 
 */
public class SQLConnector {
	
	/**
     * Connect to the desired database
     * in resources/database
     *
     * @return the Connection object
     */
    private Connection connect(String database) {
    	
        // SQLite connection string for this project
        String url = "jdbc:sqlite:resources/database/" + database;
        Connection conn = null;
        
        try {
        	// Establishes a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    /*
     * Get stored email input
     * 
     */
    private String getStoredEmail(String database) {
    	// Statement string to get email
    	String sql = "SELECT EMAIL FROM GETEMAIL";
    	try {
    		// Connects to database and extracts stored email input
    		Connection con = this.connect(database);
    		Statement stmt  = con.createStatement();
    		
    		// Get the email input data from table
    		ResultSet rs = stmt.executeQuery(sql);
    		
    		// Checks to see if there is a stored email
    		if (rs.next()) {
    			String email = rs.getString("EMAIL");
    			con.close();
            	rs.close();
            	stmt.close();
    			return email;
    		}
    		// Closes connection and statement
    		con.close();
    		rs.close();
    		stmt.close();
    		
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /*
     * Get current course name
     * 
     */
    private String getCurrentCourse(String database) {
    	// Statement string to get email
    	String sql = "SELECT CourseName FROM GetCourseName";
    	try {
    		// Connects to database and extracts stored email input
    		Connection con = this.connect(database);
    		Statement stmt  = con.createStatement();
    		
    		// Get the email input data from table
    		ResultSet rs = stmt.executeQuery(sql);
    		
    		// Checks to see if there is a stored email
    		if (rs.next()) {
    			String name = rs.getString("CourseName");
    			con.close();
            	rs.close();
            	stmt.close();
    			return name;
    		}
    		// Closes connection and statement
    		con.close();
    		rs.close();
    		stmt.close();
    		
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /*
     * Removes the stored email in the table
     * GETEMAIL from the database
     * 
     */
    private void removeStoredEmail(String database) {
    	// PreparedStatement string to remove a piece of data from database table
    	String sql = "DELETE FROM GETEMAIL";
    	try {
    		// Connects to database and prepares a statement to remove data
    		Connection con = this.connect(database);
    		PreparedStatement statement = con.prepareStatement(sql);
    		
    		// updates table GETEMAIL
    		statement.executeUpdate();
    		
    		// Closes connection and statement
    		con.close();
    		statement.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /*
     * Removes the stored current course in the table
     * GetCourseName from the database
     * 
     */
    private void removeCurrentCourse(String database) {
    	// PreparedStatement string to remove a piece of data from database table
    	String sql = "DELETE FROM GetCourseName";
    	try {
    		// Connects to database and prepares a statement to remove data
    		Connection con = this.connect(database);
    		PreparedStatement statement = con.prepareStatement(sql);
    		
    		// updates table GetCourseName
    		statement.executeUpdate();
    		
    		// Closes connection and statement
    		con.close();
    		statement.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    }

    /*
     * Inserts a new user into the database
     * 
     * Available tables: Users
     * 
     */
    public void addUser(String database, String table, User user) {
    	String sql = "INSERT INTO " + table + "(email, password, securityQuest, securityAns, userID) VALUES(?,?,?,?,?)";

    	// Establishes a connection to the database
    	// Prepares a statement
    	try (Connection conn = this.connect(database);
    			PreparedStatement statement = conn.prepareStatement(sql)) {
    		
    		// Extracts data from user object and put into database
    		statement.setString(1, user.getEmail());
    		statement.setString(2, user.getPass());
    		statement.setString(3, user.getSecurityQuest());
    		statement.setString(4, user.getSecurityAns());
    		
    		String userID = user.getEmail().replace('@', '_');
    		userID.replace('.', '_');
    		System.out.println(userID);
    		statement.setString(5, userID);
           
    		// Updates database
    		statement.executeUpdate();
    		
    		// Closes connection and statement
    		conn.close();
    		statement.close();
    		System.out.println("Added new user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Check database for the specified user
     * 
     * Available Tables: Users
     * 
     */
    public void getAllUsers(String database, String table) {
    	
    	// PreparedStatement where it pulls data from the specified table in the specified database
    	String sql = "SELECT email, password, securityQuest, securityAns FROM " + table;
    	try (Connection conn = this.connect(database);
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){
                
               // loop through the result set
               while (rs.next()) {
                   System.out.println(rs.getString("email") +  "\t" + 
                                       rs.getString("password") + "\t" +
                                       rs.getString("securityQuest") + "\t" +
                                       rs.getString("securityAns"));
               }
            // Closes connection, result set, and statement
       		conn.close();
       		rs.close();
       		stmt.close();
               
           } catch (SQLException e) {
               e.getStackTrace();
           }
    }
    
    /*
     * Find the specified user in the database by email
     * 
     * Available Tables: Users
     * 
     */
    public boolean findEmail(String database, String table, String email) {
    	
    	// PreparedStatement where it pulls a piece of data that matches
    	// with the desired username from the database
    	String sql = "SELECT email FROM " + table + " WHERE email = ?";
    	
    	// Establishes a connection to the database and creates a statement
    	//
    	try (Connection con = this.connect(database);
                PreparedStatement statement  = con.prepareStatement(sql)) {
    		
    		// Gets the user with the desired email
    		statement.setString(1, email);
    		
    		
    		// Check if user is found in the database
    		ResultSet rs  = statement.executeQuery();
    		if (rs.next()) {
                System.out.println(rs.getString("email"));
                return true;
            }
    	} catch (SQLException e) {
    		e.printStackTrace();
    		
    	}
    	return false;
    }
    
    /*
     * Find the specified user in the database by email
     * 
     * Available Tables: Users
     * 
     */
    public boolean findPass(String database, String table, String email, String password) {
    	
    	// PreparedStatement where it pulls a piece of data that matches
    	// with the desired username from the specified table in the specified database
    	String sql = "SELECT email, password FROM " + table + " WHERE email = ? and password = ?";
    	
    	// Establishes a connection to the database and creates a statement
    	//
    	try (Connection con = this.connect(database);
                PreparedStatement statement  = con.prepareStatement(sql)) {
    		
    		// Gets the user with the desired password
    		statement.setString(1, email);
    		statement.setString(2, password);
    		
    		
    		// Check if user is found in the database
    		ResultSet rs  = statement.executeQuery();
    		if (rs.next()) {
                System.out.println(rs.getString("email") + "\t" +
                		rs.getString("password"));
                // Closes connection, result set, and statement
        		con.close();
        		rs.close();
        		statement.close();
                return true;
            }
    		// Closes connection, result set, and statement
    		con.close();
    		rs.close();
    		statement.close();
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    		
    	}
    	return false;
    }
    
    /*
     * Find the specified user in the database by email
     * 
     */
    public String getSecQuest(String database, String table) {
    	
    	// PreparedStatement where it pulls a piece of data that matches
    	String sql = "SELECT email, securityQuest FROM " + table + " WHERE email = ?";
    	
    	// Establishes a connection to the database and creates a statement
    	try (Connection con = this.connect(database);
                PreparedStatement statement  = con.prepareStatement(sql)) {
    		
    		// Gets the user's security question
    		String email = this.getStoredEmail(database);
    		System.out.println("Got " + email);
    		statement.setString(1, email);
    		
    		// Pulls data from the database
    		ResultSet rs  = statement.executeQuery();
    		
    		// Checks to see if it can get the specified data
    		if (rs.next()) {
                System.out.println(rs.getString("email") + "\t" +
                		rs.getString("securityQuest"));
                
                // Gets the security question associated with the email
                String secQuest = rs.getString("SecurityQuest");
                
                // Closes connection, result set, and statement
        		con.close();
        		rs.close();
        		statement.close();
                return secQuest;
            }
    		// Closes connection, result set, and statement
    		con.close();
    		rs.close();
    		statement.close();
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    		
    	}
    	return null;
    }
    
    /*
     * Stores the email input 
     * 
     */
    public void storeEmailInput(String database, String emailInput) {
    	
    	// Removes any stored email
    	this.removeStoredEmail(database);
    	
    	// PreparedStatement String to insert email input into database
    	String sql = "INSERT INTO GETEMAIL (EMAIL) VALUES(?)";

    	// Establishes a connection to the database
    	// Prepares a statement
    	try (Connection conn = this.connect(database);
    			PreparedStatement statement = conn.prepareStatement(sql)) {
    		
    		// Extracts data from user object and put into database
    		statement.setString(1, emailInput);
    		System.out.println("Stored " + emailInput);
           
    		// Updates database
    		statement.executeUpdate();
    		conn.close();
        	statement.close();
    		System.out.println("Updated stored email in the database table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Stores the current course
     * 
     */
    public void storeCurrentCourse(String database, Course currentCourse) {
    	
    	// Removes any stored email
    	this.removeCurrentCourse(database);
    	
    	// PreparedStatement String to insert email input into database
    	String sql = "INSERT INTO GetCourseName (CourseName) VALUES(?)";

    	// Establishes a connection to the database
    	// Prepares a statement
    	try (Connection conn = this.connect(database);
    			PreparedStatement statement = conn.prepareStatement(sql)) {
    		
    		// Extracts data from user object and put into database
    		statement.setString(1, currentCourse.getName());
    		System.out.println("Stored " + currentCourse.getName());
           
    		// Updates database
    		statement.executeUpdate();
    		conn.close();
        	statement.close();
    		System.out.println("Updated stored course in the database table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Find the specified user in the database by email
     * 
     * Available tables: Users
     * 
     */
    public boolean findAnswer(String database, String table, String securityAns) {
    	
    	// PreparedStatement where it pulls a piece of data that matches
    	String sql = "SELECT email, securityAns FROM " + table + " WHERE email = ? and securityAns = ?";
    	
    	// Establishes a connection to the database and creates a statement
    	try (Connection con = this.connect(database);
                PreparedStatement statement  = con.prepareStatement(sql)) {
    		
    		// Gets the user
    		String email = this.getStoredEmail(database);
    		System.out.println(email);
    		statement.setString(1, email);
    		statement.setString(2, securityAns);
    		
    		// Check if user is found in the database
    		ResultSet rs  = statement.executeQuery();
    		if (rs.next()) {
                System.out.println(rs.getString("email") + "\t" +
                		rs.getString("securityAns"));
                
                // Checks to see if there is another user with the same data
                if (rs.next() == false) {
                	con.close();
                	rs.close();
                	statement.close();
                	return true;
                }
            }
    		return false;
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    		
    	} 
    	return false;
    }
    
    /*
     * Resets the password of the account in the specified table
     * in the specified database
     * 
     * Available Tables: Users
     * 
     */
	public void resetPassword(String database, String table, String password) {
		
		// PreparedStatement String to update the password to its new password
		// corresponding to the stored email
		String sql = "UPDATE " + table + " SET password = ? "
                + "WHERE email = ?";

        try (Connection conn = this.connect(database);
                PreparedStatement statement = conn.prepareStatement(sql)) {

            // set the password to the new password
        	String email = this.getStoredEmail(database);
            statement.setString(1, password);
            statement.setString(2, email);
           
            // Updates the password & removes stored email
            statement.executeUpdate();
            this.removeStoredEmail(database);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
    
    /*
     * Add a course to the user's courses
     * 
     */
    public void addCourse(String database, Course course) {
    	// PreparedStatement String to add a new course to the Courses page
    	String sql = "INSERT INTO Courses (email, courseName, courseID) VALUES(?,?,?)";
    	try {
    		// Connects to database
    		Connection con = this.connect(database);
    		PreparedStatement statement = con.prepareStatement(sql);
    		
    		// Stores the relevant data into the Courses table of database
    		statement.setString(1, this.getStoredEmail(database));
    		statement.setString(2, course.getName());
    		statement.setString(3, this.getStoredEmail(database) + "#" + course.getID());
    		
    		// Update Courses table in database
    		statement.executeUpdate();
    		
    		// Closes connection and prepared statement 
    		// from running in the background
    		con.close();
    		statement.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /*
     * Gets the courses of the specified email
     * 
     */
    public List<Course> getCourses(String database) {
    	// Gets list of courses
    	List<Course> courses = new ArrayList<Course>();
    	
    	// PreparedStatement where it pulls a piece of data that matches
    	String sql = "SELECT email, courseName FROM Courses WHERE email = ?";
    	
    	// Establishes a connection to the database and creates a statement
    	try (Connection con = this.connect(database);
                PreparedStatement statement  = con.prepareStatement(sql)) {
    		
    		// Gets the user's security question
    		String email = this.getStoredEmail(database);
    		System.out.println("Got " + email);
    		statement.setString(1, email);
    		
    		// Pulls data from the database
    		ResultSet rs  = statement.executeQuery();
    		while(rs.next()) {
    			courses.add(new Course(rs.getString("courseName")));
    		}
    		
    		// Closes connection, result set, and statement
    		con.close();
    		rs.close();
    		statement.close();
    		return courses;
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    		
    	}
    	return null;
    	
    }
    
    /*
     * Updates the course name when being renamed
     * 
     */
    public void updateCourseName(String database, Course newCourse) {
    	
    	// PreparedStatement String to update the name of the course
    	String sql = "UPDATE Courses SET courseName = ?, courseID = ? WHERE courseID = ?";
    	
    	try {
    		// Establishes a connection to the database
    		// Prepares to execute action
    		Connection con = this.connect(database);
    		PreparedStatement pstmt = con.prepareStatement(sql);
    		System.out.println(newCourse.getName());
    		
    		// Located course in database with the desired parameters
    		pstmt.setString(1, newCourse.getName());
    		pstmt.setString(2, this.getStoredEmail(database) + "#" + newCourse.getID());
    		Course oldCourse = new Course(this.getCurrentCourse(database));
    		pstmt.setString(3, this.getStoredEmail(database) + "#" + oldCourse.getID());
    		
    		// Updates the database
    		pstmt.executeUpdate();
    		
    		// Closes the connection and prepared statement
    		con.close();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /*
     * Deletes specified course
     * 
     */
    public void deleteCourse(String database) {
    	// PreparedStatement string to remove specified course from database table
    	String sql = "DELETE FROM Courses where courseName = ?";
    	try {
    		// Connects to database and prepares a statement to remove data
    		Connection con = this.connect(database);
    		PreparedStatement statement = con.prepareStatement(sql);
    		
    		Course course = new Course(this.getCurrentCourse(database));
    		statement.setString(1, course.getName());
    		
    		// updates table to remove course
    		statement.executeUpdate();
    		
    		// Closes connection and statement
    		con.close();
    		statement.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /*
     * Checks if name is already taken
     * 
     */
    public boolean checkName(String database, String name) {
    	// PreparedStatement where it pulls a piece of data that matches
    	// with the desired course name from the database
    	String sql = "SELECT courseName,courseID FROM Courses WHERE courseName = ? and courseID = ?";
    	
    	// Establishes a connection to the database and creates a statement
    	//
    	try (Connection con = this.connect(database);
                PreparedStatement statement  = con.prepareStatement(sql)) {
    		
    		// Gets the course name
    		statement.setString(1, name);
    		Course temp = new Course(name);
    		System.out.println(this.getStoredEmail(database) + "#" + temp.getID());
    		statement.setString(2, this.getStoredEmail(database) + "#" + temp.getID());
    		
    		
    		// Check if course is found in the database
    		ResultSet rs  = statement.executeQuery();
    		if (rs.next()) {
                System.out.println(rs.getString("courseName") + "\t"
                		+ rs.getString("courseID"));
                con.close();
        		statement.close();
        		rs.close();
                return true;
            }
    		con.close();
    		statement.close();
    		rs.close();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		
    	}
    	System.out.println("hi");
    	return false;
    }
   


}
