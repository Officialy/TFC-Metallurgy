package tfc_metallurgy.datagen.recipe;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import tfc_metallurgy.TFCMetallurgy;
import tfc_metallurgy.common.items.MetallurgyItems;

import java.util.function.Consumer;

public class ModCraftingProvider extends RecipeProvider {

    public ModCraftingProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // Generate metal tool recipes
        generateMetalToolRecipes(consumer);
    }

    private void generateMetalToolRecipes(Consumer<FinishedRecipe> consumer) {
        // Example: Generate scythe recipes for all metals
        MetallurgyItems.ITEMS.getEntries().stream()
                .filter(item -> item.getId().getPath().contains("scythe_blade"))
                .forEach(blade -> {
                    String metal = blade.getId().getPath().split("/")[2];
                    ResourceLocation resultId = new ResourceLocation(TFCMetallurgy.MOD_ID, "metal/scythe/" + metal);
                    
                    // Create shaped recipe
                    shaped(consumer, resultId, blade.get(), Tags.Items.RODS_WOODEN);
                });
    }

    private void shaped(Consumer<FinishedRecipe> consumer, ResourceLocation resultId, Item blade, TagKey<Item> rod) {
        // Implementation of shaped recipe generation
        // This is a simplified example - you'll need to expand this based on your needs
        // and the actual recipe patterns you want to generate
    }
}
