package mega.gregification.mods.gregtech.machines;

import mega.gregification.mods.AddMultipleRecipeAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import static gregtech.api.enums.GT_Values.MOD_ID;
import static gregtech.api.enums.GT_Values.RA;

/**
 * Provides access to the Fluid Solidifier recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.FluidSolidifier")
@ModOnly(MOD_ID)
public class FluidSolidifier {
    /**
     * Adds a Fluid Solidifier recipe.
     *
     * @param output        output Slot
     * @param mold          mold Slot
     * @param fluidInput    fluidInput
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack output, IItemStack mold, ILiquidStack fluidInput, int durationTicks, int euPerTick) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Fluid Solidifier recipe for " + output, mold, fluidInput, output, durationTicks, euPerTick) {
            @Override
            protected void applySingleRecipe(ArgIterator i) {
                RA.addFluidSolidifierRecipe(i.nextItem(), i.nextFluid(), i.nextItem(), i.nextInt(), i.nextInt());
            }
        });
    }
}