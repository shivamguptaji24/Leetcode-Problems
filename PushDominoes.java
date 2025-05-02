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

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 14ms runtime which is the most used time in this problem.
*/

class Solution {
    public String pushDominoes(String dominoes) {
        int N = dominoes.length();
        char[] arr = dominoes.toCharArray();
        int[] dis = new int[N];
        int pushDist = 0;
        boolean push = false;
        for(int l=N-1;l>=0;l--){
            if(arr[l]=='L'){
                pushDist=0;
                push=true;
            }
            else if(arr[l]=='R'){
                pushDist=0;
                push=false;
                dis[l]=-1;
            } 
            
            if(push){
                dis[l]= pushDist;
                pushDist++;
            }else{
                dis[l]= Integer.MAX_VALUE;
            }
        }
        //System.out.println(Arrays.toString(dis));

        pushDist = 0;
        push = false;
        for(int i=0;i<N;i++){
            if(arr[i]=='R'){
                pushDist=0;
                push=true;
            }

            if(arr[i]=='L'){
                pushDist=0;
                push=false;
            }  

            if(arr[i] == '.'){
                if(push && pushDist == dis[i]){
                    arr[i] = '.';
                }
                else if(push && pushDist < dis[i]){
                    arr[i] = 'R';
                } else if(dis[i]!= Integer.MAX_VALUE){
                    arr[i] = 'L';
                }
            }
            if(push)pushDist++;
        }
        //System.out.println(new String(arr));
        return new String(arr);
    }
}

/*
Visualization of the above code
 Let's visualize how this version of your `pushDominoes` code works with a detailed step-by-step simulation.

---

✅ Approach Summary:

* Two-pass greedy solution using distance arrays.
* First pass (right to left): Calculate distance from nearest `'L'` domino.
* Second pass (left to right): Process `'R'` pushes and determine the final state based on relative distances to `'L'`.

---

🔍 Input Example:

```
dominoes = ".L.R...LR..L.."
```

---

🧩 Step-by-Step Execution:

---

🔄 First Pass: Right to Left

Goal: Store distance from the nearest `'L'` going leftward into `dis[]`.

Looping from `N-1` to `0`:

| Index | Char | Is Pushed Left? | pushDist | dis\[] |
| ----- | ---- | --------------- | -------- | ------ |
| 13    | '.'  | false           | -        | INF    |
| 12    | '.'  | false           | -        | INF    |
| 11    | 'L'  | true            | 0        | 0      |
| 10    | '.'  | true            | 1        | 1      |
| 9     | '.'  | true            | 2        | 2      |
| 8     | 'R'  | false (stop L)  | 0        | -1     |
| 7     | 'L'  | true            | 0        | 0      |
| 6     | '.'  | true            | 1        | 1      |
| 5     | '.'  | true            | 2        | 2      |
| 4     | '.'  | true            | 3        | 3      |
| 3     | 'R'  | false           | 0        | -1     |
| 2     | '.'  | false           | -        | INF    |
| 1     | 'L'  | true            | 0        | 0      |
| 0     | '.'  | true            | 1        | 1      |

Resulting `dis[]`:

```
[1, 0, INF, -1, 3, 2, 1, 0, -1, 2, 1, 0, INF, INF]
```

Note:

* `dis[i] == -1`: position is itself an `'R'`.
* `dis[i] == Integer.MAX_VALUE`: no `'L'` influence.

---

🔄 Second Pass: Left to Right

Goal: Simulate `'R'` pushes and compare their influence with `'L'` (distance comparison).

Logic:

* For each `'.'`:

  * If being pushed from both `'R'` and `'L'`:

    * If distance from `'R'` == distance from `'L'`, remain `'.'`.
    * If `'R'` is closer → `'R'`
    * If `'L'` is closer → `'L'`

| Index | Char | push? | pushDist | dis\[i] | Final Char |
| ----- | ---- | ----- | -------- | ------- | ---------- |
| 0     | '.'  | false | -        | 1       | L          |
| 1     | 'L'  | false | -        | 0       | L          |
| 2     | '.'  | false | -        | INF     | .          |
| 3     | 'R'  | true  | 0        | -1      | R          |
| 4     | '.'  | true  | 1        | 3       | R          |
| 5     | '.'  | true  | 2        | 2       | .          |
| 6     | '.'  | true  | 3        | 1       | L          |
| 7     | 'L'  | false | -        | 0       | L          |
| 8     | 'R'  | true  | 0        | -1      | R          |
| 9     | '.'  | true  | 1        | 2       | R          |
| 10    | '.'  | true  | 2        | 1       | L          |
| 11    | 'L'  | false | -        | 0       | L          |
| 12    | '.'  | false | -        | INF     | .          |
| 13    | '.'  | false | -        | INF     | .          |

---

✅ Final State:

```
"LL.RR.LLRRLL.."
```

---

💡 Key Insights:

* Instead of using actual forces (like the optimal force array solution), this version uses distance comparisons to determine who (L or R) reaches each domino first.
* It's an intuitive greedy strategy and still very efficient: O(n) time and space.

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 8ms runtime which is the lowest time in this problem.
*/

class Solution {
    public String pushDominoes(String dominoes) {
        int n = dominoes.length();
        char ch[] = dominoes.toCharArray();
        int i = 0;
        while( i < n) {
            if(ch[i] != '.') {
                i++;
                continue;
            }
            int j = i; 
            while( j < n && ch[j] == '.')
                j++;
            if(i-1 >= 0 && j < n) {
                if(ch[i-1] == ch[j]) {
                    int k = i;
                    while(k < j)
                        ch[k++] = ch[i-1];
                } else {
                    if(ch[i-1] == 'R') {
                        int u = i, v = j-1;
                        while(u < v) {
                            ch[u++] = 'R';
                            ch[v--] = 'L';
                        }
                    }
                }
            } else if(i-1 >= 0) {
                if(ch[i-1] == 'R') {
                    int k = i;
                    while(k < j)
                        ch[k++] = 'R';
                }
            } else if(j < n) {
                if(ch[j] == 'L') {
                    int k = i;
                    while(k < j)
                        ch[k++] = 'L';
                }
            }
            i = j;
        }
        return String.valueOf(ch);
    }
}

/*
Visualization of the above code
 Let's visualize and explain the working of this `pushDominoes` solution step by step.

---

✅ Approach Overview

This is a two-pointer greedy approach that identifies contiguous blocks of `'.'` (upright dominoes) and decides their final state based on the left and right boundaries:

* It checks the character before (`ch[i-1]`) and after (`ch[j]`) the `'.'` segment.
* It simulates three situations:

  * Both sides push in the same direction: fill the block entirely with that direction.
  * Left is `'R'` and right is `'L'`: fill symmetrically inward.
  * Only one side pushes: fill all accordingly.
  * No push on either side: leave as `'.'`.

---

🔍 Input Example:

```
dominoes = ".L.R...LR..L.."
```

---

🧩 Step-by-Step Execution

We go through the string and find stretches of `.` between non-dot characters, then determine their state:

Initial:

```
. L . R . . . L R . . L . .
^
i=0
```

---

⏩ Iteration 1: i=0 (dot segment from 0 to 0)

* `i-1 < 0` and `ch[1] = 'L'`
* → Fill index `0` with `'L'`

Result:

```
L L . R . . . L R . . L . .
      ^
```

---

⏩ Iteration 2: i=2 (dot segment from 2 to 2)

* `ch[1] = 'L'`, `ch[3] = 'R'` → Different directions
* Nothing changes

Result:

```
L L . R . . . L R . . L . .
        ^
```

---

⏩ Iteration 3: i=4 (dot segment from 4 to 6)

* `ch[3] = 'R'`, `ch[7] = 'L'` → Opposite directions
* Fill symmetrically:

  * `ch[4] = R`, `ch[6] = L`
  * `ch[5] = .` → remains if odd length

Result:

```
L L . R R . L L R . . L . .
                ^
```

---

⏩ Iteration 4: i=9 (dot segment from 9 to 10)

* `ch[8] = 'R'`, `ch[11] = 'L'`
* Symmetrically: `ch[9]=R`, `ch[10]=L`

Result:

```
L L . R R . L L R R L L . .
                        ^
```

---

⏩ Iteration 5: i=12 (dot segment from 12 to 13)

* `ch[11] = L`, `ch[13]` = out of bounds
* Only left push (`L`), fill both with `'L'`

Final Result:

```
L L . R R . L L R R L L L L
```

---

✅ Final Output:

```
"LL.RR.LLRRLL.."
```

---

💡 Summary of the Logic:

* The algorithm:

  1. Finds ranges of `'.'`
  2. Examines the boundary dominoes
  3. Updates the range based on:

     * Same push → fill
     * Opposite push → symmetric fill
     * One-sided push → directional fill
     * No push → leave as is

---
*/
