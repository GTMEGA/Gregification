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
 * Provides access to the Autoclave recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.Autoclave")
@ModOnly(MOD_ID)
public class Autoclave {
    /**
     * Adds an Autoclave recipe.
     *
     * @param output        primary output
     * @param input         primary input
     * @param fluidInput    primary fluidInput
     * @param chance        chance
     * @param durationTicks assembling duration, in ticks
     * @param euPerTick     eu consumption per tick
     * @param lowGravity    the low gravity requirement
     */
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, ILiquidStack fluidInput, int chance, int durationTicks, int euPerTick, boolean lowGravity) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe>("Adding Autoclave recipe for " + output, input, fluidInput, output, chance, durationTicks, euPerTick, lowGravity) {
            @Override
            protected GT_Recipe applySingleRecipe(ArgIterator i) {
                return RA.addAutoclaveRecipeRemovable(i.nextItem(), i.nextFluid(), i.nextItem(), i.nextInt(), i.nextInt(), i.nextInt(), i.nextBool());
            }

            @Override
            protected void undoSingleRecipe(GT_Recipe recipe) {
                RA.removeAutoclaveRecipe(recipe);
            }
        });
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray , ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray, int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sAutoclaveRecipes, "Adding Autoclave recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, ILiquidStack fluidInput, int chance, int durationTicks, int euPerTick) {
        addRecipe(output, input, fluidInput, chance, durationTicks, euPerTick, false);
    }
}
