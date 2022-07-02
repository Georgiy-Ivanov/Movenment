package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player {
    private Texture bucketImage;
    private Rectangle bucket;
    Rectangle dest;
    private boolean isMoving;
    private float rastx;
    private float rasty;


    public Player() {
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        bucket = new Rectangle();
        bucket.x = 0;
        bucket.y = 0;
        bucket.width = 64;
        bucket.height = 64;
        isMoving = false;
        dest = new Rectangle(0f, 0f, 1f, 1f);
        rastx = 0f;
        rasty = 0f;
    }

    public void update(SpriteBatch batch){ //вызывается 60 раз в секунду
        if (bucket.overlaps(dest)) isMoving = false; //если достигает точки назначения - движение прекращается
        if (isMoving) {
            bucket.x += rastx;
            bucket.y += rasty;
        }
        if(bucket.x < 0) bucket.x = 0;
        if(bucket.x > 800 - 64) bucket.x = 800 - 64;
        batch.draw(bucketImage, bucket.x, bucket.y); //после всех вычислений - рисуем картинку
    }

    public void move(Vector3 vec){ //вызывается после нажатия на экран
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

    public Double dlina(Double x1, Double y1, Double x2, Double y2){
        Double x = Math.sqrt( Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) );
        return x;
    }
}
