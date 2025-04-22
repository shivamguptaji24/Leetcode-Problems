/*
Daily Question :
2338 - Count the Number of Ideal Arrays

You are given two integers n and maxValue, which are used to describe an ideal array.
A 0-indexed integer array arr of length n is considered ideal if the following conditions hold:
Every arr[i] is a value from 1 to maxValue, for 0 <= i < n.
Every arr[i] is divisible by arr[i - 1], for 0 < i < n.
Return the number of distinct ideal arrays of length n. Since the answer may be very large, return it modulo 109 + 7. 

Example 1:

Input: n = 2, maxValue = 5
Output: 10
Explanation: The following are the possible ideal arrays:
- Arrays starting with the value 1 (5 arrays): [1,1], [1,2], [1,3], [1,4], [1,5]
- Arrays starting with the value 2 (2 arrays): [2,2], [2,4]
- Arrays starting with the value 3 (1 array): [3,3]
- Arrays starting with the value 4 (1 array): [4,4]
- Arrays starting with the value 5 (1 array): [5,5]
There are a total of 5 + 2 + 1 + 1 + 1 = 10 distinct ideal arrays.

Example 2:

Input: n = 5, maxValue = 3
Output: 11
Explanation: The following are the possible ideal arrays:
- Arrays starting with the value 1 (9 arrays): 
   - With no other distinct values (1 array): [1,1,1,1,1] 
   - With 2nd distinct value 2 (4 arrays): [1,1,1,1,2], [1,1,1,2,2], [1,1,2,2,2], [1,2,2,2,2]
   - With 2nd distinct value 3 (4 arrays): [1,1,1,1,3], [1,1,1,3,3], [1,1,3,3,3], [1,3,3,3,3]
- Arrays starting with the value 2 (1 array): [2,2,2,2,2]
- Arrays starting with the value 3 (1 array): [3,3,3,3,3]
There are a total of 9 + 1 + 1 = 11 distinct ideal arrays.
 

Constraints:

2 <= n <= 104
1 <= maxValue <= 104
*/

class Solution {
    private int[][] f;
    private int[][] c;
    private int n;
    private int m;
    private static final int MOD = (int) 1e9 + 7;

    public int idealArrays(int n, int maxValue) {
        this.n = n;
        this.m = maxValue;
        this.f = new int[maxValue + 1][16];
        for (int[] row : f) {
            Arrays.fill(row, -1);
        }
        c = new int[n][16];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j <= i && j < 16; ++j) {
                c[i][j] = j == 0 ? 1 : (c[i - 1][j] + c[i - 1][j - 1]) % MOD;
            }
        }
        int ans = 0;
        for (int i = 1; i <= m; ++i) {
            ans = (ans + dfs(i, 1)) % MOD;
        }
        return ans;
    }

    private int dfs(int i, int cnt) {
        if (f[i][cnt] != -1) {
            return f[i][cnt];
        }
        int res = c[n - 1][cnt - 1];
        if (cnt < n) {
            for (int k = 2; k * i <= m; ++k) {
                res = (res + dfs(k * i, cnt + 1)) % MOD;
            }
        }
        f[i][cnt] = res;
        return res;
    }
}

/*
Visualization of the above code
   Let’s walk through a visualization of this code for the problem "Ideal Arrays" from LeetCode.

---

💡 Problem Summary

You're given:
- `n`: the length of the array
- `maxValue`: the maximum allowed value for any element

We want to count the number of ideal arrays of length `n` such that:
- Each element is in `[1, maxValue]`
- For every `i < j`, `arr[j] % arr[i] == 0` (each value divides the next one — a divisible sequence)

---

🔍 High-Level Idea of the Code

The solution uses:

1. Dynamic Programming (`dfs`) with memoization:
   - To build sequences starting from number `i` with current count `cnt`.

2. Combinatorics (`c[i][j]`):
   - To calculate the number of ways we can place values across `n` positions when we know how many values will be used (`cnt` length).

---

🧱 Data Structures

- `f[i][cnt]`: memoization for number `i` used `cnt` times
- `c[n][16]`: precomputed binomial coefficients `C(n-1, cnt-1)`
- `MOD = 1e9+7`: To avoid integer overflow

---

📊 Let’s Walk Through an Example:

Suppose:
```
n = 2, maxValue = 5
```

---

🔁 For Loop (Main Logic):

```
for (int i = 1; i <= maxValue; ++i) {
    ans = (ans + dfs(i, 1)) % MOD;
}
```

You're starting sequences with each number from 1 to 5.

---

🔁 dfs(i, cnt)

Let’s say `dfs(2, 1)`:
- Base case: `cnt = 1`, result is `C(n-1, cnt-1) = C(1, 0) = 1`
- Now try to multiply `i` with all integers ≥ 2: `2*2=4`, `2*3=6` (beyond `maxValue=5`)

So:
```
res = 1 (base)
→ dfs(4, 2): returns 1 → total res = 2
```

Same idea applies to `dfs(1, 1)`:
- `1 → 2 → 4`
- `1 → 3`
- `1 → 5`
(Tracks all valid divisible sequences of length 2)

---

🤯 Why Use Binomial Coefficients?

You count different positions you can insert values for fixed-length sequences.

For a sequence of length `cnt`, the number of ways to extend it to length `n` is:
```
C(n-1, cnt-1)
```
Because you're selecting `cnt-1` positions (excluding the fixed starting position) out of `n-1` remaining ones.

---

🧮 Final Answer

For `n = 2`, `maxValue = 5`, we count all ideal sequences of length 2:

- Starting from 1 → [1,1], [1,2], [1,3], [1,4], [1,5]
- Starting from 2 → [2,2], [2,4]
- Starting from 3 → [3,3]
- Starting from 4 → [4,4]
- Starting from 5 → [5,5]

Total = 10 sequences → Output: `10`

✅ Matches LeetCode's example!

---

📌 Summary

| Part | Purpose |
|------|---------|
| `dfs(i, cnt)` | Recursively builds sequences from `i` of length `cnt` |
| `c[n-1][cnt-1]` | Number of ways to place a sequence of length `cnt` into `n` positions |
| Memoization (`f`) | Avoid recomputing the same subproblems |
| MOD | Ensures results fit in integer limits |

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 6ms runtime which is the lowest time in this problem.
*/

import java.math.BigInteger;

class Solution {
	public static final int MODULO = (int) 1e9 + 7;

	public static int idealArrays(int n, int maxValue) {
		int[] minDivisor = new int[maxValue + 1];
		for (int p = 2; p <= maxValue; p++) {
			if (minDivisor[p] != 0)
				continue;
			for (int i = p; i <= maxValue; i += p)
				if (minDivisor[i] == 0)
					minDivisor[i] = p;
		}
		
		int maxPow = (int) (Math.log(maxValue) / Math.log(2));
		int[] binCoeff = new int[maxPow + 1];
		BigInteger b = BigInteger.ONE;
		BigInteger bigMod = BigInteger.valueOf(MODULO);
		for (int i = 1; i <= maxPow; i++) {
			b = b.multiply(BigInteger.valueOf(n + i - 1));
			b = b.divide(BigInteger.valueOf(i));
			binCoeff[i] = b.mod(bigMod).intValue();
		}
		
		int s = 0;
		for (int i = 1; i <= maxValue; i++) {
			int x = i;
			long prodBin = 1;
			while (x > 1) {
				int p = minDivisor[x];
				int w = 0;
				do {
					w++;
					x /= p;
				} while (x % p == 0);
				prodBin = prodBin * binCoeff[w] % MODULO;
			}
			s = (s + (int) prodBin) % MODULO;
		}
		return s;
	}
}
