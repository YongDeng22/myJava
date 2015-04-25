
/**
 * Hourly.java
 * Program 5: A User Database
 * Phase 2 of the Payroll project.
 * 
 * @author Yong Deng
 * @since  3/3/2015
 */

import java.io.Serializable;

@SuppressWarnings("serial")
public class Hourly extends Employee implements Serializable{
	private double hours;
	/**-----------------------------------------------------------------------------------------------
	* Constructor with three parameters. Used to generate new hourly employee
	* @param String login, double salary, String name
	* salary: hourly rate
	* */
	public Hourly(String login, double salary, String name) {
		super(login, salary, name);
	}
	
	/**-----------------------------------------------------------------------------------------------
	* Constructor with five parameters. Used to read hourly employee data from file
	* @param int employeeID, String login, double salary, long date, String name
	* salary: hourly rate
	* */
	public Hourly (int employeeID, String login, double salary, long date, String name) {
		super(employeeID, login, salary, date, name);
	}
	
	/**-----------------------------------------------------------------------------------------------
	* function to pay hourly employee
	* */
	public double getPay() {
		return super.salary * hours;
	}
	
	/**-----------------------------------------------------------------------------------------------
	* setter for hours worked
	* */
	public void setHours(double hours){
		this.hours=hours;
	}
}
