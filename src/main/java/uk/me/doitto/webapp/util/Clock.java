package uk.me.doitto.webapp.util;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.directwebremoting.Browser;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.ui.dwr.Util;

/**
 * Created by DWR
 * @author super
 *
 */
public class Clock implements Runnable {

	protected transient boolean active = false;
	
	public Clock () {
		new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(this, 1, 1, TimeUnit.SECONDS);
	}

	@Override
	public synchronized void run () {
		if (active) {
			setClockDisplay(new Date().toString());
		}
	}

	/**
	 * Called from the client to turn the clock on/off
	 */
	public synchronized void toggle () {
		active = !active;
		if (active) {
			setClockDisplay("Started");
		} else {
			setClockDisplay("Stopped");
		}
	}

	/**
	 * Actually alter the clients.
	 * 
	 * @param output
	 *            The string to display.
	 */
	public void setClockDisplay (final String output) {
		Browser.withPage(ServerContextFactory.get().getContextPath() + "/", new Runnable() {
			@Override
			public void run() {
				Util.setValue("clockDisplay", output);
			}
		});
	}
}