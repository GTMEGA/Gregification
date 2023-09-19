package mega.gregification.mods.gregtech.machines;

import gregtech.api.util.GT_Recipe;
import lombok.val;
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
 * Provides access to the Chemical Reactor recipes.
 *
 * @author DreamMasterXXL
 */
@ZenClass("mods.gregtech.ChemicalReactor")
@ModOnly(MOD_ID)
public class ChemicalReactor {
    /**
     * Adds a Chemical Reactor recipe.
     *
     * @param output1       primary output
     * @param output2       secondary output
     * @param fluidOutput   primary fluidInput
     * @param input1        primary input
     * @param input2        secondary input
     * @param fluidInput    primary fluidInput
     * @param durationTicks reaction time, in ticks
     * @param euPerTick     eu consumption per tick
     */
    @ZenMethod
    public static void addRecipe(IItemStack output1, IItemStack output2, ILiquidStack fluidOutput, IIngredient input1, IIngredient input2, ILiquidStack fluidInput, int durationTicks, int euPerTick) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Chemical Reactor recipe for " + output1, input1, input2, fluidInput, fluidOutput, output1, output2, durationTicks, euPerTick) {
            @Override
            protected void applySingleRecipe(ArgIterator i) {
                RA.addChemicalRecipe(i.nextItem(), i.nextItem(), i.nextFluid(), i.nextFluid(), i.nextItem(), i.nextItem(), i.nextInt(), i.nextInt());
            }
        });
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] inputArray, ILiquidStack[] inputFluidArray, IIngredient[] outputArray, ILiquidStack[] outputFluidArray, int[] chances, int durationTicks,  int euPerTick,boolean addToSmall, boolean addToLarge) {

        if (!addToLarge && !addToSmall) {
            MineTweakerAPI.logError("Recipe needs to add to at least 1 chemical reactor type");
        } else if ((inputArray.length == 0 && inputFluidArray.length == 0) || (outputArray.length == 0 && outputFluidArray.length == 0)) {
            MineTweakerAPI.logError("Recipe needs at least 1 input and output");
        } else {
            MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding Blast furnace recipe for " + Arrays.toString(outputArray) + " : " + Arrays.toString(outputFluidArray),
                    inputArray, outputArray, chances, inputFluidArray, outputFluidArray, durationTicks, euPerTick,addToSmall,addToLarge ) {
                @Override
                protected void applySingleRecipe(ArgIterator i) {
                    val inputArray = i.nextItemArr();
                    val outArray = i.nextItemArr();
                    val chances = i.nextIntArr();
                    val flInArray = i.nextFluidArr();
                    val flOutArray =  i.nextFluidArr();
                    val ticks = i.nextInt();
                    val power = i.nextInt();

                    if (i.nextBool()) {
                        GT_Recipe recipe = new GT_Recipe(false, inputArray, outArray, null,
                                chances, flInArray, flOutArray, ticks, power, 0);
                        GT_Recipe.GT_Recipe_Map.sChemicalRecipes.addRecipe(recipe);
                    } else if (i.nextBool()) {
                        GT_Recipe.GT_Recipe_Map_LargeChemicalReactor.GT_Recipe_LargeChemicalReactor mulRecipe =
                                new GT_Recipe.GT_Recipe_Map_LargeChemicalReactor.GT_Recipe_LargeChemicalReactor(
                                        false, inputArray, outArray, null,
                                        chances, flInArray, flOutArray, ticks, power, 0
                                );
                        GT_Recipe.GT_Recipe_Map.sMultiblockChemicalRecipes.addRecipe(mulRecipe);
                    }
                }
            });
        }
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, ILiquidStack fluidOutput, IIngredient input1, IIngredient input2, ILiquidStack fluidInput, int durationTicks, int euPerTick) {
        addRecipe(output, null, fluidOutput, input1, input2, fluidInput, durationTicks, euPerTick);
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, ILiquidStack fluidOutput, IIngredient input1, IIngredient input2, ILiquidStack fluidInput, int durationTicks) {
        addRecipe(output, fluidOutput, input1, input2, fluidInput, durationTicks, 30);
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input1, IIngredient input2, int durationTicks) {
        addRecipe(output, null, input1, input2, null, durationTicks);
    }
}
