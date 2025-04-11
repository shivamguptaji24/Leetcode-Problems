/*
Daily Question :
2843 - Count Symmetric Integers

You are given two positive integers low and high.
An integer x consisting of 2 * n digits is symmetric if the sum of the first n digits of x is equal to the sum of the last n digits of x. Numbers with an odd number of digits are never symmetric.
Return the number of symmetric integers in the range [low, high].

Example 1:

Input: low = 1, high = 100
Output: 9
Explanation: There are 9 symmetric integers between 1 and 100: 11, 22, 33, 44, 55, 66, 77, 88, and 99.
Example 2:

Input: low = 1200, high = 1230
Output: 4
Explanation: There are 4 symmetric integers between 1200 and 1230: 1203, 1212, 1221, and 1230.
 

Constraints:

1 <= low <= high <= 10^4
*/

class Solution {
    public int countSymmetricIntegers(int low, int high) {
        int count = 0;

        for (int num = low; num <= high; num++) {
            String s = Integer.toString(num);
            int len = s.length();

            if (len % 2 == 0) {
                int half = len / 2;
                int sum1 = 0, sum2 = 0;

                for (int i = 0; i < half; i++) {
                    sum1 += s.charAt(i) - '0';
                    sum2 += s.charAt(i + half) - '0';
                }

                if (sum1 == sum2) {
                    count++;
                }
            }
        }

        return count;
    }
}

/*
Visualization of the above code
 Let's visualize the code using the example:

---

🧠 Example:
Input:  
```
low = 1200  
high = 1230
```

We're finding symmetric integers between 1200 and 1230.

---

🔁 Code Walkthrough:

```
int count = 0;
```

Initialize a counter to keep track of how many symmetric numbers we find.

---

Now we loop from 1200 to 1230:

---

▶️ num = 1200  
- Convert to string: `"1200"`  
- Length = 4 (even ✅)  
- First half = `"12"` → 1 + 2 = 3  
- Second half = `"00"` → 0 + 0 = 0  
→ 3 ≠ 0 ❌ → Not symmetric

---

▶️ num = 1201  
- `"1201"` → First: 1+2=3, Second: 0+1=1 ❌

---

▶️ num = 1202  
- `"1202"` → First: 1+2=3, Second: 0+2=2 ❌

---

▶️ num = 1203  
- `"1203"` → First: 1+2=3, Second: 0+3=3 ✅  
→ Count = 1

---

▶️ num = 1204 → 3 vs 4 ❌  
▶️ num = 1205 → 3 vs 5 ❌  
▶️ num = 1206 → 3 vs 6 ❌  
▶️ num = 1207 → 3 vs 7 ❌  
▶️ num = 1208 → 3 vs 8 ❌  
▶️ num = 1209 → 3 vs 9 ❌

---

▶️ num = 1210 → 1+2 = 3, 1+0 = 1 ❌  
▶️ num = 1211 → 1+2 = 3, 1+1 = 2 ❌  
▶️ num = 1212 → 1+2 = 3, 1+2 = 3 ✅  
→ Count = 2

---

▶️ num = 1213 → 1+2 vs 1+3 → 3 vs 4 ❌  
...  
▶️ num = 1221 → 1+2 vs 2+1 → 3 vs 3 ✅  
→ Count = 3

...  
▶️ num = 1230 → 1+2 vs 3+0 → 3 vs 3 ✅  
→ Count = 4

---

✅ Final Output:
```
return 4;
```

---

🔢 Summary:

The symmetric integers between 1200 and 1230 are:

- 1203
- 1212
- 1221
- 1230

So, the result is: 4
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 2ms runtime which is the time in this problem
*/

class Solution {
    private static final short[] symCount = new short[10_001];
    
    
    public int countSymmetricIntegers(int low, int high) {
        if (symCount[11] == 0)  buildCounts();
        return symCount[high] - symCount[low - 1];
    }
    
    
    // One time build of the array of how many symmetric numbers 
    // exist at or below any index value.  After the first 
    // leetcode test case, this will not be called for the 
    // remainder of the submit test cases.  The values in 
    // symCount[] will be preserved between a submit's test cases.
    private void buildCounts() {
        // Fill in 0 to 99.
        for (int num = 11; num <= 99; num++)
            symCount[num] = (short)(num / 11);
        
        // Fill in 100 to 999.  No symmetric numbers in this range 
        // since odd number of digits, to just copy the count for 
        // symCount[99].
        short prev = symCount[99];
        for (int num = 100; num <= 999; num++)
            symCount[num] = prev;
        
        // Fill in 1000 to 9999
        prev = symCount[999];
        int idx = 1000;
        for (int high10 = 1; high10 <= 9; high10++) {
            for (int high1 = 0; high1 <= 9; high1++) {
                final int highSum = high10 + high1;
                for (int low10 = 0; low10 <= 9; low10++) 
                    for (int low1 = 0; low1 <= 9; low1++) 
                        symCount[idx++] = (short)((highSum == low10 + low1) ? ++prev : prev);
            }
        }
        
        // Fill in 10_000.
        symCount[10_000] = symCount[9999];
    }
}

/*
Visualization of the above code
 Let's break down and visualize this optimized code using an example:

---

🧠 Problem Recap:

You are given two integers `low` and `high`. Count how many symmetric integers are in the range `[low, high]`.  
A symmetric number has an even number of digits (e.g., 2 or 4), and the sum of the first half of digits equals the sum of the second half.

---

📘 Example:
```
low = 1200, high = 1230
```

We want to find how many symmetric integers are in this range.

---

🧠 Code Concept:

The code uses precomputation and prefix sums:

```
private static final short[] symCount = new short[10_001];
```

- This array stores how many symmetric numbers exist from 1 to any index `i`.
- So `symCount[i]` = number of symmetric integers ≤ `i`

---

🔁 `countSymmetricIntegers(low, high)`

Step 1: First time check:
```
if (symCount[11] == 0) buildCounts();
```
- Only builds the array once. The `symCount` array is reused between test cases.

Step 2: Return count between low and high:
```
return symCount[high] - symCount[low - 1];
```

In our case:
```
symCount[1230] - symCount[1199]
```

---

🔨 `buildCounts()` Step-by-step:

1. Fill from 11 to 99:
These are 2-digit symmetric numbers: 11, 22, ..., 99 (each digit repeated).
```
for (int num = 11; num <= 99; num++)
    symCount[num] = (short)(num / 11);
```
So:
- `symCount[11] = 1`
- `symCount[22] = 2`
- ...
- `symCount[99] = 9`

---

2. Fill from 100 to 999:
3-digit numbers can't be symmetric (odd length).
```
for (int num = 100; num <= 999; num++)
    symCount[num] = symCount[99]; // i.e., 9
```

---

3. Fill from 1000 to 9999:

Loop through all 4-digit numbers:

```
for (int high10 = 1; high10 <= 9; high10++) {
    for (int high1 = 0; high1 <= 9; high1++) {
        int highSum = high10 + high1;

        for (int low10 = 0; low10 <= 9; low10++) {
            for (int low1 = 0; low1 <= 9; low1++) {
                // Check if first two digits = last two digits in sum
                if (highSum == low10 + low1)
                    symCount[idx++] = ++prev;
                else
                    symCount[idx++] = prev;
            }
        }
    }
}
```

Example:
- 1203
  - First two digits: 1 + 2 = 3
  - Last two digits: 0 + 3 = 3 ✅

- This logic adds +1 if symmetric, else keeps previous count.

---

4. Set value for 10,000:
```
symCount[10000] = symCount[9999];
```
(10,000 is 5-digit → can't be symmetric)

---

✅ Now Let’s Use the Code:

We run:
```
countSymmetricIntegers(1200, 1230)
```

- `symCount[1230] = 13` (say)
- `symCount[1199] = 9`

Result:
```
13 - 9 = 4
```

✅ There are 4 symmetric integers between 1200 and 1230:  
- 1203  
- 1212  
- 1221  
- 1230  

---

🔍 Summary:

The code is optimized using:
- Prefix sum array
- One-time precomputation
- O(1) query time for each test case
*/
