package com.lumaa.libu.client.ui;

import com.lumaa.libu.util.Color;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;

@Environment(EnvType.CLIENT)
public class LibuToast implements Toast {
    public ItemStack icon;
    public MutableText title;
    public MutableText description;
    public ToastType type = ToastType.NORMAL;

    private static int titleColor = Color.white;
    private static int descColor = 0xffff55;
    private boolean displayed;

    public LibuToast(ItemStack icon, MutableText title, MutableText description) {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.displayed = false;
    }

    public void textColor(int titleColor, int descColor) {
        LibuToast.titleColor = titleColor;
        LibuToast.descColor = descColor;
    }

    public void display() {
        if (!displayed) {
            MinecraftClient.getInstance().getToastManager().add(this);
            this.displayed = true;
        }
    }

    @Override
    public Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        TextRenderer textRenderer = manager.getClient().textRenderer;

        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        manager.drawTexture(matrices,  0, 0, 0, type.v, getWidth(), getHeight());
        textRenderer.draw(matrices, this.title, 30.0F, 7.0F, titleColor);
        textRenderer.draw(matrices, this.description, 30.0F, 18.0F, descColor);
        manager.getClient().getItemRenderer().renderInGui(matrices, icon, 8, 8);

        this.displayed = startTime < 5000L;
        return startTime >= 5000L ? Visibility.HIDE : Visibility.SHOW;
    }

    public enum ToastType {
        NORMAL(0),
        WHITE(32),
        WARNING(64);

        public int v;

        ToastType(int v) {
            this.v = v;
        }
    }
}
