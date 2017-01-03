package application;

import java.net.URL;
import javax.swing.JOptionPane;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FrontEndController implements Initializable {

    @FXML private TableView<DataBaseObject> table;
    @FXML private TableColumn<DataBaseObject,Integer> IDColumn;
    @FXML private TableColumn<DataBaseObject,String> nameColumn;
    @FXML private TableColumn<DataBaseObject,String> emailColumn;
    @FXML private TableColumn<DataBaseObject,String> allegianceColumn;
    @FXML private TableColumn<DataBaseObject,String> positionColumn;
    @FXML private TableColumn<DataBaseObject,Double> salaryColumn;
    
    @FXML private TextField addIDField;
    @FXML private TextField addNameField;
    @FXML private TextField addEmailField;
    @FXML private ComboBox<String>  addAllegianceField;
    @FXML private TextField addPositionField;
    @FXML private TextField addSalaryField;
    @FXML private Button    addButton;
    
    @FXML private TextField deleteIDField;
    @FXML private TextField deleteNameField;
    @FXML private TextField deleteEmailField;
    @FXML private ComboBox<String>  deleteAllegianceField;
    @FXML private TextField deletePositionField;
    @FXML private TextField deleteSalaryField;
    @FXML private Button    deleteButton;
    
    
    
    ApplicationContext context = new ClassPathXmlApplicationContext("Spring.xml");
	DBManager database = (DBManager) context.getBean("GameOfThrones_Database");
    
    
    
                
    



    private ObservableList<DataBaseObject> getCharacters(){
        ObservableList<DataBaseObject> characters = FXCollections.observableArrayList();
        
        database.printResultSet(characters);
        return characters;
    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	//IDColumn
        IDColumn.setCellValueFactory(new PropertyValueFactory<DataBaseObject, Integer>("ID"));
        
        //nameColumn
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        //emailColumn
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        //allegianceColumn
        allegianceColumn.setCellValueFactory(new PropertyValueFactory<>("allegiance"));
        
        //positionColumn
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        
        //salaryColumn
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        
        addAllegianceField.setItems(DataBaseObject.getAllegiances());
        deleteAllegianceField.setItems(DataBaseObject.getAllegiances());
        addButton.setOnAction(e -> insertButtonEvent());
        deleteButton.setOnAction(e -> deleteButtonEvent());
        
        
        
        
        table.setItems(getCharacters());
        
    }
    public DataBaseObject createCharacter(){//as can see the ID column gets ignore
		DataBaseObject newCharacter = null;
		try{ newCharacter = 
		new DataBaseObject(
			//	addIDField.getText(),
				addNameField.getText(),
				addEmailField.getText(),
				addAllegianceField.getSelectionModel().getSelectedItem().toString(),
				addPositionField.getText(),
				addSalaryField.getText());
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
	//	newCharacter.setID(database.pullID(newCharacter.getName()));
		return newCharacter;
	}
    public void insertButtonEvent(){
		database.insertNewEntry(createCharacter());
		table.setItems(getCharacters());
		JOptionPane.showMessageDialog(null, "Add successful!");
		
	}
    
    public void deleteButtonEvent(){
    	if(validateDeletionFields()){
    		database.deleteEntry(determineDeletionField());
    		
    		table.setItems(getCharacters());
		JOptionPane.showMessageDialog(null, "Delete successful!");
		
    	}
   // 	database.resetEntries();
	}
    
    static public String determineProperty(TextField deletionField){
    	String property = "";
    	switch(determinePromptText(deletionField.getPromptText())){
    	case "ID..."       	 : property = "id"; break;
    	case "Name..."       : property = "name"; break;
    	case "Email..."      : property = "email"; break;
    	case "Department..." : property = "department"; break;
    	case "Position..."   : property = "position"; break;
    	case "Salary..."     : property = "salary"; break;
    	}
    	return property;
    	
    }
    
    static public String determinePromptText(String promptText){
    	String result = "";
    	switch(promptText){
    	case "ID..."       	 : result = "ID..."; break;
    	case "Name..."       : result = "Name..."; break;
    	case "Email..."      : result = "Email..."; break;
    	case "Department..." : result = "Department..."; break;
    	case "Position..."   : result = "Position..."; break;
    	case "Salary..."     : result = "Salary..."; break;
    	}
    	return result;
    }
    
    public boolean validateDeletionFields(){
    	int count =0;
    	if(!(deleteIDField.getText().trim().isEmpty()))
    		count++;
    	
    	if(!(deleteNameField.getText().trim().isEmpty()))
    		count++;
    	
    	if(!(deleteEmailField.getText().trim().isEmpty()))
    		count++;
    	
    	if(deleteAllegianceField.getSelectionModel().isEmpty() == false)
    		count++;
    	
    	if(!(deletePositionField.getText().trim().isEmpty()))
    		count++;
    	
    	if(!(deleteSalaryField.getText().trim().isEmpty()))
    		count++;
    	
    	if(count > 1){
    		System.out.print(count);
    		JOptionPane.showMessageDialog(null, "Error: Only one field can be used for deletion at a time.\nPlease empty all but one field to continue with deletion");
    		return false;
    	}
    	return true;
    }
    
    public TextField determineDeletionField(){
    	TextField deletionField = new TextField();
    	
    	if(!(deleteIDField.getText().trim().isEmpty()))
    		deletionField = deleteIDField;
    	
    	if(!(deleteNameField.getText().trim().isEmpty()))
    		deletionField = deleteNameField;
    	
    	if(!(deleteEmailField.getText().trim().isEmpty()))
    		deletionField = deleteEmailField;
    	
    	if(deleteAllegianceField.getSelectionModel().isEmpty() == false){
    		deletionField.setText(deleteAllegianceField.getSelectionModel().getSelectedItem().toString());
    		deletionField.setPromptText("Department...");
    	}
    		
    	if(!(deletePositionField.getText().trim().isEmpty()))
    		deletionField = deletePositionField;
    	
    	if(!(deleteSalaryField.getText().trim().isEmpty()))
    		deletionField = deleteSalaryField;
    	
    	return deletionField;
    	
    }
}