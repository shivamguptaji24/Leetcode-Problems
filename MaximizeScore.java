/*
Daily Question :
2818 - Apply Operations To Maximize Score

You are given an array nums of n positive integers and an integer k.
Initially, you start with a score of 1. You have to maximize your score by applying the following operation at most k times:
Choose any non-empty subarray nums[l, ..., r] that you haven't chosen previously.
Choose an element x of nums[l, ..., r] with the highest prime score. If multiple such elements exist, choose the one with the smallest index.
Multiply your score by x.
Here, nums[l, ..., r] denotes the subarray of nums starting at index l and ending at the index r, both ends being inclusive.
The prime score of an integer x is equal to the number of distinct prime factors of x. For example, the prime score of 300 is 3 since 300 = 2 * 2 * 3 * 5 * 5.
Return the maximum possible score after applying at most k operations.
Since the answer may be large, return it modulo 109 + 7. 

Example 1:

Input: nums = [8,3,9,3,8], k = 2
Output: 81
Explanation: To get a score of 81, we can apply the following operations:
- Choose subarray nums[2, ..., 2]. nums[2] is the only element in this subarray. Hence, we multiply the score by nums[2]. The score becomes 1 * 9 = 9.
- Choose subarray nums[2, ..., 3]. Both nums[2] and nums[3] have a prime score of 1, but nums[2] has the smaller index. Hence, we multiply the score by nums[2]. The score becomes 9 * 9 = 81.
It can be proven that 81 is the highest score one can obtain.

Example 2:

Input: nums = [19,12,14,6,10,18], k = 3
Output: 4788
Explanation: To get a score of 4788, we can apply the following operations: 
- Choose subarray nums[0, ..., 0]. nums[0] is the only element in this subarray. Hence, we multiply the score by nums[0]. The score becomes 1 * 19 = 19.
- Choose subarray nums[5, ..., 5]. nums[5] is the only element in this subarray. Hence, we multiply the score by nums[5]. The score becomes 19 * 18 = 342.
- Choose subarray nums[2, ..., 3]. Both nums[2] and nums[3] have a prime score of 2, but nums[2] has the smaller index. Hence, we multipy the score by nums[2]. The score becomes 342 * 14 = 4788.
It can be proven that 4788 is the highest score one can obtain.
 

Constraints:

1 <= nums.length == n <= 105
1 <= nums[i] <= 105
1 <= k <= min(n * (n + 1) / 2, 109)
*/

class Solution {
    private final int mod = (int) 1e9 + 7;

    public int maximumScore(List<Integer> nums, int k) {
        int n = nums.size();
        int[][] arr = new int[n][0];
        for (int i = 0; i < n; ++i) {
            arr[i] = new int[] {i, primeFactors(nums.get(i)), nums.get(i)};
        }
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(left, -1);
        Arrays.fill(right, n);
        Deque<Integer> stk = new ArrayDeque<>();
        for (int[] e : arr) {
            int i = e[0], f = e[1];
            while (!stk.isEmpty() && arr[stk.peek()][1] < f) {
                stk.pop();
            }
            if (!stk.isEmpty()) {
                left[i] = stk.peek();
            }
            stk.push(i);
        }
        stk.clear();
        for (int i = n - 1; i >= 0; --i) {
            int f = arr[i][1];
            while (!stk.isEmpty() && arr[stk.peek()][1] <= f) {
                stk.pop();
            }
            if (!stk.isEmpty()) {
                right[i] = stk.peek();
            }
            stk.push(i);
        }
        Arrays.sort(arr, (a, b) -> b[2] - a[2]);
        long ans = 1;
        for (int[] e : arr) {
            int i = e[0], x = e[2];
            int l = left[i], r = right[i];
            long cnt = (long) (i - l) * (r - i);
            if (cnt <= k) {
                ans = ans * qpow(x, cnt) % mod;
                k -= cnt;
            } else {
                ans = ans * qpow(x, k) % mod;
                break;
            }
        }
        return (int) ans;
    }

    private int primeFactors(int n) {
        int i = 2;
        Set<Integer> ans = new HashSet<>();
        while (i <= n / i) {
            while (n % i == 0) {
                ans.add(i);
                n /= i;
            }
            ++i;
        }
        if (n > 1) {
            ans.add(n);
        }
        return ans.size();
    }

    private int qpow(long a, long n) {
        long ans = 1;
        for (; n > 0; n >>= 1) {
            if ((n & 1) == 1) {
                ans = ans * a % mod;
            }
            a = a * a % mod;
        }
        return (int) ans;
    }
}

/*
Visualization of the above code
 Let's break down and visualize the code step by step to understand its logic.

---

Overview of the Approach
1. Calculate Prime Score  
   - For each number in `nums`, compute its prime score (number of distinct prime factors).
   - Store `(index, prime_score, value)` in `arr`.

2. Find Next Greater Prime Score Indices  
   - Using monotonic stacks, find the nearest index to the left (`left[i]`) and right (`right[i]`) where the prime score is greater.

3. Sort by Value (Descending Order)  
   - Sort elements by their value in descending order.
   - The idea is to pick the largest possible value first.

4. Select Elements to Maximize Score  
   - Calculate the contribution of each element in possible subarrays.
   - If its contribution (`cnt`) is within `k`, multiply the score by the element raised to `cnt` power.
   - If `cnt` exceeds `k`, use only `k` operations and break.

---

Step-by-Step Execution with Example
Input
```
nums = [8,3,9,3,8], k = 2
```

Step 1: Calculate Prime Scores
- Prime score of each number:
  - `8 = 2 * 2 * 2` → Distinct prime factors `{2}` → Score = 1
  - `3 = 3` → Distinct prime factors `{3}` → Score = 1
  - `9 = 3 * 3` → Distinct prime factors `{3}` → Score = 1
  - `3 = 3` → Distinct prime factors `{3}` → Score = 1
  - `8 = 2 * 2 * 2` → Distinct prime factors `{2}` → Score = 1

- `arr` stores:
  ```
  arr = [
      {0, 1, 8},
      {1, 1, 3},
      {2, 1, 9},
      {3, 1, 3},
      {4, 1, 8}
  ]
  ```

Step 2: Compute `left[]` and `right[]` (Next Greater Prime Score Indices)
Since all prime scores are 1, no greater values exist:
```
left  = [-1, -1, -1, -1, -1]
right = [5, 5, 5, 5, 5]
```

Step 3: Sort by Value (Descending)
```
arr = [
    {2, 1, 9},
    {0, 1, 8},
    {4, 1, 8},
    {1, 1, 3},
    {3, 1, 3}
]
```
Sorted by value in descending order.

Step 4: Compute Contribution and Multiply Score
1. Choose `9` at index `2`  
   - `left[2] = -1`, `right[2] = 5`
   - `cnt = (2 - (-1)) * (5 - 2) = 3 * 3 = 9`  
   - `9 > k`, so use `k=2` operations:
   - Multiply score:  
     \[
     ans = 1 \times 9^2 = 81
     \]
   - `k = 0`, so stop.

---

Final Output
```
Output = 81
```

---

Visualization
```
nums = [ 8,  3,  9,  3,  8]
index = [ 0,  1,  2,  3,  4]
score = [ 1,  1,  1,  1,  1]

Sorted by value:
  Value 9 at index 2 is chosen first
  Possible count = 9, but k = 2
  Multiply by 9^2 → 81

Final Answer: 81
```

This approach efficiently finds the best numbers to maximize the score while using at most `k` operations.
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 114ms runtime which is the lowest time in this problem
*/
