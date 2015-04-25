/**
 * Payroll.java
 * Program 4: A User Database
 * Phase 1 of the Payroll project.
 * 
 * @author Yong Deng
 * @since  2/9/2015
 */

import java.io.*;
import java.util.*;

/*---------------------------------------------------------------------------------------------------------
* Class Payroll. Controller. Creates a payroll system.
* */
public class Payroll {
	private static final String menu = "Payroll Menu\n\t1. Login \n\t"
			+"2. Enter employees\n\t3. List Employees\n\t"
			+"4. Change employee data\n\t5. Terminate an employee"
			+"\n\t6. Pay employees \n\t0. Exit system";;
	private String login, name;							//user name for login, the real name of employee
	double salary;										//employee's salary
	private int currentUserID=-1;						//current user's ID, distinguish boss from regular employees
	private String currentUserLogin;					//current user's login, unique
	private long date;
	private int employeeID;
	private Scanner sc = new Scanner(System.in);
	private ArrayList<Employee> list = new ArrayList<Employee>();	//An ArrayList holds Employee objects

/**---------------------------------------------------------------------------------------------------------
* Payroll constructor
* */
	public Payroll() throws FileNotFoundException{
		Scanner infile = null;
		try{
			File file = new File("employee.txt");
			if (!file.exists()) throw new FileNotFoundException();
			infile = new Scanner(file);
			//read information from file employee.txt to create Employees and put them in ArrayList list. 
			while(infile.hasNext()){
				employeeID=infile.nextInt();
				login=infile.next();
				salary=infile.nextDouble();
				date=infile.nextLong();
				name=infile.nextLine().trim();
				list.add(new Employee(employeeID, login, salary, date, name));
			}
			infile.close();
		}
		/*when no employee exists, create a new file employee.txt, create a boss account who can 
		 * create new employee account and have full access to the database----------------------------- */
		catch (FileNotFoundException fe){
			System.out.println("\nError! File employee.txt not found.");
			PrintWriter pw = new PrintWriter (new File("employee.txt"));
			pw.close();
			System.out.println("\nEnter the Boss's login: ");
			login = sc.next();
			System.out.println("Enter the Boss's salary: ");
			salary = sc.nextDouble();
			sc.nextLine();
			System.out.println("Enter the Boss's name: ");
			name = sc.nextLine();
			list.add(new Employee(login, salary, name));
		}
	}
/**--------------------------------------------------------------------------------------------------------
 * @param:
 * @return: void
 * This function validate new login to assure it's uniqueness. It also set currentUserLogin and currentUserID.
 * */	
	private void doLogin(){
		System.out.println("\nEnter your login: ");
		currentUserLogin = sc.next();
		if (!isEmployee(list, currentUserLogin))
			System.out.println("The user name entered is not valid.");
		else{
			System.out.println("Welcome! You logged in as "+currentUserLogin+".");
			//currentUserLogin = login;
			setCurrentUserID(currentUserLogin);
		}
	}
/**--------------------------------------------------------------------------------------------------------
* @param: String login
* @return: boolean
* This function checks whether the new login already exists.
 * */	
	private boolean isEmployee(ArrayList<Employee> al, String login){
		for (Employee em: al){
			if (login.equals(em.getLogin())) return true;
		}
		return false;
	}
/**--------------------------------------------------------------------------------------------------------
* Function to set currentUserID .
* @param: String login
* @return: void
* */		
	private void setCurrentUserID(String login) {
		for (Employee em: list){
			if (login.equals(em.getLogin())) currentUserID = em.getEmployeeID();
		}
	}
/**---------------------------------------------------------------------------------------------------------
* This function creates a new employee by calling Employee constructor. 
* */		
	private void newEmployee() throws FileNotFoundException {
		System.out.println("\nEnter the employee's login: ");
		login = sc.next();
		while (isEmployee(list, login)){
			System.out.println("The login already exists. Enter the employee's login: ");
			login = sc.next();
		}
		System.out.println("Enter the employee's salary: ");
		salary = sc.nextDouble();
		sc.nextLine();
		System.out.println("Enter the employee name: ");
		name = sc.nextLine().trim();
		list.add(new Employee(login, salary, name));
	}
	
	public void listEmployee() {
	/*---------------------------------------------------------------------------------------------------------
	* Stub function
	* */		
	};
	public void changeEmployeeData() {
	/*---------------------------------------------------------------------------------------------------------
	 * Stub function
	 * */		
	};
	public void terminate(){
	/*---------------------------------------------------------------------------------------------------------
	* Stub function
	* */
	}
	public void payEmployee() {
	/*---------------------------------------------------------------------------------------------------------
	 * Stub function
	 * */	
	};
	
/**---------------------------------------------------------------------------------------------------------
* This function asks the user to select an option from the menu and calling other functions to process the request.
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
					System.out.println("Selection is invalid. Enter a number 0 to 5");
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
					case 3: listEmployee(); break;
					case 4: changeEmployeeData(); break;
					case 5: terminate(); break;
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
			PrintWriter pw = new PrintWriter("employee.txt") ;
			for (Employee e:list){
				pw.println(e.toString());
			}
			pw.close();
			System.out.println("\nFile employee.txt has been updated successfully.");
		}
	}
}