// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/HilbertMatrix.html">HilbertMatrix</a> */
public enum HilbertMatrix {
  ;
  /** @param n
   * @param m
   * @return n x m Hilbert matrix with elements of the form 1/(i+j-1) */
  public static Tensor of(int n, int m) {
    return Tensors.matrix((i, j) -> RationalScalar.of(1, i + j + 1), n, m);
  }

  /** @param n
   * @return n x n Hilbert matrix with elements of the form 1/(i+j-1) */
  public static Tensor of(int n) {
    return of(n, n);
  }
}
