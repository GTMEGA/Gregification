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
 * Provides access to the PyrolyseOven recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.PyrolyseOven")
@ModOnly(MOD_ID)
public class PyrolyseOven {
    /**
     * Adds a Pyrolyse Oven recipe.
     *
     * @param output        recipe output
     * @param fluidOutput   recipe Fluid output
     * @param circuit       circuit preset nr.
     * @param input         recipe input
     * @param fluidInput    recipe Fluid input
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack output, ILiquidStack fluidOutput, int circuit, IIngredient input, ILiquidStack fluidInput, int durationTicks, int euPerTick) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Pyrolyse Oven recipe for " + output, input, fluidInput, circuit, output, fluidOutput, durationTicks, euPerTick) {
            @Override
            protected void applySingleRecipe(ArgIterator i) {
                RA.addPyrolyseRecipe(i.nextItem(), i.nextFluid(), i.nextInt(), i.nextItem(), i.nextFluid(), i.nextInt(), i.nextInt());
            }
        });
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
                    GT_Recipe.GT_Recipe_Map.sPyrolyseRecipes.addRecipe(recipe);
                }
            });
        }
    }
}

