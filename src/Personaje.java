import java.io.Serializable;

import processing.core.PImage;

public class Personaje implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id, x, y;
	
	public Personaje(int id, int x, int y) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
