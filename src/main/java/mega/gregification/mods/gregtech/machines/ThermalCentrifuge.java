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

@ZenClass("mods.gregtech.ThermalCentrifuge")
@ModOnly(MOD_ID)
public class ThermalCentrifuge {
    @ZenMethod
    public static void addRecipe(IItemStack output1, IItemStack output2, IItemStack output3, IIngredient input, int durationTicks, int euPerTick) {
        MineTweakerAPI.apply(new AddMultipleRecipeAction("Adding thermal centrifuge recipe for " + output1, input, output1, output2, output3, durationTicks, euPerTick) {
            @Override
            protected void applySingleRecipe(ArgIterator i) {
                RA.addThermalCentrifugeRecipe(i.nextItem(), i.nextItem(), i.nextItem(), i.nextItem(), i.nextInt(), i.nextInt());
            }
        });
    }
}
