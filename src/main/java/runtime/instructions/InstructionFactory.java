package runtime.instructions;

import runtime.instructions.comparisons.dcmp.DCMPG;
import runtime.instructions.comparisons.dcmp.DCMPL;
import runtime.instructions.comparisons.fcmp.FCMPG;
import runtime.instructions.comparisons.fcmp.FCMPL;
import runtime.instructions.comparisons.ifacmp.IF_ACMPEQ;
import runtime.instructions.comparisons.ifacmp.IF_ACMPNE;
import runtime.instructions.comparisons.ifcond.*;
import runtime.instructions.comparisons.ificmp.*;
import runtime.instructions.comparisons.lcmp.LCMP;
import runtime.instructions.constants.*;
import runtime.instructions.control.GOTO;
import runtime.instructions.control.LOOKUP_SWITCH;
import runtime.instructions.control.TABLE_SWITCH;
import runtime.instructions.conversions.d2x.D2F;
import runtime.instructions.conversions.d2x.D2I;
import runtime.instructions.conversions.d2x.D2L;
import runtime.instructions.conversions.f2x.F2D;
import runtime.instructions.conversions.f2x.F2I;
import runtime.instructions.conversions.f2x.F2L;
import runtime.instructions.conversions.i2x.*;
import runtime.instructions.conversions.l2x.L2D;
import runtime.instructions.conversions.l2x.L2F;
import runtime.instructions.conversions.l2x.L2I;
import runtime.instructions.ext.GOTO_W;
import runtime.instructions.ext.IFNONNULL;
import runtime.instructions.ext.IFNULL;
import runtime.instructions.ext.WIDE;
import runtime.instructions.loads.aload.*;
import runtime.instructions.loads.dload.*;
import runtime.instructions.loads.fload.*;
import runtime.instructions.loads.iload.*;
import runtime.instructions.loads.lload.*;
import runtime.instructions.math.arithmetic.add.DADD;
import runtime.instructions.math.arithmetic.add.FADD;
import runtime.instructions.math.arithmetic.add.IADD;
import runtime.instructions.math.arithmetic.add.LADD;
import runtime.instructions.math.arithmetic.div.DDIV;
import runtime.instructions.math.arithmetic.div.FDIV;
import runtime.instructions.math.arithmetic.div.IDIV;
import runtime.instructions.math.arithmetic.div.LDIV;
import runtime.instructions.math.arithmetic.mul.DMUL;
import runtime.instructions.math.arithmetic.mul.FMUL;
import runtime.instructions.math.arithmetic.mul.IMUL;
import runtime.instructions.math.arithmetic.mul.LMUL;
import runtime.instructions.math.arithmetic.rem.DREM;
import runtime.instructions.math.arithmetic.rem.FREM;
import runtime.instructions.math.arithmetic.rem.IREM;
import runtime.instructions.math.arithmetic.rem.LREM;
import runtime.instructions.math.arithmetic.sub.DSUB;
import runtime.instructions.math.arithmetic.sub.FSUB;
import runtime.instructions.math.arithmetic.sub.ISUB;
import runtime.instructions.math.arithmetic.sub.LSUB;
import runtime.instructions.math.bool.and.IAND;
import runtime.instructions.math.bool.and.LAND;
import runtime.instructions.math.bool.neg.DNEG;
import runtime.instructions.math.bool.neg.FNEG;
import runtime.instructions.math.bool.neg.INEG;
import runtime.instructions.math.bool.neg.LNEG;
import runtime.instructions.math.bool.or.IOR;
import runtime.instructions.math.bool.or.LOR;
import runtime.instructions.math.bool.xor.IXOR;
import runtime.instructions.math.bool.xor.LXOR;
import runtime.instructions.math.incr.IINC;
import runtime.instructions.math.shift.*;
import runtime.instructions.stack.dup.*;
import runtime.instructions.stack.pop.POP;
import runtime.instructions.stack.pop.POP2;
import runtime.instructions.stack.swap.SWAP;
import runtime.instructions.store.astore.*;
import runtime.instructions.store.dstore.*;
import runtime.instructions.store.fstore.*;
import runtime.instructions.store.istore.*;
import runtime.instructions.store.lstore.*;

import java.util.HashMap;
import java.util.Map;

public abstract class InstructionFactory {

    /**
     * 没有操作数的指令是可以复用的
     */
    private static Map<Integer, Instruction> nopInsctructionMap = new HashMap<>();

    static {
        nopInsctructionMap.put(0x00, new NOP());
        nopInsctructionMap.put(0x01, new ACONST_NULL());
        nopInsctructionMap.put(0x02, new ICONST_M1());
        nopInsctructionMap.put(0x03, new ICONST_0());
        nopInsctructionMap.put(0x04, new ICONST_1());
        nopInsctructionMap.put(0x05, new ICONST_2());
        nopInsctructionMap.put(0x06, new ICONST_3());
        nopInsctructionMap.put(0x07, new ICONST_4());
        nopInsctructionMap.put(0x08, new ICONST_5());
        nopInsctructionMap.put(0x09, new LCONST_0());
        nopInsctructionMap.put(0x0a, new LCONST_1());
        nopInsctructionMap.put(0x0b, new FCONST_0());
        nopInsctructionMap.put(0x0c, new FCONST_1());
        nopInsctructionMap.put(0x0d, new FCONST_2());
        nopInsctructionMap.put(0x0e, new DCONST_0());
        nopInsctructionMap.put(0x0f, new DCONST_1());

        nopInsctructionMap.put(0x1a, new ILOAD_0());
        nopInsctructionMap.put(0x1b, new ILOAD_1());
        nopInsctructionMap.put(0x1c, new ILOAD_2());
        nopInsctructionMap.put(0x1d, new ILOAD_3());
        nopInsctructionMap.put(0x1e, new LLOAD_0());
        nopInsctructionMap.put(0x1f, new LLOAD_1());
        nopInsctructionMap.put(0x20, new LLOAD_2());
        nopInsctructionMap.put(0x21, new LLOAD_3());
        nopInsctructionMap.put(0x22, new FLOAD_0());
        nopInsctructionMap.put(0x23, new FLOAD_1());
        nopInsctructionMap.put(0x24, new FLOAD_2());
        nopInsctructionMap.put(0x25, new FLOAD_3());
        nopInsctructionMap.put(0x26, new DLOAD_0());
        nopInsctructionMap.put(0x27, new DLOAD_1());
        nopInsctructionMap.put(0x28, new DLOAD_2());
        nopInsctructionMap.put(0x29, new DLOAD_3());
        nopInsctructionMap.put(0x2a, new ALOAD_0());
        nopInsctructionMap.put(0x2b, new ALOAD_1());
        nopInsctructionMap.put(0x2c, new ALOAD_2());
        nopInsctructionMap.put(0x2d, new ALOAD_3());

        nopInsctructionMap.put(0x3b, new ISTORE_0());
        nopInsctructionMap.put(0x3c, new ISTORE_1());
        nopInsctructionMap.put(0x3d, new ISTORE_2());
        nopInsctructionMap.put(0x3e, new ISTORE_3());
        nopInsctructionMap.put(0x3f, new LSTORE_0());
        nopInsctructionMap.put(0x40, new LSTORE_1());
        nopInsctructionMap.put(0x41, new LSTORE_2());
        nopInsctructionMap.put(0x42, new LSTORE_3());
        nopInsctructionMap.put(0x43, new FSTORE_0());
        nopInsctructionMap.put(0x44, new FSTORE_1());
        nopInsctructionMap.put(0x45, new FSTORE_2());
        nopInsctructionMap.put(0x46, new FSTORE_3());
        nopInsctructionMap.put(0x47, new DSTORE_0());
        nopInsctructionMap.put(0x48, new DSTORE_1());
        nopInsctructionMap.put(0x49, new DSTORE_2());
        nopInsctructionMap.put(0x4a, new DSTORE_3());
        nopInsctructionMap.put(0x4b, new ASTORE_0());
        nopInsctructionMap.put(0x4c, new ASTORE_1());
        nopInsctructionMap.put(0x4d, new ASTORE_2());
        nopInsctructionMap.put(0x4e, new ASTORE_3());


        nopInsctructionMap.put(0x57, new POP());
        nopInsctructionMap.put(0x58, new POP2());
        nopInsctructionMap.put(0x59, new DUP());
        nopInsctructionMap.put(0x5a, new DUP_X1());
        nopInsctructionMap.put(0x5b, new DUP_X2());
        nopInsctructionMap.put(0x5c, new DUP2());
        nopInsctructionMap.put(0x5d, new DUP2_X1());
        nopInsctructionMap.put(0x5e, new DUP2_X2());
        nopInsctructionMap.put(0x5f, new SWAP());
        nopInsctructionMap.put(0x60, new IADD());
        nopInsctructionMap.put(0x61, new LADD());
        nopInsctructionMap.put(0x62, new FADD());
        nopInsctructionMap.put(0x63, new DADD());
        nopInsctructionMap.put(0x64, new ISUB());
        nopInsctructionMap.put(0x65, new LSUB());
        nopInsctructionMap.put(0x66, new FSUB());
        nopInsctructionMap.put(0x67, new DSUB());
        nopInsctructionMap.put(0x68, new IMUL());
        nopInsctructionMap.put(0x69, new LMUL());
        nopInsctructionMap.put(0x6a, new FMUL());
        nopInsctructionMap.put(0x6b, new DMUL());
        nopInsctructionMap.put(0x6c, new IDIV());
        nopInsctructionMap.put(0x6d, new LDIV());
        nopInsctructionMap.put(0x6e, new FDIV());
        nopInsctructionMap.put(0x6f, new DDIV());
        nopInsctructionMap.put(0x70, new IREM());
        nopInsctructionMap.put(0x71, new LREM());
        nopInsctructionMap.put(0x72, new FREM());
        nopInsctructionMap.put(0x73, new DREM());
        nopInsctructionMap.put(0x74, new INEG());
        nopInsctructionMap.put(0x75, new LNEG());
        nopInsctructionMap.put(0x76, new FNEG());
        nopInsctructionMap.put(0x77, new DNEG());
        nopInsctructionMap.put(0x78, new ISHL());
        nopInsctructionMap.put(0x79, new LSHL());
        nopInsctructionMap.put(0x7a, new ISHR());
        nopInsctructionMap.put(0x7b, new LSHR());
        nopInsctructionMap.put(0x7c, new IUSHR());
        nopInsctructionMap.put(0x7d, new LUSHR());
        nopInsctructionMap.put(0x7e, new IAND());
        nopInsctructionMap.put(0x7f, new LAND());
        nopInsctructionMap.put(0x80, new IOR());
        nopInsctructionMap.put(0x81, new LOR());
        nopInsctructionMap.put(0x82, new IXOR());
        nopInsctructionMap.put(0x83, new LXOR());

        nopInsctructionMap.put(0x85, new I2L());
        nopInsctructionMap.put(0x86, new I2F());
        nopInsctructionMap.put(0x87, new I2D());
        nopInsctructionMap.put(0x88, new L2I());
        nopInsctructionMap.put(0x89, new L2F());
        nopInsctructionMap.put(0x8a, new L2D());
        nopInsctructionMap.put(0x8b, new F2I());
        nopInsctructionMap.put(0x8c, new F2L());
        nopInsctructionMap.put(0x8d, new F2D());
        nopInsctructionMap.put(0x8e, new D2I());
        nopInsctructionMap.put(0x8f, new D2L());
        nopInsctructionMap.put(0x90, new D2F());
        nopInsctructionMap.put(0x91, new I2B());
        nopInsctructionMap.put(0x92, new I2C());
        nopInsctructionMap.put(0x93, new I2S());
        nopInsctructionMap.put(0x94, new LCMP());
        nopInsctructionMap.put(0x95, new FCMPL());
        nopInsctructionMap.put(0x96, new FCMPG());
        nopInsctructionMap.put(0x97, new DCMPL());
        nopInsctructionMap.put(0x98, new DCMPG());



    }

    private static Map<Integer, Class<? extends Instruction>> insctructionClassMap = new HashMap<>();

    static {
        insctructionClassMap.put(0x10, BIPUSH.class);
        insctructionClassMap.put(0x11, SIPUSH.class);

        insctructionClassMap.put(0x15, ILOAD.class);
        insctructionClassMap.put(0x16, LLOAD.class);
        insctructionClassMap.put(0x17, FLOAD.class);
        insctructionClassMap.put(0x18, DLOAD.class);
        insctructionClassMap.put(0x19, ALOAD.class);

        insctructionClassMap.put(0x36, ISTORE.class);
        insctructionClassMap.put(0x37, LSTORE.class);
        insctructionClassMap.put(0x38, FSTORE.class);
        insctructionClassMap.put(0x39, DSTORE.class);
        insctructionClassMap.put(0x3a, ASTORE.class);

        insctructionClassMap.put(0x84, IINC.class);

        insctructionClassMap.put(0x99, IFEQ.class);
        insctructionClassMap.put(0x9a, IFNE.class);
        insctructionClassMap.put(0x9b, IFLT.class);
        insctructionClassMap.put(0x9c, IFGE.class);
        insctructionClassMap.put(0x9d, IFGT.class);
        insctructionClassMap.put(0x9e, IFLE.class);
        insctructionClassMap.put(0x9f, IF_ICMPEQ.class);
        insctructionClassMap.put(0xa0, IF_ICMPNE.class);
        insctructionClassMap.put(0xa1, IF_ICMPLT.class);
        insctructionClassMap.put(0xa2, IF_ICMPGE.class);
        insctructionClassMap.put(0xa3, IF_ICMPGT.class);
        insctructionClassMap.put(0xa4, IF_ICMPLE.class);
        insctructionClassMap.put(0xa5, IF_ACMPEQ.class);
        insctructionClassMap.put(0xa6, IF_ACMPNE.class);
        insctructionClassMap.put(0xa7, GOTO.class);

        insctructionClassMap.put(0xaa, TABLE_SWITCH.class);
        insctructionClassMap.put(0xab, LOOKUP_SWITCH.class);

        insctructionClassMap.put(0xc4, WIDE.class);

        insctructionClassMap.put(0xc6, IFNULL.class);
        insctructionClassMap.put(0xc7, IFNONNULL.class);
        insctructionClassMap.put(0xc8, GOTO_W.class);

    }


    public static Instruction get(int opCode) {
        Instruction instruction = nopInsctructionMap.get(opCode);
        if (instruction != null) {
            return instruction;
        }
        Class<? extends Instruction> clazz = insctructionClassMap.get(opCode);
        if (clazz == null) {
            throw new RuntimeException("Unsupported opcode: " + opCode);
        }
        try {
            return clazz.newInstance();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
