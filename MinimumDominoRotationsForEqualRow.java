/*
Daily Question :
1007 - Minimum Domino Rotations For Equal Row

In a row of dominoes, tops[i] and bottoms[i] represent the top and bottom halves of the ith domino. (A domino is a tile with two numbers from 1 to 6 - one on each half of the tile.)
We may rotate the ith domino, so that tops[i] and bottoms[i] swap values.
Return the minimum number of rotations so that all the values in tops are the same, or all the values in bottoms are the same.
If it cannot be done, return -1.

Example 1:

Input: tops = [2,1,2,4,2,2], bottoms = [5,2,6,2,3,2]
Output: 2
Explanation: 
The first figure represents the dominoes as given by tops and bottoms: before we do any rotations.
If we rotate the second and fourth dominoes, we can make every value in the top row equal to 2, as indicated by the second figure.

Example 2:

Input: tops = [3,5,1,2,3], bottoms = [3,6,3,3,4]
Output: -1
Explanation: 
In this case, it is not possible to rotate the dominoes to make one row of values equal.
 

Constraints:

2 <= tops.length <= 2 * 104
bottoms.length == tops.length
1 <= tops[i], bottoms[i] <= 6
*/

class Solution {
    public int minDominoRotations(int[] tops, int[] bottoms) {
        int result = check(tops[0], tops, bottoms);
        if (result != -1 || tops[0] == bottoms[0]) {
            return result;
        } else {
            return check(bottoms[0], tops, bottoms);
        }
    }

    private int check(int target, int[] tops, int[] bottoms) {
        int rotationsTop = 0;
        int rotationsBottom = 0;

        for (int i = 0; i < tops.length; i++) {
            // If target is not present on either side, not possible
            if (tops[i] != target && bottoms[i] != target) {
                return -1;
            } else if (tops[i] != target) {
                rotationsTop++; // Rotate top[i] to target
            } else if (bottoms[i] != target) {
                rotationsBottom++; // Rotate bottom[i] to target
            }
        }

        return Math.min(rotationsTop, rotationsBottom);
    }
}

/*
Visualization of the above code
 Let's walk through and visualize the logic of your code using a step-by-step explanation and an example input.

---

🔍 Goal

Make all elements in either the `tops` or `bottoms` array the same by rotating some dominoes.

---

🧩 Domino Representation

Each domino has two numbers:

* `tops[i]` is the top number of the `i-th` domino
* `bottoms[i]` is the bottom number of the `i-th` domino

You are allowed to swap these numbers (i.e., rotate the domino).

---

🧪 Example Input

```
int[] tops =    {2, 1, 2, 4, 2, 2};
int[] bottoms = {5, 2, 6, 2, 3, 2};
```

Your method checks:

1. Can all values be made equal to `tops[0]` (which is `2`)?
2. If not, can all values be made equal to `bottoms[0]` (which is `5`)?

---

👣 Step-by-Step Execution

✅ Step 1: Try to make all values == `2` (`tops[0]`)

| Index | tops\[i] | bottoms\[i] | Can we keep `2`?    | Rotate Top? | Rotate Bottom? |
| ----- | -------- | ----------- | ------------------- | ----------- | -------------- |
| 0     | 2        | 5           | ✅ Yes (top is 2)    | No          | No             |
| 1     | 1        | 2           | ✅ Yes (bottom is 2) | ✅ Yes       | No             |
| 2     | 2        | 6           | ✅ Yes (top is 2)    | No          | No             |
| 3     | 4        | 2           | ✅ Yes (bottom is 2) | ✅ Yes       | No             |
| 4     | 2        | 3           | ✅ Yes (top is 2)    | No          | No             |
| 5     | 2        | 2           | ✅ Yes (both are 2)  | No          | No             |

👉 Total Rotations:

* `rotationsTop = 2` (at i = 1, 3)
* `rotationsBottom = 0`

→ Minimum = `2`

---

Since `2` worked, you return 2 as the final answer.

---

🚫 If First Try Fails

If `tops[0]` doesn’t work, your code checks `bottoms[0]` as a candidate.

This ensures you're checking only two values, making it optimal.

---

✅ Visualization Summary

```
Check candidate values: tops[0] and bottoms[0]
↓
Try to make all values in tops or bottoms equal to the candidate
↓
For each index:
    - If candidate is on top → OK
    - If candidate is on bottom → can rotate
    - If not in either → return -1
↓
Track how many rotations needed
↓
Return minimum number of rotations
```
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 4ms runtime which is the most used time in this problem.
*/

class Solution {
    public int minDominoRotations(int[] tops, int[] bottoms) {
        int count1 = solve(tops, bottoms, tops[0]);
        if(count1 != -1)
            return count1;
        return solve(tops, bottoms, bottoms[0]);
        
    }
    int solve(int[] tops, int[] bottoms, int target){
        int flipTop = 0;
        int flipBottom = 0;
        int n = tops.length;

        for(int i=0;i<n;i++){
            if(tops[i] != target && bottoms[i] != target)
                return -1;
            else if(tops[i] != target){
                flipTop++;
            }else if(bottoms[i] != target){
                flipBottom++;
            }
        }
        int res = Math.min(flipTop, flipBottom);
        return res;
    }
}

/*
Visualization of the above code
 Let's visualize and break down this Java code step by step using a sample input, flow explanation, and table-based simulation.

---

🔍 Purpose

This code finds the minimum number of rotations required to make all values in either the `tops` or `bottoms` array the same across all dominoes. If it's impossible, it returns `-1`.

---

🔢 Input Example

```
tops    = [2, 1, 2, 4, 2, 2]
bottoms = [5, 2, 6, 2, 3, 2]
```

---

📦 Step-by-Step Breakdown

➤ `minDominoRotations(tops, bottoms)`

* Tries to make all values equal to `tops[0] = 2` by calling `solve(...)`
* If possible, return result
* If not, try `bottoms[0] = 5` by calling `solve(...)` again

---

🧮 Simulation Table for `target = 2`

| i | tops\[i] | bottoms\[i] | Contains 2? | Rotate Needed | flipTop | flipBottom |
| - | -------- | ----------- | ----------- | ------------- | ------- | ---------- |
| 0 | 2        | 5           | ✅ Top       | ❌             | 0       | 0          |
| 1 | 1        | 2           | ✅ Bottom    | ✔ Top         | 1       | 0          |
| 2 | 2        | 6           | ✅ Top       | ❌             | 1       | 0          |
| 3 | 4        | 2           | ✅ Bottom    | ✔ Top         | 2       | 0          |
| 4 | 2        | 3           | ✅ Top       | ❌             | 2       | 0          |
| 5 | 2        | 2           | ✅ Both      | ❌             | 2       | 0          |

→ `flipTop = 2`, `flipBottom = 0`
→ Result = `min(2, 0) = 0`
But note: flips are counted to make either row uniform, so here, to make bottoms all 2, no rotation is needed.

---

🔄 Flowchart (Textual Form)

```
Start
│
├─► Try candidate = tops[0]
│    └─ Check all dominoes:
│         ├─ If neither top nor bottom = candidate → return -1
│         ├─ If top ≠ candidate → flipTop++
│         └─ If bottom ≠ candidate → flipBottom++
│    └─ Return min(flipTop, flipBottom)
│
├─► If result ≠ -1 → return result
│
└─► Else try candidate = bottoms[0] and repeat above
```

---

✅ Code Logic in Simple English

* Try using the first top value (`tops[0]`) as the common number.
* If not possible, try the first bottom value (`bottoms[0]`).
* For each attempt:

  * Go through all dominoes.
  * If the target number is missing from both top and bottom of a domino → impossible.
  * If the top is not equal to the target → a flip is required for top.
  * If the bottom is not equal to the target → a flip is required for bottom.
* Return the minimum flips needed (either all in top or all in bottom).

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 2ms runtime which is the lowest time in this problem.
*/


class Solution {
    private int helper(int[] tops, int[] bottoms, int val) {
        int top_res = 0, bottom_res = 0;
        for (int i = 0; i < tops.length; i++) {
            if (tops[i] != val && bottoms[i] != val) {
                return -1;
            } else if (tops[i] != val) {
                top_res++;
            } else if (bottoms[i] != val) {
                bottom_res++;
            }
        }
        return Math.min(top_res, bottom_res);
    }



    public int minDominoRotations(int[] tops, int[] bottoms) {
        
        
        int ans = -1;
        for (int i = 1; i < 7; i++) {
            int cur_ans = helper(tops, bottoms, i);

            if (cur_ans != -1 && (ans == -1 || ans > cur_ans)) {
                ans = cur_ans;
            }
        }
        return ans;
    }
}

/*
Visualization of the above code
 Let's visualize and explain this version of the `minDominoRotations` solution step by step. This approach is slightly different—it tries all possible values from 1 to 6 (as domino values range from 1 to 6).

---

🔍 Goal

To make all domino tops or all domino bottoms equal to the same value by rotating as few dominoes as needed.
If impossible, return `-1`.

---

🧠 Key Idea

Check for each value from `1` to `6`:

* Can we make all the values in `tops` or `bottoms` equal to this value (`val`)?
* If yes, count how many rotations are needed and keep track of the minimum.

---

📦 Sample Input

```
tops    = [2, 1, 2, 4, 2, 2];
bottoms = [5, 2, 6, 2, 3, 2];
```

---

🔁 Code Flow

1. `minDominoRotations(...)`

* Loops from `val = 1` to `6`
* Calls `helper(tops, bottoms, val)` for each
* Tracks the minimum `cur_ans` where result is not `-1`

2. `helper(...)` Function

For a given target value `val`:

* Initialize `top_res` and `bottom_res` to 0
* For each domino:

  * If neither side has `val` → return `-1` (not possible)
  * If top ≠ val → increment `top_res` (we need to rotate this domino to top)
  * If bottom ≠ val → increment `bottom_res`
* Return the minimum of `top_res` and `bottom_res`

---

🧮 Simulation for Each `val`

Let’s simulate `helper(tops, bottoms, val)` for a few values:

`val = 2`

| i | tops\[i] | bottoms\[i] | Match? | RotateTop | RotateBottom |
| - | -------- | ----------- | ------ | --------- | ------------ |
| 0 | 2        | 5           | ✅      | No        | No           |
| 1 | 1        | 2           | ✅      | ✔ Yes     | No           |
| 2 | 2        | 6           | ✅      | No        | No           |
| 3 | 4        | 2           | ✅      | ✔ Yes     | No           |
| 4 | 2        | 3           | ✅      | No        | No           |
| 5 | 2        | 2           | ✅      | No        | No           |

→ `top_res = 2`, `bottom_res = 0` → return `0`

`val = 1`

| i | tops\[i] | bottoms\[i] | Match? |
| - | -------- | ----------- | ------ |
| 0 | 2        | 5           | ❌      |

→ return `-1`

This repeats for values 1 to 6.

---

📊 Final Decision

The minimum rotations among all `val` = 1 to 6 is taken.
For the input `[2,1,2,4,2,2]`, `[5,2,6,2,3,2]`, the best is when `val = 2`, requiring 0 rotations in `bottoms`.

---

✅ Final Output

```
Output: 2
```

Because to make all tops equal to `2`, we need to rotate 2 dominoes.

---

📌 Summary of Logic

* Try all values `1` to `6`
* For each, check if it's possible to make all tops or bottoms that value
* Count how many rotations are needed
* Return the minimum such value

---
*/
