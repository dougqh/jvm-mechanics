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
    Square square = new Square();
    
    for ( int i = 0; i < 10_000; ++i ) {
      apply1(square, i);
      apply2(square, i);
      apply3(square, i);
      apply4(square, i);
      apply5(square, i);
      apply6(square, i);
      apply7(square, i);
      apply8(square, i);
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

