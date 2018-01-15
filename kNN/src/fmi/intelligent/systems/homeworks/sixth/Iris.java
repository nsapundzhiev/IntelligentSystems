package fmi.intelligent.systems.homeworks.sixth;

class Iris {
	private double sepalLength;
	private double sepalWidth;
	private double petalLength;
	private double petalWidth;
	private String className;

	Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String className) {
		this.sepalLength = sepalLength;
		this.sepalWidth = sepalWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
		this.className = className;
	}

	double getSepalLength() {
		return sepalLength;
	}

	double getSepalWidth() {
		return sepalWidth;
	}

	double getPetalLength() {
		return petalLength;
	}

	double getPetalWidth() {
		return petalWidth;
	}

	String getClassName() {
		return className;
	}
}

