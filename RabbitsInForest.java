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
