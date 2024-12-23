package tfc_metallurgy.datagen;

import net.dries007.tfc.common.blocks.rock.Rock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallurgy.TFCMetallurgy;
import net.minecraft.data.PackOutput;
import tfc_metallurgy.common.blocks.MetallurgyBlocks;
import tfc_metallurgy.util.MetallurgyMetal;

import java.util.Arrays;

public class ModBlockStateProvider extends BlockStateProvider {

    ExistingFileHelper existingFileHelper;

    public ModBlockStateProvider(final PackOutput packOutput, final ExistingFileHelper existingFileHelper) {
        super(packOutput, TFCMetallurgy.MOD_ID, existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        // Register METALS
        MetallurgyBlocks.METALS.forEach((metal, metalMap) ->
                Arrays.stream(MetallurgyMetal.BlockType.values())
                        .filter(metalMap::containsKey) // Ensure the block type exists
                        .forEach(type -> {
                            RegistryObject<Block> block = metalMap.get(type);
                            if (block != null) {
                                if (type == MetallurgyMetal.BlockType.LAMP) {
                                    // Custom handling for lamp blocks
                                    ModelFile lampModel = models().withExistingParent(
                                            block.getId().getPath(), mcLoc("block/cube_all")
                                    ).texture("all", modLoc("item/metal/lamp/" + metal.name().toLowerCase()));
                                    simpleBlock(block.get(), lampModel);
                                } else {
                                    // Default handling for other block types
                                    simpleBlock(block.get());
                                }
                            }
                        })
        );



        // Register SMALL_ORES
        MetallurgyBlocks.SMALL_ORES.values().forEach(block -> {
            ModelFile smallOreModel = models().withExistingParent(
                    block.getId().getPath(), mcLoc("block/cube_all")
            ).texture("all", modLoc("item/ore/" + block.getId().getPath().substring(block.getId().getPath().lastIndexOf('/') + 1)));
            simpleBlock(block.get(), smallOreModel);
        });


        // Register ORES
        MetallurgyBlocks.ORES.forEach((rockType, oreMap) ->
                oreMap.forEach((oreType, block) -> {
                    // Remove redundant loop over Rock.values() unless needed
                    String modelName = oreType.name().toLowerCase();
                    ResourceLocation baseTexture = new ResourceLocation("tfc", "block/rock/raw/" + rockType.name().toLowerCase());

                    // Log the generated paths for debugging
                    TFCMetallurgy.LOGGER.info("Generating model for block/ore/{}/{}", modelName, rockType.name().toLowerCase());

                    // Generate model with correct paths
                    models().withExistingParent(
                                    "block/ore/" + modelName + "/" + rockType.name().toLowerCase(),
                                    new ResourceLocation("tfc", "block/ore") // Ensure this parent model exists
                            ).texture("all", baseTexture)
                            .texture("particle", baseTexture)
                            .texture("overlay", modLoc("block/ore/" + modelName));
                })
        );


        // Register GRADED_ORES
        MetallurgyBlocks.GRADED_ORES.forEach((rockType, oreMap) ->
                oreMap.forEach((oreType, block) -> {
                    Arrays.asList("poor", "normal", "rich").forEach(grade -> {
                        String modelName = grade + "_" + oreType.name().toLowerCase();
                        ResourceLocation baseTexture = new ResourceLocation("tfc", "block/rock/raw/" + rockType.name().toLowerCase());

                        TFCMetallurgy.LOGGER.info("Generating model for block/ore/{}/{}", modelName, rockType.name().toLowerCase());
                        models().withExistingParent(
                                        "block/ore/" + modelName + "/" + rockType.name().toLowerCase(),
                                        new ResourceLocation("tfc", "block/ore")
                                ).texture("all", baseTexture)
                                .texture("particle", baseTexture)
                                .texture("overlay", modLoc("block/ore/" + modelName));
                    });
                }));

    }
}