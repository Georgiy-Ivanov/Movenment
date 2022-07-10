package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bullet {
    private Rectangle bullet;
    private final int speed = 20;
    private float rastx;
    private float rasty;
    private Rectangle dest;
    private Texture bulletImg;
    private float x2;
    private float y2;

    public boolean isMoving;
    public Bullet(float x1, float y1, float x2, float y2){
        bullet = new Rectangle();
        bullet.x = x1;
        bullet.y = y1;
        this.x2 = x2;
        this.y2 = y2;
        isMoving = true;
        rastx = x2;
        rasty = y2;
        dest = new Rectangle(0f, 0f, 2f, 2f);
        bulletImg = new Texture(Gdx.files.internal("bullet.png"));
        move();
    }

    public void update(SpriteBatch batch){
        if (bullet.x > 1920 || bullet.x < -10){
            isMoving = false;
            bullet.x = -5;
            bullet. y = -5;
        }
        if (bullet.y > 1080 || bullet.y < -10) {
            isMoving = false;
            bullet.x = -5;
            bullet. y = -5;
        }
        if (isMoving) {
            bullet.x += rastx;
            bullet.y += rasty;
        }
        batch.draw(bulletImg, bullet.x, bullet.y);
    }

    public void move(){ //вызывается после нажатия на экран
        y2 -= 32;
        x2 -= 32;
        float X = dlina(bullet.x, bullet.y, x2, y2); //вычисляем длину отрезка который нужно пройти
        bullet.x = bullet.x+32;
        bullet.y = bullet.y+32;
        rastx = ((x2-bullet.x)/X) * speed ; //вычисляем скорость изменения координаты Х для достижения цели
        rasty = ((y2-bullet.y)/X) * speed; //вычисляем скорость изменения координаты Y для достижения цели
    }
    public float dlina(float x1, float y1, float x2, float y2){
        float x = (float) Math.sqrt( Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) );
        return x;
    }

    public Rectangle getRectange(){
        return bullet;
    }
}
