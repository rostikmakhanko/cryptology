package algorithms;

import algorithms.extended.euclidean.BezoutIdentity;
import algorithms.extended.euclidean.ExtendedEuclideanAlgorithm;
import javafx.util.Pair;

import java.math.BigInteger;

public class MontgomeryArithmetic {
  private static final BigInteger ONE = BigInteger.ONE;

  public static BigInteger multiply(BigInteger a, BigInteger b, BigInteger n) {
    Pair<BigInteger, BigInteger> helper = multiplicationHelp(a, b, n);
    return helper.getKey().multiply(helper.getValue()).mod(n);
  }

  private static Pair<BigInteger, BigInteger> multiplicationHelp(
      BigInteger a, BigInteger b, final BigInteger n) {
    BigInteger r = ONE.shiftLeft(n.bitLength());
    BigInteger a1 = a.shiftLeft(r.bitLength() - 1).mod(n);
    BigInteger b1 = b.shiftLeft(r.bitLength() - 1).mod(n);
    BigInteger product = KaratsubaMultiplication.multiply(a1, b1);
    BezoutIdentity rnIdentity = ExtendedEuclideanAlgorithm.calculateGcd(r, n);

    if (rnIdentity.getGcd().compareTo(ONE) != 0)
      throw new ArithmeticException("N і R не взаємно прості");

    BigInteger n1 = rnIdentity.getY().multiply(BigInteger.valueOf(-1));
    BigInteger r1 = rnIdentity.getX();
    BigInteger remainder =
        KaratsubaMultiplication.multiply(product, n1)
            .subtract(
                KaratsubaMultiplication.multiply(product, n1)
                    .shiftRight(r.bitLength() - 1)
                    .shiftLeft(r.bitLength() - 1));
    BigInteger u =
        product.add(KaratsubaMultiplication.multiply(remainder, n)).shiftRight(r.bitLength() - 1);

    while (u.compareTo(n) >= 0) u = u.subtract(n);

    return new Pair<>(u, r1);
  }

  public static BigInteger pow(BigInteger a, BigInteger e, BigInteger n) {
    BigInteger u = powHelp(a, e, n);
    BigInteger r = ONE.shiftLeft(n.bitLength());
    BezoutIdentity eeRes = ExtendedEuclideanAlgorithm.calculateGcd(r, n);

    if (eeRes.getGcd().compareTo(ONE) != 0)
      throw new ArithmeticException("N і R не взаємно прості");
    BigInteger r1 = eeRes.getX();

    return u.multiply(r1).mod(n);
  }

  private static BigInteger powHelp(BigInteger a, BigInteger e, final BigInteger n) {
    BigInteger r = ONE.shiftLeft(n.bitLength());
    BigInteger a1 = a.shiftLeft(r.bitLength() - 1).mod(n);
    BigInteger x = ONE.shiftLeft(r.bitLength() - 1).mod(n);

    for (int i = e.bitLength() - 1; i >= 0; --i) {
      x = multiplicationHelp(x, x, n).getKey();
      if (e.testBit(i)) x = multiplicationHelp(a1, x, n).getKey();
    }

    return multiplicationHelp(x, ONE, n).getKey();
  }
}
