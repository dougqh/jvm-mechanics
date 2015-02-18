package example10a;

import support.Output;
import support.RunWith;
import example10.support.Func;
import example10.support.Square;

@RunWith({
  "-XX:-TieredCompilation", "-XX:+PrintCompilation",
  "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining"
})
@Output(highlight={
  "Monomorphic::apply",
  "Square::apply",
  "inline \\(hot\\)"
})
public class Monomorphic {
  public static void main(String[] args)
    throws InterruptedException
  {
    Func func = new Square();
    
    for ( int i = 0; i < 20_000; ++i ) {
      apply(func, i);
    }
    
    Thread.sleep(5_000);
  }
  
  static double apply(Func func, int x) {
    return func.apply(x);
  }
}
