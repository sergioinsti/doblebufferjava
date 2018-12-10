package elementos;

public class Vector2D {
	protected double x;
	protected double y;
	
	public Vector2D(double x, double y) {
		this.x=x;
		this.y=y;
	}
	
	public Vector2D clone() {
		return new Vector2D(x, y);
	}

	public int getXPixel() {
		return (int)x;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}

	public int getYPixel() {
		return (int) y;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public double modulo() {
		double mod = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
		return mod;
	}
	
	public Vector2D sumar(Vector2D otro) {
		Vector2D suma = new Vector2D(this.x+otro.x, this.y+otro.y);
		return suma;
	}
	
	public double distancia(Vector2D otro) {
		double d = Math.sqrt(Math.pow(x-otro.x,2)+Math.pow(y-otro.y,2));
		return d;
	}
	
	public Vector2D producto(double escalar) {
		Vector2D result = this.clone();
		result.x=result.x*escalar;
		result.y=result.y*escalar;
		return result;
	}

    public Vector2D vectorUnidad(Elemento2D otro) {
		Vector2D vectorU = new Vector2D(x-otro.x, y-otro.y);
		return vectorU.producto(1/vectorU.distancia(otro));
	}
    
    public void mostrar() {
    	System.out.println(x+","+y);
    }
}
