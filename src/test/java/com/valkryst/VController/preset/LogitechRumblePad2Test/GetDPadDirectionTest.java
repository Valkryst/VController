package com.valkryst.VController.preset.LogitechRumblePad2Test;

import com.valkryst.VController.enums.Direction;
import com.valkryst.VController.preset.LogitechRumblePad2;
import net.java.games.input.Event;
import org.junit.Assert;
import org.junit.Test;

public class GetDPadDirectionTest {
    private final LogitechRumblePad2 preset = new LogitechRumblePad2();

    @Test(expected=NullPointerException.class)
    public void getDPadDirection_withNullEvent() {
        preset.getButtonType(null);
    }

    @Test
    public void getDPadDirection_up() {
        final Event event = new Event();

        for (float f = 0.2f ; f < 0.3f ; f += 0.001) {
            event.set(null, f, 0);
            Assert.assertEquals(Direction.UP, preset.getDPadDirection(event));
        }
    }

    @Test
    public void getDPadDirection_up_left() {
        final Event event = new Event();

        for (float f = 0.075f ; f < 0.175f ; f += 0.001) {
            event.set(null, f, 0);
            Assert.assertEquals(Direction.UP_LEFT, preset.getDPadDirection(event));
        }
    }

    @Test
    public void getDPadDirection_up_right() {
        final Event event = new Event();

        for (float f = 0.325f ; f < 0.425f ; f += 0.001) {
            event.set(null, f, 0);
            Assert.assertEquals(Direction.UP_RIGHT, preset.getDPadDirection(event));
        }
    }

    @Test
    public void getDPadDirection_down() {
        final Event event = new Event();

        for (float f = 0.7f ; f < 0.8f ; f += 0.001) {
            event.set(null, f, 0);
            Assert.assertEquals(Direction.DOWN, preset.getDPadDirection(event));
        }
    }

    @Test
    public void getDPadDirection_down_left() {
        final Event event = new Event();

        for (float f = 0.825f ; f < 0.925f ; f += 0.001) {
            event.set(null, f, 0);
            Assert.assertEquals(Direction.DOWN_LEFT, preset.getDPadDirection(event));
        }
    }

    @Test
    public void getDPadDirection_down_right() {
        final Event event = new Event();

        for (float f = 0.575f ; f < 0.675f ; f += 0.001) {
            event.set(null, f, 0);
            Assert.assertEquals(Direction.DOWN_RIGHT, preset.getDPadDirection(event));
        }
    }

    @Test
    public void getDPadDirection_left() {
        final Event event = new Event();

        for (float f = 0.95f ; f < 1.0f ; f += 0.001) {
            event.set(null, f, 0);
            Assert.assertEquals(Direction.LEFT, preset.getDPadDirection(event));
        }
    }

    @Test
    public void getDPadDirection_right() {
        final Event event = new Event();

        for (float f = 0.45f ; f < 0.55f ; f += 0.001) {
            event.set(null, f, 0);
            Assert.assertEquals(Direction.RIGHT, preset.getDPadDirection(event));
        }
    }

    @Test
    public void getDPadDirection_none() {
        final Event event = new Event();

        for (float f = 0.0f ; f < 0.05f ; f += 0.001) {
            event.set(null, f, 0);
            Assert.assertEquals(Direction.NONE, preset.getDPadDirection(event));
        }
    }
}
