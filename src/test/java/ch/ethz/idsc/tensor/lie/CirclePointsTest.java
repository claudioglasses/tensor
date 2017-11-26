// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.Arrays;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.opt.ConvexHull;
import ch.ethz.idsc.tensor.red.Norm2Squared;
import ch.ethz.idsc.tensor.red.Tally;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class CirclePointsTest extends TestCase {
  public void testSimple() {
    int n = 5;
    Tensor tensor = CirclePoints.of(n);
    assertEquals(Dimensions.of(tensor), Arrays.asList(n, 2));
    Tensor nrm = Tensor.of(tensor.stream().map(Norm2Squared::ofVector));
    assertTrue(Chop._14.close(nrm, Array.of(l -> RealScalar.ONE, n)));
  }

  public void testFirst() {
    int n = 5;
    Tensor tensor = CirclePoints.of(n);
    assertEquals(tensor.get(0), Tensors.vector(1, 0));
  }

  public void testConvexHull() {
    Tensor tensor = CirclePoints.of(6);
    Tensor hull = ConvexHull.of(tensor);
    assertEquals(Tally.of(tensor), Tally.of(hull));
  }

  public void testZero() {
    int n = 0;
    Scalar scalar = DoubleScalar.of(2 * Math.PI / n);
    assertEquals(scalar, DoubleScalar.POSITIVE_INFINITY);
  }

  public void testSmall() {
    assertEquals(CirclePoints.of(0), Tensors.empty());
    assertEquals(CirclePoints.of(1), Tensors.fromString("{{1, 0}}"));
    // System.out.println(CirclePoints.of(2));
    assertEquals(Chop._14.of(CirclePoints.of(2)), Tensors.fromString("{{1, 0}, {-1, 0}}"));
  }
}
