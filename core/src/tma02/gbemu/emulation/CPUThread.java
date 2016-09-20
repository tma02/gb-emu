package tma02.gbemu.emulation;

public class CPUThread extends Thread {

    private CPU cpu;

    public CPUThread(CPU cpu) {
        this.cpu = cpu;
    }

    @Override
    public void run() {
        try {
            while (!this.cpu.isStopped()) {
                this.cpu.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
