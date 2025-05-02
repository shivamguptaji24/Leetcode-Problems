/*
Daily Question :
838 - Push Dominoes

There are n dominoes in a line, and we place each domino vertically upright. In the beginning, we simultaneously push some of the dominoes either to the left or to the right.
After each second, each domino that is falling to the left pushes the adjacent domino on the left. Similarly, the dominoes falling to the right push their adjacent dominoes standing on the right.
When a vertical domino has dominoes falling on it from both sides, it stays still due to the balance of the forces.
For the purposes of this question, we will consider that a falling domino expends no additional force to a falling or already fallen domino.
You are given a string dominoes representing the initial state where:
dominoes[i] = 'L', if the ith domino has been pushed to the left,
dominoes[i] = 'R', if the ith domino has been pushed to the right, and
dominoes[i] = '.', if the ith domino has not been pushed.
Return a string representing the final state. 

Example 1:

Input: dominoes = "RR.L"
Output: "RR.L"
Explanation: The first domino expends no additional force on the second domino.

Example 2:

Input: dominoes = ".L.R...LR..L.."
Output: "LL.RR.LLRRLL.."
 

Constraints:

n == dominoes.length
1 <= n <= 105
dominoes[i] is either 'L', 'R', or '.'.
*/

class Solution {
    public String pushDominoes(String dominoes) {
        int n = dominoes.length();
        int[] forces = new int[n];
        int force = 0;

        // Left to right pass for 'R' forces
        for (int i = 0; i < n; i++) {
            if (dominoes.charAt(i) == 'R') {
                force = n;
            } else if (dominoes.charAt(i) == 'L') {
                force = 0;
            } else {
                force = Math.max(force - 1, 0);
            }
            forces[i] += force;
        }

        // Right to left pass for 'L' forces
        force = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (dominoes.charAt(i) == 'L') {
                force = n;
            } else if (dominoes.charAt(i) == 'R') {
                force = 0;
            } else {
                force = Math.max(force - 1, 0);
            }
            forces[i] -= force;
        }

        // Construct the result
        StringBuilder result = new StringBuilder();
        for (int f : forces) {
            if (f > 0) {
                result.append('R');
            } else if (f < 0) {
                result.append('L');
            } else {
                result.append('.');
            }
        }

        return result.toString();
    }
}

/*
Visualization of the above code
 Let's visualize how this code works step by step using an example input:

---

🔍 Example Input:

```
String dominoes = ".L.R...LR..L..";
```

🧠 Step-by-Step Breakdown:

Step 1: `forces[]` array

We initialize an array `forces[]` of size `n` to store the net force acting on each domino.

---

⬅️ First Pass (Left to Right for 'R'):

We move left to right:

* When we see `'R'`, we set force to `n` (maximum influence).
* When we see `'L'`, force becomes 0 (because it blocks 'R').
* On `'.'`, the previous force weakens by 1.

| Index | Char | Force | forces\[] |
| ----- | ---- | ----- | --------- |
| 0     | '.'  | 0     | 0         |
| 1     | 'L'  | 0     | 0         |
| 2     | '.'  | 0     | 0         |
| 3     | 'R'  | 14    | 14        |
| 4     | '.'  | 13    | 13        |
| 5     | '.'  | 12    | 12        |
| 6     | '.'  | 11    | 11        |
| 7     | 'L'  | 0     | 0         |
| 8     | 'R'  | 14    | 14        |
| 9     | '.'  | 13    | 13        |
| 10    | '.'  | 12    | 12        |
| 11    | 'L'  | 0     | 0         |
| 12    | '.'  | 0     | 0         |
| 13    | '.'  | 0     | 0         |

Forces\[] after first pass:

```
[0, 0, 0, 14, 13, 12, 11, 0, 14, 13, 12, 0, 0, 0]
```

---

➡️ Second Pass (Right to Left for 'L'):

We now move right to left:

* When we see `'L'`, force = `n`
* When we see `'R'`, force = 0 (blocks 'L')
* On `'.'`, force reduces by 1

| Index | Char | Force | forces\[] (subtracting) |
| ----- | ---- | ----- | ----------------------- |
| 13    | '.'  | 0     | 0                       |
| 12    | '.'  | 0     | 0                       |
| 11    | 'L'  | 14    | -14                     |
| 10    | '.'  | 13    | -1 (12-13 = -1)         |
| 9     | '.'  | 12    | 1 (13-12 = 1)           |
| 8     | 'R'  | 0     | 14                      |
| 7     | 'L'  | 14    | -14                     |
| 6     | '.'  | 13    | -2 (11-13 = -2)         |
| 5     | '.'  | 12    | 0 (12-12 = 0)           |
| 4     | '.'  | 11    | 2 (13-11 = 2)           |
| 3     | 'R'  | 0     | 14                      |
| 2     | '.'  | 0     | 0                       |
| 1     | 'L'  | 14    | -14                     |
| 0     | '.'  | 13    | -13                     |

Final forces\[] after subtracting:

```
[-13, -14, 0, 14, 2, 0, -2, -14, 14, 1, -1, -14, 0, 0]
```

---

🔄 Final Result Based on Forces:

| Force | Result |
| ----- | ------ |
| -13   | L      |
| -14   | L      |
| 0     | .      |
| 14    | R      |
| 2     | R      |
| 0     | .      |
| -2    | L      |
| -14   | L      |
| 14    | R      |
| 1     | R      |
| -1    | L      |
| -14   | L      |
| 0     | .      |
| 0     | .      |

✅ Final Output:

```
"LL.RR.LLRRLL.."
```

---

🧠 Summary:

* We simulate force propagation from `'R'` and `'L'`.
* Use two sweeps (left-to-right and right-to-left).
* The `forces[]` array gives us a way to determine the final state with no explicit recursion or queue, keeping the algorithm efficient.
*/
