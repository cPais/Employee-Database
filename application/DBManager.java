package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DBManager {
	
	private Connection myConn;
	
	private PreparedStatement insertStatement;
	private String insertionSQL;
	
	private PreparedStatement printAllStatement;
	private String printSQL;
	
	private PreparedStatement deleteStatement;
	private String deleteSQL;
	
	private PreparedStatement resetStatement;
	private String resetSQL;
	
	private PreparedStatement pullStatement;
	
	private ResultSet myResultSet;
	
	
	
	
	
	
	
	public DBManager(String url,String user, String password){
		try {
			myConn = DriverManager.getConnection(url,user,password);
			
			insertionSQL="INSERT INTO characters "+
					"(name,email,allegiance,position,salary) "+
					"VALUES(?,?,?,?,?)";
			
			printSQL="SELECT * FROM characters";
			
			resetSQL = "ALTER TABLE characters DROP id; "+
					 "ALTER TABLE characters AUTO_INCREMENT = 1; "+
					 "ALTER TABLE characters ADD id int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;";
			
		//	deleteSQL="DELETE FROM characters"+ 
		//	"WHERE " + "name = ?";
			
			insertStatement = myConn.prepareStatement(insertionSQL);
			printAllStatement= myConn.prepareStatement(printSQL);
			resetStatement = myConn.prepareStatement(resetSQL);
		//	deleteStatement = myConn.prepareStatement(deleteSQL);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void insertNewEntry(DataBaseObject newCharacter){
		try {
			insertStatement.setString(1,newCharacter.getName());
			insertStatement.setString(2,newCharacter.getEmail());
			insertStatement.setString(3,newCharacter.getAllegiance());
			insertStatement.setString(4,newCharacter.getPosition());
			insertStatement.setDouble(5,newCharacter.getSalary());
			insertStatement.executeUpdate();
			
		}catch (SQLException e) {	
			e.printStackTrace();
		}
		
	}
	
	public void resetEntries(){
		try{
		resetStatement.executeUpdate();	
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void deleteEntry(TextField deletionField){
		String property = FrontEndController.determineProperty(deletionField);
		String value    = deletionField.getText();
		if(property == "department"){
			property = "allegiance";
		}
		deleteSQL="DELETE FROM characters WHERE " + property + "  = ?";
		
		try {
			deleteStatement = myConn.prepareStatement(deleteSQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try{
			deleteStatement.setString(1,value);
			//deleteStatement.setString(2,value);
			deleteStatement.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public String pullID(String name){
		
		String pullIDSQL="SELECT 'id' FROM characters WHERE name = ?";
		String result="";//SELECT `id` FROM `characters` WHERE name IS x
		try {
			pullStatement = myConn.prepareStatement(pullIDSQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try{
			pullStatement.setString(1,name);
			myResultSet = pullStatement.executeQuery();
			if(myResultSet.getString("id") != null){
				result= myResultSet.getString("id");
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public ObservableList<DataBaseObject> printResultSet(ObservableList<DataBaseObject> data){
		try{
			myResultSet = printAllStatement.executeQuery(printSQL);
		while(myResultSet.next()){
			data.add(new DataBaseObject(
					myResultSet.getString("id"),
					myResultSet.getString("name"),
					myResultSet.getString("email"),
					myResultSet.getString("allegiance"),
					myResultSet.getString("position"),
					myResultSet.getString("salary")));
			
		}
		
		}catch(SQLException e){
			e.printStackTrace();
		}
		return data;
	}

	public String getInsertionSQL() {
		return insertionSQL;
	}

	public void setInsertionSQL(String insertionSQL) {
		this.insertionSQL = insertionSQL;
	}
	
	public ResultSet getResultSet(){
		try{
			myResultSet = printAllStatement.executeQuery(printSQL);
		}catch(SQLException e){	
			e.printStackTrace();
		}
		return myResultSet;
	}

	public PreparedStatement getInsertStatement() {
		return insertStatement;
	}

	public void setInsertStatement(PreparedStatement insertStatement) {
		this.insertStatement = insertStatement;
	}
	
	
	//public void test(){
	//	try {
			
		//	SQL="SELECT * FROM customers LIMIT 5";
		//	myResultSet = myStatement.executeQuery(SQL);
		//} catch (SQLException e) {
		//	e.printStackTrace();
	//	}
	}
/*
	public Connection getMyConn() {
		return myConn;
	}

	public void setMyConn(Connection myConn) {
		this.myConn = myConn;
	}
	
	//extra
	 * myStatement = myConn.createStatement();
		SQL="SELECT * FROM customers LIMIT 5";
		myResultSet = myStatement.executeQuery(SQL);
*/
	
	
		
		
//}
	

/*
try{
		//1.  Get a connection to database
		Connection myConn = DriverManager.getConnection(url,user,password);
		
		//2.  Create a statement
		Statement myStatement = myConn.createStatement();
		//String SQL="INSERT INTO customers"
		//		  +"(name,address,city,state,zip)"
		//		  +"VALUES ('Yorc YelPais', '123 Sesame', 'New York', 'NY', '88415')";
		String SQL="SELECT * FROM customers LIMIT 5";
		
		//3.  Execute SQL query
		ResultSet myResultSet = myStatement.executeQuery(SQL);
	//	myStatement.executeUpdate(SQL);
		
		//4.  Process the result set
		 * 
		 * catch(Exception e){
		e.printStackTrace();
	}
		 */

