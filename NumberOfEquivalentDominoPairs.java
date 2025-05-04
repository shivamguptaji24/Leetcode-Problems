/*
Daily Question :
1128 - Number Of Equivalent Domino Pairs

Given a list of dominoes, dominoes[i] = [a, b] is equivalent to dominoes[j] = [c, d] if and only if either (a == c and b == d), or (a == d and b == c) - that is, one domino can be rotated to be equal to another domino.
Return the number of pairs (i, j) for which 0 <= i < j < dominoes.length, and dominoes[i] is equivalent to dominoes[j]. 

Example 1:

Input: dominoes = [[1,2],[2,1],[3,4],[5,6]]
Output: 1

Example 2:

Input: dominoes = [[1,2],[1,2],[1,1],[1,2],[2,2]]
Output: 3
 

Constraints:

1 <= dominoes.length <= 4 * 104
dominoes[i].length == 2
1 <= dominoes[i][j] <= 9
*/

class Solution {
    public int numEquivDominoPairs(int[][] dominoes) {
        int[] count = new int[100];  // Since max key = 9*10 + 9 = 99
        int result = 0;

        for (int[] domino : dominoes) {
            int a = domino[0], b = domino[1];
            int key = a < b ? a * 10 + b : b * 10 + a;
            result += count[key];
            count[key]++;
        }

        return result;
    }
}

/*
Visualization of the above code
 Sure, let's visualize how the code works with a step-by-step example.

---

👨‍💻 Code:

```
int[][] dominoes = {{1,2}, {2,1}, {3,4}, {5,6}};
```

We use:

* `int[] count = new int[100]` to track normalized domino keys.
* For each domino:

  * Normalize it to a unique key using `key = a < b ? a * 10 + b : b * 10 + a`
  * Add `count[key]` to result (because that’s the number of equivalent dominoes seen so far)
  * Increment `count[key]`

---

🧠 Step-by-Step Execution:

| Iteration | Domino | a | b | Key | count\[key] before | result | count\[key] after |
| --------- | ------ | - | - | --- | ------------------ | ------ | ----------------- |
| 1         | \[1,2] | 1 | 2 | 12  | 0                  | 0      | 1                 |
| 2         | \[2,1] | 2 | 1 | 12  | 1                  | 1      | 2                 |
| 3         | \[3,4] | 3 | 4 | 34  | 0                  | 1      | 1                 |
| 4         | \[5,6] | 5 | 6 | 56  | 0                  | 1      | 1                 |

---

✅ Final Answer: `1`

We found 1 pair of equivalent dominoes: `[1,2]` and `[2,1]`.

---

📊 Visual Representation of `count[]` after the loop:

* `count[12] = 2` → represents two dominoes equivalent to `[1,2]`
* `count[34] = 1`
* `count[56] = 1`
* All other `count[i] = 0`

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 3ms runtime which is the most used time in this problem.
*/

class Solution {
    public int numEquivDominoPairs(int[][] dominoes) {
         int[] num = new int[100];
        int ret = 0;
        for (int[] domino : dominoes) {
            int val = domino[0] < domino[1]
                ? domino[0] * 10 + domino[1]
                : domino[1] * 10 + domino[0];
            ret += num[val];
            num[val]++;
        }
        return ret;
    }
}

/*
Visualization of the above code
 Let's visualize this Java code step by step with a sample input.

---

🔁 Input Example:

```
int[][] dominoes = {
    {1,2}, {2,1}, {1,2}, {3,4}, {5,6}
};
```

🧠 Understanding the Code:

* We create an array `num[100]` to count occurrences of each normalized domino.

* Dominoes like `[1,2]` and `[2,1]` are considered equivalent → we normalize by always storing the smaller number first:

  ```
  val = min * 10 + max
  ```

* For each domino:

  * Add the count of previous same dominoes to `ret`.
  * Then increment that count.

---

📊 Step-by-Step Visualization:

| Iteration | Domino | Normalized `val` | num\[val] before | ret (pair count) | num\[val] after |
| --------- | ------ | ---------------- | ---------------- | ---------------- | --------------- |
| 1         | \[1,2] | 12               | 0                | 0                | 1               |
| 2         | \[2,1] | 12               | 1                | 1                | 2               |
| 3         | \[1,2] | 12               | 2                | 3 (1+2)          | 3               |
| 4         | \[3,4] | 34               | 0                | 3                | 1               |
| 5         | \[5,6] | 56               | 0                | 3                | 1               |

---

✅ Final Result:

```
ret = 3
```

Because:

* `[1,2]` and `[2,1]` → 1 pair
* `[1,2]` and `[1,2]` → 2 more pairs

> Total = 3 equivalent pairs.

---

🧾 Summary:

* Normalized key: `min * 10 + max` (ensures \[a,b] == \[b,a])
* num\[val] keeps count of occurrences
* ret accumulates number of previously seen matching dominoes
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 1ms runtime which is the lowest time in this problem.
*/

class Solution {
    public int numEquivDominoPairs(int[][] dominoes) {
        int[][] dp = new int[10][10];
        for(int[] domino : dominoes){
            dp[domino[0]][domino[1]]++;
        }
        int max = 0;
        for(int i = 1; i < dp.length; i++){
            for(int j = i; j < dp[i].length; j++){
                int count = dp[i][j];
                if(i != j){
                    count += dp[j][i];
                }
                max += count * (count - 1) / 2;
            }
        }
        return max;
    }
}



    
//      0   1   2   3   4   5   6   7   8   9

// 0    0   0   0   0   0   0   0   0   0   0
        
// 1    0   1   3   0   0   0   0   0   0   0
        
// 2    0   0   1   0   0   0   0   0   0   0
        
// 3    0   0   0   0   0   0   0   0   0   0
        
// 4    0   0   0   0   0   0   0   0   0   0
        
// 5    0   0   0   0   0   0   0   0   0   0
        
// 6    0   0   0   0   0   0   0   0   0   0
        
// 7    0   0   0   0   0   0   0   0   0   0
        
// 8    0   0   0   0   0   0   0   0   0   0
        
// 9    0   0   0   0   0   0   0   0   0   0

/*
Visualization of the above code
 Let's visualize your code step by step, with a clear explanation of what the 2D array (`dp[10][10]`) is doing and how the logic flows.

---

🔍 Code Overview

```
public int numEquivDominoPairs(int[][] dominoes) {
    int[][] dp = new int[10][10];  // Stores frequency of each (a, b) pair

    for (int[] domino : dominoes) {
        dp[domino[0]][domino[1]]++;
    }

    int max = 0;
    for (int i = 1; i < dp.length; i++) {
        for (int j = i; j < dp[i].length; j++) {
            int count = dp[i][j];
            if (i != j) {
                count += dp[j][i];
            }
            max += count * (count - 1) / 2;
        }
    }

    return max;
}
```

---

🔧 Sample Input:

```
int[][] dominoes = {
    {1,2}, {2,1}, {1,2}, {2,1}, {1,1}
};
```

🧠 Step-by-Step Execution:

Step 1: Fill the `dp` table

* This table keeps count of exact appearances:
  `dp[a][b]++` ← stores the domino as-is.

| Domino | dp\[a]\[b] Incremented |
| ------ | ---------------------- |
| \[1,2] | dp\[1]\[2] → 1         |
| \[2,1] | dp\[2]\[1] → 1         |
| \[1,2] | dp\[1]\[2] → 2         |
| \[2,1] | dp\[2]\[1] → 2         |
| \[1,1] | dp\[1]\[1] → 1         |

Final `dp` state (only relevant values shown):

```
dp[1][1] = 1
dp[1][2] = 2
dp[2][1] = 2
```

Step 2: Count equivalent pairs

Iterate through only the upper triangle (i ≤ j) to avoid double-counting.

| i | j | dp\[i]\[j] | dp\[j]\[i] | total count | Pairs Formula                 | Pairs |
| - | - | ---------- | ---------- | ----------- | ----------------------------- | ----- |
| 1 | 1 | 1          | —          | 1           | 1 \* (1 - 1) / 2              | 0     |
| 1 | 2 | 2          | 2          | 4           | 4 \* (4 - 1) / 2 = 4 \* 3 / 2 | 6     |
| 2 | 2 | 0          | —          | 0           | 0                             | 0     |
| … | … | …          | …          | …           | …                             | …     |

🟢 Total Pairs = 6

---

✅ Final Result:

```
return 6;
```

---

📌 Key Observations:

* Instead of normalizing dominoes right away, we store all original orientations in `dp[a][b]`.
* In the second loop, we account for symmetry: if `[a,b]` and `[b,a]` both exist, we combine them (`i != j`).
* The formula `count * (count - 1) / 2` gives us number of ways to choose 2 out of `count` (combinatorics).

---

📊 dp Table Visualization:

```
     0 1 2 3 4 5 6 7 8 9
   ---------------------
0 |  0 0 0 0 0 0 0 0 0 0
1 |  0 1 2 0 0 0 0 0 0 0
2 |  0 2 0 0 0 0 0 0 0 0
3 |  0 0 0 0 0 0 0 0 0 0
...
```

---
*/
