package example10c;

import support.Output;
import support.RunWith;
import example10.support.AlsoSquare;
import example10.support.AnotherSquare;
import example10.support.Func;
import example10.support.Sqrt;
import example10.support.Square;
import example10.support.YetAnotherSquare;

@RunWith({
  "-XX:-TieredCompilation", "-XX:+PrintCompilation",
  // "-XX:+PrintSafepointStatistics", "-XX:PrintSafepointStatisticsCount=1"
})
@Output(highlight={
  "ClassDevirtualization::apply\\d",
  "made not entrant"
})
public class ClassDevirtualization {
  public static void main(String[] args) throws InterruptedException {
    System.out.println("Using Square...");
    Func func = new Square();
    for ( int i = 0; i < 20_000; ++i ) {
      apply1(func, i);
      apply2(func, i);
    }
    
    Thread.sleep(5_000);
    
    System.out.printf("Loading %s to Deoptimize Now!%n", Sqrt.class);

    System.out.println("Keep using Square in apply1...");
    func = new Square();
    for ( int i = 0; i < 20_000; ++i ) {
      apply1(func, i);
    }

    Thread.sleep(5_000);

    System.out.println("Use AlsoSquare in apply1...");
    func = new AlsoSquare();
    for ( int i = 0; i < 20_000; ++i ) {
      apply1(func, i);
    }

    Thread.sleep(5_000);

    System.out.println("Use AnotherSquare in apply1...");
    func = new AnotherSquare();
    for ( int i = 0; i < 20_000; ++i ) {
      apply1(func, i);
    }
    
    Thread.sleep(5_000);

    System.out.println("Use YetAnotherSquare in apply1...");
    func = new YetAnotherSquare();
    for ( int i = 0; i < 20_000; ++i ) {
      apply1(func, i);
    }
    
    Thread.sleep(5_000);
  }
  
  static double apply1(Func func, int x) {
    return func.apply(x);
  }
  
  static double apply2(Func func, int x) {
    return func.apply(x);
  }
}