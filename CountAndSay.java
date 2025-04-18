/*
Daily Question :
38 - Count and Say

The count-and-say sequence is a sequence of digit strings defined by the recursive formula:

countAndSay(1) = "1"
countAndSay(n) is the run-length encoding of countAndSay(n - 1).
Run-length encoding (RLE) is a string compression method that works by replacing consecutive identical characters (repeated 2 or more times) with the concatenation of the character and the number marking the count of the characters (length of the run). For example, to compress the string "3322251" we replace "33" with "23", replace "222" with "32", replace "5" with "15" and replace "1" with "11". Thus the compressed string becomes "23321511".

Given a positive integer n, return the nth element of the count-and-say sequence.

 

Example 1:

Input: n = 4

Output: "1211"

Explanation:

countAndSay(1) = "1"
countAndSay(2) = RLE of "1" = "11"
countAndSay(3) = RLE of "11" = "21"
countAndSay(4) = RLE of "21" = "1211"
Example 2:

Input: n = 1

Output: "1"

Explanation:

This is the base case.

 

Constraints:

1 <= n <= 30
 

Follow up: Could you solve it iteratively?
*/

class Solution {
    public String countAndSay(int n) {
                if (n == 1) return "1";

        String result = "1";

        for (int i = 2; i <= n; i++) {
            StringBuilder sb = new StringBuilder();
            int count = 1;
            for (int j = 1; j < result.length(); j++) {
                if (result.charAt(j) == result.charAt(j - 1)) {
                    count++;
                } else {
                    sb.append(count).append(result.charAt(j - 1));
                    count = 1;
                }
            }
            sb.append(count).append(result.charAt(result.length() - 1));
            result = sb.toString();
        }

        return result;
    }
}

/*
Visualization of the above code
 Let’s visually trace the code logic of the `countAndSay(n)` function for better understanding. We'll go step-by-step through how the string is constructed at each level for an example like `n = 5`.

---

🧠 Initial Setup
We start with:
```
String result = "1";
```

---

🔁 Iteration Breakdown (Step-by-step)

Let’s visualize how each term is constructed using run-length encoding.

---

✅ Step 1: `n = 1`
```
result = "1"
```

---

✅ Step 2: `n = 2`
Read `"1"`:
- One `1` → `"11"`

```
result = "11"
```

---

✅ Step 3: `n = 3`
Read `"11"`:
- Two `1`s → `"21"`

```
result = "21"
```

---

✅ Step 4: `n = 4`
Read `"21"`:
- One `2` → `"12"`
- One `1` → `"11"`

```
result = "1211"
```

---

✅ Step 5: `n = 5`
Read `"1211"`:
- One `1` → `"11"`
- One `2` → `"12"`
- Two `1`s → `"21"`

So final string:
```
result = "111221"
```

---

🧩 Final Visualization Table

| n | countAndSay(n) |
|---|----------------|
| 1 | `"1"`          |
| 2 | `"11"`         |
| 3 | `"21"`         |
| 4 | `"1211"`       |
| 5 | `"111221"`     |

---

🔍 Loop Logic (Zooming in)
Each time inside the loop:
- We iterate over the current `result` string.
- Count how many times the same digit appears in a row.
- Append the `count + digit` to the new string.

---

🔄 Code Segment Visual
Imagine `result = "1211"`:
```
for (int j = 1; j < result.length(); j++) {
    if (result.charAt(j) == result.charAt(j - 1)) {
        count++;
    } else {
        sb.append(count).append(result.charAt(j - 1));
        count = 1;
    }
}
```
At the end, don’t forget to append the last group:
```
sb.append(count).append(result.charAt(result.length() - 1));
```

---
*/
