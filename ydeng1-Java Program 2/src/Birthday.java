/** Birthday.java: a Java program to calculate the day of a week and a year of a specific date. 
    @author Yong Deng 
    @since 1/16/2015 
 */ 

import java.util.*;

//------------------------------------------------------------------------------
public class Birthday{
    public static final String[] months = {"Jan", "Feb", "Mar", "Apr", "May",
    "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    public static final String[] days = { "Sunday", "Monday", "Tuesday",
    "Wednesday", "Thursday", "Friday", "Saturday" };
    public static final int[] startsOn = {4, 0, 0, 3, 5, 1, 3, 6, 2, 4, 0, 2, };
    /*Added a public static final array of integers for the number of days in each month.*/
    public static final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    // The collection of data members stores the STATE of an object.
    // Data members of a class are normally private.   Document each one.
    private String month;   // 3-letter abbreviation for the month.
    private int date;       // Will be 1..31
    private String day;     // The day of the week.
    private int numberOfTheMonth;	/*-Added variable**The number of the month, will be 1..12-*/
    private int dayOfTheYear; 		/*-Added variable**The day of the year, will be 1..365-*/

    //--------------------------------------------------------------------------
    // Compute a new random value for the die.
    // Postcondition: the return value is between 1 and faces.
    Birthday( String m, int d){
        month = m;
        date = d;
        calculateDay();
        calculateDayOfYear();	// **Code added here**
    }

    //--------------------------------------------------------------------------
    private void calculateDay() {
        int found, k, answer;
        for(k=0; k<12; ++k) {
            if (months[k].equals(month))  break;
        }
        found = k;
        numberOfTheMonth = k;	// ****Code added here****numberOfTheMonth is initialized
        if (found == 12)
            System.out.println("Your month name was not a valid 3-letter abbreviation.");
        else if (date > daysOfMonth[numberOfTheMonth] || date <= 0);	//code added here
        else {
            answer = (startsOn[k] + date -1)%7;
            day = days[answer];
        }
    }
    
    //--------------------------------------------------------------------------
    // ****Added function****A function to calculate the day of the year
    public void calculateDayOfYear() {
        //-----Check if the date is valid. If not, print error message----------
    	if (numberOfTheMonth == 12);
    	else if (date > daysOfMonth[numberOfTheMonth] || date <= 0)
        	System.out.println("Your date is not valid.");
        else{
            for (int m=0; m<numberOfTheMonth; m++) {
            	dayOfTheYear += daysOfMonth[m];
            }
            dayOfTheYear += date;
        }
    }

    //---------- A get function gives read-only access to a private data member.
    public String getDay() {  return day;  }
    
    // ***Added get function*** A get function gives the day of the year.-------
    public int getDayOfYear() {  return dayOfTheYear;  }
    
    //--------------------------------------------------------------------------
    // Define toString for every class.
    // Return a string that reports the state of the class. Used for debugging.
    public String toString(){
        return month +" " + date ;
    }

    //--------------------------------------------------------------------------
    public static  void  main( String[] args ) {
        int date;
        String monthname;
        Scanner sc = new Scanner( System.in );

        System.out.println("\nBirthday Day Calculator, Welcome!");
        System.out.print ("Months are: ");
        for( String s : months) System.out.print( s+"  " );

        System.out.println("\n\nPlease enter your birth month and date:");
        monthname = sc.next();
        date = sc.nextInt();
        Birthday b = new Birthday (monthname, date);
        //---------****Modified code here**** Print day of the year-------------
        System.out.printf ( "Your %s birthday is on %s this year\n"
        					+ "\tit is day %d of the year\n",
                           b.toString(), b.getDay(), b.getDayOfYear() );
    }
}