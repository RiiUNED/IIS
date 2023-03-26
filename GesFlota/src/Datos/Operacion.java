package Datos;

import Fecha.*;

public class Operacion implements Comparable<Operacion>{
	private Fecha fecha;
	private OperacionPuerto carga;
	private Traslado traslado;
	private OperacionPuerto descarga;
	
	public Operacion(
			Fecha fecha, 
			OperacionPuerto carga, 
			Traslado traslado, 
			OperacionPuerto descarga) {
		this.fecha = fecha;
		this.carga = carga;
		this.traslado = traslado;
		this.descarga = descarga;
	}
	
	public void show() {
		System.out.print("Fecha: ");
		this.fecha.show();
		System.out.print("Operacion origen: ");
		this.carga.show();
		System.out.print("Operacion destino: ");
		this.descarga.show();
	}
	
	public Fecha getFecha() {
		return fecha;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public OperacionPuerto getCarga() {
		return carga;
	}

	public void setCarga(OperacionPuerto carga) {
		this.carga = carga;
	}

	public Traslado getTraslado() {
		return traslado;
	}

	public void setTraslado(Traslado traslado) {
		this.traslado = traslado;
	}

	public OperacionPuerto getDescarga() {
		return descarga;
	}

	public void setDescarga(OperacionPuerto descarga) {
		this.descarga = descarga;
	}
	
	@Override
	public int compareTo(Operacion o2) {
		return this.fecha.compareTo(o2.fecha);
	}

}
