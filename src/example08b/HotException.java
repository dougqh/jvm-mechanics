package example08b;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public final class HotException {
  private static final int SIZE = 100000;

  public static final void main(final String[] args) {
    Object[] someNulls = randomObjects(0.1);
    Result result1 = countNPEs1(someNulls);
    System.out.printf(
      "Null Proportion: %2f\t Caught: %6d\t Unique: %6d%n",
      0.1,
      result1.numCaught,
      result1.numUnique
    );

    Object[] halfNulls = randomObjects(0.5);
    Result result2 = countNPEs2(halfNulls);
    System.out.printf(
      "Null Proportion: %2f\t Caught: %6d\t Unique: %6d%n",
      0.5,
      result2.numCaught,
      result2.numUnique
    );
    
    Object[] manyNulls = randomObjects(0.9);
    Result result3 = countNPEs3(manyNulls);
    System.out.printf(
      "Null Proportion: %2f\t Caught: %6d\t Unique: %6d%n",
      0.9,
      result3.numCaught,
      result3.numUnique
    );
  }

  private static final Object[] randomObjects(final double nullProportion) {
    Random random = ThreadLocalRandom.current();

    Object[] objects = new Object[SIZE];
    for (int i = 0; i < objects.length; ++i) {
      objects[i] = (random.nextDouble() < nullProportion) ? null: new Object();
    }
    return objects;
  }

  private static final Result countNPEs1(final Object[] objects) {
    int numCaught = 0;
    Set<NullPointerException> nullPointerExceptions = new HashSet<>();

    for (Object object : objects) {
      try {
        object.toString();
      } catch (NullPointerException e) {
        boolean added = nullPointerExceptions.add(e);
        if ( !added ) {
          e.printStackTrace();
        }
        
        numCaught += 1;
      }
    }
    return new Result(numCaught, nullPointerExceptions.size());
  }

  private static final Result countNPEs2(final Object[] objects) {
    int numCaught = 0;
    Set<NullPointerException> nullPointerExceptions = new HashSet<>();

    for (Object object : objects) {
      try {
        object.toString();
      } catch (NullPointerException e) {
        nullPointerExceptions.add(e);
        
        numCaught += 1;
      }
    }
    return new Result(numCaught, nullPointerExceptions.size());
  }

  private static final Result countNPEs3(final Object[] objects) {
    int numCaught = 0;
    Set<NullPointerException> nullPointerExceptions = new HashSet<>();

    for (Object object : objects) {
      try {
        object.toString();
      } catch (NullPointerException e) {
        nullPointerExceptions.add(e);
        
        numCaught += 1;
      }
    }
    return new Result(numCaught, nullPointerExceptions.size());
  }
  
  static class Result {
    final int numCaught;
    final int numUnique;
    
    Result(int numCaught, int numUnique) {
      this.numCaught = numCaught;
      this.numUnique = numUnique;
    }
  }
}