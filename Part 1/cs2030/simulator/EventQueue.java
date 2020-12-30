package cs2030.simulator;

import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;

/** 
 * Creates the simulation and manages all the events for each Customer.
 * Links classes out of the package with classes in the package to run
 * the simulation.
 * 
 * @author Marcus Peh
 * @version 1.0
 * @since 2020-09-22
 */

public class EventQueue {
    /**
     * <p> 
     * Creates the simulation and manages all the events for each Customer. Stats 
     * for the simulation will also be printed out at the end.
     * </p>
     * <p>
     * numOfServer number of servers will be created and list&lt;Server&gt; will be created 
     * to store all the Server. Event will also be created for each customer. 
     * ServerList will be passed into each event, and they are pointing to the same 
     * List&lt;Server&dt;.
     * </p>
     * <p>
     * PriorityQueue will be created and it will be based on TimeComparator class.
     * Event for each customer until its status is done or leave will be stored in 
     * the PriorityQueue. The event with the highest priority based on TimeComparator 
     * will be removed from the list, printed out as the output for that simulation 
     * and execute() {@link Event#execute()}. The new event will be added back into the 
     * list unless it's status is LEAVE or DONE.This carries on until there is no more 
     * event in the queue.
     * </p>
     * <p>Stats will be printed before the simulation end. Stats that will be printed are:
     * <ol>
     * <li>the average waiting time for customers who have been served.</li>
     * <li>the number of customers served.</li>
     * <li>the number of customers who left without being served.</li>
     * </ol> 
     * </p>
     * 
     * @param numOfServer Refers to the number of Server in this simulation
     * @param customerArrival Contains all the arrival time for each customer
     */
    public static void run(int numOfServer, List<Double> customerArrival) {     
        double waitTime = 0.0;
        int served = 0;
        int left = 0;

        List<Server> serverList = new ArrayList<>();

        for (int i = 1; i <= numOfServer; i++) {
            Server newServer = new Server(i, true, false, 0);
            serverList.add(newServer);
        }
        
        PriorityQueue<Event> eventQueue = new PriorityQueue<>(customerArrival.size(), 
                new TimeComparator());

        for (int i = 1; i <= customerArrival.size(); i++) {
            Customer newCustomer = new Customer(i, customerArrival.get(i - 1));
            Event newEvent = new ArriveEvent(newCustomer, serverList);
            eventQueue.add(newEvent);
        }

        while (!eventQueue.isEmpty()) {
            Event event = eventQueue.poll();
            Status status = event.getStatus();
            System.out.println(event);
            event = event.execute();
            switch (status) {
                case ARRIVE:
                case WAIT:
                case SERVE:
                    eventQueue.add(event);
                    if (event.getStatus() == Status.SERVE) {
                        waitTime += event.getCustomer().getWaitTime(event.getTime());
                    } else if (event.getStatus() == Status.DONE) {
                        served++;
                    } else if (event.getStatus() == Status.LEAVE) {
                        left++;
                    } 
                    break;
                case DONE:
                case LEAVE:
                default:
                    continue;
                
            }
        }

        System.out.printf("[%.3f %d %d]\n", waitTime / served, served, left);
    }
}