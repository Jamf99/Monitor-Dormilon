package modelo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import modelo.Estudiante;
import modelo.Monitor;

public class Main {
	
	private static Queue<Estudiante> colaEstudiantes;
	private long semilla = Long.parseLong("1010");
	private static Semaphore sEstudiante; // Se usa para despertar al monitor
	private static Semaphore sMonitor; // Se usa para indicar que termino la monitoria
	private static Estudiante e1, e2, e3, e4, e5, e6;
	private static Monitor monitor;
	private static Semaphore sCola;
	
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
		Main main = new Main();		
	}

}
