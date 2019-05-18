package io.github.firefang.power.engine.event;

import java.util.LinkedList;
import java.util.List;

/**
 * Event bus
 * 
 * @author xinufo
 *
 */
public class PowerEventBus {
    private List<IPowerEventListener> listeners = new LinkedList<>();

    public void post(PowerEvent e) {
        for (IPowerEventListener lis : listeners) {
            lis.onEvent(e);
        }
    }

    public void registerListener(IPowerEventListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

}
