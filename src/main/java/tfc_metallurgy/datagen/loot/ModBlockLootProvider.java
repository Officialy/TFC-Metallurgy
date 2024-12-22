package tfc_metallurgy.datagen.loot;

import tfc_metallurgy.TFCMetallurgy;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ModBlockLootProvider extends BlockLootSubProvider {

    //Blacklist for blocks that have special drops / other than itself, ie all ores that drop different items
    private static final List<Block> BLACKLIST = List.of(

    );

    public ModBlockLootProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return StreamSupport.stream(BuiltInRegistries.BLOCK.spliterator(), false)
                .filter(entry ->
                        Optional.of(BuiltInRegistries.BLOCK.getKey(entry))
                                .filter(key -> key.getNamespace().equals(TFCMetallurgy.MOD_ID))
                                .isPresent()
                ).collect(Collectors.toSet());
    }

}