package com.valkryst.VController;

import com.valkryst.VController.preset.ControllerPreset;
import com.valkryst.VController.preset.LogitechRumblePad2;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class ControllerHelper {
    // Prevent users from creating an instance.
    private ControllerHelper() {}

    /**
     * Retrieves all connected controllers.
     *
     * @return
     *        The array of connected controllers.
     *
     * @throws UnsatisfiedLinkError
     *        If one of the libraries, required by JInput,
     *        cannot be laded.
     *
     * @throws IllegalStateException
     *        If one of the libraries, required by JInput,
     *        cannot be loaded and if the fallback code
     *        also fails.
     */
    public static Controller[] getAllControllers() {
        try {
            return ControllerEnvironment.getDefaultEnvironment().getControllers();
        } catch (final UnsatisfiedLinkError e) {
            try {
                System.out.println("Yeah, catch block is running");
                addLibrariesToPath();
                return ControllerEnvironment.getDefaultEnvironment().getControllers();
            } catch (IllegalAccessException | NoSuchFieldException ee) {
                ee.printStackTrace();
            }
        }

        throw new IllegalStateException("Unable to load libraries. Fallback method " +
                                        "has also failed.");
    }

    /**
     * Retrieves all connected controllers that are supported.
     *
     * @return
     *        The list of supported connected controllers.
     */
    public static List<Controller> getSupportedControllers() {
        final Controller[] controllers = getAllControllers();
        final List<Controller> supportedControllers = new ArrayList<>();

        for (final Controller controller : controllers) {
            switch (controller.getName()) {
                case "Logitech Logitech RumblePad 2 USB": {
                    supportedControllers.add(controller);
                    break;
                }
            }
        }

        return supportedControllers;
    }

    /**
     * Retrieves the preset of a controller.
     *
     * @param controller
     *        The controller.
     *
     * @return
     *        The preset.
     *
     * @throws UnsupportedOperationException
     *        If no preset exists for the controller.
     */
    public static ControllerPreset getControllerPreset(final Controller controller) {
        switch (controller.getName()) {
            case "Logitech Logitech RumblePad 2 USB": {
                return new LogitechRumblePad2();
            }

            default: {
                throw new UnsupportedOperationException("No preset exists for the '" + controller.getName() + "' controller.");
            }
        }
    }

    /**
     * Adds the libraries folder to the path.
     *
     * @throws NoSuchFieldException
     *        If the sys_paths field cannot be found.
     *
     * @throws IllegalAccessException
     *       If the sys_paths field is final or inaccessible.
     */
    public static void addLibrariesToPath() throws NoSuchFieldException, IllegalAccessException {
        String path = System.getProperty("java.library.path");

        if (path.isEmpty() == false) {
            path += ":";
        }

        path += System.getProperty("user.dir") + "/libraries/JInput";

        System.setProperty("java.library.path", path);

        // Set the sys_paths field to null.
        // This will cause the library paths to be re-initalized when a library
        // is next loaded.
        final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
        sysPathsField.setAccessible(true);
        sysPathsField.set(null, null);
    }
}
