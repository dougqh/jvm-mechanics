package example11a;

import support.Output;
import support.RunWith;
import dne.DoesNotExist;

@RunWith({"-XX:-TieredCompilation", "-XX:+PrintCompilation"})
@Output(highlight={
  "UnloadedForever::factory",
  "made not entrant",
  "made zombie"
})
public class UnloadedForever {
  public static void main(String[] args) {
    for ( int i = 0; i < 100_000; ++i ) {
      try {
        factory();
      } catch ( Throwable t ) {
        // ignore
      }
    }
  }
  
  static DoesNotExist factory() {
    return new DoesNotExist();
  }
}
