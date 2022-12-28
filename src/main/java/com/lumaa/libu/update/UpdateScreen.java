package com.lumaa.libu.update;

import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

import java.io.IOException;
import java.util.Objects;

public class UpdateScreen extends Screen {
    private final Screen parent;
    private final ModrinthMod mod;
    private final Text notice;
    private MultilineText noticeLines;

    public UpdateScreen(Screen parent, ModrinthMod mod, MutableText title, Text notice) throws IOException {
        super(title.formatted(Formatting.BOLD));
        this.parent = parent;
        this.mod = mod;
        this.notice = notice;
    }

    @Override
    protected void init() {
        super.init();
        this.noticeLines = MultilineText.create(this.textRenderer, this.notice, this.width - 50);
        int var10000 = this.noticeLines.count();
        Objects.requireNonNull(this.textRenderer);
        int i = var10000 * 9;
        int j = MathHelper.clamp(90 + i + 12, this.height / 6 + 96, this.height - 24);
        this.addDrawableChild(new ButtonWidget((this.width - 150) / 2, j, 150, 20, ScreenTexts.YES, (button) -> {
            mod.openVersion(mod.getChecker().getString("version_number"));
        }));
        this.addDrawableChild(new ButtonWidget((this.width - 150) / 2, j + 30, 150, 20, ScreenTexts.NO, (button) -> {
            this.client.setScreen(parent);
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.drawTitle(matrices);
        int i = this.width / 2 - this.noticeLines.getMaxWidth() / 2;
        this.noticeLines.drawWithShadow(matrices, i, 70, this.getLineHeight(), 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }

    protected void drawTitle(MatrixStack matrices) {
        drawTextWithShadow(matrices, this.textRenderer, this.title, 25, 30, 16777215);
    }

    protected int getLineHeight() {
        Objects.requireNonNull(this.textRenderer);
        return 9 * 2;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}