package cs2030.simulator;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

/**
 * Encapsulates shop where it stores all the servers. The Shop class support 
 * the following methods:
 * <ol> 
 * <li>
 * find method that find and return the first server that matches the criteria passed in.
 * </li>
 * <li>
 * findBest method is the same as find but it returns the server with the least queue that 
 * fit the criteria.
 * </li>
 * <li>
 * updateSelfCheckOutQueue method that update the queue for all the self checkout.
 * </li>
 * <li>
 * replace method that replace the server in teh shop with an updated one.
 * </li>
 * </ol>
 */
class Shop {
    /** Stores all the servers inside. */
    private final List<Server> lst;

    /**
     * Constructor for Shop.
     * @param n number of HUMAN server.
     */
    Shop(int n) {
        this.lst = IntStream.rangeClosed(1, n)
                .boxed()
                .map(x ->  new Server(x))
                .collect(Collectors.toList());
    }

    /**
     * Constructor for shop.
     * @param numberOfServers Number of HUMAN server.
     * @param numberOfSelfCheckout Number of SelfCheckout.
     * @param qmax Maximum length of the server.
     * @param randomGenerator To be used in server to generate random time. 
    See {@link Server#Server(ServerType, int, int, double, RandomGenerator)}
     * @param pset Probability of rest.
     */
    Shop(int numberOfServers, int numberOfSelfCheckout, int qmax, 
            RandomGenerator randomGenerator, double pset) {
        this.lst = IntStream.rangeClosed(1, numberOfServers)
                .boxed()
                .map(x ->  new Server(ServerType.HUMAN, x, qmax, pset, randomGenerator))
                .collect(Collectors.toList());


        IntStream.rangeClosed(numberOfServers + 1, numberOfSelfCheckout + numberOfServers)
                .boxed()
                .map(x -> new Server(ServerType.SELFCHECKOUT, x, qmax, pset, randomGenerator))
                .forEach(lst::add);
    }

    /**
     * Constructor for shop.
     * @param lst List of server.
     */
    Shop(List<Server> lst) {
        this.lst = lst;
    }

    /**
     * Finds the first server that matches the criteria passed in.
     * @param f Criterial for the server.
     * @return A server.
     */
    public Optional<Server> find(Function<Server, Boolean> f) {
        return lst.stream()
                .filter(x -> f.apply(x))
                .findFirst();
    }

    /**
     * Filter all the servers to get those that matches the criteria and
     * return the one with the shortest queue.
     * @return A server.
     */
    public Optional<Server> findBest(Function<Server, Boolean> f) {
        return lst.stream()
                .filter(x -> f.apply(x))
                .min((x, y) -> x.getQueue() - y.getQueue());
    }

    /**
     * Updates all the self checkout queue to be the same.
     * @param n New length of the queue
     * @return A shop.
     */
    public Shop updateSelfCheckOutQueue(int n) {
        List<Server> newlst = new ArrayList<>(lst);
        newlst.replaceAll(t -> t.getServerType() == ServerType.SELFCHECKOUT ? t.updateQueue(n) : t);
        return new Shop(newlst);
    }

    /**
     * Updates the server with an updated version of it.
     * @param s An updated server.
     * @return An updated shop.
     */
    public Shop replace(Server s) {
        List<Server> newlst = new ArrayList<>(lst);
        newlst.replaceAll(t -> t.equals(s) ? s : t);
        return new Shop(newlst);
    }

    @Override
    public String toString() {
        String string = "[";
        string += lst.stream()
                .map(Server::toString)
                .reduce("", (s, e) -> s + e + ", ");
        return string.substring(0, string.lastIndexOf(",")) + "]";
    }
}