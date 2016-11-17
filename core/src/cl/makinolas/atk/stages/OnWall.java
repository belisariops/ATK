package cl.makinolas.atk.stages;

import cl.makinolas.atk.actors.JumpState;
import cl.makinolas.atk.actors.NullState;

public class OnWall extends JumpState {
	public void firstJump() {
		hero.setGravityScale(1);
		hero.setSpeed(hero.getXSpeed(),12);
		hero.setState(new NullState(hero));
	}
	

}
