package com.valkryst.VController;

import com.valkryst.VController.preset.ControllerPreset;
import com.valkryst.VController.preset.LogitechRumblePad2;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

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
     *        cannot be loaded.
     */
    public static Controller[] getAllControllers() {
        try {
            return ControllerEnvironment.getDefaultEnvironment().getControllers();
        } catch (final UnsatisfiedLinkError e) {
            // The exception totally ignores the catch block.
            // Can't find a reason why.
        } finally {
            addLibrariesToPath();
            return ControllerEnvironment.getDefaultEnvironment().getControllers();
        }
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

    /** Adds the libraries folder to the path. */
    public static void addLibrariesToPath() {
        String path = System.getProperty("java.library.path");

        if (path.isEmpty() == false) {
            path += ":";
        }

        path += System.getProperty("user.dir") + "/libraries/JInput";

        System.setProperty("java.library.path", path);
    }
}
