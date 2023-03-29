package Gestion;

import java.util.*;

import BBDD.*;
import Datos.*;
import Fecha.*;
//import Tipos.*;

public class GesBuques {

	public static void MenuEditarBuque(Scanner sc, BBDD bbdd) {
		ArrayList<Puerto> puertos = bbdd.getPuertos();
		System.out.println("Editar Buque:");
		System.out.print("Identificador (letra entre A y E)? ");
		char identificador = sc.next().charAt(0);
		sc.nextLine(); // Consumir el salto de l�nea

		System.out.print("Nombre (entre 1 y 20 caracteres)? ");
		String nombre = sc.nextLine();

		System.out.println("Fecha inicio:");
		System.out.print("D�a? ");
		int diaInicio = sc.nextInt();

		System.out.print("Mes? ");
		int mesInicio = sc.nextInt();

		System.out.print("A�o? ");
		int anioInicio = sc.nextInt();
		Fecha botadura = GeneraBotadura(diaInicio, mesInicio, anioInicio);
		System.out.println("Puertos posibles para la ubicaci�n inicial del buque:");
		for (Puerto p : puertos) {
			p.show();
		}
		System.out.print("Identificador de puerto inicio? ");
		int puertoInicio = sc.nextInt();
		int index = puertoInicio - 1;
		Puerto puerto = puertos.get(index);
		sc.nextLine(); // Consumir el salto de l�nea

		System.out.print(
				"IMPORTANTE: Esta opci�n borra los datos anteriores. " + "Son correctos los nuevos datos (S/N)? ");
		char confirmacion = sc.next().charAt(0);

		if (confirmacion == 'S' || confirmacion == 's') {
			EditarBuque(bbdd, nombre, botadura, puerto, identificador);
			System.out.println("Los datos del buque han sido modificados correctamente.");
		} else {
			System.out.println("No se han realizado cambios en los datos del buque.");
		}
	}

	public static void MenuOperarBuque(BBDD bbdd, Scanner sc) {
		System.out.println("Operar Buque:");
		Fecha fecha = getFecha(sc);
		Buque buque = getBuque(bbdd, sc);
		GesOperaciones.putOperacion(fecha, sc, bbdd, buque);
		System.out.println("OPERAR BUQUE ha terminado");
	}

	private static Fecha getFecha(Scanner input) {
		// Obtener fecha de inicio de la operaci�n
		System.out.print("Fecha comienzo operaci�n: D�a?");
		int day = input.nextInt();
		System.out.print("Fecha comienzo operaci�n: Mes?");
		int month = input.nextInt();
		int ordinal = month - 1;
		Mes[] mes = Mes.values();
		Mes m = mes[ordinal];
		System.out.print("Fecha comienzo operaci�n: A�o?");
		int year = input.nextInt();

		Fecha f = new Fecha(day, m, year);
		return f;
	}

	private static Buque getBuque(BBDD bbdd, Scanner input) {
		Buque buque = null;
		System.out.print("Identificador del Buque (letra entre A y E)? ");
		String ID = input.next();
		for (Buque b : bbdd.getBuques()) {
			if (b.getID().equals(ID)) {
				buque = b;
			}
		}
		return buque;
	}

	public static void ResumenMensual(Scanner sc, BBDD bbdd) {
		do {
			Buque buque = getBuque(bbdd, sc);
			Fecha fecha = getFecha(sc);
			int dias = Fecha.getDiasMes(fecha.getMes(), fecha.getAnno());
			LinkedList<Lapso> lapsos = getLapsos(bbdd, buque, fecha);
			if (lapsos != null) {
				LinkedList<String> diasImprimir = getNumImprimir(lapsos, dias, fecha);
				Calendario.printResumen(diasImprimir, fecha);
			}
		} while (askContinuar(sc));
		System.out.println("Saliendo RESUMEN MENSUAL BUQUE");
	}

	private static LinkedList<String> getNumImprimir(LinkedList<Lapso> lapsos, int dias, Fecha fecha) {
		LinkedList<Fecha> diasMes = null;
		LinkedList<String> numImprimir = null;
		Integer numI = null;
		String num = null;
		String blanco = " ";
		for (int i = 1; i <= dias; ++i) {
			diasMes.add(new Fecha(i, fecha.getMes(), fecha.getAnno()));
		}
		for (Fecha f : diasMes) {
			for (Lapso l : lapsos) {
				if (l.pertece(f)) {
					numI = f.getDia();
					num = numI.toString();
					if (numI < 10) {
						num = num + blanco;
					}
					numImprimir.add(num);
				}
			}
		}

		return numImprimir;
	}

	private static LinkedList<Lapso> getLapsos(BBDD bbdd, Buque buque, Fecha fecha) {
		LinkedList<Lapso> lapsos = null;
		HashMap<Buque, SortedSet<Operacion>> oPb = bbdd.getoPb();
		SortedSet<Operacion> ops = oPb.get(buque);

		int dias = Fecha.getDiasMes(fecha.getMes(), fecha.getAnno());
		Lapso lapso = new Lapso(fecha, dias);

		for (Operacion o : ops) {
			OperacionPuerto cargar = o.getCarga();
			Traslado traslado = o.getTraslado();
			OperacionPuerto descarga = o.getDescarga();

			Fecha fechas = null;
			int dur;
			if (cargar != null && lapso.pertece(cargar.getFecha())) {
				fecha = cargar.getFecha();
				dur = cargar.getDuracion();
				lapsos.add(new Lapso(fecha, dur));
			}
			if (traslado != null && lapso.pertece(traslado.getInicio())) {
				fecha = traslado.getInicio();
				dur = traslado.getDuracion();
				lapsos.add(new Lapso(fecha, dur));
			}
			if (descarga != null && lapso.pertece(descarga.getFecha())) {
				fecha = descarga.getFecha();
				dur = descarga.getDuracion();
				lapsos.add(new Lapso(fecha, dur));
			}
		}

		return lapsos;
	}

	private static boolean askContinuar(Scanner sc) {
		boolean continuar = false;
		System.out.println("Quiere realizar continuar (S/N)?");
		char opcion = sc.next().charAt(0);
		if (opcion == 'S') {
			continuar = true;
		}
		return continuar;
	}

	/*
	 * private static boolean disponibleBuque(BBDD bbdd, Buque buque, Fecha fecha) {
	 * boolean disponible = false;
	 * 
	 * HashMap<Buque, SortedSet<Operacion>> oPb = bbdd.getoPb();
	 * SortedSet<Operacion> operaciones = oPb.get(buque); if (operaciones != null) {
	 * Operacion ultimaO = operaciones.last(); if (ultimaO.getCarga() == null) { if
	 * (ultimaO.getTraslado() == null) { disponible = true; } else { Traslado
	 * ultimoT = ultimaO.getTraslado(); Fecha inicioT = ultimoT.getInicio(); int
	 * dias = ultimoT.getDuracion(); Fecha finalT = inicioT.sumFecha(dias); if
	 * (finalT.compareTo(fecha) < 0) { disponible = true; } } } else {
	 * OperacionPuerto opP = ultimaO.getDescarga(); TipoOperacion tipo =
	 * opP.getOperacion(); if (tipo == TipoOperacion.Descarga) { Fecha inicio =
	 * opP.getFecha(); int duracion = opP.getDuracion(); Fecha finalD =
	 * inicio.sumFecha(duracion); if (finalD.compareTo(fecha) < 0) { disponible =
	 * true; } } } } else { disponible = true; }
	 * 
	 * return disponible; }
	 */

	public static void EstadoBuques(BBDD bbdd) {
		HashMap<Buque, SortedSet<Operacion>> oPb = bbdd.getoPb();
		int contador;
		for (Buque b : bbdd.getBuques()) {
			SortedSet<Operacion> operaciones = oPb.get(b);
			b.show();
			contador = 0;
			for (Operacion o : operaciones) {
				if (contador != 0) {
					o.show();
				}
				++contador;
			}
		}
	}

	private static void EditarBuque(BBDD bbdd, String nombre, Fecha botadura, Puerto puerto, char identificador) {
		int index = getIndex(identificador);
		ArrayList<Buque> buques = bbdd.getBuques();
		buques.get(index);
		String ID = String.valueOf(identificador);
		Buque nuevo = new Buque(ID, nombre, botadura, puerto);
		buques.set(index, nuevo);
	}

	private static int getIndex(char id) {
		int index;
		switch (id) {
		case 'A':
			index = 0;
			break;
		case 'B':
			index = 1;
			break;
		case 'C':
			index = 2;
			break;
		case 'D':
			index = 3;
			break;
		case 'E':
			index = 4;
			break;
		default:
			index = 5;
			break;
		}
		return index;
	}

	private static Fecha GeneraBotadura(int dia, int mes, int anno) {
		Mes fmes = null;
		switch (mes) {
		case 1:
			fmes = Mes.Enero;
			break;
		case 2:
			fmes = Mes.Febrero;
			break;
		case 3:
			fmes = Mes.Marzo;
			break;
		case 4:
			fmes = Mes.Abril;
			break;
		case 5:
			fmes = Mes.Mayo;
			break;
		case 6:
			fmes = Mes.Junio;
			break;
		case 7:
			fmes = Mes.Julio;
			break;
		case 8:
			fmes = Mes.Agosto;
			break;
		case 9:
			fmes = Mes.Septiembre;
			break;
		case 10:
			fmes = Mes.Octubre;
			break;
		case 11:
			fmes = Mes.Noviembre;
			break;
		case 12:
			fmes = Mes.Diciembre;
			break;
		default:
			break;
		}
		Fecha fecha = new Fecha(dia, fmes, anno);
		return fecha;
	}

}
