package tma02.gbemu.emulation;

public class Tile {

    private byte[][] colorData;

    public Tile(byte[] bytes) {
        char[][] bin = new char[16][8];
        this.colorData = new byte[8][8];
        for (int i = 0; i < 16; i++) {
            //http://stackoverflow.com/questions/12310017/how-to-convert-a-byte-to-its-binary-string-representation
            bin[i] = String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0').toCharArray();
            System.out.println(Integer.toBinaryString(bytes[i] & 0xFF));
            for (int j = 0; j < 8; j++) {
                bin[i][j] -= '0';
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                byte color = (byte) bin[i*2][j];
                color += bin[(i*2)+1][j] << 1;
                this.colorData[i][j] = color;
            }
        }
    }

    public byte[][] getColorData() {
        return this.colorData;
    }

    public static Tile getTile(MMU mmu, short address) {
        byte[] bytes = new byte[16];
        for (int i = 0; i < 16; i++) {
            bytes[i] = mmu.readByte((short) (address + i));
        }
        return new Tile(bytes);
    }

}
