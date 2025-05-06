/*
Daily Question :
1920 - Build Array From Permutation

Given a zero-based permutation nums (0-indexed), build an array ans of the same length where ans[i] = nums[nums[i]] for each 0 <= i < nums.length and return it.
A zero-based permutation nums is an array of distinct integers from 0 to nums.length - 1 (inclusive). 

Example 1:

Input: nums = [0,2,1,5,3,4]
Output: [0,1,2,4,5,3]
Explanation: The array ans is built as follows: 
ans = [nums[nums[0]], nums[nums[1]], nums[nums[2]], nums[nums[3]], nums[nums[4]], nums[nums[5]]]
    = [nums[0], nums[2], nums[1], nums[5], nums[3], nums[4]]
    = [0,1,2,4,5,3]

Example 2:

Input: nums = [5,0,1,2,3,4]
Output: [4,5,0,1,2,3]
Explanation: The array ans is built as follows:
ans = [nums[nums[0]], nums[nums[1]], nums[nums[2]], nums[nums[3]], nums[nums[4]], nums[nums[5]]]
    = [nums[5], nums[0], nums[1], nums[2], nums[3], nums[4]]
    = [4,5,0,1,2,3]
 

Constraints:

1 <= nums.length <= 1000
0 <= nums[i] < nums.length
The elements in nums are distinct.
*/

class Solution {
    public int[] buildArray(int[] nums) {
        int n = nums.length;
        for(int i = 0; i < n; i++) {
            nums[i] = nums[i] + (nums[nums[i]] % n) * n;
        }
        for(int i = 0; i < n; i++) {
            nums[i] = nums[i] / n;
        }
        return nums;
    }
}

/*
Visualization of the above code
 Let's visualize how this in-place array transformation works step by step using the example:

---

🔢 Input:

```
nums = [0, 2, 1, 5, 3, 4]
n = 6
```

We want to compute: `nums[i] = nums[nums[i]]` without using extra space.

To achieve this, we encode both the old and new values in each element using:

```
nums[i] = nums[i] + (nums[nums[i]] % n) * n;
```

---

🧠 Key Idea:

We pack two values into one index:

* Original value stays in `nums[i] % n`
* New value is stored in `nums[i] / n`

Because values are in range `0 to n-1`, we can use this encoding without conflict.

---

▶️ Step-by-step Execution:

Initial Array:

```
i:      0  1  2  3  4  5
nums: [ 0, 2, 1, 5, 3, 4 ]
```

🔁 First Loop – Encoding:

```
nums[i] = nums[i] + (nums[nums[i]] % n) * n;
```

Let’s compute for each index:

* `i = 0`: `nums[0] = 0 + (nums[0] % 6) * 6 = 0 + (0 % 6) * 6 = 0`
* `i = 1`: `2 + (nums[2] % 6) * 6 = 2 + (1 % 6) * 6 = 2 + 6 = 8`
* `i = 2`: `1 + (nums[1] % 6) * 6 = 1 + (2 % 6) * 6 = 1 + 12 = 13`
* `i = 3`: `5 + (nums[5] % 6) * 6 = 5 + (4 % 6) * 6 = 5 + 24 = 29`
* `i = 4`: `3 + (nums[3] % 6) * 6 = 3 + (5 % 6) * 6 = 3 + 30 = 33`
* `i = 5`: `4 + (nums[4] % 6) * 6 = 4 + (3 % 6) * 6 = 4 + 18 = 22`

Now array becomes:

```
nums = [0, 8, 13, 29, 33, 22]
```

🔁 Second Loop – Decoding:

```
nums[i] = nums[i] / n;
```

* `nums[0] = 0 / 6 = 0`
* `nums[1] = 8 / 6 = 1`
* `nums[2] = 13 / 6 = 2`
* `nums[3] = 29 / 6 = 4`
* `nums[4] = 33 / 6 = 5`
* `nums[5] = 22 / 6 = 3`

✅ Final result:

```
[0, 1, 2, 4, 5, 3]
```

---

🧩 Summary:

| Step           | nums array              |
| -------------- | ----------------------- |
| Initial        | \[0, 2, 1, 5, 3, 4]     |
| After 1st loop | \[0, 8, 13, 29, 33, 22] |
| After 2nd loop | \[0, 1, 2, 4, 5, 3] ✅   |

---
*/
