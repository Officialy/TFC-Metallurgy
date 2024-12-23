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
                new SubProviderEntry(ModBlockLootProvider::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(ModGenericLootProvider::new, LootContextParamSets.ALL_PARAMS)
        ));
    }

}