package com.lumaa.libu.update;

import net.minecraft.util.Util;

public class ModrinthMod {
    public String name;
    public String slang;
    public String versionId;
    private UpdateChecker checker;

    /**
     * Identify a Modrinth Mod
     * @param name The name of the mod
     * @param slang The slang of the mod
     * @param versionId The current version identifier of the mod
     */
    public ModrinthMod(String name, String slang, String versionId) {
        this.name = name;
        this.slang = slang;
        this.versionId = versionId;
    }

    /**
     * Opens the Modrinth project of the mod
     */
    public void openMod() {
        Util.getOperatingSystem().open("https://modrinth.com/mod/%d".formatted(this.slang));
    }

    /**
     * Opens the Modrinth project in a specific version
     * @param versionId The version identifier
     */
    public void openVersion(String versionId) {
        Util.getOperatingSystem().open("https://modrinth.com/mod/%d/version/%d".formatted(this.slang, versionId));
    }

    /**
     * Opens the Modrinth project in the current version
     */
    public void openCurrentVersion() {
        openVersion(this.versionId);
    }

    public void setChecker(UpdateChecker checker) {
        this.checker = checker;
    }

    public UpdateChecker getChecker() {
        return checker;
    }
}
