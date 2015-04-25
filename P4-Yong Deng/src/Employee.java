/**
 * Employee.java
 * Program 4: A User Database
 * Phase 1 of the Payroll project.
 * 
 * @author Yong Deng
 * @since  2/9/2015
 */

import java.util.*;

public class Employee {
	private final String login;		//user name for login
	private double salary;			//employee's salary
	private String name;			//name of employee
	private final Date date;		//date when new employee created
	private final int employeeID;	//unique employee ID number, 5 digits, final
	private static int nextId;		//automatic increment, holds temporary employee ID number
	
/**-----------------------------------------------------------------------------------------------
* Constructor: creates a new employee when given login, salary and name
* @param: login, salary, name
* */		
	public Employee(String login, double salary, String name) {
		this.login=login;
		this.salary=salary;
		this.name=name;
		date = new Date();
		employeeID = nextId;
		nextId++;
	}
	
/**-----------------------------------------------------------------------------------------------
* Constructor: creates a new employee when given employeeID, login, salary, date and name
* used for read file
* @param: employeeID, login, salary, date, name
* */		
	public Employee (int employeeID, String login, double salary, long date, String name) {
		this.employeeID=employeeID;
		this.login=login;
		this.salary=salary;
		this.date=new Date(date);
		this.name=name;
		employeeID = nextId;
		nextId++;
	}
	
/**-----------------------------------------------------------------------------------------------
* getter for login
* @return: String
* */		
	public String getLogin(){
		return login;
	}
	
/**-----------------------------------------------------------------------------------------------
* getter for employeeID
* @return: int
* */	
	public int getEmployeeID(){
		return employeeID;
	}
	
/**-----------------------------------------------------------------------------------------------
* Setter for salary
* */	
	public void setSalary(double salary){
		this.salary=salary;
	}

	public String toString(){
		return String.format("%05d\t%s\t%8.2f\t%d\t%s",employeeID, login, salary,date.getTime(), name);
	}
}
