package com.lumaa.libu.mixin;

import com.lumaa.libu.client.TestScreen;
import com.lumaa.libu.client.ui.LibuToast;
import com.lumaa.libu.util.BetterText;
import com.lumaa.libu.util.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "init()V")
    public void init(CallbackInfo ci) {
        int l = this.height / 4 + 48;
        int k = 24;

        this.addDrawableChild(ButtonWidget.builder(new BetterText("Toast", BetterText.TextType.LITERAL).withColor(Color.brand), (button) -> {
                    LibuToast test = new LibuToast(Items.STICK.getDefaultStack(), Text.literal("Title"), Text.literal("Description"));
                    test.display(MinecraftClient.getInstance().getToastManager());
                })
                .dimensions(this.width / 2 + 104, l + k, 40, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(new BetterText("L-UI", BetterText.TextType.LITERAL).withColor(Color.brand), (button) -> {
                    this.client.setScreen(new TestScreen());
                })
                .dimensions(this.width / 2 + 104, l + k * 2, 40, 20)
                .build());
    }
}
