package mega.gregification;
import java.util.List;
import mega.gregification.mods.gregtech.expand.*;

public class GregificationRegistry {
	public static void getClasses(List<Class> classes) {
		classes.add(OreDictEntryExpansion.class);
	}
}
