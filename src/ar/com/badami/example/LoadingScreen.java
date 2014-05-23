package ar.com.badami.example;

import ar.com.badami.framework.Game;
import ar.com.badami.framework.Graphics;
import ar.com.badami.framework.Graphics.PixmapFormat;
import ar.com.badami.framework.Screen;

public class LoadingScreen extends Screen {
	public LoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		// Carga las imagenes y sonidos
		// El UI Thread sigue bloqueado
		// Se podría hacer esto en otro thread y en el metodo present dibujar
		// algo
		Graphics g = game.getGraphics();
		Assets.picture = g.newPixmap("picture.png", PixmapFormat.RGB565);
		Assets.click = game.getAudio().newSound("click.ogg");
		// Carga las settings
		Settings.load(game.getFileIO());
		game.setScreen(new GameScreen(game));
	}

	@Override
	public void present(float deltaTime) {
		// Mientras se van cargando las imagenes se podría mostrar algo
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
