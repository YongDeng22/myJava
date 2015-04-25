/**
 * Payroll.java
 * Program 5: A User Database
 * Phase 2 of the Payroll project.
 * 
 * @author Yong Deng
 * @since  3/3/2015
 */

import java.io.*;
import java.util.*;

/**---------------------------------------------------------------------------------------------------------
* Class Payroll. Controller. Creates a payroll system.
* */
public class Payroll {
	private static final String menu = "Payroll Menu\n\t1. Login \n\t"
			+"2. Enter employees\n\t3. List Employees\n\t"
			+"4. Change employee data\n\t5. Terminate an employee"
			+"\n\t6. Pay employees \n\t0. Exit system";;
	private String login, name;			//user name for login and the real name of employee
	private double salaryOrHourRate;			//employee's salary (Salaried) or hourly rate (Hourly)
	private int currentUserID=-1;		//current user's ID, distinguish boss from regular employees
	private String currentUserLogin;	//current user's login, unique
	private long date;					//date when employee was created
	private int employeeID;				//final and unique
	private int countRemoved;			//count the number of employees removed from the database
	private char employeeCategory;		//salaried (s) or hourly (h)
	private Scanner sc = new Scanner(System.in);
	private ArrayList<Employee> list = new ArrayList<Employee>();	//An ArrayList holds current Employee objects
	private ArrayList<Employee> removed = new ArrayList<Employee>();//An ArrayList holds removed Employee objects

	/**-----------------------------------------------------------------------------------------------------
	 * Payroll constructor
	 * */
	@SuppressWarnings("unchecked")
	public Payroll() throws FileNotFoundException, IOException, ClassNotFoundException{
		try{
			//recover employee data from file
			FileInputStream fis = new FileInputStream("employee.txt");
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        ArrayList<Employee> temp = (ArrayList<Employee>) ois.readObject();
	        for (Employee e: temp) {
	        	//read employee information back from the file
	        	employeeID = e.getEmployeeID();
	        	login = e.getLogin();
	        	salaryOrHourRate = e.getSalary();
	        	date = e.getDate();
	        	name = e.getName();
	        	if (e instanceof Salaried) {
	        		list.add(new Salaried(employeeID, login, salaryOrHourRate, date, name));
	        	}
	        	else {
	        		//if employee is hourly paid, get hourly rate and create employee
	        		//salaryOrHourRate = ((Hourly)e).getHourRate();
	        		list.add(new Hourly(employeeID, login, salaryOrHourRate, date, name));
	        	}
	        }
	        ois.close();
		}
		
		catch (FileNotFoundException fe){
			//when no employee exists, create a new file employee.txt, create a boss account who can 
			//create new employee account and have full access to the database
			System.out.println("\nError! File employee.txt not found.");
			PrintWriter pw = new PrintWriter (new File("employee.txt"));
			pw.close();
			System.out.println("\nEnter the Boss's login: ");
			login = sc.next();
			System.out.println("Enter the Boss's salary: ");
			salaryOrHourRate = sc.nextDouble();
			sc.nextLine();
			System.out.println("Enter the Boss's name: ");
			name = sc.nextLine();
			list.add(new Salaried(login, salaryOrHourRate, name));
		}
	}
	/**----------------------------------------------------------------------------------------------------
	 * @return void
	 * This function validate new login to assure it's uniqueness. 
	 * It also set currentUserLogin and currentUserID.
	 * */	
	private void doLogin(){
		System.out.println("\nEnter your login: ");
		currentUserLogin = sc.next();
		if (!isEmployee(list, currentUserLogin))
			System.out.println("The login entered is not valid.");
		else{
			System.out.println("Welcome! You logged in as "+currentUserLogin+".");
			//currentUserLogin = login;
			setCurrentUserID(currentUserLogin);
		}
	}
	
	/**----------------------------------------------------------------------------------------------------
	 * @param  ArrayList, String
	 * @return boolean
	 * This function checks whether the new login already exists.
	 * */	
	private boolean isEmployee(ArrayList<Employee> al, String empLogin){
		for (Employee em: al){
			if (empLogin.equals(em.getLogin())) return true;
		}
		return false;
	}
	
	/**----------------------------------------------------------------------------------------------------
	 * This function finds and returns an employee by searching login.
	 * @param Arraylist, String
	 * @return Employee
	 * */
	private Employee findEmployee (ArrayList<Employee> al, int ID){
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
	private void setCurrentUserID(String login) {
		for (Employee em: list){
			if (login.equals(em.getLogin())) currentUserID = em.getEmployeeID();
		}
	}
	
	/**-----------------------------------------------------------------------------------------------------
	 * This function creates a new employee by calling Employee constructor. 
	 * Only the boss has the privilege.
	 * */		
	private void newEmployee() throws FileNotFoundException, IOException {
		do {
			System.out.println("\nEnter the employee's category (enter S if Salaried, H if Hourly): ");
			employeeCategory = Character.toLowerCase(sc.next().charAt(0));
			if (employeeCategory != 's' && employeeCategory != 'h')
				System.out.println("\nInput ERROR! Enter the employee's category (enter S if Salaried, H if Hourly):");
		} while (employeeCategory != 's' && employeeCategory != 'h');
		
		System.out.println("\nEnter the employee's login: ");
		login = sc.next();
		while (isEmployee(list, login)){
			System.out.println("The login already exists. Enter the employee's login: ");
			login = sc.next();
		}
		System.out.println("Enter the employee's salary or hourly rate: ");
		salaryOrHourRate = sc.nextDouble();
		sc.nextLine();
		System.out.println("Enter the employee name: ");
		name = sc.nextLine().trim();
		switch(employeeCategory){
			case 's': list.add(new Salaried(login, salaryOrHourRate, name)); break;
			case 'h': list.add(new Hourly(login, salaryOrHourRate, name)); break;
		}
	}
	
	/**----------------------------------------------------------------------------------------------------
	 * Print employees in a list.
	 * Only the boss has the privilege.
	 * */	
	public void listEmployee(ArrayList<Employee> al) {
		if (currentUserID == 0) {
			for (Employee e:al) System.out.println(e.toString());
		}
		else {
			System.out.println(findEmployee(list, currentUserID).toString());
		}
		
	}
	
	/**----------------------------------------------------------------------------------------------------
	 * Function to change employee's name.
	 * All of the employees have the privilege.
	 * */		
	public void changeEmployeeData() {
		Employee em = null;	//Employee object
		String empLogin;	//employee's login
		double newSalary;	//new salary or hourly rate
		
		em = findEmployee(list, currentUserID);
		System.out.println("\nDo you want to your name? Y/N: ");	//ask user whether wants to change his/her name or not
		if (sc.next().toLowerCase().equals("y")){
			//if yes, prompt the user to change name
			System.out.println("\nEnter your new name: ");
			sc.nextLine();
			em.setName(sc.nextLine());
		}
		else System.out.println("\nYour name is not changed.\n");	//if no, print message
		
		/*when logged in as boss, the boss can change any employee's salary or hourly rate*/
		if (currentUserID==0) {
			//ask whether the boss want to change an employee's salary (Salaried) or hourly rate (Hourly)
			System.out.println("\nDo you want to change an employee's salary or hourly rate? Y/N: ");
			if (sc.next().toLowerCase().equals("y")){
				//if yes, boss is able to input the employee's login; if q, return to main menu
				System.out.println("\nEnter login of the employee to remove (Enter q to return to main menu) ");
				empLogin = sc.next();
				while (!empLogin.equals("q")) {
					//validate login
					if (!isEmployee(list, empLogin)) {
						System.out.println("\nERROR! The login entered is not valid.");
						System.out.println("Enter login of the employee to remove (Enter q to return to main menu) ");
						empLogin = sc.next();
					}
				}
				
				if (empLogin.equals("q")) return;	//return to main menu when the boss choose to quit
				
				em=findEmployee(list, currentUserID);
				if (em instanceof Salaried){
					//if the employee is salaried, change salary
					System.out.printf("\nEnter %s's new salary: ", em.getName());
					newSalary=sc.nextDouble();
					em.setSalary(newSalary);
					System.out.printf("\nEmployee %s's salary has been set at $%.2f.\n", em.getName(), newSalary);
				}
				else {
					//if the employee is hourly, change hourly rate
					System.out.printf("\nEnter %s's new hourly rate: ", em.getName());
					newSalary=sc.nextDouble();
					((Hourly)em).setSalary(newSalary);
					System.out.printf("\nEmployee %s's hourly rate has been set at $%.2f.\n", em.getName(), newSalary);
				}
			}
			else System.out.println("\nNo change in salary has been made.\n");
		}
	}
	
	/**----------------------------------------------------------------------------------------------------
	 * Function for the boss to remove an employee. Only the boss has the privilege.
	 * The boss is prompted to enter the login of the employee who is going to be removed.
	 * To return to main menu, enter q.
	 * */	
	public void terminate(){
		int employeeIDToRemove;
		Employee empToRemove;
		
		if (currentUserID == 0) {
			System.out.println("\nEnter ID of the employee to remove (Enter 0 to return to main menu) ");
			employeeIDToRemove = sc.nextInt();
			while (employeeIDToRemove != 0) {
				if (!isEmployee(list, findEmployee(list, employeeIDToRemove).getLogin())) {
					System.out.println("\nERROR! The ID number is invalid.");
					System.out.println("Enter ID of the employee to remove (Enter 0 to return to main menu) ");
					employeeIDToRemove = sc.nextInt();
				}
			}
			if (employeeIDToRemove == 0) return;
			empToRemove = findEmployee(list, employeeIDToRemove);
			removed.add(empToRemove);
			list.remove(empToRemove);
			System.out.printf("\nEmployee %s has been removed from the system.\n", empToRemove.getName());
		}
		else {
			empToRemove = findEmployee(list, currentUserID);
			removed.add(empToRemove);
			list.remove(empToRemove);
			System.out.printf("\nEmployee %s has been removed from the system.\n", empToRemove.getName());
		}
	}
	
	/**-------------------------------------------------------------------------------------------------
	 * function to pay employee. Only the boss has the privilege.
	 * for Salaried employee, pay biweekly
	 * for Hourly employee, pay based on hours worked
	 * */	
	public void payEmployee() throws IOException {
		ArrayList<Employee> temp = list; //A tempory ArrayList to store employee information when get paid
		PrintWriter pay = new PrintWriter(new File("payroll.txt"));
		for (Employee e: temp) {
			//ask for the hours that a hourly paid employee worked during the pay period
			if (e instanceof Hourly) {
				System.out.printf("\nEnter the hours %s worked during this pay period: \n", e.getName());
				double hours = sc.nextDouble();
				((Hourly) e).setHours(hours);
			}
		}
		System.out.println("\n\tPay employees\n*****************************");
		System.out.println("\n\t"+new Date().toString()+"\n*****************************");
		System.out.printf("\n %-4s     %-15s  %-6s\n", "ID","Name","Pay($)");
		System.out.printf("%-4s  %-15s   %-8s\n", "-----","----------","--------");
		try{
			//pay all of the employees, write the payment information into file payroll.text
			for (Employee e: temp) {
				System.out.printf("\n%05d   %-15s%10.2f\n", e.getEmployeeID(), e.getName(), e.getPay());
				pay.printf("%05d    %8.2f   %-15s", e.getEmployeeID(), e.getPay(), e.getName());
				pay.println();
			}
		}
		finally{
			pay.close();	
		}
	}
	
	/**-------------------------------------------------------------------------------------------------
	 * This function asks the user to select an option from the menu and calling other functions 
	 * to process the request.
	 * */	
	public void doMenu() throws FileNotFoundException, IOException{
		int selection;		//option of the menu entered by user 
		try{
			while(true){
				System.out.println("\n"+menu);
				System.out.println("Select a menu option (a number 0-6): ");
				selection = sc.nextInt();
				while (selection<0 || selection>6){
					//validate input---------------
					System.out.println("Selection is invalid. Select a menu option (a number 0-6): ");
					selection = sc.nextInt();
				}
				while (currentUserID!=0 && (selection==2 || selection==3 || selection==5 || selection==6)) {
					//validate the privilege of current user--------------------------------
					System.out.println("\nYou don't have authority to perform the operation! Select an option: ");
					selection = sc.nextInt();
				}
				switch(selection){
					//calling other utility functions to process user request.
					case 1: doLogin(); break;
					case 2: newEmployee(); break;
					case 3: listEmployee(list); break;
					case 4: changeEmployeeData(); break;
					case 5: terminate(); countRemoved++; break;
					case 6: payEmployee(); break;
					case 0: System.out.println("\nYou selected 0: Exit System.");
							return;
					default: break;
				}
			}
		}
		catch (FileNotFoundException fe){
			System.out.println ("\nError! File employee.txt not found.");
		}
		finally{
			//when exit, write employee data back to file employee.txt and clean up.
	        FileOutputStream fos = new FileOutputStream("employee.txt"); 
	        ObjectOutputStream oos  = new ObjectOutputStream(fos);
	        try{
	            oos.writeObject(list); 
				System.out.println("\nFile employee.txt has been updated successfully.");
	        }
	        finally {
	        	if (countRemoved>0){
	        		//if any employee is removed, print them
	        		System.out.println("\nThe following employees have been removed from the database: ");
	        		for (Employee e:removed) {
	        			System.out.println(e.toString());
	        		}
	        	}
	        	//Flush memory and close file
	        	fos.flush();
	        	fos.close(); 
	        }
		}
	}
}