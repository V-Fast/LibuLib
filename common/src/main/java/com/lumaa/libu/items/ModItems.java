package com.lumaa.libu.items;

import com.lumaa.libu.LibuLib;
import com.lumaa.libu.items.gen.MazeTool;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(LibuLib.ID, Registries.ITEM);

    public static final RegistrySupplier<Item> MAZE_TOOL = ITEMS.register("maze_tool", () ->
            new MazeTool(new Item.Properties().stacksTo(1)));

    public static void registerAll() {
        ITEMS.register();
    }
}
