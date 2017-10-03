package com.valkryst.VController.preset;

import com.valkryst.VController.ButtonType;
import com.valkryst.VController.DirectionType;
import net.java.games.input.Event;

public interface ControllerPreset {
    /**
     * Determines the button that caused an event.
     *
     * @param event
     *        The event.
     *
     * @return
     *        The button that caused the event.
     */
    default ButtonType getButtonType(final Event event) {
        return ButtonType.UNKNOWN;
    }

    /**
     * Determines the directional pad's direction based
     * on the value of an event.
     *
     * @param event
     *        The event.
     *
     * @return
     *        The direction.
     */
    default DirectionType getDPadDirection(final Event event) {
        return DirectionType.UNKNOWN;
    }

    /**
     * Determines the analog stick's direction based on
     * the value of an event.
     *
     * @param event
     *        The event.
     *
     * @return
     *        The direction.
     */
    default DirectionType getAnalogStickDirection(final Event event) {
        return DirectionType.UNKNOWN;
    }

    /**
     * Determines if a value is within a range.
     *
     * @param value
     *        The value.
     *
     * @param min
     *        The lowest allowed value.
     *
     * @param max
     *        The highest allowed value.
     *
     * @return
     *        Whether or not the value is within the range.
     *
     * @throws IllegalArgumentException
     *        If the max value is less than the min value.
     */
    default boolean isInRange(final float value, final float min, final float max) {
        if (max < min) {
            throw new IllegalArgumentException("The min (" + min + ") value " +
                                               "cannot be greater than the max (" +
                                               max + ") value.");
        }

        boolean isInRange = min <= value;
        isInRange &= value <= max;
        return isInRange;
    }
}