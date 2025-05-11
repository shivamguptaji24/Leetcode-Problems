/*
Daily Question :
1550 - Three Consecutive Odds

Given an integer array arr, return true if there are three consecutive odd numbers in the array. Otherwise, return false. 

Example 1:

Input: arr = [2,6,4,1]
Output: false
Explanation: There are no three consecutive odds.

Example 2:

Input: arr = [1,2,34,3,4,5,7,23,12]
Output: true
Explanation: [5,7,23] are three consecutive odds.
 

Constraints:

1 <= arr.length <= 1000
1 <= arr[i] <= 1000
*/

class Solution {
    public boolean threeConsecutiveOdds(int[] arr) {
        for (int i = 0; i < arr.length - 2; i++) {
            if (arr[i] % 2 != 0 && arr[i+1] % 2 != 0 && arr[i+2] % 2 != 0) {
                return true;
            }
        }
        return false;
    }
}

/*
Visualization of the above code
 Here's a step-by-step visualization of your Java code for the method `threeConsecutiveOdds`, which checks if there are three consecutive odd numbers in an array:

---

🔍 Code Recap:

```
class Solution {
    public boolean threeConsecutiveOdds(int[] arr) {
        for (int i = 0; i < arr.length - 2; i++) {
            if (arr[i] % 2 != 0 && arr[i+1] % 2 != 0 && arr[i+2] % 2 != 0) {
                return true;
            }
        }
        return false;
    }
}
```

---

🧠 Logic:

We iterate from index `i = 0` to `arr.length - 3`, and for each position, we check:

```
arr[i], arr[i+1], arr[i+2]
```

If all three are odd (`% 2 != 0`), we return `true`.

---

🧪 Example:

Let’s take this input:

```
arr = [1, 2, 34, 3, 4, 5, 7, 23, 12]
```

Let’s walk through the loop:

| i | arr[i] | arr[i+1] | arr[i+2] | Are all odd? | Action          |
| - | ------- | --------- | --------- | ------------ | --------------- |
| 0 | 1       | 2         | 34        | ❌            | Continue        |
| 1 | 2       | 34        | 3         | ❌            | Continue        |
| 2 | 34      | 3         | 4         | ❌            | Continue        |
| 3 | 3       | 4         | 5         | ❌            | Continue        |
| 4 | 4       | 5         | 7         | ❌            | Continue        |
| 5 | 5       | 7         | 23        | ✅            | Return true |

---

✅ Output:

The function returns `true` because `[5, 7, 23]` are three consecutive odd numbers.

---
*/
