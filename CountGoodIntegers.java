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
 Let's walk through your Java solution step by step with visualization using an example:

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

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 57ms runtime which is the lowest time in this problem.
*/

class Solution {
    private static final int[][] modPow10 = new int[][]{
        {0}, 
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 
        {1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
        {1, 3, 2, 6, 4, 5, 1, 3, 2, 6, 4}, 
        {1, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    }; //get mod pow 10 with modPow10[mod][exp]

    private static final int[] factorial = new int[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800};
    public long countGoodIntegers(int n, int k) {
        /*
        for digits to even be able to rearrannge into a palindrome, need equal counnts of all digits, with possibly a single exception for center.
        Since good property needs to be able to rearrange into a palindrome, don't worry about order at first, just choose digits then find out how many 
        different n digit number perms said digit selection has if it can form a k-palindrome

        for even n:
            iterate through all sets of n/2 digits from 0-9. Since repeats are allowed, use r+s-1 choose s binom instead of r choose s
            This gives (10 + n/2 - 1) choose n/2. This caps at around 2000 with 14 choose 5, making set count relatively small
            
            once have a digit set, iterate through all perms of digits (without leading zeros) and see if there is one that when 
            do perm * (10^n/2) + reverse(perm) is divisible by k. (as this forms a pal)

            if we let the perm be represented by array perm where perm[0] is first (leading val) in perm and perm[n/2 - 1] is the last, 
            pal is also equiv to 

            perm[0] * 10^(n-1) + perm[1]*10^(n-2) + ... perm[n/2-1] * 10^(n/2) + perm[n/2-1]*10^(n/2 - 1) + ... perm[1]*10^1 + perm[0]
            = perm[0] * (10^(n-1) + 1) + perm[1] * (10^(n-2) + 10^1) + ... perm[i] * (10^(n-i-1) + 10^i) .... + perm[n/2-1] * (10^(n/2) + 10^(n/2-1))
            to be divisible by k, this needs to == 0 mod k, therefore a perm is a sol if it solves eq
            sum(perm[i] * (10^(n-i-1) + 10^i))[i = 0 to n/2 - 1] == 0 mod k under the condition that perm[0] != 0.

            for different k, have following for pows of 10
               k = 1:  10^s mod 1 == 0 for all s >= 0
                k > 1: all 10^0 mod k is trivially == 1 mod k

                k = 2, 5: 10^s mod k == 0 s >= 1
                k = 3, 9: 10^s mod k == 1 for all s >= 0
                k = 4: 10^s mod k == 2 for s == 1, 10^s mod k == 0 for s >= 2
                k = 6: 10^s mod k == 4 for s >= 1. 
                k = 8: 10^s mod k == 2 for s = 1, 10^2 mod k == 4, 10^s mod k for s >= 3 == 0. 
                k = 7: 10^s mod k. {s, k} pair set: {{1, 3}, {2, 2}, {3, 6}, {4, 4}, {5, 5}, {6, 1}, cycles from there for s congruence classes mod 7}
        
        Similar case for odd n, except now the palindrome is centered. 
            (Note for base case n = 1, can just return k/9 as this is 1 digit pos nums divis by k, and all are pals)

            So just have the same exact equation, but with added center (dont use center in perm, select center seperately)

            sum(perm[i] * (10^(n-i-1) + 10^i))[i = 0 to Floor(n/2) - 1]  + center == 0 mod k 
            center is some digit cDigit times 10^(floor(n/2))
            so have: sum(perm[i] * (10^(n-i-1) + 10^i))[i = 0 to Floor(n/2) - 1]  + cDigit*10^(floor(n/2)) == 0 mod k 
            or sum(perm[i] * (10^(n-i-1) + 10^i))[i = 0 to Floor(n/2) - 1]  == -cDigit*10^(floor(n/2)) mod k 

        */
        if(n == 1) return 9/k;
        return backtrackDigitCombos(0, 0, n, k,  new int[(n+1)/2]);
    }

    private long backtrackDigitCombos(int idx, int lo, int n, int k, int[] digits) {
        if(idx == digits.length) {
            if(digits[idx-1] == 0) return 0;
            if(n % 2 == 1) { //if odd need to pick a center.
                if(digits[idx-2] == 0) return hasKPalPerm(digits, n, k, 0, 1 << (idx-2), 0) ? validPermCnt(digits, n, 0) : 0;
                long res = 0;
                for(int c = 0; c < digits.length; c++) { 
                    if(c > 0 && digits[c] == digits[c-1]) continue;
                    if(hasKPalPerm(digits, n, k, (digits[c]*modPow10[k][n/2]) % k, 1 << c, 0)) {
                        res += validPermCnt(digits, n, digits[c]);
                    }
                }
                return res;
            }
            
            return hasKPalPerm(digits, n, k, 0, 0, 0) ? validPermCnt(digits, n, -1) : 0;
        }
        long res = 0;
        for(int d = lo; d <= 9; d++) {
            digits[idx] = d;
            res += backtrackDigitCombos(idx+1, d, n, k, digits);
        }
        return res;
    }

    private boolean hasKPalPerm(int[] digits, int n, int k, int residue, int mask, int chosenCnt) {
        if(chosenCnt == n/2) {
            return residue == 0;
        }

        for(int i = digits.length-1; i >= 0; i--) {
            if(chosenCnt == 0 && digits[i] == 0) break;
            if((mask & (1 << i)) != 0) continue;
            if(hasKPalPerm(digits, n, k, (residue + digits[i]*(modPow10[k][n-chosenCnt-1] + modPow10[k][chosenCnt])) % k, mask | (1 << i), chosenCnt+1)) return true;
        }

        return false;
    }

    private int validPermCnt(int[] digits, int n, int center) {
        /*want to get totalPerms - startZeroPerms to get perms non startign with zero
        total perms = n!/(product of factorial of repeat counts for elements to denote repeat subperms) = n!/((zeroCnt)!*(prod(cnt!) for all repeat cnts cnt)
        zeroStart perms (trivial case of zero when zeroCnt = 0), select one of the zeros to start the perm then permute the rest of the elements (now with zeroCnt-1 0's)
        zeroStart perms = (n-1)!/((zeroCnt-1)! * (prod(cnt!)))
        let total perms = tp, zero start perms = zp, prod of cnt!'s = p
        tp = n!/(zeroCnt! * p), zp = (n-1)!/((zeroCnt-1)! * p). 
        zp = (zeroCnt/n) * (n!/(zeroCnt! * p)) = (zeroCnt/n) * tp. 
            (note: involves mult by n/zeroCnt also so zeroCnt must be > 0, but even if zeroCnt = 0 reduced formula still works as (n-zeroCnt)*tp/n = n*tp/n = tp = tp-zp = tp-0)
        thus since want tp-zp, tp-zp = tp - (zeroCnt/n) * tp. = (1 - zeroCnt/n)*tp = ((n-zeroCnt)*tp)/n*/

        int nonZeroInd = 0;
        while(digits[nonZeroInd] == 0) nonZeroInd++;
        int zeroCnt = nonZeroInd * 2 - (center == 0 ? 1 : 0); //2x all counts as actual perm has it reflected in both sides of pal.

        long perms = factorial[n]/factorial[zeroCnt];
        int streak = 1;
        for(int i = nonZeroInd+1; i < digits.length; i++) { //perms of s diff elements = s!/(product of factorials of repeat elements to denote repeat subperms)
            if(digits[i] == digits[i-1]) {
                streak++;
            } else {
                perms /= factorial[2*streak - (digits[i-1] == center ? 1 : 0)];
                streak = 1;
            }
        }
        perms /= factorial[2*streak - (digits[digits.length-1] == center ? 1 : 0)];

        return (int)(perms*(n-zeroCnt)/n); //use tp-zp = ((n-zeroCnt)*tp)/n. (Note perms max = 10! < int lim and since perms*(n-zeroCnt)/n <= perms, fits in int)
    }

    
}

/*
Visualization of the above code
 You're sharing an advanced and quite clever solution involving digit combination backtracking, permutations, and modular arithmetic to count how many `n`-digit k-divisible palindromes can be formed.

It’s long, so let me break down and help with:

❓ What you're solving:
Given:
- `n`: number of digits
- `k`: number should be divisible by this

You're computing:
- How many `n`-digit numbers that are palindromes exist and are divisible by `k`.

---

✅ Summary of Approach

Palindrome insight:
If you have the first `n/2` digits, the rest of the palindrome is fixed — it's the reverse of those (for even `n`), or the same plus a middle digit (for odd `n`).

Core idea:
- Loop through all digit *multisets* of length `n/2` (considering repetition, but sorted to avoid duplicates).
- For each valid digit multiset:
  - Try all permutations that don't start with `0`.
  - Form the full palindrome.
  - Check if it's divisible by `k`.

But instead of checking full palindromes numerically, you:
- Use modular math to check if permutations satisfy the divisibility condition (`mod k`).
- Optimize with precalculated `modPow10` for modular exponentiation of 10.
- Use backtracking + memoization to prune permutations early.

---

🧠 Complex Parts:
1. `modPow10` table:
Precomputed values of `10^x % k` to avoid recomputation during palindrome evaluation.

2. `hasKPalPerm(...)`:
This function tests if **any permutation** of the half-digit multiset can lead to a full palindrome divisible by `k`, without trying all permutations explicitly. It builds the palindrome’s contribution digit-by-digit.

3. `validPermCnt(...)`:
This function calculates the number of valid permutations (with or without center digit, based on parity of `n`) while:
- Avoiding starting with `0`
- Handling repeated digits via combinatorics (`n! / (freq[digit1]! * freq[digit2]! ...)`)
- It accounts for reflection and possibly a center digit.

---

❌ Problem in the end of code:

Your last method `validPermCnt(...)` is incomplete:
```
for(int i = nonZeroInd+1; i < digits.length; i++) {
    if(digits[i] == digits[i-1]) {
        streak++;
    } else {
        perms /= factorial[2...
```

The line is incomplete:
```
perms /= factorial[2...
```

You were trying to calculate the permutations’ denominator (due to repeating digits), something like:

```
        perms /= factorial[2 * streak];
```

Because each digit appears twice (in mirror), so for a digit count `c` in half, total is `2*c`.

Here’s how you can complete this:

---

✅ Completed `validPermCnt(...)`:

```
private int validPermCnt(int[] digits, int n, int center) {
    int nonZeroInd = 0;
    while(nonZeroInd < digits.length && digits[nonZeroInd] == 0) nonZeroInd++;
    int zeroCnt = nonZeroInd * 2 - (center == 0 ? 1 : 0);

    long perms = factorial[n] / factorial[zeroCnt];
    int streak = 1;
    for(int i = nonZeroInd + 1; i <= digits.length; i++) {
        if(i < digits.length && digits[i] == digits[i - 1]) {
            streak++;
        } else {
            perms /= factorial[2 * streak]; // because each digit appears twice
            streak = 1;
        }
    }
    return (int)((((n - zeroCnt) * perms) / n)); // adjust for permutations starting with 0
}
```

---

🧪 Edge Cases:
- `n = 1`: Return `9/k` (since only digits 1–9 are valid 1-digit pals).
- `k = 1`: All palindrome numbers are valid.
- Large `n`: The code handles up to `n = 10` efficiently.

---

🔥 Final Thoughts:

This solution is:
- Mathematically rich (number theory + combinatorics).
- Optimized using bitmasking, symmetry, and modular math.
- Elegant in avoiding brute-force checking of every `n`-digit number.

If you plan to submit this on LeetCode or explain it to someone, consider:
- Adding inline comments on the key steps (like why `modPow10` works, why 2× for palindromes, etc.).
- Handling extremely large factorials using `BigInteger` if constraints go beyond `n=10`.
*/
