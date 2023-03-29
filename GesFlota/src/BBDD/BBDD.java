package BBDD;

import java.util.*;

import Fecha.*;
//import Gestion.*;
import Tipos.*;
import Datos.*;

public class BBDD {
	private ArrayList<Puerto> puertos;
	private ArrayList<Buque> buques;
	private HashMap<Buque, SortedSet<Operacion>> oPb;
	
	public BBDD() {
		Fecha f1 = new Fecha(1, Mes.Abril, 2020);
		Fecha f2 = new Fecha(15, Mes.Agosto, 2015);
		Fecha f3 = new Fecha(25, Mes.Junio, 2010);
		Fecha f4 = new Fecha(12, Mes.Noviembre, 2005);
		Fecha f5 = new Fecha(30, Mes.Marzo, 2001);
		//--------------------------- Puertos --------------------------------------
		this.puertos = new ArrayList<Puerto>();
		this.puertos.add(new Puerto(1, "Algeciras", TipoPuerto.Deposito));
		this.puertos.add(new Puerto(2, "Valencia", TipoPuerto.Refineria));
		this.puertos.add(new Puerto(3, "Barcelona", TipoPuerto.Yacimiento));
		this.puertos.add(new Puerto(4, "Bilbao", TipoPuerto.Deposito));
		this.puertos.add(new Puerto(5, "Tarragona", TipoPuerto.Refineria));
		this.puertos.add(new Puerto(6, "Las Palmas", TipoPuerto.Yacimiento));
		this.puertos.add(new Puerto(7, "Cartagena", TipoPuerto.Deposito));
		this.puertos.add(new Puerto(8, "Castellon", TipoPuerto.Refineria));
		this.puertos.add(new Puerto(9, "Huelva", TipoPuerto.Yacimiento));
		this.puertos.add(new Puerto(10, "Sevilla", TipoPuerto.Deposito));
		//--------------------------- Puertos --------------------------------------
		this.buques = new ArrayList<Buque>();
		this.buques.add(new Buque("A", "Celeste", f1, this.puertos.get(0)));
		this.buques.add(new Buque("B", "Gran Este", f2, this.puertos.get(1)));
		this.buques.add(new Buque("C", "Asturias", f3, this.puertos.get(2)));
		this.buques.add(new Buque("D", "Semper Albus", f4, this.puertos.get(3)));
		this.buques.add(new Buque("E", "Mata Mua", f5, this.puertos.get(4)));
		//--------------------------- Operaciones --------------------------------------
		this.oPb = new HashMap<Buque, SortedSet<Operacion>>();
		
		SortedSet<Operacion> ops1 = new TreeSet<Operacion>();
		Operacion op1 = new Operacion(new OperacionPuerto(f1, this.puertos.get(0), null, TipoCarga.Vacio, 0), null, null);
		ops1.add(op1);
		this.oPb.put(this.buques.get(0), ops1);
		
		SortedSet<Operacion> ops2 = new TreeSet<Operacion>();
		Operacion op2 = new Operacion(new OperacionPuerto(f2, this.puertos.get(1), null, TipoCarga.Vacio, 0), null, null);
		ops2.add(op2);
		this.oPb.put(this.buques.get(1), ops2);
		
		SortedSet<Operacion> ops3 = new TreeSet<Operacion>();
		Operacion op3 = new Operacion(new OperacionPuerto(f3, this.puertos.get(2), null, TipoCarga.Vacio, 0), null, null);
		ops3.add(op3);
		this.oPb.put(this.buques.get(2), ops3);
		
		SortedSet<Operacion> ops4 = new TreeSet<Operacion>();
		Operacion op4 = new Operacion(new OperacionPuerto(f4, this.puertos.get(3), null, TipoCarga.Vacio, 0), null, null);
		ops4.add(op4);
		this.oPb.put(this.buques.get(3), ops4);
		
		SortedSet<Operacion> ops5 = new TreeSet<Operacion>();
		Operacion op5 = new Operacion(new OperacionPuerto(f5, this.puertos.get(4), null, TipoCarga.Vacio, 0), null, null);
		ops5.add(op5);
		this.oPb.put(this.buques.get(4), ops5);
	}

	public HashMap<Buque, SortedSet<Operacion>> getoPb() {
		return oPb;
	}

	public void setoPb(HashMap<Buque, SortedSet<Operacion>> oPb) {
		this.oPb = oPb;
	}

	public ArrayList<Puerto> getPuertos() {
		return puertos;
	}

	public void setPuertos(ArrayList<Puerto> puertos) {
		this.puertos = puertos;
	}

	public ArrayList<Buque> getBuques() {
		return buques;
	}

	public void setBuques(ArrayList<Buque> buques) {
		this.buques = buques;
	}
	
	public void showP() {
		for(Puerto p : puertos) {p.show();}
	}
	
	public void showB() {
		for(Buque b : buques) {b.show();}
	}
}
