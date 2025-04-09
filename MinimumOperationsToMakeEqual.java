/*
Daily Question :
3375 - Minimum Operations to Make Array Values Equal to K

You are given an integer array nums and an integer k.
An integer h is called valid if all values in the array that are strictly greater than h are identical.
For example, if nums = [10, 8, 10, 8], a valid integer is h = 9 because all nums[i] > 9 are equal to 10, but 5 is not a valid integer.
You are allowed to perform the following operation on nums:
Select an integer h that is valid for the current values in nums.
For each index i where nums[i] > h, set nums[i] to h.
Return the minimum number of operations required to make every element in nums equal to k. If it is impossible to make all elements equal to k, return -1.

Example 1:

Input: nums = [5,2,5,4,5], k = 2

Output: 2

Explanation:

The operations can be performed in order using valid integers 4 and then 2.

Example 2:

Input: nums = [2,1,2], k = 2

Output: -1

Explanation:

It is impossible to make all the values equal to 2.

Example 3:

Input: nums = [9,7,5,3], k = 1

Output: 4

Explanation:

The operations can be performed using valid integers in the order 7, 5, 3, and 1.

 

Constraints:

1 <= nums.length <= 100 
1 <= nums[i] <= 100
1 <= k <= 100
*/

class Solution {
    public int minOperations(int[] nums, int k) {
        for (int num : nums) {
            if (num < k) return -1; // Cannot increase values
        }

        Set<Integer> greaterThanK = new HashSet<>();
        for (int num : nums) {
            if (num > k) greaterThanK.add(num);
        }

        int ops = 0;
        while (!greaterThanK.isEmpty()) {
            int max = -1;
            // Find max in the set (could also use TreeSet for sorted order)
            for (int val : greaterThanK) {
                max = Math.max(max, val);
            }

            // Remove the max (reduce all nums > max to max - 1 or next largest)
            greaterThanK.remove(max);
            ops++;
        }

        return ops;
    }
}

/*
Visualization of the above code
 Sure Shivam! Let’s visualize this code step-by-step using an example and explain what's happening.

---

✅ Example
```
Input:
nums = [5, 2, 5, 4, 5]
k = 2

Expected Output: 2
```

---

🔍 Step-by-step Execution

Step 1: Check if any `nums[i] < k`

```
for (int num : nums) {
    if (num < k) return -1;
}
```

- `nums = [5, 2, 5, 4, 5]`
- `k = 2`
- All nums ≥ 2 ✅ → continue

---

Step 2: Build a Set of numbers greater than `k`

```
Set<Integer> greaterThanK = new HashSet<>();
for (int num : nums) {
    if (num > k) greaterThanK.add(num);
}
```

- Numbers > 2 → `[5, 4]`
- Set → `{4, 5}`

---

Step 3: Count operations to remove highest values until set is empty

```
int ops = 0;
while (!greaterThanK.isEmpty()) {
    int max = -1;
    for (int val : greaterThanK) {
        max = Math.max(max, val);
    }
    greaterThanK.remove(max);
    ops++;
}
```

Let’s simulate this loop:

🌀 First Iteration:
- `greaterThanK = {4, 5}`
- `max = 5` → remove 5
- `ops = 1`

🌀 Second Iteration:
- `greaterThanK = {4}`
- `max = 4` → remove 4
- `ops = 2`

🌀 Third Iteration:
- `greaterThanK = {}` → loop ends

---

✅ Final Result:

```
return ops; // 2
```

---

🧠 Final Thoughts

- This approach is clean and works well when the number of unique values > k is small.
- Slight optimization: Instead of manually finding max every time, you could use a `TreeSet` or `PriorityQueue`.

---
*/
