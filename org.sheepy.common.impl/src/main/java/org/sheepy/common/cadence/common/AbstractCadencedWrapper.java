package org.sheepy.common.cadence.common;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractCadencedWrapper
{
	protected long tickerStep = 0;
	protected long accumulator = 0;

	public AtomicBoolean stop = new AtomicBoolean(false);

	public AbstractCadencedWrapper(float frequency)
	{
		// We planify the next tick
		if (frequency > 0)
		{
			tickerStep = (long) (1e9 / frequency);
		}
		else
		{
			// If frequency <= 0, each accumulation leads to a tick.
			tickerStep = -1;
		}
	}

	public void accumulate(long stepNano)
	{
		accumulator += stepNano;
	}

	public boolean shouldTick()
	{
		return accumulator > 0 && accumulator >= tickerStep;
	}

	public final void tick()
	{
		if (tickerStep <= 0)
		{
			// if frequency <= 0, we use the accumulated time
			doTick(accumulator);
			accumulator = 0;
		}
		else
		{
			doTick(tickerStep);
			accumulator -= tickerStep;
		}
	}

	public abstract void doTick(long stepNano);

	public abstract String getLabel();
}