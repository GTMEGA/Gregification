package mega.gregification.mods.gregtech.machines;

import gregtech.api.util.GT_Recipe;
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
 * Provides access to the Centrifuge recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.Centrifuge")
@ModOnly(MOD_ID)
public class Centrifuge {
    /**
     * Adds a Centrifuge recipe.
     *
     * @param outputs       array with 1-6 outputs
     * @param fluidOutput   primary fluid output
     * @param input1        primary input
     * @param input2        Cell input
     * @param fluidInput    primary fluid input
     * @param chances       chance 1-6
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack[] outputs, ILiquidStack fluidOutput, IIngredient input1, IIngredient input2, ILiquidStack fluidInput, int[] chances, int durationTicks, int euPerTick) {
        if (outputs.length < 1) {
            MineTweakerAPI.logError("Centrifuge must have at least 1 output");
        } else if (outputs.length != chances.length) {
            MineTweakerAPI.logError("Number of Outputs does not equal number of Chances");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Centrifuge recipe with Fluids for " + input1, input1, input2, fluidOutput, fluidInput, outputs[0],
                                                             ArrayHelper.itemOrNull(outputs, 1), ArrayHelper.itemOrNull(outputs, 2), ArrayHelper.itemOrNull(outputs, 3), ArrayHelper.itemOrNull(outputs, 4), ArrayHelper.itemOrNull(outputs, 5), chances, durationTicks, euPerTick) {
                @Override
                protected void applySingleRecipe(ArgIterator i) {
                    RA.addCentrifugeRecipe(i.nextItem(), i.nextItem(), i.nextFluid(), i.nextFluid(), i.nextItem(), i.nextItem(),
                            i.nextItem(), i.nextItem(), i.nextItem(), i.nextItem(), i.nextIntArr(), i.nextInt(), i.nextInt());
                }
            });
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray , ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray, int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Blast furnace recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                    inputArray, outputArray,chances,inputFluidArray,outputFluidArray, durationTicks, euPerTick) {
                @Override
                protected void applySingleRecipe(ArgIterator i) {
                    GT_Recipe recipe = new GT_Recipe(false,i.nextItemArr(),i.nextItemArr(),null,i.nextIntArr(),i.nextFluidArr(),i.nextFluidArr(),i.nextInt(),i.nextInt(),0);
                    GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes.addRecipe(recipe);
                }
            });
        }
    }

    @ZenMethod
    public static void addRecipeFuelCan(IItemStack[] outputs, IIngredient input, int numCans, int duration) {
        addRecipe(outputs, input, -numCans, duration);
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] outputs, IIngredient input, int cells, int durationTicks) {
        if (outputs.length < 1) {
            MineTweakerAPI.logError("Centrifuge must have at least 1 output");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding centrifuge recipe with input " + input, input, cells, outputs[0],
                                                             ArrayHelper.itemOrNull(outputs, 1), ArrayHelper.itemOrNull(outputs, 2), ArrayHelper.itemOrNull(outputs, 3), ArrayHelper.itemOrNull(outputs, 4), ArrayHelper.itemOrNull(outputs, 5), durationTicks) {
                @Override
                protected void applySingleRecipe(ArgIterator i) {
                    RA.addCentrifugeRecipe(i.nextItem(), i.nextInt(), i.nextItem(), i.nextItem(),
                            i.nextItem(), i.nextItem(), i.nextItem(), i.nextItem(), i.nextInt());
                }
            });
        }
    }
}