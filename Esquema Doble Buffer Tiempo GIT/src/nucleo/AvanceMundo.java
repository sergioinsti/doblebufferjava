package nucleo;

import java.util.concurrent.TimeUnit;

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
	 * Tiempo de refresco (en nanosegundos extra a los milisegundos, hasta 999.999)
	 */
	private long tiempoNanosRefresco;
	/**
	 * Bandera para poder parar/activar el refresco
	 */
	private boolean refrescando = true;

	public AvanceMundo(Realidad realidad, double tiempoAvance, double tiempoMilisRefresco) {
		// Se leería... hacer avanzar "tiempoAvance" el mundo, cada "tiempoMilisRefresco"
		super();
		this.realidad = realidad;
		this.tiempoAvance = tiempoAvance;
		/*if(tiempoMilisRefresco%1==0) { // COMPROBAR, no sé si va bien
			this.tiempoMilisRefresco = (int) tiempoMilisRefresco;
			this.tiempoNanosRefresco = 0;
		} else {
			this.tiempoMilisRefresco = (int) tiempoMilisRefresco;
			this.tiempoNanosRefresco = (int) ((tiempoMilisRefresco%1)*1000000);			
		}*/
		this.tiempoNanosRefresco = (long) (tiempoMilisRefresco*1000000);
		setDaemon(true); // si es el único hilo que se ejecuta el programa se cerrará
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
			//try {
				//sleep(tiempoMilisRefresco, tiempoNanosRefresco); // nanosegundos, entero hasta 999999
				//sleep(0, 0);
				//TimeUnit.NANOSECONDS.sleep(1);
				long tiempoInic = System.nanoTime(); 
				while(System.nanoTime()-tiempoInic<=tiempoNanosRefresco) {
					;
				}
			//} catch (InterruptedException e) {
				//e.printStackTrace();
			//}
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
