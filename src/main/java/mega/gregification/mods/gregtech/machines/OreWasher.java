package mega.gregification.mods.gregtech.machines;

import gregtech.api.util.GT_Recipe;
import mega.gregification.mods.AddGTDirectRecipeAction;
import mega.gregification.mods.AddMultipleRecipeAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

import static gregtech.api.enums.GT_Values.MOD_ID;
import static gregtech.api.enums.GT_Values.RA;

@ZenClass("mods.gregtech.OreWasher")
@ModOnly(MOD_ID)
public class OreWasher {
    @ZenMethod
    public static void addRecipe(IItemStack output1, IItemStack output2, IItemStack output3, IIngredient input, FluidStack fluidInput, int durationTicks, int euPerTick) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe>("Adding Ore Washer recipe for " + output1, input, output1, output2, output3, fluidInput, durationTicks, euPerTick) {
            @Override
            protected GT_Recipe applySingleRecipe(ArgIterator i) {
                return RA.addOreWasherRecipeRemovable(i.nextItem(), i.nextItem(), i.nextItem(), i.nextItem(), i.nextFluid(), i.nextInt(), i.nextInt());
            }

            @Override
            protected void undoSingleRecipe(GT_Recipe recipe) {
                RA.removeOreWasherRecipe(recipe);
            }
        });
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray , ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray, int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sOreWasherRecipes, "Adding Ore Washer recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack output1, IItemStack output2, IItemStack output3, IIngredient input, int durationTicks, int euPerTick) {
        addRecipe(output1, output2, output3, input, FluidRegistry.getFluidStack("water", 1000), durationTicks, euPerTick);
    }
}
