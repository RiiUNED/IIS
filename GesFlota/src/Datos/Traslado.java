package Datos;

public class Traslado {
	private Puerto origen;
	private int duracion;
	private Puerto destino;
	
	public Traslado (Puerto o, int duracion, Puerto destino) {
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
	
	public void show() {
		System.out.print("Origen: ");
		this.origen.show();
		System.out.print("Destino: ");
		this.destino.show();
	}
}
