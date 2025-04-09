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

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 5ms runtime which is the time in this problem
*/

class Solution {
    public int minOperations(int[] nums, int k) {
        for (int num : nums) {
            if (num < k) return -1; // Cannot increase any number to reach k
        }

        Set<Integer> seen = new HashSet<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        // Collect unique numbers > k
        for (int num : nums) {
            if (num > k && seen.add(num)) {
                maxHeap.offer(num);
            }
        }

        int ops = 0;
        while (!maxHeap.isEmpty()) {
            int curr = maxHeap.poll();
            ops++;
        }

        return ops;
    }
}

/*
Visualization of the above code
 Sure Shivam! Let’s visualize your code step by step with an example and understand what’s going on. This version uses a `PriorityQueue` as a max-heap to always pick the largest element greater than `k` first.

---

✅ Example Input
```
nums = [5, 2, 5, 4, 5]
k = 2
```

Expected Output: `2`

---

🔍 Step-by-step Execution

1️⃣ Check if any element is less than `k`
```
for (int num : nums) {
    if (num < k) return -1;
}
```
- `nums = [5, 2, 5, 4, 5]`
- `k = 2`
- All elements are `>= 2` → ✅ continue

---

2️⃣ Use a HashSet to collect unique values > k, and a max-heap to process in descending order

```
Set<Integer> seen = new HashSet<>();
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

for (int num : nums) {
    if (num > k && seen.add(num)) {
        maxHeap.offer(num);
    }
}
```

- Process `nums`:
  - 5 > 2 → added to `seen` and `maxHeap`
  - 2 == 2 → skip
  - 5 again → already in `seen`, skip
  - 4 > 2 → added to `seen` and `maxHeap`
  - 5 again → already in `seen`, skip

👉 Now:
- `seen = {4, 5}`
- `maxHeap = [5, 4]`

---

3️⃣ Count operations needed to reduce each unique number > k

```
int ops = 0;
while (!maxHeap.isEmpty()) {
    int curr = maxHeap.poll(); // Remove the largest element
    ops++;
}
```

Loop:
- `maxHeap = [5, 4]`
  - `poll()` → `5` → `ops = 1`
- `maxHeap = [4]`
  - `poll()` → `4` → `ops = 2`
- `maxHeap = []` → end

---

✅ Final Output
```
return ops; // 2
```

---

🧠 Summary

This code:
- Efficiently handles finding unique elements > `k` ✔️
- Uses a max-heap to simulate the idea of reducing the largest values first
- Ensures no duplicates are counted using `seen` ✔️
- Returns `-1` early if making all elements `k` is impossible ❌

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 1ms runtime which is the lowest time in this problem
*/
