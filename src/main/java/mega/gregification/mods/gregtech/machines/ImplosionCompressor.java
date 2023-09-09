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
 * Provides access to the Implosion Compressor recipes.
 *
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.ImplosionCompressor")
@ModOnly(MOD_ID)
public class ImplosionCompressor {
    /**
     * Adds an implosion compressor recipe with a single output.
     *
     * @param output recipe output
     * @param input  primary input
     * @param tnt    amount of TNT needed
     */
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, int tnt) {
        addRecipe(new IItemStack[]{output, null}, input, tnt);
    }

    /**
     * Adds an implosion compressor recipe with one or two outputs.
     *
     * @param output array with 1-2 outputs
     * @param input  primary input
     * @param tnt    amount of TNT needed
     */
    @ZenMethod
    public static void addRecipe(IItemStack[] output, IIngredient input, int tnt) {
        if (output.length == 0) {
            MineTweakerAPI.logError("Implosion compressor recipe requires at least 1 output");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Implosion compressor recipe for " + output[0], input, tnt, output[0], ArrayHelper.itemOrNull(output, 1)) {
                @Override
                protected void applySingleRecipe(ArgIterator i) {
                    RA.addImplosionRecipe(i.nextItem(), i.nextInt(), i.nextItem(), i.nextItem());
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
                    GT_Recipe.GT_Recipe_Map.sImplosionRecipes.addRecipe(recipe);
                }
            });
        }
    }
}
