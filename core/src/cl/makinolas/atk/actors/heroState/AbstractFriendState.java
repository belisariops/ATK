package cl.makinolas.atk.actors.heroState;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import cl.makinolas.atk.actors.Hero;
import cl.makinolas.atk.actors.JumpState;
import cl.makinolas.atk.actors.attacks.Attacks;
import cl.makinolas.atk.actors.friend.Friend;
import cl.makinolas.atk.stages.AbstractStage;

public abstract class AbstractFriendState {
	protected Hero hero;
	
	  private long cooldownTimer;
	  protected Attacks attack;

	public AbstractFriendState() {
		hero = Hero.getInstance();
	    cooldownTimer = 0;
	}

	public void attackSecondary() {
		hero.Getmplayer().PlayClaw();
		hero.setAttacking(true);
	}

	public void attackPrimary(AbstractStage abstractStage, Friend actualFriend, World myWorld, Vector2 position,
			boolean isFacingRight, Hero hero2) {
		if (actualFriend.getMagic() >= actualFriend.getAttackMagicRequirement()
				&& cooldownTimer < System.currentTimeMillis()) {
			attack = actualFriend.getFriendAttack(myWorld, position.x, position.y, isFacingRight, hero2);
			actualFriend.setMagic(actualFriend.getMagic() - actualFriend.getAttackMagicRequirement());
			hero2.Getmplayer().PlayProyectileSound();
			abstractStage.addGameActor(attack);
			attack.getSpriteState().secondaryEfectsToSource(hero2);
			cooldownTimer = System.currentTimeMillis() + attack.getSpriteState().getCooldown();
		}
	}

	public void jump(JumpState state, Hero hero2) {
			hero2.setJumping(true);
			state.restarCount();
			state.jump();
	}

	public boolean isFacingRight(int vx) {
		return vx>0;
	}

	public boolean isSinging() {
		return false;
	}
	
	public Attacks getAttack(){
		return attack;
	}
	
	public void setAttack(Attacks attack){
		this.attack = attack;
	}

	public void isNotPressingPrimaryAttack() {
		if(attack != null)
			attack.unPress();;
	}

}
