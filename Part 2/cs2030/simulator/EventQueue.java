package cs2030.simulator;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** 
 * Creates the simulation and manages all the events for each Customer.
 * Links classes out of the package with classes in the package to run
 * the simulation.
 * 
 * @author Marcus Peh
 * @version 2.0
 * @since 2020-11-11
 */
public class EventQueue {
    /**
     * Run the main program...
     * 
     * @param seed Seed for random generator
     * @param numberOfServers Number of servers
     * @param numberOfSelfCheckout Number of self-checkout counters
     * @param qmax Maximum length of queue
     * @param numberOfCustomer Number of customer
     * @param arrivalRate Arrival rate for random generator
     * @param serviceRate Service rate for random generator
     * @param restingRate Resting rate for random generator
     * @param prest Probability the human server rest
     * @param pgreedy Probability of a greedy customer
     */
    public void run(int seed, int numberOfServers, int numberOfSelfCheckout,
            int qmax, int numberOfCustomer, double arrivalRate, double serviceRate, 
            double restingRate, double prest, double pgreedy) {     
        double waitTime = 0.0;
        int served = 0;
        int left = 0;
        double customerTime = 0.0;
        PriorityQueue<Event> eventQueue = new PriorityQueue<>(numberOfCustomer, 
                new TimeComparator());
        RandomGenerator randomGenerator = new RandomGenerator(seed, arrivalRate, 
                serviceRate, restingRate);
        Shop shop = new Shop(numberOfServers, numberOfSelfCheckout, qmax, randomGenerator, prest);
    
        for (int i = 1; i <= numberOfCustomer; i++) {
            Customer newCustomer;
            if (randomGenerator.genCustomerType() >= pgreedy) {
                newCustomer = new Customer(i, customerTime, CustomerType.NORMAL);
            } else {
                newCustomer = new Customer(i, customerTime, CustomerType.GREEDY);
            }
            customerTime += randomGenerator.genInterArrivalTime();
            Event newEvent = new ArriveEvent(newCustomer);
            eventQueue.add(newEvent);
        }

        while (!eventQueue.isEmpty()) {
            Event event = eventQueue.poll();
            Status status = event.getStatus();
            String printString = event.toString();
            Pair<Shop, Event> pair = event.execute(shop);

            if (pair.first() == null) {
                eventQueue.add(pair.second());
                waitTime += pair.second().getTime() - event.getTime();
            } else {
                if (event.getServerType() == ServerType.HUMAN) {
                    //human
                    shop = pair.first();
                    event = pair.second();
                    switch (status) {
                        case ARRIVE:
                        case WAIT:
                        case SERVE:
                            System.out.println(printString);
                            Optional<Server> s = shop.find(x -> x.getIdentifier() == 
                                    pair.second().getServerNumber());
                            if (s.isPresent()) {
                                if (s.get().getServerType() == ServerType.SELFCHECKOUT) {
                                    //update selfcheckout queue
                                    int queueLength = s.get().getQueue();
                                    shop = shop.updateSelfCheckOutQueue(queueLength);
                                    eventQueue.add(event);
                                } else {
                                    eventQueue.add(event);
                                }
                            } else {
                                eventQueue.add(event);
                            }
                            //stats
                            if (event.getStatus() == Status.SERVE) {
                                waitTime += event.getCustomer()
                                        .getWaitTime(event.getTime());
                            } else if (event.getStatus() == Status.DONE) {
                                served++;
                            } else if (event.getStatus() == Status.LEAVE) {
                                left++;
                            } 
                            break;
                        case DONE:
                            if (event.getStatus() == Status.REST) {
                                waitTime += pair.second().getTime() - event.getTime();
                                eventQueue.add(event);
                            }
                            System.out.println(printString);
                            break;
                        case LEAVE:
                            System.out.println(printString);
                            break;
                        case REST:
                        default:
                            continue;
                    }
                } else {
                    //self checkout
                    shop = pair.first();
                    event = pair.second();
                    int serverNumber = event.getServerNumber();
                    Optional<Server> justServed = shop.find(x -> serverNumber == x.getIdentifier());
                    System.out.println(printString);
                    switch (status) {
                        case ARRIVE:
                        case WAIT:
                        case SERVE:
                            eventQueue.add(event);
                            if (event.getStatus() == Status.SERVE) {
                                waitTime += event.getCustomer()
                                        .getWaitTime(event.getTime());
                            } else if (event.getStatus() == Status.DONE) {
                                served++;
                            } else if (event.getStatus() == Status.LEAVE) {
                                left++;
                            } 
                            
                            if (justServed.isPresent()) {
                                //updates the queue for selfcheckout to make it same
                                if (justServed.get().getServerType() == ServerType.SELFCHECKOUT) {
                                    int queueLength = justServed.get().getQueue();
                                    shop = shop.updateSelfCheckOutQueue(queueLength);
                                }
                            }
                            break;
                        case DONE:
                            List<Event> temp = new ArrayList<>();
                            while (!eventQueue.isEmpty()) {
                                Event nextSelfCheckout = eventQueue.poll();
                                if (nextSelfCheckout.getStatus() == Status.SERVE && 
                                        nextSelfCheckout.getServerType() == 
                                        ServerType.SELFCHECKOUT) {
                                    Server newServer = shop.find(x -> x.getIdentifier() == 
                                            pair.second().getServerNumber()).get();
                                    Event updatedEvent =  new ServeEvent(
                                            nextSelfCheckout.getCustomer(), 
                                            newServer.getNextAvailableTime(), 
                                            newServer.getIdentifier(), 
                                            nextSelfCheckout.getServerType());
                                    eventQueue.add(updatedEvent);
                                    waitTime += updatedEvent.getTime() - nextSelfCheckout.getTime();
                                    break;
                                } else {
                                    temp.add(nextSelfCheckout);
                                }
                            }
                            for (Event e: temp) {
                                eventQueue.add(e);
                            }
                            break;
                        case LEAVE:
                        case REST:
                        default:
                            continue;
                    }
                }
            }

            
        }

        System.out.printf("[%.3f %d %d]\n", served == 0 ? 0 :  waitTime / served, served, left);
    }
}