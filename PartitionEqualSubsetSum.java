/*
Daily Question :
416 - Partition Equal Subset Sum

Given an integer array nums, return true if you can partition the array into two subsets such that the sum of the elements in both subsets is equal or false otherwise.

Example 1:

Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].

Example 2:

Input: nums = [1,2,3,5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.
 

Constraints:

1 <= nums.length <= 200
1 <= nums[i] <= 100
*/

class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;

        // If total sum is odd, can't partition
        if (sum % 2 != 0) return false;

        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true; // sum 0 is always possible (empty subset)

        for (int num : nums) {
            // Traverse backwards to avoid using the same num multiple times
            for (int i = target; i >= num; i--) {
                dp[i] = dp[i] || dp[i - num];
            }
        }

        return dp[target];
    }
}

/*
Visualization of the above code
 Let's walk through a visualization (step-by-step dry run) of your code with the input:

🔢 Example Input:
```
nums = [1, 5, 11, 5]
```

🔍 Step 1: Calculate Total Sum
```
sum = 1 + 5 + 11 + 5 = 22
```
✅ Total sum is even, so we continue.

🎯 Target:
```
target = sum / 2 = 11
```
We now want to check if any subset sums up to 11.

---

🧠 Initialize DP Array
```
boolean[] dp = new boolean[12]; // size = target + 1
dp[0] = true;
```

📦 `dp[i]` = true if subset with sum `i` is possible.

Initial `dp[]`:
```
[true, false, false, false, false, false, false, false, false, false, false, false]
```

---

🔁 Iterating Over nums = [1, 5, 11, 5]

---

👉 For `num = 1`:
Loop from 11 → 1:

Update:
```
dp[1] = dp[1] || dp[1 - 1] = false || true = true
```

dp becomes:
```
[true, true, false, false, false, false, false, false, false, false, false, false]
```

---

👉 For `num = 5`:
Loop from 11 → 5:

- `dp[5] = dp[5] || dp[0] = false || true = true`
- `dp[6] = dp[6] || dp[1] = false || true = true`

dp becomes:
```
[true, true, false, false, false, true, true, false, false, false, false, false]
```

---

👉 For `num = 11`:
Loop from 11 → 11:

- `dp[11] = dp[11] || dp[0] = false || true = true`

dp becomes:
```
[true, true, false, false, false, true, true, false, false, false, false, true]
```

💥 We already reached `dp[11] = true`, but let’s continue.

---

👉 For `num = 5` (again):
Loop from 11 → 5:

- `dp[10] = dp[10] || dp[5] = false || true = true`
- `dp[9] = dp[9] || dp[4] = false || false = false`
- `dp[8] = dp[8] || dp[3] = false || false = false`
- `dp[7] = dp[7] || dp[2] = false || false = false`
- `dp[6] = dp[6] || dp[1] = true || true = true`
- `dp[5] already true`

Final `dp[]`:
```
[true, true, false, false, false, true, true, false, false, false, true, true]
```

---

✅ Final Step:
```
return dp[11]; // true
```

---

💡 Meaning:
Yes, it's possible to partition `[1, 5, 11, 5]` into two subsets with equal sum (11):
- One subset: `[1, 5, 5]`
- Other subset: `[11]`

---
*/
/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 4ms runtime which is the lowest time in this problem
*/

class Solution {

    // private boolean solve(int idx, int k, int[] nums, int[][] dp){
    //     if (k == 0)
    //         return true;

    //     if (idx == 0)
    //         return nums[0] == k;

    //     if (dp[idx][k] != -1)
    //         return dp[idx][k] == 0 ? false : true;

    //     boolean notTaken = solve(idx - 1, k, nums, dp);

    //     boolean taken = false;

    //     if (nums[idx] <= k)
    //         taken = solve(idx - 1, k - nums[idx], nums, dp);

    //     dp[idx][k] = notTaken || taken ? 1 : 0;
    //     return notTaken || taken;
    // }

    Boolean canPartition(Boolean[] memo, int s, int index, int[] nums) {
        if (s == 0)
            return true;

        if (s < 0)
            return false;
       
        if (index == 0)
            return s == nums[0];
       
        if (memo[s] != null)
            return memo[s];
       
        return memo[s] = canPartition(memo, s - nums[index], index - 1, nums) || canPartition(memo, s, index - 1, nums);
    }

    public boolean canPartition(int[] nums) {

        int s = 0;

        for (int i = 0; i < nums.length; i++)
            s += nums[i];

        if (s % 2 != 0)
            return false;

        int n = nums.length;

        s /= 2;

        Boolean[] memo = new Boolean[s + 1];

        return canPartition(memo, s, n - 1, nums);

        // int sum = 0;
        // int n = nums.length;

        // for(int i : nums){
        //     sum += i;
        // }

        // if(sum % 2 == 1){
        //     return false;
        // }else{
        //     int k = sum / 2;

        //     int dp[][] = new int[n][k + 1];

        //     for (int row[] : dp)
        //         Arrays.fill(row, -1);

        //     return solve(n - 1, k, nums, dp);
        // }

    }
}

/*
Visualization of the above code
 Let's visualize your code step by step with a dry run and an explanation.

---

✅ Problem:
You are given an array of integers, and you want to determine if it can be partitioned into two subsets with equal sums.

---

🔍 Example Input:
```
nums = [1, 5, 11, 5]
```

Step 1: Total Sum Calculation
```
s = 1 + 5 + 11 + 5 = 22
```

✅ Since 22 is even, we continue.

Target sum for one subset:
```
s = s / 2 = 11
```

---

🧠 Goal:
Check if any subset from `nums` adds up to 11.

You're using **Top-down Memoized Recursion (DP)**.

---

🔁 Recursive Function:
```
canPartition(memo, s, index, nums)
```

Where:
- `s`: remaining target sum
- `index`: current index in `nums`
- `memo[s]`: memoization array

---

🧰 Initial Call:
```
canPartition(memo, 11, 3, [1,5,11,5])
```

Let’s dry run this with a recursion tree style:

---

🔽 Call: `canPartition(memo, 11, 3, nums)`
- nums[3] = 5
- Try both:
  - Include 5 → `canPartition(memo, 6, 2, nums)`
  - Exclude 5 → `canPartition(memo, 11, 2, nums)`

---

🔽 Call: `canPartition(memo, 6, 2, nums)`
- nums[2] = 11 (can't include since 11 > 6)
- Only one option:
  - `canPartition(memo, 6, 1, nums)`

---

🔽 Call: `canPartition(memo, 6, 1, nums)`
- nums[1] = 5
- Try both:
  - Include 5 → `canPartition(memo, 1, 0, nums)`
  - Exclude 5 → `canPartition(memo, 6, 0, nums)`

---

🔽 Call: `canPartition(memo, 1, 0, nums)`
- nums[0] = 1
- `s == nums[0] → true ✅`

→ So this path returns `true`.

🛑 This bubbles back up to return `true` all the way to the original call.

---

📌 Memoization Table (Boolean[] memo):

After the run, memo looks like this:
```
memo = [null, true, null, ..., true]
```
Where indices like `memo[6]`, `memo[11]` hold computed `true` values.

---

✅ Final Output:
```
return true;
```

🧠 Meaning:
Subset [1, 5, 5] sums to 11, and remaining [11] also sums to 11.

---

📌 Summary:

| Part | Logic |
|------|-------|
| Approach | Top-down DP with memoization |
| Time Complexity | `O(n * target)` where `target = sum/2` |
| Space Complexity | `O(target)` for memo array, and recursion stack |

---
*/
