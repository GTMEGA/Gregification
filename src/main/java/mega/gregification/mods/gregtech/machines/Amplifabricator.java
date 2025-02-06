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
 * Provides access to the Amplifabricator recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.Amplifabricator")
@ModOnly(MOD_ID)
public class Amplifabricator {
    /**
     * Adds a Amplifabricator recipe.
     *
     * @param input    primary Input
     * @param duration reaction time, in ticks
     */
    @ZenMethod
    public static void addRecipe(IIngredient input, int duration, int amount) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe>("Adding Amplifabricator recipe for " + input, input, duration, amount) {
            @Override
            protected GT_Recipe applySingleRecipe(ArgIterator i) {
                return RA.addAmplifierRemovable(i.nextItem(), i.nextInt(), i.nextInt());
            }

            @Override
            protected void undoSingleRecipe(GT_Recipe recipe) {
                RA.removeAmplifier(recipe);
            }
        });
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray , ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray, int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sAmplifiers, "Adding Amplifabricator recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }
}