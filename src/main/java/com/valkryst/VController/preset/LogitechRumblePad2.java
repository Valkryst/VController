package com.valkryst.VController.preset;

import com.valkryst.VController.enums.Button;
import com.valkryst.VController.enums.Direction;
import lombok.NonNull;
import net.java.games.input.Event;

public class LogitechRumblePad2 implements ControllerPreset {
    @Override
    public Button[] getSupportedButtonTypes() {
        return new Button[] {
                Button.UNKNOWN,

                Button.LEFT_SHOULDER_TOP,
                Button.LEFT_SHOULDER_BOTTOM,
                Button.RIGHT_SHOULDER_TOP,
                Button.RIGHT_SHOULDER_BOTTOM,

                Button.DPAD,

                Button.FACE_TOP,
                Button.FACE_BOTTOM,
                Button.FACE_LEFT,
                Button.FACE_RIGHT,

                Button.LEFT_STICK,
                Button.RIGHT_STICK,

                Button.LEFT_STICK_BUTTON,
                Button.RIGHT_STICK_BUTTON,

                Button.MISC_1,
                Button.MISC_2
        };
    }

    @Override
    public Button getButtonType(final @NonNull Event event) {
        switch (event.getComponent().getName()) {
            // Shoulder Buttons.
            case "Button 4":
            case "Top 2": {
                return Button.LEFT_SHOULDER_TOP;
            }

            case "Button 6":
            case "Base": {
                return Button.LEFT_SHOULDER_BOTTOM;
            }

            case "Button 5":
            case "Pinkie": {
                return Button.RIGHT_SHOULDER_TOP;
            }

            case "Button 7":
            case "Base 2": {
                return Button.RIGHT_SHOULDER_BOTTOM;
            }


            // Directional Pad Buttons.
            case "Hat Switch":
            case "pov": {
                return Button.DPAD;
            }


            // Face Pad Buttons.
            case "Button 3":
            case "Top": {
                return Button.FACE_TOP;
            }

            case "Button 1":
            case "Thumb": {
                return Button.FACE_BOTTOM;
            }

            case "Button 0":
            case "Trigger": {
                return Button.FACE_LEFT;
            }

            case "Button 2":
            case "Thumb 2": {
                return Button.FACE_RIGHT;
            }


            // Analog Stick Direction Controls.
            case "X Axis":
            case "x":
            case "Y Axis":
            case "y": {
                return Button.LEFT_STICK;
            }

            case "Z Axis":
            case "z":
            case "Z Rotation":
            case "rz": {
                return Button.RIGHT_STICK;
            }


            // Analog Stick Buttons.
            case "Button 10":
            case "Base 5": {
                return Button.LEFT_STICK_BUTTON;
            }

            case "Button 11":
            case "Base 6": {
                return Button.RIGHT_STICK_BUTTON;
            }


            // Center Buttons
            case "Button 8":
            case "Base 3": {
                return Button.MISC_1;
            }

            case "Button 9":
            case "Base 4": {
                return Button.MISC_2;
            }


            default: {
                return Button.UNKNOWN;
            }
        }
    }

    @Override
    public Direction getDPadDirection(final @NonNull Event event) {
        final float value = event.getValue();

        // Up is 0.25
        if (isInRange(value, 0.2f, 0.3f)) {
            return Direction.UP;
        }

        // Up-Left is 0.125
        if (isInRange(value, 0.075f, 0.175f)) {
            return Direction.UP_LEFT;
        }

        // Up-Right is 0.375
        if (isInRange(value, 0.325f, 0.425f)) {
            return Direction.UP_RIGHT;
        }

        // Down is 0.75
        if (isInRange(value, 0.7f, 0.8f)) {
            return Direction.DOWN;
        }

        // Down-Left is 0.875
        if (isInRange(value, 0.825f, 0.925f)) {
            return Direction.DOWN_LEFT;
        }

        // Down-Right is 0.625
        if (isInRange(value, 0.575f, 0.675f)) {
            return Direction.DOWN_RIGHT;
        }

        // Left is 1.0
        if (isInRange(value, 0.95f, 1.0f)) {
            return Direction.LEFT;
        }

        // Right is 0.5
        if (isInRange(value, 0.45f, 0.55f)) {
            return Direction.RIGHT;
        }

        // None is 0.0
        if (isInRange(value, 0.0f, 0.05f)) {
            return Direction.NONE;
        }

        return Direction.UNKNOWN;
    }

    @Override
    public Direction getAnalogStickDirection(final @NonNull Event event) {
        final float value = event.getValue();

        switch (event.getComponent().getName()) {
            // Left-Right Axis for Left/Right Analog Sticks.
            case "X Axis":
            case "x":
            case "Z Axis":
            case "z": {
                // Left is -1.0
                if (isInRange(value, -1.25f, -0.2f)) {
                    return Direction.LEFT;
                }

                // Center is 0.0
                if (isInRange(value, -0.1f, 0.1f)) {
                    return Direction.NONE;
                }

                // Right is 1.0
                if (isInRange(value, 0.2f, 1.25f)) {
                    return Direction.RIGHT;
                }
            }

            // Up-Down Axis for Left/Right Analog Sticks.
            case "Y Axis":
            case "y":
            case "Z Rotation":
            case "rz": {
                // Up is -1.0
                if (isInRange(value, -1.25f, -0.2f)) {
                    return Direction.UP;
                }

                // Center is 0.0
                if (isInRange(value, -0.1f, 0.1f)) {
                    return Direction.NONE;
                }

                // Down is 1.0
                if (isInRange(value, 0.2f, 1.25f)) {
                    return Direction.DOWN;
                }
            }

            default: {
                return Direction.UNKNOWN;
            }
        }
    }
}
