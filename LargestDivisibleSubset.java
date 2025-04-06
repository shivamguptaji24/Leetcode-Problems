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

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 10ms runtime which is the lowest time in this problem
*/

class Solution {
    public List<Integer> largestDivisibleSubset(int[] arr) {
        Arrays.sort(arr);
        int n=arr.length;
        int[] dp = new int[n];
        int[] ind = new int[n];
        int res=0;
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=0;i<n;i++){
            int maxi=0;
            ind[i]=i;
            dp[i]=1;
            int limit = (arr[i] + 1) / 2;
            for(int j=0;j<i && arr[j]<=limit;j++){
                if(arr[i]%arr[j]==0 && dp[j]+1>dp[i])
                {
                    dp[i]=1+dp[j];
                    ind[i]=j;
                }
            }
            res=dp[i]>dp[res]?i:res;
        }
        while(res!=ind[res]){
            ans.add(arr[res]);
            res=ind[res];
        }
        ans.add(arr[res]);
        Collections.reverse(ans);
        return ans;
    }
}

/*
Visualization of the above code
 Let's visualize step-by-step how this Java code works for finding the largest divisible subset in a slightly different (and optimized) way than the standard solution.

---

📘 Problem Recap:
Given an array `arr[]` of distinct positive integers, return the largest subset where every pair `(a, b)` satisfies:

> `a % b == 0` OR `b % a == 0`

---

✅ Code Overview:
This version optimizes inner loop range using a `limit`:
```
int limit = (arr[i] + 1) / 2;
```
This helps avoid unnecessary checks — numbers larger than `arr[i]/2` can't divide `arr[i]` except `arr[i]` itself.

---

🧪 Input Example:
```
arr = [1, 2, 3, 8, 4]
```

---

🔢 Step 1: Sort the array
```
Arrays.sort(arr); // arr = [1, 2, 3, 4, 8]
```

---

📦 Step 2: Initialize variables
```
dp  = [1, 1, 1, 1, 1]     // Length of largest subset ending at i
ind = [0, 1, 2, 3, 4]     // To reconstruct the path
res = 0                  // Index of max length subset
```

---

🔁 Step 3: Build dp and ind arrays

i = 0 → `arr[0] = 1`
- Nothing before it → `dp[0] = 1`, `ind[0] = 0`

---

i = 1 → `arr[1] = 2`
- limit = (2 + 1)/2 = 1
- j = 0 → 2 % 1 == 0 → `dp[1] = dp[0] + 1 = 2`, `ind[1] = 0`

---

i = 2 → `arr[2] = 3`
- limit = 2
- j = 0 → 3 % 1 == 0 → `dp[2] = 2`, `ind[2] = 0`
- j = 1 → 3 % 2 ≠ 0 → skip

---

i = 3 → `arr[3] = 4`
- limit = (4+1)/2 = 2
- j = 0 → 4 % 1 == 0 → `dp[3] = 2`, `ind[3] = 0`
- j = 1 → 4 % 2 == 0 → `dp[3] = 3`, `ind[3] = 1`
- j = 2 → 4 % 3 ≠ 0 → skip

---

i = 4 → `arr[4] = 8`
- limit = 4
- j = 0 → 8 % 1 == 0 → `dp[4] = 2`, `ind[4] = 0`
- j = 1 → 8 % 2 == 0 → `dp[4] = 3`, `ind[4] = 1`
- j = 2 → 8 % 3 ≠ 0 → skip
- j = 3 → 8 % 4 == 0 → `dp[4] = 4`, `ind[4] = 3`

---

After loop:
```
dp   = [1, 2, 2, 3, 4]
ind  = [0, 0, 0, 1, 3]
res  = 4 // Index of max subset
```

---

🔁 Step 4: Reconstruct the subset

```
res = 4 → arr[4] = 8
ind[4] = 3 → arr[3] = 4
ind[3] = 1 → arr[1] = 2
ind[1] = 0 → arr[0] = 1
```

So:
```
ans = [8, 4, 2, 1] → reverse → [1, 2, 4, 8]
```

---

✅ Final Output:
```
[1, 2, 4, 8]
```

---

📊 Summary Table:

| i | arr[i] | limit | dp[i] | ind[i] | Notes                          |
|---|--------|-------|--------|--------|--------------------------------|
| 0 | 1      | -     | 1      | 0      | First element                 |
| 1 | 2      | 1     | 2      | 0      | 2 % 1 == 0                    |
| 2 | 3      | 2     | 2      | 0      | 3 % 1 == 0 only               |
| 3 | 4      | 2     | 3      | 1      | 4 % 2 == 0                    |
| 4 | 8      | 4     | 4      | 3      | 8 % 4 == 0                    |

---
*/
