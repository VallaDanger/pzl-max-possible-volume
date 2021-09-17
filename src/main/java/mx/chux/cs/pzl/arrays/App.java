package mx.chux.cs.pzl.arrays;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    
    public static void main( String[] args ) {
	
	    /*
			3 │                ┌┐
			  │                ││
			2 │        ┌┐      ││          ┌┐
			  │        ││      ││          ││
			1 │   ┌┐   ││ ┌┐   ││  ┌┐   ┌┐ ││
			  │   ││   ││ ││   ││  ││   ││ ││
			  └─┬─┴┴─┬─┴┴─┴┴─┬─┴┴──┴┴─┬─┴┴─┴┴──
			    │ 1  │ 2  1  │ 3   1  │  1  2
			    0    0       0        0
	    */
	     
	    // for this best-case the answer must be 8
    	
		final int[] array = new int[] { 0, 1, 0, 2, 1, 0, 3, 1, 0, 1, 2 };

		final Instant start = Instant.now();
		final int maxPossibleVolume = MaxPossibleVolume.inArray(array).optimalSolution();
		final Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();

		LOGGER.log(Level.INFO, "MaxPossibleVolume [ time: {0} ]: {1}", new Object[] { timeElapsed, maxPossibleVolume });
		
    }
    
}
