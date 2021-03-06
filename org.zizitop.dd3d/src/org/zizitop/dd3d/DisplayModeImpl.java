package org.zizitop.dd3d;

import org.zizitop.pshell.utils.Lang;
import org.zizitop.pshell.window.DisplayMode;

import java.nio.Buffer;

/**
 * Implementation of a {@link DisplayMode}, that is used for resolution switching.
 * <br><br>
 * Created 27.08.18 12:22
 *
 * @author Zizitop
 */
public class DisplayModeImpl extends DisplayMode {
	public static final int BASE_WIDTH = 320, BASE_HEIGHT = 180, FPS = 60,
			BASE_VIEWPORT_WIDTH = 320, BASE_VIEWPORT_HEIGHT = 150;
	public static double VIEWPORT_ASPECT_X = BASE_VIEWPORT_WIDTH/(double)BASE_WIDTH,
						VIEWPORT_ASPECT_Y = BASE_VIEWPORT_HEIGHT/(double)BASE_HEIGHT;

	private static final DisplayModeImpl[] avalibleDisplayModes;

	static {
		int modesCount = 5;

		avalibleDisplayModes = new DisplayModeImpl[modesCount];

		int defW = BASE_WIDTH, defH = BASE_HEIGHT;

		//Size of all display modes - min size multiplied by integer
		for(int i = 0; i < modesCount; ++i) {
			avalibleDisplayModes[i] = new DisplayModeImpl(defW * (i + 1), defH * (i + 1), i);
		}
	}

	private final int width, height, id;
	private final String name;

	private DisplayModeImpl(int width, int height, int id) {
		this.width = width;
		this.height = height;
		this.id = id;

		name = String.format("%s %d, %s: %d, %s: %d", Lang.getText("displayMode"), id,
				Lang.getText("width"), width, Lang.getText("height"), height);
	}

	@Override
	public int getContentWidth() {
		return width;
	}

	@Override
	public int getContentHeight() {
		return height;
	}

	@Override
	public int getViewportWidth() {
		return (int) (width*VIEWPORT_ASPECT_X);
	}

	@Override
	public int getViewportHeight() {
		return (int) (height*VIEWPORT_ASPECT_Y);
	}

	@Override
	public int getBaseWidth() {
		return BASE_WIDTH;
	}

	@Override
	public int getBaseHeight() {
		return BASE_HEIGHT;
	}

	@Override
	public int getBaseViewportWidth() {
		return BASE_VIEWPORT_WIDTH;
	}

	@Override
	public int getBaseViewportHeight() {
		return BASE_VIEWPORT_HEIGHT;
	}

	@Override
	public double getFPS() {
		return FPS;
	}

	@Override
	public String toString() {
		return name;
	}

	public int getID() {
		return id;
	}

	/**
	 * Get avalible diplay mode
	 * @param mode - id. If id is too big or too small - {@link IllegalArgumentException}
	 * @return DisplayMode with this id
	 * @throws IllegalArgumentException
	 */
	public static DisplayMode getDisplayMode(int mode) {
		if(mode < 0 || mode >= avalibleDisplayModes.length) {
			throw new IllegalArgumentException("ID of a mode is out of a borders.");
		}

		return avalibleDisplayModes[mode];
	}

	/**
	 * Get copy of display modes list.
	 */
	public static DisplayModeImpl[] getAvalibleDisplayModes() {
		DisplayModeImpl[] ret = new DisplayModeImpl[avalibleDisplayModes.length];
		System.arraycopy(avalibleDisplayModes, 0, ret, 0, ret.length);

		return ret;
	}
}
