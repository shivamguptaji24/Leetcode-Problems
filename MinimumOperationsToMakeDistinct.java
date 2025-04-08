/*
Daily Question :
3396 - Minimum Number of Operations to Make Elements in Array Distinct

You are given an integer array nums. You need to ensure that the elements in the array are distinct. To achieve this, you can perform the following operation any number of times:
Remove 3 elements from the beginning of the array. If the array has fewer than 3 elements, remove all remaining elements.
Note that an empty array is considered to have distinct elements. Return the minimum number of operations needed to make the elements in the array distinct.

Example 1:

Input: nums = [1,2,3,4,2,3,3,5,7]

Output: 2

Explanation:

In the first operation, the first 3 elements are removed, resulting in the array [4, 2, 3, 3, 5, 7].
In the second operation, the next 3 elements are removed, resulting in the array [3, 5, 7], which has distinct elements.
Therefore, the answer is 2.

Example 2:

Input: nums = [4,5,6,4,4]

Output: 2

Explanation:

In the first operation, the first 3 elements are removed, resulting in the array [4, 4].
In the second operation, all remaining elements are removed, resulting in an empty array.
Therefore, the answer is 2.

Example 3:

Input: nums = [6,7,8,9]

Output: 0

Explanation:

The array already contains distinct elements. Therefore, the answer is 0.

 

Constraints:

1 <= nums.length <= 100
1 <= nums[i] <= 100
*/

class Solution {
    public int minimumOperations(int[] nums) {
        int ops = 0;
        int i = 0;
        while (i < nums.length) {
            // Check if remaining array from index i has all unique elements
            Set<Integer> seen = new HashSet<>();
            boolean allUnique = true;

            for (int j = i; j < nums.length; j++) {
                if (seen.contains(nums[j])) {
                    allUnique = false;
                    break;
                }
                seen.add(nums[j]);
            }

            if (allUnique) {
                break; // Already distinct, no more operations needed
            }

            // Remove 3 elements from front (by moving i forward)
            i += 3;
            ops++;
        }

        return ops;
    }
}

/*
Visualization of the above code
 Let's break down and visualize how your Java code for `minimumOperations` works step by step, using an example input:

---

✅ Problem Statement Recap
You are given an array. You can remove 3 elements from the front in one operation. You must make the remaining array contain only unique elements. Return the minimum number of operations required.

---

🔍 Code Overview
```
public int minimumOperations(int[] nums) {
    int ops = 0;           // Count of operations performed
    int i = 0;             // Current starting index

    while (i < nums.length) {
        Set<Integer> seen = new HashSet<>();
        boolean allUnique = true;

        for (int j = i; j < nums.length; j++) {
            if (seen.contains(nums[j])) {
                allUnique = false;
                break;
            }
            seen.add(nums[j]);
        }

        if (allUnique) break;

        i += 3;    // Simulates removing 3 elements from the front
        ops++;
    }

    return ops;
}
```

---

🔁 Step-by-Step Visualization
Let’s take this input:

Input:
```
nums = [1, 2, 3, 4, 2, 3, 3, 5, 7]
```

---

Initial state:
- `i = 0`, `ops = 0`
- Check if elements from index `0 → end` are unique:
  - `1 2 3 4` ✅
  - `2` ❌ Already exists ⇒ **duplicate found**
- → Not unique → `ops = 1`, move `i = 3`

---

After 1st operation (i = 3):
- Remaining: `[4, 2, 3, 3, 5, 7]`
- Check for duplicates:
  - `4 2 3` ✅
  - `3` ❌ Already exists
- → Not unique → `ops = 2`, move `i = 6`

---

After 2nd operation (i = 6):
- Remaining: `[3, 5, 7]`
- All unique ✅ → Done

---

✅ Output:
```
return 2;
```

---

📊 Time & Space Complexity
- Time Complexity: `O(n²)` worst-case, because for each removal, it may scan the entire suffix for duplicates.
- Space Complexity: `O(n)` due to the `HashSet`.

---

🎯 Final Notes
- The code is well-written, efficient for `n ≤ 100`.
- Handles edge cases (e.g., already distinct, fully duplicate).
*/
