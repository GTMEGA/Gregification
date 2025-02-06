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

@ZenClass("mods.gregtech.CircuitAssembler")
@ModOnly(MOD_ID)
public class CircuitAssembler {
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient[] inputs, ILiquidStack fluidInput, int durationTicks, int euPerTick) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe[]>("Adding Circuit Assembler recipe for " + output, inputs, fluidInput, output, durationTicks, euPerTick) {
            @Override
            protected GT_Recipe[] applySingleRecipe(ArgIterator i) {
                return RA.addCircuitAssemblerRecipeRemovable(i.nextItemArr(), i.nextFluid(), i.nextItem(), i.nextInt(), i.nextInt());
            }

            @Override
            protected void undoSingleRecipe(GT_Recipe[] recipe) {
                RA.removeCircuitAssemblerRecipe(recipe);
            }
        });
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray ,ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray,int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sCircuitAssemblerRecipes, "Adding Circuit Assembler recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient[] inputs, int durationTicks, int euPerTick) {
        addRecipe(output, inputs, null, durationTicks, euPerTick);
    }
}