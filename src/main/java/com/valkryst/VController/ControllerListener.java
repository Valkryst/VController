package com.valkryst.VController;

import net.java.games.input.Controller;
import net.java.games.input.Event;

import java.util.EventListener;

/** The listener interface for receiving {@link Controller} {@link Event}s. */
public interface ControllerListener extends EventListener {
    /**
     * Invoked when an {@link Event} occurs.
     *
     * @param event {@code Event} to be processed.
     */
    void eventOccurred(final Event event);
}
