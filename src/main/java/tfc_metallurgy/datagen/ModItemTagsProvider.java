package tfc_metallurgy.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfc_metallurgy.TFCMetallurgy;
import tfc_metallurgy.common.blocks.TFCMBlocks;
import tfc_metallurgy.common.items.MetallurgyItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, 
                             CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, TFCMetallurgy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Add metal item tags
        addMetalItemTags();
        
        // Add other item tags
        addOtherItemTags();
    }

    private void addMetalItemTags() {
        // Add metal_item/* tags for each metal
        MetallurgyItems.ITEMS.getEntries().stream()
                .filter(item -> item.getId().getPath().contains("metal/"))
                .forEach(item -> {
                    String[] parts = item.getId().getPath().split("/");
                    if (parts.length >= 3) {
                        String metal = parts[2];
                        tag(TagKey.create(net.minecraft.core.registries.Registries.ITEM, 
                                new ResourceLocation(TFCMetallurgy.MOD_ID, "metal_item/" + metal)))
                                .add(item.get());
                    }
                });
    }

    private void addOtherItemTags() {
        // Add items to forge tags
        MetallurgyItems.ITEMS.getEntries().stream()
                .filter(item -> item.getId().getPath().contains("ingot"))
                .forEach(item -> {
                    String metal = item.getId().getPath().split("/")[2];
                    tag(Tags.Items.INGOTS).add(item.get());
                    tag(TagKey.create(net.minecraft.core.registries.Registries.ITEM, 
                            new ResourceLocation("forge", "ingots/" + metal)))
                            .add(item.get());
                });
    }
}
