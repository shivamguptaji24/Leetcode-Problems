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

/*
Visualization of the above code
 Let’s break down and visualize your `numTilings` code step by step so you can clearly understand how it works and why it's optimal.

---

🧩 Problem: Tiling a `2 x n` board using:

* Domino (2×1) — can be placed vertically or horizontally.
* Tromino (L-shaped) — can be rotated in 4 ways.

---

📦 DP Idea:

We define `dp[n]` = number of ways to tile a 2×n board.

We keep only last few values:

* `a` = `dp[0]`
* `b` = `dp[1]`
* `c` = `dp[2]`
* `d` = `dp[3]`
* `sum` = dp\[0] + dp\[1] + ... + dp\[i-3] (for computing tromino contributions)

---

🧮 Initialization:

| Variable | Meaning       | Value |
| -------- | ------------- | ----- |
| a        | dp\[0]        | 1     |
| b        | dp\[1]        | 1     |
| c        | dp\[2]        | 2     |
| d        | dp\[3]        | 5     |
| sum      | dp\[0]+dp\[1] | 2     |

This setup covers the base cases.

---

🔁 Loop (from i = 4 to n)

At each step `i`:

```
temp = (2 * sum + d + c) % MOD;
```

| Term     | Meaning                                                   |
| -------- | --------------------------------------------------------- |
| 2 \* sum | Accounts for placing L-shaped tromino in two orientations |
| d        | dp\[i-1]: placing a vertical domino                       |
| c        | dp\[i-2]: placing two horizontal dominoes                 |
| temp     | dp\[i]                                                    |

Then we update:

```
sum = (sum + c) % MOD;  // expand sum to include dp[i - 2]
```

Then rotate the window forward:

```
a = b;
b = c;
c = d;
d = temp;
```

---

📈 Example Trace: n = 4

Already:

* dp\[0] = 1
* dp\[1] = 1
* dp\[2] = 2
* dp\[3] = 5
  (sum = dp\[0] + dp\[1] = 2)

Now for i = 4:

```
temp = (2 * sum + d + c)
     = (2 * 2 + 5 + 2) = 11
sum = sum + c = 2 + 2 = 4
```

Then:

* dp\[4] = 11
* shift: a = 1, b = 2, c = 5, d = 11

And so on for n = 5, 6, ...

---

📌 Visualization Summary:

Here's a quick layout:

```
dp[i] = dp[i-1] + dp[i-2] + 2 * (dp[0] + ... + dp[i-3])
       ^ vertical      ^ 2 horizontals  ^ L-tromino in 2 ways
```

Instead of storing full `dp[]`, we rotate the last few values, and use a `sum` to accumulate the L-shaped possibilities efficiently.

---
*/
