package modelo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import modelo.Estudiante;
import modelo.Monitor;

public class Main {
	
	/**
	 * Cola de estudiantes que esperan a ser atendidos (incluye el que está siendo atendido)
	 */
	private static Queue<Estudiante> colaEstudiantes;
	
	/**
	 * Semilla para generar un número aleatorio
	 */
	private long semilla = Long.parseLong("1010");
	
	/**
	 * Semáforo empleado para despertar o dormir al monitor
	 */
	private static Semaphore sEstudiante; 
	
	/**
	 * Semáforo para empezar o terminar una monitoría
	 */
	private static Semaphore sMonitor; 

	/**
	 * Prueba con seis estudiantes
	 */
	private static Estudiante e1, e2, e3, e4, e5, e6;
	
	/**
	 * Monitor encargado de atender a los estudiantes
	 */
	private static Monitor monitor;
	
	/**
	 * Semáforo empleado para indicar si un estudiante salió o entró a la cola
	 */
	private static Semaphore sCola;
	
	/**
	 * Constructor de la clase principal
	 */
	public Main() {
		sCola = new Semaphore(1,true);
		sEstudiante = new Semaphore(1,true);
		sMonitor = new Semaphore(1, true);
		colaEstudiantes = new LinkedList<Estudiante>();
		
		monitor = new Monitor(colaEstudiantes, sCola, semilla, sMonitor, sEstudiante);
		e1 = new Estudiante("Estudiante 1", sCola, sMonitor, sEstudiante, colaEstudiantes, semilla);
		e2 = new Estudiante("Estudiante 2", sCola, sMonitor, sEstudiante, colaEstudiantes, semilla);
		e3 = new Estudiante("Estudiante 3", sCola, sMonitor, sEstudiante, colaEstudiantes, semilla);
		e4 = new Estudiante("Estudiante 4", sCola, sMonitor, sEstudiante, colaEstudiantes, semilla);
		e5 = new Estudiante("Estudiante 5", sCola, sMonitor, sEstudiante, colaEstudiantes, semilla);
		e6 = new Estudiante("Estudiante 6", sCola, sMonitor, sEstudiante, colaEstudiantes, semilla);
		
		sMonitor.drainPermits();
		sEstudiante.drainPermits();

		monitor.start();
		e1.start();
		e2.start();
		e3.start();
		e4.start();
		e5.start();
		e6.start();
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Main main = new Main();		
	}

}
