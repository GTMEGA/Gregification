package mega.gregification.mods.gregtech.machines;

import mega.gregification.mods.AddMultipleRecipeAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import static gregtech.api.enums.GT_Values.MOD_ID;
import static gregtech.api.enums.GT_Values.RA;

/**
 * Provides access to the Wiremill recipes.
 *
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.Wiremill")
@ModOnly(MOD_ID)
public class Wiremill {
    /**
     * Adds a Wiremill recipe.
     *
     * @param output        recipe output
     * @param input         recipe input
     * @param durationTicks processing time
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, int durationTicks, int euPerTick) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding wiremill recipe for " + output, input, output, durationTicks, euPerTick) {
            @Override
            protected void applySingleRecipe(ArgIterator i) {
                RA.addWiremillRecipe(i.nextItem(), i.nextItem(), i.nextInt(), i.nextInt());
            }
        });
    }
}
