/*
Daily Question :
2845 - Count of Interesting Subarrays

You are given a 0-indexed integer array nums, an integer modulo, and an integer k.
Your task is to find the count of subarrays that are interesting.
A subarray nums[l..r] is interesting if the following condition holds:
Let cnt be the number of indices i in the range [l, r] such that nums[i] % modulo == k. Then, cnt % modulo == k.
Return an integer denoting the count of interesting subarrays.
Note: A subarray is a contiguous non-empty sequence of elements within an array. 

Example 1:

Input: nums = [3,2,4], modulo = 2, k = 1
Output: 3
Explanation: In this example the interesting subarrays are: 
The subarray nums[0..0] which is [3]. 
- There is only one index, i = 0, in the range [0, 0] that satisfies nums[i] % modulo == k. 
- Hence, cnt = 1 and cnt % modulo == k.  
The subarray nums[0..1] which is [3,2].
- There is only one index, i = 0, in the range [0, 1] that satisfies nums[i] % modulo == k.  
- Hence, cnt = 1 and cnt % modulo == k.
The subarray nums[0..2] which is [3,2,4]. 
- There is only one index, i = 0, in the range [0, 2] that satisfies nums[i] % modulo == k. 
- Hence, cnt = 1 and cnt % modulo == k. 
It can be shown that there are no other interesting subarrays. So, the answer is 3.

Example 2:

Input: nums = [3,1,9,6], modulo = 3, k = 0
Output: 2
Explanation: In this example the interesting subarrays are: 
The subarray nums[0..3] which is [3,1,9,6]. 
- There are three indices, i = 0, 2, 3, in the range [0, 3] that satisfy nums[i] % modulo == k. 
- Hence, cnt = 3 and cnt % modulo == k. 
The subarray nums[1..1] which is [1]. 
- There is no index, i, in the range [1, 1] that satisfies nums[i] % modulo == k. 
- Hence, cnt = 0 and cnt % modulo == k. 
It can be shown that there are no other interesting subarrays. So, the answer is 2.
 

Constraints:

1 <= nums.length <= 105 
1 <= nums[i] <= 109
1 <= modulo <= 109
0 <= k < modulo
*/

class Solution {
    public long countInterestingSubarrays(List<Integer> nums, int modulo, int k) {
        Map<Integer, Long> map = new HashMap<>();
        map.put(0, 1L); // Initial prefix sum mod value

        long result = 0;
        int prefix = 0;

        for (int num : nums) {
            if (num % modulo == k) {
                prefix++;
            }

            // Target prefix to form a valid subarray
            int need = (prefix - k + modulo) % modulo;

            result += map.getOrDefault(need, 0L);

            int modVal = prefix % modulo;
            map.put(modVal, map.getOrDefault(modVal, 0L) + 1);
        }

        return result;
    }
}

/*
Visualization of the above code
 Let's visualize how the code for counting interesting subarrays works step by step using an example:

---

📘 Example

```
nums = [3, 1, 9, 6], modulo = 3, k = 0
```

We want to find subarrays where:

> Number of elements in subarray such that `nums[i] % 3 == 0`  
> is congruent to `0 modulo 3`.

---

🔁 Step-by-Step Execution

We maintain:

- `prefix`: How many values so far satisfy `nums[i] % modulo == k`
- `map`: Keeps count of prefix values mod `modulo`
- We initialize `map = { 0: 1 }` since 0 prefix count is valid.

🧮 Initial state:
```
map = { 0: 1 }
prefix = 0
result = 0
```

---

🔹 Iteration 1: `num = 3`

- `3 % 3 == 0` ✅ → increase `prefix = 1`
- Need: `(prefix - k + modulo) % modulo = (1 - 0 + 3) % 3 = 1`
- `map[1]` = 0 → result += 0
- Update `map[1] += 1` → map becomes: `{ 0: 1, 1: 1 }`

```
Valid subarrays found so far = 0
```

---

🔹 Iteration 2: `num = 1`

- `1 % 3 == 1` ❌ → `prefix` stays 1
- Need: `(1 - 0 + 3) % 3 = 1`
- `map[1]` = 1 → result += 1 → `result = 1`
- Update `map[1] += 1` → `{ 0: 1, 1: 2 }`

```
Valid subarrays found: [1]
```

---

🔹 Iteration 3: `num = 9`

- `9 % 3 == 0` ✅ → increase `prefix = 2`
- Need: `(2 - 0 + 3) % 3 = 2`
- `map[2]` = 0 → result += 0 → `result = 1`
- Update `map[2] += 1` → `{ 0: 1, 1: 2, 2: 1 }`

```
Valid subarrays found: [1]
```

---

🔹 Iteration 4: `num = 6`

- `6 % 3 == 0` ✅ → `prefix = 3`
- Need: `(3 - 0 + 3) % 3 = 0`
- `map[0]` = 1 → result += 1 → `result = 2`
- Update `map[0] += 1` → `{ 0: 2, 1: 2, 2: 1 }`

```
Valid subarrays found: [1], [3,1,9,6]
```

---

✅ Final `result = 2`

Answer matches the example ✅

---

🧠 Visualization Summary

| Index | num | prefix | needed_mod | result | map (prefix % modulo) |
|-------|-----|--------|------------|--------|------------------------|
| 0     | 3   | 1      | 1          | 0      | {0:1, 1:1}             |
| 1     | 1   | 1      | 1          | 1      | {0:1, 1:2}             |
| 2     | 9   | 2      | 2          | 1      | {0:1, 1:2, 2:1}        |
| 3     | 6   | 3      | 0          | 2      | {0:2, 1:2, 2:1}        |

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 7ms runtime which is the lowest time in this problem.
*/

class Solution {
    public long countInterestingSubarrays(List<Integer> nums, int modulo, int k) {

        int n = nums.size();
        if(k > n) return 0;
         
        int[] count = new int[n + 1];
        count[0] = 1;

        long ans = 0;
        int sum = 0;
        for(int x:nums){
            x %= modulo;
            if(x == k)
                 ++sum;

            sum %= modulo;
            int r = sum - k;
            if(r < 0) r += modulo;
            if(r < n)
               ans += count[r];

            count[sum]++;
        }
        
        return ans;
    }
}

/*
Visualization of the above code
 Let’s break down and visualize the execution of your code step by step with an example, so you can understand how it counts interesting subarrays.

---

🧪 Example Input:
```
nums = [3, 1, 9, 6], modulo = 3, k = 0
```

---

🔍 What is an Interesting Subarray?

A subarray `nums[l..r]` is **interesting** if:

```
count of elements i in [l, r] where nums[i] % modulo == k
→ let's call this count "cnt"
→ then cnt % modulo == k
```

---

🧠 Key Variables

| Variable | Purpose |
|---------|---------|
| `sum`   | Keeps cumulative count of how many times `nums[i] % modulo == k` |
| `count` | Frequency of different `sum % modulo` values |
| `ans`   | Final answer: number of interesting subarrays |

---

🧮 Step-by-step Execution

Initialization:

```
count = new int[n+1]; // count[0] = 1
ans = 0
sum = 0
```

---

🔁 Iteration by each element:

✅ `x = 3 → x % 3 = 0 == k → sum++`

```
sum = 1
sum % modulo = 1
r = (1 - 0 + 3) % 3 = 1
count[1] = 0 → ans = 0
Increment count[1] → count = [1,1,0,0,0]
```

---

🔸 `x = 1 → x % 3 = 1 != k → sum unchanged`

```
sum = 1
sum % modulo = 1
r = (1 - 0 + 3) % 3 = 1
count[1] = 1 → ans = 1
Increment count[1] → count = [1,2,0,0,0]
```

---

🔸 `x = 9 → x % 3 = 0 == k → sum++`

```
sum = 2
sum % modulo = 2
r = (2 - 0 + 3) % 3 = 2
count[2] = 0 → ans = 1
Increment count[2] → count = [1,2,1,0,0]
```

---

🔸 `x = 6 → x % 3 = 0 == k → sum++`

```
sum = 3
sum % modulo = 0
r = (0 - 0 + 3) % 3 = 0
count[0] = 1 → ans = 2
Increment count[0] → count = [2,2,1,0,0]
```

---

✅ Final `ans = 2`

Same output as expected. ✅

---

🧾 Visual Table Summary:

| Step | x | x%mod | x==k | sum | sum%mod | r   | count[r] | ans  | count[sum] after |
|------|---|-------|------|-----|----------|-----|-----------|------|------------------|
| 1    | 3 | 0     | ✅   | 1   | 1        | 1   | 0         | 0    | count[1] = 1      |
| 2    | 1 | 1     | ❌   | 1   | 1        | 1   | 1         | 1    | count[1] = 2      |
| 3    | 9 | 0     | ✅   | 2   | 2        | 2   | 0         | 1    | count[2] = 1      |
| 4    | 6 | 0     | ✅   | 3   | 0        | 0   | 1         | 2    | count[0] = 2      |

---

🧠 Intuition

You're counting how often a prefix sum modulo matches a previously seen value that would make a subarray end at the current index such that:
```
(count of nums[i] % mod == k) % mod == k
```

---
*/
