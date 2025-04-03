/*
Daily Question :
2874 - Maximum Value Of An Ordered Triplet II

You are given a 0-indexed integer array nums.
Return the maximum value over all triplets of indices (i, j, k) such that i < j < k. If all such triplets have a negative value, return 0.
The value of a triplet of indices (i, j, k) is equal to (nums[i] - nums[j]) * nums[k]. 

Example 1:

Input: nums = [12,6,1,2,7]
Output: 77
Explanation: The value of the triplet (0, 2, 4) is (nums[0] - nums[2]) * nums[4] = 77.
It can be shown that there are no ordered triplets of indices with a value greater than 77. 

Example 2:

Input: nums = [1,10,3,4,19]
Output: 133
Explanation: The value of the triplet (1, 2, 4) is (nums[1] - nums[2]) * nums[4] = 133.
It can be shown that there are no ordered triplets of indices with a value greater than 133.

Example 3:

Input: nums = [1,2,3]
Output: 0
Explanation: The only ordered triplet of indices (0, 1, 2) has a negative value of (nums[0] - nums[1]) * nums[2] = -3. Hence, the answer would be 0.
 

Constraints:

3 <= nums.length <= 105
1 <= nums[i] <= 106
*/

class Solution {
    public long maximumTripletValue(int[] nums) {
        int n = nums.length;
        long maxValue = 0;
        int maxLeft = nums[0]; // Maximum nums[i] seen so far
        long maxDiff = Long.MIN_VALUE; // Best (nums[i] - nums[j]) seen so far

        for (int j = 1; j < n - 1; j++) {
            maxDiff = Math.max(maxDiff, (long) maxLeft - nums[j]); // Best diff seen so far
            maxValue = Math.max(maxValue, maxDiff * nums[j + 1]); // Best triplet value
            maxLeft = Math.max(maxLeft, nums[j]); // Update maxLeft
        }

        return maxValue;
    }
}

/*
Visualization of the above code
 Let's visualize the optimized code execution for `nums = [12,6,1,2,7]`.

---

Understanding the Code Execution
The key idea is to track the best difference (`maxDiff = nums[i] - nums[j]`) and multiply it by `nums[k]` to get the best triplet value.

We maintain:
- `maxLeft`: Maximum value seen so far for `nums[i]`
- `maxDiff`: Maximum `nums[i] - nums[j]` so far
- `maxValue`: Maximum triplet value seen so far

---

Example Input: `nums = [12,6,1,2,7]`
Step-by-Step Execution:

Initialization
- `maxLeft = nums[0] = 12`
- `maxDiff = Long.MIN_VALUE`
- `maxValue = 0`

Loop through `j`
| `j` | `nums[j]` | `maxLeft` (so far) | `maxDiff = max(maxDiff, maxLeft - nums[j])` | `maxValue = max(maxValue, maxDiff * nums[j+1])` |
|----|------|------|------|------|
| **1** | 6 | 12 | `max(-∞, 12 - 6) = 6` | `max(0, 6 * 1) = 6` |
| **2** | 1 | 12 | `max(6, 12 - 1) = 11` | `max(6, 11 * 2) = 22` |
| **3** | 2 | 12 | `max(11, 12 - 2) = 10` | `max(22, 10 * 7) = 77` |

Final Answer
- The maximum triplet value is `77`.

---

Visualization
```
Iteration 1 (j=1):
    nums[j] = 6
    maxLeft = 12
    maxDiff = max(-∞, 12 - 6) = 6
    maxValue = max(0, 6 * 1) = 6

Iteration 2 (j=2):
    nums[j] = 1
    maxLeft = 12
    maxDiff = max(6, 12 - 1) = 11
    maxValue = max(6, 11 * 2) = 22

Iteration 3 (j=3):
    nums[j] = 2
    maxLeft = 12
    maxDiff = max(11, 12 - 2) = 10
    maxValue = max(22, 10 * 7) = 77
```

---

Final Output
`77` ✅

---

Time Complexity: `O(n)`
- Single loop iterating once over the array.

Space Complexity: `O(1)`
- Uses only a few variables, no extra arrays.
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 1ms runtime which is the lowest time in this problem
*/
