package tfc_metallurgy.datagen;

import net.minecraftforge.common.data.LanguageProvider;
import tfc_metallurgy.TFCMetallurgy;
import net.minecraft.data.PackOutput;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, TFCMetallurgy.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

    }
}
