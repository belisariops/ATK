package cl.makinolas.atk.actors.ui;

import cl.makinolas.atk.actors.JumpState;
import cl.makinolas.atk.actors.items.Inventory;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by belisariops on 11/9/16.
 */
public interface IHero {
    public Stage getStage();

    public void setAnimation();

    public void act(float delta);

    public void moveHorizontal(int i, boolean b);

    public void jump(int i);

    public Inventory getInventory();

    public void attackPrimary();

    public void attackSecondary();

    public void prevAllie();

    public void nextAllie();

    public void foo();

    public void isNotPressingSpace();

    public void setWorld(World myWorld, Vector2 initialPosition);

    public void setWorld(World myWorld);

    void setState(JumpState state);

    void setGravityScale(float f);

    void setSpeed(float x,float y);

    float getXSpeed();
}
