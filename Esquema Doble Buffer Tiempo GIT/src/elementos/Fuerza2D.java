package elementos;

public class Fuerza2D extends Vector2D {

	public Fuerza2D(double x, double y) {
		super(x, y);
	}

	public static Fuerza2D convertirDe(Vector2D v) {
		return new Fuerza2D(v.x,v.y);
	}

}
