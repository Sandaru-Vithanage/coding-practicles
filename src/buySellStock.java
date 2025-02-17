/**
 * This class provides a method to calculate the maximum profit from buying and selling stock.
 */
public class buySellStock {

    /**
     * Calculates the maximum profit that can be achieved from buying and selling stock.
     *
     * @param prices an array where the i-th element is the price of a given stock on day i
     * @return the maximum profit that can be achieved
     */
    public int maxProfit(int[] prices) {
        // Initialize the buyDay to the first day's price
        int buyDay = prices[0];
        // Initialize the maximum profit to 0
        int maxProfit = 0;

        // Iterate through the prices starting from the second day
        for (int i = 1; i < prices.length; i++) {
            // If the current price is lower than the buyDay price, update buyDay
            if (prices[i] < buyDay) {
                buyDay = prices[i];
            } else {
                // Calculate the profit if selling on the current day
                int profit = prices[i] - buyDay;
                // Update the maximum profit if the current profit is higher
                maxProfit = Math.max(maxProfit, profit);
            }
        }

        // Return the maximum profit
        return maxProfit;
    }
}