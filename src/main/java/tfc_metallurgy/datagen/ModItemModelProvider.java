package tfc_metallurgy.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfc_metallurgy.TFCMetallurgy;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TFCMetallurgy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

    /**
     * The name of this provider, for logging / debugging.
     */
    @Override
    public String getName() {
        return "ChromaItemModelProvider";
    }
}