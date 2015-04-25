/**
 * Item.java: the item name, price per, and quantity of one purchase.
 * Part of the model for the online shop.
 * @author  Yong Deng
 * @version One
 * @since   1/25/2015
 */

package cart;

//----------------------------------------------------------------------
public class Item {
    private MenuLine product;          // The product being purchased.
    private int  quantity;             // Number of this product being purchased.
    private double priceTotal;         // Price for given quantity of this product.

    ///------------------------------------------------------------------
    /** Construct a sale item by copying a menu item and adding a quantity.
     */
    public Item( MenuLine product, int quantity ) {
        this.product = product;
        this.quantity = quantity;
        priceTotal = product.getPrice() * quantity;
    }
   
    double getPrice() { return priceTotal; }
    
    ///------------------------------------------------------------------
    /** Format the data members of an Item for printing as part of a purchase.  
     */
    public String toString(){
       return String.format("  %-2d   %-35s = $%6.2f\n", quantity, product.toString(), priceTotal);
    }
};