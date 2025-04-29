/*
Daily Question :
2962 - Count Subarrays Where Max Element Appears At Least K Times

You are given an integer array nums and a positive integer k.
Return the number of subarrays where the maximum element of nums appears at least k times in that subarray.
A subarray is a contiguous sequence of elements within an array. 

Example 1:

Input: nums = [1,3,2,3,3], k = 2
Output: 6
Explanation: The subarrays that contain the element 3 at least 2 times are: [1,3,2,3], [1,3,2,3,3], [3,2,3], [3,2,3,3], [2,3,3] and [3,3].

Example 2:

Input: nums = [1,4,2,1], k = 3
Output: 0
Explanation: No subarray contains the element 4 at least 3 times.
 

Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 106
1 <= k <= 105
*/

class Solution {
    public long countSubarrays(int[] nums, int k) {
        int n = nums.length;
        int max = 0;
        for (int num : nums) max = Math.max(max, num);

        long count = 0;
        int left = 0, freq = 0;

        for (int right = 0; right < n; right++) {
            if (nums[right] == max) freq++;

            while (freq >= k) {
                // Count all subarrays ending at right with at least k max values
                count += n - right;

                if (nums[left] == max) freq--;
                left++;
            }
        }

        return count;
    }
}

/*
Visualization of the above code
 Let's visualize the code execution of the Java solution on the example:

---

🧪 Example Input:
```
nums = [1, 3, 2, 3, 3], k = 2
```

📌 Step 1: Find max value in `nums`

```
max = 3
```

We now want to count subarrays where:
- The maximum is 3
- 3 appears at least 2 times

---

🚀 Sliding Window Execution

Initialize:
```
left = 0
freq = 0
count = 0
```

---

➤ Iteration 1: `right = 0` → nums[0] = 1

- Not equal to max → freq remains 0  
- `freq < k`, no valid subarrays
- No changes to `count`

---

➤ Iteration 2: `right = 1` → nums[1] = 3

- Equals max → `freq = 1`
- `freq < k`, no valid subarrays
- No changes to `count`

---

➤ Iteration 3: `right = 2` → nums[2] = 2

- Not equal to max → `freq = 1`
- `freq < k`, no valid subarrays
- No changes to `count`

---

➤ Iteration 4: `right = 3` → nums[3] = 3

- Equals max → `freq = 2`
- `freq >= k` → Start shrinking from `left`

Subarrays ending at `right = 3` with at least 2 `3`s:
- `[1, 3, 2, 3]` → (left = 0)
- `[3, 2, 3]` → (left = 1)
- `[2, 3]` → (left = 2)
- `[3]` → (left = 3) ← Only this has just one `3` → reject

Start shrinking `left`:

1. `nums[0] = 1` → not max → just move left → `left = 1`
2. `nums[1] = 3` → max → `freq-- = 1`, `left = 2`

→ We’ve found 2 valid subarrays for `right = 3`  
```
count += 2 → count = 2
```

---

➤ Iteration 5: `right = 4` → nums[4] = 3

- Equals max → `freq = 2`
- `freq >= k` → start shrinking from `left = 2`

Subarrays ending at `right = 4`:
- `[2, 3, 3]` (left = 2)
- `[3, 3]` (left = 3)
- `[3]` (left = 4) ← Only one `3` → not valid

Shrink:

1. `nums[2] = 2` → not max → `left = 3`
2. `nums[3] = 3` → max → `freq-- = 1`, `left = 4`

→ We’ve found **2 valid subarrays** for `right = 4`
```
count += 2 → count = 4
```

---

Now loop ends.

✅ Final Answer:
```
count = 6
```

---

✅ Valid Subarrays Found:

1. `[1, 3, 2, 3]`
2. `[1, 3, 2, 3, 3]`
3. `[3, 2, 3]`
4. `[3, 2, 3, 3]`
5. `[2, 3, 3]`
6. `[3, 3]`

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 4ms runtime which is the lowest time in this problem.
*/

class Solution {
    public long countSubarrays(int[] nums, int k) {
        int n = nums.length;
        int max = Integer.MIN_VALUE;
        
        // Step 1: Find the maximum element
        for (int i = 0; i < n; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
        }
        
        int i = 0;
        int countmax = 0;
        long result = 0;
        
        for (int j = 0; j < n; j++) {
            if (nums[j] == max) {
                countmax++;
            }
            
            while (countmax >= k) {
                result += (n - j);
                if (nums[i] == max) {
                    countmax--;
                }
                i++;
            }
        }
        
        return result;
    }
}
