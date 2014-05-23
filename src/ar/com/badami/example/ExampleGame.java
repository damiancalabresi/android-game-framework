package ar.com.badami.example;

import ar.com.badami.framework.Screen;
import ar.com.badami.framework.impl.AndroidGame;

public class ExampleGame extends AndroidGame {
	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
}
