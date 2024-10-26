package tfc_metallurgy;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import tfc_metallurgy.common.ClientEvents;
import tfc_metallurgy.common.MetallumItemGroup;
import tfc_metallurgy.common.block_entities.MetallumBlockEntities;
import tfc_metallurgy.common.blocks.MetallumBlocks;
import tfc_metallurgy.common.fluids.MetallumFluids;
import tfc_metallurgy.common.items.MetallumItems;

@Mod(TFCMetallurgy.mod_id)
public class TFCMetallurgy {

	public static final String mod_id = "tfc_metallurgy";

	public static final Logger LOGGER = LogUtils.getLogger();

	public TFCMetallurgy(FMLJavaModLoadingContext i) {
		final IEventBus bus = i.getModEventBus();
		MetallumItems.ITEMS.register(bus);
		MetallumBlocks.BLOCKS.register(bus);
		MetallumFluids.FLUID_TYPES.register(bus);
		MetallumFluids.FLUIDS.register(bus);
		MetallumBlockEntities.BLOCK_ENTITIES.register(bus);
		MetallumItemGroup.CREATIVE_TABS.register(bus);
		if (FMLEnvironment.dist == Dist.CLIENT) {
			ClientEvents.init();
		}
	}
}
