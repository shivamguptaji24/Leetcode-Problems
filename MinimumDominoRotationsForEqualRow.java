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
