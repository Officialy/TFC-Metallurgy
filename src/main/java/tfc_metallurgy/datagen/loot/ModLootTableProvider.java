package tfc_metallurgy.datagen.loot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import tfc_metallurgy.TFCMetallurgy;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import tfc_metallurgy.common.blocks.TFCMBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput pOutput, List<SubProviderEntry> pSubProviders) {
        super(pOutput, Set.of(), pSubProviders);
    }

    public static ModLootTableProvider create(final PackOutput output) {
        return new ModLootTableProvider(output, ImmutableList.of(
                new SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(ModGenericLootProvider::new, LootContextParamSets.ALL_PARAMS)
        ));
    }

    public static class ModBlockLootTables extends net.minecraft.data.loot.BlockLootSubProvider {
        protected ModBlockLootTables() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            // Generate loot tables for all blocks
            TFCMBlocks.BLOCKS.getEntries().forEach(block -> {
                dropSelf(block.get());
            });
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return TFCMBlocks.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .collect(Collectors.toList());
        }
    }
}