package algorithms;

import java.math.BigInteger;

public class BinaryExponentiation {
  private static final BigInteger ZERO = BigInteger.ZERO;
  private static final BigInteger ONE = BigInteger.ONE;

  public static BigInteger pow(BigInteger b, BigInteger n) {
    if (b.compareTo(ZERO) == 0) return ZERO;

    BigInteger result = ONE;
    while (n.compareTo(ZERO) != 0) {
      if (n.and(ONE).compareTo(ZERO) != 0) result = result.multiply(b);
      b = b.multiply(b);
      n = n.shiftRight(1);
    }

    return result;
  }
}
