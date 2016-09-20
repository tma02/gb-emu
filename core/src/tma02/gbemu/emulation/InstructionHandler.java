package tma02.gbemu.emulation;

public class InstructionHandler {

    private GameBoy gameBoy;

    public InstructionHandler(GameBoy gameBoy) {
        this.gameBoy = gameBoy;
    }

    public short getD16() throws Exception {
        short d16;
        this.INC(CPU.Register.PC);
        d16 = (short) (this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getRegister(CPU.Register.PC)) << 8);
        this.INC(CPU.Register.PC);
        d16 += this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getRegister(CPU.Register.PC));
        return d16;
    }

    public byte getD8() throws Exception {
        return this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getRegister(CPU.Register.PC));
    }

    public int handleInstruction(byte instruction) {
        try {
            switch (instruction) {
                case 0x00:
                    return 4;
                case 0x01:
                    this.LD(CPU.Register.BC, this.getD16());
                    return 12;
                case 0x02:
                    this.LD(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.BC), CPU.Register.A);
                    return 8;
                case 0x03:
                    this.INC(CPU.Register.BC);
                    return 8;
                case 0x04:
                    this.INC(CPU.Register.B);
                    return 4;
                case 0x05:
                    this.DEC(CPU.Register.B);
                    return 4;
                case 0x06:
                    this.LD(CPU.Register.B, this.getD8());
                    return 8;
                case 0x07:
                    this.RLCA();
                    return 4;
                case 0x08:
                    this.LD(this.getD16(), CPU.Register.SP);
                    return 20;
                case 0x09:
                    this.ADD(CPU.Register.HL, CPU.Register.BC);
                    return 8;
                case 0x0A:
                    this.LD(CPU.Register.A, this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.BC)));
                    return 8;
                case 0x0B:
                    this.DEC(CPU.Register.BC);
                    return 8;
                case 0x0C:
                    this.INC(CPU.Register.C);
                    return 4;
                case 0x0D:
                    this.DEC(CPU.Register.C);
                    return 4;
                case 0x0E:
                    this.LD(CPU.Register.C, this.getD8());
                    return 4;
                case 0x0F:
                    this.RRCA();
                    return 4;
                case 0x10:
                    this.STOP();
                    return 4;
                case 0x11:
                    this.LD(CPU.Register.DE, this.getD16());
                    return 12;
                case 0x12:
                    this.LD(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.DE), CPU.Register.A);
                    return 8;
                case 0x13:
                    this.INC(CPU.Register.DE);
                    return 8;
                case 0x14:
                    this.INC(CPU.Register.D);
                    return 4;
                case 0x15:
                    this.DEC(CPU.Register.D);
                    return 4;
                case 0x16:
                    this.LD(CPU.Register.D, this.getD8());
                    return 8;
                case 0x17:
                    this.RLA();
                    return 4;
                case 0x18:
                    this.JR(this.getD8());
                    return 12;
                case 0x19:
                    this.ADD(CPU.Register.HL, CPU.Register.DE);
                    return 8;
                case 0x1A:
                    this.LD(CPU.Register.A, this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.DE)));
                    return 8;
                case 0x1B:
                    this.DEC(CPU.Register.DE);
                    return 8;
                case 0x1C:
                    this.INC(CPU.Register.E);
                    return 4;
                case 0x1D:
                    this.DEC(CPU.Register.E);
                    return 4;
                case 0x1E:
                    this.LD(CPU.Register.E, this.getD8());
                    return 8;
                case 0x1F:
                    this.RRA();
                    return 4;
                case 0x20:
                    if (!this.gameBoy.getCpu().getFlag(CPU.Flag.Z)) {
                        this.JR(this.getD8());
                        return 12;
                    }
                    return 8;
                case 0x21:
                    this.LD(CPU.Register.HL, this.getD16());
                    return 12;
                case 0x22:
                    this.LD(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL), CPU.Register.A);
                    this.INC(CPU.Register.HL);
                    return 8;
                case 0x23:
                    this.INC(CPU.Register.HL);
                    return 8;
                case 0x24:
                    this.INC(CPU.Register.H);
                    return 4;
                case 0x25:
                    this.DEC(CPU.Register.H);
                    return 4;
                case 0x26:
                    this.LD(CPU.Register.H, this.getD8());
                    return 8;
                default:
                    System.out.println("Unknown instruction: " + Integer.toHexString(instruction & 0xFF));
                    return 4;
            }
        }
        catch(Exception ignored) {}
        return 4;
    }

    public void LD(CPU.Register register, short value) {
        try {
            this.gameBoy.getCpu().setRegister(register, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LD(CPU.Register toRegister, CPU.Register fromRegister) {
        try {
            if (fromRegister.isByte()) {
                this.gameBoy.getCpu().setRegister(toRegister, this.gameBoy.getCpu().getRegister(fromRegister));
            }
            else {
                this.gameBoy.getCpu().setRegister(toRegister, this.gameBoy.getCpu().getDoubleRegister(fromRegister));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LD(int address, CPU.Register register) {
        try {
            if (register.isByte()) {
                this.gameBoy.getMmu().writeByte(address, this.gameBoy.getCpu().getRegister(register));
            }
            else {
                short value = this.gameBoy.getCpu().getDoubleRegister(register);
                this.gameBoy.getMmu().writeByte(address, (byte) ((value >> 8) & 0xFF));
                this.gameBoy.getMmu().writeByte(address + 1, (byte) (value & 0xFF));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void INC(CPU.Register register) {
        try {
            if (register.isByte()) {
                byte before = this.gameBoy.getCpu().getRegister(register);
                byte after = (byte) (before + 1);
                this.gameBoy.getCpu().setRegister(register, after);
                boolean h = (before & 0xF0) != (after & 0xF0);
                this.gameBoy.getCpu().setFlag(CPU.Flag.Z, this.gameBoy.getCpu().getRegister(CPU.Register.B) == 0);
                this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
                this.gameBoy.getCpu().setFlag(CPU.Flag.H, h);
            }
            else {
                this.gameBoy.getCpu().setRegister(register, (short) (this.gameBoy.getCpu().getDoubleRegister(register) + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean DEC(CPU.Register register) {
        try {
            if (register.isByte()) {
                byte before = this.gameBoy.getCpu().getRegister(register);
                byte after = (byte) (before - 1);
                this.gameBoy.getCpu().setRegister(register, after);
                boolean h = (before & 0xF0) != (after & 0xF0);
                this.gameBoy.getCpu().setFlag(CPU.Flag.Z, this.gameBoy.getCpu().getRegister(CPU.Register.B) == 0);
                this.gameBoy.getCpu().setFlag(CPU.Flag.N, true);
                this.gameBoy.getCpu().setFlag(CPU.Flag.H, h);
            }
            else {
                this.gameBoy.getCpu().setRegister(register, (short) (this.gameBoy.getCpu().getDoubleRegister(register) - 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void RLCA() throws Exception {
        byte a = this.gameBoy.getCpu().getRegister(CPU.Register.A);
        boolean bit7 = ((a >> 7) & 0x01) == 1;
        a = (byte) (a << (8 - 1));
        this.gameBoy.getCpu().setFlag(CPU.Flag.Z, a == 0);
        this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.C, bit7);
    }

    public void ADD(CPU.Register toRegister, CPU.Register fromRegister) {
        try {
            if (fromRegister.isByte()) {
                byte from = this.gameBoy.getCpu().getRegister(fromRegister);
                byte to = this.gameBoy.getCpu().getRegister(toRegister);
                boolean c = (((from + to) >> 8) & 1) == 1;
                boolean h = (to & 0xF0) != ((from + to) & 0xF0);
                this.gameBoy.getCpu().setRegister(toRegister, (byte) (from + to));
                this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
                this.gameBoy.getCpu().setFlag(CPU.Flag.H, h);
                this.gameBoy.getCpu().setFlag(CPU.Flag.C, c);
            }
            else {
                short from = this.gameBoy.getCpu().getDoubleRegister(fromRegister);
                short to = this.gameBoy.getCpu().getDoubleRegister(toRegister);
                boolean c = (((from + to) >> 16) & 1) == 1;
                boolean h = (to & 0xF000) != ((from + to) & 0xF000);
                this.gameBoy.getCpu().setRegister(toRegister, (short) (from + to));
                this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
                this.gameBoy.getCpu().setFlag(CPU.Flag.H, h);
                this.gameBoy.getCpu().setFlag(CPU.Flag.C, c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void RRCA() throws Exception {
        byte a = this.gameBoy.getCpu().getRegister(CPU.Register.A);
        boolean bit0 = (a & 0x01) == 1;
        a = (byte) (a >>> 1);
        this.gameBoy.getCpu().setFlag(CPU.Flag.Z, a == 0);
        this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.C, bit0);
    }

    public void STOP() {
        this.gameBoy.getCpu().setStopped(true);
    }

    public void RLA() throws Exception {
        byte a = this.gameBoy.getCpu().getRegister(CPU.Register.A);
        boolean bit7 = ((a >> 7) & 0x01) == 1;
        a = (byte) (a << 1);
        a += this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0;
        this.gameBoy.getCpu().setFlag(CPU.Flag.Z, a == 0);
        this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.C, bit7);
    }

    public void JR(byte r8) throws Exception {
        this.gameBoy.getCpu().setRegister(CPU.Register.PC, (short) (this.gameBoy.getCpu().getDoubleRegister(CPU.Register.PC) + r8));
    }

    public void RRA() throws Exception {
        byte a = this.gameBoy.getCpu().getRegister(CPU.Register.A);
        boolean bit0 = (a & 0x01) == 1;
        a = (byte) (a >> 1);
        a += this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 0x80 : 0;
        this.gameBoy.getCpu().setFlag(CPU.Flag.Z, a == 0);
        this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.C, bit0);
    }

}
