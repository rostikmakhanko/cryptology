package rijndael;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RijndaelTest {
  @Test
  void emptyString() {
    String empty = "";
    String key = "test key";
    List<byte[]> encoded = Rijndael.encode(empty, key);
    assertTrue(Rijndael.compareDecoded(empty, Rijndael.decode(encoded, key)));
  }

  @Test
  void encryptionAndDecryption() {
    String key1 = "test key 1";
    String key2 = "test key 2";

    String testString = "Some Test String";

    List<byte[]> encoded1 = Rijndael.encode(testString, key1);
    List<byte[]> encoded2 = Rijndael.encode(testString, key2);

    // Encoded data with different keys is different
    assertNotEquals(encoded1, encoded2);

    assertTrue(Rijndael.compareDecoded(testString, Rijndael.decode(encoded1, key1)));
    assertTrue(Rijndael.compareDecoded(testString, Rijndael.decode(encoded2, key2)));

    // Swap keys
    assertFalse(Rijndael.compareDecoded(testString, Rijndael.decode(encoded1, key2)));
    assertFalse(Rijndael.compareDecoded(testString, Rijndael.decode(encoded2, key1)));
  }
}
