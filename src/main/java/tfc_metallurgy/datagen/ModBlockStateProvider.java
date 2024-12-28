package tfc_metallurgy.datagen;

import net.dries007.tfc.common.blocks.devices.LampBlock;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.Half;
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
                                switch (type) {
                                    case LAMP:
                                        // Define base paths
                                        String basePath = "block/metal/lamp/" + metal.name().toLowerCase();
                                        String fullMetalTexture = "tfc_metallurgy:block/metal/smooth/" + metal.name().toLowerCase();
                                        String chainTexture = "tfc_metallurgy:block/metal/chain/" + metal.name().toLowerCase();
                                        String lampOnTexture = "tfc:block/lamp";
                                        String lampOffTexture = "tfc:block/lamp_off";

                                        // Generate models
                                        ModelFile lampOn = models().withExistingParent(basePath + "_on", new ResourceLocation("tfc", "block/lamp"))
                                                .texture("metal", fullMetalTexture)
                                                .texture("lamp", lampOnTexture);

                                        ModelFile lampOff = models().withExistingParent(basePath + "_off", new ResourceLocation("tfc", "block/lamp"))
                                                .texture("metal", fullMetalTexture)
                                                .texture("lamp", lampOffTexture);

                                        ModelFile hangingOn = models().withExistingParent(basePath + "_hanging_on", new ResourceLocation("tfc", "block/lamp_hanging"))
                                                .texture("metal", fullMetalTexture)
                                                .texture("chain", chainTexture)
                                                .texture("lamp", lampOnTexture);

                                        ModelFile hangingOff = models().withExistingParent(basePath + "_hanging_off", new ResourceLocation("tfc", "block/lamp_hanging"))
                                                .texture("metal", fullMetalTexture)
                                                .texture("chain", chainTexture)
                                                .texture("lamp", lampOffTexture);

                                        // Generate block state
                                        getVariantBuilder(block.get())
                                                .partialState().with(LampBlock.LIT, true).with(LampBlock.HANGING, false)
                                                .modelForState().modelFile(lampOn).addModel()
                                                .partialState().with(LampBlock.LIT, false).with(LampBlock.HANGING, false)
                                                .modelForState().modelFile(lampOff).addModel()
                                                .partialState().with(LampBlock.LIT, true).with(LampBlock.HANGING, true)
                                                .modelForState().modelFile(hangingOn).addModel()
                                                .partialState().with(LampBlock.LIT, false).with(LampBlock.HANGING, true)
                                                .modelForState().modelFile(hangingOff).addModel();
                                        break;
                                    case ANVIL:
                                        ModelFile anvilModel = models().withExistingParent(
                                                block.getId().getPath(), new ResourceLocation("tfc", "block/anvil")
                                        ).texture("base", modLoc("block/metal/anvil/" + metal.name().toLowerCase()));
                                        simpleBlock(block.get(), anvilModel);
                                        break;
                                    case CHAIN:
                                        ModelFile chainModel = models().withExistingParent(
                                                block.getId().getPath(), mcLoc("block/chain")
                                        ).texture("particle", modLoc("block/metal/chain/" + metal.name().toLowerCase()));
                                        simpleBlock(block.get(), chainModel);
                                        break;
                                    case TRAPDOOR:
                                        // Define base path and texture
                                        ResourceLocation basePath1 = modLoc("block/metal/trapdoor/" + metal.name().toLowerCase());
                                        ResourceLocation fullMetalTexture1 = modLoc("block/metal/smooth/" + metal.name().toLowerCase());

                                        // Generate models
                                        ModelFile bottom = models().withExistingParent(basePath1 + "_bottom", new ResourceLocation("minecraft", "block/template_orientable_trapdoor_bottom"))
                                                .texture("texture", fullMetalTexture1);

                                        ModelFile top = models().withExistingParent(basePath1 + "_top", new ResourceLocation("minecraft", "block/template_orientable_trapdoor_top"))
                                                .texture("texture", fullMetalTexture1);

                                        ModelFile open = models().withExistingParent(basePath1 + "_open", new ResourceLocation("minecraft", "block/template_orientable_trapdoor_open"))
                                                .texture("texture", fullMetalTexture1);

                                        // Generate block state
                                        getVariantBuilder(block.get())
                                                .partialState().with(TrapDoorBlock.OPEN, false).with(TrapDoorBlock.HALF, Half.BOTTOM)
                                                .modelForState().modelFile(bottom).addModel()
                                                .partialState().with(TrapDoorBlock.OPEN, false).with(TrapDoorBlock.HALF, Half.TOP)
                                                .modelForState().modelFile(top).addModel()
                                                .partialState().with(TrapDoorBlock.OPEN, true)
                                                .modelForState().modelFile(open).addModel();

                                        break;
                                }
                            }
                        })
        );


        // Register SMALL_ORES
        MetallurgyBlocks.SMALL_ORES.values().forEach(block -> {
            ModelFile smallOreModel = models().withExistingParent(block.getId().getPath(), mcLoc("block/cube_all"))
                    .texture("all", modLoc("item/ore/" + block.getId().getPath().substring(block.getId().getPath().lastIndexOf('/') + 1)));
            simpleBlock(block.get(), smallOreModel);
        });


        // Register ORES
        MetallurgyBlocks.ORES.forEach((rockType, oreMap) ->
                oreMap.forEach((oreType, block) -> {
                    String modelName = oreType.name().toLowerCase();
                    ResourceLocation baseTexture = new ResourceLocation("tfc", "block/rock/raw/" + rockType.name().toLowerCase());

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