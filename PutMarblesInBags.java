/*
Daily Question :
2551 - Put Marbles in Bags

You have k bags. You are given a 0-indexed integer array weights where weights[i] is the weight of the ith marble. You are also given the integer k.
Divide the marbles into the k bags according to the following rules:
No bag is empty.
If the ith marble and jth marble are in a bag, then all marbles with an index between the ith and jth indices should also be in that same bag.
If a bag consists of all the marbles with an index from i to j inclusively, then the cost of the bag is weights[i] + weights[j].
The score after distributing the marbles is the sum of the costs of all the k bags.

Return the difference between the maximum and minimum scores among marble distributions. 

Example 1:

Input: weights = [1,3,5,1], k = 2
Output: 4
Explanation: 
The distribution [1],[3,5,1] results in the minimal score of (1+1) + (3+1) = 6. 
The distribution [1,3],[5,1], results in the maximal score of (1+3) + (5+1) = 10. 
Thus, we return their difference 10 - 6 = 4.

Example 2:

Input: weights = [1, 3], k = 2
Output: 0
Explanation: The only distribution possible is [1],[3]. 
Since both the maximal and minimal score are the same, we return 0.
 

Constraints:

1 <= k <= weights.length <= 105
1 <= weights[i] <= 109
*/

class Solution {
  public long putMarbles(int[] weights, int k) {
    // To distribute marbles into k bags, there will be k - 1 cuts. If there's a
    // cut after weights[i], then weights[i] and weights[i + 1] will be added to
    // the cost. Also, no matter how we cut, weights[0] and weights[n - 1] will
    // be counted. So, the goal is to find the max/min k - 1 weights[i] +
    // weights[i + 1].
    int[] arr = new int[weights.length - 1]; // weights[i] + weights[i + 1]
    long mn = 0;
    long mx = 0;

    for (int i = 0; i < arr.length; ++i)
      arr[i] = weights[i] + weights[i + 1];

    Arrays.sort(arr);

    for (int i = 0; i < k - 1; ++i) {
      mn += arr[i];
      mx += arr[arr.length - 1 - i];
    }

    return mx - mn;
  }
}

/*
Visualization of the above code
 Let's visualize the execution of this code step by step for better understanding.  

---

Example 1  
Input:  
```
weights = [1, 3, 5, 1], k = 2
```
---
Step 1: Compute Pair Sums
The array `arr` stores the sum of consecutive elements:  
```
arr[i] = weights[i] + weights[i+1]
```
- `arr[0] = 1 + 3 = 4`
- `arr[1] = 3 + 5 = 8`
- `arr[2] = 5 + 1 = 6`

So, arr = [4, 8, 6]  

---
Step 2: Sorting `arr`
After sorting:
```
arr = [4, 6, 8]
```
---
Step 3: Compute min and max scores  
Since `k = 2`, we need to pick (k-1) = 1 element from both ends:
- `mn = arr[0] = 4`
- `mx = arr[2] = 8`

---
Step 4: Compute Result  
```
mx - mn = 8 - 4 = 4
```
---
Final Output:
```
Output: 4
```

---

Another Example
Input:
```
weights = [1, 3], k = 2
```
---
Step 1: Compute Pair Sums
- `arr[0] = 1 + 3 = 4`
  - arr = [4]  

---
Step 2: Sorting `arr`
Since there's only one element, sorting doesn't change `arr = [4]`.  

---
Step 3: Compute min and max scores  
Since `k = 2`, we need to pick (k-1) = 1 element:
- `mn = arr[0] = 4`
- `mx = arr[0] = 4`

---
Step 4: Compute Result  
```
mx - mn = 4 - 4 = 0
```
---
Final Output:
```
Output: 0
```

---

Key Takeaways
1. Sorting is used to extract the (k-1) smallest and largest sums efficiently.
2. The difference between the sum of the k-1 largest and smallest cuts gives the answer.
3. Time Complexity → `O(n log n)` due to sorting.
*/

/*-----------------OR-----------------*/

class Solution {
    public long putMarbles(int[] weights, int k) {
        int n = weights.length;
        if (k == 1) return 0; // Only one bag, so no difference

        // Step 1: Compute pairwise sums (costs of potential cuts)
        int[] pairSums = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            pairSums[i] = weights[i] + weights[i + 1];
        }

        // Step 2: Sort the pairwise sums
        Arrays.sort(pairSums);

        // Step 3: Compute min and max scores using long
        long minScore = 0, maxScore = 0;
        for (int i = 0; i < k - 1; i++) {
            minScore += pairSums[i];                   // Take smallest k-1 splits
            maxScore += pairSums[n - 2 - i];           // Take largest k-1 splits
        }

        return maxScore - minScore; // Difference between max and min scores
    }
}

/*
Visualization of the above code
 Let's visualize the execution of this code step by step to understand how it calculates the difference between the maximum and minimum scores.

---

Example 1
Input:
```
weights = [1, 3, 5, 1], k = 2
```

---
Step 1: Compute Pairwise Sums
We compute the sum of consecutive elements:
```
pairSums[i] = weights[i] + weights[i + 1]
```

| Index | Computation          | Value |
|--------|----------------------|--------|
| 0      | `1 + 3`  | `4` |
| 1      | `3 + 5`  | `8` |
| 2      | `5 + 1`  | `6` |

So, the pairSums array is:
```
pairSums = [4, 8, 6]
```

---
Step 2: Sort the `pairSums` array
After sorting:
```
pairSums = [4, 6, 8]
```

---
Step 3: Compute Min and Max Scores
We select (k-1) = 1 elements from both ends:

- Minimum Score:
  - Take the smallest element (`pairSums[0] = 4`)
  - `minScore = 4`

- Maximum Score:
  - Take the largest element (`pairSums[2] = 8`)
  - `maxScore = 8`

---
Step 4: Compute Final Result
```
result = maxScore - minScore = 8 - 4 = 4
```

---
Final Output:
```
Output: 4
```

---

Example 2
Input:
```
weights = [1, 3], k = 2
```

---
Step 1: Compute Pairwise Sums
Only one pair exists:
```
pairSums = [1 + 3] = [4]
```

---
Step 2: Sort the `pairSums` array
```
pairSums = [4]
```
(Since there's only one element, sorting has no effect.)

---
Step 3: Compute Min and Max Scores
We select (k-1) = 1 elements:
- Minimum Score: `pairSums[0] = 4`
- Maximum Score: `pairSums[0] = 4`

---
Step 4: Compute Final Result
```
result = maxScore - minScore = 4 - 4 = 0
```

---
Final Output:
```
Output: 0
```

---

Graphical Representation
Imagine the numbers as marbles placed in a line. The cuts define groups of marbles.  

Example 1 (`weights = [1, 3, 5, 1]`)
Before sorting:
```
(1,3)   (3,5)   (5,1)
   4       8       6
```
After sorting:
```
   4       6       8
```
- Min Score: Take smallest (`4`)
- Max Score: Take largest (`8`)
- Result: `8 - 4 = 4`

---
Key Observations
✅ Sorting helps extract the largest and smallest cuts efficiently.  
✅ Time Complexity: `O(n log n)` (due to sorting).  
✅ Space Complexity: `O(n)`.  
*/
