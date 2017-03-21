package eu.crushedpixel.sponge.masquerade.impl.masquerades;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;

public class FallingBlockMasquerade extends ObjectMasquerade {

    public FallingBlockMasquerade(Player player, BlockState blockState) {
        super(player, EntityTypes.FALLING_BLOCK, Block.getStateId((IBlockState) blockState));
    }

}
