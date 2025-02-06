package mega.gregification.mods;

import gregtech.api.util.GT_Recipe;
import lombok.val;

public class AddGTDirectRecipeAction extends AddMultipleRecipeAction<GT_Recipe> {
    private final GT_Recipe.GT_Recipe_Map recipeMap;

    public AddGTDirectRecipeAction(GT_Recipe.GT_Recipe_Map recipeMap, String description, Object... recipeArgs) {
        super(description, recipeArgs);
        this.recipeMap = recipeMap;
    }

    protected GT_Recipe create(ArgIterator i) {
        return new GT_Recipe(false, i.nextItemArr(), i.nextItemArr(), null, i.nextIntArr(), i.nextFluidArr(), i.nextFluidArr(), i.nextInt(), i.nextInt(), 0);
    }

    @Override
    protected final GT_Recipe applySingleRecipe(ArgIterator i) {
        val recipe = create(i);
        return recipeMap.addRecipe(recipe);
    }

    @Override
    protected final void undoSingleRecipe(GT_Recipe recipe) {
        recipeMap.remove(recipe);
    }
}
