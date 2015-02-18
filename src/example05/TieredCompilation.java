package example05;

import support.Output;
import support.RunWith;

@RunWith({"-XX:+TieredCompilation", "-XX:+PrintCompilation"})
@Output(highlight={
  "StreamEncoder::writeBytes",
  "Object::<init>",
  "made not entrant"
})
public final class TieredCompilation {
  public static final void main(final String[] args)
    throws InterruptedException 
  {
    for ( int i = 0; i < 3_000; ++i ) {
      method();
    }
    
    System.out.println("Waiting for the compiler...");
    Thread.sleep(5_000);

  for ( int i = 0; i < 20_000; ++i ) {
      method();
    }
    
    System.out.println("Waiting for the compiler...");
    Thread.sleep(5_000);
  }
  
  private static final void method() {
    // Do something while doing nothing.
    System.out.print('\0');
  }
}