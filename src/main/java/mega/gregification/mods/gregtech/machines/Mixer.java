package mega.gregification.mods.gregtech.machines;

import gregtech.api.util.GT_Recipe;
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
import static mega.gregification.util.ArrayHelper.itemOrNull;

/**
 * Provides access to the Mixer recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.Mixer")
@ModOnly(MOD_ID)
public class Mixer {
    /**
     * Adds a Mixer recipe.
     *
     * @param output        recipe output
     * @param fluidOutput   primary fluidInput
     * @param input         input 1-4
     * @param fluidInput    primary fluidInput
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack output, ILiquidStack fluidOutput, IIngredient[] input, ILiquidStack fluidInput, int durationTicks, int euPerTick) {
        if (input.length == 0) {
            MineTweakerAPI.logError("Mixer recipe requires at least 1 input");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Mixer recipe for " + output, input[0], itemOrNull(input, 1),
                    itemOrNull(input, 2), itemOrNull(input, 3), fluidInput, fluidOutput, output, durationTicks, euPerTick) {
                @Override
                protected void applySingleRecipe(ArgIterator i) {
                    RA.addMixerRecipe(i.nextItem(), i.nextItem(), i.nextItem(), i.nextItem(), i.nextFluid(), i.nextFluid(), i.nextItem(), i.nextInt(), i.nextInt());
                }
            });
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray ,ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray,int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Blast furnace recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                    inputArray, outputArray,chances,inputFluidArray,outputFluidArray, durationTicks, euPerTick) {
                @Override
                protected void applySingleRecipe(ArgIterator i) {
                    GT_Recipe recipe = new GT_Recipe(false,i.nextItemArr(),i.nextItemArr(),null,i.nextIntArr(),i.nextFluidArr(),i.nextFluidArr(),i.nextInt(),i.nextInt(),0);
                    GT_Recipe.GT_Recipe_Map.sMixerRecipes.addRecipe(recipe);
                }
            });
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient[] input, int durationTicks, int euPerTick) {
        addRecipe(output, null, input, null, durationTicks, euPerTick);
    }
}
