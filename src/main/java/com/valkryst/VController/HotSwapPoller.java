package com.valkryst.VController;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HotSwapPoller {
    /** Singleton instance. */
    private final static HotSwapPoller INSTANCE = new HotSwapPoller();

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
     * @throws InvocationTargetException  If there is an error getting the {@link Controller}s.
     * @throws NoSuchFieldException  If there is an error getting the {@link Controller}s.
     * @throws NoSuchMethodException  If there is an error getting the {@link Controller}s.
     */
    public void start() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException, NoSuchMethodException {
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
     *
     * @param device
     * @return
     *
     * @throws ClassNotFoundException If the {@code RawDevice}, {@code RawDeviceInfo}, {@code RawInputEnvironmentPlugin}, or {@code SetupAPIDevice} classes cannot be found.
     * @throws NoSuchMethodException If the {@code RawDeviceInfo#createControllerFromDevice}, {@code RawInputEnvironmentPlugin#enumSetupAPIDevices}, {@code RawDevice#getInfo}, or {@code RawDevice#getName} methods cannot be found.
     * @throws IllegalAccessException If there is an error invoking the {@code RawDeviceInfo#createControllerFromDevice}, {@code RawInputEnvironmentPlugin#enumSetupAPIDevices}, {@code RawDevice#getInfo}, or {@code RawDevice#getName} methods.
     * @throws InvocationTargetException If there is an error invoking the {@code RawDeviceInfo#createControllerFromDevice}, {@code RawInputEnvironmentPlugin#enumSetupAPIDevices}, {@code RawDevice#getInfo}, or {@code RawDevice#getName} methods.
     */
    private Controller createControllerFromDevice(final Object device) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        final var rawDeviceClass = Class.forName("net.java.games.input.RawDevice");
        final var rawEnvironmentPluginClass = Class.forName("net.java.games.input.RawInputEnvironmentPlugin");
        final var setupApiDeviceClass = Class.forName("net.java.games.input.SetupAPIDevice");

        // Prepare Methods
        final var enumSetupAPIDevicesMethod = rawEnvironmentPluginClass.getDeclaredMethod("enumSetupAPIDevices");
        enumSetupAPIDevicesMethod.setAccessible(true);

        final var getInfoMethod = rawDeviceClass.getDeclaredMethod("getInfo");
        getInfoMethod.setAccessible(true);

        final var getNameMethod = rawDeviceClass.getDeclaredMethod("getName");
        getNameMethod.setAccessible(true);

        final var lookupSetupApiDeviceMethod = rawEnvironmentPluginClass.getDeclaredMethod(
                "lookupSetupAPIDevice",
                String.class,
                List.class
        );
        lookupSetupApiDeviceMethod.setAccessible(true);

        // Get setupApiDevices
        final var setupApiDevices = enumSetupAPIDevicesMethod.invoke(null);
        final var setupApiDevice = lookupSetupApiDeviceMethod.invoke(null, getNameMethod.invoke(device), setupApiDevices);

        if (setupApiDevice == null) {
            // Either the device is an RDP or we failed to locate the SetupAPI device that matches. - JInput Source Code Comment
            return null;
        }

        final var rawDeviceInfo = getInfoMethod.invoke(device);
        final var createControllerFromDeviceMethod = rawDeviceInfo.getClass().getDeclaredMethod(
                "createControllerFromDevice",
                rawDeviceClass,
                setupApiDeviceClass
        );
        createControllerFromDeviceMethod.setAccessible(true);

        final var controller = createControllerFromDeviceMethod.invoke(rawDeviceInfo, device, setupApiDevice);

        return (Controller) controller;
    }

    /**
     * Retrieves the set of currently detected {@link Controller}s.
     *
     * @return The set of currently detected {@link Controller}s.
     *
     * @throws ClassNotFoundException If the {@code DefaultControllerEnvironment} class cannot be found.
     * @throws IllegalAccessException If there is an error instantiating {@code DefaultControllerEnvironment}.
     * @throws IllegalStateException If the current {@link ControllerEnvironment} is not supported.
     * @throws InvocationTargetException If there is an error invoking the constructor of {@code DefaultControllerEnvironment}.
     * @throws NoSuchFieldException If the {@code defaultEnvironment} field of {@link ControllerEnvironment} cannot be found.
     * @throws NoSuchMethodException If the {@code DefaultControllerEnvironment} class does not have a default constructor.
     */
    private List<Controller> getControllers() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, NoSuchMethodException {
        /*
         * At the time of writing, JInput does not support hot-swapping controllers. When you query the default
         * ControllerEnvironment, it will always return the same set of controllers.
         *
         * To work around this issue, we need to hackily reinitialize the default environment and kill all threads
         * used by the old environment.
         */

        final var previousRawDevices = new ArrayList<>();
        for (final var thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getClass().getName().equals("net.java.games.input.RawInputEventQueue$QueueThread")) {
                Field field = thread.getClass().getDeclaredField("this$0");
                field.setAccessible(true);

                final var enclosingObject = field.get(thread);

                field = thread.getClass().getEnclosingClass().getDeclaredField("devices");
                field.setAccessible(true);

                previousRawDevices.addAll((List<?>) field.get(enclosingObject));
                break;
            }
        }

        final var currentRawDevices = getRawDevices();

        // Print name of all previous and all current devices separatly
        System.out.println("Previous Devices:");
        for (final var device : previousRawDevices) {
            final var controller = createControllerFromDevice(device);

            if (controller != null) {
                System.out.println("\t" + controller.getName());
            }
        }

        System.out.println("\nCurrent Devices:");
        for (final var device : currentRawDevices) {
            final var controller = createControllerFromDevice(device);

            if (controller != null) {
                System.out.println("\t" + controller.getName());
            }
        }

        final var addedRawDevices = new ArrayList<>(currentRawDevices);
        addedRawDevices.removeAll(previousRawDevices);
        // todo Add added ones

        final var removedRawDevices = new ArrayList<>(previousRawDevices);
        removedRawDevices.removeAll(currentRawDevices);
        // todo Eliminate removed ones


//        Class<?> c = Class.forName("net.java.games.input.DefaultControllerEnvironment");
//        Constructor<?> constructor = c.getDeclaredConstructor();
//        constructor.setAccessible(true);
//
//        final var field = ControllerEnvironment.class.getDeclaredField("defaultEnvironment");
//        field.setAccessible(true);
//        field.set(null, constructor.newInstance());

        final var environment = ControllerEnvironment.getDefaultEnvironment();
        if (!environment.isSupported()) {
            throw new IllegalStateException("The current ControllerEnvironment is not supported.");
        }
        return new ArrayList<>(List.of(environment.getControllers()));
    }

    /**
     * Retrieves the set of currently detected {@code RawDevice}s.
     *
     * @return The set of currently detected {@code RawDevice}s.
     *
     * @throws ClassNotFoundException If the {@code RawInputEnvironmentPlugin} or {@code RawInputEventQueue} classes cannot be found.
     * @throws IllegalAccessException If there is an error invoking the {@code enumRawDevices} method of {@code RawInputEnvironmentPlugin}.
     * @throws InvocationTargetException If there is an error invoking the {@code enumRawDevices} method of {@code RawInputEnvironmentPlugin}.
     * @throws NoSuchMethodException If the {@code RawInputEnvironmentPlugin} class does not have a method named {@code enumRawDevices}.
     */
    private List<?> getRawDevices() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        final var rawInputEventQueueClass = Class.forName("net.java.games.input.RawInputEventQueue");
        final var rawInputEnvironmentPluginClass = Class.forName("net.java.games.input.RawInputEnvironmentPlugin");

        final var enumerateDevicesMethod = rawInputEnvironmentPluginClass.getDeclaredMethod(
            "enumerateDevices",
            rawInputEventQueueClass,
            List.class
        );

        /*
         * The first parameter of RawInputEnvironmentPlugin#enumerateDevices is the RawInputEventQueue, which the native
         * enumerateDevices method uses when constructing RawDevice objects. However, the queue variable doesn't seem to
         * be used in the RawDevice class, so it seems safe to pass in null.
         */
        enumerateDevicesMethod.setAccessible(true);

        final var devices = new ArrayList<>();
        enumerateDevicesMethod.invoke(null, null, devices);
        return devices;
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
