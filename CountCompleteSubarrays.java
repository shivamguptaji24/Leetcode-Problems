/*
Daily Question :
2799 - Count Complete Subarrays in an Array

You are given an array nums consisting of positive integers.
We call a subarray of an array complete if the following condition is satisfied:
The number of distinct elements in the subarray is equal to the number of distinct elements in the whole array.
Return the number of complete subarrays.
A subarray is a contiguous non-empty part of an array. 

Example 1:

Input: nums = [1,3,1,2,2]
Output: 4
Explanation: The complete subarrays are the following: [1,3,1,2], [1,3,1,2,2], [3,1,2] and [3,1,2,2].

Example 2:

Input: nums = [5,5,5,5]
Output: 10
Explanation: The array consists only of the integer 5, so any subarray is complete. The number of subarrays that we can choose is 10.
 

Constraints:

1 <= nums.length <= 1000
1 <= nums[i] <= 2000
*/

class Solution {
    public int countCompleteSubarrays(int[] nums) {
        int total = 0;
        int n = nums.length;

        // Step 1: Get number of distinct elements in the full array
        Set<Integer> fullSet = new HashSet<>();
        for (int num : nums)
            fullSet.add(num);
        int totalDistinct = fullSet.size();

        // Step 2: Check all subarrays
        for (int i = 0; i < n; i++) {
            Set<Integer> subSet = new HashSet<>();
            for (int j = i; j < n; j++) {
                subSet.add(nums[j]);
                if (subSet.size() == totalDistinct)
                    total++;
            }
        }

        return total;
    }
}

/*
Visualization of the above code
 Let's visualize the working of the code for this problem with a detailed step-by-step walkthrough — no images, just clear explanation.

---

🧠 Problem Recap

We are given an array `nums`, and we need to count how many complete subarrays exist.

> A complete subarray is one where the number of distinct elements is equal to the number of distinct elements in the entire array.

---

✅ Step-by-Step Visualization

Let's take the example:

```
nums = [1, 3, 1, 2, 2]
```

---

🔹 Step 1: Count distinct elements in the whole array

```
Set<Integer> fullSet = new HashSet<>();
for (int num : nums)
    fullSet.add(num);
int totalDistinct = fullSet.size(); // = 3 → {1, 2, 3}
```

So, any complete subarray must contain all 3 elements: 1, 2, and 3.

---

🔹 Step 2: Loop through all subarrays and count the complete ones

We use two nested loops:
- Outer loop (`i`) is the start index of the subarray.
- Inner loop (`j`) is the end index of the subarray.
- For each subarray, we keep a Set to track distinct elements.

---

🔄 Iteration Breakdown:

✅ i = 0:
- j = 0 → [1] → distinct = {1} → ❌
- j = 1 → [1, 3] → {1,3} → ❌
- j = 2 → [1, 3, 1] → still {1,3} → ❌
- j = 3 → [1,3,1,2] → {1,3,2} ✅ → ✔ complete (count = 1)
- j = 4 → [1,3,1,2,2] → {1,3,2} ✅ → ✔ complete (count = 2)

✅ i = 1:
- j = 1 → [3] → {3} → ❌
- j = 2 → [3,1] → {3,1} → ❌
- j = 3 → [3,1,2] → {3,1,2} ✅ → ✔ complete (count = 3)
- j = 4 → [3,1,2,2] → {3,1,2} ✅ → ✔ complete (count = 4)

✅ i = 2:
- j = 2 → [1] → {1} ❌
- j = 3 → [1,2] → {1,2} ❌
- j = 4 → [1,2,2] → {1,2} ❌

✅ i = 3:
- j = 3 → [2] → {2} ❌
- j = 4 → [2,2] → {2} ❌

✅ i = 4:
- j = 4 → [2] → {2} ❌

---

🟩 Total Complete Subarrays = 4

Which are:
- [1,3,1,2]
- [1,3,1,2,2]
- [3,1,2]
- [3,1,2,2]

---

🔚 Summary

- This method brute-forces all subarrays but efficiently uses a `HashSet` to track unique elements.
- It's simple and works well within the constraints (`n ≤ 1000`).
- Visualization shows exactly how we're checking and counting only subarrays that contain all unique elements from the full array.

---
*/
