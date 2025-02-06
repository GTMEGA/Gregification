package mega.gregification.mods.gregtech;

import gregtech.api.util.GT_Recipe;
import lombok.val;
import mega.gregification.mods.AddMultipleRecipeAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import static gregtech.api.enums.GT_Values.MOD_ID;
import static gregtech.api.enums.GT_Values.RA;

/**
 * Provides access to the fuels used by the various generators.
 *
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.Fuels")
@ModOnly(MOD_ID)
public class Fuels {
    /**
     * Adds a Diesel Engine fuel. If the given item does not contain any liquid,
     * it will generate the equivalent of 1000 millibuckets.
     *
     * @param output           output item (optional, can be null)
     * @param input            input item
     * @param euPerMillibucket eu production per millibucket
     */
    @ZenMethod
    public static void addDieselFuel(IItemStack output, IIngredient input, int euPerMillibucket) {
        MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 0));
    }

    /**
     * Adds a Gas Turbine fuel. If the given item does not contain any liquid,
     * it will generate the equivalent of 1000 millibuckets.
     *
     * @param output           output item (optional, can be null)
     * @param input            input item
     * @param euPerMillibucket eu production per millibucket
     */
    @ZenMethod
    public static void addGasTurbineFuel(IItemStack output, IIngredient input, int euPerMillibucket) {
        MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 1));
    }

    /**
     * Adds a Thermal Generator fuel. If the given item does not contain any
     * liquid, it will generate the equivalent of 1000 millibuckets.
     *
     * @param output           output item (optional, can be null)
     * @param input            input item
     * @param euPerMillibucket eu production per millibucket
     */
    @ZenMethod
    public static void addThermalGeneratorFuel(IItemStack output, IIngredient input, int euPerMillibucket) {
        MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 2));
    }

    /**
     * Adds a Dense Fluid Generator fuel. If the given item does not contain any
     * liquid, it will generate the equivalent of 1000 millibuckets.
     *
     * @param output           output item (optional, can be null)
     * @param input            input item
     * @param euPerMillibucket eu production per millibucket
     */
    @ZenMethod
    public static void addDenseFluidFuel(IItemStack output, IIngredient input, int euPerMillibucket) {
        MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 3));
    }

    /**
     * Adds a Plasma Generator fuel. If the given item does not contain any
     * liquid, it will generate the equivalent of 1000 millibuckets.
     *
     * @param output           output item (optional, can be null)
     * @param input            input item
     * @param euPerMillibucket eu production per millibucket
     */
    @ZenMethod
    public static void addPlasmaGeneratorFuel(IItemStack output, IIngredient input, int euPerMillibucket) {
        MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 4));
    }

    /**
     * Adds a Magic Generator fuel. If the given item does not contain any liquid,
     * it will generate the equivalent of 1000 millibuckets.
     *
     * @param output           output item (optional, can be null)
     * @param input            input item
     * @param euPerMillibucket eu production per millibucket
     */
    @ZenMethod
    public static void addMagicGeneratorFuel(IItemStack output, IIngredient input, int euPerMillibucket) {
        MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 5));
    }

    // ######################
    // ### Action classes ###
    // ######################

    private static class AddRecipeAction extends AddMultipleRecipeAction<Pair<GT_Recipe[], Integer>> {
        private static final String[] GENERATORS = {"diesel", "gas turbine", "thermal", "dense fluid", "plasma", "magic"};

        public AddRecipeAction(IItemStack output, IIngredient input, int euPerMillibucket, int type) {
            super("Adding " + GENERATORS[type] + " fuel " + input, input, output, euPerMillibucket, type);
        }

        @Override
        protected Pair<GT_Recipe[], Integer> applySingleRecipe(ArgIterator i) {
            val a = i.nextItem();
            val b = i.nextItem();
            val eu = i.nextInt();
            val type = i.nextInt();
            val fuel = RA.addFuelRemovable(a, b, eu, type);
            if (fuel != null) {
                return Pair.of(fuel, type);
            } else {
                return null;
            }
        }

        @Override
        protected void undoSingleRecipe(Pair<GT_Recipe[], Integer> recipe) {
            RA.removeFuel(recipe.getLeft(), recipe.getRight());
        }
    }
}
