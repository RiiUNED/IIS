package Datos;

import Fecha.*;

public class Buque {
	private String ID;
	private String nombre;
	private Fecha fecha;
	private Puerto botadura;
	
	public Buque(
			String ID,
			String nombre,
			Fecha fecha,
			Puerto botadura) {
		this.ID = ID;
		this.nombre = nombre;
		this.fecha = fecha;
		this.botadura = botadura;
	}
	
	public boolean equals (Buque b2) {
		boolean resultado = (this.ID.equals(b2.getID())) ? true : false;
		return resultado;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Fecha getFecha() {
		return fecha;
	}
	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}
	public Puerto getBotadura() {
		return botadura;
	}
	public void setBotadura(Puerto botadura) {
		this.botadura = botadura;
	}

	public void show() {
		System.out.print("ID: "+this.ID+", nombre: "+this.nombre+", Fecha: ");
		this.fecha.show();
		System.out.print("Puerto: ");
		this.botadura.show();
	}
}
