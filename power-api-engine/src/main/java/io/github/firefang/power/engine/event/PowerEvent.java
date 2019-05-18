package io.github.firefang.power.engine.event;

import java.util.EventObject;

/**
 * @author xinufo
 */
public class PowerEvent extends EventObject {
    private static final long serialVersionUID = 6328062073917500330L;
    public static final byte LEVEL_DEBUG = 1;
    public static final byte LEVEL_INFO = 2;

    private byte level;
    private String msg;
    private Object[] args;

    public PowerEvent(Object source, byte level, String msg, Object... args) {
        super(source);
        this.level = level;
        this.msg = msg;
        this.args = args;
    }

    public PowerEvent(Object source, String msg, Object... args) {
        super(source);
        this.level = LEVEL_INFO;
        this.msg = msg;
        this.args = args;
    }

    public byte getLevel() {
        return level;
    }

    public String getMsg() {
        return msg;
    }

    public Object[] getArgs() {
        return args;
    }

}
