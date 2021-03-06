package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Texture bucketImage;
    private Rectangle bucket;
    Rectangle dest;
    private boolean isMoving;
    private float rastx;
    private float rasty;
    TextureRegion texture;



    public Player() {

        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        bucket = new Rectangle();
        bucket.x = 1024/4;
        bucket.y = 768/4;
        bucket.width = 64;
        bucket.height = 64;
        isMoving = false;
        dest = new Rectangle(0f, 0f, 1f, 1f);
        rastx = 0f;
        rasty = 0f;
        //x = 0;
        texture = new TextureRegion(bucketImage);

    }

    public void update(SpriteBatch batch, Vector2 vector2){ //вызывается 60 раз в секунду
        if (bucket.overlaps(dest)) isMoving = false; //если достигает точки назначения - движение прекращается
        if (isMoving) {
            bucket.x += rastx;
            bucket.y += rasty;
        }

        if(bucket.x < 0) bucket.x = 0;
        if(bucket.x > 1024 - 64) bucket.x = 1024 - 64;
        if (bucket.y < 0) bucket.y = 0;
        if (bucket.y > 768 - 64) bucket.y = 768 - 64;
        float x = vector2.angleDeg();

        batch.draw(texture, bucket.x, bucket.y, 32, 32, 64, 64, 1, 1, x);
    }

    public void moveByPoint(Vector3 vec){ //вызывается после нажатия на экран
        Double x1 = Double.valueOf(bucket.x + 32); //сохраняем х координату игрока
        Double y1 = Double.valueOf(bucket.y + 32); //сохраняем y координату игрока
        Double x2 = Double.valueOf(vec.x); //сохраняем х координату назначения
        Double y2 = Double.valueOf(vec.y); //сохраняем y координату назначения
        Double X = dlina(x1, y1, x2, y2); //вычисляем длину отрезка который нужно пройти
        rastx = (float) ((x2-x1)/X) * 5 ; //вычисляем скорость изменения координаты Х для достижения цели
        rasty = (float) ((y2-y1)/X) * 5; //вычисляем скорость изменения координаты Y для достижения цели
        dest.x = vec.x; //обновляем ректенгл для упрощения проверки достижения цели
        dest.y = vec.y; //обновляем ректенгл для упрощения проверки достижения цели
        isMoving = true; //разрешаем движение
    }

    public void moveByKeys(int direction){
        if (direction == 0) bucket.x = bucket.x - 400 * Gdx.graphics.getDeltaTime();
        if (direction == 1) bucket.y = bucket.y - 400 * Gdx.graphics.getDeltaTime();
        if (direction == 2) bucket.y = bucket.y + 400 * Gdx.graphics.getDeltaTime();
        if (direction == 3) bucket.x = bucket.x + 400 * Gdx.graphics.getDeltaTime();
    }

    public Double dlina(Double x1, Double y1, Double x2, Double y2){
        Double x = Math.sqrt( Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) );
        return x;
    }

    public float getX(){
        return bucket.x;
    }

    public float getY(){
        return bucket.y;
    }

    public void setX(float x) {bucket.x = x;}
    public void setY(float y) {bucket.y = y;}

    public Rectangle getRectange(){
        return bucket;
    }

}
