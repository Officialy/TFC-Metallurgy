package tfc_metallurgy.common.fluids;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import net.dries007.tfc.common.fluids.FluidHolder;
import net.dries007.tfc.common.fluids.FluidId;
import net.dries007.tfc.common.fluids.MoltenFluid;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import tfc_metallurgy.TFCMetallurgy;
import tfc_metallurgy.common.blocks.MetallurgyBlocks;
import tfc_metallurgy.common.items.MetallurgyItems;
import tfc_metallurgy.util.MetallurgyMetal;

public class MetallurgyFluids
{
    public static final int ALPHA_MASK = 0xFF000000;

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, TFCMetallurgy.mod_id);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, TFCMetallurgy.mod_id);

    public static final Map<MetallurgyMetal, FluidHolder<BaseFlowingFluid>> METALS = Helpers.mapOf(MetallurgyMetal.class, metal -> register(
        "metal/" + metal.getSerializedName(),
        properties -> properties
            .block(MetallurgyBlocks.METAL_FLUIDS.get(metal))
            .bucket(MetallurgyItems.METAL_FLUID_BUCKETS.get(FluidId.asType(metal)))
            .explosionResistance(100.0F),
        lavaLike()
            .descriptionId("fluid.tfc.metal." + metal.getSerializedName())
            .rarity(metal.rarity()),
        MoltenFluid.Source::new,
        MoltenFluid.Flowing::new
    ));

    private static FluidType.Properties lavaLike()
    {
        return FluidType.Properties.create()
            .adjacentPathType(PathType.LAVA)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
            .lightLevel(15)
            .density(3000)
            .viscosity(6000)
            .temperature(1300)
            .canConvertToSource(false)
            .canDrown(false)
            .canExtinguish(false)
            .canHydrate(false)
            .canPushEntity(false)
            .canSwim(false)
            .supportsBoating(false);
    }

    private static <F extends FlowingFluid> FluidHolder<F> register(String name, Consumer<BaseFlowingFluid.Properties> builder, FluidType.Properties typeProperties, Function<BaseFlowingFluid.Properties, F> sourceFactory, Function<BaseFlowingFluid.Properties, F> flowingFactory)
    {
        final int index = name.lastIndexOf('/');
        final String flowingName = index == -1 ? "flowing_" + name : name.substring(0, index) + "/flowing_" + name.substring(index + 1);
        return RegistrationHelpers.registerFluid(FLUID_TYPES, FLUIDS, name, name, flowingName, builder, () -> new FluidType(typeProperties), sourceFactory, flowingFactory);
    }
}
