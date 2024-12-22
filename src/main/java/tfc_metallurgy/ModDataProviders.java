package tfc_metallurgy;

import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tfc_metallurgy.datagen.*;
import tfc_metallurgy.datagen.loot.ModLootTableProvider;
import tfc_metallurgy.datagen.recipe.ModCraftingProvider;

/**
 * Registers all data providers for TFCMetallurgy.
 * <p>
 * Credit to Choonster for the original code:
 * <a href="https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.20.x/src/main/java/choonster/testmod3/data/TestMod3RecipeProvider.java">...</a>
 */
@Mod.EventBusSubscriber(modid = TFCMetallurgy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataProviders {

    @SubscribeEvent
    public static void registerDataProviders(final GatherDataEvent event) {
        final var dataGenerator = event.getGenerator();
        final var output = dataGenerator.getPackOutput();
        final var existingFileHelper = event.getExistingFileHelper();
        final var lookupProvider = event.getLookupProvider();//.thenApply(ChromaDataProviders::createLookup);

        dataGenerator.addProvider(event.includeClient(), new ModLanguageProvider(output));

        final var itemModelProvider = new ModItemModelProvider(output, existingFileHelper);
        dataGenerator.addProvider(event.includeClient(), itemModelProvider);

        // Let blockstate provider see generated item models by passing its existing file helper
        dataGenerator.addProvider(event.includeClient(), new ModBlockStateProvider(output, itemModelProvider.existingFileHelper));

        dataGenerator.addProvider(event.includeServer(), new ModCraftingProvider(output));
        dataGenerator.addProvider(event.includeServer(), ModLootTableProvider.create(output, lookupProvider));

        final var blockTagsProvider = new ModBlockTagsProvider(output, lookupProvider, existingFileHelper);
        dataGenerator.addProvider(event.includeServer(), blockTagsProvider);
        dataGenerator.addProvider(event.includeServer(), new ModItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new ModBiomeTagsProvider(output, lookupProvider, existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new ModFluidTagsProvider(output, lookupProvider, existingFileHelper));
//        dataGenerator.addProvider(event.includeServer(), new ChromaEntityTypeTagsProvider(output, lookupProvider, existingFileHelper));

//        dataGenerator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(output, lookupProvider, Set.of(TFCMetallurgy.MOD_ID)));
    }

}
