package tfc_metallurgy.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfc_metallurgy.TFCMetallurgy;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(
            final PackOutput output,
            final CompletableFuture<HolderLookup.Provider> lookupProvider,
            final ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, TFCMetallurgy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(final HolderLookup.Provider lookupProvider) {

    }

}