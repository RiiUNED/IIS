package Fecha;

import java.util.*;

public class Calendario {

	public static void printResumen(LinkedList<String> dias, Fecha fecha) {
		Mes mes = fecha.getMes();
		int anno = fecha.getAnno();
		Servicios.imprimirMES(mes, anno, dias);
	}

}
