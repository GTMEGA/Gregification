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

@ZenClass("mods.gregtech.PrimitiveBlastFurnace")
@ModOnly(MOD_ID)
public class PrimitiveBlastFurnace {
    @ZenMethod
    public static void addRecipe(IItemStack output1, IItemStack output2, IIngredient input1, IIngredient input2, int durationTicks, int coalAmount) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe[]>("Adding Primitive Blast Furnace recipe for " + output1, input1, input2, coalAmount, output1, output2, durationTicks) {
            @Override
            protected GT_Recipe[] applySingleRecipe(ArgIterator i) {
                return RA.addPrimitiveBlastRecipeRemovable(i.nextItem(), i.nextItem(), i.nextInt(), i.nextItem(), i.nextItem(), i.nextInt());
            }

            @Override
            protected void undoSingleRecipe(GT_Recipe[] recipe) {
                RA.removePrimitiveBlastRecipe(recipe);
            }
        });
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray , ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray, int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sPrimitiveBlastRecipes, "Adding Primitive Blast Furnace recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }
}