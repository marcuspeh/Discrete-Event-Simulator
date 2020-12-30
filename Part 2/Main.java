import cs2030.simulator.EventQueue;

/** 
 * Main file for working operations.
 *
 * @author Marcus Peh
 * @version 2.0
 * @since 2020-11-11
 */

class Main {
    /**
     * Calls the EventQueue class to run a simulation. Gets the data needed to call 
     * EventQueue via args. Different length of args is catered to different level.
     * @param args For generating simulation run
     */
    public static void  main(String[] args) {
        //lvl3
        int seed; 
        int numberOfServers;
        int numberOfSelfCheckout;
        int qmax;
        int numberOfCustomer;
        double arrivalRate;
        double serviceRate;
        double restingRate;
        double prest;
        double pgreedy;
        if (args.length == 5) {
            seed = Integer.parseInt(args[0]);
            numberOfServers = Integer.parseInt(args[1]);
            numberOfSelfCheckout = 0;
            qmax = 1;
            numberOfCustomer = Integer.parseInt(args[2]);
            arrivalRate = Double.parseDouble(args[3]);
            serviceRate = Double.parseDouble(args[4]);
            restingRate = 0;
            prest = 0;
            pgreedy = 0;
        } else if (args.length == 6) {
            seed = Integer.parseInt(args[0]);
            numberOfServers = Integer.parseInt(args[1]);
            numberOfSelfCheckout = 0;
            qmax = Integer.parseInt(args[2]);
            numberOfCustomer = Integer.parseInt(args[3]);
            arrivalRate = Double.parseDouble(args[4]);
            serviceRate = Double.parseDouble(args[5]);
            restingRate = 0;
            prest = 0;
            pgreedy = 0;
        } else if (args.length == 8) {
            seed = Integer.parseInt(args[0]);
            numberOfServers = Integer.parseInt(args[1]);
            numberOfSelfCheckout = 0;
            qmax = Integer.parseInt(args[2]);
            numberOfCustomer = Integer.parseInt(args[3]);
            arrivalRate = Double.parseDouble(args[4]);
            serviceRate = Double.parseDouble(args[5]);
            restingRate = Double.parseDouble(args[6]);
            prest = Double.parseDouble(args[7]);
            pgreedy = 0;
        } else if (args.length == 9) {
            seed = Integer.parseInt(args[0]);
            numberOfServers = Integer.parseInt(args[1]);
            numberOfSelfCheckout = Integer.parseInt(args[2]);
            qmax = Integer.parseInt(args[3]);
            numberOfCustomer = Integer.parseInt(args[4]);
            arrivalRate = Double.parseDouble(args[5]);
            serviceRate = Double.parseDouble(args[6]);
            restingRate = Double.parseDouble(args[7]);
            prest = Double.parseDouble(args[8]);
            pgreedy = 0;
        } else {
            seed = Integer.parseInt(args[0]);
            numberOfServers = Integer.parseInt(args[1]);
            numberOfSelfCheckout = Integer.parseInt(args[2]);
            qmax = Integer.parseInt(args[3]);
            numberOfCustomer = Integer.parseInt(args[4]);
            arrivalRate = Double.parseDouble(args[5]);
            serviceRate = Double.parseDouble(args[6]);
            restingRate = Double.parseDouble(args[7]);
            prest = Double.parseDouble(args[8]);
            pgreedy = Double.parseDouble(args[9]);
        }

        EventQueue eq = new EventQueue();
        eq.run(seed, numberOfServers, numberOfSelfCheckout, qmax, 
                numberOfCustomer, arrivalRate, serviceRate, restingRate, 
                prest, pgreedy);
    }
}
