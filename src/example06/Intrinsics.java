package example06;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import support.Output;
import support.RunWith;

@RunWith({
  "-XX:-TieredCompilation", "-XX:+PrintCompilation",
  "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining"
})
@Output(highlight={"Math::min", "intrinsic"})
public class Intrinsics {
  public static void main(String[] args) throws InterruptedException {
    int[] data = randomInts(100_000);
    
    int min = Integer.MAX_VALUE;
    for ( int x: data ) {
      min = Math.min(min, x);
    }
    
    Thread.sleep(5_000);
    
    System.out.println(min);
  }
  
  public static int[] randomInts(int size) {
    int[] data = new int[size];
    
    Random random = ThreadLocalRandom.current();
    
    for ( int i = 0; i < size; ++i ) {
      data[i] = random.nextInt();
    }
    
    return data;
  }
}
