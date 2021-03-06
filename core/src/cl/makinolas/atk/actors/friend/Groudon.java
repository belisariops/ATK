package cl.makinolas.atk.actors.friend;

import cl.makinolas.atk.actors.Monsters;
import cl.makinolas.atk.actors.attacks.Attacks;
import cl.makinolas.atk.actors.attacks.ShootAttack;
import cl.makinolas.atk.actors.attacks.states.BlueBeamState;
import cl.makinolas.atk.types.TypeFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Groudon extends AbstractFriend {

    public Groudon() {
        friend = Enemies.GROUDON;
        TextureRegion[][] faces = new TextureRegion(new Texture(Gdx.files.internal("Actors/Mewtwo_faces.png"))).split(40, 40);
        setTexture(new TextureRegion(new Texture(Gdx.files.internal("Actors/Groudon.png"))));
        setCutSprites(39,33);
        setWalkAnimation(3,4,5,6);
        setHurtAnimation(2);
        setMeleeAnimation(1,2);
        setIdleAnimation(3);
        setSpecialAnimation(1,2);
        setFaceSprite(faces[0][0]);
        initLevel(30);
        initDead();
        setActualEvolution(0);
        setStats();
        setMaxMagic(1000);
        addType(TypeFactory.getType("Ground"));
    }
    
    public Groudon(int lvl) {
    	this();
    	this.initLevel(lvl);
    }

    @Override
    protected void initLevel(int level){
        this.level = new Level(level);
    }

    @Override
    public Attacks getFriendAttack(World myWorld, float x , float y, boolean facingRight, Monsters source){
        return new ShootAttack(new BlueBeamState(),myWorld, x, y, facingRight, source);
    }
    
    @Override
	public int getAttackMagicRequirement() {
	  // TODO Auto-generated method stub
	  return BlueBeamState.getMagicRequirement();
	}


}