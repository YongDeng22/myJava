/**
 * Cart.java: a collection of Items
 * Part of the model for the online shop.
 * @author  Yong Deng
 * @version One
 * @since   1/25/2015
 */

package cart;
import java.util.*;
//----------------------------------------------------------------------
public class Cart { 
    private float totalPrice = 0;               			// Total price of all products in the cart.
    private ArrayList<Item> load = new ArrayList<Item>();   // List of pointers to products.
    private final Date creation;
    
    public Cart( Date now ){ 
        creation = now;
        System.out.println( "Shopping cart created on "+ creation);
    }
    
    // -------------------------------------------------------------------------------
    /** Place an item in the shopping cart after the previously selected items.     
     *  Update the total price of all items in the cart.
     *  @param purchase is a newly selected item to put into the cart.
     */
    public void add( Item purchase ){     
        load.add( purchase );
        totalPrice += purchase.getPrice();
    }
    
    //------------------------------------------------------------------
    /** Format the contents of the shopping cart for printing.   
     */
    public String toString(){
        StringBuilder sb = new StringBuilder("\nYour cart contains:\n");
        for( Item it : load ) sb.append( it );
        sb.append( String.format( "\nTotal price = $%6.2f\n", totalPrice ));
        return sb.toString();
    }
}
