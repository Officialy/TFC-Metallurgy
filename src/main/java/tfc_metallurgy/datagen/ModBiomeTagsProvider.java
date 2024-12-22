package tfc_metallurgy.datagen;

import net.minecraftforge.common.data.ExistingFileHelper;
import tfc_metallurgy.TFCMetallurgy;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider {
    public ModBiomeTagsProvider(final PackOutput packOutput, final CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable final ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, TFCMetallurgy.MOD_ID, existingFileHelper);
    }
}
