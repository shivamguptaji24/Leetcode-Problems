/*
Daily Question :
2560 - House Robber IV

There are several consecutive houses along a street, each of which has some money inside. There is also a robber, who wants to steal money from the homes, but he refuses to steal from adjacent homes.
The capability of the robber is the maximum amount of money he steals from one house of all the houses he robbed.
You are given an integer array nums representing how much money is stashed in each house. More formally, the ith house from the left has nums[i] dollars.
You are also given an integer k, representing the minimum number of houses the robber will steal from. It is always possible to steal at least k houses.
Return the minimum capability of the robber out of all the possible ways to steal at least k houses.

 

Example 1:

Input: nums = [2,3,5,9], k = 2
Output: 5
Explanation: 
There are three ways to rob at least 2 houses:
- Rob the houses at indices 0 and 2. Capability is max(nums[0], nums[2]) = 5.
- Rob the houses at indices 0 and 3. Capability is max(nums[0], nums[3]) = 9.
- Rob the houses at indices 1 and 3. Capability is max(nums[1], nums[3]) = 9.
Therefore, we return min(5, 9, 9) = 5.
Example 2:

Input: nums = [2,7,9,3,1], k = 2
Output: 2
Explanation: There are 7 ways to rob the houses. The way which leads to minimum capability is to rob the house at index 0 and 4. Return max(nums[0], nums[4]) = 2.
 

Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 109
1 <= k <= (nums.length + 1)/2
*/

public class RobberBinarySearch {
    public int minCapability(int[] nums, int k) {
        int left = Integer.MAX_VALUE, right = Integer.MIN_VALUE;

        for (int num : nums) {
            left = Math.min(left, num);
            right = Math.max(right, num);
        }

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (canRob(nums, k, mid)) {
                right = mid;  // Try a smaller maximum value
            } else {
                left = mid + 1;  // Increase the limit
            }
        }

        return left;
    }

    // Helper function to check if we can rob at least k houses with a given max capability
    private boolean canRob(int[] nums, int k, int maxCap) {
        int count = 0;
        int i = 0;

        while (i < nums.length) {
            if (nums[i] <= maxCap) {
                count++;
                i += 2;  // Skip the next house to avoid adjacent robberies
            } else {
                i++;
            }

            if (count >= k) return true;  // Found k houses
        }

        return false;
    }

    public static void main(String[] args) {
        RobberBinarySearch solution = new RobberBinarySearch();
        int[] nums = {2, 7, 9, 3, 1};
        int k = 2;
        System.out.println("Minimum Capability (Binary Search + Greedy): " + solution.minCapability(nums, k));
    }
}