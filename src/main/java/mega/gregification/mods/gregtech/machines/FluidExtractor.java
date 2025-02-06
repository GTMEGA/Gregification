package mega.gregification.mods.gregtech.machines;

import gregtech.api.util.GT_Recipe;
import mega.gregification.mods.AddGTDirectRecipeAction;
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
 * Provides access to the Fluid Extractor recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.FluidExtractor")
@ModOnly(MOD_ID)
public class FluidExtractor {
    /**
     * Adds a Fluid Extractor recipe.
     *
     * @param output        output Slot
     * @param input         input Slot
     * @param fluidOutput   fluidOutput Slot
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     * @param chance        chance output slot
     */
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, ILiquidStack fluidOutput, int durationTicks, int euPerTick, int chance) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe>("Adding Fluid Extractor recipe for " + input, input, output, fluidOutput, durationTicks, euPerTick, chance) {
            @Override
            protected GT_Recipe applySingleRecipe(ArgIterator i) {
                return RA.addFluidExtractionRecipeRemovable(i.nextItem(), i.nextItem(), i.nextFluid(), i.nextInt(), i.nextInt(), i.nextInt());
            }

            @Override
            protected void undoSingleRecipe(GT_Recipe recipe) {
                RA.removeFluidExtractionRecipe(recipe);
            }
        });
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray ,ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray,int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sFluidExtractionRecipes, "Adding Fluid Extractor recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }
}