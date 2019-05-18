package io.github.firefang.power.engine.event;

import java.util.EventListener;

/**
 * Listener for event bus
 * 
 * @author xinufo
 *
 */
public interface IPowerEventListener extends EventListener {

    /**
     * Invoked when a event come
     * 
     * @param e
     */
    void onEvent(PowerEvent e);

}
