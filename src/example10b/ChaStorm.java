package example10b;
import support.Output;
import support.RunWith;
import example10.support.Func;
import example10.support.Sqrt;
import example10.support.Square;

@RunWith({
  "-XX:-TieredCompilation", "-XX:+PrintCompilation",
  "-XX:+PrintSafepointStatistics", "-XX:PrintSafepointStatisticsCount=1"
})
@Output(highlight={
  // highlight for PrintCompilation / PrintInlining
  "ChaStorm::apply\\d",
  "made not entrant",
  
  // highlight for PrintSafepointStatistics
  "Deoptimize"
})
public class ChaStorm {
  public static void main(String[] args) throws InterruptedException {
    Func func = new Square();
    
    for ( int i = 0; i < 10_000; ++i ) {
      apply1(func, i);
      apply2(func, i);
      apply3(func, i);
      apply4(func, i);
      apply5(func, i);
      apply6(func, i);
      apply7(func, i);
      apply8(func, i);
    }
    
    System.out.println("Waiting for compiler...");
    Thread.sleep(5_000);
    
    System.out.println("Deoptimize...");
    System.out.println(Sqrt.class);
    
    Thread.sleep(5_000);
  }
  
  static double apply1(Func func, int x) {
    return func.apply(x);
  }
  
  static double apply2(Func func, int x) {
    return func.apply(x);
  }
  
  static double apply3(Func func, int x) {
    return func.apply(x);
  }
  
  static double apply4(Func func, int x) {
    return func.apply(x);
  }
  
  static double apply5(Func func, int x) {
    return func.apply(x);
  }
  
  static double apply6(Func func, int x) {
    return func.apply(x);
  }

  static double apply7(Func func, int x) {
    return func.apply(x);
  }
  
  static double apply8(Func func, int x) {
    return func.apply(x);
  }
}

