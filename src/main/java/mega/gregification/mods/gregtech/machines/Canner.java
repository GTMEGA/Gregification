package mega.gregification.mods.gregtech.machines;

import gregtech.api.util.GT_Recipe;
import mega.gregification.mods.AddGTDirectRecipeAction;
import mega.gregification.mods.AddMultipleRecipeAction;
import mega.gregification.util.ArrayHelper;
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
 * Provider access to the Canner recipes.
 *
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.Canner")
@ModOnly(MOD_ID)
public class Canner {
    /**
     * Adds a canner recipe with a single output.
     *
     * @param output        crafting output
     * @param input1        primary input
     * @param input2        secondary input (optional
     * @param durationTicks crafting duration, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input1, IIngredient input2, int durationTicks, int euPerTick) {
        addRecipe(new IItemStack[]{output}, input1, input2, durationTicks, euPerTick);
    }

    /**
     * Adds a canner recipe with multiple outputs.
     *
     * @param output        array with 1 or 2 outputs
     * @param input1        primary inputs
     * @param input2        secondary inputs
     * @param durationTicks crafting duration, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack[] output, IIngredient input1, IIngredient input2, int durationTicks, int euPerTick) {
        if (output.length == 0) {
            MineTweakerAPI.logError("canner requires at least 1 output");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe>("Adding Canner recipe for " + output[0], input1, input2, output[0], ArrayHelper.itemOrNull(output, 1), durationTicks, euPerTick) {
                @Override
                protected GT_Recipe applySingleRecipe(ArgIterator i) {
                    return RA.addCannerRecipeRemovable(i.nextItem(), i.nextItem(), i.nextItem(), i.nextItem(), i.nextInt(), i.nextInt());
                }

                @Override
                protected void undoSingleRecipe(GT_Recipe recipe) {
                    RA.removeCannerRecipe(recipe);
                }
            });
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray , ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray, int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sCannerRecipes, "Adding Canner recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }
}
