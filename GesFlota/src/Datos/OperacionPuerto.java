package Datos;

import Tipos.*;
import Fecha.*;

public class OperacionPuerto {
	private Fecha fecha;
	private Puerto puerto;
	private TipoOperacion operacion;
	private TipoCarga carga;
	private int duracion;
	
	public OperacionPuerto(
			Fecha fecha,
			Puerto puerto,
			TipoOperacion tO,
			TipoCarga tC,
			int dur) {
		this.fecha = fecha;
		this.puerto = puerto;
		this.operacion = tO;
		this.carga = tC;
		this.duracion = dur;
	}

	public Fecha getFecha() {
		return fecha;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public Puerto getPuerto() {
		return puerto;
	}

	public void setPuerto(Puerto puerto) {
		this.puerto = puerto;
	}

	public TipoOperacion getOperacion() {
		return operacion;
	}

	public void setOperacion(TipoOperacion operacion) {
		this.operacion = operacion;
	}

	public TipoCarga getCarga() {
		return carga;
	}

	public void setCarga(TipoCarga carga) {
		this.carga = carga;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	public void show() {
		System.out.print("Fecha: ");
		this.fecha.show();
		System.out.print("Puerto: ");
		this.puerto.show();
		System.out.println("Operacion: "+this.operacion+", Carga: "+this.carga+", Duracion: "+this.duracion);
	}
}
