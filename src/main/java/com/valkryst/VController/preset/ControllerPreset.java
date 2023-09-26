package com.valkryst.VController.preset;

import com.valkryst.VController.ButtonType;
import com.valkryst.VController.DirectionType;
import lombok.NonNull;
import net.java.games.input.Event;

public interface ControllerPreset {
    /**
     * Retrieves an array of all button types used
     * and supported by the controller.
     *
     * @return
     *         The button types that are used and
     *         supported by the controller.
     */
    default ButtonType[] getSupportedButtonTypes() {
        return new ButtonType[] {ButtonType.UNKNOWN};
    }

    /**
     * Determines the button that caused an event.
     *
     * @param event
     *        The event.
     *
     * @return
     *        The button that caused the event.
     *
     * @throws NullPointerException
     *        If the event is null.
     */
    default ButtonType getButtonType(final @NonNull Event event) {
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
     *
     * @throws NullPointerException
     *        If the event is null.
     */
    default DirectionType getDPadDirection(final @NonNull Event event) {
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
     *
     * @throws NullPointerException
     *        If the event is null.
     */
    default DirectionType getAnalogStickDirection(final @NonNull Event event) {
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
            throw new IllegalArgumentException("The min (" + min + ") value cannot be greater than the max (" + max + ") value.");
        }

        boolean isInRange = min <= value;
        isInRange &= value <= max;
        return isInRange;
    }
}
