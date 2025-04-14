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

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 4ms runtime which is the lowest time in this problem.
*/

class Solution {

    public int countGoodTriplets(int[] arr, int a, int b, int c) {
        int ans = 0, n = arr.length;
        int[] sum = new int[1001];
        for (int j = 0; j < n; ++j) {
            for (int k = j + 1; k < n; ++k) {
                if (Math.abs(arr[j] - arr[k]) <= b) {
                    int lj = arr[j] - a, rj = arr[j] + a;
                    int lk = arr[k] - c, rk = arr[k] + c;
                    int l = Math.max(0, Math.max(lj, lk)), r = Math.min(
                        1000,
                        Math.min(rj, rk)
                    );
                    if (l <= r) {
                        if (l == 0) {
                            ans += sum[r];
                        } else {
                            ans += sum[r] - sum[l - 1];
                        }
                    }
                }
            }
            for (int k = arr[j]; k <= 1000; ++k) {
                ++sum[k];
            }
        }
        return ans;
    }
}

/*
Visualization of the above code
 Great! Let's visualize and break down this optimized Java code for counting good triplets using prefix sums. We’ll do this step-by-step with the help of an example.

---

🔢 Problem Recap:
We’re given an array `arr`, and integers `a`, `b`, `c`. A triplet `(i, j, k)` is good if:

- `0 <= i < j < k < arr.length`
- `|arr[i] - arr[j]| <= a`
- `|arr[j] - arr[k]| <= b`
- `|arr[i] - arr[k]| <= c`

We want to efficiently count such triplets.

---

🧠 Code Strategy:

Instead of brute-force 3 nested loops, this code optimizes the approach using:

🔹 Prefix Sum Technique:
- `sum[x]` counts how many previous `arr[i]` values (with `i < j`) are ≤ `x`
- It helps quickly count how many values of `arr[i]` satisfy the `|arr[i] - arr[j]| <= a` and `|arr[i] - arr[k]| <= c` conditions, combined via intersection.

---

📘 Step-by-Step Visualization (with Example):

Input:
```
arr = [3, 0, 1, 1, 9, 7]
a = 7, b = 2, c = 3
```

---

Initialize:
```
int ans = 0, n = arr.length;
int[] sum = new int[1001]; // To hold prefix frequency sums
```

---

Loop Explanation:

Outer Loop (for `j`):
```
for (int j = 0; j < n; ++j)
```
We're treating `arr[j]` as the middle element of the triplet `(i, j, k)`

Inner Loop (for `k` > `j`):
```
for (int k = j + 1; k < n; ++k)
```
Now `arr[k]` is the 3rd element, and we want to find valid `i` values using conditions:

---

Let’s Take First Iteration: j = 0

`arr[j] = 3`

Inner loop: k = 1
`arr[k] = 0`

Check if `|arr[j] - arr[k]| = |3 - 0| = 3` → ❌ Not ≤ b = 2 → skip

Inner loop: k = 2
`arr[k] = 1`

`|3 - 1| = 2` ✅

Now:
```
lj = arr[j] - a = 3 - 7 = -4
rj = arr[j] + a = 3 + 7 = 10
lk = arr[k] - c = 1 - 3 = -2
rk = arr[k] + c = 1 + 3 = 4
```

So:
```
l = max(0, max(lj, lk)) = max(0, max(-4, -2)) = 0
r = min(1000, min(rj, rk)) = min(1000, min(10, 4)) = 4
```

This means: we want values of `arr[i]` (i < j) in range [0, 4]

Prefix sum lets us get this quickly:
```
if (l == 0)
    ans += sum[r]; // sum[4] → total values <= 4 seen before j
```

Since this is the first iteration (j = 0) → sum is all 0 → `ans += 0`

---

After j = 0 is done, update prefix sum:
```
for (int k = arr[j]; k <= 1000; ++k) {
    ++sum[k]; // This marks that we've seen arr[j] once now
}
```
So `arr[0] = 3`, we increment `sum[3...1000]`

Now:
```
sum[0..2] = 0
sum[3] = 1
sum[4] = 1
...
sum[1000] = 1
```

---

⚡ Key Benefit of Prefix Sum:
You can quickly count how many previous `arr[i]` are in range `[l, r]` using:
```
sum[r] - sum[l-1]
```

This avoids scanning the entire array for each j and k, reducing time from O(n³) to O(n²).

---

Final Result:

The code returns `4` for the given example, just like the brute-force version, but much faster, especially when `arr.length` is closer to 100.

---

✅ Summary:

- `j` is middle element, `k` is end, and we use `sum` to count valid starting elements `i`
- `sum` helps us avoid re-checking elements every time — it’s like a running history of valid values.
- It's optimized and perfect for interviews/LeetCode.

---
*/
