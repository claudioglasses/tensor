// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.Serializable;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** the interface Interpolation has no direct equivalent in Mathematica */
public interface Interpolation extends Serializable {
  /** if index.length() is less than the rank r of the tensor object that is being interpolated,
   * then the function get(...) returns a tensor of rank r - index.length()
   * 
   * @param index must not be {@link Scalar}
   * @return expression similar to Tensor::get(index)
   * @throws TensorRuntimeException if index is outside dimensions of tensor, or index is a {@link Scalar} */
  Tensor get(Tensor index);

  /** when using Get(...), index.length() must equal the rank r of the tensor object that is being interpolated.
   * 
   * @param index must not be {@link Scalar}
   * @return expression similar to Tensor::Get(index)
   * @throws TensorRuntimeException if index is outside dimensions of tensor, or index is a {@link Scalar} */
  Scalar Get(Tensor index);
}