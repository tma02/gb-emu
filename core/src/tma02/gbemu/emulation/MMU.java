package tma02.gbemu.emulation;

public class MMU {

    private GameBoy gameBoy;

    private byte[][] vram;
    private byte[][] wram;
    private byte[] oam;
    private byte[] io;
    private byte[] hram;
    private byte ie;

    private int vramBank;
    private int wramBank;

    public MMU(GameBoy gameBoy) {
        this.gameBoy = gameBoy;
        this.vram = new byte[2][0x2000];
        this.wram = new byte[8][0x1000];
        this.oam = new byte[0xA0];
        this.io = new byte[0x80];
        this.hram = new byte[0x7F];
        this.ie = 0;
        this.vramBank = 0;
        this.wramBank = 0;
    }

    public void init() {
        this.writeByte((short) 0xFF05, (byte) 0x00);
        this.writeByte((short) 0xFF06, (byte) 0x00);
        this.writeByte((short) 0xFF07, (byte) 0x00);
        this.writeByte((short) 0xFF10, (byte) 0x80);
        this.writeByte((short) 0xFF11, (byte) 0xBF);
        this.writeByte((short) 0xFF12, (byte) 0xF3);
        this.writeByte((short) 0xFF14, (byte) 0xBF);
        this.writeByte((short) 0xFF16, (byte) 0x3F);
        this.writeByte((short) 0xFF17, (byte) 0x00);
        this.writeByte((short) 0xFF19, (byte) 0xBF);
        this.writeByte((short) 0xFF1A, (byte) 0x7F);
        this.writeByte((short) 0xFF1B, (byte) 0xFF);
        this.writeByte((short) 0xFF1C, (byte) 0x9F);
        this.writeByte((short) 0xFF1E, (byte) 0xBF);
        this.writeByte((short) 0xFF20, (byte) 0xFF);
        this.writeByte((short) 0xFF21, (byte) 0x00);
        this.writeByte((short) 0xFF22, (byte) 0xFF);
        this.writeByte((short) 0xFF23, (byte) 0xBF);
        this.writeByte((short) 0xFF24, (byte) 0x77);
        this.writeByte((short) 0xFF25, (byte) 0xF3);
        this.writeByte((short) 0xFF26, (byte) 0xF1); //SGB 0xF0
        this.writeByte((short) 0xFF40, (byte) 0x91);
        this.writeByte((short) 0xFF42, (byte) 0x00);
        this.writeByte((short) 0xFF43, (byte) 0x00);
        this.writeByte((short) 0xFF45, (byte) 0x00);
        this.writeByte((short) 0xFF47, (byte) 0xFC);
        this.writeByte((short) 0xFF48, (byte) 0xFF);
        this.writeByte((short) 0xFF49, (byte) 0xFF);
        this.writeByte((short) 0xFF4A, (byte) 0x00);
        this.writeByte((short) 0xFF4B, (byte) 0x00);
        this.ie = 0;
    }

    public void setVramBank(int vramBank) {
        this.vramBank = vramBank;
    }

    public void setWramBank(int wramBank) {
        this.wramBank = wramBank;
    }

    public byte readByte(short word) {
        //System.out.println("Read: " + Integer.toHexString(word & 0xFFFF));
        int address = word & 0xFFFF;
        switch (address & 0xF000) {
            case 0x0000:
            case 0x1000:
            case 0x2000:
            case 0x3000:
                return this.gameBoy.getCartridge().getRom()[address];
            case 0x4000:
            case 0x5000:
            case 0x6000:
            case 0x7000:
                return this.gameBoy.getCartridge().getRom()[address];
            case 0x8000:
            case 0x9000:
                return this.vram[this.vramBank][address - 0x8000];
            case 0xA000:
            case 0xB000:
                return 0;
            case 0xC000:
                return this.wram[0][address - 0xC000];
            case 0xD000:
                return this.wram[this.wramBank][address - 0xD000];
            case 0xE000:
                return this.wram[0][address - 0xE000];
            case 0xF000:
                switch (address & 0x0F00) {
                    default:
                        return this.wram[0][address - 0xE000];
                    case 0x0E00:
                        return this.oam[address - 0xFE00];
                    case 0x0F00:
                        if ((address & 0x00F0) < 0x80) {
                            return this.io[address - 0xFF00];
                        }
                        else if (address != 0xFFFF) {
                            return this.hram[address - 0xFF80];
                        }
                        else {
                            return this.ie;
                        }
                }
        }
        return 0;
    }

    public void writeByte(short word, byte value) {
        //System.out.println("Wrote: " + Integer.toHexString(word & 0xFFFF));
        int address = word & 0xFFFF;
        switch (address & 0xF000) {
            case 0x0000:
            case 0x1000:
            case 0x2000:
            case 0x3000:
                break;
            case 0x4000:
            case 0x5000:
            case 0x6000:
            case 0x7000:
                break;
            case 0x8000:
            case 0x9000:
                this.vram[this.vramBank][address - 0x8000] = value;
                break;
            case 0xA000:
            case 0xB000:
                break;
            case 0xC000:
                this.wram[0][address - 0xC000] = value;
                break;
            case 0xD000:
                this.wram[this.wramBank][address - 0xD000] = value;
                break;
            case 0xE000:
                this.wram[0][address - 0xE000] = value;
                break;
            case 0xF000:
                switch (address & 0x0F00) {
                    default:
                        this.wram[0][address - 0xE000] = value;
                        break;
                    case 0x0E00:
                        this.oam[address - 0xFE00] = value;
                        break;
                    case 0x0F00:
                        if ((address & 0x00F0) < 0x80) {
                            this.io[address - 0xFF00] = value;
                        }
                        else if (address != 0xFFFF) {
                            this.hram[address - 0xFF80] = value;
                        }
                        else {
                            this.ie = value;
                        }
                }
        }
    }

}
