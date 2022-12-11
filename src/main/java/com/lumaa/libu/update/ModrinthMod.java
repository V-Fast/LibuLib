package com.lumaa.libu.update;

import net.minecraft.util.Util;

public class ModrinthMod {
    public String name;
    public String slang;

    public ModrinthMod(String name, String slang) {
        this.name = name;
        this.slang = slang;
    }

    public void openMod() {
        Util.getOperatingSystem().open("https://modrinth.com/mod/%d".formatted(this.slang));
    }

    public void openVersion(String versionId) {
        Util.getOperatingSystem().open("https://modrinth.com/mod/%d/version/%d".formatted(this.slang, versionId));
    }
}
