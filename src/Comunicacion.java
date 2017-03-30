import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.Observable;

public class Comunicacion extends Observable implements Runnable {

	public MulticastSocket mSocket;
	private final int PORT = 5000;
	private final String GROUP_ADDRESS = "226.24.6.8";
	private boolean life = true;
	private boolean identificado;
	private int id;

	public Comunicacion() {

		try {
			mSocket = new MulticastSocket(PORT);
			InetAddress host = InetAddress.getByName(GROUP_ADDRESS);
			mSocket.joinGroup(host);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			autoID();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void autoID() throws IOException {
		try {
			enviar(new MensajeID("Hola soy nuevo"), GROUP_ADDRESS);
			mSocket.setSoTimeout(500);
			while (!identificado) {
				DatagramPacket dPacket = recibir();
				if (dPacket != null) {
					MensajeID msg = (MensajeID) deserialize(dPacket.getData());
					String contenido = msg.getContenido();

					if (contenido.contains("soy:")) {
						String[] division = contenido.split(":");
						int idLimite = Integer.parseInt(division[1]);
						if (idLimite >= id) {
							id = idLimite + 1;
						}
					}
				}
			}
		} catch (SocketTimeoutException e) {
			if (id == 0) {
				id = 1;
			}
			identificado = true;
			System.out.println("Mi id es:" + id);
			mSocket.setSoTimeout(0);
		}
	}
	
	public void enviar(Object info, String ipAdrs) throws IOException {
		byte[] data = serialize(info);
		InetAddress host = InetAddress.getByName(ipAdrs);
		DatagramPacket dPacket = new DatagramPacket(data, data.length, host, PORT);

		mSocket.send(dPacket);
	}
	
	private DatagramPacket recibir() throws IOException {
		byte[] data = new byte[1024];
		DatagramPacket dPacket = new DatagramPacket(data, data.length);
		mSocket.receive(dPacket);
		return dPacket;
	}
	
	private byte[] serialize(Object o) {
		byte[] info = null;
		try {
			ByteArrayOutputStream baOut = new ByteArrayOutputStream();
			ObjectOutputStream oOut = new ObjectOutputStream(baOut);
			oOut.writeObject(o);
			info = baOut.toByteArray();

			oOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	private Object deserialize(byte[] b) {
		Object data = null;
		try {
			ByteArrayInputStream baOut = new ByteArrayInputStream(b);
			ObjectInputStream oOut = new ObjectInputStream(baOut);
			data = oOut.readObject();

			oOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	@Override
	public void run() {
		while (life) {
			if (mSocket != null) {
				try {
					if (!mSocket.isClosed()) {
						DatagramPacket dPacket = recibir();
						if (dPacket != null) {
							if (deserialize(dPacket.getData()) instanceof MensajeID) {
								MensajeID msg = (MensajeID) deserialize(dPacket.getData());
								String contenido = msg.getContenido();

								if (contenido.contains("soy nuevo")) {
									// Responder
									enviar(new MensajeID("soy:" + id), GROUP_ADDRESS);
								}
							}

							if (deserialize(dPacket.getData()) instanceof Weed) {
								setChanged();
								notifyObservers((Weed) deserialize(dPacket.getData()));
								clearChanged();
							}
							if (deserialize(dPacket.getData()) instanceof Plaga) {
								setChanged();
								notifyObservers((Plaga) deserialize(dPacket.getData()));
								clearChanged();
							}
							
							if (deserialize(dPacket.getData()) instanceof Bonk) {
								setChanged();
								notifyObservers((Bonk) deserialize(dPacket.getData()));
								clearChanged();
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int getId() {
		return id;
	}


}
