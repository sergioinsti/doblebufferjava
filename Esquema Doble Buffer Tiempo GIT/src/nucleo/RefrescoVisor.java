package nucleo;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class RefrescoVisor extends Thread {
	/**
	 * Objeto Canvas (lienzo) que se refrescará cada cierto tiempo
	 */
	private Canvas lienzo;
	/**
	 * Contenido con el que se refrescará el lienzo de dibujo
	 */
	private Realidad realidad;
	/**
	 * Tiempo de refresco (en milisegundos)
	 */
	private int tiempoMilis;
	/**
	 * Bandera para poder parar/activar el refresco
	 */
	private boolean refrescando = true;

	public RefrescoVisor(Canvas lienzo, Realidad realidad, int tiempoMilis) {
		super();
		this.lienzo = lienzo;
		this.realidad = realidad;
		this.tiempoMilis = tiempoMilis;
		//setDaemon(true); // si es el único hilo que se ejecuta el programa se cerrará
	    // Crear la estrategia de doble buffer sobre el lienzo de dibujo
		lienzo.createBufferStrategy(2);
	}

	public void run() {
		BufferStrategy estrategia = lienzo.getBufferStrategy();
		while (true) { // bucle infinito, que se puede parar y continuar en un momento concreto
			while (!refrescando) {
				try {
					pararme(); // detiene el bucle
				} catch (InterruptedException e) {
					// Si alguien lo interrumpe, no hace nada, deja que vuelva a wait
					e.printStackTrace();
				}
			}
			// Dibujar un único cuadro (diapositiva) {de la doc del API}
			do {
				// El siguiente bucle asegura que el contenido del buffer de dibujo
				// es consistente/completo aún en el caso de que la superficie sobre la
				// que se dibuja sea recreada {de la doc del API} 
				do {
					// Obtiene un nuevo contexto gráfico (graphics) en cada repetición
					// para asegurar que se valida la "estrategia" {de la doc del API}
					Graphics dobleBuffer = estrategia.getDrawGraphics();

					// PINTA EL CUADRO {de la doc del API}
					realidad.visualizar(dobleBuffer);

					// Se deshace del graphics {de la doc del API}
					dobleBuffer.dispose();

					// Se repite el DIBUJO si los contenidos del buffer de dibujo
					// fuera restaurado {de la doc del API}
				} while (estrategia.contentsRestored());

				// Muestra el buffer "de golpe" {de la doc del API}
				estrategia.show();

				// Repite el pintado/renderizado si el buffer de dibujo se perdiera {de la doc del API}
			} while (estrategia.contentsLost());
			// Se acaba de pintar un cuadro con doble bufer (de una sola vez)
			try {
				sleep(tiempoMilis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//realidad.paso(tiempoMilis);
		}

	}

	public synchronized void parar() {
		refrescando = false;
	}

	public synchronized void arrancar() {
		refrescando = true;
		notifyAll();
	}
	
	private synchronized void pararme() throws InterruptedException {
		wait();
	}

}
