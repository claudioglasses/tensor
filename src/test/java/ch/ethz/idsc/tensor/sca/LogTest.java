package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class LogTest extends TestCase {
  public void testLog() {
    Scalar s = DoubleScalar.of(-3);
    assertEquals(Log.of(s).toString(), "1.0986122886681098+3.141592653589793*I");
    assertEquals(Log.of(ZeroScalar.get()), RealScalar.NEGATIVE_INFINITY);
  }
}