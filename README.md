# Discrete-Event-Simulator

## Part 1
### Problem Description
A discrete event simulator is a software that simulates a system with events and states, and can be used to study many complex real-world systems such as queuing to order food at McDonald's®. An event occurs at a particular time, and each event alters the state of the system and may generate more events. States remain unchanged between two events (hence the term discrete), and this allows the simulator to jump from the time of one event to another. A simulator typically also keeps track of some statistics to measure the performance of the system.

Let us simulate a queuing system comprising only one single customer queue and single service point S.

The system comprises the following:
* There is one server (a person providing service to the customer); the server can serve one customer at a time.
* The service time (time taken to serve a customer) is constant.
* When a customer arrives (ARRIVE event):
	* if the server is idle (not serving any customer), then the server starts serving the customer immediately (SERVE event).
 	* if the server is serving another customer, then the customer that just arrived waits in the queue (WAIT event).
        * if the server is serving one customer, and another customer is waiting in the queue, and a third customer arrives, then the third customer leaves (LEAVE event). In other words, there is at most one customer waiting in the queue.
        * When the server is done serving a customer (DONE event), the server can start serving the customer waiting at the front of the queue (if any).
* If there is no waiting customer, then the server becomes idle again.
           
There are five events in the system, namely ARRIVE, SERVE, WAIT, LEAVE and DONE. For each customer, these are the only possible event transitions:
* ARRIVE → SERVE → DONE
* ARRIVE → WAIT → SERVE → DONE
* ARRIVE → LEAVE
           
Statistics of the system that we need to keep track of are:
* the average waiting time for customers who have been served
* the number of customers served 
* the number of customers who left without being served

As an example, given the arrival times (represented as a floating point value for simplicity) of three customers with one server, and assuming a constant service time of 1.0,
```
0.500
0.600
0.700
```
the following events, each of the form <time_event_occurred, customer_id, event_details>, are produced
```
0.500 1 arrives
0.500 1 served by 1
0.600 2 arrives
0.600 2 waits to be served by 1
0.700 3 arrives
0.700 3 leaves
1.500 1 done serving by 1
1.500 2 served by 1
2.500 2 done serving by 1
```
with the end-of-simulation statistics being respectively, [0.450 2 1].

### Priority Queuing
The PriorityQueue (a mutable class) can be used to keep a collection of elements, where each element is given a certain priority.
* Elements may be added to the queue with the add(E e) method. The queue is modified;
* The poll() method may be used to retrieve and remove the element with the highest priority from the queue. It returns an object of type E, or null if the queue is empty. The queue is modified.

To enable PriorityQueue to order events, instantiate a PriorityQueue object using the constructor that takes in a Comparator object. For more details, refer to the Java API Specifications.

## Part 2
### Problem Description
This stage of the project comprises three parts:
* Dealing with side-effects
* Randomizing arrival and service times
* More complex behavior of shops, servers, and customers

###Dealing with Side-Effects
For this part of the project, we shall first tackle the issue of side effects in Project #1. You would probably have coded something similar to the following, when processing the different Events (arrive, serve, wait, leave, done, ...) in the PriorityQueue pq.
```
while (!pq.isEmpty()) {
        Event e;
            e = pq.poll();
                System.out.println(e);
                    Event nextEvent = e.execute();
                        if (nextEvent.isValidEvent()) {
                                    pq.add(nextEvent);
                                        } 
}
```

Notice that every time the execute method of an event is invoked, it not only generates the next event base on the status of the Servers (here we call it a Shop), it will actually change the status of the servers as a  side-effect. As such, we would like to address this side-effect by re-defining event generation as follows:
```
e.execute(s) -> (e',s') 
```
That is to say, the invocation of the execute method of an Event e with the argument Shop s, would now generate the next Event e' and Shop s' as a pair of values. In a similar fashion, e'and s' can then be used to generate the next pair of values.

### Randomized Arrival and Service Time
Rather than fixed arrival and service times, we will generate random times. A random number generator is an entity that generates one random number after another. Since it is not possible to generate a truly random number algorithmically, pseudo random number generation is adopted instead. A pseudo-random number generator can be initialized with a seed, such that the same seed always produces the same sequence of (seemingly random) numbers.

Although, Java provides a class java.util.Random, an alternative RandomGenerator class that is more suitable for discrete event simulation is provided for you that encapsulates different random number generators for use in our simulator. Each random number generator generates a different stream of random numbers. The constructor for RandomGenerator takes in three parameters:
* int seed is the base seed. Each random number generator uses its own seed that is derived from this base seed;
* double lambda is the arrival rate, λ;
* double mu is the service rate, μ.
    
The inter-arrival time is usually modeled as an exponential random variable, characterized by a single parameter λ denoting the arrival rate. The genInterArrivalTime() method of the class RandomGenerator is used for this purpose. Specifically,
* start the simulation by generating the first customer arrival event with timestamp 0
* if there are still more customers to simulate, generate the next arrival event with a timestamp of T + now, where T is generated with the method genInterArrivalTime();

The service time is modeled as an exponential random variable, characterized by a single parameter, service rate μ. The method genServiceTime() from the class RandomGenerator can be used to generate the service time. Specifically,
* each time a customer is being served, a DONE event is generated and scheduled;
* the DONE event generated will have a timestamp of T + now, where T is generated with the method genServiceTime().
        

### More Complex Behaviour of Shops, Serves and Customers
You will extend the simulator to model the following entities.
* FIFO (first-in-first-out) queues for customers with a given maximum capacity
* Two new events
	* SERVER_REST that simulates a server taking a rest, and
        * SERVER_BACK that simulates a server returning back from rest
* Two types of servers,
        * human servers who may rest after serving a customer, and
        * self-checkout counters that never rest
* Two types of customers
        * typical customers that joins the first queue (scanning from server 1 onwards) that is still not full,
        * greedy customers that joins the queue with the fewest waiting customers

### Customer Queues
Each human server now has a queue of customers to allow multiple customers to queue up. A customer that chooses to join a queue joins at the tail. When a server is done serving a customer, it serves the next waiting customer at the head of the queue. Hence, the queue should be a first-in-first-out (FIFO) structure. The self-checkout counters, however, have only a single shared queue.

### Taking a Rest
The human servers are allowed to take occasional breaks. When a server finishes serving a customer, there is a probability Pr that the server takes a rest for a random amount of time Tr. During the break, the server does not serve the next waiting customer. Upon returning from the break, the server serves the next customer in the queue immediately.

To implement this behavior, introduce two new events, SERVER_REST and SERVER_BACK, to simulate taking a break, and returning. These events should be generated and scheduled in the simulator when the server decides to rest.

### Self-Checkout
To reduce waiting time, self-checkout counters have been set-up. These self-checkout counters never need to rest. Customers queue up for the self-checkout counters in the same way as human servers. There is one shared queue for all self-checkout counters.

### Customers' Choice of Queue
As before, when a customer arrives, he or she first scans through the servers (in order, from 1 to k) to see if there is an idle server (i.e. not serving a customer and not resting). If there is one, the customer will go to the server to be served. Otherwise, a typical customer just chooses the first queue (while scanning from servers 1 to k) that is still not full to join. However, other than the typical customer, a greedy customer is introduced that always joins the queue with the fewest customers. In the case of a tie, the customer breaks the tie by choosing the first one while scanning from servers 1 to k.

If a customer cannot find any queue to join, he/she will leave the shop. 

All classes dealing with the simulation should now reside in the cs2030.simulator package, with the Main class outside the package, but importing the necessary classes from the package.
