import java.util.*;

public class E {

    private static final int inf = 1000000;
    private static int[][] dp;
    private static int[] costs;


    private static int DP(int i, int j) {
        if (j > i) return inf;

        if (j > 0) {
            if (dp[i][j] != -1) return dp[i][j];
            if (costs[i] > 100) dp[i][j] = Math.min(DP(i - 1, j + 1), DP(i - 1, j - 1) + costs[i]);
            else                dp[i][j] = Math.min(DP(i - 1, j + 1), DP(i - 1, j) + costs[i]);

            return dp[i][j];
        }

        if (i >= 1) {
            if (costs[i] <= 100) {
                dp[i][j] = Math.min(DP(i - 1, j + 1), DP(i - 1, j) + costs[i]);
                return dp[i][j];
            }

            return DP(i - 1, j + 1);
        }

        return 0;
    }


    private static void goodOldDays(List<Integer> used, int i, int j) {
        if (j >= i) return;

        if (j <= 0) {
            if (i >= 1) {
                if (costs[i] > 100 || DP(i, j) == DP(i - 1, j + 1)) {
                    used.add(i);
                    goodOldDays(used, i - 1, j + 1);
                }
                else goodOldDays(used, i - 1, j);
            }
            return;
        }

        if (DP(i - 1, j + 1) == DP(i, j)) {
            used.add(i);
            goodOldDays(used, i - 1, j + 1);
        } else {
            if (costs[i] <= 100) goodOldDays(used, i - 1, j);
            else                 goodOldDays(used, i - 1, j - 1);
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int N = scanner.nextInt();
        costs = new int[N + 1];

        for (int i = 1; i <= N; i++)
            costs[i] = scanner.nextInt();

        scanner.close();


        dp = new int[N + 1][N + 2];
        for (int i = 0; i <= N; i++)
            Arrays.fill(dp[i], -1);

        int minPrice = inf;
        int couponsLeft = 0;

        for (int i = 0; i <= N; i++) {
            if (minPrice >= DP(N, i)) {
                minPrice = DP(N, i);
                couponsLeft = i;
            }
        }

        List<Integer> used = new LinkedList<>();
        goodOldDays(used, N, couponsLeft);
        Collections.reverse(used);

        System.out.println(minPrice);
        System.out.println(couponsLeft + " " + used.size());
        used.forEach(System.out::println);
    }
}
