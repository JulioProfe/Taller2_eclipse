import processing.core.PApplet;

public class Main extends PApplet{
	
	private PApplet app;
	private Logica log; 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	}

	@Override
	public void settings() {
		// TODO Auto-generated method stub
		size(300, 700);
	}
	
	@Override
	public void setup() {
		// TODO Auto-generated method stub
		log = new Logica(this);
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		background(255);
		log.pintar();
	}
	
}