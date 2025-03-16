/*
Daily Question :
2594 - Minimum Tiime to Repair Cars

You are given an integer array ranks representing the ranks of some mechanics. ranksi is the rank of the ith mechanic. A mechanic with a rank r can repair n cars in r * n2 minutes.

You are also given an integer cars representing the total number of cars waiting in the garage to be repaired.

Return the minimum time taken to repair all the cars.

Note: All the mechanics can repair the cars simultaneously.

 

Example 1:

Input: ranks = [4,2,3,1], cars = 10
Output: 16
Explanation: 
- The first mechanic will repair two cars. The time required is 4 * 2 * 2 = 16 minutes.
- The second mechanic will repair two cars. The time required is 2 * 2 * 2 = 8 minutes.
- The third mechanic will repair two cars. The time required is 3 * 2 * 2 = 12 minutes.
- The fourth mechanic will repair four cars. The time required is 1 * 4 * 4 = 16 minutes.
It can be proved that the cars cannot be repaired in less than 16 minutes.​​​​​
Example 2:

Input: ranks = [5,1,8], cars = 6
Output: 16
Explanation: 
- The first mechanic will repair one car. The time required is 5 * 1 * 1 = 5 minutes.
- The second mechanic will repair four cars. The time required is 1 * 4 * 4 = 16 minutes.
- The third mechanic will repair one car. The time required is 8 * 1 * 1 = 8 minutes.
It can be proved that the cars cannot be repaired in less than 16 minutes.​​​​​
 

Constraints:

1 <= ranks.length <= 105
1 <= ranks[i] <= 100
1 <= cars <= 106
*/

class Solution {
    public long repairCars(int[] ranks, int cars) {
        long left = 1; // Minimum Possible Time
        long right = (long) Arrays.stream(ranks).min().getAsInt() * (long) cars * cars;  // Maximum Possible Time

        while(left < right) {
            long mid = left + (right - left) / 2;

            if(canRepairAllCars(ranks, cars, mid)) {
                right = mid;  // Try a smaller time
            } else {
                left = mid + 1;  // Increase the time range
            }
        }
        return left; // The minimum possible time
    }

    // Helper method to check if given 'mid' time is feasible
    public boolean canRepairAllCars(int[] ranks, int cars, long mid) {
        long totalCars = 0;
        for(int rank : ranks) {
            // Calculate the maximum number of cars this mechanic can repair in 'mid' time
            totalCars += Math.sqrt(mid / rank);
            // If enough cars are already counted, no need to check further
            if(totalCars >= cars) {
                return true;
            }
        }
        return totalCars >= cars;
    }
}
