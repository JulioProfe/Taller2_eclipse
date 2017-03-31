import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PImage;

public class Logica implements Observer {

	private Tiempo time;
	private PApplet app;
	private Comunicacion com;
	private int id, cambio, cambioDos, cambioTres, puntaje;
	private boolean start;
	private final String GROUP_ADDRESS = "226.24.6.8";
	private PImage escenarioUno, escenarioDos, escenarioTres, inesUno, inesDos, inesTres, bonk, dos, plaga, tres, uno, weed;

	public Logica(PApplet app) {
		// TODO Auto-generated constructor stub
		this.app = app;
		time = new Tiempo();
		

		com = new Comunicacion();
		com.addObserver(this);
		Thread hilo = new Thread(com);
		hilo.start();
		time.start();

		escenarioUno = app.loadImage("escenarioUno.png");
		escenarioDos = app.loadImage("escenarioDos.png");
		escenarioTres = app.loadImage("escenarioTres.png");
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
		if (start != true && id < 4 && app.frameCount < 540) {
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
				time.setCorre(true);
				app.image(escenarioUno, 0, 0);
				app.fill(0);
				app.textAlign(PApplet.CENTER);
				app.textSize(20);
				app.text("Puntaje", 230, 40);
				app.text(puntaje, 280, 41);
				app.textSize(20);
				int sec = time.getSec();
				app.text(sec, app.width/2, 40);
				fin();
				break;
			}
		}
		if (id == 2) {
			switch (cambioDos) {
			case 0:
				app.image(inesDos, 0, 0);
				break;

				case 1:
					if (id == 1 && time.getSec() == 30) {
						time.setCorre(true);
					}

					app.image(escenarioDos, 0, 0);
					app.fill(0);
					app.textAlign(PApplet.CENTER);
					app.textSize(20);
					app.text("Puntaje", 230, 40);
					app.text(puntaje, 280, 41);
					app.textSize(20);
					int sec = time.getSec();
					app.text(sec, app.width/2, 40);
					fin();
				break;
			}
		}
		
		if (id == 3) {
			switch (cambioTres) {
			case 0:
				app.image(inesTres, 0, 0);
				break;

				case 1:
					time.setCorre(true);
					app.image(escenarioTres, 0, 0);
					app.fill(0);
					app.textAlign(PApplet.CENTER);
					app.textSize(20);
					app.text("Puntaje", 230, 40);
					app.text(puntaje, 280, 41);
					app.textSize(20);
					int sec = time.getSec();
					app.text(sec, app.width/2, 40);
					fin();
				break;
			}
		}

	}
	
	public void fin(){
		int sec = time.getSec();

		if (sec >= 30 && cambio == 1) {
			time.setCorre(false);
			app.fill(0);
			app.noStroke();
			app.rect(0, 150, app.width, 400);
			app.fill(255);
			app.textAlign(PApplet.CENTER);
			app.text("TU TIEMPO SE ACABO", app.width/2, app.height/2);
		}
		if (sec >= 30 && cambioDos == 1) {
			time.setCorre(false);
			app.fill(0);
			app.noStroke();
			app.rect(0, 150, app.width, 400);
			app.fill(255);
			app.textAlign(PApplet.CENTER);
			app.text("TU TIEMPO SE ACABO", app.width/2, app.height/2);
		}
		if (sec >= 60 && cambioTres == 1) {
			time.setCorre(false);
			app.fill(0);
			app.noStroke();
			app.rect(0, 150, app.width, 400);
			app.fill(255);
			app.textAlign(PApplet.CENTER);
			app.text("TU TIEMPO SE ACABO", app.width/2, app.height/2);
		}
	}
	
	public void click(){
		if (app.mousePressed && app.mouseX > 90 && app.mouseX < 230 && app.mouseY > 450 && app.mouseY < 500 && cambio == 0 && id == 1) {
			cambio = 1;
		}
		
		if (app.mousePressed && app.mouseX > 90 && app.mouseX < 230 && app.mouseY > 450 && app.mouseY < 500 && cambioDos == 0 && id == 2) {
			cambioDos = 1;
		}

		if (app.mousePressed && app.mouseX > 90 && app.mouseX < 230 && app.mouseY > 450 && app.mouseY < 500 && cambioTres == 0 && id == 3) {
			cambioTres = 1;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
