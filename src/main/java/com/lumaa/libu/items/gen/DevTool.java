package com.lumaa.libu.items.gen;

import com.lumaa.libu.generation.MazeCore;
import com.lumaa.libu.generation.PillarPattern;
import com.lumaa.libu.generation.type.ShapeType;
import com.lumaa.libu.generation.type.StructureType;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DevTool extends Item {
    public DevTool(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        MazeCore mazeCore = new MazeCore(Blocks.PODZOL, Blocks.STONE, Blocks.BEDROCK, 2);
        mazeCore.setWallsVariants(List.of(Blocks.GRANITE, Blocks.COBBLESTONE));
        mazeCore.setSize(13, 13);
        mazeCore.setHeight(3);
        mazeCore.setShape(ShapeType.BOX);
        mazeCore.setType(StructureType.BLOCKY);
        mazeCore.setBreakNonAir(false, false);

//        mazeCore.generate(context.getWorld(), context.getBlockPos());
        PillarPattern pillarPattern = new PillarPattern(mazeCore, mazeCore.getWalls());
        pillarPattern.generate(context.getWorld(), context.getBlockPos());
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}