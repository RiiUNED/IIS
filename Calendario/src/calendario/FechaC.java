package calendario;

public class FechaC {
	private int dia;
	private Mes mes;
	private int anno;

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

	public static boolean esBisiesto(int anno) {
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
