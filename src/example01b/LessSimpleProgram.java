package example01b;

import support.RunWith;

@RunWith("-XX:+TieredCompilation")
public class LessSimpleProgram {
  static final int CHUNK_SIZE = 1_000;
  
  public static void main(String[] args) {
    Object trap = null;
    
    for ( int i = 0; i < 250; ++i ) {
      long startTime = System.nanoTime();
      
      for ( int j = 0; j < CHUNK_SIZE; ++j ) {
        new Object();
        
        if ( trap != null ) {
          System.out.println("trap!");
          trap = null;
        }
      }
      
      if ( i == 200 ) trap = new Object();
      
      long endTime = System.nanoTime();
      System.out.printf("%d\t%d%n", i, endTime - startTime);
    }
  }
}
