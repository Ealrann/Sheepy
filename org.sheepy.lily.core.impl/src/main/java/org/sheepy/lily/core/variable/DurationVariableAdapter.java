package org.sheepy.lily.core.variable;

import org.logoce.adapter.api.Adapter;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.cadence.Tick;
import org.logoce.extender.api.ModelExtender;
import org.logoce.notification.api.Notifier;
import org.sheepy.lily.core.api.variable.IDurationVariableAdapter;
import org.sheepy.lily.core.api.variable.IModelVariableAdapter;
import org.sheepy.lily.core.model.variable.DurationVariable;

import java.nio.ByteBuffer;
import java.util.List;

@ModelExtender(scope = DurationVariable.class)
@Adapter
@AutoLoad
public final class DurationVariableAdapter extends Notifier<IModelVariableAdapter.Features> implements
																							IDurationVariableAdapter
{
	private long startMs = 0;
	private int durationMs = 0;

	private DurationVariableAdapter()
	{
		super(List.of(Features.Value));
	}

	@Load
	private void load()
	{
		resetDuration();
	}

	@Tick
	private void tick()
	{
		durationMs = (int) (System.currentTimeMillis() - startMs);
		notify(IModelVariableAdapter.Features.Value, durationMs);
	}

	@Override
	public void getValue(final DurationVariable variable, final ByteBuffer buffer)
	{
		buffer.putInt(durationMs);
	}

	@Override
	public void setValue(final DurationVariable variable, final String value)
	{
		resetDuration();
		durationMs = Integer.parseInt(value);
		startMs -= durationMs;
	}

	@Override
	public void resetDuration()
	{
		startMs = System.currentTimeMillis();
	}

	@Override
	public int bytes()
	{
		return Integer.BYTES;
	}
}
