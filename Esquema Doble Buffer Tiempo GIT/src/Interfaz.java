import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import elementos.Elemento2D;
import elementos.Velocidad2D;
import nucleo.AvanceMundo;
import nucleo.Realidad;
import nucleo.RealidadCircular;
import nucleo.RefrescoVisor;

public class Interfaz {

	private JFrame frame;

	private JPanel panelVisor;
	private Canvas canvas;

	private Realidad realidad;
	private RefrescoVisor refresco;
	private AvanceMundo avanceMundo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// VISTA
		Interfaz window = new Interfaz();
		
		// MODELO
		//window.realidad = new RealidadCircular(window.canvas, window.panelVisor);
		window.realidad = new Realidad(window.canvas);
		// subelementos
		Elemento2D elemento1 = new Elemento2D(530, 250, 75, Color.YELLOW);
		//elemento1.setMasa(1.989e30);
		elemento1.setMasa(190);
		//elemento1.aplicarFuerza(new Fuerza2D(2.8,0),1);
		window.realidad.añadirElemento(elemento1);
		window.realidad.setExcepción(elemento1);
		
		Elemento2D elemento2 = new Elemento2D(690, 250, 10, Color.blue);
		//elemento2.setMasa(5.972e24);
		elemento2.setMasa(40.5);
		//elemento2.aplicarFuerza(new Fuerza2D(0,1.2),1);
		// Cálculo velocidad
		//final double G = 1;
		//double v = Math.sqrt(G*elemento1.getMasa()/elemento1.distancia(elemento2));
		elemento2.setVelocidad(new Velocidad2D(0, v)); // 0,75 ó v
		window.realidad.añadirElemento(elemento2);

		/*Elemento2D elemento3 = new Elemento2D(500, 200, 75, Color.RED);
		//elemento2.setMasa(5.972e24);
		elemento3.setMasa(1000);
		elemento3.aplicarFuerza(new Fuerza2D(0,350),1);
		window.realidad.añadirElemento(elemento3);
*/
		
		Elemento2D elemento4 = new Elemento2D(690, 155, 10, Color.orange);
		//elemento2.setMasa(5.972e24);
		elemento4.setMasa(40.5);
		//elemento2.aplicarFuerza(new Fuerza2D(0,1.2),1);
		//elemento4.setVelocidad(new Velocidad2D(-0, -0.1));
		window.realidad.añadirElemento(elemento4);
		
		for(int i=0; i<10;i++) {
			Elemento2D elAux = new Elemento2D(600+1, 100+i, 10);
			elAux.setMasa(4.05);
			//elAux.aplicarFuerza(new Fuerza2D(0,1.2),1);
			elAux.setVelocidad(new Velocidad2D(-0, -0.1));
			window.realidad.añadirElemento(elAux);
				
		}
		
		// VISTA
		window.frame.setVisible(true);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Activar vista
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Visión estática
					window.realidad.visualizar(window.canvas.getGraphics());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Acción de background //CONTROL
		window.refresco = new RefrescoVisor(window.canvas, window.realidad, 1);
		window.refresco.start();
		window.avanceMundo = new AvanceMundo(window.realidad, 1, 5);
		window.avanceMundo.start();
	}

	/**
	 * Create the application.
	 */
	public Interfaz() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		panelVisor = new JPanel();
		panelVisor.setBackground(Color.WHITE);
		panelVisor.setBounds(10, 11, 800, 500);
		panel.add(panelVisor);
		panelVisor.setLayout(new BorderLayout(0, 0));
		
		JButton btnParar = new JButton("Parar refresco");
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresco.parar();
			}
		});
		btnParar.setBounds(10, 522, 139, 23);
		panel.add(btnParar);
		
		JButton btnArrancar = new JButton("Continuar refresco");
		btnArrancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresco.arrancar();
			}
		});
		btnArrancar.setBounds(159, 522, 146, 23);
		panel.add(btnArrancar);

		canvas = new Canvas();
		canvas.setBackground(Color.GRAY);
		panelVisor.add(canvas);
		
		JButton btnPararMundo = new JButton("Parar Mundo");
		btnPararMundo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				avanceMundo.parar();
				refresco.parar();
			}
		});
		btnPararMundo.setBounds(10, 549, 139, 26);
		panel.add(btnPararMundo);
		
		JButton btnNewButton = new JButton("Continuar Mundo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresco.arrancar();
				avanceMundo.arrancar();
			}
		});
		btnNewButton.setBounds(159, 549, 146, 26);
		panel.add(btnNewButton);
	}
}
