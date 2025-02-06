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
 * Provides access to the Electrolyzer recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.Electrolyzer")
@ModOnly(MOD_ID)
public class Electrolyzer {
    /**
     * Adds a Electrolyzer recipe.
     *
     * @param outputs       output 1-6
     * @param fluidOutput   primary fluid output
     * @param input         primary input
     * @param cells         Cell input
     * @param fluidInput    primary fluid input
     * @param chances       chance 1-6
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack[] outputs, ILiquidStack fluidOutput, IIngredient input, IIngredient cells, ILiquidStack fluidInput, int[] chances, int durationTicks, int euPerTick) {
        if (outputs.length < 1) {
            MineTweakerAPI.logError("Electrolyzer must have at least 1 output");
        } else if (outputs.length != chances.length) {
            MineTweakerAPI.logError("Number of Outputs does not equal number of Chances");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe>("Adding Electrolyzer recipe with Liquid support for " + input, input, cells, fluidInput, fluidOutput, outputs[0],
                                                             ArrayHelper.itemOrNull(outputs, 1), ArrayHelper.itemOrNull(outputs, 2), ArrayHelper.itemOrNull(outputs, 3), ArrayHelper.itemOrNull(outputs, 4), ArrayHelper.itemOrNull(outputs, 5), chances, durationTicks, euPerTick) {
                @Override
                protected GT_Recipe applySingleRecipe(ArgIterator i) {
                    return RA.addElectrolyzerRecipeRemovable(i.nextItem(), i.nextItem(), i.nextFluid(), i.nextFluid(), i.nextItem(), i.nextItem(),
                            i.nextItem(), i.nextItem(), i.nextItem(), i.nextItem(), i.nextIntArr(), i.nextInt(), i.nextInt());
                }

                @Override
                protected void undoSingleRecipe(GT_Recipe recipe) {
                    RA.removeElectrolyzerRecipe(recipe);
                }
            });
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray ,ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray,int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sElectrolyzerRecipes, "Adding Electrolyzer recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] outputs, IIngredient input, int cells, int durationTicks, int euPerTick) {
        if (outputs.length == 0) {
            MineTweakerAPI.logError("Electrolyzer recipe requires at least 1 input");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe>("Adding Electrolyzer recipe with input " + input, input, cells, outputs[0],
                                                             ArrayHelper.itemOrNull(outputs, 1), ArrayHelper.itemOrNull(outputs, 2), ArrayHelper.itemOrNull(outputs, 3), ArrayHelper.itemOrNull(outputs, 4), ArrayHelper.itemOrNull(outputs, 5), durationTicks, euPerTick) {
                @Override
                protected GT_Recipe applySingleRecipe(ArgIterator i) {
                    return RA.addElectrolyzerRecipeRemovable(i.nextItem(), i.nextInt(), i.nextItem(), i.nextItem(), i.nextItem(),
                            i.nextItem(), i.nextItem(), i.nextItem(), i.nextInt(), i.nextInt()
                    );
                }

                @Override
                protected void undoSingleRecipe(GT_Recipe recipe) {
                    RA.removeElectrolyzerRecipe(recipe);
                }
            });
        }
    }
}
