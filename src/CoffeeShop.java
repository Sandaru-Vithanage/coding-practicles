import Exceptions.QueueCapacityException;
import Exceptions.ShopClosedException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe implementation of a coffee shop ordering system.
 * Manages order queue and coordinates between customers and baristas.
 *
 * @author D.V.S. Rashmika w1867090 20210334
 * @version 1.0
 */
public class CoffeeShop {
    // Queue to store customer orders
    private final Queue<String> orderQueue;
    // Maximum number of orders that can be in the queue
    private final int capacity;
    // Flag to track if shop is open for new orders
    private volatile boolean isOpen;
    /**
     * Initializes a new coffee shop with specified queue capacity.
     *
     * @param capacity Maximum number of orders the queue can hold
     * @throws QueueCapacityException if capacity is zero or negative
     */
    public CoffeeShop(int capacity) throws QueueCapacityException {
        // Validate capacity
        if (capacity <= 0) {
            throw new QueueCapacityException("Queue capacity must be greater than zero");
        }
        this.capacity = capacity;
        this.orderQueue = new LinkedList<>();
        this.isOpen = true;
        System.out.println("Coffee shop initialized with capacity: " + capacity);
    }

    /**
     * Places a customer order in the queue. If queue is full, waits until space is available.
     *
     * @param order The order to be placed
     * @throws InterruptedException if the thread is interrupted while waiting
     * @throws ShopClosedException if the shop is closed
     */
    public synchronized void placeOrder(String order)
            throws InterruptedException, ShopClosedException {
        // Check if shop is open before attempting to place order
        if (!isOpen) {
            throw new ShopClosedException("Cannot place order - shop is closed");
        }

        synchronized (this) {
            // Wait while queue is full and shop is open
            while (orderQueue.size() == capacity && isOpen) {
                System.out.println("Baristas are busy " + order + " waiting to be placed");
                wait(); // Release lock and wait
            }

            // Recheck shop status after waiting
            if (!isOpen) {
                throw new ShopClosedException("Cannot place order - shop closed while waiting");
            }

            // Add order to queue
            orderQueue.offer(order);
            System.out.println(order + " has been placed");
            notifyAll(); // Notify waiting baristas
        }
        // Wait while queue is full and shop is open
        while (orderQueue.size() == capacity && isOpen) {
            System.out.println("Baristas are busy " + order + " waiting to be placed");
            wait(); // Release lock and wait
        }

        // Recheck shop status after waiting
        if (!isOpen) {
            throw new ShopClosedException("Cannot place order - shop closed while waiting");
        }

        // Add order to queue
        orderQueue.offer(order);
        System.out.println(order + " has been placed");
        notifyAll(); // Notify waiting baristas
    }    }

    /**
     * Retrieves and removes the next order from the queue for processing.
     * If queue is empty, waits until an order becomes available.
     *
     * @return The next order to process, or null if shop is closed and queue is empty
     * @throws InterruptedException if the thread is interrupted while waiting
     */

    public  String prepareOrder() throws InterruptedException {
        // Wait while queue is empty and shop is open
        synchronized (this) {
            while (orderQueue.isEmpty() && isOpen) {
                System.out.println("CoffeeShop is not busy " + Thread.currentThread().getName() + " waiting for orders");
                wait(); // Release lock and wait
            }

            // Check if shop is closed and no more orders to process
            if (!isOpen && orderQueue.isEmpty()) {
                System.out.println("Shop closed and no more orders to process");
                return null;
            }

            // Remove and return next order
            String order = orderQueue.poll();
            System.out.println(Thread.currentThread().getName() + " took " + order + " for preparation");
            notifyAll(); // Notify waiting customers
            return order;
        }
    public synchronized String prepareOrder() throws InterruptedException {
        // Wait while queue is empty and shop is open
        while (orderQueue.isEmpty() && isOpen) {
            System.out.println("CoffeeShop is not busy " + Thread.currentThread().getName() + " waiting for orders");
            wait(); // Release lock and wait
        }

        // Check if shop is closed and no more orders to process
        if (!isOpen && orderQueue.isEmpty()) {
            System.out.println("Shop closed and no more orders to process");
            return null;
        }

        // Remove and return next order
        String order = orderQueue.poll();
        System.out.println(Thread.currentThread().getName() + " took " + order + " for preparation");
        notifyAll(); // Notify waiting customers
        return order;

    }

    /**
     * Closes the shop for new orders but allows processing of existing orders.
     * Notifies all waiting threads about the closure.
     */
    public synchronized void closeShop() {
        isOpen = false;
        System.out.println("Coffee shop is now closed for new orders");
        notifyAll(); // Wake up all waiting threads
    }

    /**
     * Checks if the shop is currently accepting new orders.
     *
     * @return true if shop is open, false otherwise
     */
    public boolean isOpen() {
        return isOpen;
    }
}
