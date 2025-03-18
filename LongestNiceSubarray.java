/*
Daily Question :
2401 - Longest Nice Subarray

You are given an array nums consisting of positive integers.
We call a subarray of nums nice if the bitwise AND of every pair of elements that are in different positions in the subarray is equal to 0.
Return the length of the longest nice subarray.
A subarray is a contiguous part of an array.
Note that subarrays of length 1 are always considered nice. 

Example 1:

Input: nums = [1,3,8,48,10]
Output: 3
Explanation: The longest nice subarray is [3,8,48]. This subarray satisfies the conditions:
- 3 AND 8 = 0.
- 3 AND 48 = 0.
- 8 AND 48 = 0.
It can be proven that no longer nice subarray can be obtained, so we return 3.

Example 2:

Input: nums = [3,1,5,11,13]
Output: 1
Explanation: The length of the longest nice subarray is 1. Any subarray of length 1 can be chosen.
 

Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 109
*/

class Solution {
    public int longestNiceSubarray(int[] nums) {
        int left = 0, maxLen = 0, window = 0;

        for(int right = 0; right < nums.length; right++) {
            // Shrink window if AND condition fails
            while((window & nums[right]) != 0) {
                window ^= nums[left];  // Remove nums[left] from window
                left++;
            }

            // Add nums[right] to the window
            window |= nums[right];

            // Update max length of the valid window
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}

/*
Visualization of the above code
  Let's visualize the code step-by-step with a clear breakdown of each step, including the movement of pointers (`left` and `right`), the `window` value, and the condition checks.

---

Input Array: `[1, 3, 8, 48, 10]`

---

Initial State
```
nums = [1, 3, 8, 48, 10]
left = 0
right = 0
window = 0
maxLen = 0
```

---

Step-by-Step Execution

| Step | `right` | `nums[right]` | `window` Before | Condition (`window & nums[right]`) | Action                       | `window` After | `maxLen` |
|------|----------|----------------|------------------|------------------------------------|------------------------------|----------------|-----------|
| 1    | 0        | 1              | 0                  | 0                                  | Add `1` to window            | `1`             | 1         |
| 2    | 1        | 3              | 1                  | 0                                  | Add `3` to window            | `3`             | 2         |
| 3    | 2        | 8              | 3                  | 0                                  | Add `8` to window            | `11`            | 3         |
| 4    | 3        | 48             | 11                 | 0                                  | Add `48` to window           | `59`            | 4         |
| 5    | 4        | 10             | 59                 | ≠ 0 (Violation)                | Shrink window (remove `1`)    | `58`            | 4         |
|      |          |                |                    | ≠ 0 (Violation continues)      | Shrink window (remove `3`)    | `56`            | 4         |
|      |          |                |                    | ≠ 0 (Violation continues)      | Shrink window (remove `8`)    | `48`            | 3         |

✅ Final Output: `3`

---

Visualization
```
[1]                 → window = 1 → maxLen = 1
[1, 3]              → window = 3 → maxLen = 2
[1, 3, 8]           → window = 11 → maxLen = 3
[1, 3, 8, 48]       → window = 59 → maxLen = 4
[3, 8, 48]          → window = 58 → maxLen = 4
[8, 48]             → window = 56 → maxLen = 4
[48, 10]            → window = 48 → maxLen = 3
```

---

Key Observations
✅ Each time the AND condition is violated, the `left` pointer moves forward to shrink the window.  
✅ The `window` variable efficiently tracks the bitwise OR of elements in the current valid window.  
✅ Maximum window length encountered = 3.

---
*/
