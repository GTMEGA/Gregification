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
 * Provides access to the OilCracker recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.OilCracker")
@ModOnly(MOD_ID)
public class OilCracker {
    /**
     * Adds a Oil Cracker recipe.
     *
     * @param fluidOutput   recipe Fluid output
     * @param fluidInput1   primary Fluid input
     * @param fluidInput2   secondary Fluid input
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(int circuitConfig, ILiquidStack fluidOutput, ILiquidStack fluidInput1, ILiquidStack fluidInput2, int durationTicks, int euPerTick) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe>("Adding Oil Cracker recipe for " + fluidOutput, circuitConfig, fluidInput1, fluidInput2, fluidOutput, durationTicks, euPerTick) {
            @Override
            protected GT_Recipe applySingleRecipe(ArgIterator i) {
                return RA.addCrackingRecipeRemovable(i.nextInt(), i.nextFluid(), i.nextFluid(), i.nextFluid(), i.nextInt(), i.nextInt());
            }

            @Override
            protected void undoSingleRecipe(GT_Recipe recipe) {
                RA.removeCrackingRecipe(recipe);
            }
        });
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray , ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray, int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sCrackingRecipes, "Adding Oil Cracker recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluidOutput, ILiquidStack fluidInput, int durationTicks, int euPerTick) {
        MineTweakerAPI.logWarning("This method is no longer supported for OilCracker. Use OilCracker.addRecipe()");
    }
}

