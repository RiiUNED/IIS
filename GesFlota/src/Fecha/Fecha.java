package Fecha;

public class Fecha implements Comparable<Fecha>{
	private int dia;
	private Mes mes;
	private int anno;
	
	public Fecha(int d, Mes m, int a) {
		this.dia = d;
		this.mes = m;
		this.anno = a;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public Mes getMes() {
		return mes;
	}

	public void setMes(Mes mes) {
		this.mes = mes;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}
	
	@Override
	public int compareTo(Fecha f2) {
		int resultado = 99;
		Integer f1A = this.anno;
		Integer f2A = f2.anno;
		int cA = f1A.compareTo(f2A);
		if(cA == 0) {
			Integer f1M = this.mes.ordinal();
			Integer f2M = f2.mes.ordinal();
			int cM = f1M.compareTo(f2M);
			if(cM == 0) {
				Integer f1D = this.dia;
				Integer f2D = f2.dia;
				resultado = f1D.compareTo(f2D);
			} else {
				resultado = cM;
			}
		} else {
			resultado = cA; 
		}
		return resultado;
	}
	
	public void show() {
		int mesO = this.mes.ordinal() + 1;
		System.out.println(this.dia+"/"+mesO+"/"+this.anno);
	}
	
	public Fecha sumFecha(int dias) {
		Fecha nuevaFecha = new Fecha(0, Mes.Abril, 0);
		System.out.println("dias: "+dias);
		int nuevoDia;
		Mes nuevoMes;
		int nuevoAnno;
		int diasMes = getDiasMes(this.getMes(), this.getAnno());
		int diaMes = this.dia;
		if(diaMes + dias > diasMes) {
			int numMes = this.getMes().ordinal();
			nuevaFecha.setDia(1);
			Mes[] meses = mes.values();
			nuevoAnno = this.anno;
			int diasConsumidos = diasMes - diaMes;
			System.out.println("dias consumidos : "+diasConsumidos);
			int diasR = dias - diasConsumidos;
			System.out.println("dias que faltan : "+diasR);
			if(numMes<11) {
				Mes mes = meses[numMes+1];
				nuevaFecha.setMes(mes);
				nuevaFecha.setAnno(nuevoAnno);
				nuevaFecha = nuevaFecha.sumFecha(diasR);
			} else {
				Mes mes = meses[0];
				nuevaFecha.setMes(mes);
				nuevaFecha.setAnno(nuevoAnno+1);
				nuevaFecha = nuevaFecha.sumFecha(diasR);
			}
			
		} else {
			System.out.println("dias: "+dias);
			nuevoDia = diaMes + dias;
			System.out.println("nuevo dia : "+nuevoDia);
			nuevaFecha.setDia(nuevoDia);
			nuevaFecha.show();
			nuevoMes = this.getMes();
			nuevaFecha.setMes(nuevoMes);
			nuevoAnno = this.getAnno();
			nuevaFecha.setAnno(nuevoAnno);
			nuevaFecha.show();	
		}
		return nuevaFecha;
	}
	
	int getDiasMes(Mes mes, int anno) {
		int dias = 0;
		switch (mes){
		case Enero:
			dias = 31;
			break;
		case Febrero:
			dias = (esBisiesto(anno)) ? 29 : 28;
			break;
		case Marzo:
			dias = 31;
			break;
		case Abril:
			dias = 30;
			break;
		case Mayo:
			dias = 31;
			break;
		case Junio:
			dias = 30;
			break;
		case Julio:
			dias = 31;
			break;
		case Agosto:
			dias = 31;
			break;
		case Septiembre:
			dias = 30;
			break;
		case Octubre:
			dias = 31;
			break;
		case Noviembre:
			dias = 30;
			break;
		case Diciembre:
			dias = 31;
			break;
		}
		return dias;
	}

	boolean esBisiesto(int anno) {
		int test1 = 4;
		int test2 = 100;
		int test3 = 400;
		boolean bisiesto = false;
		if (anno % test1 == 0) {
			bisiesto = true;
			if (anno % test2 == 0) {
				bisiesto = false;
			}
			if (anno % test3 == 0) {
				bisiesto = true;
			}
		}
		return bisiesto;
	}

}
