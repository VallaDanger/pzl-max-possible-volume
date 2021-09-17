package mx.chux.cs.pzl.arrays;

import org.junit.Test;

import mx.chux.cs.pzl.PuzzleSolution;

import static org.assertj.core.api.Assertions.*;

public class MaxPossibleVolumeTest {

	private void assertBruteForceSolution( final PuzzleSolution<Integer> puzzle, int solution ) {
		assertThat(solution).isEqualTo(puzzle.bruteForceSolution());
	}
	
	private void assertZeroSolution(final PuzzleSolution<Integer> puzzle) {
		final int solution = puzzle.optimalSolution();
		assertThat(solution).isZero();
		assertBruteForceSolution(puzzle, solution);
	}
	
	private void assertNonZeroSolution(final PuzzleSolution<Integer> puzzle, final int value) {
		final int solution = puzzle.optimalSolution();
		assertThat(solution).isNotZero().isEqualTo(value);
		assertBruteForceSolution(puzzle, solution);
	}
	
	@Test
	public void bestCaseTest() {
		final int[] testCase = new int[] { 0, 1, 0, 2, 1, 0, 3, 1, 0, 1, 2 };
		assertNonZeroSolution(MaxPossibleVolume.inArray(testCase), 8);
	}

	@Test
	public void emptyArrayTest() {
		// no terrain
		final int[] testCase = new int[] {};
		assertZeroSolution(MaxPossibleVolume.inArray(testCase));
	}

	@Test
	public void onlyOneElementTest() {
		// water spills to the sides
		final int[] testCase = new int[] { 1 };
		assertZeroSolution(MaxPossibleVolume.inArray(testCase));
	}

	@Test
	public void twoZeroesTest() {
		// plain terrain
		final int[] testCase = new int[] { 0, 0 };
		assertZeroSolution(MaxPossibleVolume.inArray(testCase));
	}
	
	@Test
	public void twoOnesTest() {
		// plain terrain
		final int[] testCase = new int[] { 1, 1 };
		assertZeroSolution(MaxPossibleVolume.inArray(testCase));
	}

	@Test
	public void allZeroesTest() {
		// wide plain terrain
		final int[] testCase = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		assertZeroSolution(MaxPossibleVolume.inArray(testCase));
	}

	@Test
	public void oneAndZeroesTest() {
		// water spills to the right
		final int[] testCase = new int[] { 1, 0, 0, 0, 0, 0, 0 };
		assertZeroSolution(MaxPossibleVolume.inArray(testCase));
	}

	@Test
	public void zeroesAndOneTest() {
		// water spills to the left
		final int[] testCase = new int[] { 0, 0, 0, 0, 0, 0, 1 };
		assertZeroSolution(MaxPossibleVolume.inArray(testCase));
	}

	@Test
	public void middleOneTest() {
		// water spills towards both sides
		final int[] testCase = new int[] { 0, 0, 0, 1, 0, 0, 0 };
		assertZeroSolution(MaxPossibleVolume.inArray(testCase));
	}

	@Test
	public void oneZeroesOneTest() {
		// 2 units of water get collected in the middle
		final int[] testCase = new int[] { 1, 0, 0, 1 };
		assertNonZeroSolution(MaxPossibleVolume.inArray(testCase), 2);
	}

	@Test
	public void leftHigherThanRightTest() {
		// no matter how high a boundary is, water levels to smaller right side
		final int[] testCase = new int[] { 0, 10, 0, 0, 0, 0, 0, 0, 1 };
		assertNonZeroSolution(MaxPossibleVolume.inArray(testCase), 6);
	}

	@Test
	public void rightHigherThanLeftTest() {
		// no matter how high a boundary is, water levels to smaller left side
		final int[] testCase = new int[] { 0, 1, 0, 0, 0, 0, 0, 0, 10 };
		assertNonZeroSolution(MaxPossibleVolume.inArray(testCase), 6);
	}

}
