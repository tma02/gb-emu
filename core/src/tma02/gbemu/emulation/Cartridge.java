package tma02.gbemu.emulation;

public class Cartridge {

    private byte[] rom;
    private String title;
    private boolean ready;
    private byte type;

    public Cartridge(int size) {
        this.rom = new byte[size];
        this.ready = false;
    }

    public byte[] getRom() {
        return this.rom;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
        if (ready) {
            System.out.println("ROM Ready");
            byte[] title = new byte[11];
            System.arraycopy(this.rom, 0x134, title, 0, 11);
            this.title = new String(title);
            System.out.println("Title: " + this.title);
            this.type = this.rom[0x147];
            System.out.println("Cartridge Type: " + Integer.toHexString(this.type & 0xFF));
        }
    }

    public byte getType() {
        return this.type;
    }
}
