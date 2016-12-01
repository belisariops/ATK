package cl.makinolas.atk.actors.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import cl.makinolas.atk.GameConstants;
import cl.makinolas.atk.actors.GameActor;
import cl.makinolas.atk.actors.Hero;
import cl.makinolas.atk.actors.NullState;
import cl.makinolas.atk.actors.OnGround;
import cl.makinolas.atk.actors.OnWater;

public class WaterPlatform extends GameActor {
	
	private TextureRegion[][] texture;

	static final float TILE_FACTOR = 1.8f;
	protected BodyDef myBodyDefinition;
	int xp, yp, wp, hp;
	int n=1,k,f=0,c=0;

	public WaterPlatform(World myWorld, int x, int y, int widthTiles, int heightTiles) {
				
		/* 8x4 */
		texture = new TextureRegion(new Texture(Gdx.files.internal("Background/WaterBlock.png"))).split(44,44);
		/*k es el indice de que tan rapido se mueve el agua, entre mas alto mas lento, 0 es lo mas rapido*/
		k=5;
		
		myBodyDefinition = new BodyDef();
	    myBodyDefinition.position.set(new Vector2(x*TILE_FACTOR + widthTiles * TILE_FACTOR /2, y*TILE_FACTOR + heightTiles * TILE_FACTOR / 2));

	    Body myBody = myWorld.createBody(myBodyDefinition);
	    PolygonShape shape = new PolygonShape();
	    shape.setAsBox(widthTiles * TILE_FACTOR /2, heightTiles * TILE_FACTOR / 2);

	    /*Fixture atravesable de la plataforma para detectar cuando entra y sale del agua*/
	    FixtureDef fixture= new FixtureDef();
	    fixture.isSensor=true;
	    fixture.shape=shape;
	    myBody.createFixture(fixture);
	    shape.dispose();
	    setBody(myBody);
	    
	    xp = (int) (x * TILE_FACTOR * GameConstants.WORLD_FACTOR);
	    yp = (int) (y * TILE_FACTOR* GameConstants.WORLD_FACTOR);
	    wp = widthTiles;
	    hp = heightTiles;
	  }
	    
	
	@Override
	public void interactWithHero(Hero hero, WorldManifold worldManifold){	    
		/*setea que tan rapido cae en el agua*/
		hero.myBody.setGravityScale(0.5f);
		
		/*damping para frenar al hero cuando entra al agua y cuando se mueve en general*/
		hero.myBody.setLinearDamping(5);
		
		/*setea estado a dentro del agua*/
		hero.setState(new OnWater());
		hero.setInsideWater(true);

	}
	
	@Override
	public void endHeroInteraction(Hero hero, WorldManifold worldManifold) {
		/*restituimos que caiga con la velocidad original*/
		hero.myBody.setGravityScale(1);
		
		/*eliminamos damping*/
		hero.myBody.setLinearDamping(0);
		
		/*seteamos estado a ground para que pueda saltar luego de salir del agua*/
		hero.setState(new OnGround());
		hero.setInsideWater(false);
	}
	
	@Override
	public void act(float delta) {
	    if(n%k==0)
	    	c++;
	    
	    if(n==k*8-1) {
	    	n=0;
	    	c=0;
	    	f++;
	    	if(f%4==0)
	    		f=0;
	    }
	    n++;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
	    for (int i = 0; i < wp; i++) {
	      for (int j = 0; j < hp; j++) {
	        batch.draw(texture[f][c], xp + i*36, yp + j*36,37,37);
	      }
	    }
	  }


	@Override
	public void interact(GameActor actor2, WorldManifold worldManifold) {
		actor2.interactWithWater(this, worldManifold);
	
	}

	@Override
	public void endInteraction(GameActor actor2, WorldManifold worldManifold) {
		actor2.endWaterInteraction(this, worldManifold);
		
	}
}
