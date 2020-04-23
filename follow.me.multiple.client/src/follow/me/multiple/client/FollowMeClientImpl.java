package follow.me.multiple.client;

import follow.me.multiple.service.FollowMeItf;
import java.util.Map;

public class FollowMeClientImpl implements Runnable {

	/** Field for followMeServices dependency */
	private FollowMeItf[] followMeServices;
	private boolean endThread = false;

	/** Bind Method for followMeServices dependency */
	public void bindFollowMeServices(FollowMeItf followMeItf, Map properties) {
		System.out.println("Binding client.");
	}

	/** Unbind Method for followMeServices dependency */
	public void unbindFollowMeServices(FollowMeItf followMeItf, Map properties) {
		System.out.println("Unbinding follow me client");
	}

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("Follow me multiple bundles client is stopping");
		endThread = true;
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("Follow me multiple bundles client is starting");
		Thread t = new Thread(this);
		endThread = false;
		t.start();
	}

	@Override
	public void run() {
		try {
			while (!endThread) {
				System.out.println("Thread executing, and then sleeping");
				System.out.println("followMeServices length = " + this.followMeServices.length);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			stop();
		}

	}

}
