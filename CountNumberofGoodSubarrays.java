/*
Daily Question :
2537 - Count the Number of Good Subarrays

Given an integer array nums and an integer k, return the number of good subarrays of nums.
A subarray arr is good if there are at least k pairs of indices (i, j) such that i < j and arr[i] == arr[j].
A subarray is a contiguous non-empty sequence of elements within an array. 

Example 1:

Input: nums = [1,1,1,1,1], k = 10
Output: 1
Explanation: The only good subarray is the array nums itself.

Example 2:

Input: nums = [3,1,4,3,2,2,4], k = 2
Output: 4
Explanation: There are 4 different good subarrays:
- [3,1,4,3,2,2] that has 2 pairs.
- [3,1,4,3,2,2,4] that has 3 pairs.
- [1,4,3,2,2,4] that has 2 pairs.
- [4,3,2,2,4] that has 2 pairs.
 

Constraints:

1 <= nums.length <= 105
1 <= nums[i], k <= 109
*/

class Solution {
    public long countGood(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        long res = 0, pairs = 0;
        int n = nums.length, left = 0;

        for (int right = 0; right < n; right++) {
            int num = nums[right];
            int count = freq.getOrDefault(num, 0);
            pairs += count; // Adding num forms `count` new pairs
            freq.put(num, count + 1);

            // Move left to shrink window until pairs < k
            while (pairs >= k) {
                res += (n - right); // All subarrays [left..right], [left..right+1], .. are valid
                int leftNum = nums[left];
                int leftFreq = freq.get(leftNum);
                pairs -= (leftFreq - 1); // Removing leftNum breaks (leftFreq - 1) pairs
                freq.put(leftNum, leftFreq - 1);
                left++;
            }
        }
        return res;
    }
}

/*
Visualization of the above code
 Let's walk through the visualization of the optimized sliding window algorithm for the problem:

---

🔢 Input:

```
nums = [3, 1, 4, 3, 2, 2, 4]
k = 2
```

We want to count the number of subarrays with at least 2 equal pairs (i, j) such that `i < j` and `nums[i] == nums[j]`.

---

📊 Key Variables:
- `freq`: keeps count of numbers inside the current window.
- `pairs`: number of pairs formed in current window.
- `left`, `right`: sliding window pointers.
- `res`: final result (number of good subarrays).

---

🪟 Sliding Window Visualization Step-by-Step:

We'll simulate how the window grows and shrinks:

---

✅ Step 1: `right = 0` → `nums[0] = 3`

- `freq = {3: 1}`
- `pairs = 0` (only one 3 so no pair)
- `pairs < k` → don’t count subarray.

---

✅ Step 2: `right = 1` → `nums[1] = 1`

- `freq = {3:1, 1:1}`
- `pairs = 0` (no duplicate yet)

---

✅ Step 3: `right = 2` → `nums[2] = 4`

- `freq = {3:1, 1:1, 4:1}`
- `pairs = 0` (still no duplicates)

---

✅ Step 4: `right = 3` → `nums[3] = 3`

- `freq[3]` before = 1 → adding one more makes 1 new pair.
- `pairs = 1`
- `freq = {3:2, 1:1, 4:1}`

Still `pairs < k`, so continue.

---

✅ Step 5: `right = 4` → `nums[4] = 2`

- `freq[2]` = 0 → adding → no new pairs
- `pairs = 1`
- `freq = {3:2, 1:1, 4:1, 2:1}`

---

✅ Step 6: `right = 5` → `nums[5] = 2`

- `freq[2] = 1` → add → 1 more pair (2,2)
- `pairs = 2 ✅`
- Now we found a valid subarray from `left = 0` to `right = 5`

We now count all subarrays starting at `left = 0` and ending from `right = 5` to end:
- [0…5] (length = 6)
- [0…6] (next)

So add `(n - right) = 7 - 5 = 2` to result  
res = 2

🧹 Now shrink window from left to remove excess pairs:

- Remove `nums[0] = 3`
- `freq[3] = 2 → 1`, removing 1 → 1 fewer pair
- `pairs = 1`
- `left = 1`

---

✅ Step 7: `right = 6` → `nums[6] = 4`

- `freq[4] = 1 → 2` → add 1 new pair
- `pairs = 2 ✅`
- Valid window → count subarrays from `left = 1` to `right = 6`

Add `(n - right) = 1`  
res = 3

🧹 Remove `nums[1] = 1` → `freq = 0` → no effect on pairs  
res = 4

🧹 Next remove `nums[2] = 4` → breaking pair → `pairs = 1`  
Window invalid → done

---

✅ Final Answer: `res = 4`

---

📈 Visual Summary Table:

| Right | Window                | Pairs | Valid? | Subarrays Added |
|-------|------------------------|-------|--------|------------------|
| 0     | [3]                   | 0     | ❌     | 0                |
| 1     | [3,1]                 | 0     | ❌     | 0                |
| 2     | [3,1,4]              | 0     | ❌     | 0                |
| 3     | [3,1,4,3]           | 1     | ❌     | 0                |
| 4     | [3,1,4,3,2]        | 1     | ❌     | 0                |
| 5     | [3,1,4,3,2,2]     | 2     | ✅     | 2                |
| 6     | [1,4,3,2,2,4]     | 2     | ✅     | 2                |

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 30ms runtime which is the lowest time in this problem.
*/

public class Solution {
    public long countGood(int[] nums, int k) {
        if (nums.length < 2) {
            return 0L;
        }
        Map<Integer, Integer> countMap = new HashMap<>(nums.length, 0.99f);
        long goodSubArrays = 0L;
        long current = 0L;
        int left = 0;
        int right = -1;
        while (left < nums.length) {
            if (current < k) {
                if (++right == nums.length) {
                    break;
                }
          
                Integer num = nums[right];
                Integer count = countMap.get(num);
                if (count == null) {
                    count = 1;
                } else {
                    current += count;
                    if (current >= k) {
                        goodSubArrays += nums.length - right;
                    }
                    count = count + 1;
                }
                countMap.put(num, count);
            } else {
                Integer num = nums[left++];
                int count = countMap.get(num) - 1;
                if (count > 0) {
                    countMap.put(num, count);
                    current -= count;
                } else {
                    countMap.remove(num);
                }
                if (current >= k) {
                    goodSubArrays += nums.length - right;
                }
            }
        }
        return goodSubArrays;
    }
}

/*
Visualization of the above code
 Let's visualize this sliding window solution step-by-step for the problem:

---

🔍 Problem Recap
You are given:
- An array `nums`
- An integer `k`

You must count the number of subarrays where there are **at least `k` equal pairs** `(i, j)` such that `i < j` and `nums[i] == nums[j]`.

---

👨‍💻 Code Summary

```
Map<Integer, Integer> countMap
```
Stores the frequency of elements in the current window.

```
long current
```
Keeps track of how many valid pairs are currently in the window.

```
long goodSubArrays
```
Counts all valid subarrays found so far.

```
int left, right
```
Used to control the sliding window.

---

⚙️ Working Logic (Step-by-Step)

Let’s take a sample input:
```
nums = [3, 1, 4, 3, 2, 2, 4]
k = 2
```

We'll simulate the loop:

---

📦 Initial State

- `left = 0`, `right = -1`
- `current = 0`, `goodSubArrays = 0`
- `countMap = {}`

---

➕ Expand `right` until we find `k` pairs

➡️ `right = 0` → nums[0] = 3

- 3 is new → `count = 1`, `current = 0`  
- `countMap = {3:1}`

➡️ `right = 1` → nums[1] = 1

- 1 is new → `count = 1`, `current = 0`  
- `countMap = {3:1, 1:1}`

➡️ `right = 2` → nums[2] = 4

- 4 is new → `count = 1`, `current = 0`  
- `countMap = {3:1, 1:1, 4:1}`

➡️ `right = 3` → nums[3] = 3

- 3 already exists → old count = 1 → add 1 pair
- `current = 1`
- `countMap = {3:2, 1:1, 4:1}`

➡️ `right = 4` → nums[4] = 2

- 2 is new → `count = 1`, `current = 1`  
- `countMap = {3:2, 1:1, 4:1, 2:1}`

➡️ `right = 5` → nums[5] = 2

- 2 already exists → old count = 1 → add 1 pair
- `current = 2 ✅`
- Subarray [0…5] is valid

✅ Add `nums.length - right = 7 - 5 = 2` to result  
`goodSubArrays = 2`

---

➖ Shrink `left` while maintaining `current >= k`

➡️ `left = 0` → remove 3

- `count = 2 → 1` → remove 1 pair  
- `current = 1 ❌`  
- `countMap = {3:1, 1:1, 4:1, 2:2}`

---

➕ Expand `right = 6` → nums[6] = 4

- 4 already exists → old count = 1 → add 1 pair  
- `current = 2 ✅`
- Subarray [1…6] is valid

✅ Add `nums.length - right = 7 - 6 = 1` to result  
`goodSubArrays = 3`

---

➖ Shrink `left = 1` → remove 1

- `count = 1 → 0` → no pair removed  
- `countMap = {3:1, 4:2, 2:2}`
- `current = 2 ✅`
✅ Add `1` more → `goodSubArrays = 4`

---

➖ Shrink `left = 2` → remove 4

- `count = 2 → 1` → remove 1 pair  
- `current = 1 ❌`

---

🚫 Can't expand anymore → loop ends.

---

✅ Final Result: `goodSubArrays = 4`

---

📊 Visual Summary Table

| Window      | Right | Current Pairs | Valid? | Subarrays Added |
|-------------|-------|----------------|--------|------------------|
| [3,1,4,3,2,2] | 5     | 2              | ✅     | 2                |
| [1,4,3,2,2,4] | 6     | 2              | ✅     | 2                |

---

🎨 Diagram (Sliding Window)

```
[3, 1, 4, 3, 2, 2, 4]
 ^                 ^  → left = 0, right = 6
Valid subarrays = [3,1,4,3,2,2]
                  [3,1,4,3,2,2,4]

Shift window:
[1, 4, 3, 2, 2, 4]
 ^                 ^  → valid again

Shift:
[4, 3, 2, 2, 4]
 → invalid after removing pair
```

---
*/
