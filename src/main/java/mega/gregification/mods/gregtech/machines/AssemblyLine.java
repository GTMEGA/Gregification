package mega.gregification.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.MOD_ID;
import static gregtech.api.enums.GT_Values.RA;

import gregtech.api.util.GT_Recipe;
import mega.gregification.mods.AddMultipleRecipeAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

/**
 * Provides access to the Assembly Line recipes.
 *
 * @author Draknyte1 / Alkalus
 */
@ZenClass("mods.gregtech.AssemblyLine")
@ModOnly(MOD_ID)
public class AssemblyLine {
    /**
     * Adds an Assembly Line recipe.
     *
     * @param aInputs   must be != null, 4-16 inputs
     * @param aFluidInputs 0-4 fluids
     * @param aOutput  must be != null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     */
    @ZenMethod
    public static void addRecipe(IItemStack aResearchItem, int aResearchTime, IItemStack[] aInputs, ILiquidStack[] aFluidInputs, IItemStack aOutput, int aDuration, int aEUt) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction<Pair<GT_Recipe[], GT_Recipe.GT_Recipe_AssemblyLine>>("Adding Assembly Line recipe for " + aOutput, aResearchItem, aResearchTime, aInputs, aFluidInputs, aOutput, aDuration, aEUt) {
            @Override
            protected Pair<GT_Recipe[], GT_Recipe.GT_Recipe_AssemblyLine> applySingleRecipe(ArgIterator i) {
                return RA.addAssemblylineRecipeRemovable(i.nextItem(), i.nextInt(), i.nextItemArr(), i.nextFluidArr(), i.nextItem(), i.nextInt(), i.nextInt());
            }

            @Override
            protected void undoSingleRecipe(Pair<GT_Recipe[], GT_Recipe.GT_Recipe_AssemblyLine> recipe) {
                RA.removeAssemblylineRecipe(recipe);
            }
        });
    }
}

