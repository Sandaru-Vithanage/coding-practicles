import Exceptions.InsufficientFundsException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Represents a bank account with thread-safe operations.
 * Supports deposit, withdrawal, and balance inquiry.
 *
 * @author D.V.S. Rashmika w1867090 20210334
 * @version 1.0
 */
public class BankAccount {

    // Unique identifier for the bank account
    private final int id;
    // Current balance of the bank account
    private BigDecimal balance;
    // Lock for managing concurrent access to the account
    private final ReentrantReadWriteLock rwLock;

    /**
     * Creates a bank account with a given ID and initial balance.
     *
     * @param id Unique identifier for the account
     * @param initialBalance Initial balance of the account
     * @throws InsufficientFundsException if initial balance is negative
     */
    public BankAccount(int id, BigDecimal initialBalance) throws InsufficientFundsException {
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException(
                    "Initial balance cannot be negative. Provided: " + initialBalance);
        }
        this.id = id;
        this.balance = initialBalance.setScale(2, RoundingMode.HALF_UP);
        this.rwLock = new ReentrantReadWriteLock(true);

        System.out.println("Created BankAccount(id=" + id + ", initialBalance=" + this.balance + ")");
    }

    /**
     * Overloaded constructor to handle double initial balance.
     *
     * @param id Unique identifier for the account
     * @param initialBalance Initial balance of the account
     * @throws InsufficientFundsException if initial balance is negative
     */
    public BankAccount(int id, double initialBalance) throws InsufficientFundsException {
        this(id, BigDecimal.valueOf(initialBalance));
    }

    /**
     * Returns the unique identifier of the account.
     *
     * @return Account ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the current balance of the account.
     * Acquires a read lock to ensure thread safety.
     *
     * @return Current balance
     */
    public BigDecimal getBalance() {
        rwLock.readLock().lock();
        try {
            return balance;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    /**
     * Deposits a specified amount into the account.
     * Acquires a write lock to ensure thread safety.
     *
     * @param amount Amount to deposit
     * @throws InsufficientFundsException if the deposit amount is <= 0
     */
    public void deposit(BigDecimal amount) throws InsufficientFundsException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientFundsException(
                    "Deposit amount must be positive. Provided: " + amount);
        }
        rwLock.writeLock().lock();
        try {
            this.balance = this.balance.add(amount);
            System.out.println("Account " + id + ": Deposited " + amount + ", new balance=" + balance);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    /**
     * Overloaded deposit method to handle double amount.
     *
     * @param amount Amount to deposit
     * @throws InsufficientFundsException if the deposit amount is <= 0
     */
    public void deposit(double amount) throws InsufficientFundsException {
        deposit(BigDecimal.valueOf(amount));
    }

    /**
     * Withdraws a specified amount from the account.
     * Acquires a write lock to ensure thread safety.
     *
     * @param amount Amount to withdraw
     * @throws InsufficientFundsException if the withdrawal amount is <= 0 or exceeds the balance
     */
    public void withdraw(BigDecimal amount) throws InsufficientFundsException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientFundsException(
                    "Withdraw amount must be positive. Provided: " + amount);
        }
        rwLock.writeLock().lock();
        try {
            if (this.balance.compareTo(amount) < 0) {
                throw new InsufficientFundsException(
                        "Account " + id + " has insufficient funds (needs " + amount + ", has " + balance + ")");
            }
            this.balance = this.balance.subtract(amount);
            System.out.println("Account " + id + ": Withdrew " + amount + ", new balance=" + balance);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    /**
     * Overloaded withdraw method to handle double amount.
     *
     * @param amount Amount to withdraw
     * @throws InsufficientFundsException if the withdrawal amount is <= 0 or exceeds the balance
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        withdraw(BigDecimal.valueOf(amount));
    }

    /**
     * Acquires a write lock for the account.
     */
    public void writeLock() {
        rwLock.writeLock().lock();
    }

    /**
     * Releases the write lock for the account.
     */
    public void writeUnlock() {
        rwLock.writeLock().unlock();
    }
}