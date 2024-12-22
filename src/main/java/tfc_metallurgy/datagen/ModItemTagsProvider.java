package tfc_metallurgy.datagen;

import net.minecraftforge.common.data.ExistingFileHelper;
import tfc_metallurgy.TFCMetallurgy;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, blockTags, TFCMetallurgy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {

    }
}
