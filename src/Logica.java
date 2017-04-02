import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PImage;

public class Logica implements Observer {

	private Tiempo time;
	private PApplet app;
	private Comunicacion com;
	private int id, cambio, cambioDos, cambioTres, puntaje, posX, posY;
	private boolean start;
	private final String GROUP_ADDRESS = "226.24.6.8";
	private PImage escenarioUno, escenarioDos, escenarioTres, inesUno, inesDos, inesTres, bonk, dos, plaga, tres, uno,weed;
	private ArrayList<Weed> plantas;
	private ArrayList<Plaga> plagas;
	private ArrayList<Bonk> crack;
	private Personaje per, perDos, perTres;

	public Logica(PApplet app) {
		// TODO Auto-generated constructor stub
		this.app = app;
		time = new Tiempo();
		per = new Personaje(1, 100, 100);
		perDos = new Personaje(2, 100, 100);
		perTres = new Personaje(3, 100, 100);
		

		plantas = new ArrayList<>();
		plagas = new ArrayList<>();
		crack = new ArrayList<>();

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
				app.text(sec, app.width / 2, 40);
				pintarMaricadasDos();
				mover();
				for (int i = 0; i < plantas.size(); i++) {
					Weed planta = (Weed) plantas.get(i);
//					Personaje per = new Personaje(1,100, 100);
					if (planta instanceof Weed && per instanceof Personaje) {
						if (PApplet.dist(planta.getX(), planta.getY(), per.getX(), per.getY()) < 10) {
							plantas.remove(i);
							puntaje += 100;
						}
					}
				}
				try {
					com.enviar(new Resultado(puntaje), GROUP_ADDRESS);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				app.text(sec, app.width / 2, 40);
				pintarMaricadas();
				moverDos();
				for (int i = 0; i < plagas.size(); i++) {
					Plaga plaga = (Plaga) plagas.get(i);
//					Personaje per = new Personaje(2,100, 100);
					if (plaga instanceof Plaga && perDos instanceof Personaje) {
						if (PApplet.dist(plaga.getX(), plaga.getY(), perDos.getX(), perDos.getY()) < 10) {
							plagas.remove(i);
							puntaje -= 50;
						}
					}
				}
				try {
					com.enviar(new Resultado(puntaje), GROUP_ADDRESS);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				app.text(sec, app.width / 2, 40);
				pintarMaricadasTres();
				moverTres();
				for (int i = 0; i < crack.size(); i++) {
					Bonk cracks = (Bonk) crack.get(i);
//					Personaje per = new Personaje(3,100, 100);
					if (cracks instanceof Bonk && perTres instanceof Personaje) {
						if (PApplet.dist(cracks.getX(), cracks.getY(), perTres.getX(), perTres.getY()) < 10) {
							plagas.remove(i);
							puntaje -= 50;
						}
					}
				}
				try {
					com.enviar(new Resultado(puntaje), GROUP_ADDRESS);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fin();
				break;
			}
		}

	}

	public void pintarMaricadas() {
		for (int j = 0; j < plagas.size(); j++) {
			app.image(plaga, plagas.get(j).getX(), plagas.get(j).getY());
		}
	}
	public void pintarMaricadasDos() {
		for (int j = 0; j < plantas.size(); j++) {
			app.image(weed, plantas.get(j).getX(), plantas.get(j).getY());
		}
	}
	public void pintarMaricadasTres() {
		for (int j = 0; j < crack.size(); j++) {
			app.image(bonk, crack.get(j).getX(), crack.get(j).getY());
		}
	}

	public void fin() {
		int sec = time.getSec();

		if (sec >= 30 && cambio == 1) {
			time.setCorre(false);
			app.fill(0);
			app.noStroke();
			app.rect(0, 150, app.width, 400);
			app.fill(255);
			app.textAlign(PApplet.CENTER);
			app.text("TU TIEMPO SE ACABO", app.width / 2, app.height / 2);
			try {
				com.enviar(plantas, GROUP_ADDRESS);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (sec >= 30 && cambioDos == 1) {
			time.setCorre(false);
			app.fill(0);
			app.noStroke();
			app.rect(0, 150, app.width, 400);
			app.fill(255);
			app.textAlign(PApplet.CENTER);
			app.text("TU TIEMPO SE ACABO", app.width / 2, app.height / 2);
		}
		if (sec >= 60 && cambioTres == 1) {
			time.setCorre(false);
			app.fill(0);
			app.noStroke();
			app.rect(0, 150, app.width, 400);
			app.fill(255);
			app.textAlign(PApplet.CENTER);
			app.text("TU TIEMPO SE ACABO", app.width / 2, app.height / 2);
		}
	}
	
	public void mover(){
		if (id == 1) {
			posX = per.getX();
			posY = per.getY();
			if (app.key == 'a') {
				posX -= 1;
			}
			if (app.key == 's') {
				posY += 1;
			}
			if (app.key == 'd') {
				posX += 1;
			}
			if (app.key == 'w') {
				posY -= 1;
			}
		}
	}
	
	public void moverDos(){
		if (id == 2) {
			posX = perDos.getX();
			posY = perDos.getY();
			if (app.key == 'a') {
				posX -= 1;
			}
			if (app.key == 's') {
				posY += 1;
			}
			if (app.key == 'd') {
				posX += 1;
			}
			if (app.key == 'w') {
				posY -= 1;
			}
		}
	}
	
	public void moverTres(){
		if (id == 3) {
			posX = perTres.getX();
			posY = perTres.getY();
			if (app.key == 'a') {
				posX -= 1;
			}
			if (app.key == 's') {
				posY += 1;
			}
			if (app.key == 'd') {
				posX += 1;
			}
			if (app.key == 'w') {
				posY -= 1;
			}
		}
	}

	public void click() {
		if (app.mousePressed && app.mouseX > 90 && app.mouseX < 230 && app.mouseY > 450 && app.mouseY < 500
				&& cambio == 0 && id == 1) {
			cambio = 1;
		}

		if (app.mousePressed && app.mouseX > 90 && app.mouseX < 230 && app.mouseY > 450 && app.mouseY < 500
				&& cambioDos == 0 && id == 2) {
			cambioDos = 1;
		}

		if (app.mousePressed && app.mouseX > 90 && app.mouseX < 230 && app.mouseY > 450 && app.mouseY < 500
				&& cambioTres == 0 && id == 3) {
			cambioTres = 1;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof Personaje) {
			Personaje yupi = (Personaje) arg;
			if (yupi.getId() == id) {
				switch (yupi.getId()) {
				case 1:
					app.image(uno, yupi.getX(), yupi.getY());
					break;

				case 2:
					app.image(dos, yupi.getX(), yupi.getY());
					break;
				case 3:
					app.image(tres, yupi.getX(), yupi.getY());
					break;
				}
			}
		}

		if (arg instanceof Plaga) {
			Plaga plaguita = (Plaga) arg;
			if (plaguita.getId() == 1) {
				plagas.add(new Plaga((int) app.random(20, 260), (int) app.random(200, 680), 1));
			}
		}
		if (arg instanceof Weed) {
			Weed plantula = (Weed) arg;
			if (plantula.getId() == 2) {
				plantas.add(new Weed((int) app.random(20, 260), (int) app.random(550, 680), 2));
			}
		}
		if (arg instanceof Bonk) {
			Bonk plantula = (Bonk) arg;
			if (plantula.getId() == 2) {
				crack.add(new Bonk((int) app.random(20, 260), (int) app.random(200, 680), 3));
			}
		}
		if (arg instanceof ArrayList<?>) {
			Weed aja = (Weed) arg;
			if (aja.getId() == 1) {
				pintarMaricadasDos();
			}
		}
		if (arg instanceof ArrayList<?>) {
			Weed aja = (Weed) arg;
			if (aja.getId() == 2) {
				pintarMaricadasDos();
			}
		}
		if (arg instanceof ArrayList<?>) {
			Bonk aja = (Bonk) arg;
			if (aja.getId() == 3) {
				pintarMaricadasTres();
			}
		}
	}

}
