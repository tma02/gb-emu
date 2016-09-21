package tma02.gbemu.emulation;

public class InstructionHandler {

    private GameBoy gameBoy;

    public InstructionHandler(GameBoy gameBoy) {
        this.gameBoy = gameBoy;
    }

    public short getD16() throws Exception {
        int d16 = 0;
        d16 += this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.PC)) & 0xFF;
        this.INC(CPU.Register.PC);
        d16 += this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.PC)) << 8;
        this.INC(CPU.Register.PC);
        return (short) d16;
    }

    public byte getD8() throws Exception {
        int d8 = this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.PC));
        this.INC(CPU.Register.PC);
        return (byte) (d8 & 0xFF);
    }

    /**
     * Handles the instruction given
     * @param instruction
     * @return CPU cycles taken by the instruction
     */
    public int handleInstruction(byte instruction) {
        System.out.println("Instruction: " + Integer.toHexString(instruction & 0xFF));
        try {
            byte d8;
            short d16;
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
                    d8 = this.getD8();
                    if (!this.gameBoy.getCpu().getFlag(CPU.Flag.Z)) {
                        this.JR(d8);
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
                case 0x27:
                    this.DAA();
                    return 4;
                case 0x28:
                    d8 = this.getD8();
                    if (this.gameBoy.getCpu().getFlag(CPU.Flag.Z)) {
                        this.JR(d8);
                        return 12;
                    }
                    return 8;
                case 0x29:
                    this.ADD(CPU.Register.HL, CPU.Register.HL);
                    return 8;
                case 0x2A:
                    this.LD(CPU.Register.A, this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    this.INC(CPU.Register.HL);
                    return 8;
                case 0x2B:
                    this.DEC(CPU.Register.HL);
                    return 8;
                case 0x2C:
                    this.INC(CPU.Register.L);
                    return 4;
                case 0x2D:
                    this.DEC(CPU.Register.L);
                    return 4;
                case 0x2E:
                    this.LD(CPU.Register.L, this.getD8());
                    return 8;
                case 0x2F:
                    this.CPL();
                    return 4;
                case 0x30:
                    d8 = this.getD8();
                    if (!this.gameBoy.getCpu().getFlag(CPU.Flag.C)) {
                        this.JR(d8);
                        return 12;
                    }
                    return 8;
                case 0x31:
                    this.LD(CPU.Register.SP, this.getD16());
                    return 12;
                case 0x32:
                    this.LD(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL), CPU.Register.A);
                    this.DEC(CPU.Register.HL);
                    return 8;
                case 0x33:
                    this.INC(CPU.Register.SP);
                    return 8;
                case 0x34:
                    this.INC(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL));
                    return 12;
                case 0x35:
                    this.DEC(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL));
                    return 12;
                case 0x36:
                    this.LD(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL), this.getD8());
                    return 12;
                case 0x37:
                    this.SCF();
                    return 4;
                case 0x38:
                    d8 = this.getD8();
                    if (this.gameBoy.getCpu().getFlag(CPU.Flag.C)) {
                        this.JR(d8);
                        return 12;
                    }
                    return 8;
                case 0x39:
                    this.ADD(CPU.Register.HL, CPU.Register.SP);
                    return 8;
                case 0x3A:
                    this.LD(CPU.Register.A, this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    this.DEC(CPU.Register.HL);
                    return 8;
                case 0x3B:
                    this.DEC(CPU.Register.SP);
                    return 8;
                case 0x3C:
                    this.INC(CPU.Register.A);
                    return 4;
                case 0x3D:
                    this.DEC(CPU.Register.A);
                    return 4;
                case 0x3E:
                    this.LD(CPU.Register.A, this.getD8());
                    return 8;
                case 0x3F:
                    this.CCF();
                    return 4;
                case 0x40:
                    this.LD(CPU.Register.B, CPU.Register.B);
                    return 4;
                case 0x41:
                    this.LD(CPU.Register.B, CPU.Register.C);
                    return 4;
                case 0x42:
                    this.LD(CPU.Register.B, CPU.Register.D);
                    return 4;
                case 0x43:
                    this.LD(CPU.Register.B, CPU.Register.E);
                    return 4;
                case 0x44:
                    this.LD(CPU.Register.B, CPU.Register.H);
                    return 4;
                case 0x45:
                    this.LD(CPU.Register.B, CPU.Register.L);
                    return 4;
                case 0x46:
                    this.LD(CPU.Register.B, this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 8;
                case 0x47:
                    this.LD(CPU.Register.B, CPU.Register.A);
                    return 4;
                case 0x48:
                    this.LD(CPU.Register.C, CPU.Register.B);
                    return 4;
                case 0x49:
                    this.LD(CPU.Register.C, CPU.Register.C);
                    return 4;
                case 0x4A:
                    this.LD(CPU.Register.C, CPU.Register.D);
                    return 4;
                case 0x4B:
                    this.LD(CPU.Register.C, CPU.Register.E);
                    return 4;
                case 0x4C:
                    this.LD(CPU.Register.C, CPU.Register.H);
                    return 4;
                case 0x4D:
                    this.LD(CPU.Register.C, CPU.Register.L);
                    return 4;
                case 0x4E:
                    this.LD(CPU.Register.C, this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 4;
                case 0x4F:
                    this.LD(CPU.Register.C, CPU.Register.A);
                    return 4;
                case 0x50:
                    this.LD(CPU.Register.D, CPU.Register.B);
                    return 4;
                case 0x51:
                    this.LD(CPU.Register.D, CPU.Register.C);
                    return 4;
                case 0x52:
                    this.LD(CPU.Register.D, CPU.Register.D);
                    return 4;
                case 0x53:
                    this.LD(CPU.Register.D, CPU.Register.E);
                    return 4;
                case 0x54:
                    this.LD(CPU.Register.D, CPU.Register.H);
                    return 4;
                case 0x55:
                    this.LD(CPU.Register.D, CPU.Register.L);
                    return 4;
                case 0x56:
                    this.LD(CPU.Register.D, this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 8;
                case 0x57:
                    this.LD(CPU.Register.D, CPU.Register.A);
                    return 4;
                case 0x58:
                    this.LD(CPU.Register.E, CPU.Register.B);
                    return 4;
                case 0x59:
                    this.LD(CPU.Register.E, CPU.Register.C);
                    return 4;
                case 0x5A:
                    this.LD(CPU.Register.E, CPU.Register.D);
                    return 4;
                case 0x5B:
                    this.LD(CPU.Register.E, CPU.Register.E);
                    return 4;
                case 0x5C:
                    this.LD(CPU.Register.E, CPU.Register.H);
                    return 4;
                case 0x5D:
                    this.LD(CPU.Register.E, CPU.Register.L);
                    return 4;
                case 0x5E:
                    this.LD(CPU.Register.E, this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 4;
                case 0x5F:
                    this.LD(CPU.Register.E, CPU.Register.A);
                    return 4;
                case 0x60:
                    this.LD(CPU.Register.H, CPU.Register.B);
                    return 4;
                case 0x61:
                    this.LD(CPU.Register.H, CPU.Register.C);
                    return 4;
                case 0x62:
                    this.LD(CPU.Register.H, CPU.Register.D);
                    return 4;
                case 0x63:
                    this.LD(CPU.Register.H, CPU.Register.E);
                    return 4;
                case 0x64:
                    this.LD(CPU.Register.H, CPU.Register.H);
                    return 4;
                case 0x65:
                    this.LD(CPU.Register.H, CPU.Register.L);
                    return 4;
                case 0x66:
                    this.LD(CPU.Register.H, this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 8;
                case 0x67:
                    this.LD(CPU.Register.H, CPU.Register.A);
                    return 4;
                case 0x68:
                    this.LD(CPU.Register.L, CPU.Register.B);
                    return 4;
                case 0x69:
                    this.LD(CPU.Register.L, CPU.Register.C);
                    return 4;
                case 0x6A:
                    this.LD(CPU.Register.L, CPU.Register.D);
                    return 4;
                case 0x6B:
                    this.LD(CPU.Register.L, CPU.Register.E);
                    return 4;
                case 0x6C:
                    this.LD(CPU.Register.L, CPU.Register.H);
                    return 4;
                case 0x6D:
                    this.LD(CPU.Register.L, CPU.Register.L);
                    return 4;
                case 0x6E:
                    this.LD(CPU.Register.L, this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 4;
                case 0x6F:
                    this.LD(CPU.Register.L, CPU.Register.A);
                    return 4;
                case 0x70:
                    this.LD(this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)), this.gameBoy.getCpu().getRegister(CPU.Register.B));
                    return 8;
                case 0x71:
                    this.LD(this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)), this.gameBoy.getCpu().getRegister(CPU.Register.C));
                    return 8;
                case 0x72:
                    this.LD(this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)), this.gameBoy.getCpu().getRegister(CPU.Register.D));
                    return 8;
                case 0x73:
                    this.LD(this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)), this.gameBoy.getCpu().getRegister(CPU.Register.E));
                    return 8;
                case 0x74:
                    this.LD(this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)), this.gameBoy.getCpu().getRegister(CPU.Register.H));
                    return 8;
                case 0x75:
                    this.LD(this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)), this.gameBoy.getCpu().getRegister(CPU.Register.L));
                    return 8;
                case 0x76:
                    this.HALT();
                    return 4;
                case 0x77:
                    this.LD(this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)), this.gameBoy.getCpu().getRegister(CPU.Register.A));
                    return 8;
                case 0x78:
                    this.LD(CPU.Register.A, CPU.Register.B);
                    return 4;
                case 0x79:
                    this.LD(CPU.Register.A, CPU.Register.C);
                    return 4;
                case 0x7A:
                    this.LD(CPU.Register.A, CPU.Register.D);
                    return 4;
                case 0x7B:
                    this.LD(CPU.Register.A, CPU.Register.E);
                    return 4;
                case 0x7C:
                    this.LD(CPU.Register.A, CPU.Register.H);
                    return 4;
                case 0x7D:
                    this.LD(CPU.Register.A, CPU.Register.L);
                    return 4;
                case 0x7E:
                    this.LD(CPU.Register.A, this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 8;
                case 0x7F:
                    this.LD(CPU.Register.A, CPU.Register.A);
                    return 4;
                case (byte) 0x80:
                    this.ADD(CPU.Register.A, CPU.Register.B);
                    return 4;
                case (byte) 0x81:
                    this.ADD(CPU.Register.A, CPU.Register.C);
                    return 4;
                case (byte) 0x82:
                    this.ADD(CPU.Register.A, CPU.Register.D);
                    return 4;
                case (byte) 0x83:
                    this.ADD(CPU.Register.A, CPU.Register.E);
                    return 4;
                case (byte) 0x84:
                    this.ADD(CPU.Register.A, CPU.Register.H);
                    return 4;
                case (byte) 0x85:
                    this.ADD(CPU.Register.A, CPU.Register.L);
                    return 4;
                case (byte) 0x86:
                    this.ADD(CPU.Register.A, this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 8;
                case (byte) 0x87:
                    this.ADD(CPU.Register.A, CPU.Register.A);
                    return 4;
                case (byte) 0x88:
                    this.ADD(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.B) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0x89:
                    this.ADD(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.C) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0x8A:
                    this.ADD(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.D) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0x8B:
                    this.ADD(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.E) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0x8C:
                    this.ADD(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.H) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0x8D:
                    this.ADD(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.L) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0x8E:
                    this.ADD(CPU.Register.A, (byte) (this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getRegister(CPU.Register.HL)) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 8;
                case (byte) 0x8F:
                    this.ADD(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.A) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0x90:
                    this.SUB(CPU.Register.A, CPU.Register.B);
                    return 4;
                case (byte) 0x91:
                    this.SUB(CPU.Register.A, CPU.Register.C);
                    return 4;
                case (byte) 0x92:
                    this.SUB(CPU.Register.A, CPU.Register.D);
                    return 4;
                case (byte) 0x93:
                    this.SUB(CPU.Register.A, CPU.Register.E);
                    return 4;
                case (byte) 0x94:
                    this.SUB(CPU.Register.A, CPU.Register.H);
                    return 4;
                case (byte) 0x95:
                    this.SUB(CPU.Register.A, CPU.Register.L);
                    return 4;
                case (byte) 0x96:
                    this.SUB(CPU.Register.A, this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 8;
                case (byte) 0x97:
                    this.SUB(CPU.Register.A, CPU.Register.A);
                    return 4;
                case (byte) 0x98:
                    this.SUB(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.B) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0x99:
                    this.SUB(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.C) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0x9A:
                    this.SUB(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.D) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0x9B:
                    this.SUB(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.E) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0x9C:
                    this.SUB(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.H) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0x9D:
                    this.SUB(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.L) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0x9E:
                    this.SUB(CPU.Register.A, (byte) (this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getRegister(CPU.Register.HL)) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 8;
                case (byte) 0x9F:
                    this.SUB(CPU.Register.A, (byte) (this.gameBoy.getCpu().getRegister(CPU.Register.A) + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 4;
                case (byte) 0xA0:
                    this.AND(CPU.Register.B);
                    return 4;
                case (byte) 0xA1:
                    this.AND(CPU.Register.C);
                    return 4;
                case (byte) 0xA2:
                    this.AND(CPU.Register.D);
                    return 4;
                case (byte) 0xA3:
                    this.AND(CPU.Register.E);
                    return 4;
                case (byte) 0xA4:
                    this.AND(CPU.Register.H);
                    return 4;
                case (byte) 0xA5:
                    this.AND(CPU.Register.L);
                    return 4;
                case (byte) 0xA6:
                    this.AND(this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 8;
                case (byte) 0xA7:
                    this.AND(CPU.Register.A);
                    return 4;
                case (byte) 0xA8:
                    this.XOR(CPU.Register.B);
                    return 4;
                case (byte) 0xA9:
                    this.XOR(CPU.Register.C);
                    return 4;
                case (byte) 0xAA:
                    this.XOR(CPU.Register.D);
                    return 4;
                case (byte) 0xAB:
                    this.XOR(CPU.Register.E);
                    return 4;
                case (byte) 0xAC:
                    this.XOR(CPU.Register.H);
                    return 4;
                case (byte) 0xAD:
                    this.XOR(CPU.Register.L);
                    return 4;
                case (byte) 0xAE:
                    this.XOR(this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 8;
                case (byte) 0xAF:
                    this.XOR(CPU.Register.A);
                    return 4;
                case (byte) 0xB0:
                    this.OR(CPU.Register.B);
                    return 4;
                case (byte) 0xB1:
                    this.OR(CPU.Register.C);
                    return 4;
                case (byte) 0xB2:
                    this.OR(CPU.Register.D);
                    return 4;
                case (byte) 0xB3:
                    this.OR(CPU.Register.E);
                    return 4;
                case (byte) 0xB4:
                    this.OR(CPU.Register.H);
                    return 4;
                case (byte) 0xB5:
                    this.OR(CPU.Register.L);
                    return 4;
                case (byte) 0xB6:
                    this.OR(this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 8;
                case (byte) 0xB7:
                    this.OR(CPU.Register.A);
                    return 4;
                case (byte) 0xB8:
                    this.CP(CPU.Register.B);
                    return 4;
                case (byte) 0xB9:
                    this.CP(CPU.Register.C);
                    return 4;
                case (byte) 0xBA:
                    this.CP(CPU.Register.D);
                    return 4;
                case (byte) 0xBB:
                    this.CP(CPU.Register.E);
                    return 4;
                case (byte) 0xBC:
                    this.CP(CPU.Register.H);
                    return 4;
                case (byte) 0xBD:
                    this.CP(CPU.Register.L);
                    return 4;
                case (byte) 0xBE:
                    this.CP(this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 8;
                case (byte) 0xBF:
                    this.CP(CPU.Register.A);
                    return 4;
                case (byte) 0xC0:
                    if (!this.gameBoy.getCpu().getFlag(CPU.Flag.Z)) {
                        this.RET();
                        return 20;
                    }
                    return 8;
                case (byte) 0xC1:
                    this.POP(CPU.Register.BC);
                    return 12;
                case (byte) 0xC2:
                    d16 = this.getD16();
                    if (!this.gameBoy.getCpu().getFlag(CPU.Flag.Z)) {
                        this.JP(d16);
                        return 16;
                    }
                    return 12;
                case (byte) 0xC3:
                    this.JP(this.getD16());
                    return 16;
                case (byte) 0xC4:
                    d16 = this.getD16();
                    if (!this.gameBoy.getCpu().getFlag(CPU.Flag.Z)) {
                        this.CALL(d16);
                        return 24;
                    }
                    return 12;
                case (byte) 0xC5:
                    this.PUSH(CPU.Register.BC);
                    return 16;
                case (byte) 0xC6:
                    this.ADD(CPU.Register.A, this.getD8());
                    return 8;
                case (byte) 0xC7:
                    this.RST((byte) 0x00);
                    return 16;
                case (byte) 0xC8:
                    if (this.gameBoy.getCpu().getFlag(CPU.Flag.Z)) {
                        this.RET();
                        return 20;
                    }
                    return 8;
                case (byte) 0xC9:
                    this.RET();
                    return 16;
                case (byte) 0xCA:
                    d16 = this.getD16();
                    if (this.gameBoy.getCpu().getFlag(CPU.Flag.Z)) {
                        this.JP(d16);
                        return 16;
                    }
                    return 12;
                case (byte) 0xCB:
                    return this.handleCBInstruction(this.getD8());
                case (byte) 0xCC:
                    d16 = this.getD16();
                    if (this.gameBoy.getCpu().getFlag(CPU.Flag.Z)) {
                        this.CALL(d16);
                        return 24;
                    }
                    return 12;
                case (byte) 0xCD:
                    this.CALL(this.getD16());
                    return 24;
                case (byte) 0xCE:
                    this.ADD(CPU.Register.A, (byte) (this.getD8() + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 8;
                case (byte) 0xCF:
                    this.RST((byte) 0x08);
                    return 16;
                case (byte) 0xD0:
                    if (!this.gameBoy.getCpu().getFlag(CPU.Flag.C)) {
                        this.RET();
                        return 20;
                    }
                    return 8;
                case (byte) 0xD1:
                    this.POP(CPU.Register.DE);
                    return 12;
                case (byte) 0xD2:
                    d16 = this.getD16();
                    if (!this.gameBoy.getCpu().getFlag(CPU.Flag.C)) {
                        this.JP(d16);
                        return 16;
                    }
                    return 12;
                case (byte) 0xD3:
                    return 4;
                case (byte) 0xD4:
                    d16 = this.getD16();
                    if (!this.gameBoy.getCpu().getFlag(CPU.Flag.C)) {
                        this.CALL(d16);
                        return 24;
                    }
                    return 12;
                case (byte) 0xD5:
                    this.PUSH(CPU.Register.DE);
                    return 16;
                case (byte) 0xD6:
                    this.SUB(CPU.Register.A, this.getD8());
                    return 8;
                case (byte) 0xD7:
                    this.RST((byte) 0x10);
                    return 16;
                case (byte) 0xD8:
                    if (this.gameBoy.getCpu().getFlag(CPU.Flag.C)) {
                        this.RET();
                        return 20;
                    }
                    return 8;
                case (byte) 0xD9:
                    this.RET();
                    this.gameBoy.getCpu().setInterruptEnable(true);
                    return 16;
                case (byte) 0xDA:
                    d16 = this.getD16();
                    if (this.gameBoy.getCpu().getFlag(CPU.Flag.C)) {
                        this.JP(d16);
                        return 16;
                    }
                    return 12;
                case (byte) 0xDB:
                    return 4;
                case (byte) 0xDC:
                    d16 = this.getD16();
                    if (this.gameBoy.getCpu().getFlag(CPU.Flag.C)) {
                        this.CALL(d16);
                        return 24;
                    }
                    return 12;
                case (byte) 0xDD:
                    return 4;
                case (byte) 0xDE:
                    this.SUB(CPU.Register.A, (byte) (this.getD8() + (this.gameBoy.getCpu().getFlag(CPU.Flag.C) ? 1 : 0)));
                    return 8;
                case (byte) 0xDF:
                    this.RST((byte) 0x18);
                    return 16;
                case (byte) 0xE0:
                    this.LD(0xFF00 + (this.getD8() & 0xFF), CPU.Register.A);
                    return 12;
                case (byte) 0xE1:
                    this.POP(CPU.Register.HL);
                    return 12;
                case (byte) 0xE2:
                    this.LD(0xFF00 + (this.gameBoy.getCpu().getRegister(CPU.Register.C) & 0xFF), CPU.Register.A);
                    return 8;
                case (byte) 0xE5:
                    this.PUSH(CPU.Register.HL);
                    return 16;
                case (byte) 0xE6:
                    this.AND(this.getD8());
                    return 8;
                case (byte) 0xE7:
                    this.RST((byte) 0x20);
                    return 16;
                case (byte) 0xE8:
                    this.ADD(CPU.Register.SP, this.getD8());
                    return 16;
                case (byte) 0xE9:
                    this.JP(this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.HL)));
                    return 4;
                case (byte) 0xEA:
                    this.LD(this.getD16(), CPU.Register.A);
                    return 16;
                case (byte) 0xEE:
                    this.XOR(this.getD8());
                    return 8;
                case (byte) 0xEF:
                    this.RST((byte) 0x28);
                    return 16;
                case (byte) 0xF0:
                    this.LD(CPU.Register.A, this.gameBoy.getMmu().readByte((short) (0xFF00 + this.getD8())));
                    return 12;
                case (byte) 0xF1:
                    this.POP(CPU.Register.AF);
                    return 12;
                case (byte) 0xF2:
                    this.LD(CPU.Register.A, this.gameBoy.getMmu().readByte((short) (0xFF00 + this.gameBoy.getCpu().getRegister(CPU.Register.C))));
                    return 8;
                case (byte) 0xF3:
                    this.gameBoy.getCpu().setInterruptEnable(false);
                    return 4;
                case (byte) 0xF5:
                    this.PUSH(CPU.Register.AF);
                    return 16;
                case (byte) 0xF6:
                    this.OR(this.getD8());
                    return 8;
                case (byte) 0xF7:
                    this.RST((byte) 0x30);
                    return 16;
                case (byte) 0xF8:
                    this.LD(CPU.Register.HL, (short) (this.gameBoy.getCpu().getRegister(CPU.Register.SP) + this.getD8()));
                    return 12;
                case (byte) 0xF9:
                    this.LD(CPU.Register.SP, CPU.Register.HL);
                    return 8;
                case (byte) 0xFA:
                    this.LD(CPU.Register.A, this.gameBoy.getMmu().readByte(this.getD16()));
                    return 16;
                case (byte) 0xFB:
                    this.gameBoy.getCpu().setInterruptEnable(true);
                    return 4;
                case (byte) 0xFE:
                    this.CP(this.getD8());
                    return 8;
                case (byte) 0xFF:
                    this.RST((byte) 0x38);
                    return 16;
                default:
                    System.out.println("Unknown instruction: " + Integer.toHexString(instruction & 0xFF));
                    return 4;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println();
        }
        return 4;
    }

    public int handleCBInstruction(byte instruction) {
        return 4;
    }

    public void LD(CPU.Register register, short value) {
        try {
            if (register.isByte()) {
                this.gameBoy.getCpu().setRegister(register, (byte) value);
            }
            else {
                this.gameBoy.getCpu().setRegister(register, value);
            }
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
        address &= 0xFFFF;
        try {
            if (register.isByte()) {
                this.gameBoy.getMmu().writeByte((short) address, this.gameBoy.getCpu().getRegister(register));
            }
            else {
                short value = this.gameBoy.getCpu().getDoubleRegister(register);
                this.gameBoy.getMmu().writeByte((short) (address + 1), (byte) ((value >> 8) & 0xFF));
                this.gameBoy.getMmu().writeByte((short) address, (byte) (value & 0xFF));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LD(int address, byte value) {
        try {
            this.gameBoy.getMmu().writeByte((short) address, value);
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
                this.gameBoy.getCpu().setFlag(CPU.Flag.Z, after == 0);
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

    public void INC(int address) throws Exception {
        byte before = this.gameBoy.getMmu().readByte((short) address);
        byte after = (byte) (before + 1);
        this.gameBoy.getMmu().writeByte((short) address, after);
        boolean h = (before & 0xF0) != (after & 0xF0);
        this.gameBoy.getCpu().setFlag(CPU.Flag.Z, after == 0);
        this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, h);
    }

    public boolean DEC(CPU.Register register) {
        try {
            if (register.isByte()) {
                byte before = this.gameBoy.getCpu().getRegister(register);
                byte after = (byte) (before - 1);
                this.gameBoy.getCpu().setRegister(register, after);
                boolean h = (before & 0xF0) != (after & 0xF0);
                this.gameBoy.getCpu().setFlag(CPU.Flag.Z, after == 0);
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

    public void DEC(int address) throws Exception {
        byte before = this.gameBoy.getMmu().readByte((short) address);
        byte after = (byte) (before - 1);
        this.gameBoy.getMmu().writeByte((short) address, after);
        boolean h = (before & 0xF0) != (after & 0xF0);
        this.gameBoy.getCpu().setFlag(CPU.Flag.Z, after == 0);
        this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, h);
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

    public void ADD(CPU.Register toRegister, CPU.Register fromRegister) throws Exception {
        /*try {
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
        }*/
        if (fromRegister.isByte()) {
            this.ADD(toRegister, this.gameBoy.getCpu().getRegister(fromRegister));
        }
        else {
            this.ADD(toRegister, this.gameBoy.getCpu().getDoubleRegister(fromRegister));
        }
    }

    public void ADD(CPU.Register register, short value) {
        try {
            if (register.isByte()) {
                int from = value & 0xFF;
                int to = this.gameBoy.getCpu().getRegister(register) & 0xFF;
                boolean c = (((from + to) >> 8) & 1) == 1;
                boolean h = (to & 0xF0) != ((from + to) & 0xF0);
                this.gameBoy.getCpu().setRegister(register, (byte) (from + to));
                this.gameBoy.getCpu().setFlag(CPU.Flag.Z, from + to == 0);
                this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
                this.gameBoy.getCpu().setFlag(CPU.Flag.H, h);
                this.gameBoy.getCpu().setFlag(CPU.Flag.C, c);
            }
            else {
                int from = value & 0xFFFF;
                int to = this.gameBoy.getCpu().getDoubleRegister(register) & 0xFFFF;
                boolean c = (((from + to) >> 16) & 1) == 1;
                boolean h = (to & 0xF000) != ((from + to) & 0xF000);
                this.gameBoy.getCpu().setRegister(register, (short) (from + to));
                this.gameBoy.getCpu().setFlag(CPU.Flag.Z, from + to == 0);
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

    public void HALT() {
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

    public void DAA() throws Exception {
        byte a = this.gameBoy.getCpu().getRegister(CPU.Register.A);
        byte error = 0x00;
        byte lowerNibble = (byte) (a & 0xF);
        byte upperNibble = (byte) ((a >> 4) & 0xF);
        if (lowerNibble > 0x09 || this.gameBoy.getCpu().getFlag(CPU.Flag.H)) {
            error += 0x06;
        }
        if (upperNibble > 0x09 || this.gameBoy.getCpu().getFlag(CPU.Flag.C)) {
            error += 0x60;
            this.gameBoy.getCpu().setFlag(CPU.Flag.C, true);
        }
        if (this.gameBoy.getCpu().getFlag(CPU.Flag.N)) {
            this.gameBoy.getCpu().setRegister(CPU.Register.A, (byte) (a - error));
        }
        else {
            this.gameBoy.getCpu().setRegister(CPU.Register.A, (byte) (a + error));
        }
        this.gameBoy.getCpu().setFlag(CPU.Flag.Z, this.gameBoy.getCpu().getRegister(CPU.Register.A) == 0);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, false);
    }

    public void CPL() throws Exception {
        byte a = this.gameBoy.getCpu().getRegister(CPU.Register.A);
        a ^= 0xFF;
        this.gameBoy.getCpu().setRegister(CPU.Register.A, a);
        this.gameBoy.getCpu().setFlag(CPU.Flag.N, true);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, true);
    }

    public void SCF() {
        this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.C, true);
    }

    public void CCF() {
        this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.C, !this.gameBoy.getCpu().getFlag(CPU.Flag.C));
    }

    public void SUB(CPU.Register register, short value) throws Exception {
        if (register.isByte()) {
            int from = value & 0xFF;
            int to = this.gameBoy.getCpu().getRegister(register) & 0xFF;
            boolean c = (to - from) < 0;
            boolean h = (to & 0xF0) != ((to - from) & 0xF0);
            this.gameBoy.getCpu().setRegister(register, (byte) (to - from));
            this.gameBoy.getCpu().setFlag(CPU.Flag.Z, to - from == 0);
            this.gameBoy.getCpu().setFlag(CPU.Flag.N, true);
            this.gameBoy.getCpu().setFlag(CPU.Flag.H, !h);
            this.gameBoy.getCpu().setFlag(CPU.Flag.C, !c);
        }
        else {
            int from = value & 0xFFFF;
            int to = this.gameBoy.getCpu().getDoubleRegister(register) & 0xFFFF;
            boolean c = (to - from) < 0;
            boolean h = (to & 0xF000) != ((to - from) & 0xF000);
            this.gameBoy.getCpu().setRegister(register, (short) (to - from));
            this.gameBoy.getCpu().setFlag(CPU.Flag.Z, to - from == 0);
            this.gameBoy.getCpu().setFlag(CPU.Flag.N, true);
            this.gameBoy.getCpu().setFlag(CPU.Flag.H, !h);
            this.gameBoy.getCpu().setFlag(CPU.Flag.C, !c);
        }
    }

    public void SUB(CPU.Register toRegister, CPU.Register fromRegsiter) throws Exception {
        if (fromRegsiter.isByte()) {
            this.SUB(toRegister, this.gameBoy.getCpu().getRegister(fromRegsiter));
        }
        else {
            this.SUB(toRegister, this.gameBoy.getCpu().getDoubleRegister(fromRegsiter));
        }
    }

    public void AND(byte value) throws Exception {
        byte a = this.gameBoy.getCpu().getRegister(CPU.Register.A);
        a &= value;
        this.gameBoy.getCpu().setRegister(CPU.Register.A, a);
        this.gameBoy.getCpu().setFlag(CPU.Flag.Z, a == 0);
        this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, true);
        this.gameBoy.getCpu().setFlag(CPU.Flag.C, false);
    }

    public void AND(CPU.Register register) throws Exception {
        this.AND(this.gameBoy.getCpu().getRegister(register));
    }

    public void XOR(byte value) throws Exception {
        byte a = this.gameBoy.getCpu().getRegister(CPU.Register.A);
        a ^= value;
        this.gameBoy.getCpu().setRegister(CPU.Register.A, a);
        this.gameBoy.getCpu().setFlag(CPU.Flag.Z, a == 0);
        this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.C, false);
    }

    public void XOR(CPU.Register register) throws Exception {
        this.XOR(this.gameBoy.getCpu().getRegister(register));
    }

    public void OR(byte value) throws Exception {
        byte a = this.gameBoy.getCpu().getRegister(CPU.Register.A);
        a |= value;
        this.gameBoy.getCpu().setRegister(CPU.Register.A, a);
        this.gameBoy.getCpu().setFlag(CPU.Flag.Z, a == 0);
        this.gameBoy.getCpu().setFlag(CPU.Flag.N, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, false);
        this.gameBoy.getCpu().setFlag(CPU.Flag.C, false);
    }

    public void OR(CPU.Register register) throws Exception {
        this.OR(this.gameBoy.getCpu().getRegister(register));
    }

    public void CP(byte value) throws Exception {
        byte a = this.gameBoy.getCpu().getRegister(CPU.Register.A);
        boolean same = a == value;
        boolean h = (a & 0xF0) != ((a - value) & 0xF0);
        this.gameBoy.getCpu().setRegister(CPU.Register.A, a);
        this.gameBoy.getCpu().setFlag(CPU.Flag.Z, same);
        this.gameBoy.getCpu().setFlag(CPU.Flag.N, true);
        this.gameBoy.getCpu().setFlag(CPU.Flag.H, !h);
        this.gameBoy.getCpu().setFlag(CPU.Flag.C, a < value);
    }

    public void CP(CPU.Register register) throws Exception {
        this.CP(this.gameBoy.getCpu().getRegister(register));
    }

    public void JP(int address) throws Exception {
        this.gameBoy.getCpu().setRegister(CPU.Register.PC, (short) address);
    }

    public void RET() throws Exception {
        byte lowerByte = this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.SP));
        this.INC(CPU.Register.SP);
        byte upperByte = this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.SP));
        this.INC(CPU.Register.SP);
        short address = (short) ((upperByte << 8) + (lowerByte));
        this.JP(address);
    }

    public void POP(CPU.Register register) throws Exception {
        if (register.isByte()) {
            throw new Exception("Attempt to POP into 8 bit register");
        }
        else {
            byte lowerByte = this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.SP));
            this.INC(CPU.Register.SP);
            byte upperByte = this.gameBoy.getMmu().readByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.SP));
            this.INC(CPU.Register.SP);
            short value = (short) ((upperByte << 8) + (lowerByte));
            this.gameBoy.getCpu().setRegister(register, value);
        }
    }

    public void CALL(int address) throws Exception {
        short nextInstruction = (short) (this.gameBoy.getCpu().getDoubleRegister(CPU.Register.PC) + 1);
        byte upperByte = (byte) ((nextInstruction >> 8) & 0xFF);
        byte lowerByte = (byte) (nextInstruction & 0xFF);
        this.DEC(CPU.Register.SP);
        this.gameBoy.getMmu().writeByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.SP), upperByte);
        this.DEC(CPU.Register.SP);
        this.gameBoy.getMmu().writeByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.SP), lowerByte);
        this.JP(address);
    }

    public void PUSH(CPU.Register register) throws Exception {
        if (register.isByte()) {
            throw new Exception("Attempt to PUSH from an 8 bit register");
        }
        else {
            short value = (short) this.gameBoy.getCpu().getDoubleRegister(register);
            byte upperByte = (byte) ((value >> 8) & 0xFF);
            byte lowerByte = (byte) (value & 0xFF);
            this.DEC(CPU.Register.SP);
            this.gameBoy.getMmu().writeByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.SP), upperByte);
            this.DEC(CPU.Register.SP);
            this.gameBoy.getMmu().writeByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.SP), lowerByte);
        }
    }

    public void RST(byte value) throws Exception {
        short nextInstruction = (short) (this.gameBoy.getCpu().getDoubleRegister(CPU.Register.PC) + 1);
        byte upperByte = (byte) ((nextInstruction >> 8) & 0xFF);
        byte lowerByte = (byte) (nextInstruction & 0xFF);
        this.DEC(CPU.Register.SP);
        this.gameBoy.getMmu().writeByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.SP), upperByte);
        this.DEC(CPU.Register.SP);
        this.gameBoy.getMmu().writeByte(this.gameBoy.getCpu().getDoubleRegister(CPU.Register.SP), lowerByte);
        this.gameBoy.getCpu().setRegister(CPU.Register.PC, value);
    }

}
