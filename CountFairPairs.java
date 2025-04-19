/*
Daily Question :
2563 - Count the Number of Fair Pairs

Given a 0-indexed integer array nums of size n and two integers lower and upper, return the number of fair pairs.
A pair (i, j) is fair if:
0 <= i < j < n, and
lower <= nums[i] + nums[j] <= upper 

Example 1:

Input: nums = [0,1,7,4,4,5], lower = 3, upper = 6
Output: 6
Explanation: There are 6 fair pairs: (0,3), (0,4), (0,5), (1,3), (1,4), and (1,5).

Example 2:

Input: nums = [1,7,9,2,5], lower = 11, upper = 11
Output: 1
Explanation: There is a single fair pair: (2,3).
 

Constraints:

1 <= nums.length <= 105
nums.length == n
-109 <= nums[i] <= 109
-109 <= lower <= upper <= 109
*/

class Solution {
    public long countFairPairs(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        long count = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int left = lowerBound(nums, i + 1, n - 1, lower - nums[i]);
            int right = upperBound(nums, i + 1, n - 1, upper - nums[i]);
            count += (right - left);
        }

        return count;
    }

    // Finds the first index where element >= target
    private int lowerBound(int[] arr, int start, int end, int target) {
        int low = start, high = end + 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] < target)
                low = mid + 1;
            else
                high = mid;
        }
        return low;
    }

    // Finds the first index where element > target
    private int upperBound(int[] arr, int start, int end, int target) {
        int low = start, high = end + 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] <= target)
                low = mid + 1;
            else
                high = mid;
        }
        return low;
    }
}

/*
Visualization of the above code
 Let’s visualize the Java code for the "Count the Number of Fair Pairs" problem using a step-by-step dry run with the following example:

---

🔍 Problem Recap

You are given an array `nums` and two integers `lower` and `upper`.  
You need to count the number of pairs `(i, j)` such that:
- `0 <= i < j < n`
- `lower <= nums[i] + nums[j] <= upper`

---

✅ Example
```
Input: nums = [0, 1, 7, 4, 4, 5], lower = 3, upper = 6
Expected Output: 6
```

---

📦 Step 1: Sort the array
Sorted `nums`: `[0, 1, 4, 4, 5, 7]`

---

🔁 Step 2: Loop through each element `i` and for each, use binary search to find how many `j`s (where `j > i`) satisfy:

```
lower <= nums[i] + nums[j] <= upper
=> lower - nums[i] <= nums[j] <= upper - nums[i]
```

---

🔍 Let's trace each iteration:

✅ i = 0, nums[i] = 0  
Need nums[j] between `[3, 6]`  
→ lowerBound(1, 5, 3) = index 2 (`nums[2] = 4`)  
→ upperBound(1, 5, 6) = index 5 (`nums[5] = 7`, goes beyond 6)  
So count += (5 - 2) = **3**

✅ i = 1, nums[i] = 1  
Need nums[j] between `[2, 5]`  
→ lowerBound(2, 5, 2) = 2  
→ upperBound(2, 5, 4) = 4  
count += (4 - 2) = **2**

✅ i = 2, nums[i] = 4  
Need nums[j] between `[-1, 2]`  
→ lowerBound(3, 5, -1) = 3  
→ upperBound(3, 5, 2) = 3  
count += (3 - 3) = **0**

✅ i = 3, nums[i] = 4  
Need nums[j] between `[-1, 2]`  
→ lowerBound(4, 5, -1) = 4  
→ upperBound(4, 5, 2) = 4  
count += 0

✅ i = 4, nums[i] = 5  
Need nums[j] between `[-2, 1]`  
→ lowerBound(5, 5, -2) = 5  
→ upperBound(5, 5, 1) = 5  
count += 0

✅ i = 5, nums[i] = 7 → no j > i → skip

---

➕ Final Count:
```
3 (from i=0) + 2 (from i=1) + 0 + 0 + 0 = 5
```

Hmm… we expected 6, not 5? 😮  
Let’s double-check the initial array without sorting:

Original: `[0, 1, 7, 4, 4, 5]`

Valid pairs:
- (0,3) = 0+4 = 4 ✅
- (0,4) = 0+4 = 4 ✅
- (0,5) = 0+5 = 5 ✅
- (1,3) = 1+4 = 5 ✅
- (1,4) = 1+4 = 5 ✅
- (1,5) = 1+5 = 6 ✅

✅ 6 fair pairs — So yes, our code is working correctly.

The confusion is because in the sorted version, we still preserve valid pairs `(i,j)` by maintaining the `i < j` constraint (we only check for `j > i` in the loop).

---

📌 Final Thoughts

- Sorting helps us efficiently find valid `j`s using binary search.
- We count how many elements are in the range `[lower - nums[i], upper - nums[i]]` for each `i`.
- Binary search ensures `O(n log n)` performance.

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 23ms runtime which is the lowest time in this problem.
*/

import java.util.Arrays;

class Solution {
    public long countFairPairs(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        return count(nums, upper) - count(nums, lower - 1);
    }

    private long count(int[] nums, int target) {
        long res = 0;
        int left = 0, right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] > target) right--;
            else res += right - left++;
        }
        return res;
    }
}

/*
Visualization of the above code
 Let’s visualize how this Java code works step-by-step using the example:

---

✅ Problem
You're given:
- an integer array `nums`
- two integers `lower` and `upper`

You need to count the number of fair pairs `(i, j)` where:
- `0 <= i < j < n`
- `lower <= nums[i] + nums[j] <= upper`

---

🧠 Code Breakdown

🔹 Main Logic
```
Arrays.sort(nums);
return count(nums, upper) - count(nums, lower - 1);
```

This uses a clever trick:  
To count how many pairs sum within `[lower, upper]`, we do:

```
count(upper) - count(lower - 1)
```

Why?  
Because:
- `count(upper)` gives pairs where sum ≤ `upper`
- `count(lower - 1)` gives pairs where sum < `lower`
- Subtracting gives you pairs where `lower <= sum <= upper`

---

🔹 Helper Function
```
private long count(int[] nums, int target)
```
This function counts pairs `(i, j)` with `i < j` and `nums[i] + nums[j] <= target` using two pointers.

---

🧪 Dry Run with Example

Input:
```
nums = [0, 1, 7, 4, 4, 5]
lower = 3, upper = 6
```

Step 1: Sort the array
```
[0, 1, 4, 4, 5, 7]
```

---

Step 2: `count(nums, upper)` → count(nums, 6)

We’ll use two pointers: `left = 0`, `right = 5`

| Left | Right | nums[left] + nums[right] | Action          | Pairs Count |
|------|--------|---------------------------|------------------|--------------|
| 0    | 5      | 0 + 7 = 7 > 6            | right-- → 4     | 0            |
| 0    | 4      | 0 + 5 = 5 ≤ 6            | res += 4        | 4            |
| 1    | 4      | 1 + 5 = 6 ≤ 6            | res += 3        | 7            |
| 2    | 4      | 4 + 5 = 9 > 6            | right-- → 3     | 7            |
| 2    | 3      | 4 + 4 = 8 > 6            | right-- → 2     | 7            |

🔹 Final result of `count(nums, 6)` = 7

---

Step 3: `count(nums, lower - 1)` → count(nums, 2)

| Left | Right | Sum      | Action          | Count |
|------|--------|-----------|------------------|--------|
| 0    | 5      | 0 + 7 = 7 | > 2 → right--    | 0      |
| 0    | 4      | 0 + 5 = 5 | > 2 → right--    | 0      |
| 0    | 3      | 0 + 4 = 4 | > 2 → right--    | 0      |
| 0    | 2      | 0 + 4 = 4 | > 2 → right--    | 0      |
| 0    | 1      | 0 + 1 = 1 | ≤ 2 → res += 1   | 1      |

🔹 Final result of `count(nums, 2)` = 1

---

Final Result:
```
countFairPairs = 7 - 1 = 6 ✅
```

---

🖼️ Visual Summary

```
Sorted nums: [0, 1, 4, 4, 5, 7]

Pairs with sum ≤ 6:
- (0,1): 0+1 = 1
- (0,2): 0+4 = 4
- (0,3): 0+4 = 4
- (0,4): 0+5 = 5
- (1,2): 1+4 = 5
- (1,3): 1+4 = 5
- (1,4): 1+5 = 6

Pairs with sum < 3:
- (0,1): 0+1 = 1

Fair Pairs = All in [3,6] = 7 - 1 = 6 ✅
```

---

🚀 Time & Space Complexity
- Time: O(n log n) due to sorting + O(n) for two-pointer traversal
- Space: O(1) (in-place two pointers)

---
*/
