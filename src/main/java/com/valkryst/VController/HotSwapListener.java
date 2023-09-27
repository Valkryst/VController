package com.valkryst.VController;

import net.java.games.input.Controller;

import java.util.EventListener;

/** The listener interface for receiving hotswap (i.e. when a controller is added or removed) events. */
public interface HotSwapListener extends EventListener {
    /**
     * Invoked when a new {@link Controller} is added to the system.
     *
     * @param controller The new {@link Controller}.
     */
    void controllerAdded(final Controller controller);

    /**
     * Invoked when a {@link Controller} is removed from the system.
     *
     * @param controller The removed {@link Controller}.
     */
    void controllerRemoved(final Controller controller);
}
