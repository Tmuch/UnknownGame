package utils;

public class Time
{
	public static final long	SECOND_NS	= 1000000000L;	//one second in nanoseconds
	public static final long	SECOND_MS	= 1000L;		//one second in milliseconds

	private static double		delta;


	public static long getTime()
	{
		return System.nanoTime();
	}

	public static double getDelta()
	{
		return delta;
	}

	public static void setDelta(double delta)
	{
		Time.delta = delta;
	}
}
