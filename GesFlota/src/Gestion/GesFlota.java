package Gestion;

import java.util.*;

//import Fecha.*;
import BBDD.*;
//import Datos.*;

public class GesFlota {

	public static void main(String[] args) {
		
		BBDD bbdd = new BBDD();
				
		//Fecha f1 = new Fecha(1, Mes.Enero, 2023);
		//Fecha f2 = new Fecha(10, Mes.Enero, 2023);
		//System.out.println(f1.compareTo(f2)); -> -1
		//System.out.println(f2.compareTo(f1)); -> 1
		//System.out.println(f1.compareTo(f1)); -> 0
		MenuPrincipal(bbdd);

	}
	
	private static void MenuPrincipal(BBDD bbdd) {
		Scanner sc = new Scanner(System.in);
        char opcion;
        do {
            System.out.println("GesFlota: Gesti�n de Movimientos de una Flota");
            System.out.println("Editar Puerto (P)");
            System.out.println("Editar Buque (B)");
            System.out.println("Estado Buques (E)");
            System.out.println("Operar Buque (O)");
            System.out.println("Resumen Mensual Buque (R)");
            System.out.println("Salir (S)");
            System.out.print("Teclear una opci�n v�lida (P|B|E|O|R|S)? ");
            opcion = sc.next().charAt(0);
            switch (opcion) {
                case 'P':
                	System.out.println("Ha seleccionado EDITAR PUERTO");
                    GesPuertos.MenuEditarPuerto(sc, bbdd);
                    break;
                case 'B':
                	System.out.println("Ha seleccionado EDITAR BUQUE");
                    GesBuques.MenuEditarBuque(sc, bbdd);
                    break;
                case 'E':
                	System.out.println("Ha seleccionado ESTADO BUQUE");
                    GesBuques.EstadoBuques(bbdd);
                    break;
                case 'O':
                    // c�digo para operar buque
                    break;
                case 'R':
                    // c�digo para resumen mensual de buque
                    break;
                case 'S':
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opci�n inv�lida.");
                    break;
            }
        } while (opcion != 'S');
        sc.close();
    }

}
