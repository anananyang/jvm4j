package runtime.rtda.share.heap;

import classFile.attributes.CodeAttributeInfo;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.ClassRef;

public class JExceptionTable {

    private RuntimeConstantPool constantPool;
    private ExceptionHandler[] exceptionHandlers;


    JExceptionTable(CodeAttributeInfo.ExcetionTableEntry[] excetionTableEntries,
                    RuntimeConstantPool constantPool) {
        this.constantPool = constantPool;
        this.exceptionHandlers = getExceptionHandlers(excetionTableEntries);

    }

    private ExceptionHandler[] getExceptionHandlers(CodeAttributeInfo.ExcetionTableEntry[] excetionTableEntries) {
        if (isEmpty(excetionTableEntries)) {
            return null;
        }
        int len = excetionTableEntries.length;
        ExceptionHandler[] exceptionHandles = new ExceptionHandler[len];
        for (int i = 0; i < len; i++) {
            CodeAttributeInfo.ExcetionTableEntry entry = excetionTableEntries[i];
            exceptionHandles[i] = getExceptionHandler(entry);
        }
        return exceptionHandles;
    }

    private ExceptionHandler getExceptionHandler(CodeAttributeInfo.ExcetionTableEntry entry) {
        return new ExceptionHandler(entry);
    }


    private <T> boolean isEmpty(T[] arr) {
        return arr == null || arr.length == 0;
    }

    public ExceptionHandler findExceptionHandler(JClass exceptionClass, int PC) {
        if (isEmpty(exceptionHandlers)) {
            return null;
        }
        for (ExceptionHandler handler : exceptionHandlers) {
            if (handler.isBetween(PC) && handler.onCatchType(exceptionClass)) {
                return handler;
            }
        }
        return null;
    }


    public class ExceptionHandler {
        // try 块的第一条指令
        private int startPC;
        // try 块下的第一条指令
        private int endPC;
        private int handlerPC;
        private ClassRef catchType;

        ExceptionHandler(CodeAttributeInfo.ExcetionTableEntry entry) {
            this.startPC = entry.getStartPC();
            this.endPC = entry.getEndPC();
            this.handlerPC = entry.getHandlerPC();
            this.catchType = getCatchTypeClassRef(entry.getCatchType());
        }

        private ClassRef getCatchTypeClassRef(int catchTypeIndex) {
            // 常量池索引 0 是无效的
            if (catchTypeIndex == 0) {
                return null;  // 没有指定类型可能是 catch all
            }
            return (ClassRef) constantPool.getValueByIndex(catchTypeIndex);
        }


        public int getHandlerPC() {
            return handlerPC;
        }

        public ClassRef getCatchType() {
            return catchType;
        }

        public boolean isBetween(int pc) {
            return startPC >= pc && pc < endPC;
        }

        public boolean onCatchType(JClass exceptionClass) {
            if (catchType == null) {
                return true;   // catch all
            }
            JClass catchClass = catchType.resolveClass();
            return catchClass == exceptionClass || exceptionClass.isSubClassOf(catchClass);
        }

    }
}
