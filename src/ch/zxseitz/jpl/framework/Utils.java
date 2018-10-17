package ch.zxseitz.jpl.framework;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class Utils {
  public static final Charset utf8 = Charset.forName("UTF-8");

  public static String readFile(String path, Charset encoding) {
    try {
      var encoded = Files.readAllBytes(Paths.get(path));
      return new String(encoded, encoding);
    } catch (Exception e) {
      System.err.println(String.format("Error reading source %s", path));
      e.printStackTrace();
    }
    return null;
  }
}
