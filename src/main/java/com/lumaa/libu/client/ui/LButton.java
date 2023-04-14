package com.lumaa.libu.client.ui;

import com.lumaa.libu.util.Color;
import com.lumaa.libu.util.Geometry.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;

public class LButton extends DrawableHelper implements Drawable, Element, Selectable {
    public int x;
    public int y;
    public Translation2d size = new Translation2d();
    public boolean visible = true;
    public boolean locked = false;
    public boolean hovering = false;
    public boolean focused = false;
    public MutableText content;

    public LButton(int x, int y, MutableText content) {
        this.x = x;
        this.y = y;
        this.content = content;
    }

    public void changeSize(int width, int height) {
        size.setEndCoordinates(width, height);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int x1 = this.x;
        int y1 = this.y;

        int x2 = this.x + this.size.getEndCoordinates().x;
        int y2 = this.y + this.size.getEndCoordinates().y;

        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;

        renderButton(matrices, textRenderer, x1, x2, y1, y2, Color.brand);
    }

    private void renderButton(MatrixStack matrixStack, TextRenderer textRenderer, int x1, int x2, int y1, int y2, int color) {
        int l = x2 - x1;
        int k = y2 - y1;

        fill(matrixStack, x1 + 10, y1 + 10, x2 - 10, y2 - 10, Color.white);
        textRenderer.draw(matrixStack, this.content, l / 2, k / 2, color);
    };

    @Override
    public SelectionType getType() {
        if (this.isFocused()) {
            return SelectionType.FOCUSED;
        } else {
            return this.hovering ? SelectionType.HOVERED : SelectionType.NONE;
        }
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        int endX = this.x + this.size.getEndCoordinates().x;
        int endY = this.y + this.size.getEndCoordinates().y;

        boolean axisX = mouseX >= x && mouseX <= endX;
        boolean axisY = mouseY >= y && mouseY <= endY;

        this.hovering = axisX && axisY;
        return this.hovering;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {}

    public boolean isHovering() {
        return this.hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

    @Override
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    @Override
    public boolean isFocused() {
        return this.focused;
    }
}
