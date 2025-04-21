/*
Daily Question :
2145 - Count the Hidden Sequences

You are given a 0-indexed array of n integers differences, which describes the differences between each pair of consecutive integers of a hidden sequence of length (n + 1). More formally, call the hidden sequence hidden, then we have that differences[i] = hidden[i + 1] - hidden[i].
You are further given two integers lower and upper that describe the inclusive range of values [lower, upper] that the hidden sequence can contain.
For example, given differences = [1, -3, 4], lower = 1, upper = 6, the hidden sequence is a sequence of length 4 whose elements are in between 1 and 6 (inclusive).
[3, 4, 1, 5] and [4, 5, 2, 6] are possible hidden sequences.
[5, 6, 3, 7] is not possible since it contains an element greater than 6.
[1, 2, 3, 4] is not possible since the differences are not correct.
Return the number of possible hidden sequences there are. If there are no possible sequences, return 0. 

Example 1:

Input: differences = [1,-3,4], lower = 1, upper = 6
Output: 2
Explanation: The possible hidden sequences are:
- [3, 4, 1, 5]
- [4, 5, 2, 6]
Thus, we return 2.

Example 2:

Input: differences = [3,-4,5,1,-2], lower = -4, upper = 5
Output: 4
Explanation: The possible hidden sequences are:
- [-3, 0, -4, 1, 2, 0]
- [-2, 1, -3, 2, 3, 1]
- [-1, 2, -2, 3, 4, 2]
- [0, 3, -1, 4, 5, 3]
Thus, we return 4.

Example 3:

Input: differences = [4,-7,2], lower = 3, upper = 6
Output: 0
Explanation: There are no possible hidden sequences. Thus, we return 0.
 

Constraints:

n == differences.length
1 <= n <= 105
-105 <= differences[i] <= 105
-105 <= lower <= upper <= 105
*/

class Solution {
    public int numberOfArrays(int[] differences, int lower, int upper) {
                long min = 0, max = 0;
        long curr = 0;

        // Build prefix sum array to track min and max values
        for (int diff : differences) {
            curr += diff;
            min = Math.min(min, curr);
            max = Math.max(max, curr);
        }

        // The valid starting number must be in the range:
        // [lower - min, upper - max]
        long validStartMin = lower - min;
        long validStartMax = upper - max;

        // If the valid start range is invalid (min > max), return 0
        if (validStartMin > validStartMax) return 0;

        // Total number of valid starting values
        return (int)(validStartMax - validStartMin + 1);
    }
}

/*
Visualization of the above code
 Let’s visualize how the solution works with a step-by-step breakdown for the example:

---

🧪 Example Input:
```
differences = [1, -3, 4]
lower = 1
upper = 6
```

We want to find how many hidden sequences of length 4 exist such that:
- All values lie between 1 and 6.
- The difference between consecutive elements is as given.

---

🔍 Step 1: Understanding What We’re Building

Let:
- `hidden[0] = x`
- The rest of the sequence is built as:
  - `hidden[1] = x + 1`
  - `hidden[2] = x + 1 - 3 = x - 2`
  - `hidden[3] = x - 2 + 4 = x + 2`

So we generate the entire sequence by prefix sums of differences starting from x.

---

🔍 Step 2: Build Prefix Sums

We simulate how `x` evolves through the sequence:

| Index | Difference | Cumulative sum | Expression     |
|-------|------------|----------------|----------------|
| 0     | —          | 0              | x              |
| 1     | +1         | 1              | x + 1          |
| 2     | -3         | -2             | x - 2          |
| 3     | +4         | 2              | x + 2          |

➡️ So the full hidden sequence becomes:
```
[x, x + 1, x - 2, x + 2]
```

---

🔍 Step 3: Find Min & Max from Prefix Sums

From cumulative values: `0, 1, -2, 2`  
- min prefix sum = -2  
- max prefix sum = 2

These show how far the values in the sequence can dip or rise relative to x.

---

🔍 Step 4: Set Valid Range for Starting Value (x)

To ensure that all values of the sequence are between `lower` and `upper` (i.e., 1 and 6):

- For min: `x + (-2) ≥ 1` ⟹ `x ≥ 3`
- For max: `x + 2 ≤ 6` ⟹ `x ≤ 4`

So x must lie in `[3, 4]`  
✅ Valid `x` values: 3 and 4

---

✅ Final Answer:

```
return 4 - 3 + 1 = 2
```

There are 2 valid sequences:

1. x = 3 → [3, 4, 1, 5]  
2. x = 4 → [4, 5, 2, 6]

---

🧠 Final Insight:
We’re not generating the sequences directly.  
Instead, we track the range of values based on prefix sum movement and check which starting values produce valid full sequences.

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 3ms runtime which is the lowest time in this problem.
*/

class Solution {
    //My Brute Force gives TLE beacuse it check each element in lower and upper bound

   /*  public int numberOfArrays(int[] differences, int lower, int upper) {
        int firstLastDiff = 0, n = differences.length, c =0;

        for(int diff: differences) firstLastDiff+=diff;

        for(int i = lower;i<=upper;i++){
            int prev = i;
            for(int j=0;j<n;j++){
                prev = differences[j] + prev;
                if(prev<lower || prev>upper){
                    break;
                }
                if(j == n-1){
                    if(prev != i+firstLastDiff) break;
                    c++;
                }
            }
        }

        return c;
    }*/

    //Optimizing - what we can observe is
    //hiddin[i] = firstEle + prefixSum[i];
    //how? - 

    /*[1,-3,4]=[1,-2,2]prefixSum
    //first Ele = 3
    [3,3+1,3-2,3+2]=[3,4,1,5]-VALID[1,2,-1,3]-NOT VALID[4,5,2,6]-VALDI*/

    public int numberOfArrays(int[] differences, int lower, int upper) {
        long start = 0, max = 0, min = 0;
        for(int diff : differences){
            start += diff;
            max = (max<start)?start:max;
            min = (min>start)?start:min;
        }
        // System.out.println(min+" "+max);        

        return (int)Math.max(0,(upper-lower)-(max-min)+1);
    }

}
