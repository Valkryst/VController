package com.valkryst.VController;

import com.valkryst.VRadio.Radio;
import lombok.Getter;
import lombok.NonNull;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

import javax.swing.Timer;

public class ControllerListener implements Runnable {
    /** The thread that the listener is running on. */
    @Getter private final Thread thread;

    /** The controller being listened to. */
    @Getter private final Controller controller;

    /** The timer used to poll for, and transmit, new events. */
    @Getter private Timer timer;

    /** The radio used to transmit events. */
    @Getter private final Radio<Event> radio;

    /**
     * The delay between polling the controller for new input, in
     * milliseconds.
     */
    @Getter private int pollDelay;

    /**
     * Constructs a new ControllerListener.
     *
     * @param controller
     *        The controller being listened to.
     *
     * @param pollDelay
     *        The delay between polling the controller for new
     *        input, in milliseconds.
     */
    public ControllerListener(@NonNull final Controller controller, final int pollDelay) {
        this.controller = controller;
        radio = new Radio<>();

        thread = new Thread(this);
        thread.start();
    }

    /**
     * Constructs a new ControllerListener with a poll
     * delay of 16ms.
     *
     * @param controller
     *        The controller being listened to.
     */
    public ControllerListener(@NonNull final Controller controller) {
        this(controller, 16);
    }

    @Override
    public void run() {
        timer = new Timer(pollDelay, e -> {
            controller.poll();

            final EventQueue eventQueue = controller.getEventQueue();
            Event event = new Event();

            while (eventQueue.getNextEvent(event)) {
                radio.transmit(event.getComponent().getName(), event);
                event = new Event();
            }
        });

        timer.start();
    }
}
