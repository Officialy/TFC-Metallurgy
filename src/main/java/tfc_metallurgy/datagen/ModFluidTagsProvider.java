package tfc_metallurgy.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfc_metallurgy.TFCMetallurgy;
import tfc_metallurgy.common.fluids.MetallurgyFluids;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FluidTagsProvider {
    public ModFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TFCMetallurgy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Add fluid tags for each metal
        MetallurgyFluids.FLUIDS.getEntries().stream()
                .filter(fluid -> fluid.getId().getPath().contains("metal/"))
                .forEach(fluid -> {
                    String metal = fluid.getId().getPath().split("/")[1];
                    tag(TagKey.create(net.minecraft.core.registries.Registries.FLUID, 
                            new ResourceLocation("forge", "molten_" + metal)))
                            .add(fluid.get());
                });
    }
}
