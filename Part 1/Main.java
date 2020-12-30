import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import cs2030.simulator.EventQueue;

/** 
 * Main file for working operations.
 *
 * @author Marcus Peh
 * @version 1.0
 * @since 2020-09-22
 */

class Main {
    /**
     * Calls the EventQueue class to run a simulation. Reads the input
     * using a Scanner and store the first int (number of server) as a Integer and 
     * the doubles following that (customers arrival time) as a List&lt;Double&dt;.
     * Calls EventQueue using the data stored from Scanner to run the simulation
     * @param args For generating simulation run
     */
    public static void  main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numOfServers = sc.nextInt();
        
        List<Double> customerArrival = new ArrayList<Double>();


        while (sc.hasNextDouble()) {
            double arrivalTime = sc.nextDouble();
            customerArrival.add(arrivalTime);
        }

        sc.close();

        EventQueue.run(numOfServers, customerArrival);
    }
}
