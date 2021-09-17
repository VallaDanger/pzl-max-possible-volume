package mx.chux.cs.pzl.arrays;

import mx.chux.cs.pzl.PuzzleSolution;

public class MaxPossibleVolume implements PuzzleSolution<Integer> {
	
	/*
		3 │                ┌┐
		  │                ││
		2 │        ┌┐  ~ ~ ││   ~ ~  ~ ┌┐
		  │        ││      ││          ││
		1 │   ┌┐ ~ ││ ┌┐ ~ ││  ┌┐ ~ ┌┐ ││
		  │   ││   ││ ││   ││  ││   ││ ││
		  └─┬─┴┴─┬─┴┴─┴┴─┬─┴┴──┴┴─┬─┴┴─┴┴──
		    │ 1  │ 2  1  │ 3   1  │  1  2
		    0    0       0        0
		    
		[ 0, 1, 0, 2, 1, 0, 3, 1, 0, 1, 2 ]
	*/
	
	// Imagine that each position in array is the height of terrain.
	// The goal is to find how much water could it be contained if flooded.
	// Limits of the array (0 and size) can't be used as walls for containment.
	// In the example, every character ~ is a unit of water above terrain (8).
	
	public static MaxPossibleVolume inArray(final int[] array) {
		return new MaxPossibleVolume(array);
	}

	private final int[] array;
	
	final int size;
	
	private MaxPossibleVolume(int[] array) {
		this.array = array;
		this.size = this.array.length;
	}
	
	private int get(int index) {
		return this.array[index];
	}
	
	@Override
	public Integer bruteForceSolution() {
		// time complexity: O(n^2)

		int maxVolume = 0;
		
		// the general idea is that every step can only contain as much
		// water above it as the difference between its own height and the
		// immediate higher elevation (IHE) towards its left and right.
		// IHE is not the highest, just higher than current step.
		// If highest elevation ( left or right ) is used and there
		// is a higher elevation than current step ( opposite from highest ), 
		// then water above it would spill towards that side.
		
		// scan every element in array ( this is the current step )
		for( int i = 0; i < this.size ; i++ ) {
			
			int maxLeft = 0;
			int maxRight = 0;
			
			int j = i-1;
			
			// find the highest elevation to the left ( start of array )
			while( j >= 0 ) {
				maxLeft = Math.max(maxLeft, get(j--));
			}
			
			j = i+1;
			
			// find the highest elevation to the right ( end of array )
			while( j < this.size ) {
				maxRight = Math.max(maxRight, get(j++));
			}
			
			// Take the lesser of the higher elevations.
			// Terrain might be plain and max height the same on both sides and difference is 0.
			// Current step might be the highest; max is always lesser and difference is negative.
			final int volume = Math.min(maxLeft, maxRight) - get(i);
			
			//only consider cases where water units are greater than zero ( not spilled )
			maxVolume += ( volume > 0 )? volume : 0;
			
		}
		
		return Integer.valueOf(maxVolume);
	}
	
	private int[] maxLeft() {
		
		int[] maxArray = new int[this.size];
		
		maxArray[0] = get(0);
		
		for( int index = 1 ; index < this.size ; index++ ) {
			maxArray[index] = Math.max(maxArray[index-1], get(index));
		}
		
		return maxArray;
	}
	
	private int[] maxRight() {
		
		int[] maxArray = new int[this.size];
		
		maxArray[this.size-1] = get(this.size-1);
		
		for( int index = this.size-2 ; index >= 0 ; index-- ) {
			maxArray[index] = Math.max(maxArray[index+1], get(index));
		}
		
		return maxArray;
	}
	
	@Override
	public Integer optimalSolution() { 
		
		int indexOfMaxHeight = 0;
		for( int index = 0 ; index < this.size ; index++ ) {
			if( get(index) > get(indexOfMaxHeight) ) {
				indexOfMaxHeight = index;
			}
		}
		
		int maxVolume = 0;
		
		int maxLeft = 0;
		for( int index = 0 ; index < indexOfMaxHeight ; index++ ) {
			maxLeft = Math.max(maxLeft, get(index));
			maxVolume += maxLeft - get(index);
		}
		
		int maxRight = 0;
		for( int index = this.size-1 ; index > indexOfMaxHeight ; index-- ) {
			maxRight = Math.max(maxRight, get(index));
			maxVolume += maxRight - get(index);
		}
		
		return Integer.valueOf(maxVolume);
	}
		
	public Integer goodSolution() {
		// time complexity: O(n)
		
		// short and simple solution, but uses more space
		
		int[] maxFromLeft = maxLeft();
		int[] maxFromRight = maxRight();
		
		int maxVolume = 0;
		
		for( int index = 0 ; index < this.size ; index++ ) {
			maxVolume += Math.min(maxFromLeft[index], maxFromRight[index]) - get(index);
		} 
		
		return Integer.valueOf(maxVolume);
	}
	
	public Integer bestSolution() {
		// time complexity: O(n)
		
		int i = 0;
		int j = this.size-1;
		
		int maxLeft = 0;
		int maxRight = 0;
		
		int maxVolume = 0;
		
		// Max height to the right of i or to the left of j is
		// not unknown, it will be discovered at each iteration
		while( i < j ) {
			
			// all we know at every step is that the smaller side 
			// can contain some volume if it is greater than 0
			
			final int valueAtI = get(i);
			final int valueAtJ = get(j);
			
			// Find which side has a higher wall.
			// Algorithm will operate on smaller side.
			// The side with greater height is a wall.
			if( valueAtI <= valueAtJ ) {
			
				// if the current height is greater than 
				// previously seen max height, then it can't 
				// contain any volume, it would just spill.
				// Update max value and continue
				if( valueAtI >= maxLeft ) {
					maxLeft = valueAtI;
				} else {
					// if current height is smaller than
					// the previously seen max value, then
					// it can only contain the difference
					maxVolume += maxLeft - valueAtI;
				}
				
				// move left index to the right
				i += 1;
			
			} else {
				
				if( valueAtJ >= maxRight ) {
					maxRight = valueAtJ;
				} else {
					// max possible volume at each step is
					// given by the lesser height of both sides,
					// in any other case it would just spill
					maxVolume += maxRight - valueAtJ;
				}
				
				// move right index to the left
				j -= 1;
			
			}
			
		}
		
		return Integer.valueOf(maxVolume);
	}

}
