package org.sheepy.lily.core.api.util;

import java.util.concurrent.TimeUnit;

public class DurationMeter
{
	private final int tickBeforeReport;
	private final String name;
	private final TimeUnit reportUnit;

	private int tick = 0;
	private long duration = 0;
	private long timeStart = 0;

	public DurationMeter(String name, int tickBeforeReport, TimeUnit reportUnit)
	{
		this.name = name;
		this.tickBeforeReport = tickBeforeReport;
		this.reportUnit = reportUnit;
	}

	public void startRecord()
	{
		timeStart = System.nanoTime();
	}

	public void endRecord()
	{
		duration += System.nanoTime() - timeStart;
		tick++;

		if (tick >= tickBeforeReport)
		{
			printDuration();
			reset();
		}
	}

	public void reset()
	{
		duration = 0;
		tick = 0;
	}

	private void printDuration()
	{
		long averageDuration = duration / tick;
		int convertedDuration = (int) reportUnit.convert(averageDuration, TimeUnit.NANOSECONDS);

		System.out.println(String.format("%s: %d %s", name, convertedDuration,
				reportUnit.toChronoUnit().name()));
	}
}