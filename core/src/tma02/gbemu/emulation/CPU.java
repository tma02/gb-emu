package tma02.gbemu.emulation;

public class CPU {

    public static final double CLOCK_HZ = 4194304;

    private GameBoy gameBoy;
    private InstructionHandler instructionHandler;
    private long lastCycle;
    private long lastSecond;
    private float cycles;
    private byte[] registers;
    private boolean stopped;

    public CPU(GameBoy gameBoy) {
        this.gameBoy = gameBoy;
        this.registers = new byte[12];
        this.lastCycle = 0;
        this.lastSecond = 0;
        this.cycles = 0;
        this.stopped = false;
        this.instructionHandler = new InstructionHandler(gameBoy);
    }

    public void init() throws Exception {
        this.setRegister(Register.PC, (short) 0x0100);
        this.setRegister(Register.SP, (short) 0xFFFE);
    }

    public int step() throws Exception {
        byte instruction = this.gameBoy.getMmu().readByte(this.getDoubleRegister(Register.PC));
        this.instructionHandler.INC(Register.PC);
        return this.instructionHandler.handleInstruction(instruction);
    }

    public void run() throws Exception {
        long delta = System.nanoTime() - lastCycle;
        int cyclesTaken = this.step();
        cycles += cyclesTaken;
        if (System.currentTimeMillis() - lastSecond >= 1000) {
            System.out.println("cycles this second: " + cycles);
            cycles = 0;
            lastSecond = System.currentTimeMillis();
        }
        lastCycle = System.nanoTime();
    }

    public byte getRegister(Register register) throws Exception {
        if (register.isByte()) {
            return this.registers[register.getAddress()];
        }
        else {
            throw new Exception("Attempted to get 16 bit register through getRegister()");
        }
    }

    public void setRegister(Register register, byte value) {
        if (register.isByte()) {
            this.registers[register.getAddress()] = value;
        }
        else {
            this.registers[register.getAddress() + 1] = value;
        }
    }

    public void setRegister(Register register, short value) throws Exception {
        if (!register.isByte()) {
            this.registers[register.getAddress()] = (byte) (value >> 8);
            this.registers[register.getAddress() + 1] = (byte) (value & 0xFF);
        }
        else {
            throw new Exception("Attempted to 16 bit value to byte register");
        }
    }

    public short getDoubleRegister(Register register) throws Exception {
        if (!register.isByte()) {
            short value;
            value = (short) (this.registers[register.getAddress()] << 8);
            value += this.registers[register.getAddress() + 1];
            return value;
        }
        else {
            throw new Exception("Attempted to get 8 bit register through getDoubleRegister()");
        }
    }

    public boolean getFlag(Flag flag) {
        return (this.registers[Register.F.getAddress()] & flag.getMask()) != 0;
    }

    public void setFlag(Flag flag, boolean value) {
        byte f = this.registers[Register.F.getAddress()];
        f |= flag.getMask();
        f &= 0xF0;
        this.registers[Register.F.getAddress()] = f;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public enum Register {
        A(true, 0),
        B(true, 2),
        D(true, 4),
        H(true, 6),
        F(true, 1),
        C(true, 3),
        E(true, 5),
        L(true, 7),
        SP(false, 8),
        PC(false, 10),
        AF(false, 0),
        BC(false, 2),
        DE(false, 4),
        HL(false, 6);

        private boolean isByte;
        private int address;

        Register(boolean isByte, int address) {
            this.isByte = isByte;
            this.address = address;
        }

        public boolean isByte() {
            return this.isByte;
        }

        public int getAddress() {
            return this.address;
        }
    }

    public enum Flag {
        Z(7, 0x80),
        N(6, 0x40),
        H(5, 0x20),
        C(4, 0x10);

        private int address;
        private int mask;

        Flag(int address, int mask) {
            this.address = address;
            this.mask = mask;
        }

        public int getAddress() {
            return this.address;
        }

        public int getMask() {
            return this.mask;
        }
    }

}
