package tfc_metallurgy.common.blocks.rock;

import net.dries007.tfc.util.registry.RegistryRock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public enum MetallurgyOre {
    BAUXITE(true),
    BERTRANDITE(true),
    COBALTITE(true),
    KERNITE(true),
    GALENA(true),
    MAGNESITE(true),
    MONAZITE(true),
    NATIVE_OSMIUM(true),
    NATIVE_IRIDIUM(true),
    NATIVE_PLATINUM(true),
    RUTILE(true),
    PYROLUSITE(true),
    SPODUMENE(true),
    STIBNITE(true),
    URANINITE(true),
    WOLFRAMITE(true),
    ZIRCON(true),
    CERTUS_QUARTZ(false);

    private final boolean isGraded;

    MetallurgyOre(boolean grade) {
        this.isGraded = grade;
    }

    public boolean isGraded() {
        return isGraded;
    }

    public Block create(RegistryRock rock) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().mapColor(rock.color()).sound(SoundType.STONE).strength(rock.category().hardness(6.5F), 10.0F).requiresCorrectToolForDrops();
        return new Block(properties);
    }
}
