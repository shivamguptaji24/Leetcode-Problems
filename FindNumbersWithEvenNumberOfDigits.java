/*
Daily Question :
1295 - Find Numbers With Even Number Of Digits

Given an array nums of integers, return how many of them contain an even number of digits. 

Example 1:

Input: nums = [12,345,2,6,7896]
Output: 2
Explanation: 
12 contains 2 digits (even number of digits). 
345 contains 3 digits (odd number of digits). 
2 contains 1 digit (odd number of digits). 
6 contains 1 digit (odd number of digits). 
7896 contains 4 digits (even number of digits). 
Therefore only 12 and 7896 contain an even number of digits.

Example 2:

Input: nums = [555,901,482,1771]
Output: 1 
Explanation: 
Only 1771 contains an even number of digits.
 

Constraints:

1 <= nums.length <= 500
1 <= nums[i] <= 105
*/

class Solution {
    public int findNumbers(int[] nums) {
        int count = 0;
        for (int num : nums) {
            if (numDigits(num) % 2 == 0) {
                count++;
            }
        }
        return count;
    }

    // Helper method to count digits using math
    private int numDigits(int num) {
        int digits = 0;
        while (num > 0) {
            num /= 10;
            digits++;
        }
        return digits;
    }
}

/*
Visualization of the above code
 Here's a step-by-step visualization of how your `findNumbers` function works with an example input:

---

🎯 Input:
```
nums = [12, 345, 2, 6, 7896]
```

🔍 Step-by-step Execution:

| Iteration | `num` | `numDigits(num)` | Digit Count | Even Digits? | `count` |
|-----------|-------|------------------|--------------|--------------|---------|
| 1         | 12    | 2                | 2            | ✅ Yes        | 1       |
| 2         | 345   | 3                | 3            | ❌ No         | 1       |
| 3         | 2     | 1                | 1            | ❌ No         | 1       |
| 4         | 6     | 1                | 1            | ❌ No         | 1       |
| 5         | 7896  | 4                | 4            | ✅ Yes        | 2       |

🔚 Final Output:
```
return 2;
```

---

🧠 What’s Happening Internally:

- `numDigits()` function divides the number by 10 repeatedly to count how many digits it has.
- `findNumbers()` checks if this count is even (`% 2 == 0`), and if yes, increments `count`.

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 0ms runtime which is the lowest time in this problem.
*/

class Solution {
    public int findNumbers(int[] nums) {
        int result = 0;
        for (int number: nums) {
            if ((number > 9 && number < 100) || (number > 999 && number < 10000) || (number == 100000)) result++;
        }
        return result;
    }
}

/*
Visualization of the above code
 Let's visualize the code is an optimized version that checks whether the number has an even number of digits using number ranges instead of digit counting.

---

🎯 Input Example:
```
nums = [12, 345, 2, 6, 7896]
```

---

🔍 Step-by-Step Execution:

| Iteration | `number` | Number of Digits | Condition Matched?                                      | `result` |
|-----------|----------|------------------|----------------------------------------------------------|----------|
| 1         | 12       | 2                | ✅ `number > 9 && number < 100`                           | 1        |
| 2         | 345      | 3                | ❌ (not in any of the specified ranges)                  | 1        |
| 3         | 2        | 1                | ❌                                                       | 1        |
| 4         | 6        | 1                | ❌                                                       | 1        |
| 5         | 7896     | 4                | ✅ `number > 999 && number < 10000`                      | 2        |

---

✅ Final Output:
```
return 2;
```

---

📘 Logic Behind Conditions:
Your `if` condition checks these:
- `(number > 9 && number < 100)` → 2-digit numbers
- `(number > 999 && number < 10000)` → 4-digit numbers
- `(number == 100000)` → 6-digit number (only possible even-length 6-digit number given constraint)

These are all even-digit lengths.

---

🧠 Efficiency:
- Avoids loops or string conversions.
- Fastest in terms of raw performance (O(n)) with simple range comparisons.

---
*/
