package modelo;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Monitor extends Thread {
	
	/**
	 * Cola de estudiantes que esperan a ser atendidos (incluye el que está en la monitoría)
	 */
	private Queue<Estudiante> colaEstudiantes;
	
	/**
	 * Generador de números aleatorios
	 */
	private Random aleatorio;
	
	/**
	 * Semáforo que indica si un estudiante salió o no de la cola
	 */
	private Semaphore sCola;
	
	/**
	 * Semáforo empleado para hacer dormir al monitor
	 */
	private Semaphore sEstudiante;
	
	/**
	 * Semáforo empleado para empezar la monitoría
	 */
	private Semaphore sMonitor;

	/**
	 * Constructor de la clase. Inicializa todos los datos requeridos
	 * @param colaEstudiantes - Cola de estudiantes
	 * @param sCola - Semáforo de la cola
	 * @param semilla - Semilla para generar un número aleatorio
	 * @param sMonitor - Semáforo para empezar o terminar una monitoría
	 * @param sEstudiante - Semáforo para despertar o dormir al monitor
	 */
	public Monitor(Queue<Estudiante> colaEstudiantes, Semaphore sCola, long semilla, Semaphore sMonitor, Semaphore sEstudiante) {
		super();
		this.sCola = sCola;
		this.aleatorio = new Random(semilla);
		this.sMonitor = sMonitor;
		this.colaEstudiantes = colaEstudiantes;
		this.sEstudiante = sEstudiante;
	}
	
	
	/**
	 * Método principal del hilo
	 */
	public void run() {
		while (true) {
			try {
				if(colaEstudiantes.isEmpty()) {
					System.out.println("- [Monitor] Bueno, no hay nadie, así que ¡A dormir!");
					sEstudiante.release();
					sleep(Math.abs(aleatorio.nextInt()) % 1000);
				}else {
					System.out.println("- [Monitor] Que pase el "+colaEstudiantes.peek().getNombre()+". Empieza la monitoría");
					sleep(Math.abs(aleatorio.nextInt()) % 1000);
					sMonitor.acquire();
					System.out.println("- [Monitor] Terminé la monitoría");
					sCola.release();
					colaEstudiantes.poll();
				}
				
			}catch(InterruptedException e) {
				
			}
		}
	}
}
