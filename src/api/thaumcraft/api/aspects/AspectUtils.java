package thaumcraft.api.aspects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class AspectUtils {
	
	public static Aspect getCombinationResult(Aspect aspect1, Aspect aspect2) {
		Collection<Aspect> aspects = Aspect.aspects.values();
		for (Aspect aspect:aspects) {
			if (aspect.getComponents()!=null && (
				(aspect.getComponents()[0]==aspect1 && aspect.getComponents()[1]==aspect2) ||
				(aspect.getComponents()[0]==aspect2 && aspect.getComponents()[1]==aspect1))) {
				
				return aspect;
			}
		}
		return null;
	}

	public static Aspect getRandomPrimal(Random rand, Aspect aspect) {
		ArrayList<Aspect> list = new ArrayList<Aspect>();
		if (aspect!=null) {			
			AspectList temp = new AspectList();
			temp.add(aspect, 1);
			AspectList temp2 = reduceToPrimals(temp);
			for (Aspect a:temp2.getAspects()) {
				for (int b=0;b<temp2.getAmount(a);b++) {
					list.add(a);
				}
			}
		}
		
		return list.size()>0?list.get(rand.nextInt(list.size())):null;
	}
	
	public static AspectList reduceToPrimals(AspectList in) {
		AspectList out = new AspectList();
		for (Aspect aspect:in.getAspects()) {
			if (aspect!=null) {
				if (aspect.isPrimal()) {
					out.add(aspect, in.getAmount(aspect));
				} else {
					AspectList temp = new AspectList();
					temp.add(aspect.getComponents()[0],in.getAmount(aspect));
					temp.add(aspect.getComponents()[1],in.getAmount(aspect));
					AspectList temp2 = reduceToPrimals(temp);
					
					for (Aspect a:temp2.getAspects()) {
						out.add(a, temp2.getAmount(a));
					}
				}
			}
		}
		return out;
	}
	
	public static AspectList getPrimalAspects(AspectList in) {
		AspectList t = new AspectList();
		t.add(Aspect.AIR, in.getAmount(Aspect.AIR));
		t.add(Aspect.FIRE, in.getAmount(Aspect.FIRE));
		t.add(Aspect.WATER, in.getAmount(Aspect.WATER));
		t.add(Aspect.EARTH, in.getAmount(Aspect.EARTH));
		t.add(Aspect.ORDER, in.getAmount(Aspect.ORDER));
		t.add(Aspect.ENTROPY, in.getAmount(Aspect.ENTROPY));
		return t;
	}
}
