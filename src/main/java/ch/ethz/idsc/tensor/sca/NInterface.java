// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Scalar;

public interface NInterface {
  /** @return numerical approximation of this scalar
   * for instance 1/3 is converted to 1.0/3.0 == 0.3333... */
  Scalar n();
}