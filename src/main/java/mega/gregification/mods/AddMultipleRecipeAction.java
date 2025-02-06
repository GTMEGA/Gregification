package mega.gregification.mods;

import lombok.val;
import mega.gregification.util.exception.AnyIngredientException;
import mega.gregification.util.exception.EmptyIngredientException;
import mega.gregification.util.exception.OutOfStackSizeException;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

/**
 * Created by Techlone
 */
public abstract class AddMultipleRecipeAction<T> implements IUndoableAction {
    private static List<List<Object>> createNewMatrix(int initCount) {
        return new ArrayList<List<Object>>(initCount) {{
            this.add(new ArrayList<Object>());
        }};
    }

    private static void extendBySingle(Object singleArg, List<List<Object>> recipesData) {
        for (List<Object> recipeData : recipesData) {
            recipeData.add(singleArg);
        }
    }

    private static void extendByMultiple(List args, List<List<Object>> recipesData) {
        List<List<Object>> originData = fullCopy(recipesData);
        recipesData.clear();
        for (Object singleArg : args) {
            List<List<Object>> tmp = fullCopy(originData);
            extendBySingle(singleArg, tmp);
            recipesData.addAll(tmp);
        }
    }

    private static List<List<Object>> fullCopy(List<List<Object>> recipesData) {
        List<List<Object>> result = new ArrayList<List<Object>>(recipesData.size());
        for (List<Object> recipeData : recipesData) {
            result.add(new ArrayList<Object>(recipeData));
        }
        return result;
    }

    private String description;
    private List<List<Object>> recipesData;
    private List<T> undoData;

    private void addArgument(Object recipeArg) {
        if (recipeArg instanceof ILiquidStack) {
            extendBySingle(MineTweakerMC.getLiquidStack((ILiquidStack) recipeArg), recipesData);
        } else if (recipeArg instanceof ILiquidStack[]) {
            extendBySingle(MineTweakerMC.getLiquidStacks((ILiquidStack[]) recipeArg), recipesData);
        } else if (recipeArg instanceof IItemStack) {
            extendBySingle(MineTweakerMC.getItemStack((IItemStack) recipeArg), recipesData);
        } else if (recipeArg instanceof IItemStack[]) {
            extendBySingle(MineTweakerMC.getItemStacks((IItemStack[]) recipeArg), recipesData);
        } else if (recipeArg instanceof IIngredient) {
            extendByMultiple(getItemStacks((IIngredient) recipeArg), recipesData);
        } else if (recipeArg instanceof IIngredient[]) {
            extendByMultiple(getItemStackArrays((IIngredient[]) recipeArg), recipesData);
        } else {
            extendBySingle(recipeArg, recipesData);
        }
    }

    private List<ItemStack[]> getItemStackArrays(IIngredient[] recipeArg) {
        List<List<Object>> tempArgs = createNewMatrix(recipeArg.length);
        for (IIngredient ingredient : recipeArg) {
            extendByMultiple(getItemStacks(ingredient), tempArgs);
        }

        List<ItemStack[]> result = new ArrayList<ItemStack[]>(tempArgs.size());
        for (List<Object> tempArg : tempArgs) {
            ItemStack[] arg = new ItemStack[tempArg.size()];
            for (int i = 0; i < arg.length; i++)
                arg[i] = (ItemStack) tempArg.get(i);
            result.add(arg);
        }

        return result;
    }

    private List<ItemStack> getItemStacks(IIngredient ingredientArg) {
        List<IItemStack> items = ingredientArg.getItems();
        if (items == null) {
            throw new AnyIngredientException();
        }
        if (items.size() == 0) {
            throw new EmptyIngredientException(ingredientArg);
        }
        List<ItemStack> itemStackList = Arrays.asList(MineTweakerMC.getItemStacks(items));
        int amount = ingredientArg.getAmount();
        if (amount < 0) {
            throw new RuntimeException("Negative amount for ingredient " + ingredientArg);
        }
        for (ItemStack stack : itemStackList) {
            if (amount > stack.getMaxStackSize()) {
                throw new OutOfStackSizeException(ingredientArg, amount);
            }
            stack.stackSize = amount;
        }
        return itemStackList;
    }

    protected AddMultipleRecipeAction(String description, Object... recipeArgs) {
        this.description = description;
        recipesData = createNewMatrix(recipeArgs.length);

        try {
            for (Object recipeArg : recipeArgs) {
                addArgument(recipeArg);
            }
        } catch (Exception e) {
            MineTweakerAPI.logError(e.toString());
        }
    }

    protected abstract T applySingleRecipe(ArgIterator i);
    protected abstract void undoSingleRecipe(T recipe);

    protected static class ArgIterator {
        private Iterator<Object> iterator;

        public ArgIterator(List<Object> args) {
            this.iterator = args.iterator();
        }

        public ItemStack nextItem() {
            return (ItemStack) iterator.next();
        }

        public ItemStack[] nextItemArr() {
            return (ItemStack[]) iterator.next();
        }

        public FluidStack nextFluid() {
            return (FluidStack) iterator.next();
        }

        public FluidStack[] nextFluidArr() {
            return (FluidStack[]) iterator.next();
        }

        public int nextInt() {
            return (Integer) iterator.next();
        }

        public int[] nextIntArr() {
            return (int[]) iterator.next();
        }

        public boolean nextBool() {
            return (Boolean) iterator.next();
        }
    }

    @Override
    public void apply() {
        val undoData = new ArrayList<T>();
        for (List<Object> recipeData : recipesData) {
            val r = applySingleRecipe(new ArgIterator(recipeData));
            if (r != null) {
                undoData.add(r);
            }
        }
        if (!undoData.isEmpty()) {
            this.undoData = undoData;
        }
    }

    @Override
    public void undo() {
        if (undoData == null)
            return;
        val undoData = this.undoData;
        this.undoData = null;
        for (T recipe: undoData) {
            undoSingleRecipe(recipe);
        }
    }

    @Override
    public String describe() {
        return this.description;
    }

    @Override
    public String describeUndo() {
        return this.description.replace("Adding", "Removing");
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddMultipleRecipeAction that = (AddMultipleRecipeAction) o;
        return recipesData != null ? recipesData.equals(that.recipesData) : that.recipesData == null;
    }

    @Override
    public int hashCode() {
        return recipesData != null ? recipesData.hashCode() : 0;
    }
}
