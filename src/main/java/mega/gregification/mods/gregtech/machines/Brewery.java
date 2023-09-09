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

/**
 * Provides access to the Brewing Machine recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.Brewery")
@ModOnly(MOD_ID)
public class Brewery {
    /**
     * Adds a Brewing Machine recipe.
     *
     * @param output     primary fluid output
     * @param ingredient primary ingredient
     * @param input      primary fluid ingredient
     * @param hidden     hidden true or false
     */
    @ZenMethod
    public static void addRecipe(ILiquidStack output, IIngredient ingredient, ILiquidStack input, boolean hidden) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Brewery recipe for " + output, ingredient, input, output, hidden) {
            @Override
            protected void applySingleRecipe(ArgIterator i) {
                RA.addBrewingRecipe(i.nextItem(), i.nextFluid().getFluid(), i.nextFluid().getFluid(), i.nextBool());
            }
        });
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
                    GT_Recipe.GT_Recipe_Map.sBrewingRecipes.addRecipe(recipe);
                }
            });
        }
    }
}
