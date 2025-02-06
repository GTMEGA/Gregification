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

@ZenClass("mods.gregtech.ThermalCentrifuge")
@ModOnly(MOD_ID)
public class ThermalCentrifuge {
    @ZenMethod
    public static void addRecipe(IItemStack output1, IItemStack output2, IItemStack output3, IIngredient input, int durationTicks, int euPerTick) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe>("Adding Thermal Centrifuge recipe for " + output1, input, output1, output2, output3, durationTicks, euPerTick) {
            @Override
            protected GT_Recipe applySingleRecipe(ArgIterator i) {
                return RA.addThermalCentrifugeRecipeRemovable(i.nextItem(), i.nextItem(), i.nextItem(), i.nextItem(), i.nextInt(), i.nextInt());
            }

            @Override
            protected void undoSingleRecipe(GT_Recipe recipe) {
                RA.removeThermalCentrifugeRecipe(recipe);
            }
        });
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray , ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray, int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sThermalCentrifugeRecipes, "Adding Thermal Centrifuge recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }
}
