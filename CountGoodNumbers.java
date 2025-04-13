/*
Daily Question :
1922 - Count Good Numbers

A digit string is good if the digits (0-indexed) at even indices are even and the digits at odd indices are prime (2, 3, 5, or 7).
For example, "2582" is good because the digits (2 and 8) at even positions are even and the digits (5 and 2) at odd positions are prime. However, "3245" is not good because 3 is at an even index but is not even.
Given an integer n, return the total number of good digit strings of length n. Since the answer may be large, return it modulo 109 + 7.
A digit string is a string consisting of digits 0 through 9 that may contain leading zeros.

Example 1:

Input: n = 1
Output: 5
Explanation: The good numbers of length 1 are "0", "2", "4", "6", "8".
Example 2:

Input: n = 4
Output: 400
Example 3:

Input: n = 50
Output: 564908303
 

Constraints:

1 <= n <= 10^15
*/

class Solution {
    // Define the modulo constant as per the problem statement
    private static final int MOD = 1_000_000_007;

    public int countGoodNumbers(long n) {
        // Count of digits at even indices (0, 2, 4, ...): these must be even digits (0, 2, 4, 6, 8) => 5 options
        long evenCount = (n + 1) / 2;

        // Count of digits at odd indices (1, 3, 5, ...): these must be prime digits (2, 3, 5, 7) => 4 options
        long oddCount = n / 2;

        // Compute total combinations for even and odd indices separately using modular exponentiation
        long evenWays = modPow(5, evenCount); // 5^evenCount % MOD
        long oddWays = modPow(4, oddCount);   // 4^oddCount % MOD

        // Multiply both results and take final modulo
        return (int)((evenWays * oddWays) % MOD);
    }

    // Helper function to compute (base^exp) % MOD efficiently using fast exponentiation
    private long modPow(long base, long exp) {
        long result = 1;
        base %= MOD;

        while (exp > 0) {
            // If current bit of exponent is 1, multiply the base to result
            if ((exp & 1) == 1) {
                result = (result * base) % MOD;
            }

            // Square the base and shift exponent to the right by 1 (equivalent to exp / 2)
            base = (base * base) % MOD;
            exp >>= 1;
        }

        return result;
    }
}
