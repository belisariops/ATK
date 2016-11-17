package cl.makinolas.atk.actors;

import cl.makinolas.atk.actors.ui.IHero;
import cl.makinolas.atk.gamemodes.SurvivalHero;
import cl.makinolas.atk.minigames.ICharacter;
import cl.makinolas.atk.minigames.MinigameCharacter;

public class OnGround extends JumpState {
	
	public OnGround() {
		super();
	}

	public OnGround(IHero hero) {
		super(hero);
	}
	
	public void firstJump() {
		hero.setGravityScale(0.3f);
	  //hero.myBody.setGravityScale(0.3f);
	  	//hero.setSpeed(hero.getBody().getLinearVelocity().x,15f);
		hero.setSpeed(hero.getXSpeed(),15f);
	  hero.setState(new OnAir());
	}
	
	@Override
  public void setAnimation(ICharacter minigameCharacter, float delta) {
    minigameCharacter.onGroundAnimation(delta);
  }
}
