package tfc_metallurgy;

import com.mojang.logging.LogUtils;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import org.slf4j.Logger;
import tfc_metallurgy.common.ClientEvents;
import tfc_metallurgy.common.MetallumArmorMaterials;
import tfc_metallurgy.common.MetallurgyItemGroup;
import tfc_metallurgy.common.block_entities.MetallurgyBlockEntities;
import tfc_metallurgy.common.blocks.MetallurgyBlocks;
import tfc_metallurgy.common.fluids.MetallurgyFluids;
import tfc_metallurgy.common.items.MetallurgyItems;
import tfc_metallurgy.util.MetallurgyMetal;

@Mod(TFCMetallurgy.mod_id)
public class TFCMetallurgy {

	public static final String mod_id = "tfc_metallurgy";

	public static final Logger LOGGER = LogUtils.getLogger();

	public TFCMetallurgy(IEventBus bus) {
		MetallumArmorMaterials.ARMOR_MATERIALS.register(bus);
		MetallurgyItems.ITEMS.register(bus);
		MetallurgyBlocks.BLOCKS.register(bus);
		MetallurgyFluids.FLUID_TYPES.register(bus);
		MetallurgyFluids.FLUIDS.register(bus);
		MetallurgyBlockEntities.BLOCK_ENTITIES.register(bus);
		MetallurgyItemGroup.CREATIVE_TABS.register(bus);
		bus.addListener(MetallurgyItemGroup::addCreative);
		bus.addListener(TFCMetallurgy::addBlocksToBlockEntities);
		if (FMLEnvironment.dist == Dist.CLIENT) {
			ClientEvents.init(bus);
		}
	}

	private static void addBlocksToBlockEntities(BlockEntityTypeAddBlocksEvent event)
	{
		Block[] anvilBlocks = MetallurgyBlocks.METALS.values().stream()
			.filter(map -> map.containsKey(MetallurgyMetal.BlockType.ANVIL))
			.map(map -> map.get(MetallurgyMetal.BlockType.ANVIL).get())
			.toArray(Block[]::new);
		event.modify(TFCBlockEntities.ANVIL.get(), anvilBlocks);
	}
}
