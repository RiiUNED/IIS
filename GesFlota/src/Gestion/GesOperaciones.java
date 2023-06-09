package Gestion;

import java.util.*;

import Datos.*;
import Tipos.*;
import BBDD.*;
import Fecha.*;

public class GesOperaciones {

	/*Operacion completa
	 * Operar buques 
	 * 		getFecha
	 * 		getBuque
	 * Estado Buque
	 * 		Opciones carga
	 * 			|_Cargar (S/N) -> Carga
	 * 			|_Traslado
	 * 				Estado Buque
	 * 					|_Traslado (S/N) -> Traslado
	 * 					|_Descarga -> (S/N)
	 */
	public static void putOperacion(Fecha fecha, Scanner sc, BBDD bbdd, Buque buque) {
		//------------------------------ Inicializar ------------------------------------------------------------
		boolean continuar = false;
		OperacionPuerto cargar = null;
		Traslado traslado = null;
		OperacionPuerto descargar = null;
		HashMap<Buque, SortedSet<Operacion>> oPb = bbdd.getoPb();
		SortedSet<Operacion> ops = oPb.get(buque);
		//Operacion status = getStatus(bbdd, buque);
		//------------------------------ Cargar ------------------------------------------------------------
		cargar = getCargar(fecha, sc, bbdd, buque);
		//------------------------------ Trasladar ------------------------------------------------------------
		String pregunta = "el traslado";
		TipoCarga carga = null;
		if(cargar != null) {
			carga = cargar.getCarga();
		} else {
			carga = getCargaTransportada(ops);
		}
		continuar = askContinuar(sc, pregunta);
		if (continuar) {
			traslado = getTraslado(fecha, sc, bbdd, carga, buque);
		}
		//------------------------------ Descargar ------------------------------------------------------------
		pregunta = "la descarga";
		continuar = askContinuar(sc, pregunta);
		Puerto destino = null;
		if (continuar) {
			if (traslado == null) {
				destino = getDestino(ops);
			} else {
				destino = traslado.getDestino();
			}
			carga = TipoCarga.Vacio;
			descargar = getDescarga(sc, fecha, destino, carga);
		}
		//------------------------------ Confirmar ------------------------------------------------------------
		if( cargar != null || traslado != null || descargar != null) {
			Operacion operacion = new Operacion(cargar, traslado, descargar);
			operacion.show();
			System.out.println("Es correcta la operacion (S/N)?");
			char opcion = sc.next().charAt(0);
			if (opcion == 'S') {
				//------------------------------ Registrar ------------------------------------------------------------
				registrarOperacion(bbdd, buque, operacion);	
			}
		}
	}
	
	private static Puerto getDestino(SortedSet<Operacion> ops) {
		Puerto destino = null;
		Operacion ultima = ops.last();
		Traslado traslado = ultima.getTraslado();
		OperacionPuerto cargar = ultima.getCarga();
		if (traslado == null) {
			if (cargar == null) {
				SortedSet<Operacion> neuOps = ops.headSet(ultima);
				destino = getDestino(neuOps);
			} else {
				destino = cargar.getPuerto();
			}
		} else {
			destino = traslado.getDestino();
		}
		return destino;
	}
	
	private static TipoCarga getCargaTransportada(SortedSet<Operacion> ops) {
		TipoCarga carga = null;
		Operacion ultima = ops.last();
		OperacionPuerto descargar = ultima.getDescarga();
		if(descargar != null) {
			carga = TipoCarga.Vacio;
		} else {
			OperacionPuerto cargar = ultima.getCarga();
			if(cargar != null) {
				carga = cargar.getCarga();
			} else {
				SortedSet<Operacion> neuOps = ops.headSet(ultima);
				getCargaTransportada(neuOps);
			}
		}
		return carga;
	}
	
	private static Operacion getStatus(BBDD bbdd, Buque buque, SortedSet<Operacion> ops) {
		Operacion status = null;
		status = ops.last();
		return status;
	}

	private static OperacionPuerto getDescarga(Scanner sc, Fecha fecha, Puerto destino, TipoCarga carga) {
		OperacionPuerto descarga = null;
		TipoOperacion tipo = TipoOperacion.Descarga;
		String pregunta = "la descarga";
		int dias = askDuracion(sc, pregunta);
		descarga = new OperacionPuerto(fecha, destino, tipo, carga, dias);
		return descarga;
	}

	private static void registrarOperacion(BBDD bbdd, Buque buque, Operacion operacion) {
		HashMap<Buque, SortedSet<Operacion>> oPb = bbdd.getoPb();
		SortedSet<Operacion> operaciones = oPb.get(buque);
		operaciones.add(operacion);
		oPb.put(buque, operaciones);
	}

	private static Traslado getTraslado(Fecha fecha, Scanner sc, BBDD bbdd, TipoCarga carga, Buque buque) {
		Traslado traslado = null;
		String pregunta = "del traslado";
		LinkedList<Puerto> puertosP = getPuertos(bbdd, carga);
		Puerto destino = selectDestino(sc, puertosP, bbdd);
		int dias = askDuracion(sc, pregunta);
		Puerto origen = getOrigen(bbdd, buque);
		traslado = new Traslado(fecha, origen, dias, destino);
		return traslado;
	}

	private static Puerto getOrigen(BBDD bbdd, Buque buque) {
		Puerto origen = null;
		HashMap<Buque, SortedSet<Operacion>> oPb = bbdd.getoPb();
		SortedSet<Operacion> operaciones = oPb.get(buque);
		if (operaciones.isEmpty()) {
			origen = buque.getBotadura();
		} else {
			Operacion ultimaO = operaciones.last();
			if (ultimaO.getTraslado() == null) {
				if (ultimaO.getDescarga() == null) {
					origen = ultimaO.getCarga().getPuerto();
				} else {
					origen = ultimaO.getDescarga().getPuerto();
				}
			} else {
				origen = ultimaO.getTraslado().getDestino();
			}
		}
		return origen;
	}

	private static Puerto selectDestino(Scanner sc, LinkedList<Puerto> puertosP, BBDD bbdd) {
		Puerto puerto = null;
		System.out.println("Puertos posibles de destino del buque");
		for (Puerto p : puertosP) {
			p.show();
		}
		System.out.print("Identificador del puerto de destino: ");
		int ID = sc.nextInt();
		int index = ID - 1;
		puerto = bbdd.getPuertos().get(index);
		return puerto;
	}

	private static LinkedList<Puerto> getPuertos(BBDD bbdd, TipoCarga carga) {
		ArrayList<Puerto> puertos = bbdd.getPuertos();
		LinkedList<Puerto> puertosReceptores = new LinkedList<Puerto>();
		TipoPuerto tipoP, patron;
		switch (carga) {
		case Crudo:
			patron = TipoPuerto.Refineria;
			for (Puerto p : puertos) {
				tipoP = p.getTipo();
				if (tipoP == patron) {
					puertosReceptores.add(p);
				}
			}
			break;
		case Vacio:
			puertosReceptores = new LinkedList<Puerto>(puertos);
			break;
		case RefinadoFuel:
			patron = TipoPuerto.Deposito;
			for (Puerto p : puertos) {
				tipoP = p.getTipo();
				if (tipoP == patron) {
					puertosReceptores.add(p);
				}
			}
			break;
		case RefinadoGasoil:
			patron = TipoPuerto.Deposito;
			for (Puerto p : puertos) {
				tipoP = p.getTipo();
				if (tipoP == patron) {
					puertosReceptores.add(p);
				}
			}
			break;
		case RefinadoGasolina:
			patron = TipoPuerto.Deposito;
			for (Puerto p : puertos) {
				tipoP = p.getTipo();
				if (tipoP == patron) {
					puertosReceptores.add(p);
				}
			}
			break;
		default:
			break;
		}
		return puertosReceptores;
	}

	/*
	 * Status -> Se puede cargar
	 * 				|_ Si - > qu� se puede cargar -> Opciones
	 * 				|				|_ Si -> Get Carga
	 * 				|_______________|_ No -> carga - Null -> Traslado
	 */
	private static OperacionPuerto getCargar(Fecha fecha, Scanner sc, BBDD bbdd, Buque buque) {
		String pregunta = "de la carga";
		OperacionPuerto cargar = null;
		TipoOperacion tipo = TipoOperacion.Carga;
		String c = "la carga";
		Puerto puerto = getPuerto(bbdd, buque);
		HashMap<Buque, SortedSet<Operacion>> oPb = bbdd.getoPb();
		SortedSet<Operacion> operaciones = oPb.get(buque);
		Operacion status = getStatus(bbdd, buque, operaciones);
		boolean isEmpty = isEmpty(status, operaciones);
		if(isEmpty) {
			System.out.println("El buque: ");
			buque.show();
			System.out.println("Esta disponible en: ");
			puerto.show();
			LinkedList<TipoCarga> cargasPuerto = getCargasPuerto(puerto);
			TipoCarga carga = null;
			boolean continuar = false;
			if (cargasPuerto.size() == 1) {
				carga = cargasPuerto.getFirst();
				System.out.println("Se puede cargar: " + carga);
				continuar = askContinuar(sc, c);
				if (continuar) {
					int dias = askDuracion(sc, pregunta);
					cargar = new OperacionPuerto(fecha, puerto, tipo, carga, dias);
				}
			} else {
				System.out.println("Se puede cargar: ");
				LinkedList<TipoCarga> cargas = getCargasPuerto(puerto);
				for (TipoCarga cg : cargas) {
					System.out.println(" " + cg);
				}
				continuar = askContinuar(sc, c);
				if (continuar) {
					carga = getCarga(sc, cargas);
					int dias = askDuracion(sc, pregunta);
					cargar = new OperacionPuerto(fecha, puerto, tipo, carga, dias);
				}
			}
		} else {
			System.out.println("El buque seleccionado ya esta cargado");
		}
		return cargar;
	}
	
	private static boolean isEmpty(Operacion status, SortedSet<Operacion> operaciones) {
		boolean empty = false;
		OperacionPuerto descargar = status.getDescarga();
		if (descargar != null) {
			empty = true;
		} else {
			OperacionPuerto cargar = status.getCarga();
			if(cargar != null) {
				TipoCarga carga = cargar.getCarga();
				if(carga == TipoCarga.Vacio) {empty = true;}
			} else {
				if(!status.equals(operaciones.first())) {
					SortedSet<Operacion> nuevasOperaciones = operaciones.headSet(status);
					Operacion nuevoStatus = nuevasOperaciones.last();
					empty = isEmpty(nuevoStatus, nuevasOperaciones);
				}
			}
		}
		return empty;
	}

	private static TipoCarga getCarga(Scanner sc, LinkedList<TipoCarga> cargas) {
		TipoCarga carga = null;
		System.out.println("Producto a cargar");
		int contador = 1;
		for (TipoCarga cg : cargas) {
			System.out.println(contador + ". " + cg);
			++contador;
		}
		int opcion = sc.nextInt();
		switch (opcion) {
		case 1:
			carga = TipoCarga.RefinadoFuel;
			break;
		case 2:
			carga = TipoCarga.RefinadoGasoil;
			break;
		case 3:
			carga = TipoCarga.RefinadoGasolina;
			break;
		default:
			break;
		}
		return carga;
	}

	private static LinkedList<TipoCarga> getCargasPuerto(Puerto puerto) {
		LinkedList<TipoCarga> cargasPuerto = new LinkedList<TipoCarga>();
		TipoPuerto tipoPuerto = puerto.getTipo();
		switch (tipoPuerto) {
		case Yacimiento:
			cargasPuerto.add(TipoCarga.Crudo);
			break;
		case Refineria:
			cargasPuerto.add(TipoCarga.RefinadoFuel);
			cargasPuerto.add(TipoCarga.RefinadoGasoil);
			cargasPuerto.add(TipoCarga.RefinadoGasolina);
			break;
		case Deposito:
			cargasPuerto.add(TipoCarga.RefinadoFuel);
			cargasPuerto.add(TipoCarga.RefinadoGasoil);
			cargasPuerto.add(TipoCarga.RefinadoGasolina);
			break;
		default:
			break;
		}
		return cargasPuerto;
	}

	private static Puerto getPuerto(BBDD bbdd, Buque buque) {
		Puerto puerto = null;

		HashMap<Buque, SortedSet<Operacion>> oPb = bbdd.getoPb();
		SortedSet<Operacion> operaciones = oPb.get(buque);
		if (operaciones != null) {
			Operacion ultimaO = operaciones.last();
			OperacionPuerto carga = ultimaO.getCarga();
			Traslado traslado = ultimaO.getTraslado();
			OperacionPuerto descarga = ultimaO.getDescarga();
			if (carga == null) {
				if (traslado == null) {
					puerto = descarga.getPuerto();
				} else {
					puerto = traslado.getDestino();
				}
			} else {
				puerto = carga.getPuerto();
			}
		} else {
			puerto = buque.getBotadura();
		}

		return puerto;
	}

	private static int askDuracion(Scanner sc, String op) {
		int dias;
		System.out.println("Duracion " + op + " en dias?");
		dias = sc.nextInt();
		return dias;
	}

	private static boolean askContinuar(Scanner sc, String op) {
		boolean continuar = false;
		System.out.println("Quiere realizar " + op + " (S/N)?");
		char opcion = sc.next().charAt(0);
		if (opcion == 'S') {
			continuar = true;
		}
		return continuar;
	}
}