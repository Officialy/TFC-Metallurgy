package tfc_metallurgy.common.block_entities;

import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tfc_metallurgy.TFCMetallurgy;
import tfc_metallurgy.common.blocks.MetallurgyBlocks;

import net.dries007.tfc.common.blockentities.AnvilBlockEntity;
import tfc_metallurgy.util.MetallurgyMetal;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class MetallurgyBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TFCMetallurgy.mod_id);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MetallurgyBellBlockEntity>> BELL = register("bell", MetallurgyBellBlockEntity::new, Stream.of(MetallurgyBlocks.FLORENTINE_BRONZE_BELL, MetallurgyBlocks.BERYLLIUM_COPPER_BELL));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AnvilBlockEntity>> ANVIL = register("anvil",
        (pos, state) -> new AnvilBlockEntity(MetallurgyBlockEntities.ANVIL.get(), pos, state, AnvilBlockEntity.AnvilInventory::new),
        MetallurgyBlocks.METALS.values().stream().filter(map -> map.containsKey(MetallurgyMetal.BlockType.ANVIL)).map(map -> map.get(MetallurgyMetal.BlockType.ANVIL)));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MetallurgyLampBlockEntity>> LAMP = register("lamp", MetallurgyLampBlockEntity::new,
        MetallurgyBlocks.METALS.values().stream().filter(map -> map.containsKey(MetallurgyMetal.BlockType.LAMP)).map(map -> map.get(MetallurgyMetal.BlockType.LAMP)));

    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }

    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }
}
