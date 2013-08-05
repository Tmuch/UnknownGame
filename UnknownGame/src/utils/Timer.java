package utils;

import render.Window;

public class Timer
{
	public static final long	SECOND_NS	= 1000000000L;	//one second in nanoseconds
	public static final long	SECOND_MS	= 1000L;		//one second in milliseconds

	float						tps;						//ticks per second
	public int					elapsedTicks;				//how many ticks have occurred since the last call to updateTimer()
	private long				lastTime	= -1L;			//time reported at last updateTimer()
	//private double				lastHighResTime;
	private double				delta;

	//private long				lastSyncHRClock;

	public Timer(float tps)
	{
		this.tps = tps;
		this.elapsedTicks = 0;
	}

	public long getTime()
	{
		return System.nanoTime();
	}

	public double getDelta()
	{
		return delta;
	}

	public void setDelta(double delta)
	{
		this.delta = delta;
	}

	long	prevTime	= System.currentTimeMillis();

	/* 1 ms to prevent divide by zero when counters first begin.
	 * Will have no significant impact on counters */
	long	elapsed		= 1;
	long	ticks;

	public void updateTimer()
	{
		if (lastTime == -1L) lastTime = getTime();
		long currTime = getTime();
		long diff = currTime - lastTime;
		delta += diff / (SECOND_NS / this.tps);
		lastTime = currTime;
		while (delta >= 1)
		{
			elapsedTicks++;
			ticks++;
			delta--;
		}


		if (this.elapsedTicks > 10)
		{
			this.elapsedTicks = 10;
		}

		long curr = System.currentTimeMillis();
		elapsed += curr - prevTime;
		prevTime = curr;
		Window.setTitle(((((long) ticks) * Timer.SECOND_MS) / elapsed) + " tps");

	}
}
