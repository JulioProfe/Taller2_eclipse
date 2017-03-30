public class Tiempo extends Thread {

	private int mil, sec;
	private boolean corre;

	public Tiempo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (corre) {
				mil++;
				if (mil / 1000 == 1) {
					sec += 1;
					mil = 0;
				}
			}
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public int getMil() {
		return mil;
	}

	public int getSec() {
		return sec;
	}

	public boolean isCorre() {
		return corre;
	}

	public void setCorre(boolean corre) {
		this.corre = corre;
	}

}