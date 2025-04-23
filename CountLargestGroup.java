/*
Daily Question :
1399 - Count Largest Group

You are given an integer n.
Each number from 1 to n is grouped according to the sum of its digits.
Return the number of groups that have the largest size. 

Example 1:

Input: n = 13
Output: 4
Explanation: There are 9 groups in total, they are grouped according sum of its digits of numbers from 1 to 13:
[1,10], [2,11], [3,12], [4,13], [5], [6], [7], [8], [9].
There are 4 groups with largest size.

Example 2:

Input: n = 2
Output: 2
Explanation: There are 2 groups [1], [2] of size 1.
 

Constraints:

1 <= n <= 104
*/

class Solution {
    public int countLargestGroup(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;

        for (int i = 1; i <= n; i++) {
            int sum = digitSum(i);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
            max = Math.max(max, map.get(sum)); // keep track of largest group size
        }

        int count = 0;
        for (int size : map.values()) {
            if (size == max) {
                count++;
            }
        }

        return count;
    }

        private int digitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}

/*
Visualization of the above code
 Let’s visualize step-by-step how the Java code works for `n = 13` with full breakdown. The goal is to group numbers based on the sum of their digits and return how many groups have the largest size.

---

🔢 Step 1: Loop through 1 to 13
We compute the digit sum of each number and count occurrences:

| Number | Digit Sum | Group |
|--------|-----------|-------|
| 1      | 1         | [1]   |
| 2      | 2         | [2]   |
| 3      | 3         | [3]   |
| 4      | 4         | [4]   |
| 5      | 5         | [5]   |
| 6      | 6         | [6]   |
| 7      | 7         | [7]   |
| 8      | 8         | [8]   |
| 9      | 9         | [9]   |
| 10     | 1 (1+0)   | [1,10]|
| 11     | 2 (1+1)   | [2,11]|
| 12     | 3 (1+2)   | [3,12]|
| 13     | 4 (1+3)   | [4,13]|

---

🧮 Group counts after loop:

| Digit Sum | Count |
|-----------|-------|
| 1         | 2     |
| 2         | 2     |
| 3         | 2     |
| 4         | 2     |
| 5 to 9    | 1 each|

Maximum group size = 2

---

✅ Step 2: Count groups of size 2

From above: digit sums 1, 2, 3, 4 each have size 2 → 4 groups

---

📌 Final Answer: `4`

---

🔄 Visualization of Code Execution:

```
Map<Integer, Integer> map = new HashMap<>();
int max = 0;
```

Iterating from 1 to 13:

1. `i = 1` → digitSum = 1 → map[1] = 1 → max = 1  
2. `i = 2` → digitSum = 2 → map[2] = 1  
3. ...  
10. `i = 10` → digitSum = 1 → map[1] = 2 → max = 2  
11. `i = 11` → digitSum = 2 → map[2] = 2  
12. `i = 12` → digitSum = 3 → map[3] = 2  
13. `i = 13` → digitSum = 4 → map[4] = 2  

Now:

```
for (int size : map.values()) {
    if (size == max) count++;
}
```

`count = 4`

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 1ms runtime which is the lowest time in this problem.
*/

class Solution {
    int[][] ref = { {1}, new int[10], new int[19], new int[28] };
    
    public int countLargestGroup(int n) {
        int[] digits = {1,0,0,0}, counts = new int[37];
        int power = 0, psum = 0;
        for (int num = Math.min(n, 9999); num > 0; num /= 10)
            psum += digits[power++] += num % 10;
        for (int d = 0; d < power; d++) {
            int digit = digits[d], prevSize = ref[d].length;
            int currSize = d > 2 || ref[d + 1][ref[d + 1].length - 1] > 0 ? 0 : ref[d + 1].length;
            int limit = Math.max(d * 9 + digit, currSize);
            psum -= digit;
            for (int i = 0, val = 0; i < limit; i++) {
                val += (i < prevSize ? ref[d][i] : 0)
                     - (i >= digit && i - digit < prevSize ? ref[d][i - digit] : 0);
                counts[psum + i] += val;
                if (currSize > 0)
                    ref[d + 1][i] = (i > 0 && i - 1 < currSize ? ref[d + 1][i - 1] : 0)
                            + (i < prevSize ? ref[d][i] : 0)
                            - (i >= 10 && i - 10 < prevSize ? ref[d][i - 10] : 0);
            }
        }
        counts[0] = 0;
        int count = 0, max = 0;
        for (int x : counts)
            if (x > max) {
                max = x;
                count = 1;
            } else if (x == max)
                count++;
        return count;
    }
}

/*
Visualization of the above code
 Let's break down and visualize this compact but complex solution step-by-step using a simple example — say, `n = 13`.

---

🔍 Problem Recap:
We group numbers from `1` to `n` by the sum of their digits. Then, we count how many groups have the largest size.

For `n = 13`, we already know the digit sum groups:

```
1  -> 1
2  -> 2
3  -> 3
4  -> 4
5  -> 5
6  -> 6
7  -> 7
8  -> 8
9  -> 9
10 -> 1
11 -> 2
12 -> 3
13 -> 4
```

So the digit sum groups are:

```
Sum 1: [1, 10]
Sum 2: [2, 11]
Sum 3: [3, 12]
Sum 4: [4, 13]
Sum 5-9: [5], [6], [7], [8], [9]
```

Max group size = 2, and there are 4 such groups → Final answer: 4

---

Now, let’s decode the Java code logic (especially the optimized way it precomputes values):

---

🔢 Code Breakdown

1. ref Table Initialization
```
int[][] ref = { {1}, new int[10], new int[19], new int[28] };
```
This `ref` table helps build the number of ways digit sums can form recursively by digit positions (1-digit, 2-digit, etc.).

- `ref[0]` = `{1}` → base case
- `ref[1]` = new int[10] → for 1-digit numbers: max sum = 9
- `ref[2]` = new int[19] → for 2-digit numbers: max sum = 18
- `ref[3]` = new int[28] → for 3-digit numbers: max sum = 27

2. Digit Sum Array
```
int[] digits = {1,0,0,0}
```
The `digits` array stores the sum of digits at each digit place (units, tens, hundreds...)

We calculate `psum`, the total digit sum of `n`, which helps determine the max digit sum that could be formed.

For `n = 13`, the loop:
```
for (int num = Math.min(n, 9999); num > 0; num /= 10)
    psum += digits[power++] += num % 10;
```
This gives:
- digits = [3, 1, 0, 0] (because 13 = 1\*10 + 3)
- psum = 1 + 3 = 4

---

3. Building Combinations of Digit Sums

This loop:
```
for (int d = 0; d < power; d++) { ... }
```
Builds how digit sums could combine for multi-digit numbers using previous values in `ref`.

It uses:
- `ref[d]` = previously computed combinations
- `ref[d+1]` = current digit position
- It accumulates combinations to `counts[psum + i]`, which represents how many numbers have digit sum `psum + i`.

So by the end of this loop, `counts` contains the number of numbers that belong to each digit sum group.

---

4. Finding the Largest Group Size

```
for (int x : counts)
    if (x > max) {
        max = x;
        count = 1;
    } else if (x == max)
        count++;
```

Just like a frequency count:
- Finds the maximum frequency (`max`)
- Counts how many groups have that frequency

---

✅ Example for n = 13:

Final `counts[]` (key values):
```
counts[1] = 2 → [1, 10]
counts[2] = 2 → [2, 11]
counts[3] = 2 → [3, 12]
counts[4] = 2 → [4, 13]
...
others = 1
```

So, `max = 2`, and it occurs in 4 places → ✅ Answer = `4`

---

📌 Summary:

This approach uses:
- A recursive DP-style approach (`ref`) to build digit sum frequencies
- Efficient lookup and updating of groups
- Final scan to find how many groups have the largest count

This is a highly optimized solution for large n (up to 9999).
*/
