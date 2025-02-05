public class buySellStock {
    public int maxProfit(int[] prices) {
        int buyDay = prices[0];
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < buyDay) {
                buyDay = prices[i];
            } else {
                int profit = prices[i] - buyDay;
                maxProfit = Math.max(maxProfit, profit);
            }
        }
        return maxProfit;
    }
}
