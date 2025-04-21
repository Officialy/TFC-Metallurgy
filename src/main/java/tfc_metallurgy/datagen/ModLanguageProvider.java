package tfc_metallurgy.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import tfc_metallurgy.TFCMetallurgy;
import tfc_metallurgy.common.blocks.TFCMBlocks;
import tfc_metallurgy.common.items.MetallurgyItems;

import java.util.HashSet;
import java.util.Set;

public class ModLanguageProvider extends LanguageProvider {
    private final Set<String> addedTranslations = new HashSet<>();

    public ModLanguageProvider(PackOutput output) {
        super(output, TFCMetallurgy.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Item Groups
        add("itemGroup.tfc_metallurgy.metals", "TFC Metallum - Metals");
        add("itemGroup.tfc_metallurgy.ores", "TFC Metallum - Ores");

        // Advancements
        add("tfc_metallurgy.advancements.world.metallurgist.title", "Metallurgist+");
        add("tfc_metallurgy.advancements.world.metallurgist.description", "Obtain a metal specimen of all TFC Metallurgy's metals.");

        // Add translations for all blocks and items
        TFCMBlocks.BLOCKS.getEntries().forEach(block -> {
            String path = block.getId().getPath();
            String translationKey = block.get().getDescriptionId();
            if (!addedTranslations.contains(translationKey)) {
                add(block.get(), formatName(path));
                addedTranslations.add(translationKey);
            }
        });

        MetallurgyItems.ITEMS.getEntries().forEach(item -> {
            String path = item.getId().getPath();
            String translationKey = item.get().getDescriptionId();
            if (!addedTranslations.contains(translationKey)) {
                add(item.get(), formatName(path));
                addedTranslations.add(translationKey);
            }
        });
    }

    private String formatName(String path) {
        // Split by slashes and underscores
        String[] parts = path.split("[/_]");
        StringBuilder result = new StringBuilder();
        
        for (String part : parts) {
            if (!part.isEmpty()) {
                // Skip "metal" and "block" parts as they're not needed in the display name
                if (part.equals("metal") || part.equals("block")) {
                    continue;
                }
                
                // Capitalize the first letter and add a space
                result.append(Character.toUpperCase(part.charAt(0)))
                      .append(part.substring(1).toLowerCase())
                      .append(" ");
            }
        }
        
        return result.toString().trim();
    }
}
