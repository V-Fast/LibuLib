package com.lumaa.libu.client.ui;

import com.lumaa.libu.util.Color;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class LibuToast implements Toast {
    public ItemStack icon;
    public Component title;
    public Component description;
    public ToastType type = ToastType.NORMAL;

    private static int titleColor = Color.white;
    private static int descColor = 0xffff55;
    private boolean displayed;

    public LibuToast(@Nullable ItemStack icon, Component title, Component description) {
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
            Minecraft.getInstance().getToasts().addToast(this);
            this.displayed = true;
        }
    }

    @Override
    public Visibility render(PoseStack poseStack, ToastComponent toastComponent, long l) {
        Font textRenderer = toastComponent.getMinecraft().font;

        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        GuiComponent.blit(poseStack,  0, 0, 0, type.v, this.width(), this.height());
        textRenderer.draw(poseStack, this.title, 30.0F, 7.0F, titleColor);
        textRenderer.draw(poseStack, this.description, 30.0F, 18.0F, descColor);

        assert icon != null;
        toastComponent.getMinecraft().getItemRenderer().renderGuiItem(poseStack, icon, 8, 8);

        this.displayed = l < 5000L;
        return l >= 5000L ? Visibility.HIDE : Visibility.SHOW;
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
