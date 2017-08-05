// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.ConjugateTranspose;
import ch.ethz.idsc.tensor.sca.Conjugate;

/** Transpose is consistent with Mathematica::Transpose
 * Transpose is consistent with MATALB::permute
 * 
 * <p>Transpose does <b>not</b> conjugate the elements.
 * For that purpose, use {@link Conjugate}, or {@link ConjugateTranspose}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Transpose.html">Transpose</a> */
public enum Transpose {
  ;
  /** Remark:
   * if the input tensor is a matrix, function Transpose.of(tensor)
   * is identical albeit faster than Transpose.of(tensor, 1, 0).
   * 
   * <p>The function also operates on matrices with tensors as entries. Example:
   * <pre>
   * Transpose.of({{1, {2, 2}}, {{3}, 4}, {5, {6}}}) == {{1, {3}, 5}, {{2, 2}, 4, {6}}}
   * </pre>
   * 
   * @param tensor
   * @return tensor with the two first dimensions transposed and the remaining dimensions left as-is
   * @throws Exception if input is a vector or scalar */
  public static Tensor of(Tensor tensor) {
    List<Integer> dims = Dimensions.of(tensor);
    return Tensors.matrix((i, j) -> tensor.get(j, i), dims.get(1), dims.get(0));
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
    if (!ArrayQ.ofRank(tensor, sigma.length))
      throw TensorRuntimeException.of(tensor);
    if (tensor.isScalar())
      throw TensorRuntimeException.of("Transpose[scalar, {}] undefined", tensor);
    // ---
    List<Integer> dims = Dimensions.of(tensor);
    int[] size = new int[dims.size()];
    for (int index = 0; index < size.length; ++index)
      size[index] = dims.get(index);
    Size mySize = Size.of(size);
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
