/**
 * Employee.java
 * Program 6: A User Database
 * Phase 3 of the Payroll project.
 * 
 * @author Yong Deng
 * @since  3/3/2015
 */

import java.util.*;
import java.io.*;

@SuppressWarnings("serial")
public abstract class Employee implements Serializable{
	protected final String login;		//user name for login
	protected double salary;			//employee's salary
	protected String name;				//name of employee
	protected final Date date;			//date when new employee created
	protected final int employeeID;		//unique employee ID number, 5 digits, final
	protected static int nextId;		//automatic increment, holds temporary employee ID number
	protected byte[] password;			//user's password
	/**-----------------------------------------------------------------------------------------------
	 * Constructor: creates a new employee when given login, salary and name
	 * @param String login, double salary, String name
	 * */		
	public Employee(String login, byte[] password, double salary, String name) {
		this.login=login;
		this.password = password;
		this.salary=salary;
		this.name=name;
		date = new Date();
		employeeID = nextId;
		nextId++;
	}
	
	/**-----------------------------------------------------------------------------------------------
	* Constructor with five parameters. 
	* @param int employeeID, String login, double salary, long date, String name
	* */
	public Employee (int employeeID, String login, byte[] password, double salary, long date, String name) {
		this.employeeID=employeeID;
		this.login=login;
		this.password = password;
		this.salary=salary;
		this.date=new Date(date);
		this.name=name;
		nextId=++employeeID;
	}
	
	/**-----------------------------------------------------------------------------------------------
	 * getter for name
	 * @return String
	 * */		
	public String getName(){
		return name;
	}
	
	/**-----------------------------------------------------------------------------------------------
	 * getter for login
	 * @return String
	 * */		
	public String getLogin(){
		return login;
	}
	
	/**-----------------------------------------------------------------------------------------------
	 * getter for employeeID
	 * @return int
	 * */	
	public int getEmployeeID(){
		return employeeID;
	}
	
	/**-----------------------------------------------------------------------------------------------
	 * getter for date
	 * @return long
	 * */	
	public long getDate(){
		return date.getTime();
	}
	
	/**-----------------------------------------------------------------------------------------------
	 * getter for salary
	 * @return double
	 * */	
	public double getSalary(){
		return salary;
	}
	
	/**-----------------------------------------------------------------------------------------------
	 * getter for password
	 * @return String
	 * */	
	public byte[] getPassword() {
		return password;
	}
	
	/**-----------------------------------------------------------------------------------------------
	 * Setter for salary
	 * @param double
	 * @return void
	 * */	
	public void setSalary(double salary){
		this.salary=salary;
	}

	/**-----------------------------------------------------------------------------------------------
	 * Setter for name
	 * @param String
	 * @return void
	 * */	
	public void setName(String name){
		this.name=name;
	}
	/**-----------------------------------------------------------------------------------------------
	* Abstract function to pay employee
	* */
	public abstract double getPay();

	public String toString(){
		return String.format("%05d%2s%-10s%2s%-10s%2s%10.2f%2s%15d%2s%-15s", employeeID," ", login," ", 
				password.toString()," ", salary," ", date.getTime()," ", name);
	}
}
