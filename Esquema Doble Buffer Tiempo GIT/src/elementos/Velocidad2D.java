package elementos;

public class Velocidad2D extends Vector2D{
	public Velocidad2D(double x, double y) {
		super(x,y);
	}
	public static Velocidad2D convertirDe(Vector2D v) {
		return new Velocidad2D(v.x,v.y);
	}
	public Velocidad2D clone() {
		Vector2D v2 = super.clone();
		return new Velocidad2D(v2.getX(),v2.getY());
	}
}
