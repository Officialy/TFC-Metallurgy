package tfc_metallurgy.common.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBellBlock;
import net.minecraft.resources.ResourceLocation;

public class MetallurgyBellBlock extends TFCBellBlock {

    public MetallurgyBellBlock(ExtendedProperties properties, float pitch, String textureLocation) {
        super(properties, pitch, textureLocation);
    }

    public MetallurgyBellBlock(ExtendedProperties properties, float pitch, ResourceLocation textureLocation) {
        super(properties, pitch, textureLocation);
    }
}
