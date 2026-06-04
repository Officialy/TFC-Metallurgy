package tfc_metallurgy.common;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.SimpleTier;

import net.dries007.tfc.common.LevelTier;

public final class MetallurgyTiers
{
    // Level 2 (bronze-equiv)
    public static final LevelTier FLORENTINE_BRONZE = create("florentine_bronze", BlockTags.INCORRECT_FOR_IRON_TOOL, 2, 1300, 7.3f, 4.0f, 13);
    public static final LevelTier ALUMINUM = create("aluminum", BlockTags.INCORRECT_FOR_IRON_TOOL, 2, 880, 8f, 4f, 32);
    public static final LevelTier MITHRIL = create("mithril", BlockTags.INCORRECT_FOR_IRON_TOOL, 2, 1600, 10f, 4.25f, 12);
    public static final LevelTier NICKEL_SILVER = create("nickel_silver", BlockTags.INCORRECT_FOR_IRON_TOOL, 2, 4490, 11.9f, 5.3f, 19);
    public static final LevelTier INVAR = create("invar", BlockTags.INCORRECT_FOR_IRON_TOOL, 2, 4500, 12f, 5.25f, 14);
    public static final LevelTier PEWTER = create("pewter", BlockTags.INCORRECT_FOR_IRON_TOOL, 2, 4900, 9.9f, 4.7f, 23);

    // Level 3 (wrought iron-equiv)
    public static final LevelTier COMPRESSED_IRON = create("compressed_iron", BlockTags.INCORRECT_FOR_IRON_TOOL, 3, 2500, 8.2f, 4.8f, 15);
    public static final LevelTier COBALT = create("cobalt", BlockTags.INCORRECT_FOR_IRON_TOOL, 3, 2200, 13f, 4.75f, 13);
    public static final LevelTier OSMIUM = create("osmium", BlockTags.INCORRECT_FOR_IRON_TOOL, 3, 5000, 17f, 7f, 15);
    public static final LevelTier OSMIRIDIUM = create("osmiridium", BlockTags.INCORRECT_FOR_IRON_TOOL, 3, 5900, 17.5f, 7.5f, 15);
    public static final LevelTier BORON = create("boron", BlockTags.INCORRECT_FOR_IRON_TOOL, 3, 6200, 15f, 5.9f, 28);
    public static final LevelTier URANIUM = create("uranium", BlockTags.INCORRECT_FOR_IRON_TOOL, 3, 3900, 12f, 5.1f, 12);
    public static final LevelTier BERYLLIUM_COPPER = create("beryllium_copper", BlockTags.INCORRECT_FOR_IRON_TOOL, 3, 5200, 13.9f, 6.1f, 15);
    public static final LevelTier TITANIUM = create("titanium", BlockTags.INCORRECT_FOR_IRON_TOOL, 3, 3900, 10.9f, 5.5f, 20);
    public static final LevelTier THORIUM = create("thorium", BlockTags.INCORRECT_FOR_IRON_TOOL, 3, 3900, 11.5f, 5.2f, 12);
    public static final LevelTier TUNGSTEN = create("tungsten", BlockTags.INCORRECT_FOR_IRON_TOOL, 3, 4000, 11f, 5.8f, 18);

    // Level 4 (steel-equiv)
    public static final LevelTier FERROBORON = create("ferroboron", BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 4, 4500, 12.5f, 6.75f, 19);
    public static final LevelTier SIGNALUM = create("signalum", BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 4, 5900, 16f, 7f, 12);
    public static final LevelTier LUMIUM = create("lumium", BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 4, 6500, 16f, 7f, 13);
    public static final LevelTier REFINED_OBSIDIAN = create("refined_obsidian", BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 4, 5900, 15.9f, 7.1f, 15);
    public static final LevelTier REFINED_GLOWSTONE = create("refined_glowstone", BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 4, 6300, 15.9f, 7.1f, 15);

    // Level 5 (black steel-equiv)
    public static final LevelTier ENDERIUM = create("enderium", BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 5, 7900, 19f, 10f, 10);

    // Level 6 (colored steel-equiv)
    public static final LevelTier TUNGSTEN_STEEL = create("tungsten_steel", BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 6, 9200, 18f, 9.1f, 24);

    private static LevelTier create(String name, TagKey<Block> tag, int level, int uses, float speed, float damage, int enchantmentValue)
    {
        return new LeveledTier(tag, level, uses, speed, damage, enchantmentValue, name);
    }

    static class LeveledTier extends SimpleTier implements LevelTier
    {
        private final String name;
        private final int level;

        public LeveledTier(TagKey<Block> tag, int level, int uses, float speed, float damage, int enchantmentValue, String name)
        {
            super(tag, uses, speed, damage, enchantmentValue, () -> Ingredient.EMPTY);
            this.name = name;
            this.level = level;
        }

        @Override
        public int level()
        {
            return level;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }
}
