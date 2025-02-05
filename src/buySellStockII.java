public class buySellStockII {
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxProfit += prices[i] - prices[i - 1];
            }
        }

        return maxProfit;
    }

    public int maxProfit2(int[] prices) {
        int buy = prices[0];
        int profit = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < buy) {
                buy = prices[i];
            } else if (prices[i] - buy > 0) {
                profit += prices[i] - buy;
                buy = prices[i];
            }
        }
        return profit;
    }

}
