package com.lumaa.libu.client.ui;

import com.lumaa.libu.util.Color;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;

public class LibuToast implements Toast {
    public ItemStack icon;
    public MutableText title;
    public MutableText description;

    private static int titleColor = Color.white;
    private static int descColor = 0xffff55;
    private boolean displayed = false;

    public LibuToast(ItemStack icon, MutableText title, MutableText description) {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.displayed = false;
    }

    public void display(ToastManager toastManager) {
        if (!displayed) {
            toastManager.add(this);
            this.displayed = true;
        }
    }

    @Override
    public Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        TextRenderer textRenderer = manager.getClient().textRenderer;

        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        manager.drawTexture(matrices,  0, 0, 0, 0, getWidth(), getHeight());
        textRenderer.draw(matrices, this.title, 30.0F, 7.0F, titleColor);
        textRenderer.draw(matrices, this.description, 30.0F, 18.0F, descColor);
        manager.getClient().getItemRenderer().renderInGui(matrices, icon, 8, 8);

        this.displayed = startTime < 5000L;
        return startTime >= 5000L ? Visibility.HIDE : Visibility.SHOW;
    }
}
