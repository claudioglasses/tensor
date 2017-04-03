// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** Transpose is consistent with Mathematica::Transpose
 * Transpose is consistent with MATALB::permute
 * <br/>
 * Transpose does <b>not</b> conjugate the elements.
 * <br/>
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Transpose.html">Transpose</a> */
public enum Transpose {
  ;
  /** @param matrix
   * @return matrix transposed */
  public static Tensor of(Tensor matrix) {
    return of(matrix, 1, 0);
  }

  /** transpose according to permutation sigma.
   * function conforms to Mathematica::Transpose
   * 
   * Warning: different convention from MATLAB::permute !
   * 
   * @param tensor with array structure
   * @param sigma is permutation with rank of tensor == sigma.length
   * @return */
  public static Tensor of(Tensor tensor, Integer... sigma) {
    if (tensor instanceof Scalar)
      throw TensorRuntimeException.of(tensor);
    if (!Dimensions.isArray(tensor))
      throw TensorRuntimeException.of(tensor);
    if (TensorRank.of(tensor) != sigma.length)
      throw TensorRuntimeException.of(tensor);
    // ---
    List<Integer> dims = Dimensions.of(tensor);
    int[] size = new int[dims.size()];
    for (int index = 0; index < size.length; ++index)
      size[index] = dims.get(index);
    Size mySize = new Size(size);
    Size tensorSize = mySize.permute(sigma);
    Tensor data = Tensor.of(tensor.flatten(-1));
    int[] inverse = new int[sigma.length];
    IntStream.range(0, sigma.length).forEach(index -> inverse[sigma[index]] = index);
    List<Scalar> list = new ArrayList<>();
    for (MultiIndex src : tensorSize)
      list.add(data.Get(mySize.indexOf(src.permute(inverse))));
    Integer[] tsize = new Integer[sigma.length]; // int[] to Integer[]
    IntStream.range(0, sigma.length).forEach(index -> tsize[index] = tensorSize.size[index]);
    return ArrayReshape.of(list.stream(), tsize);
  }
}