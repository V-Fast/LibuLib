package com.lumaa.libu.client;

import com.lumaa.libu.client.ui.LButton;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TestScreen extends Screen {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final LButton BUTTON_A = new LButton(client.currentScreen.width / 2, client.currentScreen.height / 2, Text.literal("LButton"));

    public TestScreen() {
        super(Text.empty());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        BUTTON_A.x = 20;
        BUTTON_A.y = 20;

        BUTTON_A.changeSize(20, 20);

        BUTTON_A.render(matrices, mouseX, mouseY, delta);
    }
}
