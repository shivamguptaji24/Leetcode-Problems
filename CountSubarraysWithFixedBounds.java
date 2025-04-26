/*
Daily Question :
2444 - Count Subarrays With Fixed Bounds

You are given an integer array nums and two integers minK and maxK.
A fixed-bound subarray of nums is a subarray that satisfies the following conditions:
The minimum value in the subarray is equal to minK.
The maximum value in the subarray is equal to maxK.
Return the number of fixed-bound subarrays.
A subarray is a contiguous part of an array. 

Example 1:

Input: nums = [1,3,5,2,7,5], minK = 1, maxK = 5
Output: 2
Explanation: The fixed-bound subarrays are [1,3,5] and [1,3,5,2].

Example 2:

Input: nums = [1,1,1,1], minK = 1, maxK = 1
Output: 10
Explanation: Every subarray of nums is a fixed-bound subarray. There are 10 possible subarrays.
 

Constraints:

2 <= nums.length <= 105
1 <= nums[i], minK, maxK <= 106
*/

class Solution {
  public long countSubarrays(int[] nums, int minK, int maxK) {
    long ans = 0;
    int j = -1;
    int prevMinKIndex = -1;
    int prevMaxKIndex = -1;

    for (int i = 0; i < nums.length; ++i) {
      if (nums[i] < minK || nums[i] > maxK)
        j = i;
      if (nums[i] == minK)
        prevMinKIndex = i;
      if (nums[i] == maxK)
        prevMaxKIndex = i;
      // Any index k in [j + 1, min(prevMinKIndex, prevMaxKIndex)] can be the
      // start of the subarray s.t. nums[k..i] satisfies the conditions.
      ans += Math.max(0, Math.min(prevMinKIndex, prevMaxKIndex) - j);
    }

    return ans;
  }
}

/*
Visualization of the above code
 Alright, let's visualize this `countSubarrays` code very clearly step-by-step:

---

🚀 Problem
Find the number of subarrays where:
- The minimum element is exactly `minK`.
- The maximum element is exactly `maxK`.

---

🧠 Key Variables

| Variable | Purpose |
|---|---|
| `ans` | Final answer (total count of valid subarrays) |
| `j` | Last index where the element was **out of range** (less than minK or greater than maxK) |
| `prevMinKIndex` | Last index where element = minK |
| `prevMaxKIndex` | Last index where element = maxK |

---

🔁 Walkthrough Process

For each index `i` in `nums`:

- If `nums[i] < minK || nums[i] > maxK`, update `j = i` (meaning: we **cannot** start before or at `j`).
- If `nums[i] == minK`, update `prevMinKIndex = i`.
- If `nums[i] == maxK`, update `prevMaxKIndex = i`.
- Then,
  - Compute the minimum of `prevMinKIndex` and `prevMaxKIndex`.
  - Subarrays can start between `j + 1` and that minimum index.
  - Add to `ans` the number of valid starting points.

---

🧪 Example

Let's run an example manually:

```
nums = [1,3,5,2,7,5], minK = 1, maxK = 5
```

---

| i | nums[i] | Action | j | prevMinKIndex | prevMaxKIndex | Math.min(prevMinKIndex, prevMaxKIndex) - j | ans |
|---|--------|--------|---|---------------|---------------|-------------------------------------------|-----|
| 0 | 1      | 1 == minK → update prevMinKIndex = 0 | -1 | 0 | -1 | max(0, -1-(-1)) = 0 | 0 |
| 1 | 3      | nothing | -1 | 0 | -1 | max(0, -1-(-1)) = 0 | 0 |
| 2 | 5      | 5 == maxK → update prevMaxKIndex = 2 | -1 | 0 | 2 | max(0, min(0,2)-(-1)) = 1 | 1 |
| 3 | 2      | nothing | -1 | 0 | 2 | max(0, min(0,2)-(-1)) = 1 | 2 |
| 4 | 7      | 7 > maxK → j=4 | 4 | 0 | 2 | max(0, min(0,2)-4) = 0 | 2 |
| 5 | 5      | 5 == maxK → prevMaxKIndex = 5 | 4 | 0 | 5 | max(0, min(0,5)-4) = 0 | 2 |

✅ Final answer: `2`

---

🔥 Visual Flow

```
Loop through nums:
   1. Check if nums[i] is out of bounds => update j.
   2. If nums[i] == minK => save index.
   3. If nums[i] == maxK => save index.
   4. Find minimum index of minK and maxK seen so far.
   5. Valid subarrays start from (j+1) up to min(prevMinKIndex, prevMaxKIndex).
```

> Think of `j` as a "barrier" you cannot cross, because before `j` the values are invalid.

---

🧠 Why does it work?

Because to have a valid subarray, we must:
- Have at least one minK and one maxK inside.
- All numbers between them should be within [minK, maxK].

That's why:
- `j` tracks last invalid.
- `prevMinKIndex` and `prevMaxKIndex` track where we last saw valid min/max values.

---

📈 Intuition Table:

| Condition | What Happens |
|:---|:---|
| nums[i] < minK or nums[i] > maxK | reset `j = i` |
| nums[i] == minK | update `prevMinKIndex = i` |
| nums[i] == maxK | update `prevMaxKIndex = i` |
| count of subarrays ending at i | `Math.max(0, Math.min(prevMinKIndex, prevMaxKIndex) - j)` |

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 4ms runtime which is the lowest time in this problem.
*/

class Solution {
    public long countSubarrays(int[] nums, int minK, int maxK) {
        int left = -1, min = -1, max = -1, n = nums.length;
        long count = 0l;
        for (int i = 0; i < n; i++) 
            if (nums[i] < minK || nums[i] > maxK) 
                left = min = max = i;
            else {
                min = nums[i] == minK ? i : min;
                max = nums[i] == maxK ? i : max;
                count += (min < max ? min : max) - left;
            } 
        return count;
    }
}

/*
Visualization of the above code
