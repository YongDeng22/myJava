/**
 * Salaried.java
 * Program 6: A User Database
 * Phase 3 of the Payroll project.
 * 
 * @author Yong Deng
 * @since  3/3/2015
 */

import java.io.Serializable;

@SuppressWarnings("serial")
public class Salaried extends Employee implements Serializable{
	/**-----------------------------------------------------------------------------------------------
	* Constructor with three parameters. Used to generate new Salaried employee
	* @param String login, double salary, String name
	* */
	public Salaried(String login,  byte[] password, double salary,String name) {
		super(login, password, salary, name);
	}
	
	/**-----------------------------------------------------------------------------------------------
	* Constructor with five parameters. Used to read Salaried employee data from file
	* @param int employeeID, String login, double salary, long date, String name
	* */
	public Salaried (int employeeID, String login, byte[] password, double salary, long date, String name) {
		super(employeeID, login, password, salary, date, name);
	}
	
	/**-----------------------------------------------------------------------------------------------
	* Function to pay salaried employee 
	* */
	public double getPay() {
		return super.salary/24;
	}
}
