/**
 * Payroll.java
 * Program 6: A User Database
 * Phase 3 of the Payroll project.
 * 
 * @author Yong Deng
 * @since  3/30/2015
 */

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/**---------------------------------------------------------------------------------------------------------
* Class Payroll. Controller. Creates a payroll system.
* */
public class Payroll extends Application{
			
	private String     login, name;			    //user name for login and the real name of employee
	private double     salaryOrHourRate;	    //employee's salary (Salaried) or hourly rate (Hourly)
	private int        currentUserID=-1;		//current user's ID, distinguish boss from regular employees
	private String     currentUserLogin;	    //current user's login, unique
	private long       date;					//date when employee was created
	private int        employeeID;				//final and unique
	private char       employeeCategory;		//salaried (s) or hourly (h)
	private static int empCount = 0;			//number of employee get paid
	private byte[]     password;				//password, encrypted
	
	private ArrayList<Employee> list = new ArrayList<Employee>();	//An ArrayList holds current Employee objects
	private ArrayList<Employee> removed = new ArrayList<Employee>();//An ArrayList holds removed Employee objects
	//StringBuilder holds information of removed employeess
	StringBuilder sbRemove = new StringBuilder("The following employees have been terminated: \n");

	Stage st;
	Scene sn;
		
	ImageView iv = new ImageView(new Image("2.jpg"));
	ImageView iv22 = new ImageView(new Image("22.jpg"));
	ImageView iv33 = new ImageView(new Image("33.jpg"));
	ImageView iv44 = new ImageView(new Image("44.jpg"));
	
	Font boldArial = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20);
	Font regSans  = Font.font("Monospace", FontWeight.NORMAL, FontPosture.REGULAR, 20);
	
	TextField     usernameField      = new TextField();
	TextField     salaryField        = new TextField();
	TextField     nameField          = new TextField();
	TextField     empCategoryField   = new TextField();
	TextField     IDField            = new TextField();
	TextArea      ta                 = new TextArea();
		
	PasswordField passwordField1 	 = new PasswordField();
	PasswordField passwordField2 	 = new PasswordField();

	Label 		  message        	 = new Label("\nNo employee in the database yet. Enter the boss's infomration: ");
	Label         userLabel          = new Label("Username");
	Label         passwordLabel1     = new Label("Password");
	Label         passwordLabel2     = new Label("Re-enter Password");
	Label         salaryLabel        = new Label("Salary/Hour Rate");
	Label         nameLabel          = new Label("Name");	
	Label         empCatLabel        = new Label("Category");
	Label         IDLabel        	 = new Label("Employee ID");
	Label         empToPay       	 = new Label();
		
	Button        btLogin       	 = new Button("Login");
	Button        btNewEmp    		 = new Button("Enter new employee");
	Button        btListEmp    		 = new Button("List employee");
	Button        btChangeData 		 = new Button("Change employee data");
	Button        btTerminate   	 = new Button("Terminate an employee");
	Button        btPay         	 = new Button("Pay employees");
	Button        btLogout      	 = new Button("Logout");
	Button        btMainmenu    	 = new Button("Main menu");
	Button        btSubmit      	 = new Button("Submit");
	
	RadioButton   rbSalaried         = new RadioButton("Salaried");
	RadioButton   rbHourly           = new RadioButton("Hourly");
	ToggleGroup   groupCategory      = new ToggleGroup();

	/**-----------------------------------------------------------------------------------------------------
	 * start function
	 * */	
	public void start(Stage st) throws FileNotFoundException {
		this.st = st;
		buildGui();
		initLoad();
		st.setScene(sn);
		st.setTitle("Payroll");
		st.show();
	}
	
	/**-----------------------------------------------------------------------------------------------------
	 * Initial function to read data from file or create boss
	 * */	
	@SuppressWarnings("unchecked")
	public void initLoad () {
		try{
			//recover employee data from file
			FileInputStream fis = new FileInputStream("employee.txt");
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        ArrayList<Employee> temp = (ArrayList<Employee>) ois.readObject();
	        for (Employee e: temp) {
	        	//read employee information back from the file
	        	employeeID = e.getEmployeeID();
	        	login = e.getLogin().trim();
				password = e.getPassword();
		
	        	salaryOrHourRate = e.getSalary();
	        	date = e.getDate();
	        	name = e.getName().trim();
	        	if (e instanceof Salaried) {
	        		list.add(new Salaried(employeeID, login, password, salaryOrHourRate, date, name));
	        	}
	        	else {
	        		//if employee is hourly paid, get hourly rate and create employee
	        		list.add(new Hourly(employeeID, login, password, salaryOrHourRate, date, name));
	        	}
	        }
	        ois.close();
	        buildLoginGui();
		}
		catch (FileNotFoundException fe){
			//when no employee exists, create a new file employee.txt, create a boss account who can 
			//create new employee account and have full access to the database
			System.out.println("\nError! File employee.txt not found.");
			
			btSubmit.setOnAction(e->{
				PrintWriter pw = null;
				byte[] tempPassword = null;
				try {
					pw = new PrintWriter (new File("employee.txt"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				pw.close();
				login = usernameField.getText();
				try {
					password = computeHash(passwordField1.getText());
					tempPassword = computeHash(passwordField2.getText());
				}
				catch (NoSuchAlgorithmException ne) {
					ne.printStackTrace();
				}
				if (password != null && !Arrays.equals(password, tempPassword)) 
					System.out.print("\nPasswords do not match. ");
				salaryOrHourRate = Double.parseDouble(salaryField.getText());
				name = nameField.getText();
				list.add(new Salaried(login, password, salaryOrHourRate, name));
				buildLoginGui();
			});
		}
		catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
		catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
	/**-----------------------------------------------------------------------------------------------------
	 * Builds the initial run GUI when no employee is in the system yet
	 * */	
	public void buildGui() {
		GridPane gp = new GridPane();
		VBox vb = new VBox(10);
		StackPane sp = new StackPane();
		
		message       .setTextFill(Color.TOMATO);
		
		ta      	  .setFont(regSans);
		btSubmit	  .setFont(boldArial);
		message 	  .setFont(boldArial);
		usernameField .setFont(regSans);
		salaryField   .setFont(regSans);
		passwordField1.setFont(regSans);
		passwordField2.setFont(regSans);
		nameField     .setFont(regSans);
		userLabel     .setFont(boldArial);
		passwordLabel1.setFont(boldArial);
		passwordLabel2.setFont(boldArial);
		salaryLabel   .setFont(boldArial);
		nameLabel     .setFont(boldArial);

		btSubmit      .setPrefWidth(300);
		btMainmenu    .setPrefWidth(300);
		usernameField .setPrefWidth(300);
		passwordField1.setPrefWidth(300);
		passwordField2.setPrefWidth(300);
		salaryField   .setPrefWidth(300);
		nameField     .setPrefWidth(300);
		
		gp.add(userLabel, 0, 0);
		gp.add(usernameField, 1, 0);
		gp.add(passwordLabel1, 0, 1);
		gp.add(passwordField1, 1, 1);
		gp.add(passwordLabel2, 0, 2);
		gp.add(passwordField2, 1, 2);
		gp.add(salaryLabel, 0, 3);
		gp.add(salaryField, 1, 3);
		gp.add(nameLabel, 0, 4);
		gp.add(nameField, 1, 4);
		gp.add(btSubmit, 1, 5);
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setPadding(new Insets(20,20,20,20));
		gp.setAlignment(Pos.CENTER);
		
		vb.getChildren().addAll(message, gp, ta);
		vb.setAlignment(Pos.CENTER);
		sp.getChildren().addAll(iv, vb);
		sn = new Scene(sp, 800, 600);
	}
	
	/**-----------------------------------------------------------------------------------------------------
	 * Builds the login menu GUI
	 * */	
	public void buildLoginGui() {
		GridPane gp = new GridPane();
		VBox vb = new VBox(10);
		StackPane sp = new StackPane();
		message.setText("Welcome to Payroll!");

		usernameField .setPrefWidth(300);
		passwordField1.setPrefWidth(300);
		
		btSubmit      .setFont(boldArial);
		btLogin       .setFont(boldArial);
		message       .setFont(boldArial);
		usernameField .setFont(regSans);
		passwordField1.setFont(regSans);
		userLabel     .setFont(boldArial);
		passwordLabel1.setFont(boldArial);
		
		usernameField .setText("");
		passwordField1.setText("");
		passwordField2.setText("");
		
		gp.add(userLabel, 0, 0);
		gp.add(usernameField, 1, 0);
		gp.add(passwordLabel1, 0, 1);
		gp.add(passwordField1, 1, 1);
		gp.add(btLogin, 1, 2);
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setPadding(new Insets(20,20,20,20));
		gp.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(message, gp);
		vb.setAlignment(Pos.CENTER);
		
		btLogin.setOnAction(e -> {doLogin(); usernameField.setText(""); passwordField1.setText("");});
		
		sp.getChildren().addAll(iv, vb);
		sn = new Scene(sp, 800, 600);
		st.setScene(sn);
	}
	
	/**-----------------------------------------------------------------------------------------------------
	 * Builds the boss employee main menu GUI
	 * */	
	public void buildBossGui() {
		VBox vb = new VBox(10);
		vb.setPadding(new Insets(20,20,20,20));
		StackPane sp = new StackPane();
		message.setText("Welcome Boss. Choose an option.");
		ta.setText("");
		
		btNewEmp    .setPrefWidth(300);
		btListEmp   .setPrefWidth(300);
		btChangeData.setPrefWidth(300);
		btTerminate .setPrefWidth(300);
		btPay       .setPrefWidth(300);
		btLogout    .setPrefWidth(300);
		
		btNewEmp    .setFont(boldArial);
		btListEmp   .setFont(boldArial);
		btChangeData.setFont(boldArial);
		btTerminate .setFont(boldArial);
		btPay       .setFont(boldArial);
		btLogout    .setFont(boldArial);
	
		vb.getChildren().addAll(message, btNewEmp, btListEmp, btChangeData, btTerminate, btPay, btLogout, ta);
		vb.setAlignment(Pos.CENTER);
		btNewEmp    .setOnAction( e -> { 
								usernameField.setText(""); 
								passwordField1.setText("");
								salaryField.setText("");
								nameField.setText("");
								ta.setText(""); 
								buildAddNewEmployeeGui(); } );
		btListEmp   .setOnAction( e -> listEmployee() );
		btChangeData.setOnAction( e -> { 
								ta.setText(""); 
								salaryField.setText(""); 
								nameField.setText("");
								IDField.setText("");
								buildChangeDataGui();} );
		btTerminate .setOnAction( e -> { 
			ta.setText(""); 
			salaryField.setText(""); 
			buildTerminateEmployeeGui();
			ta.setText("");
			} );
		btPay       .setOnAction( e -> { ta.setText(""); salaryField.setText(""); buildPayEmployeeGui(); } );
		btLogout    .setOnAction( e -> { ta.setText("");  logout(); empCount = 0; buildLoginGui(); } );
		
		sp.getChildren().addAll(iv33, vb);
		sn = new Scene(sp, 800, 600);
		st.setScene(sn);
	}
	
	/**-----------------------------------------------------------------------------------------------------
	 * Builds the non-boss employee main menu GUI
	 * */	
	public void buildEmployeeGui(){
		StackPane sp = new StackPane();
		VBox vb = new VBox(10);
		message.setText("Choose an option by clicking the buttons.");

		btListEmp  .setFont(boldArial);
		btTerminate.setFont(boldArial);
		btLogout   .setFont(boldArial);
		btListEmp  .setPrefWidth(300);
		btTerminate.setPrefWidth(300);
		btLogout   .setPrefWidth(300);

		btListEmp  .setOnAction(e -> listEmployee());
		btTerminate.setOnAction(e -> {ta.setText("");buildTerminateEmployeeGui();});
		btLogout   .setOnAction(e -> {ta.setText(""); buildLoginGui();});	

		vb.getChildren().addAll(message, btListEmp, btTerminate, btLogout, ta);
		vb.setAlignment(Pos.CENTER);
		vb.setPadding(new Insets(10));
		sp.getChildren().addAll(iv22, vb);
		sn = new Scene(sp, 800, 600);
		st.setScene(sn);
	}
	
	/**-----------------------------------------------------------------------------------------------------
	 * Builds the add new employee GUI.
	 * This screen is only visible to the boss.
	 * All fields are required. No field can be left empty.
	 * */	
	public void buildAddNewEmployeeGui() {
		GridPane gp = new GridPane();
		StackPane sp = new StackPane();
		VBox vb = new VBox(10);
		ta.setText("Enter new employees");
		message.setText("Enter the information of the new employee");
		Label message1 = new Label("Click OK to return to main menu");
		message1.setTextFill(Color.BLUE);
		message1.setFont(boldArial);
		
		btMainmenu.setText("OK");
		btMainmenu.setFont(boldArial);
		btMainmenu.setPrefWidth(300);
		btSubmit  .setPrefWidth(300);
		
		rbSalaried.setToggleGroup(groupCategory);
		rbSalaried.setUserData("S");
		rbSalaried.setSelected(true);
		rbHourly  .setToggleGroup(groupCategory);
		rbHourly  .setUserData("H");
		
		rbSalaried.setFont(boldArial);
		rbHourly  .setFont(boldArial);
		
		gp.add(rbSalaried, 0, 0);
		gp.add(rbHourly, 1, 0);
		gp.add(userLabel, 0, 2);
		gp.add(usernameField, 1, 2);
		gp.add(passwordLabel1, 0, 3);
		gp.add(passwordField1, 1, 3);
		gp.add(passwordLabel2, 0, 4);
		gp.add(passwordField2, 1, 4);
		gp.add(salaryLabel, 0, 5);
		gp.add(salaryField, 1, 5);
		gp.add(nameLabel, 0, 6);
		gp.add(nameField, 1, 6);
		gp.add(btSubmit, 1, 7);
		gp.add(btMainmenu, 1, 8);
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setPadding(new Insets(20,20,20,20));
		gp.setAlignment(Pos.CENTER);
		
		vb.getChildren().addAll(message, message1, gp, ta);
		vb.setAlignment(Pos.CENTER);
		btSubmit.setOnAction(e -> {
			newEmployee();
			usernameField.setText("");
			passwordField1.setText("");
			passwordField2.setText("");
			salaryField.setText("");
			nameField.setText("");
			});
		if(currentUserID == 0) {
			btMainmenu.setOnAction(e -> {ta.setText(""); buildBossGui();});
		}
		else {
			btMainmenu.setOnAction(e -> {ta.setText(""); buildEmployeeGui();});
		}
		sp.getChildren().addAll(iv44, vb);
		sn = new Scene(sp, 800, 600);
		st.setScene(sn);

	}
	
	/**-----------------------------------------------------------------------------------------------------
	 * Builds the change employee data GUI. 
	 * This screen is only visible to the boss.
	 * The boss can change the name or salary or both.
	 * */	
	public void buildChangeDataGui() {
		GridPane gp = new GridPane();
		StackPane sp = new StackPane();
		VBox vb = new VBox(10);
		Label message1 = new Label("Click OK to return to main menu");
		
		message   .setText("Enter the ID, new name and salary of the employee");
		message1  .setTextFill(Color.BLUE);
		btMainmenu.setText("OK");
		message1  .setFont(boldArial);
		IDField   .setPrefWidth(300);
		IDField   .setFont(regSans);
		IDLabel   .setFont(boldArial);
		btMainmenu.setFont(boldArial);
		btMainmenu.setPrefWidth(300);
		btSubmit  .setPrefWidth(300);
		
		gp.add(IDLabel, 0, 0);
		gp.add(IDField, 1, 0);
		gp.add(salaryLabel, 0, 1);
		gp.add(salaryField, 1, 1);
		gp.add(nameLabel, 0, 2);
		gp.add(nameField, 1, 2);
		gp.add(btSubmit, 1, 3);
		gp.add(btMainmenu, 1, 4);

		gp.setVgap(10);
		gp.setHgap(10);
		gp.setPadding(new Insets(20,20,20,20));
		gp.setAlignment(Pos.CENTER);
		
		vb.getChildren().addAll(message, message1, gp, ta);
		vb.setAlignment(Pos.CENTER);
		btSubmit.setOnAction(e -> changeEmployeeData());
		btMainmenu.setOnAction(e -> {ta.setText(""); buildBossGui();});

		sp.getChildren().addAll(iv44, vb);
		sn = new Scene(sp, 800, 600);
		st.setScene(sn);
	}
	
	/**-----------------------------------------------------------------------------------------------------
	 * Builds the pay employee GUI. 
	 * The boss only pay employee once every time he logs in.
	 * To pay salaried employee, click submit.
	 * To pay hourly employee, enter hours and click submit.
	 * */	
	public void buildPayEmployeeGui() {
		GridPane gp = new GridPane();
		StackPane sp = new StackPane();
		VBox vb = new VBox(10);
		Label hoursLabel = new Label("Hours");
		Label message1 = new Label("Click OK to return to main menu");
		message1  .setTextFill(Color.BLUE);
		message1  .setFont(boldArial);
		
		message   .setText("Paying employees");
		btMainmenu .setText("OK");
		btMainmenu.setFont(boldArial);
		empToPay  .setFont(boldArial);
		hoursLabel.setFont(boldArial);
		
		gp.add(hoursLabel, 0, 0);
		gp.add(salaryField, 1, 0);
		gp.add(btSubmit, 1, 1);
		gp.add(btMainmenu, 2, 1);
		gp.setVgap(20);
		gp.setHgap(20);
		gp.setPadding(new Insets(20,20,20,20));
		gp.setAlignment(Pos.CENTER);
		empToPay.setText ("Boss is salaried. Press Submit ");

		vb.getChildren().addAll(message, message1, empToPay, gp, ta);
		vb.setAlignment(Pos.CENTER);
		
		btSubmit.setOnAction( e-> { 
			empCount++;
			if (empCount<list.size()) {
				if (list.get(empCount) instanceof Hourly) {
					empToPay.setText(list.get(empCount).getName()+ " is hourly paid. Enter hours and press Submit ");
				}
				else {
					empToPay.setText(list.get(empCount).getName()+ " is salaried. Press Submit");
				}
				pay(); 
				printPay();
			}
			else if (empCount==list.size()) {
				pay(); 
				printPay();
				empToPay.setText("All of the employees have been paid.");
			}
			else {
				empToPay.setText("All of the employees have been paid.");
			}
		});
		
		if(currentUserID == 0) {
			btMainmenu.setOnAction(e -> {ta.setText(""); buildBossGui();});
		}
		else {
			btMainmenu.setOnAction(e -> {ta.setText(""); buildEmployeeGui();});
		}
		sp.getChildren().addAll(iv44, vb);
		sn = new Scene(sp, 800, 600);
		st.setScene(sn);		
	}
	
	/**-----------------------------------------------------------------------------------------------------
	 * pay employee
	 * */	
	public void pay(){		
		int length = list.size();
		double hours;
		Employee emp;
		if (empCount <= length){
			emp = list.get(empCount-1);
			if (emp instanceof Hourly) {
				//empToPay.setText(emp.getName()+ " is hourly paid. Enter hours and press Submit ");
				if (!salaryField.getText().equals("")) {
					hours = Double.parseDouble(salaryField.getText());
					((Hourly) emp).setHours(hours);
				}
			}
		}
		else{
			empToPay.setText("All of the employees have been paid.");
		}
		salaryField.setText("");
	}
	
	/**-----------------------------------------------------------------------------------------------------
	 * Print pay employee information
	 * */	
	public void printPay() {
		PrintWriter pay = null;
		StringBuilder employeePay = new StringBuilder();
		employeePay.append("\n\n*****************************************\n\t   Pay employees\n");
		employeePay.append("   "+new Date().toString()+"\n*****************************************\n");
		employeePay.append(String.format("%5s%2s%15s%2s%8s\n\n", "ID  ", " ", "Name      ", " ", "Pay($)  "));
		try {
			pay = new PrintWriter(new File("payroll.txt"));
			for (Employee em: list) {
				pay.printf("%05d   %-15s%12.2f\n", em.getEmployeeID(), em.getName(), em.getPay());
				pay.println();
				employeePay.append(String.format("%05d%4s%-15s%4s%8.2f\n\n", 
						em.getEmployeeID()," ", em.getName()," ", em.getPay()));
			}
		}
		catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}
		finally{
			pay.close();	
		}
		ta.setText(employeePay.toString());
	}
	
	/**-----------------------------------------------------------------------------------------------------
	 * Builds the terminate employee GUI. A boss can file any employee while a non-boss employee can quit.
	 * When a non-boss employee quits, returns to login screen
	 * */	
	public void buildTerminateEmployeeGui() {
		GridPane gp = new GridPane();
		StackPane sp = new StackPane();
		VBox vb = new VBox(10);
		sbRemove.append(String.format("\n%5s%2s%15s\n", "ID  "," ", "Name       "));
		
		Label message1 = new Label("Click OK to return to main menu");
		message1  .setTextFill(Color.BLUE);
		message1  .setFont(boldArial);
		
		IDField   .setPrefWidth(300);
		IDField   .setFont(regSans);
		IDLabel   .setFont(boldArial);
		btMainmenu.setFont(boldArial);
		
		gp.add(IDLabel, 0, 0);
		gp.add(IDField, 1, 0);
		gp.add(btSubmit, 1, 1);
		gp.add(btMainmenu, 2, 1);

		gp.setVgap(10);
		gp.setHgap(10);
		gp.setPadding(new Insets(20,20,20,20));
		gp.setAlignment(Pos.CENTER);

		if(currentUserID == 0) {
			message.setText("Enter the employee's ID to terminate");
			btMainmenu.setOnAction(e -> {ta.setText(""); buildBossGui();});
			btSubmit.setOnAction(e-> terminate());
			vb.getChildren().addAll(message, message1, gp, ta);
		}
		else {
			message.setText("Do you want to quit? Press Submit to quit!");
			gp.getChildren().remove(IDLabel);
			gp.getChildren().remove(IDField);
			btMainmenu.setOnAction(e -> {ta.setText(""); buildEmployeeGui();});
			btSubmit.setOnAction( e-> {terminate(); logout(); buildLoginGui();});
			vb.getChildren().addAll(message, message1, gp);
		}
		
		vb.setAlignment(Pos.CENTER);
		sp.getChildren().addAll(iv44, vb);
		sn = new Scene(sp, 800, 600);
		st.setScene(sn);
	}
	

	/**----------------------------------------------------------------------------------------------------
	 * @return void
	 * This function validate new login to assure it's uniqueness. 
	 * It also set currentUserLogin and currentUserID.
	 * @throws NoSuchAlgorithmException 
	 * */	
	public void doLogin() {
		byte[] tempPassword = null;
	
		currentUserLogin = usernameField.getText();
		Employee currentEmp = isEmployee(list, currentUserLogin);
		try {		
			tempPassword = computeHash((passwordField1.getText()));

			if (currentEmp != null && 
					Arrays.equals(currentEmp.getPassword(), tempPassword))
			 {
				message.setText("Welcome! You logged in as "+currentUserLogin+".");
				setCurrentUserID(currentUserLogin);
				if (currentUserID == 0) buildBossGui();
				else buildEmployeeGui();
			}
			else {
				message.setText("Either the username or password is invalid.");
			}
		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**----------------------------------------------------------------------------------------------------
	 * @param  ArrayList, String
	 * @return boolean
	 * This function checks whether the new login already exists.
	 * */	
	public Employee isEmployee(ArrayList<Employee> al, String empLogin){
		for (Employee em: al){
			if (empLogin.equals(em.getLogin())) return em;
		}
		return null;
	}
	
	/**----------------------------------------------------------------------------------------------------
	 * This function finds and returns an employee by searching login.
	 * @param Arraylist, String
	 * @return Employee
	 * */
	public Employee findEmployee (ArrayList<Employee> al, int ID){
		for (Employee e: al) {
			if (ID == e.getEmployeeID()) 
				return e;
		}
		return null;
	}
	
	/**----------------------------------------------------------------------------------------------------
	 * Function to set currentUserID .
	 * @param: String login
	 * @return: void
	 * */		
	public void setCurrentUserID(String login) {
		for (Employee em: list){
			if (login.equals(em.getLogin())) currentUserID = em.getEmployeeID();
		}
	}
	
	/** 
	 *  Source: Prof. Alice Fischer
	 *  Encrypt a string using SHA-256.   
	 *  input another.  Say whether second does or does not match first.
	 *  @param x  a String to be encrypted
	 *  @return   an encrypted String
	 *  @throws NoSuchAlgorithmException if the local system does not support SHA-1.
	 */
	 private static byte[] computeHash( String x ) throws NoSuchAlgorithmException {
	      MessageDigest d = MessageDigest.getInstance("SHA-256");
	      d.update(x.getBytes());
	      return  d.digest();
	 }
	
	/**-----------------------------------------------------------------------------------------------------
	 * This function creates a new employee by calling Employee constructor. 
	 * Only the boss has the privilege.
	 * */		
	 public void newEmployee()  {
		 Employee newEmp;
		 employeeCategory = groupCategory.getSelectedToggle().getUserData().toString().charAt(0);
		 if (usernameField.getText().equals("") || usernameField.getText().equals("") ||
				 passwordField1.getText().equals("") || passwordField2.getText().equals("") ||
				 salaryField.getText().equals("") || nameField.getText().equals("")) {
			 ta.setText("ERROR! No field can be empty!");
		 }
		 else {
			 login = usernameField.getText();
			 if (isEmployee(list, login)!=null){
				 ta.setText("\nThe login already exists. Enter the employee's login: ");
			 }
			 else {
				 try {
					 password = computeHash(passwordField1.getText());
				 } catch (NoSuchAlgorithmException e) {
				 	// TODO Auto-generated catch block
				 	e.printStackTrace();
				 }
				 salaryOrHourRate = Double.parseDouble(salaryField.getText());
				 name = nameField.getText();
				 switch(employeeCategory){
				 	case 'S': 
				 		newEmp = new Salaried(login, password, salaryOrHourRate, name);
				 		break;
				 	case 'H': 
				 		newEmp = new Hourly(login, password, salaryOrHourRate, name);
				 		break;
				 	default:
				 		newEmp = null;
				 }
				 if (newEmp != null) {
					 list.add(newEmp);
					 ta.setText("The following employee has been added:\n"+newEmp.toString());
				 }
		 	 }
		 }
	}
	
	/**----------------------------------------------------------------------------------------------------
	 * Print employee information.
	 * The boss has the privilege to list all of the employees, while an employee can only list his own information.
	 * */	
	 public void listEmployee() {
		StringBuilder employeeList = new StringBuilder();
		employeeList.append(String.format("%5s%2s%10s%2s%10s%2s%10s%2s%15s%2s%15s", 
				"ID  ", " ", "username ", " ", "Password ", " ", "Salary  ", " ", "Date     ", " ", "Name      \n\n"));
		
		if (currentUserID == 0) {
			//when logged in as boss, list all employee's information
			for (Employee em:list) {
				employeeList.append(em.toString()+"\n");
			}
		}
		else {
			//when logged in as employee, list own information
			employeeList.append(findEmployee(list, currentUserID).toString()+"\n");
		}
		ta.setText(employeeList.toString());
	}
	
	/**----------------------------------------------------------------------------------------------------
	 * Function to change employee's name or salary or both.
	 * Only the boss has the privilege. 
	 * */		
	public void changeEmployeeData() {
		Employee em = null;	    //Employee object
		int employeeToChange;	//employee's login
		double newSalary = 0;	//new salary or hourly rate
		String newName = null, oldName = null, info = "";
		if (!IDField.getText().equals("")) {
			employeeToChange = Integer.parseInt(IDField.getText());
			em = findEmployee(list, employeeToChange);
			if (em != null ) {
				oldName = em.getName();
				newName = oldName;
				if (!salaryField.getText().equals("")) {
					newSalary=Double.parseDouble(salaryField.getText());
					em.setSalary(newSalary);
					info += oldName + "'s salary has been changed to " + newSalary + ".\n";			
				}
				if (!nameField.getText().equals("")) {
					newName=nameField.getText();
					em.setName(newName);
					info += oldName + "'s name hae been changed to " + newName+"\n";
				}
				ta.setText(info);
			}
		}
		else ta.setText("The employee ID is invalid.");
		salaryField.setText(""); 
		nameField.setText("");
		IDField.setText("");
	}

	/**----------------------------------------------------------------------------------------------------
	 * Function for the boss to remove an employee, or an employee to quit the job. 
	 * To return to main menu, enter 0.
	 * */	
	public void terminate(){
		int employeeIDToRemove;
		Employee empToRemove;
		
		//when logged in as the boss, boss can terminate an employee
		if (currentUserID == 0) {
			if (!IDField.getText().equals("")) {
				employeeIDToRemove = Integer.parseInt(IDField.getText());
				if (findEmployee(list, employeeIDToRemove) == null) {
					message.setText("ERROR! The ID number is invalid.");
				}			
			//remove the selected employee from list and add it to empToremove list
				else {
					empToRemove = findEmployee(list, employeeIDToRemove);
					removed.add(empToRemove);
					list.remove(empToRemove);
					System.out.printf("\nEmployee %s has been removed from the system.\n", empToRemove.getName());
					sbRemove.append(String.format("\n%05d%2s%-15s\n", 
							empToRemove.getEmployeeID()," ", empToRemove.getName()));
				}
			}
		}
		//when logged in as an employee, he can quit
		else {			
			empToRemove = findEmployee(list, currentUserID);
			removed.add(empToRemove);
			list.remove(empToRemove);
			System.out.printf("\nEmployee %s has been removed from the system.\n", empToRemove.getName());
		}
		ta.setText(sbRemove.toString());
		IDField.setText("");
	}
	

	/**-------------------------------------------------------------------------------------------------
	 * This function log outs. 
	 * */	
	public void logout() {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("employee.txt");
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
        ObjectOutputStream oos  = null;
        try{
        	oos  = new ObjectOutputStream(fos);
        	oos.writeObject(list); 
			System.out.println("\nFile employee.txt has been updated successfully.");
        }
        catch (IOException e) {
			e.printStackTrace();
		}
        finally {
        	try {
				fos.flush();
				fos.close(); 
			} 
        	catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	/**-------------------------------------------------------------------------------------------------
	 * main function used in IDE.
	 * */
	public static void main (String[] args) {
		Application.launch(args);
	}
}
	