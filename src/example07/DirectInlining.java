package example07;

import support.NullPrintStream;
import support.Output;
import support.RunWith;

@RunWith({
  "-XX:-TieredCompilation", "-XX:+PrintCompilation",
  "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining",
  // "-XX:CompileCommand=print,example07/DirectInlining::hotMethod"
})
@Output(highlight={
  // highlights for PrintCompilation / PrintInlining
  "DirectInlining::square", 
  "PrintStram::println",
  
  // highlights for assembly
  "invokevirtual println", "0x31", "0x51"
})
public class DirectInlining {
  public static void main(String[] args)
    throws InterruptedException
  {
    System.setOut(new NullPrintStream());
    for ( int i = 0; i < 20_000; ++i ) {
      hotMethod();
    }
    Thread.sleep(5_000);
  }

  public static void hotMethod() {
    System.out.println(square(7));
    System.out.println(square(9));
  }

  static int square(int x) {
    return x * x;
  }
}