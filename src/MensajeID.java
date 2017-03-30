import java.io.Serializable;

public class MensajeID implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String data;

	public MensajeID(String data) {
		this.data = data;
	}

	public String getContenido() {
		return data;
	}

	public void setContenido(String data) {
		this.data = data;
	}
}