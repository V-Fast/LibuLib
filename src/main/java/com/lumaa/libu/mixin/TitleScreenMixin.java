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
    public void init(CallbackInfo ci) throws IOException {
        // update all the mods using LibuLib
        LibuLib.getUpdates().forEach(update -> {
            try {
                if (!update.getString("version_number").equals(update.getMod().versionId) && !updatePopped) {
                    if (update.getMod().versionId == "Dev") {
                        updatePopped = true;
                    } else {
                        updatePopped = true;
                        this.client.setScreen(new UpdateScreen(LibuLib.updateChecker.getMod(), Text.translatable("update.libulib.title"), Text.translatable("update.libulib.description")));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // update LibuLib (last because message will appear first if many)
        if (LibuLib.published) {
            if (!LibuLib.updateChecker.getString("version_number").equals(LibuLib.version) && !updatePopped) {
                if (LibuLib.version == "Dev") {
                    updatePopped = true;
                } else {
                    updatePopped = true;
                    this.client.setScreen(new UpdateScreen(LibuLib.updateChecker.getMod(), Text.translatable("update.libulib.title"), Text.translatable("update.libulib.description")));
                }
            }
        }
    }
}
