package cl.makinolas.atk.actors.attacks.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import cl.makinolas.atk.actors.Monsters;
import cl.makinolas.atk.stateEfects.BurnedStateEffect;
import cl.makinolas.atk.types.IType;
import cl.makinolas.atk.types.TypeFactory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FireballState extends SpriteState {
	
  private static int magicRequirement = 45;
  
  public FireballState() {
	  super();
	  cooldown = 100;
  }

  @Override
  public void initializeBody(float x, float y) {
    myAttack.initializeBody(x, y);
  }

  @Override
  public int getAttackDamage() {
    return 30;
  }

  @Override
  public TextureRegion getTexture() {
    return new TextureRegion(new Texture(Gdx.files.internal("Attacks/fireball.png")));
  }

  @Override
  public int getWidth() {
    return 30;
  }

  @Override
  public int getHeight() {
    return 37;
  }

  @Override
  public float getFrameTime() {
    return 0.2f;
  }

  @Override
  public PlayMode getModeAnimation() {
    return Animation.PlayMode.LOOP;
  }

  @Override
  public int getInitialSprite() {
    return 0;
  }

  @Override
  public int getFinalSprite() {
    return 4;
  }
  
  @Override
  public int getTypeAttack(Monsters monster) {
    return myAttack.getSpecialAttackDamage(monster);
  }

@Override
public IType getType() {
	return TypeFactory.getType("Fire");
}

@Override
public void secondaryEfectsToAfected(Monsters monster) {
	monster.addState(new BurnedStateEffect(monster, myAttack), 20);
}

public static int getMagicRequirement(){
	return magicRequirement;
}

}
