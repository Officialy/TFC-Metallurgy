package tfc_metallurgy.common.items;

import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.util.Helpers;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import tfc_metallurgy.TFCMetallurgy;
import tfc_metallurgy.common.blocks.rock.MetallurgyOre;
import tfc_metallurgy.common.fluids.MetallurgyFluids;
import tfc_metallurgy.util.BloomMetal;
import tfc_metallurgy.util.MetallurgyMetal;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class MetallurgyItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TFCMetallurgy.mod_id);

    public static final Map<MetallurgyMetal, Map<MetallurgyMetal.ItemType, DeferredItem<Item>>> METAL_ITEMS = Helpers.mapOf(MetallurgyMetal.class, metal ->
            Helpers.mapOf(MetallurgyMetal.ItemType.class, type -> type.has(metal), type ->
                    register("metal/" + type.name() + "/" + metal.name(), () -> type.create(metal))
            )
    );

    public static final Map<MetallurgyOre, Map<Ore.Grade, DeferredItem<Item>>> GRADED_ORES = Helpers.mapOf(MetallurgyOre.class, MetallurgyOre::isGraded, ore ->
            Helpers.mapOf(Ore.Grade.class, grade ->
                    register("ore/" + grade.name() + '_' + ore.name())
            )
    );

    public static final Map<MetallurgyOre, DeferredItem<Item>> ORES = Helpers.mapOf(MetallurgyOre.class, ore -> !ore.isGraded(), ore ->
            register("ore/"+ ore.name())
    );

    public static final Map<MetallurgyMetal, DeferredItem<BucketItem>> METAL_FLUID_BUCKETS = Helpers.mapOf(MetallurgyMetal.class, metal ->
            register("bucket/metal/" + metal.name(), () -> new BucketItem(MetallurgyFluids.METALS.get(metal).source().get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))) //todo add to tfc misc tab
    );

    public static final Map<BloomMetal, DeferredItem<Item>> RAW_BLOOM = Helpers.mapOf(BloomMetal.class, metal ->
            register("raw_"+ metal.name() +"_bloom")
    );

    public static final Map<BloomMetal, DeferredItem<Item>> REFINED_BLOOM = Helpers.mapOf(BloomMetal.class, metal ->
            register("refined_"+ metal.name() +"_bloom")
    );

    private static DeferredItem<Item> register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> DeferredItem<T> register(String name, Supplier<T> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
}
