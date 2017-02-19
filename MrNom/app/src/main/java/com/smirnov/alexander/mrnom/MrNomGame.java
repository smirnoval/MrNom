package com.smirnov.alexander.mrnom;

import com.smirnov.alexander.framework.Screen;
import com.smirnov.alexander.framework.impl.AndroidGame;

public class MrNomGame extends AndroidGame {
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
