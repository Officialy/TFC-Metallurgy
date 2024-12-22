package tfc_metallurgy.common.block_entities;

import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallurgy.TFCMetallurgy;
import tfc_metallurgy.common.blocks.MetallurgyBlocks;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class MetallurgyBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TFCMetallurgy.mod_id);

    public static final RegistryObject<BlockEntityType<MetallurgyBellBlockEntity>> BELL = register("bell", MetallurgyBellBlockEntity::new, Stream.of(MetallurgyBlocks.FLORENTINE_BRONZE_BELL, MetallurgyBlocks.BERYLLIUM_COPPER_BELL));

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }
}
