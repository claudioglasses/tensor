// code by jph
package ch.ethz.idsc.tensor.io.obj;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.HomeDirectory;

enum WavefrontFormatDemo {
  ;
  static void load(File file) throws IOException {
    try (InputStream inputStream = new FileInputStream(file)) {
      Stream<String> stream = StaticHelper.lines(inputStream);
      Wavefront wavefront = WavefrontFormat.parse(stream);
      Tensor normals = wavefront.normals();
      System.out.println(Dimensions.of(normals));
      Tensor vertices = wavefront.vertices();
      System.out.println(Dimensions.of(vertices));
      List<WavefrontObject> objects = wavefront.objects();
      System.out.println(objects.size());
    }
  }

  public static void main(String[] args) throws IOException {
    File dir = HomeDirectory.file("Projects/gym-duckietown/gym_duckietown/meshes");
    for (File file : dir.listFiles())
      if (file.getName().endsWith(".obj"))
        try {
          load(file);
        } catch (Exception exception) {
          System.err.println(file);
          // ---
          exception.printStackTrace();
        }
  }
}