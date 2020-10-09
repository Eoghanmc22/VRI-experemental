package net.minestom.vanilla.entity;

import net.minestom.server.entity.ai.GoalSelector;
import net.minestom.server.entity.ai.goal.DoNothingGoal;
import net.minestom.server.entity.ai.goal.RandomLookAroundGoal;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import net.minestom.server.entity.type.animal.EntityPig;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.Position;

import java.util.List;

public class VanillaPig extends EntityPig {

	public VanillaPig(Position spawnPosition, Instance instance) {
		super(spawnPosition);
		setInstance(instance);
		final List<GoalSelector> goals = getGoalSelectors();
		goals.add(new DoNothingGoal(this, 100, 0.33f));
		goals.add(new RandomLookAroundGoal(this, 3));
		goals.add(new RandomStrollGoal(this, 10, 0.33f, true));
	}

}
