package com.lumaa.libu.items.gen;

import com.lumaa.libu.generation.MazeCore;
import com.lumaa.libu.generation.type.ShapeType;
import com.lumaa.libu.generation.type.StructureType;
import com.lumaa.libu.util.BetterText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MazeTool extends Item {
    public MazeTool(Item.Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        MazeCore mazeCore = new MazeCore(Blocks.PODZOL, Blocks.STONE, Blocks.BEDROCK, 2);
        mazeCore.setWallsVariants(List.of(Blocks.GRANITE, Blocks.COBBLESTONE));
        mazeCore.setSize(13, 13);
        mazeCore.setHeight(3);
        mazeCore.setShape(ShapeType.BOX);
        mazeCore.setType(StructureType.BLOCKY);
        mazeCore.setBreakNonAir(false, false);

        mazeCore.generate(useOnContext.getLevel(), useOnContext.getClickedPos());
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.empty()); // jump to line
        list.add(getTooltip());
    }

    private Component getTooltip() {
        return new BetterText(this.getDescriptionId()).withColor(ChatFormatting.GRAY);
    }
}
