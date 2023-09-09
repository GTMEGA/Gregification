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
 * Provides access to the Plasma Arc Furnace recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.PlasmaArcFurnace")
@ModOnly(MOD_ID)
public class PlasmaArcFurnace {
    /**
     * Adds an Arc Furnace recipe.
     *
     * @param outputs       1-4 recipe output
     * @param fluidOutput   primary fluidOutput
     * @param input         primary input
     * @param fluidInput    primary fluidInput
     * @param outChances    chances of 1-4 output
     * @param durationTicks assembling duration, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack[] outputs, ILiquidStack fluidOutput, IIngredient input, ILiquidStack fluidInput, int[] outChances, int durationTicks, int euPerTick) {
        if (outputs.length < 1) {
            MineTweakerAPI.logError("Plasma Arc Furnace must have at least 1 output");
        } else if (outputs.length != outChances.length) {
            MineTweakerAPI.logError("Number of Outputs does not equal number of Chances");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Plasma Arc Furnace recipe for " + input, input, fluidInput, outputs, fluidOutput, outChances, durationTicks, euPerTick) {
                @Override
                protected void applySingleRecipe(ArgIterator i) {
                    RA.addPlasmaArcFurnaceRecipe(i.nextItem(), i.nextFluid(), i.nextItemArr(), i.nextFluid(), i.nextIntArr(), i.nextInt(), i.nextInt());
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
                    GT_Recipe.GT_Recipe_Map.sPlasmaArcFurnaceRecipes.addRecipe(recipe);
                }
            });
        }
    }

    /**
     * Adds an Arc Furnace recipe.
     *
     * @param outputs       1-4 recipe output
     * @param fluidOutput   primary fluidOutput
     * @param input         primary input
     * @param outChances    chances of 1-4 output
     * @param durationTicks assembling duration, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack[] outputs, ILiquidStack fluidOutput, IIngredient input, int[] outChances, int durationTicks, int euPerTick) {
        addRecipe(outputs, fluidOutput, input, null, outChances, durationTicks, euPerTick);
    }
}