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
 * Provides access to the Fusion Reactor recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.FusionReactor")
@ModOnly(MOD_ID)
public class FusionReactor {
    /**
     * Adds a Mixer recipe.
     *
     * @param fluidOutput   primary fluid Output
     * @param fluidInput1   primary fluid Input
     * @param fluidInput2   secondary fluid Input
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     * @param startEU       Starting at ... EU
     */
    @ZenMethod
    public static void addRecipe(ILiquidStack fluidOutput, ILiquidStack fluidInput1, ILiquidStack fluidInput2, int durationTicks, int euPerTick, int startEU) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Fusion Reactor recipe for " + fluidOutput, fluidInput1, fluidInput2, fluidOutput, durationTicks, euPerTick, startEU) {
            @Override
            protected void applySingleRecipe(ArgIterator i) {
                RA.addFusionReactorRecipe(i.nextFluid(), i.nextFluid(), i.nextFluid(), i.nextInt(), i.nextInt(), i.nextInt());
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
                    GT_Recipe.GT_Recipe_Map.sFusionRecipes.addRecipe(recipe);
                }
            });
        }
    }
}
