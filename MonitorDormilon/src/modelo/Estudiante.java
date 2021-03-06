package modelo;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Estudiante extends Thread {

	/**
	 * Nombre del estudiante
	 */
	private String nombre;
	
	/**
	 * Semáforo que indica si un estudiante entró o salió de la cola
	 */
	private Semaphore sCola;
	
	/**
	 * Semáfoto empleado para terminar una monitoría
	 */
	private Semaphore sMonitor;
	
	/**
	 * Semáforo empleado para despertar al monitor
	 */
	private Semaphore sEstudiante;
	
	/**
	 * Cola de estudiantes (incluye el que está en monitoría)
	 */
	private Queue<Estudiante> cola;
	
	/**
	 * Generador de números aleatorio
	 */
	private Random aleatorio;
	
	/**
	 * Constructor de la clase. Inicializa todos los datos requeridos
	 * @param nombre - Nombre del estudiante
	 * @param sCola - Semáforo de la cola
	 * @param sMonitor - Semáforo para empezar o terminar una monitoría
	 * @param sEstudiante - Semáforo para dormir o despertar al monitor
	 * @param cola - Cola de estudiantes
	 * @param semilla - Semilla para generar un número aleatorio
	 */
	public Estudiante(String nombre, Semaphore sCola, Semaphore sMonitor, Semaphore sEstudiante, Queue<Estudiante> cola, long semilla) {
		super();
		this.sCola = sCola;
		this.sEstudiante = sEstudiante;
		this.nombre = nombre;
		this.cola = cola;
		this.sMonitor = sMonitor;
		aleatorio = new Random(semilla);
	}
	
	/**
	 * Método principal del hilo
	 */
	public void run() {
		while (true) {
			try {
				System.out.println("- ["+nombre+"] ¡Necesito ayuda!");
				sCola.acquire();
				if(cola.size() == 4) {
					System.out.println("- ["+nombre+"] ¡Rayos! La sala de espera está llena, me iré a la sala de cómputo a programar :(");
					sleep(Math.abs(aleatorio.nextInt()) % 1000);	
					sCola.release();
				} else {
					if(cola.isEmpty()) {
						cola.add(this);
						System.out.println("- ["+nombre+"] Despierte monitor, necesito de su ayuda");
						sEstudiante.acquire();
						sMonitor.release();
						System.out.println("- ["+nombre+"] ¡Terminé!");
					}else {
						System.out.println("- ["+nombre+"] El monitor está ocupado y hay sillas disponibles, me sentaré a esperar");
						cola.add(this);
						sleep(Math.abs(aleatorio.nextInt()) % 1000);
				}
			}
				
			} catch (InterruptedException e) {
			}
		}
	}
	
	/**
	 * Devuelve el nombre del estudiante
	 * @return El nombre del estudiante
	 */
	public String getNombre() {
		return nombre;
	}
	
}
