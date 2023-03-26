package Gestion;

import java.util.*;

import BBDD.*;
import Datos.*;
import Fecha.*;

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
        for(Puerto p : puertos) {p.show();}
        System.out.print("Identificador de puerto inicio? ");
        int puertoInicio = sc.nextInt();
        int index = puertoInicio - 1;
        Puerto puerto = puertos.get(index);
        sc.nextLine(); // Consumir el salto de l�nea

        System.out.print("IMPORTANTE: Esta opci�n borra los datos anteriores. " +
                         "Son correctos los nuevos datos (S/N)? ");
        char confirmacion = sc.next().charAt(0);

        if (confirmacion == 'S' || confirmacion == 's') {
            EditarBuque(bbdd, nombre, botadura, puerto, identificador);
            System.out.println("Los datos del buque han sido modificados correctamente.");
        } else {
            System.out.println("No se han realizado cambios en los datos del buque.");
        }
    }
	
	public static void EstadoBuques(BBDD bbdd) {
		HashMap<Buque, SortedSet<Operacion>> oPb = bbdd.getoPb();
		for(Buque b : bbdd.getBuques()) {
			b.show();
			SortedSet<Operacion> operaciones = oPb.get(b);
			if(operaciones==null) {
				System.out.println("nunca se programaron operaciones para el buque");
			} else {
				Operacion o = operaciones.first();
				o.show();	
			}
		}
	}
	private static void EditarBuque(
			BBDD bbdd, 
			String nombre,
			Fecha botadura,
			Puerto puerto,
			char identificador) {
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
