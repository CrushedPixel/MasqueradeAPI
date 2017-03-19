package eu.crushedpixel.sponge.masquerade.api.masquerades.impl;

import eu.crushedpixel.sponge.masquerade.api.masquerades.ObjectMasquerade;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.living.player.Player;

public class FallingBlockMasquerade extends ObjectMasquerade<EntityFallingBlock> {

    private static final int FALLING_BLOCK_ID = 70;

    public FallingBlockMasquerade(Player player, BlockType blockType, int metadata) {
        super(player, EntityFallingBlock.class, FALLING_BLOCK_ID, Block.getIdFromBlock((Block) blockType) | (metadata << 12));
    }

}
