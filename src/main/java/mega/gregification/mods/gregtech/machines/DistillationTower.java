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
 * Provides access to the Distillation Tower recipes.
 *
 * @author DreamMasterXXL
 * @author Blood Asp
 */
@ZenClass("mods.gregtech.DistillationTower")
@ModOnly(MOD_ID)
public class DistillationTower {
    /**
     * Adds an Distillation Tower recipe.
     *
     * @param fluidInput    Fluid Input
     * @param fluidOutput   Up to 6 Fluid Outputs
     * @param itemOutput    Item output Slot
     * @param durationTicks duration, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(ILiquidStack[] fluidOutput, IItemStack itemOutput, ILiquidStack fluidInput, int durationTicks, int euPerTick) {
        if (fluidOutput.length < 1) {
            MineTweakerAPI.logError("Distillation Tower must have at least 1 Fluid output");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Distillation Tower recipe for " + fluidInput.getDisplayName(), fluidInput, fluidOutput, itemOutput, durationTicks, euPerTick) {
                @Override
                protected void applySingleRecipe(ArgIterator i) {
                    RA.addDistillationTowerRecipe(i.nextFluid(), i.nextFluidArr(), i.nextItem(), i.nextInt(), i.nextInt());
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
                    GT_Recipe.GT_Recipe_Map.sDistillationRecipes.addRecipe(false,i.nextItemArr(),i.nextItemArr(),null,i.nextIntArr(),i.nextFluidArr(),i.nextFluidArr(),
                            i.nextInt(),i.nextInt(),0);
                }
            });
        }
    }

    @ZenMethod
    public static void addUniversalRecipe(ILiquidStack[] fluidOutput, IItemStack itemOutput, ILiquidStack fluidInput, int durationTicks, int euPerTick) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding universal distillation recipe for " + fluidInput.getDisplayName(), fluidInput, fluidOutput, itemOutput, durationTicks, euPerTick) {
            @Override
            protected void applySingleRecipe(ArgIterator i) {
                RA.addUniversalDistillationRecipe(i.nextFluid(), i.nextFluidArr(), i.nextItem(), i.nextInt(), i.nextInt());
            }
        });
    }
}