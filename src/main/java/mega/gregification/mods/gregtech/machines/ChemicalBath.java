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
 * Provides access to the Chemical Bath recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.ChemicalBath")
@ModOnly(MOD_ID)
public class ChemicalBath {
    /**
     * Adds a Chemical Bath recipe.
     *
     * @param output        outputs 1-3
     * @param input         primary input
     * @param fluidInput    primary fluidInput
     * @param chances       chance of 3 outputs
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack[] output, IIngredient input, ILiquidStack fluidInput, int[] chances, int durationTicks, int euPerTick) {
        if (output.length == 0) {
            MineTweakerAPI.logError("chemical bath requires at least 1 output");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Chemical Bath recipe for " + input, input, fluidInput, output[0],
                                                             ArrayHelper.itemOrNull(output, 1), ArrayHelper.itemOrNull(output, 2), chances, durationTicks, euPerTick) {
                @Override
                protected void applySingleRecipe(ArgIterator i) {
                    RA.addChemicalBathRecipe(i.nextItem(), i.nextFluid(), i.nextItem(), i.nextItem(), i.nextItem(), i.nextIntArr(), i.nextInt(), i.nextInt());
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
                    GT_Recipe.GT_Recipe_Map.sChemicalBathRecipes.addRecipe(recipe);
                }
            });
        }
    }
}