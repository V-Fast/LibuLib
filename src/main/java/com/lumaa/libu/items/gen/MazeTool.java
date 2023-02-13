package com.lumaa.libu.items.gen;

import com.lumaa.libu.generation.MazeCore;
import com.lumaa.libu.generation.type.ShapeType;
import com.lumaa.libu.generation.type.StructureType;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

import java.util.List;

public class MazeTool extends Item {
    public MazeTool(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        MazeCore mazeCore = new MazeCore(Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.BEDROCK, 5);
        mazeCore.setWallsVariants(List.of(Blocks.GRANITE, Blocks.COBBLESTONE));
        mazeCore.setSize(6, 6);
        mazeCore.setHeight(6);
        mazeCore.setShape(ShapeType.BOX);
        mazeCore.setType(StructureType.BLOCKY);

        mazeCore.generate(context.getWorld(), context.getBlockPos());
        return ActionResult.SUCCESS;
    }
}
