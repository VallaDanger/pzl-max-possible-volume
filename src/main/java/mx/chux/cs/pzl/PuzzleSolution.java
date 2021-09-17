package mx.chux.cs.pzl;

public interface PuzzleSolution<R> {

	public R bruteForceSolution();
	
	public R optimalSolution();
	
}
