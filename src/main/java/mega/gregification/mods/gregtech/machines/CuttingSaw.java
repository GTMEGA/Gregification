package mega.gregification.mods.gregtech.machines;

import gregtech.api.util.GT_Recipe;
import mega.gregification.mods.AddGTDirectRecipeAction;
import mega.gregification.mods.AddMultipleRecipeAction;
import mega.gregification.util.ArrayHelper;
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
 * Provides access to the Saw recipes.
 *
 * @author DreamMasterXXL
 * @author bculkin2442
 */
@ZenClass("mods.gregtech.CuttingSaw")
@ModOnly(MOD_ID)
public class CuttingSaw {
    /**
     * Adds a Cutting Saw recipe.
     *
     * @param output1       recipe output1
     * @param output2       recipe output2
     * @param input         primary input
     * @param lubricant     primary fluidInput
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack output1, IItemStack output2, IIngredient input, ILiquidStack lubricant, int durationTicks, int euPerTick) {
        if (lubricant == null) {
            MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe[]>("Adding Cutting Saw recipe for " + input, input, output1, output2, durationTicks, euPerTick) {
                @Override
                protected GT_Recipe[] applySingleRecipe(ArgIterator i) {
                    return RA.addCutterRecipeRemovable(i.nextItem(), i.nextItem(), i.nextItem(), i.nextInt(), i.nextInt());
                }

                @Override
                protected void undoSingleRecipe(GT_Recipe[] recipe) {
                    RA.removeCutterRecipe(recipe);
                }
            });
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe[]>("Adding Cutting Saw recipe for " + input, input, lubricant, output1, output2, durationTicks, euPerTick) {
                @Override
                protected GT_Recipe[] applySingleRecipe(ArgIterator i) {
                    return RA.addCutterRecipeRemovable(i.nextItem(), i.nextFluid(), i.nextItem(), i.nextItem(), i.nextInt(), i.nextInt());
                }

                @Override
                protected void undoSingleRecipe(GT_Recipe[] recipe) {
                    RA.removeCutterRecipe(recipe);
                }
            });
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] output, IIngredient input, ILiquidStack lubricant, int durationTicks, int euPerTick) {
        if (output.length == 0) {
            MineTweakerAPI.logError("canner requires at least 1 output");
        } else {
            addRecipe(output[0], ArrayHelper.itemOrNull(output, 1), input, lubricant, durationTicks, euPerTick);
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray ,ILiquidStack[] inputFluidArray , IIngredient[] outputArray, ILiquidStack[] outputFluidArray,int[] chances, int durationTicks, int euPerTick) {
        if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map.sCutterRecipes, "Adding Cutting Saw recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                                                             inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick));
        }
    }
    
    @ZenMethod
    public static void addRecipe(IItemStack output1, IItemStack output2, IIngredient input, IItemStack circuit, int durationTicks, int euPerTick) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction<GT_Recipe[]>("Adding Cutting Saw recipe for " + input, input, circuit, output1, output2, durationTicks, euPerTick) {
            @Override
            protected GT_Recipe[] applySingleRecipe(ArgIterator i) {
                return RA.addCutterRecipeRemovable(i.nextItem(), i.nextItem(), i.nextItem(), i.nextItem(), i.nextInt(), i.nextInt());
            }

            @Override
            protected void undoSingleRecipe(GT_Recipe[] recipe) {
                RA.removeCutterRecipe(recipe);
            }
        });
    }
}
