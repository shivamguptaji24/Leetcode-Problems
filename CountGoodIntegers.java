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
