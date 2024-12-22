package tfc_metallurgy;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import tfc_metallurgy.common.ClientEvents;
import tfc_metallurgy.common.MetallurgyItemGroup;
import tfc_metallurgy.common.block_entities.MetallurgyBlockEntities;
import tfc_metallurgy.common.blocks.MetallurgyBlocks;
import tfc_metallurgy.common.fluids.MetallurgyFluids;
import tfc_metallurgy.common.items.MetallurgyItems;

@Mod(TFCMetallurgy.MOD_ID)
public class TFCMetallurgy {

	public static final String MOD_ID = "tfc_metallurgy";

	public static final Logger LOGGER = LogUtils.getLogger();

	public TFCMetallurgy(FMLJavaModLoadingContext i) {
		final IEventBus bus = i.getModEventBus();
		MetallurgyItems.ITEMS.register(bus);
		MetallurgyBlocks.BLOCKS.register(bus);
		MetallurgyFluids.FLUID_TYPES.register(bus);
		MetallurgyFluids.FLUIDS.register(bus);
		MetallurgyBlockEntities.BLOCK_ENTITIES.register(bus);
		MetallurgyItemGroup.CREATIVE_TABS.register(bus);
		if (FMLEnvironment.dist == Dist.CLIENT) {
			ClientEvents.init();
		}
	}
}
