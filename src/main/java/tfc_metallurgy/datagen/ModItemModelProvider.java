package tfc_metallurgy.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallurgy.TFCMetallurgy;
import tfc_metallurgy.common.blocks.TFCMBlocks;
import tfc_metallurgy.common.items.MetallurgyItems;
import tfc_metallurgy.util.MetallurgyMetal;

import java.util.Arrays;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TFCMetallurgy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Register METAL BLOCK ITEMS
        TFCMBlocks.METALS.forEach((metal, metalMap) ->
                Arrays.stream(MetallurgyMetal.BlockType.values())
                        .filter(metalMap::containsKey)
                        .forEach(type -> {
                            RegistryObject<?> block = metalMap.get(type);
                            if (block != null) {
                                switch (type) {
                                    case BLOCK_STAIRS:
                                        // ✅ Generate stairs model manually
                                        String metalName = metal.name().toLowerCase();
                                        String stairBasePath = "block/metal/smooth/" + metalName;

                                        getBuilder("item/metal/stairs/" + metalName)
                                                .parent(new ModelFile.UncheckedModelFile("minecraft:block/stairs"))
                                                .texture("bottom", modLoc(stairBasePath))
                                                .texture("top", modLoc(stairBasePath))
                                                .texture("side", modLoc(stairBasePath));

                                        getBuilder("item/metal/stairs_inner/" + metalName)
                                                .parent(new ModelFile.UncheckedModelFile("minecraft:block/inner_stairs"))
                                                .texture("bottom", modLoc(stairBasePath))
                                                .texture("top", modLoc(stairBasePath))
                                                .texture("side", modLoc(stairBasePath));

                                        getBuilder("item/metal/stairs_outer/" + metalName)
                                                .parent(new ModelFile.UncheckedModelFile("minecraft:block/outer_stairs"))
                                                .texture("bottom", modLoc(stairBasePath))
                                                .texture("top", modLoc(stairBasePath))
                                                .texture("side", modLoc(stairBasePath));
                                        break;

                                    case CHAIN:
                                    case ANVIL:
                                        getBuilder("item/metal/" + block.getId().getPath())
                                                .parent(getExistingFile(modLoc("block/metal/anvil/" + metal.name().toLowerCase())));
                                        break;
                                    case TRAPDOOR:
                                        withExistingParent("item/metal/" + block.getId().getPath(),
                                                new ResourceLocation(TFCMetallurgy.MOD_ID, "block/" + block.getId().getPath() + "_bottom"));
                                        break;
                                    case LAMP:
                                        // Metal lamp item models
                                        String lampModelPath = "block/metal/lamp/" + metal.name().toLowerCase();
                                        withExistingParent("item/metal/" + lampModelPath,
                                                new ResourceLocation(TFCMetallurgy.MOD_ID, lampModelPath + "_off"));
                                        break;
                                }
                            }
                        })
        );

        // Register ORE ITEMS
        TFCMBlocks.ORES.forEach((rockType, oreMap) ->
                oreMap.forEach((oreType, block) -> {
                    withExistingParent("item/" + block.getId().getPath(),
                            new ResourceLocation("minecraft", "item/generated"))
                            .texture("layer0", new ResourceLocation(TFCMetallurgy.MOD_ID, "block/ore/" + oreType.name().toLowerCase()));
                })
        );

        // Register GRADED ORE ITEMS
        TFCMBlocks.GRADED_ORES.forEach((rockType, oreMap) ->
                oreMap.forEach((oreType, gradeMap) ->
                        gradeMap.forEach((grade, block) -> {
                            String modelPath = "block/ore/" + grade.name().toLowerCase() + "_" + oreType.name().toLowerCase() + "/" + rockType.name().toLowerCase();
                            withExistingParent("item/" + block.getId().getPath(),
                                    new ResourceLocation(TFCMetallurgy.MOD_ID, modelPath));
                        })
                )
        );

        TFCMBlocks.SMALL_ORES.forEach((oreType, block) -> {
            String modelPath = "item/ore/small_" + oreType.name().toLowerCase();
            ResourceLocation texturePath = modLoc("item/ore/small_" + oreType.name().toLowerCase());

            getBuilder(modelPath)
                    .parent(getExistingFile(mcLoc("item/generated"))) // ✅ Uses correct parent model
                    .texture("layer0", texturePath);
        });


        MetallurgyItems.METAL_ITEMS.forEach((metal, metalMap) ->
                metalMap.forEach((type, item) -> {
                    String modelPath = "item/metal/" + type.name().toLowerCase() + "/" + metal.name().toLowerCase();

                    switch (type) {
                        case AXE, PICKAXE, SHOVEL, HOE, SWORD ->
                                getBuilder(modelPath)
                                        .parent(getExistingFile(mcLoc("item/handheld")))
                                        .texture("layer0", modLoc(modelPath));

                        case SHIELD ->
                                getBuilder(modelPath)
                                        .parent(getExistingFile(mcLoc("item/shield")))
                                        .texture("particle", modLoc("item/metal/shield/" + metal.name().toLowerCase() + "_front"))
                                        .texture("front", modLoc("item/metal/shield/" + metal.name().toLowerCase() + "_front"))
                                        .texture("back", modLoc("item/metal/shield/" + metal.name().toLowerCase() + "_back"));

                        default ->
                                getBuilder(modelPath)
                                        .parent(getExistingFile(mcLoc("item/generated")))
                                        .texture("layer0", modLoc(modelPath));
                    }
                })
        );

    }

    @Override
    public String getName() {
        return "TFC Metallurgy Item Models";
    }
}
