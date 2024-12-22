package tfc_metallurgy.datagen;

import net.minecraftforge.common.data.ExistingFileHelper;
import tfc_metallurgy.TFCMetallurgy;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FluidTagsProvider {
    public ModFluidTagsProvider(
            final PackOutput output,
            final CompletableFuture<HolderLookup.Provider> holderLookup,
            @Nullable final ExistingFileHelper existingFileHelper
    ) {
        super(output, holderLookup, TFCMetallurgy.MOD_ID, existingFileHelper);
    }
}
