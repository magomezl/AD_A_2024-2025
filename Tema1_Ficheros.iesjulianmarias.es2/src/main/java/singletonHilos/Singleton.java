package singletonHilos;

public class Singleton {
	private static Singleton instance;
	private String color;

	private Singleton(String color) {
		super();
		this.color = color;
	}

	public static synchronized Singleton getInstance(String color) {
		if (instance == null) {
			instance = new Singleton(color);
		}
		return instance;
	}

	public String getColor() {
		return color;
	}
}

