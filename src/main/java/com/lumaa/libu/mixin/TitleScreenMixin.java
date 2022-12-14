package com.lumaa.libu.mixin;

import com.lumaa.libu.LibuLib;
import com.lumaa.libu.update.UpdateScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    private static boolean updatePopped = false;

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init()V")
    public void init(CallbackInfo ci) {
        // update all the mods using LibuLib (including LibuLib)
        if (LibuLib.getUpdates().size() > 0) {
            LibuLib.getUpdates().forEach(update -> {
                LibuLib.logger.info("[LibuLib] UpdateChecker - Checking %d".formatted(update.getMod().name));
                try {
                    if (!update.getString("version_number").equals(update.getMod().versionId) && !update.isShown()) {
                        if (update.getMod().versionId == "Dev") {
                            update.setShown(true);
                        } else {
                            update.setShown(true);
                            this.client.setScreen(new UpdateScreen(LibuLib.updateChecker.getMod(), Text.translatable("update.libulib.title".formatted(update.getMod().name)), Text.translatable("update.libulib.description".formatted(update.getMod().name, update.getMod().versionId, update.getString("version_number"), update.getMod().name))));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            LibuLib.logger.error("[LibuLib] No mods in UpdateChecker list");
        }
    }
}
