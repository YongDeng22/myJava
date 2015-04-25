/**
 * Write a method convertToText that takes int number as parameter and returns the text of that number
 * -For example, if 963 is passed, convertToText should return "Nine Hundred Sixty Three". See Word Document for more details.
 * -You can create additional methods if needed
 * 
 * @author Yong Deng	3/11/2015
 * 
 */

import java.util.*;

public class intToText {
	public static final String[] belowTwenty = {	//Text for numbers below twenty
		"", "ONE", "TWO", "THREE", "FOUR",
		"FIVE",	"SIX", "SEVEN", "EIGHT", "NINE", 
		"TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", 
		"FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN"
	};
	
    public static final String[] tens = {			//Text for tens
        "",        // 0
        "",        // 10
        "TWENTY",  // 20
        "THIRTY",  // 30
        "FORTY",   // 40
        "FIFTY",   // 50
        "SIXTY",   // 60
        "SEVENTY", // 70
        "EIGHTY",  // 80
        "NINETY"   // 90
    };
    
	/**
	 * main function. Prompt the user to enter an integer and call convertToText function to convert.
	 */
    public static void main(String[] args) {
		//int number = 456;
    	Scanner sc = new Scanner(System.in);
    	int number = 0;		//The number to convert. 
 		
    	System.out.println("\nEnter an integer: ");
    	try {
    		number = sc.nextInt();
     	}
     	catch (InputMismatchException ime) {
     		//If the user enters non-integers, print error message and prompt the user to input an integer again
     		System.out.println("\nInput ERROR! Enter an integer between -2147483648 and 2147483647: ");
     		sc.nextLine();
     		number = sc.nextInt();
     	}
    	catch (Exception e) {
    		//catch any other exceptions
    		e.printStackTrace();
    	}
     	finally {
     		sc.close();
     	}

    	String strNumber = convertToText(number);
		System.out.println("Integer: " + number + "\nText: " + strNumber);
	}
    
	/**
	 * Overview
	 * @param int number
	 * @return String
	 */
	public static String convertToText(int number){
		int absNumber;									//absolute value of number
		String numberText;								//text of number
		
		if (number == 0) return "ZERO";					//if input number is 0
		else if (number < 0) absNumber = 0 - number;	//if input number is negative, negate it
		else absNumber = number;
		
		//convert number to text using recursion
		if (absNumber < 20) {
			numberText = belowTwenty[absNumber];
		}
		else if (absNumber < 100) {
			numberText = tens[absNumber / 10] + " " + belowTwenty[absNumber % 10];
		}
		else if (absNumber < 1000) {
			numberText = belowTwenty[absNumber / 100] + " HUNDRED " + convertToText(absNumber % 100);
		}
		else if (absNumber < 1000000) {
			numberText = convertToText(absNumber / 1000) + " THOUSAND " + convertToText(absNumber % 1000);
		}
		else if (number < 1000000000) {
			numberText = convertToText(absNumber / 1000000) + " MILLION " + convertToText(absNumber % 1000000);
		}
		else {
			numberText = convertToText(absNumber / 1000000000) + " BILLION " + convertToText(absNumber % 1000000);
		}
		
		if (number < 0) return "NEGATIVE " + numberText;	//return text of negative number
		else return numberText;
	}
}

/*-----------------------------------SAMPLE OUTPUTS---------------------------------------------
 Enter an integer: 
456
Integer: 456
Text: FOUR HUNDRED FIFTY SIX

Enter an integer: 
963
Integer: 963
Text: NINE HUNDRED SIXTY THREE

Enter an integer: 
-456
Integer: -456
Text: NEGATIVE FOUR HUNDRED FIFTY SIX

Enter an integer: 
ABCDEFG

Input ERROR! Enter an integer between -2147483648 and 2147483647: 
9999
Integer: 9999
Text: NINE THOUSAND NINE HUNDRED NINETY NINE
 */