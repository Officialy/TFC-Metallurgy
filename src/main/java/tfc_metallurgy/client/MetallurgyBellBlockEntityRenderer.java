package tfc_metallurgy.client;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.client.render.blockentity.TFCBellBlockEntityRenderer;
import net.dries007.tfc.util.Helpers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import tfc_metallurgy.common.blocks.MetallurgyBlocks;

public class MetallurgyBellBlockEntityRenderer extends TFCBellBlockEntityRenderer
{
    private static final Map<Block, Material> MATERIALS = RenderHelpers.mapOf(map -> {
        map.accept(MetallurgyBlocks.BERYLLIUM_COPPER_BELL, new Material(RenderHelpers.BLOCKS_ATLAS, Helpers.identifier("entity/bell/beryllium_copper")));
        map.accept(MetallurgyBlocks.FLORENTINE_BRONZE_BELL, new Material(RenderHelpers.BLOCKS_ATLAS, Helpers.identifier("entity/bell/florentine_bronze")));
    });

    private final ModelPart bellBody;

    public MetallurgyBellBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        super(context);
        this.bellBody = context.bakeLayer(RenderHelpers.layerId("bell_body")).getChild("bell_body");
    }

    @Override
    public void render(BellBlockEntity bellBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay)
    {
        Material material = MATERIALS.get(bellBlockEntity.getBlockState().getBlock());
        if (material == null)
        {
            super.render(bellBlockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
            return;
        }

        float ticks = (float) bellBlockEntity.ticks + partialTick;
        float xRot = 0.0F;
        float zRot = 0.0F;

        if (bellBlockEntity.shaking)
        {
            float swing = Mth.sin(ticks / (float) Math.PI) / (4.0F + ticks / 3.0F);
            Direction dir = bellBlockEntity.clickDirection;
            if (dir == Direction.NORTH) xRot = -swing;
            else if (dir == Direction.SOUTH) xRot = swing;
            else if (dir == Direction.EAST) zRot = -swing;
            else if (dir == Direction.WEST) zRot = swing;
        }

        bellBody.xRot = xRot;
        bellBody.zRot = zRot;
        VertexConsumer vertexConsumer = material.buffer(bufferSource, RenderType::entitySolid);
        bellBody.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }
}
