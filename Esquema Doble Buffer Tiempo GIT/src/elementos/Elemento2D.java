package elementos;

import java.awt.Color;
import java.awt.Graphics;

import nucleo.Realidad;

public class Elemento2D extends Vector2D{
	private static int ANCHO_DEFECTO = 10;
	private int ancho;
	private Velocidad2D velocidad;
	
	private Color color;
	private Realidad realidad;
	private double masa;

	public Elemento2D(double x, double y) {
		super(x,y);
		ancho = ANCHO_DEFECTO;
		velocidad = new Velocidad2D(0,0);
		color = new Color((int)(Math.random()*Integer.MAX_VALUE));
	}

	public Elemento2D(double x, double y, int ancho) {
		this(x,y);
		this.ancho = ancho;
	}
	
	public Elemento2D(double x, double y, int ancho, Color color) {
		this(x,y,ancho);
		this.color = color;
	}

	public Elemento2D clone() {
		Elemento2D el = new Elemento2D(x, y, ancho);
		el.velocidad = velocidad.clone();
		el.color = color;
		el.realidad = realidad;
		el.masa = masa;
		return el;
	}
	
	public void setRealidad(Realidad r) {
		this.realidad = r;
	}
	
	public int getAncho() {
		return ancho;
	}

	public double getMasa() {
		return masa;
	}

	public void setMasa(double masa) {
		this.masa = masa;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval((int)x-(ancho/2), (int)y-(ancho/2), ancho, ancho);
	}
	
	public Elemento2D elementoPaso(double tiempo) {
		Elemento2D nuevo = clone();
		nuevo.aplicarFuerza(realidad.fuerzasSobrePartícula(this), tiempo);
		return nuevo;
	}

	public void aplicarFuerza (Fuerza2D fuerza, double tiempo) {
		Vector2D aceleracion = fuerza.producto(1/masa);
		x = x+velocidad.getX()*(tiempo)+0.5*aceleracion.getX()*(tiempo)*(tiempo);
		y = y+velocidad.getY()*(tiempo)+0.5*aceleracion.getX()*(tiempo)*(tiempo);
	    velocidad.setX(velocidad.getX()+(aceleracion.getX()*tiempo));
	    velocidad.setY(velocidad.getY()+(aceleracion.getY()*tiempo));
	}

	public void setVelocidad(Velocidad2D velocidad2d) {
		velocidad = velocidad2d;
	}
}

