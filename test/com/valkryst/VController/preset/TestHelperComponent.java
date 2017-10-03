package com.valkryst.VController.preset;

import net.java.games.input.Component;

public class TestHelperComponent implements Component {
    public final String name;

    public TestHelperComponent(final String name) {
        this.name = name;
    }

    @Override
    public Identifier getIdentifier() {
        return null;
    }

    @Override
    public boolean isRelative() {
        return false;
    }

    @Override
    public boolean isAnalog() {
        return false;
    }

    @Override
    public float getDeadZone() {
        return 0;
    }

    @Override
    public float getPollData() {
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }
}
