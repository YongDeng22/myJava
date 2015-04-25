/**
 * MenuLine.java
 * Create a simple online store with a shopping cart and items for sale.
 * @author  Yong Deng
 * @version One
 * @since   1/25/2015
 */

package cart;

public class MenuLine {
    private String description;        // Name of the product.         
    private double  pricePer;          // Price for one item.

    /** This constructor creates item-lines for the menu.  It requires one parameter for each 
     *  data member and simply copies those parameters into the new object.
     *  @param description The name or description of the item being purchased.
     *  @param pricePer The price of 1 copy of this item
     */
    public MenuLine( String description, double pricePer ) {
        this.description = description;
        this.pricePer = pricePer;
     }

    double getPrice() { return pricePer; }
    
    //------------------------------------------------------------------
    /** Format the description and price for printing as part of a menu.
     */
    public String toString(){
        return String.format("%-30s  %5.2f", description, pricePer);
    }
}
