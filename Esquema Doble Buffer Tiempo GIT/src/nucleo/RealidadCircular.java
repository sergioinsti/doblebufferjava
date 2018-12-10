package nucleo;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import elementos.Elemento2D;

public class RealidadCircular extends Realidad{
	private JPanel visor;
	public RealidadCircular(Canvas lienzo, JPanel visor) {
		super(lienzo);
		this.visor = visor;
	}

	public void visualizar() {
		Graphics g = visor.getGraphics();
		g.setColor(visor.getBackground());
		g.fillRect(0, 0, visor.getWidth(), visor.getHeight());
		for(Elemento2D elemento : elementos) {
			// siempre se dibuja el elemento en su coordenada
			elemento.paint(g);
			// MUNDO CIRCULAR
			// Duplicación horizontal simple
			// Si se pasa por la derecha dibujar en la izquierda
			int excesoDerecha = elemento.getXPixel()+elemento.getAncho()/2-visor.getWidth();
			if (excesoDerecha>0) {
				Elemento2D elAux = elemento.clone();
				elAux.setX(elemento.getXPixel()-visor.getWidth());
				elAux.paint(g);
			}
			// Puede salirse por los dos lados (feo, pero posible)
			// Si se pasa por la izquierda dibujar en la derecha
			int excesoIzquierda = elemento.getXPixel()-elemento.getAncho()/2;
			if (excesoIzquierda<0) {
				Elemento2D elAux = elemento.clone();
				elAux.setX(elemento.getXPixel()+visor.getWidth());
				elAux.paint(g);
			}
			// Corrección vertical
			int excesoAbajo = elemento.getYPixel()+elemento.getAncho()/2-visor.getHeight();
			if (excesoAbajo>0) {
				// Si se pasa por abajo dibujar por arriba
				Elemento2D elAux = elemento.clone();
				elAux.setY(elemento.getYPixel()-visor.getHeight());
				elAux.paint(g);
			}
			int excesoArriba = elemento.getYPixel()-elemento.getAncho()/2;
			if (excesoArriba<0) {
				// Si se pasa por arriba dibujar por abajo
				Elemento2D elAux = elemento.clone();
				elAux.setY(elemento.getYPixel()+visor.getHeight());
				elAux.paint(g);
			}
			// Doble corrección (horizontal y vertical simultánea, para las esquinas)
			// Se parte de que se ha hecho lo anterior
			// ESQUINA 1: Se sale por la esquina de arriba a la izquierda
			if(excesoIzquierda<0&&excesoArriba<0) {
				Elemento2D elAux = elemento.clone();
				elAux.setX(elemento.getXPixel()+visor.getWidth());
				elAux.setY(elemento.getYPixel()+visor.getHeight());
				elAux.paint(g);
			}
			// sin else, porque si el elemento es grande puede darse varios casos a la vez
			// ESQUINA 2: Se sale por la esquina de arriba a la derecha
			if(excesoDerecha>0&&excesoArriba<0) {
				Elemento2D elAux = elemento.clone();
				elAux.setX(elemento.getXPixel()-visor.getWidth());
				elAux.setY(elemento.getYPixel()+visor.getHeight());
				elAux.paint(g);
			}
			// ESQUINA 3: Se sale por la esquina de abajo a la izquierda
			if(excesoDerecha>0&&excesoArriba<0) {
				Elemento2D elAux = elemento.clone();
				elAux.setX(elemento.getXPixel()+visor.getWidth());
				elAux.setY(elemento.getYPixel()-visor.getHeight());
				elAux.paint(g);
			}
			// ESQUINA 4: Se sale por la esquina de abajo a la derecha
			if(excesoDerecha>0&&excesoArriba<0) {
				Elemento2D elAux = elemento.clone();
				elAux.setX(elemento.getXPixel()-visor.getWidth());
				elAux.setY(elemento.getYPixel()-visor.getHeight());
				elAux.paint(g);
			}
		}
		
	}

	public void paso(double tiempo) {
		ArrayList<Elemento2D> elementosPaso = new ArrayList<Elemento2D>();

		for(Elemento2D elemento : elementos) {
			Elemento2D nuevo = elemento.elementoPaso(tiempo);
			// MUNDO CIRCULAR
			// Corrección horizontal
			if (nuevo.getXPixel()>=visor.getWidth()) {
				// Si se pasa por la derecha empieza por la izquierda
				nuevo.setX(nuevo.getXPixel()-visor.getWidth());
			} else if (nuevo.getXPixel()<0) {
				// Si se pasa por la izquierda empieza por la derecha
				nuevo.setX(nuevo.getXPixel()+visor.getWidth());
			}
			// Corrección vertical
			if (nuevo.getYPixel()>=visor.getHeight()) {
				// Si se pasa por abajo empieza por arriba
				nuevo.setY(nuevo.getYPixel()-visor.getHeight());
			} else if (nuevo.getYPixel()<0) {
				// Si se pasa por arriba empieza por abajo
				nuevo.setY(nuevo.getYPixel()+visor.getHeight());
			}
		}
		elementos = elementosPaso;
	}

}
