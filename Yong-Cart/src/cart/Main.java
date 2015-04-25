/**
 * Main.java
 * Create a simple online store with a shopping cart and items for sale.
 * @author  Yong Deng
 * @version One
 * @since   1/25/2015
 */
package cart;
import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) { 
		try {
            Date now = new Date();
            Northwoods nw = new Northwoods( now );
            nw.sellItems();
        }
		catch( FileNotFoundException e) {
            System.err.println( "Sorry; Come back later.  Our system is down." );
            e.printStackTrace();
        }
    }
}


/*--------------------Sample Ouptut---------------------------------------------------
Northwoods Sweets
Welcome to our shop.

Shopping cart created on Sun Feb 01 22:00:55 EST 2015

0.   Proceed to checkout.
1.   Maple syrup, half gallon      26.00
2.   Maple syrup, quart            15.00
3.   Maple syrup, pint               8.00
4.   Maple sugar, 1 pound          24.00
5.   Maple-leaf candies, 8 oz        8.00
6.   Maple cream spread, 8 oz        8.00

Please enter an item number and a quantity, or 0 to check out: 3 2
  2     Maple syrup, pint               8.00 = $ 16.00


0.   Proceed to checkout.
1.   Maple syrup, half gallon      26.00
2.   Maple syrup, quart            15.00
3.   Maple syrup, pint               8.00
4.   Maple sugar, 1 pound          24.00
5.   Maple-leaf candies, 8 oz        8.00
6.   Maple cream spread, 8 oz        8.00

Please enter an item number and a quantity, or 0 to check out: 6 3
  3     Maple cream spread, 8 oz        8.00 = $ 24.00


0.   Proceed to checkout.
1.   Maple syrup, half gallon      26.00
2.   Maple syrup, quart            15.00
3.   Maple syrup, pint               8.00
4.   Maple sugar, 1 pound          24.00
5.   Maple-leaf candies, 8 oz        8.00
6.   Maple cream spread, 8 oz        8.00

Please enter an item number and a quantity, or 0 to check out: 0

Your cart contains:
  2     Maple syrup, pint               8.00 = $ 16.00
  3     Maple cream spread, 8 oz        8.00 = $ 24.00

Total price = $ 40.00

Thank you for shopping with us.  Come again.
------------------------------------------------------------------------------------*/
 