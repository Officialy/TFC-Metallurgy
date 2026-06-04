package tfc_metallurgy.common.block_entities;

import net.dries007.tfc.common.blockentities.LampBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MetallurgyLampBlockEntity extends LampBlockEntity
{
    public MetallurgyLampBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    public @NotNull BlockEntityType<?> getType()
    {
        return MetallurgyBlockEntities.LAMP.get();
    }
}
