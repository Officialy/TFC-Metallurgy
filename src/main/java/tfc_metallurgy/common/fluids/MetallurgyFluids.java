package tfc_metallurgy.common.fluids;

import net.dries007.tfc.common.fluids.*;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tfc_metallurgy.TFCMetallurgy;
import tfc_metallurgy.common.blocks.MetallurgyBlocks;
import tfc_metallurgy.common.items.MetallurgyItems;
import tfc_metallurgy.util.MetallurgyMetal;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class MetallurgyFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, TFCMetallurgy.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, TFCMetallurgy.MOD_ID);

    private static FluidType.Properties lavaLike() {
        return FluidType.Properties.create().adjacentPathType(BlockPathTypes.LAVA)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .lightLevel(15).density(3000).viscosity(6000).temperature(1300)
                .canConvertToSource(false).canDrown(false).canExtinguish(false)
                .canHydrate(false).canPushEntity(false).canSwim(false).supportsBoating(false);
    }

    public static final Map<MetallurgyMetal, FluidRegistryObject<ForgeFlowingFluid>> METALS = Helpers.mapOfKeys(MetallurgyMetal.class, metal -> register(
            "metal/" + metal.getSerializedName(),
            properties -> properties.block(MetallurgyBlocks.METAL_FLUIDS.get(metal)).bucket(MetallurgyItems.METAL_FLUID_BUCKETS.get(FluidId.asType(metal))).explosionResistance(100.0F),
            lavaLike().descriptionId("fluid.tfc.metal." + metal.getSerializedName()).rarity(metal.getRarity()),
            new FluidTypeClientProperties(-16777216 | metal.getColor(), new ResourceLocation("tfc_metallurgy:block/metal/fluid/" + metal.getSerializedName() + "_still"),
                    new ResourceLocation("tfc_metallurgy:block/metal/fluid/" + metal.getSerializedName() + "_flow"), null, null),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new));

    private static <F extends FlowingFluid> FluidRegistryObject<F> register(String name, Consumer<ForgeFlowingFluid.Properties> builder, FluidType.Properties typeProperties, FluidTypeClientProperties clientProperties, Function<ForgeFlowingFluid.Properties, F> sourceFactory, Function<ForgeFlowingFluid.Properties, F> flowingFactory) {
        int index = name.lastIndexOf(47);
        String flowingName = index == -1 ? "flowing_" + name : name.substring(0, index) + "/flowing_" + name.substring(index + 1);
        return RegistrationHelpers.registerFluid(FLUID_TYPES, FLUIDS, name, name, flowingName, builder, () -> new ExtendedFluidType(typeProperties, clientProperties), sourceFactory, flowingFactory);
    }
}
