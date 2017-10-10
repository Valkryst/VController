package com.valkryst.VController;

import com.valkryst.VController.preset.ControllerPreset;
import com.valkryst.VController.preset.LogitechRumblePad2;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.util.ArrayList;
import java.util.List;

public class ControllerHelper {
    /**
     * Retrieves all connected controllers.
     *
     * @return
     *        The array of connected controllers.
     */
    public static Controller[] getAllControllers() {
        return ControllerEnvironment.getDefaultEnvironment().getControllers();
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
                case "Logitech RumblePad 2 USB":
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
            case "Logitech RumblePad 2 USB":
            case "Logitech Logitech RumblePad 2 USB": {
                return new LogitechRumblePad2();
            }

            default: {
                throw new UnsupportedOperationException("No preset exists for the '" + controller.getName() + "' controller.");
            }
        }
    }
}
