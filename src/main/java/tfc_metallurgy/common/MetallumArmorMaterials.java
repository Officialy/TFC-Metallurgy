package tfc_metallurgy.common;

import java.util.EnumMap;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.resources.ResourceLocation;
import tfc_metallurgy.TFCMetallurgy;

public final class MetallumArmorMaterials
{
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, TFCMetallurgy.mod_id);

    public static final Id FLORENTINE_BRONZE = register("florentine_bronze", 275, 320, 329, 257, 1, 4, 4, 1, 9, 0.0f, 0.0f);
    public static final Id ALUMINUM = register("aluminum", 300, 350, 315, 290, 1, 5, 6, 1, 32, 0f, 0f);
    public static final Id MITHRIL = register("mithril", 320, 370, 335, 310, 1, 6, 6, 1, 12, 0f, 0.5f);
    public static final Id NICKEL_SILVER = register("nickel_silver", 290, 390, 265, 240, 1, 4, 5, 1, 19, 0f, 0.2f);
    public static final Id INVAR = register("invar", 350, 410, 365, 340, 1, 5, 7, 1, 14, 0f, 0f);
    public static final Id COMPRESSED_IRON = register("compressed_iron", 450, 500, 538, 380, 1, 4, 5, 2, 15, 0f, 0f);
    public static final Id COBALT = register("cobalt", 520, 570, 535, 510, 1, 7, 5, 1, 13, 0f, 0f);
    public static final Id OSMIRIDIUM = register("osmiridium", 610, 600, 590, 545, 1, 6, 7, 1, 15, 0f, 0f);
    public static final Id OSMIUM = register("osmium", 610, 600, 590, 545, 1, 6, 7, 1, 15, 0f, 0f);
    public static final Id URANIUM = register("uranium", 520, 510, 490, 475, 1, 6, 8, 1, 12, 0.5f, 0.5f);
    public static final Id BERYLLIUM_COPPER = register("beryllium_copper", 620, 610, 600, 555, 2, 6, 5, 3, 19, 0.5f, 0f);
    public static final Id PEWTER = register("pewter", 500, 490, 480, 435, 1, 7, 6, 1, 20, 0.5f, 0f);
    public static final Id BORON = register("boron", 580, 570, 560, 515, 1, 6, 7, 1, 18, 0.5f, 0f);
    public static final Id FERROBORON = register("ferroboron", 620, 600, 640, 440, 2, 5, 6, 2, 12, 1f, 0f);
    public static final Id SIGNALUM = register("signalum", 720, 700, 690, 675, 1, 7, 7, 1, 12, 0.5f, 0f);
    public static final Id LUMIUM = register("lumium", 720, 700, 690, 675, 1, 7, 7, 1, 13, 0.5f, 0f);
    public static final Id REFINED_OBSIDIAN = register("refined_obsidian", 720, 700, 690, 675, 1, 7, 7, 1, 12, 0.5f, 0f);
    public static final Id REFINED_GLOWSTONE = register("refined_glowstone", 720, 700, 690, 675, 1, 7, 7, 1, 13, 0.5f, 0f);
    public static final Id ENDERIUM = register("enderium", 740, 720, 710, 695, 2, 8, 8, 1, 10, 1f, 0.5f);
    public static final Id TITANIUM = register("titanium", 410, 400, 490, 310, 1, 4, 5, 1, 10, 0.5f, 0f);
    public static final Id THORIUM = register("thorium", 400, 410, 485, 310, 1, 5, 4, 2, 12, 0.5f, 0f);
    public static final Id TUNGSTEN = register("tungsten", 395, 405, 500, 305, 1, 5, 5, 1, 15, 0.5f, 0f);
    public static final Id TUNGSTEN_STEEL = register("tungsten_steel", 904, 1040, 1030, 735, 4, 6, 8, 4, 23, 3f, 0.1f);
    public static final Id HIGH_ALLOY_STEEL = register("high_alloy_steel", 1120, 1290, 1270, 910, 4, 7, 9, 4, 22, 3.5f, 0.15f);

    private static Id register(
        String name,
        int feetDamage, int legDamage, int chestDamage, int headDamage,
        int feetReduction, int legReduction, int chestReduction, int headReduction,
        int enchantability, float toughness, float knockbackResistance
    ) {
        return new Id(ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, feetReduction);
                map.put(ArmorItem.Type.LEGGINGS, legReduction);
                map.put(ArmorItem.Type.CHESTPLATE, chestReduction);
                map.put(ArmorItem.Type.HELMET, headReduction);
                map.put(ArmorItem.Type.BODY, chestReduction);
            }),
            enchantability,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.EMPTY,
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(TFCMetallurgy.mod_id, name))),
            toughness,
            knockbackResistance
        )), feetDamage, legDamage, chestDamage, headDamage);
    }

    public record Id(
        Holder<ArmorMaterial> holder,
        int feetDamage,
        int legDamage,
        int chestDamage,
        int headDamage
    ) {}
}
