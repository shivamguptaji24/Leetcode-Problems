/*
Daily Question :
2780 - Minimum Index Of A Valid Split

An element x of an integer array arr of length m is dominant if more than half the elements of arr have a value of x.
You are given a 0-indexed integer array nums of length n with one dominant element.
You can split nums at an index i into two arrays nums[0, ..., i] and nums[i + 1, ..., n - 1], but the split is only valid if:

0 <= i < n - 1
nums[0, ..., i], and nums[i + 1, ..., n - 1] have the same dominant element.
Here, nums[i, ..., j] denotes the subarray of nums starting at index i and ending at index j, both ends being inclusive. Particularly, if j < i then nums[i, ..., j] denotes an empty subarray.
Return the minimum index of a valid split. If no valid split exists, return -1. 

Example 1:

Input: nums = [1,2,2,2]
Output: 2
Explanation: We can split the array at index 2 to obtain arrays [1,2,2] and [2]. 
In array [1,2,2], element 2 is dominant since it occurs twice in the array and 2 * 2 > 3. 
In array [2], element 2 is dominant since it occurs once in the array and 1 * 2 > 1.
Both [1,2,2] and [2] have the same dominant element as nums, so this is a valid split. 
It can be shown that index 2 is the minimum index of a valid split. 

Example 2:

Input: nums = [2,1,3,1,1,1,7,1,2,1]
Output: 4
Explanation: We can split the array at index 4 to obtain arrays [2,1,3,1,1] and [1,7,1,2,1].
In array [2,1,3,1,1], element 1 is dominant since it occurs thrice in the array and 3 * 2 > 5.
In array [1,7,1,2,1], element 1 is dominant since it occurs thrice in the array and 3 * 2 > 5.
Both [2,1,3,1,1] and [1,7,1,2,1] have the same dominant element as nums, so this is a valid split.
It can be shown that index 4 is the minimum index of a valid split.

Example 3:

Input: nums = [3,3,3,3,7,2,2]
Output: -1
Explanation: It can be shown that there is no valid split.
 

Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 109
nums has exactly one dominant element.
*/

class Solution {
    public int minimumIndex(List<Integer> nums) {
        int n = nums.size();

        // Step 1: Find the dominant element using Boyer-Moore Voting Algorithm
        int candidate = -1, count = 0;
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
                count = 1;
            } else if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }

        // Step 2: Count occurrences of the dominant element
        int totalCount = 0;
        for (int num : nums) {
            if (num == candidate) totalCount++;
        }

        // If no dominant element found (which shouldn't happen as per constraints)
        if (totalCount * 2 <= n) return -1;

        // Step 3: Find the minimum valid split index
        int leftCount = 0; // Count of `candidate` in left subarray
        for (int i = 0; i < n - 1; i++) {
            if (nums.get(i) == candidate) leftCount++;

            // Check if `candidate` is dominant in both left and right subarrays
            int leftSize = i + 1, rightSize = n - leftSize;
            int rightCount = totalCount - leftCount;

            if (leftCount * 2 > leftSize && rightCount * 2 > rightSize) {
                return i;
            }
        }

        return -1; // No valid split found
    }
}

/*
Visualization of the above code
 ### **Visualization of the Code Execution**

We will walk through the **Example 2** step by step:

```java
Input: nums = [2,1,3,1,1,1,7,1,2,1]
Output: 4
```

---

### **Step 1: Find the Dominant Element**
We use the **Boyer-Moore Voting Algorithm** to determine the dominant element.

| Index | Number | Candidate | Count |
|--------|---------|-------------|-------|
| 0      | 2       | 2           | 1     |
| 1      | 1       | 2           | 0     |
| 2      | 3       | 3           | 1     |
| 3      | 1       | 3           | 0     |
| 4      | 1       | 1           | 1     |
| 5      | 1       | 1           | 2     |
| 6      | 7       | 1           | 1     |
| 7      | 1       | 1           | 2     |
| 8      | 2       | 1           | 1     |
| 9      | 1       | 1           | 2     |

- The **dominant element** is `1`, since it remained as the candidate in the Boyer-Moore algorithm.
- We now count the total occurrences of `1` in `nums`.

```java
Total occurrences of 1 = 6
```

---

### **Step 2: Find the Minimum Valid Split**
We iterate through `nums` and track the count of `1` in both left and right parts.

| Index | Subarray Left  | Count of `1` in Left | Left Size | Condition `count * 2 > leftSize` | Count of `1` in Right | Right Size | Condition `count * 2 > rightSize` |
|--------|-----------------|--------------------|------------|----------------------------------|----------------------|------------|----------------------------------|
| 0      | `[2]`           | 0                  | 1          | ❌                              | 6                    | 9          | ✅                              |
| 1      | `[2,1]`         | 1                  | 2          | ❌                              | 5                    | 8          | ✅                              |
| 2      | `[2,1,3]`       | 1                  | 3          | ❌                              | 5                    | 7          | ✅                              |
| 3      | `[2,1,3,1]`     | 2                  | 4          | ❌                              | 4                    | 6          | ✅                              |
| 4      | `[2,1,3,1,1]`   | 3                  | 5          | ✅                              | 3                    | 5          | ✅                              | ✅ **Valid split found at index 4** ✅ |

Thus, **index `4` is the minimum valid split**.

---

### **Summary of Code Flow**
1. **Find the dominant element using Boyer-Moore Algorithm.**  
   - Found `1` as the dominant element.
2. **Count occurrences of dominant element.**  
   - Found `1` appears `6` times.
3. **Iterate through the array to find the minimum split index where the element remains dominant in both left and right subarrays.**  
   - **Found valid split at index `4`**.

---

### **Complexity Breakdown**
- **Finding dominant element** → `O(n)`
- **Counting occurrences** → `O(n)`
- **Finding the split index** → `O(n)`

**Total Time Complexity: `O(n)`**  
**Space Complexity: `O(1)`**

---

### **Final Answer**
```java
Output: 4
```
---
✅ Efficiently finds the minimum index for a valid split in `O(n)`. 🚀
*/
