package Datos;

import Tipos.TipoPuerto;

public class Puerto {
	private int ID;
	private String nombre;
	private TipoPuerto tipo;
	
	public Puerto(int ID, String nombre, TipoPuerto tipo) {
		this.ID = ID;
		this.nombre = nombre;
		this.tipo = tipo;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Puerto other = (Puerto) obj;
	    return this.ID == other.getID();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoPuerto getTipo() {
		return tipo;
	}

	public void setTipo(TipoPuerto tipo) {
		this.tipo = tipo;
	}
	
	public void show() {
		System.out.println("ID: "+this.ID+", nombre: "+this.nombre+", "+this.tipo);
	}
	
}
