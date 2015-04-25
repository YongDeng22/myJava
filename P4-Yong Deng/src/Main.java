/**
 * Main.java
 * Program 4: A User Database
 * Phase 1 of the Payroll project.
 * 
 * @author Yong Deng
 * @since  2/9/2015
 */


import java.io.*;
import java.util.*;

public class Main {
	/**-------------------------------------------------------------------------------------
	* main function
	* */
	public static void main(String[] args) {
		System.out.println("\tProgram 4: A User Database");
		System.out.println("\t\tYong Deng");
		
		//create new payroll system, may through exceptions
		try{
			Payroll pr = new Payroll();
			pr.doMenu();
		}
		//catch input data type mismatch errors
		catch (InputMismatchException ime){
			System.out.println("\n"+ime+" :Input Error! Check data type!\n");
			ime.printStackTrace();
		}
		//catch IO exceptions
		catch (IOException e){
			System.out.println("An IO error occured.");
			e.printStackTrace();
			System.exit(0);
		}
		//catch all the other exceptions
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
