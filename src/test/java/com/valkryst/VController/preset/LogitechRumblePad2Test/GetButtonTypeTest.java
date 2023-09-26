package com.valkryst.VController.preset.LogitechRumblePad2Test;

import com.valkryst.VController.ButtonType;
import com.valkryst.VController.preset.LogitechRumblePad2;
import com.valkryst.VController.preset.TestHelperComponent;
import net.java.games.input.Component;
import net.java.games.input.Event;
import org.junit.Assert;
import org.junit.Test;

public class GetButtonTypeTest {
    private final LogitechRumblePad2 preset = new LogitechRumblePad2();

    @Test(expected=NullPointerException.class)
    public void getButtonType_withNullEvent() {
        preset.getButtonType(null);
    }

    @Test
    public void getButtonType_withLeftTopShoulder() {
        final Component component = new TestHelperComponent("Top 2");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.LEFT_SHOULDER_TOP, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withLeftBottomShoulder() {
        final Component component = new TestHelperComponent("Base");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.LEFT_SHOULDER_BOTTOM, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withRightTopShoulder() {
        final Component component = new TestHelperComponent("Pinkie");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.RIGHT_SHOULDER_TOP, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withRightBottomShoulder() {
        final Component component = new TestHelperComponent("Base 2");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.RIGHT_SHOULDER_BOTTOM, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withDPad() {
        final Component component = new TestHelperComponent("pov");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.DPAD, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withFaceTop() {
        final Component component = new TestHelperComponent("Top");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.FACE_TOP, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withFaceBottom() {
        final Component component = new TestHelperComponent("Thumb");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.FACE_BOTTOM, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withFaceLeft() {
        final Component component = new TestHelperComponent("Trigger");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.FACE_LEFT, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withFaceRight() {
        final Component component = new TestHelperComponent("Thumb 2");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.FACE_RIGHT, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withAnalogStickLeft_xAxis() {
        final Component component = new TestHelperComponent("x");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.LEFT_STICK, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withAnalogStickLeft_yAxis() {
        final Component component = new TestHelperComponent("y");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.LEFT_STICK, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withAnalogStickRight_xAxis() {
        final Component component = new TestHelperComponent("z");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.RIGHT_STICK, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withAnalogStickRight_yAxis() {
        final Component component = new TestHelperComponent("rz");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.RIGHT_STICK, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withAnalogStickLeftButton() {
        final Component component = new TestHelperComponent("Base 5");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.LEFT_STICK_BUTTON, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withAnalogStickRightButton() {
        final Component component = new TestHelperComponent("Base 6");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.RIGHT_STICK_BUTTON, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withCenterButton_left() {
        final Component component = new TestHelperComponent("Base 3");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.MISC_1, preset.getButtonType(event));
    }

    @Test
    public void getButtonType_withCenterButton_right() {
        final Component component = new TestHelperComponent("Base 4");
        final Event event = new Event();
        event.set(component, 0f, 0);

        Assert.assertEquals(ButtonType.MISC_2, preset.getButtonType(event));
    }
}
