import java.io.Serializable;

public class Plaga implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x, y, id;
	
	public Plaga(int x, int y, int id) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getId() {
		return id;
	}
	
	
}
