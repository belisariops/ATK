package cl.makinolas.atk.stages;

import cl.makinolas.atk.actors.*;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import cl.makinolas.atk.actors.ui.MapStageActor;
import cl.makinolas.atk.actors.ui.MobileGroup;
import cl.makinolas.atk.audio.GDXMusicPlayer;
import cl.makinolas.atk.screen.GameScreen;
import cl.makinolas.atk.screen.ShopScreen;

public class MapStage extends Stage implements KeyHandable{

    private Traveler traveler;
    private GDXMusicPlayer musicplayer;
    private Levels[] levels;
    private Spot current;
    private boolean[] levelsAllowed;
    private Game myGame;

    private boolean[] unlockedStages;

    public MapStage(Viewport v, Game game) {
        super(v);
        myGame = game;
        
    
        //Adding the actors to the stage (currently just the background, the traveler and the levels)
        addActor(new Background("Background/mapa.png", getCamera()));
        musicplayer=new GDXMusicPlayer();
        musicplayer.PlayLooped("Music/palett.mp3");
        buildLevels();
        levelsAllowed = Hero.getInstance().getLevelsUnlocked();

        unlockedStages = Hero.getInstance().getLevelsUnlocked();
        // Add floors
        for (int i = 0; i < levels.length; i++) {
            addActor(new MapStageActor(Levels.values()[i].levelSpot, !unlockedStages[i], this, levels[i].mapx, levels[i].mapy));
        }

        traveler = new Traveler();
        addActor(traveler);

        //Input configurations
        addListener(new SimpleInputController(this, new MobileGroup(Gdx.app.getType() == Application.ApplicationType.Android)));
        Gdx.input.setInputProcessor(this);

        current = Levels.LEVEL1.levelSpot;

        TextButton shopButton = new TextButton("Enter Shop",  new Skin(Gdx.files.internal("Data/uiskin.json")));
        shopButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                enterShop();
            }
        });
        shopButton.setPosition(20,12);
        addActor(shopButton);

        TextButton startButton = new TextButton("Start",  new Skin(Gdx.files.internal("Data/uiskin.json")));
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startLevel();
            }
        });
        startButton.setPosition(640-80,12);
        startButton.setWidth(60);
        addActor(startButton);

        //The initial position of the traveler
        moveToLevel(current);
    }

    public void moveToLevel(Spot currentSpot) {
        current = currentSpot;
        traveler.setPosition(currentSpot.getXPosition()*20, currentSpot.getYPosition()*20);
    }

    private void buildLevels() {
        levels = Levels.values();
    }

    //public int getCurrentLevel(){return current;}

    public void handleKey(int keycode){
        Spot auxiliarSpot = current;
        if(keycode == Input.Keys.Z){
        	musicplayer.StopMusic();
            startLevel();
            return;
        }
        else if(keycode == Input.Keys.X){
            enterShop();
            return;
        } else if(keycode == Input.Keys.UP){
            auxiliarSpot = current.UpMove();
        } else if(keycode == Input.Keys.DOWN){
            auxiliarSpot = current.DownMove();
        } else if(keycode == Input.Keys.LEFT){
            auxiliarSpot = current.LeftMove();
        } else if(keycode == Input.Keys.RIGHT) {
          auxiliarSpot = current.RightMove();
        }

        if(unlockedStages[auxiliarSpot.getLevel().ordinal()])
          current = auxiliarSpot;

        moveToLevel(current);
        /*int keynext = -1;
        int keyprev = -1;
        if(current > 0){
            if(levels[current].mapx > levels[current-1].mapx) keyprev = Input.Keys.LEFT;
            else if(levels[current].mapx < levels[current-1].mapx) keyprev = Input.Keys.RIGHT;
            else if(levels[current].mapy < levels[current-1].mapy) keyprev = Input.Keys.UP;
            else if(levels[current].mapy > levels[current-1].mapy) keyprev = Input.Keys.DOWN;
        }
        if(current < maxAllowed-1){
            if(levels[current].mapx > levels[current+1].mapx) keynext = Input.Keys.LEFT;
            else if(levels[current].mapx < levels[current+1].mapx) keynext = Input.Keys.RIGHT;
            else if(levels[current].mapy < levels[current+1].mapy) keynext = Input.Keys.UP;
            else if(levels[current].mapy > levels[current+1].mapy) keynext = Input.Keys.DOWN;
        }
        if(keycode == keyprev)
            prevLevel();
        else if(keycode == keynext)
            nextLevel();*/
    }

    private void enterShop() {
        myGame.setScreen(new ShopScreen(myGame));
    }

    public void startLevel(){
        GameScreen gameScreen = new GameScreen(myGame);
        if(!current.getLevel().bossLevel)
            gameScreen.setStage(new GameStage(new FitViewport(640,480), gameScreen, myGame, current.getLevel()));
        else
            gameScreen.setStage(new BossStage(new FitViewport(640,480), gameScreen, myGame, current.getLevel()));
        myGame.setScreen(gameScreen);
    }

}
