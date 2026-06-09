package tfc_metallurgy.common;

import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.client.extensions.FluidRendererExtension;
import net.dries007.tfc.client.extensions.ItemRendererExtension;
import net.dries007.tfc.client.render.blockentity.JavelinItemRenderer;
import net.dries007.tfc.client.render.entity.ThrownJavelinRenderer;
import net.dries007.tfc.common.items.JavelinItem;
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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.dries007.tfc.client.render.blockentity.TFCBellBlockEntityRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import tfc_metallurgy.common.block_entities.MetallurgyBlockEntities;
import tfc_metallurgy.common.blocks.MetallurgyBlocks;
import tfc_metallurgy.common.fluids.MetallurgyFluids;
import tfc_metallurgy.common.items.MetallurgyItems;
import tfc_metallurgy.util.MetallurgyMetal;

public class ClientEvents
{
    private static final ResourceLocation MOLTEN_STILL = Helpers.identifier("block/metal/fluid/molten_still");
    private static final ResourceLocation MOLTEN_FLOW = Helpers.identifier("block/metal/fluid/molten_flow");

    public static void init(IEventBus modBus)
    {
        modBus.addListener(ClientEvents::clientSetup);
        modBus.addListener(ClientEvents::registerEntitiesRenderer);
        modBus.addListener(ClientEvents::registerLayerDefinitions);
        modBus.addListener(ClientEvents::registerClientExtensions);
    }

    public static void clientSetup(FMLClientSetupEvent event)
    {
        final RenderType cutout = RenderType.cutout();

        MetallurgyBlocks.SMALL_ORES.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));
        MetallurgyBlocks.ORES.values().forEach(inner -> inner.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout)));
        MetallurgyBlocks.GRADED_ORES.values().forEach(map -> map.values().forEach(inner -> inner.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout))));

        for (MetallurgyMetal metal : MetallurgyMetal.values())
        {
            for (MetallurgyMetal.BlockType type : MetallurgyMetal.BlockType.values())
            {
                if (type.has(metal))
                {
                    ItemBlockRenderTypes.setRenderLayer(MetallurgyBlocks.METALS.get(metal).get(type).get(), cutout);
                }
            }
        }

        ItemBlockRenderTypes.setRenderLayer(MetallurgyBlocks.ENDERIUM_BARS.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(MetallurgyBlocks.TITANIUM_BARS.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(MetallurgyBlocks.TUNGSTEN_BARS.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(MetallurgyBlocks.TUNGSTEN_STEEL_BARS.get(), cutout);

        event.enqueueWork(() -> {
            for (MetallurgyMetal metal : MetallurgyMetal.values())
            {
                if (metal.allParts())
                {
                    Item rod = MetallurgyItems.METAL_ITEMS.get(metal).get(MetallurgyMetal.ItemType.FISHING_ROD).get();
                    ItemProperties.register(rod, Helpers.identifier("cast"), (stack, level, entity, unused) -> {
                        if (entity == null) return 0.0F;
                        return entity instanceof Player player && TFCFishingRodItem.isThisTheHeldRod(player, stack) && player.fishing != null ? 1.0F : 0.0F;
                    });

                    Item shield = MetallurgyItems.METAL_ITEMS.get(metal).get(MetallurgyMetal.ItemType.SHIELD).get();
                    ItemProperties.register(shield, ResourceLocation.parse("blocking"), (stack, level, entity, unused) -> {
                        if (entity == null) return 0.0F;
                        return entity instanceof Player && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0f : 0.0f;
                    });

                    Item javelin = MetallurgyItems.METAL_ITEMS.get(metal).get(MetallurgyMetal.ItemType.JAVELIN).get();
                    ItemProperties.register(javelin, Helpers.identifier("throwing"), (stack, level, entity, unused) ->
                        entity != null && ((entity.isUsingItem() && entity.getUseItem() == stack) || (entity instanceof Monster monster && monster.isAggressive())) ? 1.0F : 0.0F
                    );

                    // Register javelin entity textures for the thrown projectile renderer
                    ThrownJavelinRenderer.JAVELIN_TEXTURES.put(javelin, Helpers.identifier("textures/entity/projectiles/" + metal.name().toLowerCase(java.util.Locale.ROOT) + "_javelin.png"));
                }
            }
        });
    }

    public static void registerClientExtensions(RegisterClientExtensionsEvent event)
    {
        // Register fluid rendering extensions
        MetallurgyFluids.METALS.forEach((metal, holder) -> event.registerFluidType(
            new FluidRendererExtension(MetallurgyFluids.ALPHA_MASK | metal.getColor(), MOLTEN_STILL, MOLTEN_FLOW, null, null),
            holder.getType()
        ));

        // Register javelin BEWLR (BlockEntityWithoutLevelRenderer) for in-hand rendering
        for (MetallurgyMetal metal : MetallurgyMetal.values())
        {
            if (metal.allParts())
            {
                var javelinSupplier = MetallurgyItems.METAL_ITEMS.get(metal).get(MetallurgyMetal.ItemType.JAVELIN);
                if (javelinSupplier != null)
                {
                    Item javelinItem = javelinSupplier.get();
                    event.registerItem(
                        ItemRendererExtension.cached(() -> new JavelinItemRenderer((JavelinItem) javelinItem)),
                        javelinItem
                    );
                }
            }
        }
    }

    public static void registerEntitiesRenderer(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(MetallurgyBlockEntities.BELL.get(), TFCBellBlockEntityRenderer::new);
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(RenderHelpers.layerId("bell_body"), BellRenderer::createBodyLayer);
    }
}
