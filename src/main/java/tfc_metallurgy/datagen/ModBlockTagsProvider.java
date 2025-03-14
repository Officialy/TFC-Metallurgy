package tfc_metallurgy.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfc_metallurgy.TFCMetallurgy;
import tfc_metallurgy.common.blocks.TFCMBlocks;
import tfc_metallurgy.common.blocks.rock.MetallurgyOre;
import net.dries007.tfc.common.blocks.rock.Rock;
import tfc_metallurgy.util.MetallurgyMetal;
import net.dries007.tfc.common.blocks.rock.Ore;

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
        // === ORES (Unclassified) ===
        TFCMBlocks.ORES.forEach((rockType, oreMap) ->
                oreMap.forEach((oreType, block) -> {
                    tag(Tags.Blocks.ORES).add(block.get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block.get());
                })
        );

        // === GRADED ORES ===
        TFCMBlocks.GRADED_ORES.forEach((rockType, oreMap) ->
                oreMap.forEach((oreType, gradeMap) ->
                        gradeMap.forEach((grade, block) -> {
                            tag(Tags.Blocks.ORES).add(block.get());
                            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block.get());
                        })
                )
        );

        // === SMALL ORES ===
        TFCMBlocks.SMALL_ORES.forEach((oreType, block) -> {
            tag(Tags.Blocks.ORES).add(block.get());
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block.get());
        });

        // === METALS ===
        TFCMBlocks.METALS.forEach((metal, metalMap) ->
                metalMap.forEach((blockType, block) -> {
                    switch (blockType) {
                        case ANVIL:
                            tag(BlockTags.ANVIL).add(block.get());
                            break;
                        case BARS:
                            tag(BlockTags.WALLS).add(block.get());
                            break;
                        case LAMP:
                            tag(BlockTags.BEACON_BASE_BLOCKS).add(block.get());
                            break;
                        case CHAIN:
                            tag(BlockTags.WALLS).add(block.get());
                            break;
                        case TRAPDOOR:
                            tag(BlockTags.TRAPDOORS).add(block.get());
                        case BLOCK:
                            tag(BlockTags.BEACON_BASE_BLOCKS).add(block.get());

                            break;
                    }
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block.get()); // General mining tag
                })
        );

        // === INDIVIDUAL BLOCKS ===
//        tag(BlockTags.WALLS).add(
//                TFCMBlocks.ENDERIUM_BARS.get(),
//                TFCMBlocks.TITANIUM_BARS.get(),
//                TFCMBlocks.TUNGSTEN_BARS.get(),
//                TFCMBlocks.TUNGSTEN_STEEL_BARS.get()
//        );

//        tag(BlockTags.BEACON_BASE_BLOCKS).add(
//                TFCMBlocks.BERYLLIUM_COPPER_BELL.get(),
//                TFCMBlocks.FLORENTINE_BRONZE_BELL.get()
//        );
    }
}
