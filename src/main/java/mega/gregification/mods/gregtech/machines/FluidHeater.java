package mega.gregification.mods.gregtech.machines;

import gregtech.api.util.GT_Recipe;
import mega.gregification.mods.AddGTDirectRecipeAction;
import mega.gregification.mods.AddMultipleRecipeAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

import static gregtech.api.enums.GT_Values.MOD_ID;
import static gregtech.api.enums.GT_Values.RA;

/**
 * Provides access to the Fluid Heater recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.FluidHeater")
@ModOnly(MOD_ID)
public class FluidHeater {
    /**
     * Adds a Fluid Heater recipe.
     *
     * @param fluidOutput   Fluid output
     * @param circuit       Circuit
     * @param fluidInput    fluid input
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(ILiquidStack fluidOutput, IItemStack circuit, ILiquidStack fluidInput, int durationTicks, int euPerTick) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe>("Adding Fluid Heater recipe for " + fluidOutput, circuit, fluidInput, fluidOutput, durationTicks, euPerTick) {
            @Override
            protected GT_Recipe applySingleRecipe(ArgIterator i) {
                return RA.addFluidHeaterRecipeRemovable(i.nextItem(), i.nextFluid(), i.nextFluid(), i.nextInt(), i.nextInt());
            }

            @Override
            protected void undoSingleRecipe(GT_Recipe recipe) {
                RA.removeFluidHeaterRecipe(recipe);
            }
        });
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray , ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray, int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sFluidHeaterRecipes, "Adding Fluid Heater recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }
}