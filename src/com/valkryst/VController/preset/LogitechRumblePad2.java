package com.valkryst.VController.preset;

import com.valkryst.VController.ButtonType;
import com.valkryst.VController.DirectionType;
import lombok.NonNull;
import net.java.games.input.Event;

public class LogitechRumblePad2 implements ControllerPreset {
    @Override
    public ButtonType[] getSupportedButtonTypes() {
        return new ButtonType[] {
                ButtonType.UNKNOWN,

                ButtonType.LEFT_SHOULDER_TOP,
                ButtonType.LEFT_SHOULDER_BOTTOM,
                ButtonType.RIGHT_SHOULDER_TOP,
                ButtonType.RIGHT_SHOULDER_BOTTOM,

                ButtonType.DPAD,

                ButtonType.FACE_TOP,
                ButtonType.FACE_BOTTOM,
                ButtonType.FACE_LEFT,
                ButtonType.FACE_RIGHT,

                ButtonType.LEFT_STICK,
                ButtonType.RIGHT_STICK,

                ButtonType.LEFT_STICK_BUTTON,
                ButtonType.RIGHT_STICK_BUTTON,

                ButtonType.MISC_1,
                ButtonType.MISC_2
        };
    }

    @Override
    public ButtonType getButtonType(final @NonNull Event event) {
        switch (event.getComponent().getName()) {
            // Shoulder Buttons.
            case "Button 4":
            case "Top 2": {
                return ButtonType.LEFT_SHOULDER_TOP;
            }

            case "Button 6":
            case "Base": {
                return ButtonType.LEFT_SHOULDER_BOTTOM;
            }

            case "Button 5":
            case "Pinkie": {
                return ButtonType.RIGHT_SHOULDER_TOP;
            }

            case "Button 7":
            case "Base 2": {
                return ButtonType.RIGHT_SHOULDER_BOTTOM;
            }


            // Directional Pad Buttons.
            case "Hat Switch":
            case "pov": {
                return ButtonType.DPAD;
            }


            // Face Pad Buttons.
            case "Button 3":
            case "Top": {
                return ButtonType.FACE_TOP;
            }

            case "Button 1":
            case "Thumb": {
                return ButtonType.FACE_BOTTOM;
            }

            case "Button 0":
            case "Trigger": {
                return ButtonType.FACE_LEFT;
            }

            case "Button 2":
            case "Thumb 2": {
                return ButtonType.FACE_RIGHT;
            }


            // Analog Stick Direction Controls.
            case "X Axis":
            case "x":
            case "Y Axis":
            case "y": {
                return ButtonType.LEFT_STICK;
            }

            case "Z Axis":
            case "z":
            case "Z Rotation":
            case "rz": {
                return ButtonType.RIGHT_STICK;
            }


            // Analog Stick Buttons.
            case "Button 10":
            case "Base 5": {
                return ButtonType.LEFT_STICK_BUTTON;
            }

            case "Button 11":
            case "Base 6": {
                return ButtonType.RIGHT_STICK_BUTTON;
            }


            // Center Buttons
            case "Button 8":
            case "Base 3": {
                return ButtonType.MISC_1;
            }

            case "Button 9":
            case "Base 4": {
                return ButtonType.MISC_2;
            }


            default: {
                return ButtonType.UNKNOWN;
            }
        }
    }

    @Override
    public DirectionType getDPadDirection(final @NonNull Event event) {
        final float value = event.getValue();

        // Up is 0.25
        if (isInRange(value, 0.2f, 0.3f)) {
            return DirectionType.UP;
        }

        // Up-Left is 0.125
        if (isInRange(value, 0.075f, 0.175f)) {
            return DirectionType.UP_LEFT;
        }

        // Up-Right is 0.375
        if (isInRange(value, 0.325f, 0.425f)) {
            return DirectionType.UP_RIGHT;
        }

        // Down is 0.75
        if (isInRange(value, 0.7f, 0.8f)) {
            return DirectionType.DOWN;
        }

        // Down-Left is 0.875
        if (isInRange(value, 0.825f, 0.925f)) {
            return DirectionType.DOWN_LEFT;
        }

        // Down-Right is 0.625
        if (isInRange(value, 0.575f, 0.675f)) {
            return DirectionType.DOWN_RIGHT;
        }

        // Left is 1.0
        if (isInRange(value, 0.95f, 1.0f)) {
            return DirectionType.LEFT;
        }

        // Right is 0.5
        if (isInRange(value, 0.45f, 0.55f)) {
            return DirectionType.RIGHT;
        }

        // None is 0.0
        if (isInRange(value, 0.0f, 0.05f)) {
            return DirectionType.NONE;
        }

        return DirectionType.UNKNOWN;
    }

    @Override
    public DirectionType getAnalogStickDirection(final @NonNull Event event) {
        final float value = event.getValue();

        switch (event.getComponent().getName()) {
            // Left-Right Axis for Left/Right Analog Sticks.
            case "X Axis":
            case "x":
            case "Z Axis":
            case "z": {
                // Left is -1.0
                if (isInRange(value, -1.25f, -0.2f)) {
                    return DirectionType.LEFT;
                }

                // Center is 0.0
                if (isInRange(value, -0.1f, 0.1f)) {
                    return DirectionType.NONE;
                }

                // Right is 1.0
                if (isInRange(value, 0.2f, 1.25f)) {
                    return DirectionType.RIGHT;
                }
            }

            // Up-Down Axis for Left/Right Analog Sticks.
            case "Y Axis":
            case "y":
            case "Z Rotation":
            case "rz": {
                // Up is -1.0
                if (isInRange(value, -1.25f, -0.2f)) {
                    return DirectionType.UP;
                }

                // Center is 0.0
                if (isInRange(value, -0.1f, 0.1f)) {
                    return DirectionType.NONE;
                }

                // Down is 1.0
                if (isInRange(value, 0.2f, 1.25f)) {
                    return DirectionType.DOWN;
                }
            }

            default: {
                return DirectionType.UNKNOWN;
            }
        }
    }
}
