/*
Daily Question :
368 - Largest Divisible Subset

Given a set of distinct positive integers nums, return the largest subset answer such that every pair (answer[i], answer[j]) of elements in this subset satisfies:
answer[i] % answer[j] == 0, or
answer[j] % answer[i] == 0
If there are multiple solutions, return any of them. 

Example 1:

Input: nums = [1,2,3]
Output: [1,2]
Explanation: [1,3] is also accepted.

Example 2:

Input: nums = [1,2,4,8]
Output: [1,2,4,8]
 

Constraints:

1 <= nums.length <= 1000
1 <= nums[i] <= 2 * 109
All the integers in nums are unique.
*/

class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums); // Sort the array

        int[] dp = new int[n];     // dp[i] = size of largest subset ending at i
        int[] prev = new int[n];   // prev[i] = previous index in subset
        Arrays.fill(dp, 1);        // Every element is a subset of size 1
        Arrays.fill(prev, -1);     // No previous element initially

        int maxSize = 1, maxIndex = 0;

        // Build the dp and prev arrays
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }

            if (dp[i] > maxSize) {
                maxSize = dp[i];
                maxIndex = i;
            }
        }

        // Reconstruct the subset
        List<Integer> result = new ArrayList<>();
        int curr = maxIndex;
        while (curr >= 0) {
            result.add(nums[curr]);
            curr = prev[curr];
        }

        Collections.reverse(result); // To maintain increasing order
        return result;
    }
}

/*
Visualization of the above code
 Let's visualize step-by-step how the `largestDivisibleSubset` code works using an example input.

---

🧠 Problem:
Given a set of distinct positive integers `nums`, find the largest subset such that for every pair `(a, b)` in the subset:
- `a % b == 0` OR `b % a == 0`

---

🧪 Input:
```
nums = [1, 2, 4, 8]
```

---

🔢 Step 1: Sort the array
```
Arrays.sort(nums); // nums = [1, 2, 4, 8]
```

---

📦 Step 2: Initialize
```
dp    = [1, 1, 1, 1] // Each element is its own subset
prev  = [-1, -1, -1, -1] // No previous index yet
maxSize = 1
maxIndex = 0
```

---

🔁 Step 3: Fill DP and PREV Arrays

i = 1 (`nums[1] = 2`)
- j = 0 → 2 % 1 == 0 → `dp[1] = dp[0] + 1 = 2` → `prev[1] = 0`

`dp = [1, 2, 1, 1]`  
`prev = [-1, 0, -1, -1]`  
`maxSize = 2, maxIndex = 1`

---

i = 2 (`nums[2] = 4`)
- j = 0 → 4 % 1 == 0 → `dp[2] = dp[0] + 1 = 2`, `prev[2] = 0`
- j = 1 → 4 % 2 == 0 → `dp[2] = dp[1] + 1 = 3`, `prev[2] = 1`

`dp = [1, 2, 3, 1]`  
`prev = [-1, 0, 1, -1]`  
`maxSize = 3, maxIndex = 2`

---

i = 3 (`nums[3] = 8`)
- j = 0 → 8 % 1 == 0 → `dp[3] = dp[0] + 1 = 2`, `prev[3] = 0`
- j = 1 → 8 % 2 == 0 → `dp[3] = dp[1] + 1 = 3`, `prev[3] = 1`
- j = 2 → 8 % 4 == 0 → `dp[3] = dp[2] + 1 = 4`, `prev[3] = 2`

`dp = [1, 2, 3, 4]`  
`prev = [-1, 0, 1, 2]`  
`maxSize = 4, maxIndex = 3`

---

🔁 Step 4: Reconstruct the Subset

```
List<Integer> result = new ArrayList<>();
int curr = maxIndex = 3

while (curr >= 0):
    result.add(nums[curr])
    curr = prev[curr]
```

- result = [8] → prev[3] = 2  
- result = [8, 4] → prev[2] = 1  
- result = [8, 4, 2] → prev[1] = 0  
- result = [8, 4, 2, 1] → prev[0] = -1 (stop)

Reverse the list → [1, 2, 4, 8]

---

✅ Final Output:
```
[1, 2, 4, 8]
```

---

🖼️ Visualization Summary:

| i | nums[i] | dp[i] | prev[i] | Explanation |
|---|---------|-------|---------|-------------|
| 0 | 1       | 1     | -1      | Single element |
| 1 | 2       | 2     | 0       | 2 % 1 == 0 |
| 2 | 4       | 3     | 1       | 4 % 2 == 0 |
| 3 | 8       | 4     | 2       | 8 % 4 == 0 |

---
*/
