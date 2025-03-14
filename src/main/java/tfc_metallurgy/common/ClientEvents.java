package tfc_metallurgy.common;

import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.common.items.TFCFishingRodItem;
import net.dries007.tfc.util.Helpers;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.dries007.tfc.client.render.blockentity.TFCBellBlockEntityRenderer;
import tfc_metallurgy.common.block_entities.MetallurgyBlockEntities;
import tfc_metallurgy.common.blocks.TFCMBlocks;
import tfc_metallurgy.common.items.MetallurgyItems;
import tfc_metallurgy.util.MetallurgyMetal;

public class ClientEvents {

    public static void init() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ClientEvents::clientSetup);
//        bus.addListener(ClientEvents::onTextureStitch);
        bus.addListener(ClientEvents::registerEntitiesRenderer);
        bus.addListener(ClientEvents::registerLayerDefinitions);
    }

/*    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        final ResourceLocation sheet = event.getAtlas().location();
        if (sheet.equals(RenderHelpers.BLOCKS_ATLAS)) {
            for (MetallumMetal metal : MetallumMetal.values())
            {
                event.addSprite(new ResourceLocation("tfc_metallurgy:block/metal/smooth/" + metal.getSerializedName()));
            }
            event.addSprite(Helpers.identifier("entity/bell/beryllium_copper"));
            event.addSprite(Helpers.identifier("entity/bell/florentine_bronze"));
        }
    }*/

    public static void clientSetup(FMLClientSetupEvent event) {

        final RenderType cutout = RenderType.cutout();

        TFCMBlocks.SMALL_ORES.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));

        TFCMBlocks.ORES.values().forEach(inner -> inner.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout)));

        TFCMBlocks.GRADED_ORES.values().forEach(map -> map.values().forEach(inner -> inner.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout))));
        for(MetallurgyMetal metal: MetallurgyMetal.values()) {
            for(MetallurgyMetal.BlockType type: MetallurgyMetal.BlockType.values()) {
                if(type.has(metal)) {
                    ItemBlockRenderTypes.setRenderLayer(TFCMBlocks.METALS.get(metal).get(type).get(), cutout);
                }
            }
        }

//        ItemBlockRenderTypes.setRenderLayer(TFCMBlocks.ENDERIUM_BARS.get(), cutout);
//        ItemBlockRenderTypes.setRenderLayer(TFCMBlocks.TITANIUM_BARS.get(), cutout);
//        ItemBlockRenderTypes.setRenderLayer(TFCMBlocks.TUNGSTEN_BARS.get(), cutout);
//        ItemBlockRenderTypes.setRenderLayer(TFCMBlocks.TUNGSTEN_STEEL_BARS.get(), cutout);

        event.enqueueWork(() -> {

            for (MetallurgyMetal metal : MetallurgyMetal.values())
            {
                if (metal.hasTools())
                {
                    Item rod = MetallurgyItems.METAL_ITEMS.get(metal).get(MetallurgyMetal.ItemType.FISHING_ROD).get();
                    ItemProperties.register(rod, Helpers.identifier("cast"), (stack, level, entity, unused) -> {
                        if (entity == null)
                        {
                            return 0.0F;
                        }
                        else
                        {
                            return entity instanceof Player player && TFCFishingRodItem.isThisTheHeldRod(player, stack) && player.fishing != null ? 1.0F : 0.0F;
                        }
                    });

                    Item shield = MetallurgyItems.METAL_ITEMS.get(metal).get(MetallurgyMetal.ItemType.SHIELD).get();
                    ItemProperties.register(shield, new ResourceLocation("blocking"), (stack, level, entity, unused) -> {
                        if (entity == null)
                        {
                            return 0.0F;
                        }
                        else
                        {
                            return entity instanceof Player && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0f : 0.0f;
                        }
                    });

                    Item javelin = MetallurgyItems.METAL_ITEMS.get(metal).get(MetallurgyMetal.ItemType.JAVELIN).get();
                    ItemProperties.register(javelin, Helpers.identifier("throwing"), (stack, level, entity, unused) ->
                            entity != null && ((entity.isUsingItem() && entity.getUseItem() == stack) || (entity instanceof Monster monster && monster.isAggressive())) ? 1.0F : 0.0F
                    );
                }
            }
        });
    }

    public static void registerEntitiesRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(MetallurgyBlockEntities.BELL.get(), TFCBellBlockEntityRenderer::new);
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(RenderHelpers.modelIdentifier("bell_body"), BellRenderer::createBodyLayer);
    }
}
