package tfc_metallurgy.util;

import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.LevelTier;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.TFCChainBlock;
import net.dries007.tfc.common.blocks.devices.AnvilBlock;
import net.dries007.tfc.common.blocks.devices.LampBlock;
import net.dries007.tfc.common.items.ChiselItem;
import net.dries007.tfc.common.items.HammerItem;
import net.dries007.tfc.common.items.JavelinItem;
import net.dries007.tfc.common.items.LampBlockItem;
import net.dries007.tfc.common.items.PropickItem;
import net.dries007.tfc.common.items.ScytheItem;
import net.dries007.tfc.common.items.TFCFishingRodItem;
import net.dries007.tfc.common.items.TFCHoeItem;
import net.dries007.tfc.common.items.TFCMaceItem;
import net.dries007.tfc.common.items.TFCShieldItem;
import net.dries007.tfc.common.items.ToolItem;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryMetal;
import tfc_metallurgy.common.MetallumArmorMaterials;
import tfc_metallurgy.common.MetallurgyTiers;
import tfc_metallurgy.common.blocks.MetallurgyBlocks;

public enum MetallurgyMetal implements RegistryMetal
{
    ALNICO(0xD87F36, MapColor.COLOR_ORANGE, Rarity.UNCOMMON, -1, PartType.DEFAULT),
    ALUMINUM(0xE3F7FF, MapColor.COLOR_LIGHT_GRAY, Rarity.UNCOMMON, -1, MetallurgyTiers.ALUMINUM, MetallumArmorMaterials.ALUMINUM),
    ANDESITE_ALLOY(0xC9CABA, MapColor.METAL, Rarity.COMMON, -1, PartType.DEFAULT),
    ANTIMONY(0xF4F4F4, MapColor.METAL, Rarity.COMMON, -1, PartType.DEFAULT),
    BORON(0x5C5454, MapColor.COLOR_GRAY, Rarity.UNCOMMON, -1, MetallurgyTiers.BORON, MetallumArmorMaterials.BORON),
    BERYLLIUM(0xF6FFCC, MapColor.COLOR_LIGHT_GREEN, Rarity.UNCOMMON, -1, PartType.DEFAULT),
    BERYLLIUM_COPPER(0xFFA677, MapColor.COLOR_ORANGE, Rarity.UNCOMMON, -1, MetallurgyTiers.BERYLLIUM_COPPER, MetallumArmorMaterials.BERYLLIUM_COPPER),
    BLUTONIUM(0x58A1CC, MapColor.COLOR_BLUE, Rarity.EPIC, -1, PartType.DEFAULT),
    CONSTANTAN(0xFCE7BB, MapColor.GOLD, Rarity.COMMON, -1, PartType.DEFAULT),
    COBALT(0x59A6EE, MapColor.COLOR_BLUE, Rarity.UNCOMMON, -1, MetallurgyTiers.COBALT, MetallumArmorMaterials.COBALT),
    COMPRESSED_IRON(0xBDBDBD, MapColor.METAL, Rarity.UNCOMMON, -1, MetallurgyTiers.COMPRESSED_IRON, MetallumArmorMaterials.COMPRESSED_IRON),
    ELECTRUM(0xFBFBBB, MapColor.GOLD, Rarity.COMMON, -1, PartType.DEFAULT),
    ENDERIUM(0x76F0EE, MapColor.COLOR_CYAN, Rarity.RARE, -1, MetallurgyTiers.ENDERIUM, MetallumArmorMaterials.ENDERIUM),
    FERROBORON(0x6F7377, MapColor.COLOR_GRAY, Rarity.EPIC, -1, MetallurgyTiers.FERROBORON, MetallumArmorMaterials.FERROBORON),
    FLORENTINE_BRONZE(0xAA924C, MapColor.TERRACOTTA_ORANGE, Rarity.UNCOMMON, -1, MetallurgyTiers.FLORENTINE_BRONZE, MetallumArmorMaterials.FLORENTINE_BRONZE),
    GRAPHITE(0x818088, MapColor.COLOR_GRAY, Rarity.UNCOMMON, -1, PartType.DEFAULT),
    HIGH_ALLOY_STEEL(0x322E42, MapColor.QUARTZ, Rarity.EPIC, -1, MetallurgyTiers.HIGH_ALLOY_STEEL, MetallumArmorMaterials.HIGH_ALLOY_STEEL),
    HIGH_CARBON_TUNGSTEN_STEEL(0xF0F399, MapColor.COLOR_GRAY, Rarity.UNCOMMON, -1, PartType.INGOT),
    INVAR(0xDBDCCC, MapColor.COLOR_LIGHT_GRAY, Rarity.UNCOMMON, -1, MetallurgyTiers.INVAR, MetallumArmorMaterials.INVAR),
    IRIDIUM(0xE8F9FF, MapColor.QUARTZ, Rarity.UNCOMMON, -1, PartType.DEFAULT),
    LEAD(0x727988, MapColor.COLOR_GRAY, Rarity.COMMON, -1, PartType.DEFAULT),
    LITHIUM(0xC9CBC3, MapColor.COLOR_LIGHT_GRAY, Rarity.RARE, -1, PartType.DEFAULT),
    LUMIUM(0xFFF1AA, MapColor.COLOR_YELLOW, Rarity.RARE, -1, MetallurgyTiers.LUMIUM, MetallumArmorMaterials.LUMIUM),
    MAGNESIUM(0xFF978195, MapColor.COLOR_PINK, Rarity.RARE, -1, PartType.DEFAULT),
    MANGANESE(0xFF9397A8, MapColor.STONE, Rarity.RARE, -1, PartType.DEFAULT),
    MITHRIL(0x89D9FF, MapColor.COLOR_LIGHT_BLUE, Rarity.COMMON, -1, MetallurgyTiers.MITHRIL, MetallumArmorMaterials.MITHRIL),
    MOLYBDENUM(0xB8B8C8, MapColor.COLOR_LIGHT_GRAY, Rarity.RARE, -1, PartType.DEFAULT),
    NEODYMIUM(0xFFC0C0C0, MapColor.COLOR_LIGHT_GRAY, Rarity.RARE, -1, PartType.DEFAULT),
    NICKEL_SILVER(0xA4A3AA, MapColor.COLOR_LIGHT_GRAY, Rarity.COMMON, -1, MetallurgyTiers.NICKEL_SILVER, MetallumArmorMaterials.NICKEL_SILVER),
    NIOBIUM(0x4A6FA5, MapColor.COLOR_BLUE, Rarity.RARE, -1, PartType.DEFAULT),
    OSMIUM(0xDDEFFF, MapColor.QUARTZ, Rarity.UNCOMMON, -1, MetallurgyTiers.OSMIUM, MetallumArmorMaterials.OSMIUM),
    OSMIRIDIUM(0xB2C1CC, MapColor.COLOR_LIGHT_GRAY, Rarity.UNCOMMON, -1, MetallurgyTiers.OSMIRIDIUM, MetallumArmorMaterials.OSMIRIDIUM),
    PEWTER(0xB0ABAA, MapColor.STONE, Rarity.UNCOMMON, -1, MetallurgyTiers.PEWTER, MetallumArmorMaterials.PEWTER),
    PINK_SLIME(0xC279B6, MapColor.COLOR_PINK, Rarity.UNCOMMON, -1, PartType.DEFAULT),
    PLATINUM(0x9CB8BF, MapColor.COLOR_LIGHT_GRAY, Rarity.RARE, -1, PartType.DEFAULT),
    REFINED_GLOWSTONE(0xFFFDCC, MapColor.COLOR_YELLOW, Rarity.RARE, -1, MetallurgyTiers.REFINED_GLOWSTONE, MetallumArmorMaterials.REFINED_GLOWSTONE),
    REFINED_OBSIDIAN(0xBC92DD, MapColor.COLOR_PURPLE, Rarity.RARE, -1, MetallurgyTiers.REFINED_OBSIDIAN, MetallumArmorMaterials.REFINED_OBSIDIAN),
    SIGNALUM(0xFFC788, MapColor.COLOR_ORANGE, Rarity.RARE, -1, MetallurgyTiers.SIGNALUM, MetallumArmorMaterials.SIGNALUM),
    SOLDER(0x888888, MapColor.STONE, Rarity.UNCOMMON, -1, PartType.DEFAULT),
    TANTALUM(0x5A5568, MapColor.COLOR_PURPLE, Rarity.RARE, -1, PartType.DEFAULT),
    THORIUM(0x787B77, MapColor.COLOR_GRAY, Rarity.EPIC, -1, MetallurgyTiers.THORIUM, MetallumArmorMaterials.THORIUM),
    TITANIUM(0xD8DAEE, MapColor.QUARTZ, Rarity.EPIC, -1, MetallurgyTiers.TITANIUM, MetallumArmorMaterials.TITANIUM),
    TUNGSTEN(0x97A3BB, MapColor.STONE, Rarity.EPIC, -1, MetallurgyTiers.TUNGSTEN, MetallumArmorMaterials.TUNGSTEN),
    TUNGSTEN_STEEL(0x555E66, MapColor.COLOR_GRAY, Rarity.EPIC, -1, MetallurgyTiers.TUNGSTEN_STEEL, MetallumArmorMaterials.TUNGSTEN_STEEL),
    URANIUM(0xF0F399, MapColor.COLOR_LIGHT_GREEN, Rarity.UNCOMMON, -1, MetallurgyTiers.URANIUM, MetallumArmorMaterials.URANIUM),
    VANADIUM(0x9AAF9E, MapColor.COLOR_GREEN, Rarity.UNCOMMON, -1, PartType.DEFAULT),
    ZIRCALOY(0xFF43423A, MapColor.COLOR_BROWN, Rarity.RARE, -1, PartType.DEFAULT),
    ZIRCONIUM(0xFF747527, MapColor.TERRACOTTA_GREEN, Rarity.RARE, -1, PartType.DEFAULT);

    private final String serializedName;
    private final PartType partType;
    @Nullable private final LevelTier toolTier;
    @Nullable private final MetallumArmorMaterials.Id armorMaterial;
    private final MapColor mapColor;
    private final Rarity rarity;
    private final int color;
    private final float weathering;

    MetallurgyMetal(int color, MapColor mapColor, Rarity rarity, float weathering, PartType partType)
    {
        this(color, mapColor, rarity, weathering, partType, null, null);
    }

    MetallurgyMetal(int color, MapColor mapColor, Rarity rarity, float weathering, LevelTier toolTier, MetallumArmorMaterials.Id armorMaterial)
    {
        this(color, mapColor, rarity, weathering, PartType.ALL, toolTier, armorMaterial);
    }

    MetallurgyMetal(int color, MapColor mapColor, Rarity rarity, float weathering, PartType partType, @Nullable LevelTier toolTier, @Nullable MetallumArmorMaterials.Id armorMaterial)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.toolTier = toolTier;
        this.armorMaterial = armorMaterial;
        this.rarity = rarity;
        this.mapColor = mapColor;
        this.color = color;
        this.partType = partType;
        this.weathering = weathering;
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }

    public int getColor()
    {
        return color;
    }

    @Override
    public Rarity rarity()
    {
        return rarity;
    }

    @Override
    public float weatheringResistance()
    {
        return weathering;
    }

    public boolean defaultParts()
    {
        return partType != PartType.INGOT;
    }

    public boolean allParts()
    {
        return partType == PartType.ALL;
    }

    @Override
    public LevelTier toolTier()
    {
        return Objects.requireNonNull(toolTier, "Tried to get non-existent tier from " + name());
    }

    @Override
    public Holder<ArmorMaterial> armorMaterial()
    {
        return Objects.requireNonNull(armorMaterial, "Tried to get non-existent armor material from " + name()).holder();
    }

    @Override
    public int armorDurability(ArmorItem.Type type)
    {
        Objects.requireNonNull(armorMaterial);
        return switch (type)
        {
            case HELMET -> armorMaterial.headDamage();
            case BODY, CHESTPLATE -> armorMaterial.chestDamage();
            case LEGGINGS -> armorMaterial.legDamage();
            case BOOTS -> armorMaterial.feetDamage();
        };
    }

    @Override
    public MapColor mapColor()
    {
        return mapColor;
    }

    @Override
    public Block getBlock(Metal.BlockType type)
    {
        return MetallurgyBlocks.METALS.get(this).get(type).get();
    }

    public int tier()
    {
        return toolTier != null ? toolTier.level() : 0;
    }

    public enum ItemType
    {
        INGOT(PartType.INGOT, true),
        DOUBLE_INGOT(PartType.DEFAULT, false),
        SHEET(PartType.DEFAULT, false),
        DOUBLE_SHEET(PartType.DEFAULT, false),
        ROD(PartType.DEFAULT, false),
        DUST(PartType.DEFAULT, false),
        TUYERE(PartType.ALL, metal -> new TieredItem(metal.toolTier(), base(metal))),
        FISH_HOOK(PartType.ALL, false),
        FISHING_ROD(PartType.ALL, metal -> new TFCFishingRodItem(base(metal).durability(metal.toolTier().getUses()), metal.toolTier())),
        UNFINISHED_LAMP(PartType.ALL, metal -> new Item(base(metal))),

        PICKAXE(PartType.ALL, metal -> new PickaxeItem(metal.toolTier(), tool(metal, 0.75f, -2.8f))),
        PICKAXE_HEAD(PartType.ALL, true),
        PROPICK(PartType.ALL, metal -> new PropickItem(metal.toolTier(), tool(metal, 0.5f, -2.8f))),
        PROPICK_HEAD(PartType.ALL, true),
        AXE(PartType.ALL, metal -> new AxeItem(metal.toolTier(), tool(metal, 1.5f, -3.1f))),
        AXE_HEAD(PartType.ALL, true),
        SHOVEL(PartType.ALL, metal -> new ShovelItem(metal.toolTier(), tool(metal, 0.875f, -3.0f))),
        SHOVEL_HEAD(PartType.ALL, true),
        HOE(PartType.ALL, metal -> new TFCHoeItem(metal.toolTier(), tool(metal, 0.5f, -2.0f))),
        HOE_HEAD(PartType.ALL, true),
        CHISEL(PartType.ALL, metal -> new ChiselItem(metal.toolTier(), tool(metal, 0.27f, 1.5f))),
        CHISEL_HEAD(PartType.ALL, true),
        HAMMER(PartType.ALL, metal -> new HammerItem(metal.toolTier(), tool(metal, 1f, -3f))),
        HAMMER_HEAD(PartType.ALL, true),
        SAW(PartType.ALL, metal -> new AxeItem(metal.toolTier(), tool(metal, 0.5f, -3f))),
        SAW_BLADE(PartType.ALL, true),
        JAVELIN(PartType.ALL, metal -> new JavelinItem(metal.toolTier(), tool(metal, 0.7f, -2.6f))),
        JAVELIN_HEAD(PartType.ALL, true),
        SWORD(PartType.ALL, metal -> new SwordItem(metal.toolTier(), tool(metal, 1f, -2.4f))),
        SWORD_BLADE(PartType.ALL, true),
        MACE(PartType.ALL, metal -> new TFCMaceItem(tool(metal, 1.3f, -3.4f).durability(metal.toolTier().getUses()))),
        MACE_HEAD(PartType.ALL, true),
        KNIFE(PartType.ALL, metal -> new ToolItem(metal.toolTier(), TFCTags.Blocks.MINEABLE_WITH_KNIFE, tool(metal, 0.6f, -2.0f))),
        KNIFE_BLADE(PartType.ALL, true),
        SCYTHE(PartType.ALL, metal -> new ScytheItem(metal.toolTier(), tool(metal, 0.7f, -3.2f))),
        SCYTHE_BLADE(PartType.ALL, true),
        SHEARS(PartType.ALL, metal -> new ShearsItem(base(metal).durability(metal.toolTier().getUses()))),

        UNFINISHED_HELMET(PartType.ALL, false),
        HELMET(PartType.ALL, armor(ArmorItem.Type.HELMET)),
        UNFINISHED_CHESTPLATE(PartType.ALL, false),
        CHESTPLATE(PartType.ALL, armor(ArmorItem.Type.CHESTPLATE)),
        UNFINISHED_GREAVES(PartType.ALL, false),
        GREAVES(PartType.ALL, armor(ArmorItem.Type.LEGGINGS)),
        UNFINISHED_BOOTS(PartType.ALL, false),
        BOOTS(PartType.ALL, armor(ArmorItem.Type.BOOTS)),

        SHIELD(PartType.ALL, metal -> new TFCShieldItem(metal.toolTier(), base(metal)));

        private static Item.Properties base(RegistryMetal metal)
        {
            return new Item.Properties().rarity(metal.rarity());
        }

        private static Item.Properties tool(RegistryMetal metal, float attackDamageFactor, float attackSpeed)
        {
            return base(metal).attributes(ToolItem.productAttributes(metal.toolTier(), attackDamageFactor, attackSpeed));
        }

        private static Function<RegistryMetal, Item> armor(ArmorItem.Type type)
        {
            return metal -> new ArmorItem(metal.armorMaterial(), type, base(metal).durability(metal.armorDurability(type)));
        }

        private final Function<RegistryMetal, Item> itemFactory;
        private final PartType type;
        private final boolean mold;

        ItemType(PartType type, boolean mold)
        {
            this(type, mold, metal -> new Item(base(metal)));
        }

        ItemType(PartType type, Function<RegistryMetal, Item> itemFactory)
        {
            this(type, false, itemFactory);
        }

        ItemType(PartType type, boolean mold, Function<RegistryMetal, Item> itemFactory)
        {
            this.type = type;
            this.mold = mold;
            this.itemFactory = itemFactory;
        }

        public Item create(RegistryMetal metal)
        {
            return itemFactory.apply(metal);
        }

        public boolean has(MetallurgyMetal metal)
        {
            return type.hasMetal(metal.partType);
        }

        public boolean hasMold()
        {
            return mold;
        }
    }

    public enum BlockType
    {
        ANVIL(PartType.ALL, metal -> new AnvilBlock(ExtendedProperties.of().mapColor(metal.mapColor()).noOcclusion().sound(SoundType.ANVIL).strength(10, 10).requiresCorrectToolForDrops().blockEntity(TFCBlockEntities.ANVIL), metal.toolTier().level())),
        CHAIN(PartType.ALL, metal -> new TFCChainBlock(BlockBehaviour.Properties.of().mapColor(metal.mapColor()).requiresCorrectToolForDrops().strength(5, 6).sound(SoundType.CHAIN).lightLevel(TFCBlocks.lavaLoggedBlockEmission()))),
        LAMP(PartType.ALL, metal -> new LampBlock(ExtendedProperties.of().mapColor(metal.mapColor()).noOcclusion().sound(SoundType.LANTERN).strength(4, 10).randomTicks().lightLevel(state -> state.getValue(LampBlock.LIT) ? 15 : 0).blockEntity(TFCBlockEntities.LAMP)), (block, properties) -> new LampBlockItem(block, properties.stacksTo(1))),
        TRAPDOOR(PartType.ALL, metal -> new TrapDoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(metal.mapColor()).requiresCorrectToolForDrops().strength(5.0f).sound(SoundType.METAL).noOcclusion().isValidSpawn(TFCBlocks::neverEntity))),
        BLOCK(PartType.DEFAULT, metal -> new Block(BlockBehaviour.Properties.of().mapColor(metal.mapColor()).requiresCorrectToolForDrops().strength(5.0f, 6.0f).sound(SoundType.METAL))),
        STAIRS(PartType.DEFAULT, metal -> new StairBlock(Blocks.AIR.defaultBlockState(), BlockBehaviour.Properties.of().mapColor(metal.mapColor()).requiresCorrectToolForDrops().strength(5.0f, 6.0f).sound(SoundType.METAL))),
        SLAB(PartType.DEFAULT, metal -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(metal.mapColor()).requiresCorrectToolForDrops().strength(5.0f, 6.0f).sound(SoundType.METAL)));

        private final Function<RegistryMetal, Block> blockFactory;
        private final BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory;
        private final PartType type;

        BlockType(PartType type, Function<RegistryMetal, Block> blockFactory, BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory)
        {
            this.type = type;
            this.blockFactory = blockFactory;
            this.blockItemFactory = blockItemFactory;
        }

        BlockType(PartType type, Function<RegistryMetal, Block> blockFactory)
        {
            this(type, blockFactory, BlockItem::new);
        }

        public Supplier<Block> create(RegistryMetal metal)
        {
            return () -> blockFactory.apply(metal);
        }

        public Function<Block, BlockItem> createBlockItem(Item.Properties properties)
        {
            return block -> blockItemFactory.apply(block, properties);
        }

        public boolean has(MetallurgyMetal metal)
        {
            return type.hasMetal(metal.partType);
        }
    }

    enum PartType
    {
        INGOT, DEFAULT, ALL;

        boolean hasMetal(PartType metal)
        {
            return switch (this)
            {
                case ALL -> metal == ALL;
                case DEFAULT -> metal.ordinal() >= DEFAULT.ordinal();
                case INGOT -> true;
            };
        }
    }
}
