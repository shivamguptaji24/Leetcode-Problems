/*
Daily Question :
3272 - Find the Count of Good Integers

You are given two positive integers n and k.
An integer x is called k-palindromic if:
x is a palindrome.
x is divisible by k.
An integer is called good if its digits can be rearranged to form a k-palindromic integer. For example, for k = 2, 2020 can be rearranged to form the k-palindromic integer 2002, whereas 1010 cannot be rearranged to form a k-palindromic integer.
Return the count of good integers containing n digits.
Note that any integer must not have leading zeros, neither before nor after rearrangement. For example, 1010 cannot be rearranged to form 101.

Example 1:

Input: n = 3, k = 5

Output: 27

Explanation:

Some of the good integers are:

551 because it can be rearranged to form 515.
525 because it is already k-palindromic.
Example 2:

Input: n = 1, k = 4

Output: 2

Explanation:

The two good integers are 4 and 8.

Example 3:

Input: n = 5, k = 6

Output: 2468

 

Constraints:

1 <= n <= 10
1 <= k <= 9
*/

class Solution {
  public long countGoodIntegers(int n, int k) {
    final int halfLength = (n + 1) / 2;
    final int minHalf = (int) Math.pow(10, halfLength - 1);
    final int maxHalf = (int) Math.pow(10, halfLength);
    long ans = 0;
    Set<String> seen = new HashSet<>();

    for (int num = minHalf; num < maxHalf; ++num) {
      final String firstHalf = String.valueOf(num);
      final String secondHalf = new StringBuilder(firstHalf).reverse().toString();
      final String palindrome = firstHalf + secondHalf.substring(n % 2);
      if (Long.parseLong(palindrome) % k != 0)
        continue;
      char[] sortedDigits = palindrome.toCharArray();
      Arrays.sort(sortedDigits);
      String sortedDigitsStr = new String(sortedDigits);
      if (seen.contains(sortedDigitsStr))
        continue;
      seen.add(sortedDigitsStr);
      int[] digitCount = new int[10];
      for (char c : palindrome.toCharArray())
        ++digitCount[c - '0'];
      // Leading zeros are not allowed, so the first digit is special.
      final int firstDigitChoices = n - digitCount[0];
      long permutations = firstDigitChoices * factorial(n - 1);
      // For each repeated digit, divide by the factorial of the frequency since
      // permutations that swap identical digits don't create a new number.
      for (final int freq : digitCount)
        if (freq > 1)
          permutations /= factorial(freq);
      ans += permutations;
    }

    return ans;
  }

  private long factorial(int n) {
    long res = 1;
    for (int i = 2; i <= n; ++i)
      res *= i;
    return res;
  }
}

/*
Visualization of the above code
 Sure Shivam! Let's walk through your Java solution step by step with visualization using an example:

---

🧠 Problem Recap

Given:
- `n` = number of digits
- `k` = a number
You need to count good integers of length `n` whose digits can be rearranged into a k-palindromic number.

---

📌 Example:  
Let’s take  
Input: `n = 3`, `k = 5`  
Expected Output: `27` ✅

---

🔍 Code Breakdown + Visualization

```
final int halfLength = (n + 1) / 2;  // halfLength = (3 + 1) / 2 = 2
```
We only need to generate palindromes by forming their first half (with optional middle digit if n is odd).

```
final int minHalf = 10; // 10^1 = 10
final int maxHalf = 100; // 10^2 = 100
```
Now we iterate from 10 to 99 (all 2-digit numbers, because n=3 needs a 2-digit half):

```
for (int num = 10; num < 100; ++num) {
```

---

🌀 Let's try `num = 12`:
```
firstHalf = "12"
secondHalf = reverse("12") = "21"
```

Since `n % 2 == 1`, we remove the middle digit from `secondHalf`:
```
palindrome = "12" + "1" = "121"
```

Now:
```
Long.parseLong("121") % 5 != 0 → skip
```

---

🎯 Try `num = 15`:
```
firstHalf = "15"
secondHalf = "51"
palindrome = "15" + "5" = "155"
155 % 5 == 0 ✅
```

Next, sort the digits:
```
sorted = ['1', '5', '5'] → "155"
```

Check if this digit combination has already been used:
```
seen.contains("155")? No → add it to seen
```

Now we count how many unique permutations of these digits without leading 0 can form valid numbers:

```
digitCount: [0,1,0,0,0,2,0,0,0,0]
```

- First digit cannot be 0
- Total digits = 3
- FirstDigitChoices = 3 - digitCount[0] = 3 - 0 = 3
- permutations = 3 * factorial(2) = 3 * 2 = 6
- We divide by factorial of repeated digits → digit 5 has freq = 2  
  So, `permutations = 6 / 2! = 6 / 2 = 3`

✅ Add 3 to answer.

---

🔁 Continue loop for all palindromes...

Repeat for `num = 16` to `99`, check if the palindrome formed:
- is divisible by `k`
- is unique (via sorted digits)
- and then count valid permutations

---

✅ Final Output
For `n = 3` and `k = 5`, you’ll collect `ans = 27` in total, which matches the expected output.

---

📌 Summary of what this code is doing:

1. Generates palindromic numbers of length `n` by mirroring half.
2. Filters palindromes divisible by `k`.
3. Avoids duplicates by using a set of sorted digit strings.
4. Counts permutations of digits that can form valid numbers without leading zeros and adjusts for repeated digits.

---
*/
