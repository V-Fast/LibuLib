package com.lumaa.libu.mixin;

import com.lumaa.libu.LibuLib;
import com.lumaa.libu.client.ui.LibuToast;
import com.lumaa.libu.util.BetterText;
import com.lumaa.libu.util.Color;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "init()V")
    public void init(CallbackInfo ci) {
        int l = this.height / 4 + 48;
        int k = 24;

        this.addRenderableWidget(Button.builder(new BetterText("Toast", BetterText.TextType.LITERAL).withColor(Color.brand), (button) -> {
                    LibuToast test = new LibuToast(Items.STICK.getDefaultInstance(), Component.literal("Title"), Component.literal("Description"));
                    test.display();
                })
                .bounds(this.width / 2 + 104, l + k, 40, 20)
                .build());
    }
}
