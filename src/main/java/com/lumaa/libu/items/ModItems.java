package com.lumaa.libu.items;

import com.lumaa.libu.items.gen.MazeTool;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ModItems {
    public static final Item MAZE_TOOL = registerItem("maze_tool", new MazeTool(new Item.Settings().maxCount(1)), ItemGroups.FUNCTIONAL);

    public static Item registerItem(String name, Item item, @Nullable ItemGroup group) {
        Item newItem = Registry.register(Registries.ITEM, new Identifier("libu", name), item);

        // put in item group
        if (group != null) {
            ItemGroupEvents.modifyEntriesEvent(group).register(content -> {
                content.add(newItem);
            });
        }

        return newItem;
    }

    public static String registerAll() {
        return MAZE_TOOL.getName().getString();
    }
}
