package com.valkryst.VController;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HotSwapPoller {
    /** Singleton instance. */
    private final static HotSwapPoller INSTANCE = new HotSwapPoller();

    /** JInput threads to kill when reinitializing the {@code net.java.games.input.DefaultControllerEnvironment}. */
    private final static String[] JINPUT_THREADS_TO_KILL = {
        "net.java.games.input.RawInputEventQueue$QueueThread"
    };


    /** {@link HotSwapListener}s to notify when the set of {@link Controller}s changes. */
    private final List<HotSwapListener> listeners = new CopyOnWriteArrayList<>();

    /** {@link ScheduledExecutorService} used to poll the {@link Controller}s and notify {@code listeners}. */
    private ScheduledExecutorService executorService;

    /** Private constructor to prevent instantiation. */
    private HotSwapPoller() {}

    /**
     * <p>Starts polling the {@link Controller}s.</p>
     *
     * <p>See {@link ScheduledExecutorService#scheduleAtFixedRate(Runnable, long, long, TimeUnit)} for caveats.</p>
     *
     * @throws ClassNotFoundException If there is an error getting the {@link Controller}s.
     * @throws IllegalAccessException  If there is an error getting the {@link Controller}s.
     * @throws IllegalStateException If the {@link HotSwapPoller} is already running or if the current {@link ControllerEnvironment} is not supported.
     * @throws InstantiationException If there is an error getting the {@link Controller}s.
     * @throws InterruptedException If there is an error getting the {@link Controller}s.
     * @throws InvocationTargetException  If there is an error getting the {@link Controller}s.
     * @throws NoSuchFieldException  If there is an error getting the {@link Controller}s.
     * @throws NoSuchMethodException  If there is an error getting the {@link Controller}s.
     */
    public void start() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InterruptedException, InvocationTargetException, NoSuchFieldException, NoSuchMethodException {
        final var oldControllers = getControllers();

        synchronized (this) {
            if (executorService != null) {
                throw new IllegalStateException("HotSwapPoller is already running.");
            }

            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(() -> {
                final List<Controller> newControllers;
                try {
                    newControllers = getControllers();
                } catch (final Exception e) {
                    e.printStackTrace();
                    stop();
                    return;
                }

                final var addedControllers = new ArrayList<>(newControllers);
                final var removedControllers = new ArrayList<>(oldControllers);

                for (final var oldController : oldControllers) {
                    for (final var newController: newControllers) {
                        if (oldController.getName().equals(newController.getName())) {
                            addedControllers.remove(newController);
                            removedControllers.remove(oldController);
                        }
                    }
                }

                listeners.forEach(listener -> addedControllers.forEach(listener::controllerAdded));
                listeners.forEach(listener -> removedControllers.forEach(listener::controllerRemoved));

                oldControllers.clear();
                oldControllers.addAll(newControllers);
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    /**
     * Stops polling the {@link Controller}s.
     *
     * @throws IllegalStateException If the {@link HotSwapPoller} is not running.
     */
    public void stop() {
        synchronized (this) {
            if (executorService == null) {
                throw new IllegalStateException("HotSwapPoller is not running.");
            }

            executorService.shutdown();
            executorService = null;
        }
    }

    /**
     * Adds a {@link HotSwapListener} to {@code listeners}.
     *
     * @param listener Listener to add.
     * @throws NullPointerException If {@code listener} is {@code null}.
     */
    public void addListener(final HotSwapListener listener) {
        Objects.requireNonNull(listener);
        listeners.add(listener);
    }

    /**
     * Removes a {@link HotSwapListener} from {@code listeners}.
     *
     * @param listener Listener to remove.
     * @throws NullPointerException If {@code listener} is {@code null}.
     */
    public void removeListener(final HotSwapListener listener) {
        Objects.requireNonNull(listener);
        listeners.remove(listener);
    }

    /**
     * Retrieves the set of currently detected {@link Controller}s.
     *
     * @return The set of currently detected {@link Controller}s.
     *
     * @throws ClassNotFoundException If the {@code DefaultControllerEnvironment} class cannot be found.
     * @throws IllegalAccessException If there is an error instantiating {@code DefaultControllerEnvironment}.
     * @throws IllegalStateException If the current {@link ControllerEnvironment} is not supported.
     * @throws InstantiationException If there is an error instantiating {@code DefaultControllerEnvironment}.
     * @throws InterruptedException If there is an error interrupting one of the threads used by {@code DefaultControllerEnvironment}. This will lead to a memory leak.
     * @throws InvocationTargetException If there is an error invoking the constructor of {@code DefaultControllerEnvironment}.
     * @throws NoSuchFieldException If the {@code defaultEnvironment} field of {@link ControllerEnvironment} cannot be found.
     * @throws NoSuchMethodException If the {@code DefaultControllerEnvironment} class does not have a default constructor.
     */
    private List<Controller> getControllers() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InterruptedException, InvocationTargetException, NoSuchFieldException, NoSuchMethodException {
        /*
         * At the time of writing, JInput does not support hot-swapping controllers. When you query the default
         * ControllerEnvironment, it will always return the same set of controllers.
         *
         * To work around this issue, we need to hackily reinitialize the default environment and kill all threads
         * used by the old environment.
         */
        for (final var thread : Thread.getAllStackTraces().keySet()) {
            if (Arrays.asList(JINPUT_THREADS_TO_KILL).contains(thread.getClass().getName())) {
                thread.interrupt();
                thread.join();
                break;
            }
        }

        Class<?> c = Class.forName("net.java.games.input.DefaultControllerEnvironment");
        Constructor<?> constructor = c.getDeclaredConstructor();
        constructor.setAccessible(true);

        final var field = ControllerEnvironment.class.getDeclaredField("defaultEnvironment");
        field.setAccessible(true);
        field.set(null, constructor.newInstance());

        final var environment = ControllerEnvironment.getDefaultEnvironment();
        if (!environment.isSupported()) {
            throw new IllegalStateException("The current ControllerEnvironment is not supported.");
        }
        return new ArrayList<>(List.of(environment.getControllers()));
    }

    /**
     * Retrieves the singleton instance.
     *
     * @return The singleton instance.
     */
    public static HotSwapPoller getInstance() {
        return INSTANCE;
    }
}
