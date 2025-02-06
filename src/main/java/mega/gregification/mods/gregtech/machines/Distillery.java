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
 * Provides access to the Distillery recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.Distillery")
@ModOnly(MOD_ID)
public class Distillery {
    /**
     * Adds a Distillery recipe.
     *
     * @param fluidOutput   Fluid output
     * @param circuit       Circuit
     * @param fluidInput    fluidInput
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     * @param hidden        hidden
     */
    @ZenMethod
    public static void addRecipe(ILiquidStack fluidOutput, IItemStack circuit, ILiquidStack fluidInput, int durationTicks, int euPerTick, boolean hidden) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe>("Adding Distillery recipe for " + fluidOutput, circuit, fluidInput, fluidOutput, durationTicks, euPerTick, hidden) {
            @Override
            protected GT_Recipe applySingleRecipe(ArgIterator i) {
                return RA.addDistilleryRecipeRemovable(i.nextItem(), i.nextFluid(), i.nextFluid(), i.nextInt(), i.nextInt(), i.nextBool());
            }

            @Override
            protected void undoSingleRecipe(GT_Recipe recipe) {
                RA.removeDistilleryRecipe(recipe);
            }
        });
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray , ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray, int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sDistilleryRecipes, "Adding Distillery recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }
}