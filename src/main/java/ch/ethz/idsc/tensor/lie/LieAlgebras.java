// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dot;

/** class provides ad-tensors of several low-dimensional Lie-algebras */
// EXPERIMENTAL
public enum LieAlgebras {
  ;
  private static final Scalar P1 = RealScalar.ONE;
  private static final Scalar M1 = RealScalar.ONE.negate();
  private static final Scalar P2 = RealScalar.of(+2);
  private static final Scalar M2 = RealScalar.of(-2);

  private static Tensor _so3() {
    Tensor ad = Array.zeros(3, 3, 3);
    ad.set(P1, 2, 1, 0);
    ad.set(M1, 2, 0, 1);
    ad.set(P1, 0, 2, 1);
    ad.set(M1, 0, 1, 2);
    ad.set(P1, 1, 0, 2);
    ad.set(M1, 1, 2, 0);
    return ad;
  }

  private static final Tensor SO3 = _so3().unmodifiable();

  /** @param x matrix
   * @param y matrix
   * @return */
  public static Tensor bracketMatrix(Tensor x, Tensor y) {
    return Dot.of(x, y).subtract(Dot.of(y, x));
  }

  /** @return ad tensor of 3-dimensional Heisenberg Lie-algebra */
  public static Tensor heisenberg3() {
    Tensor ad = Array.zeros(3, 3, 3);
    ad.set(P1, 2, 1, 0);
    ad.set(M1, 2, 0, 1);
    return ad;
  }

  /** @return ad tensor of 3-dimensional so(3) */
  public static Tensor so3() {
    return SO3;
  }

  /** @return ad tensor of 3-dimensional sl(3) */
  public static Tensor sl3() {
    Tensor ad = Array.zeros(3, 3, 3);
    ad.set(P1, 1, 1, 0);
    ad.set(M1, 1, 0, 1);
    ad.set(M1, 2, 2, 0);
    ad.set(P1, 2, 0, 2);
    ad.set(P2, 0, 2, 1);
    ad.set(M2, 0, 1, 2);
    return ad;
  }
}