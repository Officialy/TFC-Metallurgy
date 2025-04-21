package tfc_metallurgy;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import tfc_metallurgy.datagen.*;
import tfc_metallurgy.datagen.loot.ModLootTableProvider;
import tfc_metallurgy.datagen.recipe.ModCraftingProvider;

import java.util.concurrent.CompletableFuture;
import java.util.Set;

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
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Client-side providers
        dataGenerator.addProvider(event.includeClient(), new ModLanguageProvider(output));
        dataGenerator.addProvider(event.includeClient(), new ModBlockStateProvider(output, existingFileHelper));
        dataGenerator.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));

        // Server-side providers
        dataGenerator.addProvider(event.includeServer(), new ModCraftingProvider(output));
        dataGenerator.addProvider(event.includeServer(), ModLootTableProvider.create(output));

        final var blockTagsProvider = new ModBlockTagsProvider(output, lookupProvider, existingFileHelper);
        dataGenerator.addProvider(event.includeServer(), blockTagsProvider);
        dataGenerator.addProvider(event.includeServer(), new ModItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new ModBiomeTagsProvider(output, lookupProvider, existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new ModFluidTagsProvider(output, lookupProvider, existingFileHelper));

        dataGenerator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(output, lookupProvider, Set.of(TFCMetallurgy.MOD_ID)));
    }

}
