package classifier;

import classifier.ClassifierManager.Classification;
import classifier.ClassifierManager.ClassifierGenerator;
import classifier.ClassifierManager.Processor;
import classifier.ClassifierManager.TreeNode;
import classifier.ClassifierManager.Tuple;

public class ClassificationEnhancer {

	public static enum ClassificationEnhancingMethod { BAGGING_ENHANCER, BOOSTING_ENHANCER, ADAPTIVE_BOOSTING_ENHANCER, RANDOM_FOREST_ENHANCER, NONE};
	
	public static final Enhancer BAGGING_ENHANCER = new BaggingEnhancer();
	
	public static final Enhancer BOOSTING_ENHANCER = new BoostingEnhancer();
	
	public static final Enhancer ADAPTIVE_BOOSTING_ENHANCER = new AdaptiveBoostingEnhancer();
	
	public static final Enhancer RANDOM_FOREST_ENHANCER = new RandomForestEnhancer();
	
	private ClassificationEnhancer(){};
	
	/**
	 * Classification Enhancement interface used for enhancing
	 * a classification by using one of the specified conventional
	 * methods!
	 * 
	 * @author Eudy Contreras & Pierre Leidbring
	 *
	 * @param <CLASS>
	 */
	public static interface Enhancer{
		
		public Classification getClassification(ClassifierGenerator generator, Processor processor, TreeNode tree, Tuple tuple, long dueTime);
	}
	
	public static class AdaptiveBoostingEnhancer implements Enhancer{

		@Override
		public Classification getClassification(ClassifierGenerator generator, Processor processor, TreeNode tree, Tuple tuple, long dueTime) {
			// TODO Auto-generated method stub
			try {
				throw new NotImplementedException();
			} catch (NotImplementedException e) {
				e.printStackTrace();
			}
			return null;
		}	
	}
	
	public static class RandomForestEnhancer implements Enhancer{

		@Override
		public Classification getClassification(ClassifierGenerator generator, Processor processor, TreeNode tree, Tuple tuple, long dueTime) {
			// TODO Auto-generated method stub
			try {
				throw new NotImplementedException();
			} catch (NotImplementedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	public static class BaggingEnhancer implements Enhancer{

		@Override
		public Classification getClassification(ClassifierGenerator generator, Processor processor, TreeNode tree, Tuple tuple, long dueTime) {
			// TODO Auto-generated method stub
			try {
				throw new NotImplementedException();
			} catch (NotImplementedException e) {
				e.printStackTrace();
			}
			return null;
		}	
	}
	
	public static class BoostingEnhancer implements Enhancer{

		@Override
		public Classification getClassification(ClassifierGenerator generator, Processor processor, TreeNode tree, Tuple tuple, long dueTime) {
			// TODO Auto-generated method stub
			try {
				throw new NotImplementedException();
			} catch (NotImplementedException e) {
				e.printStackTrace();
			}
			return null;
		}	
	}		
}
