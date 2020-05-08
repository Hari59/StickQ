package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class StickQuestGame extends ApplicationAdapter{
	public static SpriteBatch sb;
	private OrthographicCamera cam;
	private Player player;
	private float delta;
	private int dir = 2; //default char direction

	//map
	private TiledMap tileMap, collisionMap;
	private OrthogonalTiledMapRenderer tileMapRenderer, collisionMapRenderer;
	private TiledMapTileLayer collisionLayer;

	//bird stuff
	private Texture whitebirb;
	Array<Rectangle> birds;
	Rectangle playerbox;
	long lastEnemyTime;
	int level = 1;
	int enemiesEncountered = 0;
	boolean canMove = true;

	//controller and fonts
	Controller controller;
	BitmapFont font50;
	BitmapFont font100;

	public StickQuestGame(int level){
		this.level = level;
	}

	@Override
	public void create () {
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sb.setProjectionMatrix(cam.combined);


		//load map
		whitebirb = new Texture(Gdx.files.internal("whitebirb.png"));

		if(level==1) {
			tileMap = new TmxMapLoader().load("SQlvl1.tmx");
			collisionMap = new TmxMapLoader().load("collision.tmx");
		}
		if(level==2) {
			tileMap = new TmxMapLoader().load("SQlvl2.tmx");
			collisionMap = new TmxMapLoader().load("collisionlvl2.tmx");
		}
		if(level==3) {
			tileMap = new TmxMapLoader().load("SQlvl3.tmx");
			collisionMap = new TmxMapLoader().load("collisionlvl2.tmx");
		}
		tileMapRenderer = new OrthogonalTiledMapRenderer(tileMap);

		collisionMapRenderer = new OrthogonalTiledMapRenderer(collisionMap);
		collisionLayer = (TiledMapTileLayer) collisionMap.getLayers().get("Tile Layer 1");
		//load map

		player = new Player(collisionLayer);

		//player-bird collision
		playerbox = new Rectangle();
		playerbox.x = player.x;
		playerbox.y = player.y;
		playerbox.width=80;
		playerbox.height=80;

		birds = new Array<Rectangle>();
		spawnEnemy();

		controller = new Controller();

		//font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonttest.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		font50 = generator.generateFont(parameter);

		parameter.size = 100;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 7;
		font100 = generator.generateFont(parameter);
		generator.dispose();
	}

	private void spawnEnemy() {
		Rectangle enemy = new Rectangle();
		enemy.x = MathUtils.random(80, Gdx.graphics.getWidth()-120);
		enemy.y = Gdx.graphics.getHeight()-80;
		enemy.width = 69;
		enemy.height = 42;
		birds.add(enemy);
		lastEnemyTime = TimeUtils.nanoTime();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		delta = Gdx.graphics.getDeltaTime();

		checkMovement();
		checkEnd();

		//player-bird collision box
		playerbox.x = player.x;
		playerbox.y = player.y;

		//spawn birds
		if (TimeUtils.nanoTime() - lastEnemyTime > 300000000 && canMove)
			spawnEnemy();
		Iterator<Rectangle> iter = birds.iterator();
		while (iter.hasNext()) {
			Rectangle enemy = iter.next();
			enemy.y -= 400 * Gdx.graphics.getDeltaTime();
			if (enemy.y < 80) {
				iter.remove();
			}
			if (enemy.overlaps(playerbox)) {
				iter.remove();
				enemiesEncountered+=1;
			}

		}
		//spawn birds

		//rendering
		tileMapRenderer.setView(cam);
		tileMapRenderer.render();
		cam.update();
		controller.draw();
		sb.begin();
		player.render(sb, dir);
		for (Rectangle enemy : birds) {
			    sb.draw(whitebirb, enemy.x, enemy.y);
		}
		font50.draw(sb, "Enemies Encountered: "+ enemiesEncountered, 10, 1785);
		if(!canMove)
			font100.draw(sb, "Level Complete", 130, 900);
		sb.end();
		//rendering

	}

	public void checkMovement(){
		if (Gdx.input.isTouched()) {
			if (controller.isDownPressed() && !player.checkCollisionMap(player.x,player.y) && canMove) {
				player.y = player.y - 5;
				if(player.checkCollisionMap(player.x,player.y))
					player.y=player.y+5;
				dir = 1; //down
			}
			if (controller.isUpPressed() && !player.checkCollisionMap(player.x,player.y) && canMove) {
				player.y = player.y + 5;
				if(player.checkCollisionMap(player.x,player.y))
					player.y=player.y-5;
				dir = 2; //up
			}
			if (controller.isRightPressed() && !player.checkCollisionMap(player.x,player.y) && canMove) {
				player.x = player.x + 5;
				if(player.checkCollisionMap(player.x,player.y))
					player.x=player.x-5;
				dir = 3; //right
			}
			if (controller.isLeftPressed() && !player.checkCollisionMap(player.x,player.y) && canMove) {
				player.x = player.x - 5;
				if(player.checkCollisionMap(player.x,player.y))
					player.x=player.x+5;
				dir = 4; //left
			}
		}
	}

	public void checkEnd(){
		if(player.y > 1600)
			canMove = false;
	}
}