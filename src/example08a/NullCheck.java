package example08a;

import support.Output;
import support.RunWith;

@RunWith({
  "-XX:-TieredCompilation", "-XX:+PrintCompilation",
  "-XX:+UnlockDiagnosticVMOptions",
  "-XX:CompileCommand=print,example08a/NullCheck::hotMethod"
})
@Output(highlight={"NullCheck::hotMethod", "made not entrant"})
public class NullCheck {
  public static void main(String[] args)
   throws InterruptedException
  {
    for ( int i = 0; i < 20_000; ++i ) {
    	hotMethod("hello");
    }
    
    Thread.sleep(5_000);
   
    for ( int i = 0; i < 10; ++i ) {  
      System.out.printf("tempting fate %d%n", i);  
      try {
        hotMethod(null);
      } catch ( NullPointerException e ) {
        // ignore
      }
    }
  }
  
  static final void hotMethod(final Object value) {
    value.hashCode();
  }
}