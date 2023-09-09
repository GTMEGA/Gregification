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
 * Provides access to the Vacuum Freezer recipes.
 *
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.VacuumFreezer")
@ModOnly(MOD_ID)
public class VacuumFreezer {
    /**
     * Adds a vacuum freezer recipe.
     *
     * @param output        recipe output
     * @param input         recipe input
     * @param durationTicks freezing duration, in ticks
     */
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, int durationTicks) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding vacuum freezer recipe for " + output, input, output, durationTicks) {
            @Override
            protected void applySingleRecipe(ArgIterator i) {
                RA.addVacuumFreezerRecipe(i.nextItem(), i.nextItem(), i.nextInt());
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
                    GT_Recipe.GT_Recipe_Map.sVacuumRecipes.addRecipe(recipe);
                }
            });
        }
    }
}
