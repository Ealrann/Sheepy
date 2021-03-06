package org.sheepy.lily.core.allocation.test;

import org.junit.jupiter.api.Test;
import org.sheepy.lily.core.allocation.test.adapters.*;
import org.sheepy.lily.core.allocation.test.testallocation.TestallocationFactory;
import org.sheepy.lily.core.api.allocation.IAllocationService;
import org.sheepy.lily.core.api.model.LilyEObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAllocationDependency
{
	@Test
	public void testOptionalDependency()
	{
		final var context = new TestContext(0);
		final var root = TestallocationFactory.eINSTANCE.createRoot();
		final var node = TestallocationFactory.eINSTANCE.createNode();
		final var leaf = TestallocationFactory.eINSTANCE.createLeaf();
		final var container = TestallocationFactory.eINSTANCE.createContainer();
		final var box = TestallocationFactory.eINSTANCE.createBox();

		root.getNodes().add(node);
		root.getContainers().add(container);
		node.getLeaves().add(leaf);
		container.getBoxes().add(box);
		node.setContainer(container);

		((LilyEObject) root).loadExtenderManager();
		final var allocator = IAllocationService.INSTANCE.buildAllocator(root,
																		 context,
																		 AllocationObjectAllocation.class);
		allocator.updateAllocation();

		assertEquals(1, root.getCurrentAllocationCount());
		assertEquals(1, node.getCurrentAllocationCount());
		assertEquals(0, node.getDependencyUpdateCount());
		assertEquals(1, leaf.getCurrentAllocationCount());
		assertEquals(1, container.getCurrentAllocationCount());
		assertEquals(1, box.getCurrentAllocationCount());

		final var containerAllocation = container.adapt(ContainerAllocation.class);
		containerAllocation.lockAllocation();
		containerAllocation.markObsolete();
		allocator.updateAllocation();

		assertEquals(1, node.getDependencyUpdateCount());
		assertEquals(1, root.getCurrentAllocationCount());
		assertEquals(1, node.getCurrentAllocationCount());
		assertEquals(1, node.getDependencyUpdateCount());
		assertEquals(1, leaf.getCurrentAllocationCount());
		assertEquals(2, container.getCurrentAllocationCount());
		assertEquals(2, container.getTotalAllocationCount());
		assertEquals(2, box.getCurrentAllocationCount());

		containerAllocation.unlockAllocation();
		allocator.updateAllocation();

		assertEquals(1, node.getDependencyUpdateCount());
		assertEquals(1, root.getCurrentAllocationCount());
		assertEquals(1, node.getCurrentAllocationCount());
		assertEquals(1, node.getDependencyUpdateCount());
		assertEquals(1, leaf.getCurrentAllocationCount());
		assertEquals(1, container.getCurrentAllocationCount());
		assertEquals(2, container.getTotalAllocationCount());
		assertEquals(1, box.getCurrentAllocationCount());
		assertEquals(2, box.getTotalAllocationCount());
	}

	@Test
	public void testMandatoryDependency()
	{
		final var context = new TestContext(0);
		final var root = TestallocationFactory.eINSTANCE.createRoot();
		final var node = TestallocationFactory.eINSTANCE.createNode();
		final var leaf = TestallocationFactory.eINSTANCE.createLeaf();
		final var container = TestallocationFactory.eINSTANCE.createContainer();
		final var box = TestallocationFactory.eINSTANCE.createBox();

		root.getNodes().add(node);
		root.getContainers().add(container);
		node.getLeaves().add(leaf);
		container.getBoxes().add(box);
		leaf.getBoxes().add(box);

		((LilyEObject) root).loadExtenderManager();
		final var allocator = IAllocationService.INSTANCE.buildAllocator(root,
																		 context,
																		 AllocationObjectAllocation.class);
		allocator.updateAllocation();

		assertEquals(1, root.getCurrentAllocationCount());
		assertEquals(1, node.getCurrentAllocationCount());
		assertEquals(1, container.getCurrentAllocationCount());
		assertEquals(1, leaf.getCurrentAllocationCount());
		assertEquals(1, box.getCurrentAllocationCount());
		assertEquals(1, leaf.getTotalAllocationCount());
		assertEquals(1, box.getTotalAllocationCount());

		box.adapt(BoxAllocation.class).markObsolete();
		allocator.updateAllocation();

		assertEquals(1, root.getCurrentAllocationCount());
		assertEquals(1, node.getCurrentAllocationCount());
		assertEquals(1, container.getCurrentAllocationCount());
		assertEquals(1, leaf.getCurrentAllocationCount());
		assertEquals(1, box.getCurrentAllocationCount());
		assertEquals(2, leaf.getTotalAllocationCount());
		assertEquals(2, box.getTotalAllocationCount());

		leaf.adapt(LeafAllocation.class).lockAllocation();
		box.adapt(BoxAllocation.class).markObsolete();
		allocator.updateAllocation();

		assertEquals(1, root.getCurrentAllocationCount());
		assertEquals(1, node.getCurrentAllocationCount());
		assertEquals(1, container.getCurrentAllocationCount());
		assertEquals(2, leaf.getCurrentAllocationCount());
		assertEquals(1, box.getCurrentAllocationCount());
		assertEquals(3, leaf.getTotalAllocationCount());
		assertEquals(3, box.getTotalAllocationCount());
	}

	@Test
	public void testUpdateCriticalDependency()
	{
		final var context = new TestContext(0);
		final var root = TestallocationFactory.eINSTANCE.createRoot();
		final var node = TestallocationFactory.eINSTANCE.createNode();
		final var leaf = TestallocationFactory.eINSTANCE.createLeaf();
		final var container = TestallocationFactory.eINSTANCE.createContainer();
		final var box = TestallocationFactory.eINSTANCE.createBox();

		root.getNodes().add(node);
		root.getContainers().add(container);
		node.getLeaves().add(leaf);
		container.getBoxes().add(box);
		leaf.getBoxes().add(box);

		((LilyEObject) root).loadExtenderManager();
		final var allocator = IAllocationService.INSTANCE.buildAllocator(root,
																		 context,
																		 AllocationObjectAllocation.class);
		allocator.updateAllocation();

		assertEquals(1, root.getCurrentAllocationCount());
		assertEquals(1, node.getCurrentAllocationCount());
		assertEquals(1, container.getCurrentAllocationCount());
		assertEquals(1, leaf.getCurrentAllocationCount());
		assertEquals(1, box.getCurrentAllocationCount());
		assertEquals(1, leaf.getTotalAllocationCount());
		assertEquals(1, box.getTotalAllocationCount());

		allocator.updateAllocation();
		allocator.updateAllocation();

		assertEquals(1, root.getCurrentAllocationCount());
		assertEquals(1, node.getCurrentAllocationCount());
		assertEquals(1, container.getCurrentAllocationCount());
		assertEquals(1, leaf.getCurrentAllocationCount());
		assertEquals(1, box.getCurrentAllocationCount());
		assertEquals(1, leaf.getTotalAllocationCount());
		assertEquals(1, box.getTotalAllocationCount());

		box.adapt(BoxAllocation.class).requestUpdate();
		allocator.updateAllocation();

		assertEquals(1, root.getCurrentAllocationCount());
		assertEquals(1, node.getCurrentAllocationCount());
		assertEquals(1, container.getCurrentAllocationCount());
		assertEquals(1, leaf.getCurrentAllocationCount());
		assertEquals(1, box.getCurrentAllocationCount());
		assertEquals(1, leaf.getTotalAllocationCount());
		assertEquals(1, box.getTotalAllocationCount());

		box.adapt(BoxAllocation.class).requestUpdate();
		final var lock = box.adapt(BoxAllocation.class).newLock();

		allocator.updateAllocation();

		assertEquals(1, leaf.getCurrentAllocationCount());
		assertEquals(2, box.getCurrentAllocationCount());
		assertEquals(2, leaf.getTotalAllocationCount());
		assertEquals(2, box.getTotalAllocationCount());

		lock.unlock();

		allocator.updateAllocation();

		assertEquals(1, leaf.getCurrentAllocationCount());
		assertEquals(1, box.getCurrentAllocationCount());
		assertEquals(2, leaf.getTotalAllocationCount());
		assertEquals(2, box.getTotalAllocationCount());
	}

	@Test
	public void testTriageDependency()
	{
		final var context = new TestContext(0);
		final var root = TestallocationFactory.eINSTANCE.createRoot();
		final var node = TestallocationFactory.eINSTANCE.createNode();
		final var leaf = TestallocationFactory.eINSTANCE.createLeaf();
		final var container = TestallocationFactory.eINSTANCE.createContainer();
		final var box = TestallocationFactory.eINSTANCE.createBox();

		root.getNodes().add(node);
		root.getContainers().add(container);
		node.getLeaves().add(leaf);
		container.getBoxes().add(box);
		leaf.getBoxes().add(box);

		((LilyEObject) root).loadExtenderManager();
		final var allocator = IAllocationService.INSTANCE.buildAllocator(root,
																		 context,
																		 AllocationObjectAllocation.class);
		allocator.updateAllocation();

		assertEquals(1, root.getCurrentAllocationCount());
		assertEquals(1, node.getCurrentAllocationCount());
		assertEquals(1, container.getCurrentAllocationCount());
		assertEquals(1, leaf.getCurrentAllocationCount());
		assertEquals(1, box.getCurrentAllocationCount());
		assertEquals(1, leaf.getTotalAllocationCount());
		assertEquals(1, box.getTotalAllocationCount());

		container.adapt(AllocationObjectAllocation.class).requestUpdate();
		container.adapt(AllocationObjectAllocation.class).lockAllocation();
		allocator.updateAllocation();

		assertEquals(2, container.getCurrentAllocationCount());
		assertEquals(2, box.getCurrentAllocationCount());
		assertEquals(1, leaf.getCurrentAllocationCount());
		assertEquals(2, container.getTotalAllocationCount());
		assertEquals(2, box.getTotalAllocationCount());
		assertEquals(2, leaf.getTotalAllocationCount());
	}

	@Test
	public void testDeactivateDependency()
	{
		final var context = new TestContext(0);
		final var root = TestallocationFactory.eINSTANCE.createRoot();
		final var node = TestallocationFactory.eINSTANCE.createNode();
		final var leaf = TestallocationFactory.eINSTANCE.createLeaf();
		final var container = TestallocationFactory.eINSTANCE.createContainer();
		final var box1 = TestallocationFactory.eINSTANCE.createBox();
		final var box2 = TestallocationFactory.eINSTANCE.createBox();

		root.getNodes().add(node);
		root.getContainers().add(container);
		node.getLeaves().add(leaf);
		container.getBoxes().add(box1);
		container.getBoxes().add(box2);
		leaf.getBoxes().add(box1);
		leaf.getBoxes().add(box2);

		((LilyEObject) root).loadExtenderManager();
		final var allocator = IAllocationService.INSTANCE.buildAllocator(root,
																		 context,
																		 AllocationObjectAllocation.class);

		box2.setActivated(false);
		allocator.updateAllocation();

		assertEquals(1, root.getCurrentAllocationCount());
		assertEquals(1, node.getCurrentAllocationCount());
		assertEquals(1, container.getCurrentAllocationCount());
		assertEquals(1, leaf.getCurrentAllocationCount());
		assertEquals(1, leaf.getTotalAllocationCount());
		assertEquals(1, box1.getCurrentAllocationCount());
		assertEquals(0, box2.getCurrentAllocationCount());
		assertEquals(1, box1.getTotalAllocationCount());
		assertEquals(0, box2.getTotalAllocationCount());

		box2.setActivated(true);
		allocator.updateAllocation();

		assertEquals(1, box1.getCurrentAllocationCount());
		assertEquals(1, box2.getCurrentAllocationCount());
		assertEquals(1, box1.getTotalAllocationCount());
		assertEquals(1, box2.getTotalAllocationCount());

		box1.setActivated(false);
		box2.setActivated(false);
		allocator.updateAllocation();

		assertEquals(0, box1.getCurrentAllocationCount());
		assertEquals(0, box2.getCurrentAllocationCount());
		assertEquals(1, box1.getTotalAllocationCount());
		assertEquals(1, box2.getTotalAllocationCount());
	}

	@Test
	public void testDependencyStructureChange()
	{
		final var context = new TestContext(0);
		final var root = TestallocationFactory.eINSTANCE.createRoot();
		final var node1 = TestallocationFactory.eINSTANCE.createNode();
		final var node2 = TestallocationFactory.eINSTANCE.createNode();
		final var node3 = TestallocationFactory.eINSTANCE.createNode();
		final var container1 = TestallocationFactory.eINSTANCE.createContainer();
		final var container2 = TestallocationFactory.eINSTANCE.createContainer();

		root.getNodes().add(node1);
		root.getNodes().add(node2);
		root.getNodes().add(node3);
		root.getContainers().add(container1);
		node1.setContainer(container1);
		node2.setContainer(container1);
		node3.setContainer(container1);

		((LilyEObject) root).loadExtenderManager();
		final var allocator = IAllocationService.INSTANCE.buildAllocator(root,
																		 context,
																		 AllocationObjectAllocation.class);
		allocator.updateAllocation();

		assertEquals(1, root.getCurrentAllocationCount());
		assertEquals(1, node1.getCurrentAllocationCount());
		assertEquals(1, node2.getCurrentAllocationCount());
		assertEquals(1, node3.getCurrentAllocationCount());
		assertEquals(0, node1.getDependencyUpdateCount());
		assertEquals(0, node2.getDependencyUpdateCount());
		assertEquals(0, node3.getDependencyUpdateCount());
		assertEquals(1, container1.getCurrentAllocationCount());

		root.getContainers().remove(container1);
		root.getContainers().add(container2);
		node1.setContainer(container2);
		node2.setContainer(container2);
		node3.setContainer(container2);
		allocator.updateAllocation();

		assertEquals(1, root.getCurrentAllocationCount());
		assertEquals(1, node1.getCurrentAllocationCount());
		assertEquals(1, node2.getCurrentAllocationCount());
		assertEquals(1, node3.getCurrentAllocationCount());
		assertEquals(1, node1.getDependencyUpdateCount());
		assertEquals(1, node2.getDependencyUpdateCount());
		assertEquals(1, node3.getDependencyUpdateCount());
		assertEquals(1, container2.getCurrentAllocationCount());
	}
}
