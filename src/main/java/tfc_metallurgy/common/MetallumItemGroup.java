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

    public static void fillMetalTab(CreativeModeTab.ItemDisplayParameters param, CreativeModeTab.Output out) {
        for (MetallumMetal metal : MetallumMetal.values()) {
            processBlockTypes(out, metal);
            processItemTypes(out, metal);
        }
    }

    private static void processBlockTypes(CreativeModeTab.Output out, MetallumMetal metal) {
        for (MetallumMetal.BlockType blockType : MetallumMetal.BlockType.values()) {
            accept(out, MetallumBlocks.METALS, metal, blockType);
        }
    }

    private static void processItemTypes(CreativeModeTab.Output out, MetallumMetal metal) {
        for (MetallumMetal.ItemType itemType : MetallumMetal.ItemType.values()) {
            accept(out, MetallumItems.METAL_ITEMS, metal, itemType);
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
