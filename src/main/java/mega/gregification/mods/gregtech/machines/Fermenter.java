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
 * Provides access to the Fermenter recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.Fermenter")
@ModOnly(MOD_ID)
public class Fermenter {
    /**
     * Adds a Fermenter recipe.
     *
     * @param fluidOutput primary fluidOutput
     * @param fluidInput  primary fluidInput
     * @param duration    reaction time, in ticks
     * @param hidden      hidden
     */
    @ZenMethod
    public static void addRecipe(ILiquidStack fluidOutput, ILiquidStack fluidInput, int duration, boolean hidden) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe>("Adding Fermenter recipe for " + fluidOutput, fluidInput, fluidOutput, duration, hidden) {
            @Override
            protected GT_Recipe applySingleRecipe(ArgIterator i) {
                return RA.addFermentingRecipeRemovable(i.nextFluid(), i.nextFluid(), i.nextInt(), i.nextBool());
            }

            @Override
            protected void undoSingleRecipe(GT_Recipe recipe) {
                RA.removeFermentingRecipe(recipe);
            }
        });
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray , ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray, int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sFermentingRecipes, "Adding Fermenter recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }
}