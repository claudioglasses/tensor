// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ChopTest extends TestCase {
  public void testChop() {
    Tensor v = Tensors.vectorDouble(1e-10, 1e-12, 1e-14, 1e-16);
    Tensor c = v.map(Chop._12);
    assertFalse(c.get(0).equals(RealScalar.ZERO));
    assertFalse(c.get(1).equals(RealScalar.ZERO));
    assertTrue(c.get(2).equals(RealScalar.ZERO));
    assertTrue(c.get(3).equals(RealScalar.ZERO));
  }

  public void testCustom() {
    Chop chop = Chop.below(3.142);
    assertTrue(chop.close(DoubleScalar.of(Math.PI), RealScalar.ZERO));
    assertFalse(chop.close(DoubleScalar.of(3.15), RealScalar.ZERO));
  }

  public void testExclusive() {
    assertFalse(Chop._12.allZero(RealScalar.of(Chop._12.threshold())));
  }

  public void testFail() {
    try {
      Chop.below(-1e-9);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNaN() {
    Scalar s = (DoubleScalar) Chop._05.apply(DoubleScalar.INDETERMINATE);
    assertTrue(Double.isNaN(s.number().doubleValue()));
  }

  public void testInf() {
    Scalar s = (DoubleScalar) Chop._05.apply(DoubleScalar.NEGATIVE_INFINITY);
    assertTrue(Double.isInfinite(s.number().doubleValue()));
  }

  public void testClose() {
    Scalar s1 = DoubleScalar.of(1);
    Scalar s2 = DoubleScalar.of(1 + 1e-10);
    assertTrue(Chop._07.close(s1, s2));
    assertTrue(Chop._09.close(s1, s2));
    assertFalse(Chop._10.close(s1, s2));
    assertFalse(Chop._12.close(s1, s2));
  }

  public void testCloseFail() {
    try {
      Chop._05.close(Tensors.vector(1), Tensors.vector(1, 1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}