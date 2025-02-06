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
 * Provides access to the Separator recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.Separator")
@ModOnly(MOD_ID)
public class Separator {
    /**
     * Adds a Separator recipe.
     *
     * @param input         recipe input
     * @param output        Item output Slot 1-3
     * @param outChances    Item output chances
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack[] output, IIngredient input, int[] outChances, int durationTicks, int euPerTick) {
        if (output.length < 1) {
            MineTweakerAPI.logError("Seperator must have at least 1 output");
        } else if (output.length != outChances.length) {
            MineTweakerAPI.logError("Number of Outputs does not equal number of Chances");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe>("Adding Separator recipe for " + input, input, output[0], ArrayHelper.itemOrNull(output, 1), ArrayHelper.itemOrNull(output, 2), outChances, durationTicks, euPerTick) {
                @Override
                protected GT_Recipe applySingleRecipe(ArgIterator i) {
                    return RA.addElectromagneticSeparatorRecipeRemovable(i.nextItem(), i.nextItem(), i.nextItem(), i.nextItem(), i.nextIntArr(), i.nextInt(), i.nextInt());
                }

                @Override
                protected void undoSingleRecipe(GT_Recipe recipe) {
                    RA.removeElectromagneticSeparatorRecipe(recipe);
                }
            });
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray , ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray, int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sElectroMagneticSeparatorRecipes, "Adding Separator recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }
}
