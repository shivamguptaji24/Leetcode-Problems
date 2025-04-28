/*
Daily Question :
2302 - Count Subarrays With Score Less Than K

The score of an array is defined as the product of its sum and its length.
For example, the score of [1, 2, 3, 4, 5] is (1 + 2 + 3 + 4 + 5) * 5 = 75.
Given a positive integer array nums and an integer k, return the number of non-empty subarrays of nums whose score is strictly less than k.
A subarray is a contiguous sequence of elements within an array. 

Example 1:

Input: nums = [2,1,4,3,5], k = 10
Output: 6
Explanation:
The 6 subarrays having scores less than 10 are:
- [2] with score 2 * 1 = 2.
- [1] with score 1 * 1 = 1.
- [4] with score 4 * 1 = 4.
- [3] with score 3 * 1 = 3. 
- [5] with score 5 * 1 = 5.
- [2,1] with score (2 + 1) * 2 = 6.
Note that subarrays such as [1,4] and [4,3,5] are not considered because their scores are 10 and 36 respectively, while we need scores strictly less than 10.

Example 2:

Input: nums = [1,1,1], k = 5
Output: 5
Explanation:
Every subarray except [1,1,1] has a score less than 5.
[1,1,1] has a score (1 + 1 + 1) * 3 = 9, which is greater than 5.
Thus, there are 5 subarrays having scores less than 5.
 

Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 105
1 <= k <= 1015
*/

class Solution {
    public long countSubarrays(int[] nums, long k) {
        long count = 0;
        long sum = 0;
        int left = 0;
        
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            
            // Shrink from left if the score is not valid
            while (left <= right && sum * (right - left + 1) >= k) {
                sum -= nums[left];
                left++;
            }
            
            // All subarrays ending at right are valid
            count += (right - left + 1);
        }
        
        return count;
    }
}

/*
Visualization of the above code
 Let's visualize how the code works step-by-step on:

---
Input:  
`nums = [2, 1, 4, 3, 5]`  
`k = 10`

We use:
- `left = 0`
- `sum = 0`
- `count = 0`

We'll slide the `right` pointer from left to right.  
At each step, we'll expand the window, check the score, and shrink from the left if needed.

---

| Step | left | right | nums[right] | sum of window | window size | score = sum × size | Action                                                    | count |
|-----:|:----:|:-----:|:-----------:|:-------------:|:-----------:|:------------------:|:----------------------------------------------------------|:-----:|
| 1    | 0    | 0     | 2           | 2             | 1           | 2                  | Score < 10 → Add 1 valid subarray                        | 1     |
| 2    | 0    | 1     | 1           | 3             | 2           | 6                  | Score < 10 → Add 2 valid subarrays                      | 3     |
| 3    | 0    | 2     | 4           | 7             | 3           | 21                 | Score ≥ 10 → Move `left`                                 |       |
| 4    | 1    | 2     | (no move)   | 5             | 2           | 10                 | Score ≥ 10 → Move `left`                                 |       |
| 5    | 2    | 2     | (no move)   | 4             | 1           | 4                  | Score < 10 → Add 1 valid subarray                       | 4     |
| 6    | 2    | 3     | 3           | 7             | 2           | 14                 | Score ≥ 10 → Move `left`                                 |       |
| 7    | 3    | 3     | (no move)   | 3             | 1           | 3                  | Score < 10 → Add 1 valid subarray                       | 5     |
| 8    | 3    | 4     | 5           | 8             | 2           | 16                 | Score ≥ 10 → Move `left`                                 |       |
| 9    | 4    | 4     | (no move)   | 5             | 1           | 5                  | Score < 10 → Add 1 valid subarray                       | 6     |

---
✅ Final count = 6.  
Exactly the expected output.

---

How the window moves
Here’s a more intuitive timeline:

```
Initial: []

1. Right = 0 → [2] → score = 2 (valid) → count += 1
2. Right = 1 → [2,1] → score = 6 (valid) → count += 2
3. Right = 2 → [2,1,4] → score = 21 (invalid)
   Shrink → [1,4] → score = 10 (invalid)
   Shrink → [4] → score = 4 (valid) → count += 1
4. Right = 3 → [4,3] → score = 14 (invalid)
   Shrink → [3] → score = 3 (valid) → count += 1
5. Right = 4 → [3,5] → score = 16 (invalid)
   Shrink → [5] → score = 5 (valid) → count += 1
```
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 3ms runtime which is the second lowest time in this problem.
*/

class Solution {
    public long countSubarrays(int[] nums, long k) {
        int n = nums.length ; 
        long sum = 0 ;
        long count = 0 ; 
        int right = 0 , left = 0 ; 
        while(right < n){
            sum += nums[right]; 
            while(left <= right && sum * (right - left + 1) >= k){
                sum -= nums[left]; 
                left ++; 
            }
            count += (right - left + 1); 
            right ++ ; 
        }
        return count; 
    }
}
