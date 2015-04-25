
/**
 * Hourly.java
 * Program 6: A User Database
 * Phase 3 of the Payroll project.
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
	public Hourly(String login, byte[] password, double salary, String name) {
		super(login, password, salary,  name);
	}
	
	/**-----------------------------------------------------------------------------------------------
	* Constructor with five parameters. Used to read hourly employee data from file
	* @param int employeeID, String login, double salary, long date, String name
	* salary: hourly rate
	* */
	public Hourly (int employeeID, String login, byte[] password, double salary, long date, String name) {
		super(employeeID, login, password, salary, date, name);
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
