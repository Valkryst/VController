package com.valkryst.VController.preset.LogitechRumblePad2Test;

import com.valkryst.VController.DirectionType;
import com.valkryst.VController.preset.LogitechRumblePad2;
import com.valkryst.VController.preset.TestHelperComponent;
import net.java.games.input.Component;
import net.java.games.input.Event;
import org.junit.Assert;
import org.junit.Test;

public class GetAnalogStickDirectionTest {
    private final LogitechRumblePad2 preset = new LogitechRumblePad2();

    @Test(expected=NullPointerException.class)
    public void getAnalogStickDirection_withNullEvent() {
        preset.getButtonType(null);
    }

    @Test
    public void getAnalogStickDirection_left_withX() {
        final Component component = new TestHelperComponent("x");
        final Event event = new Event();

        for (float f = -1.25f ; f < -0.2f ; f += 0.01) {
            event.set(component, f, 0);
            Assert.assertEquals(DirectionType.LEFT, preset.getAnalogStickDirection(event));
        }
    }

    @Test
    public void getAnalogStickDirection_left_withZ() {
        final Component component = new TestHelperComponent("z");
        final Event event = new Event();

        for (float f = -1.25f ; f < -0.2f ; f += 0.01) {
            event.set(component, f, 0);
            Assert.assertEquals(DirectionType.LEFT, preset.getAnalogStickDirection(event));
        }
    }

    @Test
    public void getAnalogStickDirection_none_withX() {
        final Component component = new TestHelperComponent("x");
        final Event event = new Event();

        for (float f = -0.1f ; f < 0.1f ; f += 0.01) {
            event.set(component, f, 0);
            Assert.assertEquals(DirectionType.NONE, preset.getAnalogStickDirection(event));
        }
    }

    @Test
    public void getAnalogStickDirection_none_withZ() {
        final Component component = new TestHelperComponent("z");
        final Event event = new Event();

        for (float f = -0.1f ; f < 0.1f ; f += 0.01) {
            event.set(component, f, 0);
            Assert.assertEquals(DirectionType.NONE, preset.getAnalogStickDirection(event));
        }
    }

    @Test
    public void getAnalogStickDirection_right_withX() {
        final Component component = new TestHelperComponent("x");
        final Event event = new Event();

        for (float f = 0.2f ; f < 1.25f ; f += 0.01) {
            event.set(component, f, 0);
            Assert.assertEquals(DirectionType.RIGHT, preset.getAnalogStickDirection(event));
        }
    }

    @Test
    public void getAnalogStickDirection_right_withZ() {
        final Component component = new TestHelperComponent("z");
        final Event event = new Event();

        for (float f = 0.2f ; f < 1.25f ; f += 0.01) {
            event.set(component, f, 0);
            Assert.assertEquals(DirectionType.RIGHT, preset.getAnalogStickDirection(event));
        }
    }

    @Test
    public void getAnalogStickDirection_up_withY() {
        final Component component = new TestHelperComponent("y");
        final Event event = new Event();

        for (float f = -1.25f ; f < -0.2f ; f += 0.01) {
            event.set(component, f, 0);
            Assert.assertEquals(DirectionType.UP, preset.getAnalogStickDirection(event));
        }
    }

    @Test
    public void getAnalogStickDirection_up_withRZ() {
        final Component component = new TestHelperComponent("rz");
        final Event event = new Event();

        for (float f = -1.25f ; f < -0.2f ; f += 0.01) {
            event.set(component, f, 0);
            Assert.assertEquals(DirectionType.UP, preset.getAnalogStickDirection(event));
        }
    }

    @Test
    public void getAnalogStickDirection_none_withY() {
        final Component component = new TestHelperComponent("y");
        final Event event = new Event();

        for (float f = -0.1f ; f < 0.1f ; f += 0.01) {
            event.set(component, f, 0);
            Assert.assertEquals(DirectionType.NONE, preset.getAnalogStickDirection(event));
        }
    }

    @Test
    public void getAnalogStickDirection_none_withRZ() {
        final Component component = new TestHelperComponent("rz");
        final Event event = new Event();

        for (float f = -0.1f ; f < 0.1f ; f += 0.01) {
            event.set(component, f, 0);
            Assert.assertEquals(DirectionType.NONE, preset.getAnalogStickDirection(event));
        }
    }

    @Test
    public void getAnalogStickDirection_down_withY() {
        final Component component = new TestHelperComponent("y");
        final Event event = new Event();

        for (float f = 0.2f ; f < 1.25f ; f += 0.01) {
            event.set(component, f, 0);
            Assert.assertEquals(DirectionType.DOWN, preset.getAnalogStickDirection(event));
        }
    }

    @Test
    public void getAnalogStickDirection_down_withRZ() {
        final Component component = new TestHelperComponent("rz");
        final Event event = new Event();

        for (float f = 0.2f ; f < 1.25f ; f += 0.01) {
            event.set(component, f, 0);
            Assert.assertEquals(DirectionType.DOWN, preset.getAnalogStickDirection(event));
        }
    }
}
