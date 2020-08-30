package org.sheepy.lily.core.api.allocation;

import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.lily.core.api.model.ILilyEObject;

import java.util.ServiceLoader;

public interface IAllocationService
{
	void updateAllocation(ILilyEObject target, IAllocationContext context, Class<? extends IExtender> type);
	void free(ILilyEObject target, IAllocationContext context, Class<? extends IExtender> type);

	IAllocationService INSTANCE = ServiceLoader.load(IAllocationService.class).findFirst().orElse(null);
}
