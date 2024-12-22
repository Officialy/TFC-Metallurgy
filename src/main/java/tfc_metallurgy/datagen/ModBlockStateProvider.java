package tfc_metallurgy.datagen;

import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfc_metallurgy.TFCMetallurgy;
import net.minecraft.data.PackOutput;

public class ModBlockStateProvider extends BlockStateProvider {

    ExistingFileHelper existingFileHelper;

    public ModBlockStateProvider(final PackOutput packOutput, final ExistingFileHelper existingFileHelper) {
        super(packOutput, TFCMetallurgy.MOD_ID, existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
//        simpleBlock(ChromaBlocks.BLACK_CHROMATICAL_LEAVES.get(), new ModelFile.ExistingModelFile(mcLoc("block/oak_leaves"), existingFileHelper));
    }
}