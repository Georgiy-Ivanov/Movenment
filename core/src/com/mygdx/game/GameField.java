package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
		camera.setToOrtho(true, 800, 480);
	}

	@Override
	public void render () { //отрабатывает 60 раз в секунду
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player.update(batch);
		batch.end();
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			//bucketPos.set(bucket.x, bucket.y, 0);
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			player.move(touchPos);
			camera.unproject(touchPos);
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
