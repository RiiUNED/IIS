package Gestion;

import java.util.*;

import BBDD.*;
import Tipos.*;
import Datos.*;

public class GesPuertos {
	public static void MenuEditarPuerto(Scanner sc, BBDD bbdd) {
		 int id;
	     String nombre;
	     char tipo, confirmacion;
	     do {
	         System.out.println("Editar Puerto:");
	         System.out.print("Identificador (n�mero entre 1 y 10)? ");
	         id = sc.nextInt();
	         sc.nextLine(); // consume el salto de l�nea despu�s del n�mero entero
	         System.out.print("Nombre (entre 1 y 20 caracteres)? ");
	         nombre = sc.nextLine();
	         System.out.print("Tipo (Y-Yacimiento, R-Refiner�a, D-Deposito)? ");
	         tipo = sc.next().charAt(0);
	         System.out.print("IMPORTANTE: Esta opci�n borra los datos anteriores. Son " +
	                          "correctos los nuevos datos (S/N)? ");
	         confirmacion = sc.next().charAt(0);
	         if (confirmacion == 'S') {
	             EditarPuerto(id, nombre, tipo, bbdd);
	             break; // salir del bucle si se confirmaron los nuevos datos
	         }
	     } while (true);
	}
	
	private static void EditarPuerto(
			int ID, 
			String nombre, 
			char tipo,
			BBDD bbdd) {
		int index = ID - 1;
		TipoPuerto nuevoT = null;
		switch(tipo) {
		case 'Y':
			nuevoT = TipoPuerto.Yacimiento;
			break;
		case 'R':
			nuevoT = TipoPuerto.Refineria;
			break;
		case 'D':
			nuevoT = TipoPuerto.Deposito;
			break;
		}
		Puerto nuevoP = new Puerto(ID, nombre, nuevoT);
		ArrayList<Puerto> puertos = bbdd.getPuertos();
		puertos.get(index);
		puertos.set(index, nuevoP);
	}
}
