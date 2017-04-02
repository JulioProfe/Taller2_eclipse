import java.io.Serializable;

public class Resultado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int puntaje;

	public Resultado(int puntaje) {
		this.puntaje = puntaje;
	}

	public int getPuntaje() {
		return puntaje;
	}
}
