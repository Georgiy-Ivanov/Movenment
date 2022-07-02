package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;




public class GameField extends Game {
	SpriteBatch batch;
	Texture img;

	private Texture dropImage;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	Player player;

	@Override
	public void create () { //загрузка всего перед запуском
		batch = new SpriteBatch();
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
		batch.end();
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			player.move(touchPos);
		}
	}
	
	@Override
	public void dispose () { //отрабатывает при закрытии программы
		batch.dispose();
		img.dispose();
		dropImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}
}
