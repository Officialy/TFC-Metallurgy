package tfc_metallurgy.datagen;

import net.dries007.tfc.common.blocks.devices.LampBlock;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallurgy.TFCMetallurgy;
import net.minecraft.data.PackOutput;
import tfc_metallurgy.common.blocks.TFCMBlocks;
import tfc_metallurgy.util.MetallurgyMetal;

import java.util.Arrays;
import static net.minecraftforge.client.model.generators.ModelProvider.BLOCK_FOLDER;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(final PackOutput packOutput, final ExistingFileHelper existingFileHelper) {
        super(packOutput, TFCMetallurgy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        TFCMBlocks.METALS.forEach((metal, metalMap) ->
                Arrays.stream(MetallurgyMetal.BlockType.values())
                        .filter(metalMap::containsKey)
                        .forEach(type -> {
                            RegistryObject<Block> block = metalMap.get(type);
                            if (block != null) {
                                switch (type) {
                                    case LAMP -> registerLamp(block, metal);
                                    case ANVIL -> registerAnvil(block, metal);
                                    case CHAIN -> registerChain(block, metal);
                                    case TRAPDOOR -> registerTrapdoor(block, metal);
                                    case BLOCK_STAIRS -> registerStairs(block, metal);
                                    case BLOCK_SLAB -> registerSlab(block, metal);
                                    case BLOCK -> registerBlock(block, metal);
                                    case BARS -> registerBars(block, metal);
                                }
                            }
                        })
        );

        TFCMBlocks.SMALL_ORES.values().forEach(block -> {
            ModelFile smallOreModel = models().withExistingParent("block/" + block.getId().getPath(), mcLoc("block/cube_all"))
                    .texture("all", modLoc("item/ore/" + block.getId().getPath().substring(block.getId().getPath().lastIndexOf('/') + 1)));
            simpleBlock(block.get(), smallOreModel);
        });

        TFCMBlocks.ORES.forEach((rockType, oreMap) ->
                oreMap.forEach((oreType, block) -> {
                    String modelName = "block/ore/" + oreType.name().toLowerCase() + "/" + rockType.name().toLowerCase();
                    ResourceLocation baseTexture = new ResourceLocation("tfc", "block/rock/raw/" + rockType.name().toLowerCase());
                    ResourceLocation overlayTexture = modLoc("block/ore/" + oreType.name().toLowerCase());

                    ModelFile oreModel = models().withExistingParent(modelName, "tfc:block/ore")
                            .texture("all", baseTexture)
                            .texture("particle", baseTexture)
                            .texture("overlay", overlayTexture);

                    simpleBlock(block.get(), oreModel);
                })
        );

        TFCMBlocks.GRADED_ORES.forEach((rockType, oreMap) ->
                oreMap.forEach((oreType, gradeMap) ->
                        gradeMap.forEach((grade, block) -> {
                            String modelName = "block/ore/" + grade.name().toLowerCase() + "_" + oreType.name().toLowerCase() + "/" + rockType.name().toLowerCase();
                            ResourceLocation baseTexture = new ResourceLocation("tfc", "block/rock/raw/" + rockType.name().toLowerCase());
                            ResourceLocation overlayTexture = modLoc("block/ore/" + grade.name().toLowerCase() + "_" + oreType.name().toLowerCase());

                            ModelFile gradedOreModel = models().withExistingParent(modelName, "tfc:block/ore")
                                    .texture("all", baseTexture)
                                    .texture("particle", baseTexture)
                                    .texture("overlay", overlayTexture);

                            simpleBlock(block.get(), gradedOreModel);
                        })
                )
        );
        TFCMBlocks.METAL_FLUIDS.forEach((metal, block) -> {
            String fluidModelPath = "block/metal/fluid/" + metal.name().toLowerCase();
            ResourceLocation fluidStill = modLoc("block/metal/fluid/" + metal.name().toLowerCase() + "_still");
            ResourceLocation fluidFlowing = modLoc("block/metal/fluid/" + metal.name().toLowerCase() + "_flow");

            getVariantBuilder(block.get()).forAllStates(state -> {
                int level = state.getValue(LiquidBlock.LEVEL);
                return ConfiguredModel.builder()
                        .modelFile(models().withExistingParent(fluidModelPath, "minecraft:block/water")
                                .texture("still", fluidStill)
                                .texture("flowing", fluidFlowing))
                        .build();
            });

            models().withExistingParent(fluidModelPath, "minecraft:block/water")
                    .texture("still", fluidStill)
                    .texture("flowing", fluidFlowing);
        });

    }

    private void registerLamp(RegistryObject<Block> block, MetallurgyMetal metal) {
        String basePath = "block/metal/lamp/" + metal.name().toLowerCase();
        String fullMetalTexture = "tfc_metallurgy:block/metal/smooth/" + metal.name().toLowerCase();
        String chainTexture = "tfc_metallurgy:block/metal/chain/" + metal.name().toLowerCase();
        String lampOnTexture = "tfc:block/lamp";
        String lampOffTexture = "tfc:block/lamp_off";

        ModelFile lampOn = models().withExistingParent(basePath + "_on", "tfc:block/lamp")
                .texture("metal", fullMetalTexture)
                .texture("lamp", lampOnTexture);

        ModelFile lampOff = models().withExistingParent(basePath + "_off", "tfc:block/lamp")
                .texture("metal", fullMetalTexture)
                .texture("lamp", lampOffTexture);

        getVariantBuilder(block.get())
                .partialState().with(LampBlock.LIT, true).modelForState().modelFile(lampOn).addModel()
                .partialState().with(LampBlock.LIT, false).modelForState().modelFile(lampOff).addModel();
    }

    private void registerAnvil(RegistryObject<Block> block, MetallurgyMetal metal) {
        String anvilModelPath = "block/metal/anvil/" + metal.name().toLowerCase();
        ResourceLocation anvilTexture = modLoc("block/metal/anvil/" + metal.name().toLowerCase());

        // Generate the anvil model with correct parent and texture
        ModelFile anvilModel = models().withExistingParent(anvilModelPath, "tfc:block/anvil")
                .texture("all", anvilTexture)
                .texture("particle", anvilTexture);

        // Blockstate rotation for facing directions
        getVariantBuilder(block.get())
                .partialState().with(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                .modelForState().modelFile(anvilModel).addModel()
                .partialState().with(HorizontalDirectionalBlock.FACING, Direction.EAST)
                .modelForState().modelFile(anvilModel).rotationY(90).addModel()
                .partialState().with(HorizontalDirectionalBlock.FACING, Direction.SOUTH)
                .modelForState().modelFile(anvilModel).rotationY(180).addModel()
                .partialState().with(HorizontalDirectionalBlock.FACING, Direction.WEST)
                .modelForState().modelFile(anvilModel).rotationY(270).addModel();
    }


    private void registerChain(RegistryObject<Block> block, MetallurgyMetal metal) {
        ModelFile chainModel = models().withExistingParent(
                        "block/metal/chain/" + metal.name().toLowerCase(), mcLoc("block/chain"))
                .texture("particle", modLoc("block/metal/chain/" + metal.name().toLowerCase()));

        getVariantBuilder(block.get()).forAllStates(state ->
                ConfiguredModel.builder().modelFile(chainModel).build()
        );
    }

    private void registerTrapdoor(RegistryObject<Block> block, MetallurgyMetal metal) {
        String basePath = "block/metal/trapdoor/" + metal.name().toLowerCase();
        ResourceLocation fullMetalTexture = modLoc("block/metal/smooth/" + metal.name().toLowerCase());

        models().withExistingParent(basePath + "_bottom", "minecraft:block/template_orientable_trapdoor_bottom")
                .texture("texture", fullMetalTexture);

        models().withExistingParent(basePath + "_top", "minecraft:block/template_orientable_trapdoor_top")
                .texture("texture", fullMetalTexture);

        models().withExistingParent(basePath + "_open", "minecraft:block/template_orientable_trapdoor_open")
                .texture("texture", fullMetalTexture);
    }

    private void registerStairs(RegistryObject<Block> block, MetallurgyMetal metal) {
        String stairsPath = "block/metal/block_stairs/" + metal.name().toLowerCase();
        ResourceLocation texture = modLoc("block/metal/smooth/" + metal.name().toLowerCase());

        models().stairs(stairsPath, texture, texture, texture);
    }

    private void registerSlab(RegistryObject<Block> block, MetallurgyMetal metal) {
        String slabModelPath = "block/metal/block_slab/" + metal.name().toLowerCase();
        ResourceLocation slabTexture = modLoc("block/metal/smooth/" + metal.name().toLowerCase());

        models().slab(slabModelPath, slabTexture, slabTexture, slabTexture);
    }

    private void registerBlock(RegistryObject<Block> block, MetallurgyMetal metal) {
        models().cubeAll("block/metal/block/" + metal.name().toLowerCase(), modLoc("block/metal/smooth/" + metal.name().toLowerCase()));
    }

    private void registerBars(RegistryObject<Block> block, MetallurgyMetal metal) {
        String barsModelPath = "block/metal/bars/" + metal.name().toLowerCase();
        ResourceLocation texture = modLoc("block/metal/smooth/" + metal.name().toLowerCase());

        // Generate the bar models (post, side, and normal bars)
        ModelFile post = models().withExistingParent(barsModelPath + "_post", mcLoc("block/iron_bars_post"))
                .texture("texture", texture);

        ModelFile side = models().withExistingParent(barsModelPath + "_side", mcLoc("block/iron_bars_side"))
                .texture("texture", texture);

        // Use multipartBlock to avoid partial match conflicts
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block.get());

        // North-facing bars
        builder.part().modelFile(side).addModel().condition(IronBarsBlock.NORTH, true);

        // South-facing bars (rotated 180 degrees)
        builder.part().modelFile(side).rotationY(180).addModel().condition(IronBarsBlock.SOUTH, true);

        // East-facing bars (rotated 90 degrees)
        builder.part().modelFile(side).rotationY(90).addModel().condition(IronBarsBlock.EAST, true);

        // West-facing bars (rotated 270 degrees)
        builder.part().modelFile(side).rotationY(270).addModel().condition(IronBarsBlock.WEST, true);

        // Post model (only when no connections exist)
        builder.part().modelFile(post)
                .addModel()
                .condition(IronBarsBlock.NORTH, false)
                .condition(IronBarsBlock.SOUTH, false)
                .condition(IronBarsBlock.EAST, false)
                .condition(IronBarsBlock.WEST, false);
    }



}
