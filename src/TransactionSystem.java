import Exceptions.AccountNotFoundException;
import Exceptions.InsufficientFundsException;
import Exceptions.InvalidInputException;
import Exceptions.InvalidTransactionException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages transactions between bank accounts in a thread-safe manner.
 * Supports transferring and reversing transactions.
 * Uses a concurrent map to store and manage bank accounts.
 *
 * @author D.V.S. Rashmika w1867090 20210334
 * @version 1.0
 */
public class TransactionSystem {

    // Map to store bank accounts by their ID
    private final Map<Integer, BankAccount> accounts;

    /**
     * Initializes the transaction system with a list of bank accounts.
     *
     * @param accountList List of bank accounts to manage
     */
    public TransactionSystem(List<BankAccount> accountList) {
        this.accounts = new ConcurrentHashMap<>();
        for (BankAccount acc : accountList) {
            this.accounts.put(acc.getId(), acc);
        }
    }

    /**
     * Transfers a specified amount from one account to another.
     * If the transfer fails due to insufficient funds or other issues,
     * a reverse transaction is attempted automatically to compensate.
     *
     * @param fromAccountId ID of the account to transfer from
     * @param toAccountId ID of the account to transfer to
     * @param amount Amount to transfer
     * @param isReversal Flag indicating whether the current transfer is a reversal attempt
     * @throws AccountNotFoundException if either account does not exist
     * @throws InvalidTransactionException if fromAccountId equals toAccountId
     * @throws InsufficientFundsException if the balance is too low or amount is <= 0
     * @throws InvalidInputException if the amount format is invalid
     */
    public void transfer(int fromAccountId, int toAccountId, double amount, boolean isReversal)
            throws AccountNotFoundException, InvalidTransactionException,
            InsufficientFundsException, InvalidInputException {

        BigDecimal bigDecimalAmount;
        try {
            bigDecimalAmount = BigDecimal.valueOf(amount); // Convert amount to BigDecimal
        } catch (NumberFormatException e) {
            // Throw an exception if the amount format is invalid
            throw new InvalidInputException("Invalid amount format: " + amount);
        }

        // Check if the amount is valid (greater than zero)
        if (bigDecimalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException(
                    "Transfer amount must be positive. Provided: " + bigDecimalAmount
            );
        }

        // Retrieve the bank accounts involved in the transaction
        BankAccount fromAcc = accounts.get(fromAccountId);
        BankAccount toAcc = accounts.get(toAccountId);

        // Throw an exception if either account does not exist
        if (fromAcc == null || toAcc == null) {
            throw new AccountNotFoundException(String.format(
                    "Either account %d or %d not found.", fromAccountId, toAccountId
            ));
        }

        // Throw an exception if attempting to transfer to the same account
        if (fromAccountId == toAccountId) {
            throw new InvalidTransactionException("Cannot transfer to the same account.");
        }

        if (fromAcc.getBalance().compareTo(bigDecimalAmount) < 0) {
            throw new InsufficientFundsException(
                    "Account " + fromAccountId + " has insufficient funds. Required: " + bigDecimalAmount
                            + ", Available: " + fromAcc.getBalance());
        }

        System.out.println("Initiating transfer fromAcc=" + fromAccountId
                + " toAcc=" + toAccountId + " amount=" + bigDecimalAmount);

        // Lock accounts in ascending order to avoid deadlock
        BankAccount first = (fromAccountId < toAccountId) ? fromAcc : toAcc;
        BankAccount second = (fromAccountId < toAccountId) ? toAcc : fromAcc;

        first.writeLock(); // Lock the first account
        second.writeLock(); // Lock the second account
        try {
            // Attempt to withdraw from the source account
            fromAcc.withdraw(bigDecimalAmount);

            // Attempt to deposit into the destination account
            toAcc.deposit(bigDecimalAmount);

            System.out.println("Transfer successful: " + bigDecimalAmount
                    + " from " + fromAccountId + " to " + toAccountId);
        } catch (InsufficientFundsException e) {
            System.err.println("Transfer failed: " + e.getMessage());

            // If the transfer fails and it's not already a reversal attempt, try reversing the transaction
            if (!isReversal) {
                try {
                    reverseTransaction(toAccountId, fromAccountId, bigDecimalAmount);
                    System.out.println("Compensated: Reversed partial transfer.");
                } catch (Exception compensationError) {
                    // Log the compensation error if the reversal fails
                    System.err.println("Compensation failed: " + compensationError.getMessage());
                }
            }

            // Re-throw the original exception after attempting the reversal
            throw e;
        } finally {
            // Always unlock the accounts in the finally block to avoid deadlock
            second.writeUnlock();
            first.writeUnlock();
        }
    }

    /**
     * Reverses a transaction by transferring the same amount in the opposite direction.
     * This method ensures that if a transfer fails, it can be compensated by attempting
     * a reverse transaction to restore the original account balances.
     *
     * @param fromAccountId ID of the account to transfer from
     * @param toAccountId ID of the account to transfer to
     * @param amount Amount to reverse
     * @throws AccountNotFoundException if either account does not exist
     * @throws InvalidTransactionException if fromAccountId equals toAccountId
     * @throws InsufficientFundsException if the balance is too low or amount is <= 0
     * @throws InvalidInputException if the amount format is invalid
     */
    public void reverseTransaction(int fromAccountId, int toAccountId, BigDecimal amount)
            throws AccountNotFoundException, InvalidTransactionException,
            InsufficientFundsException, InvalidInputException {

        System.out.println("Reversing transaction: from=" + fromAccountId
                + ", to=" + toAccountId + ", amount=" + amount);

        // Call the transfer method with the isReversal flag set to true
        // This ensures that if the reversal also fails, it won't trigger another reversal attempt
        transfer(toAccountId, fromAccountId, amount.doubleValue(), true);
    }

    /**
     * Prints the balances of all managed bank accounts.
     */
    public void printAccountBalances() {
        accounts.values().stream()
                .sorted((a, b) -> Integer.compare(a.getId(), b.getId())) // Sort accounts by ID
                .forEach(acc -> {
                    System.out.println("Account " + acc.getId()
                            + " | Balance: " + acc.getBalance());
                });
    }
}