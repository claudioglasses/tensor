// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.Serializable;

import ch.ethz.idsc.tensor.Scalar;

/** @see FrechetDistribution
 * @see GumbelDistribution */
/* package */ abstract class AbstractAlphaBetaDistribution extends AbstractContinuousDistribution implements //
    MeanInterface, VarianceInterface, Serializable {
  static final double NEXT_DOWN_ONE = Math.nextDown(1.0);
  // ---
  final Scalar alpha;
  final Scalar beta;

  /** @param alpha
   * @param beta */
  AbstractAlphaBetaDistribution(Scalar alpha, Scalar beta) {
    this.alpha = alpha;
    this.beta = beta;
  }

  @Override // from Object
  public final String toString() {
    return String.format("%s[%s, %s]", getClass().getSimpleName(), alpha, beta);
  }
}
