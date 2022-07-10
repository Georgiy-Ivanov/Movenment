package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Bot {
    private Texture bucketImage;
    private Texture bucketDamageImage;
    private Texture bucketDeadImage;
    private Rectangle bucket;
    private int timer = 0;
    Vector2 vector2;
    Rectangle dest;
    private boolean isMoving;
    private float rastx;
    private float rasty;
    TextureRegion texture;
    TextureRegion damageTexture;
    TextureRegion deadTexture;
    private boolean isAlive;
    private boolean isTakingDamage;

    private int hp;


    public Bot() {
        bucketImage = new Texture(Gdx.files.internal("bot.png"));
        bucketDamageImage = new Texture(Gdx.files.internal("bot_damage.png"));
        bucketDeadImage = new Texture(Gdx.files.internal("blood.png"));
        this.hp = 1000;
        bucket = new Rectangle();
        vector2 = new Vector2(0, 0);
        isAlive = true;
        bucket.x = 1024/2;
        bucket.y = 768/2;
        bucket.width = 64;
        bucket.height = 64;
        isMoving = false;
        dest = new Rectangle(0f, 0f, 1f, 1f);
        rastx = 0f;
        rasty = 0f;
        //x = 0;
        texture = new TextureRegion(bucketImage);
        damageTexture = new TextureRegion(bucketDamageImage);
        deadTexture = new TextureRegion(bucketDeadImage);

    }

    public void update(SpriteBatch batch, Rectangle player) { //вызывается 60 раз в секунду
        if (isAlive) {
            if (bucket.overlaps(player)) isMoving = false;
            timer++;
            if (timer > 60) {
                vector2.set(MathUtils.random(0, 768), MathUtils.random(0, 1024));
                moveByPoint(vector2);
                timer = 0;
            }
            if (bucket.overlaps(dest)) isMoving = false; //если достигает точки назначения - движение прекращается
            if (isMoving) {
                bucket.x += rastx;
                bucket.y += rasty;
            }
            if (bucket.x < 0) bucket.x = 0;
            if (bucket.x > 1024 - 64) bucket.x = 800 - 64;
            if (bucket.y < 0) bucket.y = 0;
            if (bucket.y > 768 - 64) bucket.y = 768 - 64;
            float x = vector2.angleDeg();
            if (isTakingDamage) {
                batch.draw(damageTexture, bucket.x, bucket.y, 32, 32, 64, 64, 1, 1, x);
                isTakingDamage = false;
            } else {
                batch.draw(texture, bucket.x, bucket.y, 32, 32, 64, 64, 1, 1, x);
            }
        } else {
            batch.draw(deadTexture, bucket.x, bucket.y, 32, 32, 64, 64, 1, 1, 0f);
        }
    }


    public void moveByPoint(Vector2 vec){ //вызывается после нажатия на экран
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

    public void getDamage(){
        this.hp -= 100;
        isTakingDamage = true;
        if(this.hp <= 0) isAlive = false;
    }
}
