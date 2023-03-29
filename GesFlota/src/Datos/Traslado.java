package Datos;

import Fecha.*;

public class Traslado {
	private Fecha inicio;
	private int duracion;
	private Puerto origen;
	private Puerto destino;
	
	public Traslado (Fecha inicio, Puerto o, int duracion, Puerto destino) {
		this.inicio = inicio;
		this.origen = o;
		this.duracion = duracion;
		this.destino = destino;
	}

	public Puerto getOrigen() {
		return origen;
	}

	public void setOrigen(Puerto origen) {
		this.origen = origen;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Puerto getDestino() {
		return destino;
	}

	public void setDestino(Puerto destino) {
		this.destino = destino;
	}
	
	public Fecha getInicio() {
		return inicio;
	}

	public void setInicio(Fecha inicio) {
		this.inicio = inicio;
	}

	public void show() {
		System.out.print("El trasaldo se inicia el: ");
		this.inicio.show();
		System.out.println("El trasaldo dura "+this.duracion+" dias.");
		System.out.print("Origen: ");
		this.origen.show();
		System.out.print("Destino: ");
		this.destino.show();
	}
}
