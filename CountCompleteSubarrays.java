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

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 7ms runtime which is the most uses time in this problem.
*/

class Solution {
    public int countCompleteSubarrays(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for(int val : nums){
            set.add(val);
        }

        int k = set.size();

        int i = 0;
        
        int j = 0;

        int ans = 0;

        HashMap<Integer,Integer> map = new HashMap<>();

        while(j < nums.length){

            map.put(nums[j],map.getOrDefault(nums[j],0)+1);

            while(map.size() == k){

                ans += (nums.length - j);

                map.put(nums[i],map.get(nums[i])-1);

                if(map.get(nums[i]) == 0){
                    map.remove(nums[i]);
                }
                i++;
            }
            j++;
        }

        return ans;
    }
}

/*
Visualization of the above code
 Let's visualize your optimized Java code for counting complete subarrays — line by line and with a clear step-by-step explanation using an example. You’ve used a sliding window + HashMap technique, which is more efficient than brute force. Let’s break it down in a simple way 👇

---

✅ Problem Recap:

You want to count all complete subarrays of `nums`, where:
> A complete subarray contains all distinct elements that are present in the entire array.

---

🧠 Code Overview

You:
1. First find how many unique elements are in the full array (`k`).
2. Then use a sliding window from index `i` to `j`.
3. Maintain a `HashMap` to count frequency of each element inside the window.
4. When current window has all `k` unique elements:
   - Add `(nums.length - j)` to the result.
   - Shrink the window from the left (`i++`).

---

📌 Let's use this array as an example:

```
nums = [1, 3, 1, 2, 2]
```

Step 1: Calculate unique elements
```
Set = {1, 3, 2} → k = 3
```

Step 2: Sliding window logic

| i | j | Window            | Map                 | map.size() | Action                        | ans |
|---|---|-------------------|---------------------|------------|-------------------------------|-----|
| 0 | 0 | [1]               | {1=1}               | 1          | Not complete                  | 0   |
| 0 | 1 | [1, 3]            | {1=1, 3=1}          | 2          | Not complete                  | 0   |
| 0 | 2 | [1, 3, 1]         | {1=2, 3=1}          | 2          | Not complete                  | 0   |
| 0 | 3 | [1, 3, 1, 2]      | {1=2, 3=1, 2=1}     | ✅ 3       | Complete → ans += 2 (5-3)     | 2   |
| 1 | 3 | [3, 1, 2]         | {1=1, 3=1, 2=1}     | ✅ 3       | Complete → ans += 2           | 4   |
| 2 | 3 | [1, 2]            | {1=1, 2=1}          | 2          | Not complete                  | 4   |
| 2 | 4 | [1, 2, 2]         | {1=1, 2=2}          | 2          | Not complete                  | 4   |
| 3 | 4 | [2, 2]            | {2=2}               | 1          | Not complete                  | 4   |

---

🟩 Final Answer: `4`

Subarrays counted:
- [1,3,1,2]
- [1,3,1,2,2]
- [3,1,2]
- [3,1,2,2]

---

⚙️ Key Insight of Your Code

- It avoids checking all `O(n²)` subarrays.
- Instead, for each end pointer `j`, you only consider a valid window.
- When a valid (complete) window is found, all its right extensions are also complete ⇒ That’s why:
  ```
  ans += (nums.length - j);
  ```

---

🚀 Time Complexity

- O(n) average for sliding window (since each element enters and exits the window at most once).
- O(n) to count unique elements in the beginning.

So, total time is approximately O(n).

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 2ms runtime which is the lowest time in this problem.
*/

class Solution {
    public int countCompleteSubarrays(int[] nums) {
        boolean[] exists = new boolean[2001];
        int distinct = 0;
        for( int n : nums ){
            if( !exists[n] ){
                exists[n] = true;
                distinct++;
            }
        }
        int[] freq = new int[2001];
        int count = 0, n = nums.length;
        int sub = 0;
        for( int start = 0, end = 0; end < n; end++ ){
            if( freq[ nums[ end ] ]++ == 0 )
                count++;
            while( count == distinct ){
                sub += n - end;
                if( freq[ nums[ start ++ ] ]-- == 1 )
                    count--;
            }
        }
        return sub;
    }
}
