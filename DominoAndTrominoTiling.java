/*
Daily Question :
790 - Domino and Tromino Tiling

You have two types of tiles: a 2 x 1 domino shape and a tromino shape. You may rotate these shapes.
Given an integer n, return the number of ways to tile an 2 x n board. Since the answer may be very large, return it modulo 109 + 7.
In a tiling, every square must be covered by a tile. Two tilings are different if and only if there are two 4-directionally adjacent cells on the board such that exactly one of the tilings has both squares occupied by a tile.

Example 1:

Input: n = 3
Output: 5
Explanation: The five different ways are show above.

Example 2:

Input: n = 1
Output: 1
 

Constraints:

1 <= n <= 1000
*/

class Solution {
    static final int MOD = 1_000_000_007;

    public int numTilings(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        if (n == 3) return 5;

        long a = 1; // dp[0]
        long b = 1; // dp[1]
        long c = 2; // dp[2]
        long d = 5; // dp[3]
        long sum = a + b; // sum of dp[0] to dp[i-3]

        for (int i = 4; i <= n; i++) {
            long temp = (2 * sum % MOD + d + c) % MOD;

            // update sum = sum + dp[i - 2]
            sum = (sum + c) % MOD;

            // shift window
            a = b;
            b = c;
            c = d;
            d = temp;
        }

        return (int) d;
    }
}
