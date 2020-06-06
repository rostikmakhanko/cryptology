package algorithms;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

public class PrimalityTestFermat {
  public static int numOfIterations = 500;

  public static boolean isPrime(BigInteger number) {
    if (number.compareTo(BigInteger.ONE) <= 0) {
      return false;
    } else if (number.compareTo(BigInteger.valueOf(2)) == 0) {
      return true;
    }

    int i = 0;
    while (i != numOfIterations) {
      if (getRandFermatBase(number)
              .modPow(number.subtract(BigInteger.ONE), number)
              .compareTo(BigInteger.ONE)
          != 0) return false;
      i++;
    }
    return true;
  }

  private static BigInteger getRandFermatBase(BigInteger number) {
    Random rand = new Random(new Date().getTime());
    while (true) {
      BigInteger base = new BigInteger(number.bitLength(), rand);
      if ((base.compareTo(number) < 0) && (base.compareTo(BigInteger.ONE) > 0)) return base;
    }
  }
}
