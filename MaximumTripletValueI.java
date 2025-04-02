/*
Daily Question :
2873 - Maximum Value Of An Ordered Triplet I

You are given a 0-indexed integer array nums.
Return the maximum value over all triplets of indices (i, j, k) such that i < j < k. If all such triplets have a negative value, return 0.
The value of a triplet of indices (i, j, k) is equal to (nums[i] - nums[j]) * nums[k]. 

Example 1:

Input: nums = [12,6,1,2,7]
Output: 77
Explanation: The value of the triplet (0, 2, 4) is (nums[0] - nums[2]) * nums[4] = 77.
It can be shown that there are no ordered triplets of indices with a value greater than 77. 

Example 2:

Input: nums = [1,10,3,4,19]
Output: 133
Explanation: The value of the triplet (1, 2, 4) is (nums[1] - nums[2]) * nums[4] = 133.
It can be shown that there are no ordered triplets of indices with a value greater than 133.

Example 3:

Input: nums = [1,2,3]
Output: 0
Explanation: The only ordered triplet of indices (0, 1, 2) has a negative value of (nums[0] - nums[1]) * nums[2] = -3. Hence, the answer would be 0.
 

Constraints:

3 <= nums.length <= 100
1 <= nums[i] <= 106
*/

class Solution {
    public long maximumTripletValue(int[] nums) {
        int n = nums.length;
        long maxValue = 0; // Store maximum triplet value

        // Iterate over all triplets (i, j, k) where i < j < k
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    long tripletValue = (long) (nums[i] - nums[j]) * nums[k];
                    maxValue = Math.max(maxValue, tripletValue);
                }
            }
        }

        return maxValue;
    }
}

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 1ms runtime which is the second lowest time in this problem
*/

class Solution {
    public long maximumTripletValue(int[] nums) {
        int n = nums.length;
        long maxVal = 0;

        // Prefix max stores max nums[i] for i < j
        int prefixMax = nums[0];

        // Stores the maximum (nums[i] - nums[j]) value seen so far
        long maxDiff = Long.MIN_VALUE;

        // Iterate from j = 1 to n - 2, tracking maxDiff
        for (int j = 1; j < n - 1; j++) {
            maxDiff = Math.max(maxDiff, (long) prefixMax - nums[j]);
            prefixMax = Math.max(prefixMax, nums[j]); // Update prefix max
            
            // Find max k > j in one pass
            int maxK = Integer.MIN_VALUE;
            for (int k = j + 1; k < n; k++) {
                maxK = Math.max(maxK, nums[k]);
            }

            // Update result if maxDiff is valid
            if (maxDiff != Long.MIN_VALUE) {
                maxVal = Math.max(maxVal, maxDiff * maxK);
            }
        }

        return maxVal;
    }
}

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 0ms runtime which is the lowest time in this problem
*/

class Solution {
    public long maximumTripletValue(int[] nums) {
        int n = nums.length;
        long res = 0;
        int imax = 0, dmax = 0;
        for (int num: nums) {
            res = Math.max(res, (long) dmax * num);
            dmax = Math.max(dmax, imax - num);
            imax = Math.max(imax, num);
        }
        return res;
    }
}
