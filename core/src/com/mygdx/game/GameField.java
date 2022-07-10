package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;


public class GameField extends Game {
	SpriteBatch batch;
	Texture img;
	List<Bullet> ammo;
	private Texture dropImage;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	Player player;
	Bot bot;


	@Override
	public void create () { //загрузка всего перед запуском
		ammo = new ArrayList<>();
		batch = new SpriteBatch();
		bot = new Bot();
		dropImage = new Texture(Gdx.files.internal("drop.png"));
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		player = new Player();
		rainMusic.setLooping(true);
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1024, 768);
	}

	@Override
	public void render () { //отрабатывает 60 раз в секунду
		Vector2 vector2 = new Vector2(player.getX(), player.getY());
		ScreenUtils.clear(1f, 1f, 1f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		vector2.set(Gdx.input.getX() - player.getX(), Gdx.input.getY() - player.getY());
		batch.begin();
		player.update(batch, vector2);
		bot.update(batch, new Rectangle(player.getX(), player.getY(), 64 , 64));
		for (Bullet bullet: ammo){
			bullet.update(batch);
		}
		batch.end();

		if(Gdx.input.justTouched()){
			//Vector3 touchPos = new Vector3();
			//touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			ammo.add(new Bullet(player.getX(), player.getY(), Gdx.input.getX(), Gdx.input.getY()));
		}


		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			player.moveByKeys(0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.moveByKeys(1);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.moveByKeys(2);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.moveByKeys(3);
		}

		bulletsCheck();

	}
	
	@Override
	public void dispose () { //отрабатывает при закрытии программы
		batch.dispose();
		img.dispose();
		dropImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}

	private void bulletsCheck(){
		List<Integer> ints = new ArrayList();
		for (int i = 0; i < ammo.size(); i++) { //запоминаем индексы нужных нам элементов списка bots
			if (ammo.get(i).getRectange().overlaps(bot.getRectange()) || !ammo.get(i).isMoving) {
				ints.add(i);
			}
			if (ammo.get(i).getRectange().overlaps(bot.getRectange())) {
				bot.getDamage();
			}
		}
		for (int i = 0; i < ints.size(); i++) {
			int asd = ints.get(i);
			ammo.remove(asd);
			ints.clear();
		}
	}
}
