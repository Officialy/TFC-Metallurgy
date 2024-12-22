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

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {
        super.validate(map, validationcontext);
        final var modLootTableIds = BuiltInLootTables
                .all()
                .stream()
                .filter(lootTable -> lootTable.getNamespace().equals(TFCMetallurgy.MOD_ID))
                .collect(Collectors.toSet());

        for (final var id : Sets.difference(modLootTableIds, map.keySet())) {
            validationcontext.reportProblem("Missing mod loot table: " + id);
        }

        map.forEach((lootTable) -> lootTable.validate(
                validationcontext
                        .setParams(lootTable.getParamSet())
                        .enterElement("{" + lootTable.getLootTableId() + "}", LootDataType.TABLE.registryKey()))
        );
    }
}