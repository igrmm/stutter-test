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
	float accumulator;
	float deltaBuffer;
	float oldDelta;
	float delta;

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
		smoothDeltaTime();

		ScreenUtils.clear(1, 0, 0, 1);
		cam.position.lerp(target, 0.1f);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(img, target.x - 52f, target.y - 16f);
//		font.draw(batch, "" + (target.x - cam.position.x), 0f, Gdx.graphics.getHeight() / 2f);
		batch.end();
	}

	public void smoothDeltaTime() {
		delta += deltaBuffer;
		oldDelta = delta;
		delta = Gdx.graphics.getDeltaTime();
		deltaBuffer = oldDelta - delta;
		target.x += speed * Math.abs(delta);
	}

	public void fixedTimeStep() {
		float delta = 1f / 60f;
		accumulator += delta;
		while (accumulator >= 1.0 / 60.0) {
			target.x += speed * delta;
			accumulator -= 1.0 / 60.0;
		}
	}

	public void variableTimeStep() {
		float delta = Gdx.graphics.getDeltaTime();
		target.x += speed * delta;
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		font.dispose();
	}
}
