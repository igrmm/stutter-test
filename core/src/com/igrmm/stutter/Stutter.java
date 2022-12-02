package com.igrmm.stutter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class Stutter extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Vector3 target;
	float speed = 240f;
	OrthographicCamera cam;
	BitmapFont font;
	float oldDelta = 0f;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("img.png");
		cam = new OrthographicCamera();
		font = new BitmapFont();
		target = new Vector3();
		cam.zoom = 1f / 32f;
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = width;
		cam.viewportHeight = height;
	}

	@Override
	public void render() {
//		float delta = Gdx.graphics.getDeltaTime();
		float delta = 1f / 60f;
		ScreenUtils.clear(1, 0, 0, 1);
		target.x += speed * delta;
		cam.position.lerp(target, 0.1f);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(img, target.x - 52f, target.y - 16f);
		font.draw(batch, "test", 0f, Gdx.graphics.getHeight() - 20f);
		batch.end();

		System.out.println((target.x - cam.position.x));
//		if (delta > 1f / 40f || delta < 1f / 80f) {
//			System.out.println(oldDelta + " " + delta + " " + (oldDelta - delta));
//			oldDelta = delta;
//		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		font.dispose();
	}
}
