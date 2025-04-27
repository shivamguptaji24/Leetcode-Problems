/*
Daily Question :
3392 - Count Subarrays of Length Three With a Condition

Given an integer array nums, return the number of subarrays of length 3 such that the sum of the first and third numbers equals exactly half of the second number.

Example 1:

Input: nums = [1,2,1,4,1]

Output: 1

Explanation:

Only the subarray [1,4,1] contains exactly 3 elements where the sum of the first and third numbers equals half the middle number.

Example 2:

Input: nums = [1,1,1]

Output: 0

Explanation:

[1,1,1] is the only subarray of length 3. However, its first and third numbers do not add to half the middle number.

 

Constraints:

3 <= nums.length <= 100
-100 <= nums[i] <= 100
*/

class Solution {
    public int countSubarrays(int[] nums) {
        int ans = 0;
        for (int i = 1; i + 1 < nums.length; ++i) {
            if ((nums[i - 1] + nums[i + 1]) * 2 == nums[i]) {
                ++ans;
            }
        }
        return ans;
    }
}

/*
Visualization of the above code
 Let’s visualize your code step-by-step:

---

Code:
```
class Solution {
    public int countSubarrays(int[] nums) {
        int ans = 0;
        for (int i = 1; i + 1 < nums.length; ++i) {
            if ((nums[i - 1] + nums[i + 1]) * 2 == nums[i]) {
                ++ans;
            }
        }
        return ans;
    }
}
```

---

🛠 How it works:

1. `ans = 0` → to store the number of valid subarrays.
2. `for (int i = 1; i + 1 < nums.length; ++i)`:
   - Start from index `1` and go up to `length - 2` because you need a triplet: previous (`i-1`), current (`i`), next (`i+1`).
3. For each index `i`, check:
   - `(nums[i-1] + nums[i+1]) * 2 == nums[i]`
   - **Important:** you are checking if twice the sum of first and third equals the middle number.

4. If yes, increment `ans++`.

---

📊 Visualization on Example:

Suppose:
```
nums = [1, 2, 1, 4, 1]
Indexes:  0  1  2  3  4
```

Step-by-step:
- `i = 1`:
  - nums[0] + nums[2] = 1 + 1 = 2
  - 2 * 2 = 4
  - 4 ≠ nums[1] (which is 2) → ❌
- `i = 2`:
  - nums[1] + nums[3] = 2 + 4 = 6
  - 6 * 2 = 12
  - 12 ≠ nums[2] (which is 1) → ❌
- `i = 3`:
  - nums[2] + nums[4] = 1 + 1 = 2
  - 2 * 2 = 4
  - 4 == nums[3] (which is 4) → ✅ count++

Finally:  
ans = 1

Return `1`.

---

🔥 Quick Flowchart:

```
Start
 |
Initialize ans = 0
 |
Loop i from 1 to nums.length-2:
    |
    Check if (nums[i-1] + nums[i+1]) * 2 == nums[i]
    |
    If YES → ans++
    If NO → continue
 |
Return ans
```

---

⚡ Important Note:
In your version:
- Condition is `(sum of 1st + 3rd) * 2 == 2nd`.
- This differs slightly from the original problem you posted earlier (which was `(first + third) == half of second`).
  
> Here you're checking twice the sum equals middle number, instead of sum equals half middle.

If you want me to fix it fully to match the earlier statement (half of second equals sum), just let me know. 🔥

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 2ms runtime which is the second lowest time in this problem.
*/

class Solution {
    public int countSubarrays(int[] nums) {
        int n  = nums.length;
        int count = 0;
        for(int i = 0;i<=n-3;i++){
            int a = nums[i];
            int b = nums[i+1];
            int c= nums[i+2];
            if(b%2==0 && (a+c)==(b/2))count++;
        }
        return count;
    }
}

/*
Visualization of the above code
 Let's visualize your new code step-by-step:

---

🛠 Code:
```
class Solution {
    public int countSubarrays(int[] nums) {
        int n  = nums.length;
        int count = 0;
        for (int i = 0; i <= n - 3; i++) {
            int a = nums[i];
            int b = nums[i + 1];
            int c = nums[i + 2];
            if (b % 2 == 0 && (a + c) == (b / 2))
                count++;
        }
        return count;
    }
}
```

---

📚 What's happening?

1. `n = nums.length`
2. `count = 0` → Initialize the count of valid subarrays.
3. Loop from `i = 0` to `i = n-3`:
   - Take three numbers: `a = nums[i]`, `b = nums[i+1]`, `c = nums[i+2]`.
   - Check:
     - `b` should be **even** (`b % 2 == 0`).
     - `a + c == b / 2`.
4. If both conditions satisfy → increment `count++`.
5. After the loop, return `count`.

---

📊 Example Walkthrough:

Suppose:
```
nums = [1, 2, 1, 4, 1]
Indexes:  0  1  2  3  4
```

Loop steps:

| i | a = nums[i] | b = nums[i+1] | c = nums[i+2] | b even? | (a + c) == (b / 2)? | Action |
|:-:|:-----------:|:------------:|:-------------:|:-------:|:------------------:|:------:|
| 0 | 1           | 2            | 1             | Yes     | 1 + 1 = 2 ≠ 1       | ❌ No |
| 1 | 2           | 1            | 4             | No      | -                  | ❌ No |
| 2 | 1           | 4            | 1             | Yes     | 1 + 1 = 2 == 2      | ✅ count++ |

Final count = 1.

---

🔥 Quick Visual Flow:

```
Start
 |
Initialize n = nums.length, count = 0
 |
Loop i from 0 to n-3
 |
Take a = nums[i], b = nums[i+1], c = nums[i+2]
 |
Check:
    b is even? (b % 2 == 0)
    AND
    (a + c) == (b / 2)
 |
If yes → count++
 |
End Loop
 |
Return count
```

---

⚡ Key Points:
- Only subarrays of size 3 are considered.
- `b` (the middle element) must be even to be divisible by 2 cleanly.
- `(first + third)` must equal half of the middle.

---

🎨 Bonus Visual with Boxes:

Imagine the window sliding:

```
Window 1: [1, 2, 1]
           ↑  ↑  ↑
           a  b  c

Window 2: [2, 1, 4]
           ↑  ↑  ↑
           a  b  c

Window 3: [1, 4, 1]
           ↑  ↑  ↑
           a  b  c
```

At Window 3:
- Middle element `b = 4` (even ✅)
- Sum of first and third: `1 + 1 = 2`
- Half of middle: `4/2 = 2`
- ✅ They match → count++

---
*/
