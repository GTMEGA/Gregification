package mega.gregification.mods.gregtech.machines;

import gregtech.api.util.GT_Recipe;
import mega.gregification.mods.AddMultipleRecipeAction;
import mega.gregification.util.ArrayHelper;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

import static gregtech.api.enums.GT_Values.MOD_ID;
import static gregtech.api.enums.GT_Values.RA;

/**
 * Provides access to the Blast Furnace recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.BlastFurnace")
@ModOnly(MOD_ID)
public class BlastFurnace {
    /**
     * Adds a Blast Furnace recipe.
     *
     * @param output        recipe output 1+2
     * @param fluidInput    primary fluidInput
     * @param input         recipes input  1+2
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     * @param heat          heat in Kelvin
     */
    @ZenMethod
    public static void addRecipe(IItemStack[] output, ILiquidStack fluidOutput, IIngredient[] input, ILiquidStack fluidInput, int durationTicks, int euPerTick, int heat) {
        if (output.length == 0 || input.length == 0) {
            MineTweakerAPI.logError("Blast furnace recipe requires at least 1 input and 1 output");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Blast furnace recipe for " + output[0], input[0], ArrayHelper.itemOrNull(input, 1), fluidInput, fluidOutput, output[0], ArrayHelper.itemOrNull(output, 1), durationTicks, euPerTick, heat) {
                @Override
                protected void applySingleRecipe(ArgIterator i) {
                    RA.addBlastRecipe(i.nextItem(), i.nextItem(), i.nextFluid(), i.nextFluid(), i.nextItem(), i.nextItem(), i.nextInt(), i.nextInt(), i.nextInt());
                }
            });
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray ,ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray,int[] chances, int durationTicks, int euPerTick, int heat) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Blast furnace recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                    inputArray, outputArray,chances,inputFluidArray,outputFluidArray, durationTicks, euPerTick, heat) {
                @Override
                protected void applySingleRecipe(ArgIterator i) {
                    GT_Recipe recipe = new GT_Recipe(false,i.nextItemArr(),i.nextItemArr(),null,i.nextIntArr(),i.nextFluidArr(),i.nextFluidArr(),i.nextInt(),i.nextInt(),i.nextInt());
                }
            });
        }
    }



    @ZenMethod
    public static void addRecipe(IItemStack[] output, ILiquidStack fluidInput, IIngredient[] input, int durationTicks, int euPerTick, int heat) {
        addRecipe(output, null, input, fluidInput, durationTicks, euPerTick, heat);
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] output, IIngredient[] input, int durationTicks, int euPerTick, int heat) {
        addRecipe(output, null, input, durationTicks, euPerTick, heat);
    }
}