/*
Daily Question :
1534 - Count Good Triplets

Given an array of integers arr, and three integers a, b and c. You need to find the number of good triplets.
A triplet (arr[i], arr[j], arr[k]) is good if the following conditions are true:
0 <= i < j < k < arr.length
|arr[i] - arr[j]| <= a
|arr[j] - arr[k]| <= b
|arr[i] - arr[k]| <= c
Where |x| denotes the absolute value of x.
Return the number of good triplets. 

Example 1:

Input: arr = [3,0,1,1,9,7], a = 7, b = 2, c = 3
Output: 4
Explanation: There are 4 good triplets: [(3,0,1), (3,0,1), (3,1,1), (0,1,1)].

Example 2:

Input: arr = [1,1,2,2,3], a = 0, b = 0, c = 1
Output: 0
Explanation: No triplet satisfies all conditions.
 

Constraints:

3 <= arr.length <= 100
0 <= arr[i] <= 1000
0 <= a, b, c <= 1000
*/

class Solution {
    public int countGoodTriplets(int[] arr, int a, int b, int c) {
        int count = 0;
        int n = arr.length;

        // Iterate all possible triplets (i, j, k) where i < j < k
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                // First condition check
                if (Math.abs(arr[i] - arr[j]) <= a) {
                    for (int k = j + 1; k < n; k++) {
                        // Check all three conditions for a good triplet
                        if (Math.abs(arr[j] - arr[k]) <= b && 
                            Math.abs(arr[i] - arr[k]) <= c) {
                            count++; // Valid triplet found
                        }
                    }
                }
            }
        }
        
        return count;
    }
}

/*
Visualization of the above code
 Great! Let’s visualize how this Java code works step by step with a sample input:

---

🔢 Input:

```
arr = [3, 0, 1, 1, 9, 7]
a = 7, b = 2, c = 3
```

We want to find the number of triplets `(i, j, k)` such that:

- `0 <= i < j < k < arr.length`
- `|arr[i] - arr[j]| <= 7`
- `|arr[j] - arr[k]| <= 2`
- `|arr[i] - arr[k]| <= 3`

---

🎯 Code Overview:

We loop through **all combinations of triplets** `(i, j, k)` and count how many satisfy the three conditions above.

Let’s trace one full example triplet and then summarize how the algorithm works for the rest.

---

🔍 Step-by-Step Example for Triplet (i=0, j=1, k=2):

- `arr[i] = 3`, `arr[j] = 0`, `arr[k] = 1`
- Check conditions:
  - `|3 - 0| = 3 ≤ 7 ✅`
  - `|0 - 1| = 1 ≤ 2 ✅`
  - `|3 - 1| = 2 ≤ 3 ✅`
- ✅ This is a **good triplet**, so `count++`

---

✅ All Good Triplets:

We’ll do this for all valid `(i, j, k)`:

| Triplet (i, j, k) | Values (arr[i], arr[j], arr[k]) | Conditions Met? |
|------------------|-------------------------------|------------------|
| (0, 1, 2)        | (3, 0, 1)                      | ✅               |
| (0, 1, 3)        | (3, 0, 1)                      | ✅               |
| (0, 2, 3)        | (3, 1, 1)                      | ✅               |
| (1, 2, 3)        | (0, 1, 1)                      | ✅               |

All others fail at least one of the conditions.

So, total `count = 4`.

---

🧠 Final Thoughts:

The code:

- Loops `i` from 0 to n-3
- Loops `j` from i+1 to n-2
- Loops `k` from j+1 to n-1
- Checks the 3 conditions
- Increments `count` if valid

---

🧪 Output:

```
return 4;
```

That’s your answer ✅

---
*/
