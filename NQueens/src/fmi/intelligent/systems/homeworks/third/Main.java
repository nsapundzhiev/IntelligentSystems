package fmi.intelligent.systems.homeworks.third;

public class Main {

	public static void main(String[] args) {
		NQueensSolver solver = new NQueensSolver(5);

		solver.solve();
		solver.printQueensTable();
	}
}
