package tfc_metallurgy.common;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.TFCCreativeTabs;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.util.SelfTests;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallurgy.TFCMetallurgy;
import tfc_metallurgy.common.blocks.MetallumBlocks;
import tfc_metallurgy.common.blocks.rock.MetallumOre;
import tfc_metallurgy.common.items.MetallumItems;
import tfc_metallurgy.util.BloomMetal;
import tfc_metallurgy.util.MetallumMetal;

import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = TFCMetallurgy.mod_id, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MetallumItemGroup {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TFCMetallurgy.mod_id);
    public static final TFCCreativeTabs.CreativeTabHolder METAL;
    public static final TFCCreativeTabs.CreativeTabHolder ORES;

    private static void fillMetalTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out) {
        MetallumMetal[] var2 = MetallumMetal.values();
        for (MetallumMetal metal : var2) {
            MetallumMetal.BlockType[] var6 = new MetallumMetal.BlockType[]{MetallumMetal.BlockType.ANVIL, /*MetallumMetal.BlockType.BLOCK, MetallumMetal.BlockType.BLOCK_SLAB, MetallumMetal.BlockType.BLOCK_STAIRS, MetallumMetal.BlockType.BARS*/ MetallumMetal.BlockType.CHAIN, MetallumMetal.BlockType.TRAPDOOR, MetallumMetal.BlockType.LAMP};
            int var7 = var6.length;

            int var8;
            for (var8 = 0; var8 < var7; ++var8) {
                MetallumMetal.BlockType type = var6[var8];
                accept(out, MetallumBlocks.METALS, metal, type);
            }

            MetallumMetal.ItemType[] var10 = new MetallumMetal.ItemType[]{MetallumMetal.ItemType.INGOT, MetallumMetal.ItemType.DOUBLE_INGOT, MetallumMetal.ItemType.SHEET, MetallumMetal.ItemType.DOUBLE_SHEET, MetallumMetal.ItemType.ROD, MetallumMetal.ItemType.TUYERE, MetallumMetal.ItemType.PICKAXE, MetallumMetal.ItemType.PROPICK, MetallumMetal.ItemType.AXE, MetallumMetal.ItemType.SHOVEL, MetallumMetal.ItemType.HOE, MetallumMetal.ItemType.CHISEL, MetallumMetal.ItemType.HAMMER, MetallumMetal.ItemType.SAW, MetallumMetal.ItemType.KNIFE, MetallumMetal.ItemType.SCYTHE, MetallumMetal.ItemType.JAVELIN, MetallumMetal.ItemType.SWORD, MetallumMetal.ItemType.MACE, MetallumMetal.ItemType.FISHING_ROD, MetallumMetal.ItemType.SHEARS, MetallumMetal.ItemType.HELMET, MetallumMetal.ItemType.CHESTPLATE, MetallumMetal.ItemType.GREAVES, MetallumMetal.ItemType.BOOTS, MetallumMetal.ItemType.SHIELD, MetallumMetal.ItemType.PICKAXE_HEAD, MetallumMetal.ItemType.PROPICK_HEAD, MetallumMetal.ItemType.AXE_HEAD, MetallumMetal.ItemType.SHOVEL_HEAD, MetallumMetal.ItemType.HOE_HEAD, MetallumMetal.ItemType.CHISEL_HEAD, MetallumMetal.ItemType.HAMMER_HEAD, MetallumMetal.ItemType.SAW_BLADE, MetallumMetal.ItemType.KNIFE_BLADE, MetallumMetal.ItemType.SCYTHE_BLADE, MetallumMetal.ItemType.JAVELIN_HEAD, MetallumMetal.ItemType.SWORD_BLADE, MetallumMetal.ItemType.MACE_HEAD, MetallumMetal.ItemType.FISH_HOOK, MetallumMetal.ItemType.UNFINISHED_HELMET, MetallumMetal.ItemType.UNFINISHED_CHESTPLATE, MetallumMetal.ItemType.UNFINISHED_GREAVES, MetallumMetal.ItemType.UNFINISHED_BOOTS};
            var7 = var10.length;

            for (var8 = 0; var8 < var7; ++var8) {
                MetallumMetal.ItemType itemType = var10[var8];
                accept(out, MetallumItems.METAL_ITEMS, metal, itemType);
            }
        }
    }

    //Essentially the same as TFC but for our metals
    private static void fillOresTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out) {
        MetallumOre[] var2 = MetallumOre.values();
        int var3 = var2.length;

        int var4;
        MetallumOre ore;
        for(var4 = 0; var4 < var3; ++var4) {
            ore = var2[var4];
            if (ore.isGraded()) {
                accept(out, MetallumItems.GRADED_ORES, ore, Ore.Grade.POOR);
                accept(out, MetallumBlocks.SMALL_ORES, ore);
                accept(out, MetallumItems.GRADED_ORES, ore, Ore.Grade.NORMAL);
                accept(out, MetallumItems.GRADED_ORES, ore, Ore.Grade.RICH);
            }
        }

        var2 = MetallumOre.values();
        var3 = var2.length;

        for(var4 = 0; var4 < var3; ++var4) {
            ore = var2[var4];
            if (!ore.isGraded()) {
                accept(out, MetallumItems.ORES, ore);
            }
        }

/*        Gem[] var6 = Gem.values();
        var3 = var6.length;

        for(var4 = 0; var4 < var3; ++var4) {
            Gem gem = var6[var4];
            accept(out, MetallumItems.GEMS, gem);
            accept(out, MetallumItems.GEM_DUST, gem);
        }*/

        var2 = MetallumOre.values();
        var3 = var2.length;

        for(var4 = 0; var4 < var3; ++var4) {
            ore = var2[var4];
            if (ore.isGraded()) {
                MetallumOre finalOre = ore;
                MetallumBlocks.GRADED_ORES.values().forEach((map) -> {
                    ((Map)map.get(finalOre)).values().forEach((reg) -> {
                        accept(out, (Supplier)reg);
                    });
                });
            } else {
                MetallumOre finalOre1 = ore;
                MetallumBlocks.ORES.values().forEach((map) -> {
                    accept(out, map, finalOre1);
                });
            }
        }

        for (BloomMetal metal : BloomMetal.values()) {
            out.accept(new ItemStack((MetallumItems.RAW_BLOOM.get(metal)).get()));
            out.accept(new ItemStack((MetallumItems.REFINED_BLOOM.get(metal)).get()));
        }
    }

    static {
        METAL = register("metals", () -> new ItemStack((ItemLike)((RegistryObject)((Map) MetallumItems.METAL_ITEMS.get(MetallumMetal.ALUMINUM)).get(MetallumMetal.ItemType.INGOT)).get()), MetallumItemGroup::fillMetalTab);
        ORES = register("ores", () -> new ItemStack((ItemLike)((RegistryObject)((Map) MetallumItems.GRADED_ORES.get(MetallumOre.BAUXITE)).get(Ore.Grade.NORMAL)).get()), MetallumItemGroup::fillOresTab);

    }

    private static TFCCreativeTabs.CreativeTabHolder register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems) {
        RegistryObject<CreativeModeTab> reg = CREATIVE_TABS.register(
                name, () -> CreativeModeTab.builder()
                        .icon(() -> {
                            ItemStack stack = icon.get();
                            return stack.isEmpty() ? new ItemStack(Items.DIRT) : stack;
                        })
                        .title(Component.translatable("itemGroup.tfc_metallurgy." + name))
                        .displayItems(displayItems)
                        .build());
        return new TFCCreativeTabs.CreativeTabHolder(reg, displayItems);
    }

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == TFCCreativeTabs.MISC.tab().getKey()) {
            for (MetallumMetal metal : MetallumMetal.values()) {
                event.accept(MetallumItems.METAL_FLUID_BUCKETS.get(metal).get());
            }
        }
        if (event.getTabKey() == TFCCreativeTabs.DECORATIONS.tab().getKey()) {
            event.accept(MetallumBlocks.BERYLLIUM_COPPER_BELL.get());
            event.accept(MetallumBlocks.FLORENTINE_BRONZE_BELL.get());
            event.accept(MetallumBlocks.ENDERIUM_BARS.get());
            event.accept(MetallumBlocks.TITANIUM_BARS.get());
            event.accept(MetallumBlocks.TUNGSTEN_BARS.get());
            event.accept(MetallumBlocks.TUNGSTEN_STEEL_BARS.get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>> void accept(CreativeModeTab.Output out, R reg) {
        if (((ItemLike)reg.get()).asItem() == Items.AIR) {
            TerraFirmaCraft.LOGGER.error("BlockItem with no Item added to creative tab: " + reg);
            SelfTests.reportExternalError();
        } else {
            out.accept((ItemLike)reg.get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>, K1, K2> void accept(CreativeModeTab.Output out, Map<K1, Map<K2, R>> map, K1 key1, K2 key2) {
        if (map.containsKey(key1) && ((Map)map.get(key1)).containsKey(key2)) {
            out.accept((ItemLike)((Supplier)((Map)map.get(key1)).get(key2)).get());
        }

    }

    private static <T extends ItemLike, R extends Supplier<T>, K> void accept(CreativeModeTab.Output out, Map<K, R> map, K key) {
        if (map.containsKey(key)) {
            out.accept((ItemLike)((Supplier)map.get(key)).get());
        }

    }
}
