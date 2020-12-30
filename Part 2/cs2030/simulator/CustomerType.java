package cs2030.simulator;

/** 
 * Type of customer. 
 * <blockquote>
 * NORMAL - Customers that joins the first queue (scanning from server 1 onwards) 
 * that is still not full.
 * </blockquote>
 * <blockquote> 
 * GREEDY - Customers that joins the queue with the fewest waiting customers.
 * </blockquote>
 * 
 * @author Marcus Peh
 * @version 2.0
 * @since 2020-11-11
 */

enum CustomerType {
    NORMAL, GREEDY;
}
