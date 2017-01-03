package application;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;



public class DataBaseObject implements Initializable {
	
	
	
	private int id;
	private String name;
	private String email;
	private String allegiance;
	private String position;
	private double salary;
	
	private static ObservableList<String> GameOfThrones_Allegiances = 
		    FXCollections.observableArrayList(
		        "IT",
		        "HR",
		        "Accounting",
		        "R&D",
		        "Marketing",
		        "Production",
		        null
		        
		        
		    );
	
	public DataBaseObject(){
	}
	
	public DataBaseObject(String name, String email, String allegiance, String position, double salary){
		
		setName(name);
		setEmail(email);
		setAllegiance(allegiance);
		setPosition(position);
		setSalary(salary);
	}
	
	public DataBaseObject(String name, String email, String allegiance, String position, String salary){
		
		setName(name);
		setEmail(email);
		setAllegiance(allegiance);
		setPosition(position);
		setSalary(salary);
	//	setID(database.pullID(name));
	}
	
	public DataBaseObject(String id, String name, String email, String allegiance, String position, double salary){
		setID(id);
		setEmail(email);
		setName(name);
		setAllegiance(allegiance);
		setPosition(position);
		setSalary(salary);
	}
	
	
	public DataBaseObject(String id, String name, String email, String allegiance, String position, String salary){
		setID(id);
		setName(name);
		setEmail(email);
		setAllegiance(allegiance);
		setPosition(position);
		setSalary(salary);
	}
	
	public int getID(){
		return id;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public void setID(String id){
		this.id = Integer.parseInt(id);
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
			this.name = name;
	}

	public String getAllegiance() {
		return allegiance;
	}

	public void setAllegiance (String allegiance){
			this.allegiance = allegiance;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
			this.position = position;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public void setSalary(String salary) {
		this.salary = Double.parseDouble(salary);
	}
	
	
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return email;
	}
	
	public static ObservableList<String> getAllegiances(){
		return GameOfThrones_Allegiances;
	}
	
	//public int getID(String name){
		
	//	return 1;
	//}
	
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring.xml");
	DBManager database = (DBManager) context.getBean("GameOfThrones_Database");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setID(database.pullID(name));
		
	}
	
}
/*For resetting, BEWARE of foreign table relationships
ALTER TABLE `users` DROP `id`;
ALTER TABLE `users` AUTO_INCREMENT = 1;
ALTER TABLE `users` ADD `id` int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;
 */



