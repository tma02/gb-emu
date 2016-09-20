package tma02.gbemu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import tma02.gbemu.emulation.CPU;
import tma02.gbemu.emulation.CPUThread;
import tma02.gbemu.emulation.GameBoy;

public class GBEmu extends ApplicationAdapter {

	public static int SCALE = 5;
	private ShapeRenderer shapeRenderer;
	
	@Override
	public void create () {
		this.resize(160 * SCALE, 144 * SCALE);
		this.shapeRenderer = new ShapeRenderer();
		GameBoy gameBoy = new GameBoy();
		gameBoy.loadCartridge("ROMS/tetris.gb");
		try {
			gameBoy.init();
			//test registers
			/*gameBoy.getCpu().setRegister(CPU.Register.AF, (short) 0xAA00);
			System.out.println(Integer.toHexString(gameBoy.getCpu().getRegister(CPU.Register.A) & 0xFF));
			System.out.println(Integer.toHexString(gameBoy.getCpu().getRegister(CPU.Register.F) & 0xFF));*/
			//test flags
			/*gameBoy.getCpu().setFlag(CPU.Flag.Z, true);
			gameBoy.getCpu().setFlag(CPU.Flag.C, true);
			System.out.println(Integer.toHexString(gameBoy.getCpu().getRegister(CPU.Register.F) & 0xFF));*/
			new CPUThread(gameBoy.getCpu()).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(0, 0, 160 * SCALE, 144 * SCALE);
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
