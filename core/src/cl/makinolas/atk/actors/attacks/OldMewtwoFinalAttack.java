package cl.makinolas.atk.actors.attacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import cl.makinolas.atk.GameConstants;
import cl.makinolas.atk.actors.Monsters;

public class OldMewtwoFinalAttack extends ThrowableAttacks {
  
  public OldMewtwoFinalAttack(World myWorld, float x, float y, boolean facingRight, Monsters source) {
    super(myWorld, x, y, facingRight, source);
    isDropping();
  }

  @Override 
  protected void setAnimation() {
    setMasterTexture(new TextureRegion(new Texture(Gdx.files.internal("Attacks/bluebeam.png"))),25,15);
    addAnimation(0.2f, new int[]{0, 0}, new int[]{0, 1}, new int[]{0, 2},new int[]{0, 3});
  }
  
  @Override
  public int getAttackDamage() {
    return 70;
  }
  
  @Override
  public void draw(Batch batch, float alpha){
      Vector2 myPosition = myBody.getPosition();
      TextureRegion actualSprite = getActualSprite();
      batch.draw(actualSprite, myPosition.x * GameConstants.WORLD_FACTOR - actualSprite.getRegionWidth() / 2 , myPosition.y * GameConstants.WORLD_FACTOR - actualSprite.getRegionHeight() / 2,
              actualSprite.getRegionWidth() / 2, actualSprite.getRegionHeight() / 2, actualSprite.getRegionWidth(), actualSprite.getRegionHeight(),
              getScaleX(), -1*getScaleY(), -90);
  }
}
