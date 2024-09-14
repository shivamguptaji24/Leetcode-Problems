/*
 *  Search Insert Position
 * 
 *  Given a sorted array of distinct integers and a target value, return the index if the target is found.
 *  If not, return the index where it would be if it were inserted in order.
 *  You must write an algorithm with O(log n) runtime complexity.
 *  
 *  Example 1:
 *  Input: nums = [1,3,5,6], target = 5
 *  Output: 2
 * 
 *  Example 2:
 *  Input: nums = [1,3,5,6], target = 2
 *  Output: 1
 * 
 *  Example 3:
 *  Input: nums = [1,3,5,6], target = 7
 *  Output: 4
 *  
 *  Constraints:
 *  1 <= nums.length <= 104
 *  -104 <= nums[i] <= 104
 *  nums contains distinct values sorted in ascending order.
 *  -104 <= target <= 104
 */


/*
 *  Solution and explanation
 *  1. Pointers -
 *      low starts at the beginning (0).
 *      high starts at the last index (nums.length - 1).
 * 
 *  2. Logic (Binary Search) -
 *      Calculate middle index (mid = low + (high - low) / 2).
 *      If middle element equal to the target then return mid.
 *      If nums[mid] is less than the target then adjust the low pointer to mid + 1.
 *      If nums[mid] is greater than the target then adjust the high pointer to mid - 1.
 * 
 *  3. Return -
 *      If the target is not found, the low pointer will eventually point to the correct
 *          insertion index where the target should be placed to maintain the sorted order.
 * 
 *  Time Complexity - O(log n) - due to the binary search.
 *  Space Complexity - O(1) - since we are using a constant amount of extra space.
 */

import java.util.Scanner;

class Solution {

    public int searchInsert(int[] nums, int target) {

        int low = 0, high = nums.length - 1;

        while (low <= high) {
            
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                return mid;                 // Target found, return index.
            } else if (nums[mid] < target) {
                low = mid + 1;              // Move the left pointer to mid + 1.
            } else {
                high = mid - 1;             // Move the right pointer to mid - 1.
            }

        }
        return low;                         // Return the left pointer, which will be the insert position.

    }

    // Main method to test the solution
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);

        // Take the array size from the user
        System.out.println("Enter the size (number of elements) of the array : ");
        int size = scan.nextInt();

        int[] nums = new int[size];

        // Take the array element from the user
        System.out.println("Enter the elements of the array in the sorted order : ");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scan.nextInt();
        }

        // Take the target value from the user
        System.out.println("Enter the target value : ");
        int target = scan.nextInt();

        // Create an instance of Solution class and call the searchInsert method
        Solution sol = new Solution();
        int result = sol.searchInsert(nums, target);

        // Output the result
        System.out.println("The target should be inserted at index : " + result);

        scan.close();

    }

}