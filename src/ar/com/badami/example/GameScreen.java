package ar.com.badami.example;

import java.util.List;

import android.graphics.Color;
import ar.com.badami.framework.Game;
import ar.com.badami.framework.Graphics;
import ar.com.badami.framework.Input.TouchEvent;
import ar.com.badami.framework.Screen;

public class GameScreen extends Screen {
	enum GameState {
		Running,
		// Muestra la posibilidad de resumir o salir
		Paused,
	}

	GameState state = GameState.Running;
	int xPicCenter = 160;
	int yPicCenter = 240;

	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		if (state == GameState.Running) {
			updateRunning(touchEvents);
		} else if (state == GameState.Paused) {
			updatePaused(touchEvents);
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				state = GameState.Paused;
				Assets.click.play(1);
				return;
			}
			if (event.type == TouchEvent.TOUCH_DOWN || event.type == TouchEvent.TOUCH_DRAGGED) {
				xPicCenter = event.x;
				yPicCenter = event.y;
				return;
			}
		}

	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		// Verifica si tocó resume o quit
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.y < 240) {
					Assets.click.play(1);
					state = GameState.Running;
					return;
				} else {
					Assets.click.play(1);
					game.setScreen(new LoadingScreen(game));
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clear(Color.WHITE);

		if (state == GameState.Running) {
			drawRunningUI(g);
		} else if (state == GameState.Paused) {
			drawPausedUI(g);
		}
	}

	private void drawRunningUI(Graphics g) {
		g.drawPixmap(Assets.picture, xPicCenter - (Assets.picture.getWidth() / 2), yPicCenter - (Assets.picture.getHeight() / 2));
	}

	private void drawPausedUI(Graphics g) {
		drawRunningUI(g);
		g.drawLine(0, 240, 320, 240, Color.RED);
	}

	@Override
	public void pause() {
		// Es el metodo al que se llama si se pausa la activity
		if (state == GameState.Running)
			state = GameState.Paused;

		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
