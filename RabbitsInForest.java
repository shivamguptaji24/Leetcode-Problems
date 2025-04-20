/*
Daily Question :
781 - Rabbits in Forest

There is a forest with an unknown number of rabbits. We asked n rabbits "How many rabbits have the same color as you?" and collected the answers in an integer array answers where answers[i] is the answer of the ith rabbit.
Given the array answers, return the minimum number of rabbits that could be in the forest. 

Example 1:

Input: answers = [1,1,2]
Output: 5
Explanation:
The two rabbits that answered "1" could both be the same color, say red.
The rabbit that answered "2" can't be red or the answers would be inconsistent.
Say the rabbit that answered "2" was blue.
Then there should be 2 other blue rabbits in the forest that didn't answer into the array.
The smallest possible number of rabbits in the forest is therefore 5: 3 that answered plus 2 that didn't.

Example 2:

Input: answers = [10,10,10]
Output: 11
 

Constraints:

1 <= answers.length <= 1000
0 <= answers[i] < 1000
*/

class Solution {
    public int numRabbits(int[] answers) {
                // Map to count how many times each answer appears
        Map<Integer, Integer> freq = new HashMap<>();
        for (int ans : answers) {
            freq.put(ans, freq.getOrDefault(ans, 0) + 1);
        }

        int res = 0;
        for (int x : freq.keySet()) {
            int count = freq.get(x);       // Number of rabbits that answered x
            int groupSize = x + 1;         // Each group has (x + 1) rabbits of the same color
            int groups = (count + groupSize - 1) / groupSize; // Ceiling division
            res += groups * groupSize;     // Total rabbits for this answer group
        }

        return res;
    }
}

/*
Visualization of the above code
 Let’s walk through a visualization of the code for the example:

---

🔢 Input:
```
answers = [1, 1, 2]
```

🧠 What answers mean:
- A rabbit answering `1` means there is 1 other rabbit of the same color (i.e. a group of 2 rabbits total).
- A rabbit answering `2` means there are 2 other rabbits of the same color (i.e. a group of 3 rabbits total).

---

🔍 Step-by-step Execution:

Step 1: Build frequency map

```
Map<Integer, Integer> freq = new HashMap<>();
```

Loop through `answers`:

- `answers[0] = 1` → `freq[1] = 1`
- `answers[1] = 1` → `freq[1] = 2`
- `answers[2] = 2` → `freq[2] = 1`

So,
```
freq = {1=2, 2=1}
```

---

Step 2: Iterate through the map

For answer `x = 1`:

- `count = 2` (2 rabbits said "1")
- `groupSize = x + 1 = 2`
- `groups = (2 + 2 - 1) / 2 = 3 / 2 = 1` group (because exactly 2 rabbits fit 1 group of size 2)
- Total rabbits so far: `res = 1 * 2 = 2`

For answer `x = 2`:

- `count = 1` (1 rabbit said "2")
- `groupSize = x + 1 = 3`
- `groups = (1 + 3 - 1) / 3 = 3 / 3 = 1`
- Total rabbits so far: `res += 1 * 3 = 3` (Now res = 2 + 3 = 5)

---

✅ Final Output:
```
return 5;
```

---

🎯 Visualization Summary:

| Answer x | Frequency | Group Size (x+1) | Groups Needed | Rabbits Counted |
|----------|-----------|------------------|----------------|------------------|
|    1     |     2     |        2         |       1        |        2         |
|    2     |     1     |        3         |       1        |        3         |
|  Total  |           |                  |                |      5       |

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 0ms runtime which is the lowest time in this problem.
*/

class Solution {
    public int numRabbits(int[] answers) {

        int n = answers.length;
        int[] count = new int[1000];
        
        int ans = 0;
        for(int x:answers){
            if(++count[x] == 1)
                ans += x + 1;
            if(count[x] == x + 1)
                count[x] = 0;
        }
        
        return ans;
    }
}

/*
Visualization of the above code
 Let's visualize the working of your code using an example. The code counts the minimum number of rabbits in the forest based on their answers about how many other rabbits share their color.

---

✅ Example Input:

```
answers = [1, 1, 2]
```

---

🔍 Step-by-step Explanation:

You have:

```
int[] count = new int[1000]; // used to count occurrences of each answer
int ans = 0; // the final number of rabbits
```

Now you iterate through the `answers` array.

---

🧠 Iteration 1: `x = 1`

- `++count[1] = 1`
- Since `count[1] == 1`, it’s the first rabbit of this color group.
  - So, we add `x + 1 = 2` rabbits (1 who answered, 1 additional).
  - `ans = 0 + 2 = 2`
- Since `count[1] != x + 1 = 2`, do nothing more.

---

🧠 Iteration 2: `x = 1` (again)

- `++count[1] = 2`
- Now `count[1] == x + 1 = 2`, meaning this color group is full.
  - So, reset: `count[1] = 0`

`ans` remains 2

---

🧠 Iteration 3: `x = 2`

- `++count[2] = 1`
- Since `count[2] == 1`, it’s the first rabbit of a new color group of size 3 (2 + 1)
  - So, we add `x + 1 = 3`
  - `ans = 2 + 3 = 5`
- `count[2] != 3`, so don’t reset.

---

🎯 Final Output:

```
return ans = 5
```

---

🔢 Visualization Table

| x (answer) | count[x] after ++ | Condition Met                   | Action Taken         | ans |
|------------|-------------------|----------------------------------|----------------------|-----|
| 1          | 1                 | First rabbit in group            | `ans += 2`           | 2   |
| 1          | 2                 | Group full (2 of same color)     | Reset count[x]       | 2   |
| 2          | 1                 | First rabbit in group of 3       | `ans += 3`           | 5   |

---

🧠 Concept Behind Logic

- When you see a rabbit answering `x`, it implies a group of (x + 1) rabbits of that same color.
- You add `x + 1` to the answer only once for each new group.
- Once that group is full, you reset the counter to be ready for a possible new group of the same kind.

---
*/
