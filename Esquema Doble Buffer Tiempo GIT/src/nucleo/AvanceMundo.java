package nucleo;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class AvanceMundo extends Thread {
	/**
	 * Contenido con el que se refrescará el lienzo de dibujo
	 */
	private Realidad realidad;
	/**
	 * Tiempo de avance del mundo (en segundos)
	 */
	private double tiempoAvance;
		/**
	 * Tiempo de refresco (en milisegundos)
	 */
	private int tiempoMilisRefresco;
	/**
	 * Bandera para poder parar/activar el refresco
	 */
	private boolean refrescando = true;

	public AvanceMundo(Realidad realidad, double tiempoAvance, int tiempoMilisRefresco) {
		// Se leería... hacer avanzar "tiempoAvance" el mundo, cada "tiempoMilisRefresco"
		super();
		this.realidad = realidad;
		this.tiempoAvance = tiempoAvance;
		this.tiempoMilisRefresco = tiempoMilisRefresco;
		//setDaemon(true); // si es el único hilo que se ejecuta el programa se cerrará
	}

	public void run() {
		while (true) { // bucle infinito, que se puede parar y continuar en un momento concreto
			while (!refrescando) {
				try {
					pararme(); // detiene el bucle
				} catch (InterruptedException e) {
					// Si alguien lo interrumpe, no hace nada, deja que vuelva a wait
					e.printStackTrace();
				}
			}
			try {
				sleep(tiempoMilisRefresco);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			realidad.paso(tiempoAvance);
		}

	}

	public synchronized void parar() {
		refrescando = false;
	}

	public synchronized void arrancar() {
		refrescando = true;
		notify();
	}
	
	private synchronized void pararme() throws InterruptedException {
		wait();
	}

}
