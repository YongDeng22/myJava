/**
 * Northwoods.java
 * @author  Yong Deng
 * @version One
 * @since   1/25/2015
 */
package cart;
import java.util.*;
import java.io.*;

	// Controller for the online shop.
public class Northwoods {
    // Data structures for the contoller.
    private Scanner sc = new Scanner(System.in);
    private Scanner infile;
    private ArrayList<MenuLine> menu = new ArrayList<MenuLine>(); 
    //--------------------------------------------------------------------------------
    // The model.
    private Cart c;

    /// -------------------------------------------------------------------------------
    /** Construct the application controller. Allocate a cart for purchases.  Contents of 
     *  cart and total purchase price will be displayed upon exit.
     *  @param now The date and time on which this store and shopping cart were created.
     */
    public Northwoods( Date now )  throws FileNotFoundException { 
        double price;
        String desc;
        infile = new Scanner( new File( "northwoodsMenu.txt" ) );
        while ( infile.hasNext() ) {
            try{
                price = infile.nextDouble();
                desc = infile.nextLine();
                menu.add( new MenuLine( desc, price ));
            }
            catch( InputMismatchException e ){  // Handle non-numeric input in price field.
                System.err.println( "Error reading price from menu file on line "+ menu.size() 
                	               +"\nItem omitted from this menu.\n" );
                infile.nextLine();  
            } 
        }
        infile.close();
        System.out.println("Northwoods Sweets\nWelcome to our shop.\n");
        c = new Cart(now);
    }

    /// -------------------------------------------------------------------------------
    /** Display a menu of all items for sale, with prices.
     */
    public void showMenu(){
        System.out.println( "\n0.  " + " Proceed to checkout." );
        for(int k=1; k<menu.size(); ++k){
            MenuLine m = menu.get(k);
            System.out.println( k + ".  " + m );
        }
        System.out.print( "\nPlease enter an item number and a quantity, or 0 to check out: " );
    }

    /// -------------------------------------------------------------------------------
    /** Allow the user to select items from the menu and put them in the shopping cart. Menu 
     *  selections are validated. Selecting 0 ends the transaction and prints out the cart.
     */
    public void sellItems(){
        for(;;){
            showMenu();
            try {
                int choice = sc.nextInt();
                if (choice==0) break;
                if (choice<1 || choice>=menu.size()) {
                    System.out.println("Please select a number on the menu!\n");
                    continue;
                }
                int quantity = sc.nextInt();
                MenuLine selected = menu.get( choice );       // Point at one of the menu items.
                Item temp = new Item( selected, quantity );   // Copy the selection;
                System.out.println( temp );
                c.add(temp);
            }
            catch( InputMismatchException e ){  // Handle non-numeric inputs.
                sc.nextLine();
                System.err.println( "\nError reading menu selection or quantity; please re-enter" );
            } //Now return to the shopping loop.
        }
        // User selected menu item 0: quit shopping and pay now.
        System.out.println( c );
        System.out.println( "Thank you for shopping with us.  Come again." );
    }
}
