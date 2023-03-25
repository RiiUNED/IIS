package calendario;

import java.util.*;
public class Calendario {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		Mes mes = Servicios.getMes(sc);
		int anno = Servicios.getAnno(sc);
		
		Servicios.imprimirMES(mes, anno);
		
		sc.close();
	}
}

