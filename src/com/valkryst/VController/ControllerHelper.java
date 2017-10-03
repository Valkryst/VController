package com.valkryst.VController;

import com.valkryst.VController.preset.ControllerPreset;
import com.valkryst.VController.preset.LogitechRumblePad2;
import lombok.Getter;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ControllerHelper {
    static {
        try {
            instance = new ControllerHelper();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Getter private static ControllerHelper instance;

    /**
     * Constructs a new ControllerHelper
     *
     * @throws NoSuchFieldException
     *        If there was an issue adding the libraries folder to the path.
     *
     * @throws IllegalAccessException
     *        If there was an issue adding the libraries folder to the path.
     */
    private ControllerHelper() throws NoSuchFieldException, IllegalAccessException {
        addLibrariesToPath();
    }

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
    public Controller[] getAllControllers() {
        return ControllerEnvironment.getDefaultEnvironment().getControllers();
    }

    /**
     * Retrieves all connected controllers that are supported.
     *
     * @return
     *        The list of supported connected controllers.
     */
    public List<Controller> getSupportedControllers() {
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
    public ControllerPreset getControllerPreset(final Controller controller) {
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
    public void addLibrariesToPath() throws NoSuchFieldException, IllegalAccessException {
        String path = System.getProperty("java.library.path");

        if (path.isEmpty() == false) {
            path += ":";
        }

        path += System.getProperty("user.dir") + "/libraries/JInput";

        System.setProperty("java.library.path", path);

        // Set the sys_paths field to null.
        // This will cause the library paths to be re-initialized when a library
        // is next loaded.
        final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
        sysPathsField.setAccessible(true);
        sysPathsField.set(null, null);
    }
}
