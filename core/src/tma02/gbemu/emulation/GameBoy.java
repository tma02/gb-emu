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

    public boolean loadCartridge(String fileName) {
        File file = new File(fileName);
        if (!file.isFile()) {
            return false;
        }
        this.cartridge = new Cartridge((int) file.length());
        try {
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            dataInputStream.read(this.cartridge.getRom(), 0, this.cartridge.getRom().length);
            dataInputStream.close();
            this.cartridge.setReady(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.cartridge.isReady();
    }
}
