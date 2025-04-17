/*
Daily Question :
2176 - Count Equal and Divisible Pairs in an Array

Given a 0-indexed integer array nums of length n and an integer k, return the number of pairs (i, j) where 0 <= i < j < n, such that nums[i] == nums[j] and (i * j) is divisible by k. 

Example 1:

Input: nums = [3,1,2,2,2,1,3], k = 2
Output: 4
Explanation:
There are 4 pairs that meet all the requirements:
- nums[0] == nums[6], and 0 * 6 == 0, which is divisible by 2.
- nums[2] == nums[3], and 2 * 3 == 6, which is divisible by 2.
- nums[2] == nums[4], and 2 * 4 == 8, which is divisible by 2.
- nums[3] == nums[4], and 3 * 4 == 12, which is divisible by 2.

Example 2:

Input: nums = [1,2,3,4], k = 1
Output: 0
Explanation: Since no value in nums is repeated, there are no pairs (i,j) that meet all the requirements.
 

Constraints:

1 <= nums.length <= 100
1 <= nums[i], k <= 100
*/

class Solution {
    public int countPairs(int[] nums, int k) {
        int count = 0;
        int n = nums.length;
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nums[i] == nums[j] && (i * j) % k == 0) {
                    count++;
                }
            }
        }
        
        return count;
    }
}

/*
Visualization of the above code
 Let's visualize the brute-force code step-by-step for the input:

```
nums = [3, 1, 2, 2, 2, 1, 3], k = 2
```

🔄 Code Recap:
```
for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
        if (nums[i] == nums[j] && (i * j) % k == 0) {
            count++;
        }
    }
}
```

---

👀 Visualization (Table Format):

| i | j | nums[i] | nums[j] | nums[i] == nums[j]? | i * j | (i * j) % 2 == 0? | Count |
|---|---|---------|---------|----------------------|--------|----------------------|--------|
| 0 | 1 | 3       | 1       | ❌                   | 0      | ✅                   | 0      |
| 0 | 2 | 3       | 2       | ❌                   | 0      | ✅                   | 0      |
| 0 | 3 | 3       | 2       | ❌                   | 0      | ✅                   | 0      |
| 0 | 4 | 3       | 2       | ❌                   | 0      | ✅                   | 0      |
| 0 | 5 | 3       | 1       | ❌                   | 0      | ✅                   | 0      |
| 0 | 6 | 3       | 3       | ✅                   | 0      | ✅                   | 1 ✅    |
| 1 | 2 | 1       | 2       | ❌                   | 2      | ✅                   | 1      |
| 1 | 3 | 1       | 2       | ❌                   | 3      | ❌                   | 1      |
| 1 | 4 | 1       | 2       | ❌                   | 4      | ✅                   | 1      |
| 1 | 5 | 1       | 1       | ✅                   | 5      | ❌                   | 1      |
| 1 | 6 | 1       | 3       | ❌                   | 6      | ✅                   | 1      |
| 2 | 3 | 2       | 2       | ✅                   | 6      | ✅                   | 2 ✅    |
| 2 | 4 | 2       | 2       | ✅                   | 8      | ✅                   | 3 ✅    |
| 2 | 5 | 2       | 1       | ❌                   | 10     | ✅                   | 3      |
| 2 | 6 | 2       | 3       | ❌                   | 12     | ✅                   | 3      |
| 3 | 4 | 2       | 2       | ✅                   | 12     | ✅                   | 4 ✅    |
| 3 | 5 | 2       | 1       | ❌                   | 15     | ❌                   | 4      |
| 3 | 6 | 2       | 3       | ❌                   | 18     | ✅                   | 4      |
| 4 | 5 | 2       | 1       | ❌                   | 20     | ✅                   | 4      |
| 4 | 6 | 2       | 3       | ❌                   | 24     | ✅                   | 4      |
| 5 | 6 | 1       | 3       | ❌                   | 30     | ✅                   | 4      |

---

✅ Final Count: `4`

---

📊 Summary of Valid Pairs

| Pair (i, j) | Explanation |
|------------|-------------|
| (0, 6)      | 3 == 3, 0 × 6 = 0 → divisible by 2 |
| (2, 3)      | 2 == 2, 2 × 3 = 6 → divisible by 2 |
| (2, 4)      | 2 == 2, 2 × 4 = 8 → divisible by 2 |
| (3, 4)      | 2 == 2, 3 × 4 = 12 → divisible by 2 |

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 1ms runtime which is the lowest time in this problem.
*/

class Solution {
    public int countPairs(int[] nums, int k) {
        return helper(nums,k,0);
    }

private static int helper(int[] nums, int k, int i){
    if(i>=nums.length){
        return 0;
    }
    int count = 0;
    for(int j=i+1;j<nums.length;j++){
        if(nums[i]==nums[j] && (i*j)%k==0){
            count++;
        }
    }
    return count+helper(nums,k,i+1);
  }
}
