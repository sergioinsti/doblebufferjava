package nucleo;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import elementos.Elemento2D;
import elementos.Fuerza2D;

public class Realidad {
    protected ArrayList<Elemento2D> elementos = new ArrayList<Elemento2D>();
	protected Canvas visor;
	protected Elemento2D excepci�n;
	protected BufferStrategy dobleBuff;

	public Realidad (Canvas visor) {
	    this.visor = visor;
	}

	public synchronized void a�adirElemento(Elemento2D el) {
		elementos.add(el);
		el.setRealidad(this);
	}

	public synchronized void setExcepci�n(Elemento2D el) {
		this.excepci�n = el;
	}

	public synchronized void visualizar(Graphics g) {
    	g.setColor(visor.getBackground());
		g.fillRect(0, 0, visor.getWidth(), visor.getHeight());
		for(Elemento2D elemento : elementos) {
			// siempre se dibuja el elemento en su coordenada
			elemento.paint(g);
		}
	}

	public synchronized void paso(double tiempo) {
		// Copia de los elementos, previa modificaci�n, para mantener las mismas condiciones para todos
		ArrayList<Elemento2D> elementosPaso = new ArrayList<Elemento2D>();
		for(Elemento2D elemento : elementos) {
			if(elemento!=excepci�n) {
				elementosPaso.add(elemento.elementoPaso(tiempo));
			} else {
				elementosPaso.add(excepci�n);
			}
		}
		// Sustituci�n del anterior conjunto de elementos por los nuevos
		elementos = elementosPaso;
	}

	public Fuerza2D fuerzasSobrePart�cula(Elemento2D yo) {
		// Gravedad
		//final double G = 6.6720e-11;
		final double G = 1;
		Fuerza2D suma = new Fuerza2D (0,0);
		for(Elemento2D elemento : elementos) {			
			if(elemento!=yo&&yo!=excepci�n) {
				// F = -G.Mm/d2
				double distancia = yo.distancia(elemento);
				double moduloFuerza = -G*elemento.getMasa()*yo.getMasa()/(distancia*distancia);
				Fuerza2D fuerzaUno = Fuerza2D.convertirDe(yo.vectorUnidad(elemento).producto(moduloFuerza));
				suma = Fuerza2D.convertirDe(suma.sumar(fuerzaUno));
			}
		}
		//suma.mostrar();
		return suma;
	}
}

