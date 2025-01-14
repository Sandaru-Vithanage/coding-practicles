import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The FloorBathroom class simulates the usage of a shared bathroom with a limited number of stalls.
 * Multiple employees and students (users) try to access the bathroom concurrently.
 *
 * @author D.V.S. Rashmika w1867090 20210334
 * @version 1.0
 */
public class FloorBathroom {
    // Total number of bathroom stalls
    private static final int BATHROOM_STALLS = 6;
    // Total number of users (employees and students)
    private static final int NUM_EMPLOYEES = 100;
    // Semaphore to control stall access with fair ordering
    private static final Semaphore stalls = new Semaphore(BATHROOM_STALLS, true);

    /**
     * The User class represents a thread that simulates a user (either an employee or a student) trying to use a stall.
     */
    static class User extends Thread {
        // Type of user (Employee or Student)
        private final String userType;
        // Unique ID of the user
        private final int userId;

        /**
         * Constructor to create a new User instance.
         *
         * @param userType Type of user (Employee or Student)
         * @param userId Unique ID of the user
         */
        public User(String userType, int userId) {
            this.userType = userType;
            this.userId = userId;
        }

        /**
         * The run method simulates the user's behavior of waiting for a stall, using it, and then leaving.
         */
        @Override
        public void run() {
            try {
                // Try to acquire a stall (blocking call if no stall is available)
                System.out.println(userType + " " + userId + " is waiting to use a stall...");
                // Acquire a permit (stall) from the semaphore
                stalls.acquire();
                System.out.println(userType + " " + userId + " is using a stall.");

                // Simulate time taken to use the stall (random between 1 to 3 seconds)
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));

                // Release the stall after use
                System.out.println(userType + " " + userId + " has left the stall.");
                // Release the permit (stall) back to the semaphore
                stalls.release();
            } catch (InterruptedException e) {
                // Handle interruption and restore the interrupted status
                Thread.currentThread().interrupt();
                System.out.println(userType + " " + userId + " was interrupted.");
            }
        }
    }

    /**
     * Main method to run the bathroom stall simulation.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Array to hold user threads
        Thread[] users = new Thread[NUM_EMPLOYEES];

        // Create 100 users (alternating between Employees and Students)
        for (int i = 0; i < NUM_EMPLOYEES; i++) {
            // Alternate between Employee and Student
            String userType = (i % 2 == 0) ? "Employee" : "Student";
            // Create a new User thread
            users[i] = new User(userType, i + 1);
        }

        // Start all user threads
        for (Thread user : users) {
            user.start();
        }

        // Join all user threads to ensure they complete before finishing the main method
        for (Thread user : users) {
            try {
                // Wait for each thread to finish
                user.join();
            } catch (InterruptedException e) {
                // Handle interruption and restore the interrupted status
                Thread.currentThread().interrupt();
                System.out.println("Main thread was interrupted.");
            }
        }

        // Print final message indicating the end of the simulation
        System.out.println("Bathroom stall simulation finished.");
    }
}