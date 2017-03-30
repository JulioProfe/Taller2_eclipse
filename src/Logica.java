import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PImage;

public class Logica implements Observer {

	private Tiempo time;
	private PApplet app;
	private Comunicacion com;
	private int id, cambio, cambioDos, cambioTres;
	private boolean start;
	private final String GROUP_ADDRESS = "226.24.6.8";
	private PImage escenarioUno, escenarioDos, escenarioTres, inesUno, inesDos, inesTres, bonk, dos, plaga, tres, uno, weed;

	public Logica(PApplet app) {
		// TODO Auto-generated constructor stub
		this.app = app;
		time = new Tiempo();
		time.start();

		com = new Comunicacion();
		com.addObserver(this);
		Thread hilo = new Thread(com);
		hilo.start();

		escenarioUno = app.loadImage("escenarioUno.png");
		escenarioDos = app.loadImage("escenarioUno.png");
		escenarioTres = app.loadImage("escenarioUno.png");
		inesUno = app.loadImage("inesUno.png");
		inesDos = app.loadImage("inesDos.png");
		inesTres = app.loadImage("inesTres.png");
		bonk = app.loadImage("bonk.png");
		dos = app.loadImage("dos.png");
		plaga = app.loadImage("plaga.png");
		tres = app.loadImage("tres.png");
		uno = app.loadImage("uno.png");
		weed = app.loadImage("weed.png");
		
		
		cambio = 0;
		cambioDos = 0;
		cambioTres = 0;

		id = com.getId();

		if (id == 3) {
			try {
				com.enviar("comenzar", GROUP_ADDRESS);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void pintar() {
		if (start != true && id < 4 && app.frameCount < 360) {
			app.fill(0);
			app.textAlign(PApplet.CENTER);
			app.text("Esperando Jugadores", app.width / 2, app.height / 2);
		}
		if (id >= 4) {
			app.fill(0);
			app.textAlign(PApplet.CENTER);
			app.text("Jugadores Completos", app.width / 2, app.height / 2);
			start = true;
		}
		if (id == 1) {
			switch (cambio) {
			case 0:
				app.image(inesUno, 0, 0);
				break;

			case 1:
				app.image(escenarioUno, 0, 0);
				break;
			}
		}
		if (id == 2) {
			switch (cambioDos) {
			case 0:
				app.image(inesDos, 0, 0);
				break;

				case 1:
					app.image(escenarioDos, 0, 0);
				break;
			}
		}
		
		if (id == 3) {
			switch (cambioTres) {
			case 0:
				app.image(inesTres, 0, 0);
				break;

				case 1:
					app.image(escenarioTres, 0, 0);
				break;
			}
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
