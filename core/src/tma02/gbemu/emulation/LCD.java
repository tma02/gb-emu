package tma02.gbemu.emulation;

public class LCD {

    private GameBoy gameBoy;

    private boolean enable; //(0=Off, 1=On)
    private boolean tileMapDisplaySelect; //(0=9800-9BFF, 1=9C00-9FFF)
    private boolean windowEnable; //(0=Off, 1=On)
    private boolean windowTileDataSelect; //(0=8800-97FF, 1=8000-8FFF)
    private boolean bgTileMapSelect; //(0=9800-9BFF, 1=9C00-9FFF)
    private boolean spriteSize; //(0=8x8, 1=8x16)
    private boolean spriteDisplayEnable; //(0=Off, 1=On)
    private boolean bgDisplay; //(0=Off, 1=On)

    public LCD(GameBoy gameBoy) {
        this.gameBoy = gameBoy;
        this.enable = false;
        this.tileMapDisplaySelect = false;
        this.windowEnable = false;
        this.windowTileDataSelect = false;
        this.bgTileMapSelect = false;
        this.spriteSize = false;
        this.spriteDisplayEnable = false;
        this.bgDisplay = false;
    }

    public void pollLCDC() {
        byte lcdc = this.gameBoy.getMmu().readByte((short) 0xFF40);
        this.enable = (lcdc & 0x80) != 0;
        this.tileMapDisplaySelect = (lcdc & 0x40) != 0;
        this.windowEnable = (lcdc & 0x20) != 0;
        this.windowTileDataSelect = (lcdc & 0x10) != 0;
        this.bgTileMapSelect = (lcdc & 0x08) != 0;
        this.spriteSize = (lcdc & 0x04) != 0;
        this.spriteDisplayEnable = (lcdc & 0x02) != 0;
        this.bgDisplay = (lcdc & 0x01) != 0;
    }

}
