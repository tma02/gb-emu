package tma02.gbemu.emulation;

import java.io.*;

public class GameBoy {

    private CPU cpu;
    private MMU mmu;
    private Cartridge cartridge;

    public GameBoy() {
        this.cpu = new CPU(this);
        this.mmu = new MMU(this);
    }

    public void init() {
        try {
            this.cpu.init();
            this.mmu.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CPU getCpu() {
        return cpu;
    }

    public MMU getMmu() {
        return mmu;
    }

    public Cartridge getCartridge() {
        return this.cartridge;
    }

    public void loadCartridge(String fileName) {
        File file = new File(fileName);
        this.cartridge = new Cartridge((int) file.length());
        try {
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            dataInputStream.read(this.cartridge.getRom(), 0, this.cartridge.getRom().length);
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.cartridge.setReady(true);
    }
}
