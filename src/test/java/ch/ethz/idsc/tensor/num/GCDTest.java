// code by jph
package ch.ethz.idsc.tensor.num;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class GCDTest extends TestCase {
  public void testExamples() {
    assertEquals(GCD.of(RealScalar.of(+90), RealScalar.of(+60)), RealScalar.of(30));
    assertEquals(GCD.of(RealScalar.of(+90), RealScalar.of(-60)), RealScalar.of(30));
    assertEquals(GCD.of(RealScalar.of(-90), RealScalar.of(-60)), RealScalar.of(30));
    assertEquals(GCD.of(RealScalar.of(-90), RealScalar.of(+60)), RealScalar.of(30));
  }

  public void testZero() {
    assertEquals(GCD.of(RealScalar.of(0), RealScalar.of(+60)), RealScalar.of(60));
    assertEquals(GCD.of(RealScalar.of(+60), RealScalar.of(0)), RealScalar.of(60));
    assertEquals(GCD.of(RealScalar.of(0), RealScalar.of(-60)), RealScalar.of(60));
    assertEquals(GCD.of(RealScalar.of(-60), RealScalar.of(0)), RealScalar.of(60));
    assertEquals(GCD.of(RealScalar.of(0), RealScalar.of(0)), RealScalar.of(0));
  }

  public void testReduce() {
    Scalar scalar = Tensors.vector(13 * 700, 64 * 7, 4 * 7 * 13).stream() //
        .map(Scalar.class::cast) //
        .reduce(GCD::of).get();
    assertEquals(scalar.toString(), "28");
  }

  public void testRational() {
    Scalar scalar = GCD.of(RationalScalar.of(3, 2), RationalScalar.of(2, 1));
    assertEquals(scalar, RationalScalar.HALF); // Mathematica gives 1/2
  }

  public void testComplex1() {
    Scalar scalar = GCD.of(ComplexScalar.of(2, 1), ComplexScalar.of(3, 1));
    assertEquals(scalar, ComplexScalar.I); // Mathematica gives 1
  }

  public void testComplex2() {
    // GCD[9 + 3 I, 123 + 9 I]
    Scalar scalar = GCD.of(ComplexScalar.of(9, 3), ComplexScalar.of(123, 9));
    assertEquals(scalar, ComplexScalar.of(-3, 3));
  }

  public void testQuantity() {
    Scalar scalar = GCD.of(Quantity.of(2 * 7 * 5, "s"), Quantity.of(2 * 5 * 13, "s"));
    assertEquals(scalar, Quantity.of(2 * 5, "s"));
  }

  public void testNumericFail() {
    try {
      GCD.of(RealScalar.of(0.3), RealScalar.of(+60));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      GCD.of(RealScalar.of(123), RealScalar.of(0.2));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
