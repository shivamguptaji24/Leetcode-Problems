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
