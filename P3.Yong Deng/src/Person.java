/**
 * @author Yong Deng
 * @since  1/25/2015
 * @Param  name, age
 */

/*
 * P3: Family
 * This is a program to calculate the average age of the members of a family.
 * The user is asked to input the number of family members, the name and age 
 * of each family member. The program will then give the average age.
 * */

import java.util.*;

//------------------------------------------------------------------------------------------------
public class Person {
	private String name;	//The name of family member
	private int age;		//The age of family member. Cannot be less than 0.
	
	/*--------------------------------------------------------------------------------------------
	 * Constructor
	 */
	Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	/*--------------------------------------------------------------------------------------------
	 * A getter function to return age.
	 */
	public int getAge() {
		return age;
	}
	
	/*--------------------------------------------------------------------------------------------
	 * toString function to print object information
	 */
	public String toString() {
		return "  "+name+": "+age+" years old";
	}
	
	/*--------------------------------------------------------------------------------------------
	 * A function taking an array of Person type objects, calculates and returns the average age.
	 */
	public static double compute(Person[] p) {
		int sum = 0;
		for (int x=0; x<p.length; x++){
			sum += p[x].getAge();
		}
		return (double)sum/(p.length);
	}	
	
	/*--------------------------------------------------------------------------------------------
	 * Main function
	 */
	public static void main(String[] args) {
		int numberOfMember;		//The number of family members. Cannot be 0 or less.
		String name;			//The name of family member
		int age;				//The age of family member. Cannot be less than 0.
		
		System.out.println("\tYong Deng	Assignment: P3: Family\n");
		
		Scanner input = new Scanner(System.in);
		System.out.println("How many people are there in your family? ");
		/*----------------------------------------------------------------------------------------
		 * Input the number of family members. It must be bigger than 0.
		 */
		numberOfMember = input.nextInt();
		while (numberOfMember <= 0) {
			System.out.println("Your input is invalid. Enter a positive number: ");
			numberOfMember = input.nextInt();
		}
		/*----------------------------------------------------------------------------------------
		 * Ask the user to input the name and age of each family member
		 */
		Person[] familyMember = new Person[numberOfMember];
		for (int x=0; x<numberOfMember; x++) {
			System.out.printf("Enter the name and age of member %d: ", x+1);
			name = input.next();
			age = input.nextInt();
			//------validate input. Age cannot be less than 0.------------------------------------
			while (age < 0) {
				System.out.println("Age cannot be smaller than 0. Enter the age: ");
				age = input.nextInt();
			}
			familyMember[x] = new Person(name, age);
		}
		/*----------------------------------------------------------------------------------------
		 * Print the name and age of all of the family members.
		 */
		System.out.printf("\nThe %d family members are: \n", numberOfMember);
		for (int x=0; x<numberOfMember; x++) {
			System.out.println (familyMember[x]);
		}
		System.out.printf("\nThe avergae age of the family is: %4.2f", compute(familyMember));
	}
}

//********************************** SAMPLE OUTPUTS **********************************************
/*------------------------------------------------------------------------------------------------
	Yong Deng	Assignment: P3: Family

How many people are there in your family? 
0
Your input is invalid. Enter a positive number: 
-2
Your input is invalid. Enter a positive number: 
1
Enter the name and age of member 1: michael 50

The 1 family members are: 
  michael: 50 years old

The avergae age of the family is: 50.00
-------------------------------------------------------------------------------------------------*/

/*------------------------------------------------------------------------------------------------
	Yong Deng	Assignment: P3: Family

How many people are there in your family? 
3
Enter the name and age of member 1: michael 50
Enter the name and age of member 2: bob 35
Enter the name and age of member 3: geroge 26

The 3 family members are: 
  michael: 50 years old
  bob: 35 years old
  geroge: 26 years old

The avergae age of the family is: 37.00
-------------------------------------------------------------------------------------------------*/


/*------------------------------------------------------------------------------------------------
 	Yong Deng	Assignment: P3: Family

How many people are there in your family? 
2
Enter the name and age of member 1: michael
-50
Age cannot be smaller than 0. Enter the age: 
50
Enter the name and age of member 2: bob
35

The 2 family members are: 
  michael: 50 years old
  bob: 35 years old

The avergae age of the family is: 42.50
-------------------------------------------------------------------------------------------------*/
