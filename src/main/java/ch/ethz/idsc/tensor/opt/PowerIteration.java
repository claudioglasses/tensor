// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Normalize;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Chop;

/** The algorithm is also known as the <em>Von Mises iteration</em>.
 * 
 * https://en.wikipedia.org/wiki/Power_iteration */
public enum PowerIteration {
  ;
  /** @param m
   * @return */
  public static Tensor of(Tensor m) {
    return of(m, RandomVariate.of(NormalDistribution.standard(), m.length()));
  }

  /** @param m
   * @param x
   * @return */
  public static Tensor of(Tensor m, Tensor x) {
    final int max = m.length() * 15;
    for (int iteration = 0; iteration < max; ++iteration) {
      final Tensor prev = x;
      x = Normalize.of(m.dot(x));
      if (Chop._15.allZero(prev.subtract(x)) || Chop._15.allZero(prev.add(x)))
        return x;
    }
    throw TensorRuntimeException.of(m, x);
  }
}
