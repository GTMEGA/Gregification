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
 * Provides access to the Precision Laser recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.PrecisionLaser")
@ModOnly(MOD_ID)
public class PrecisionLaser {
    /**
     * Adds a Laser Engraver recipe.
     *
     * @param output        recipe output
     * @param input1        Item input
     * @param input2        Lens input
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     * @param cleanroom     the cleanroom requirement
     */
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input1, IIngredient input2, int durationTicks, int euPerTick, boolean cleanroom) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Precision Laser recipe for " + output, input1, input2, output, durationTicks, euPerTick, cleanroom) {
            @Override
            protected void applySingleRecipe(ArgIterator i) {
                RA.addLaserEngraverRecipe(i.nextItem(), i.nextItem(), i.nextItem(), i.nextInt(), i.nextInt(), i.nextBool());
            }
        });
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
                    GT_Recipe recipe = new GT_Recipe(false,i.nextItemArr(),i.nextItemArr(),null,i.nextIntArr(),i.nextFluidArr(),i.nextFluidArr(),i.nextInt(),i.nextInt(),0);
                    GT_Recipe.GT_Recipe_Map.sLaserEngraverRecipes.addRecipe(recipe);
                }
            });
        }
    }
    
    @ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input1, IIngredient input2, int durationTicks, int euPerTick) {
        addRecipe(output, input1, input2, durationTicks, euPerTick, false);
	}
}
