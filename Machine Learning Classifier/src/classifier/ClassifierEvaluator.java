package classifier;

import java.util.List;

import classifier.ClassifierManager.Classification;
import classifier.ClassifierManager.ClassifierMaster;
import classifier.ClassifierManager.DataSet;
import classifier.ClassifierManager.Processor;
import classifier.ClassifierManager.TreeNode;
import classifier.ClassifierManager.Tuple;



public class ClassifierEvaluator {

	public static enum AccuracyType { TRAINING_ACCUARCY, TESTING_ACCURACY, OVERALL }
	
	public static enum EvaluatorType { DEFAULT_EVALUATOR, CONFUSION_MATRIX_EVALUATOR };
	
	public static final Evaluator DEFAULT_EVALUATOR = new DefaultEvaluator();
	
	public static final Evaluator CONFUSION_MATRIX_EVALUATOR = new ConfusionMatrix();
	
	public static interface Evaluator {
		
		public Evaluation getAccuracy(DataSet dataSet, ClassifierMaster classifier, TreeNode tree, Processor processor, AccuracyType accuracyType);
	}
	
	public static class DefaultEvaluator implements Evaluator{
		
		@Override
		public Evaluation getAccuracy(DataSet dataSet, ClassifierMaster classifier, TreeNode tree, Processor processor, AccuracyType accuracyType){			
			
			List<Tuple> data = getDataToEvaluate(dataSet, accuracyType);
			
			int hitCount = 0;
			
			int tupleCount =  data.size();
			
			for(int i = 0; i<tupleCount; i++){
				
				Tuple tuple = data.get(i);
				
				Classification classification = classifier.retrieveClassification(processor, tree, new Tuple(tuple), Integer.MAX_VALUE);

				if(processor.equalClasses(tuple.getClassification(), classification)){
					hitCount++;
				}
			}
			
			return new Evaluation(hitCount, tupleCount-hitCount, tupleCount);
		}
	}
	
	/**
	 * Class used for generating and using a confusion matrix for
	 * calculating the precision and error rate of X classifier.
	 * 
	 * TODO: Determine if CM can be used with non binary classes! I don't remember
	 * 
	 * @author Eudy Contreras & Pierre Leidbring
	 *
	 */
	public static class ConfusionMatrix implements Evaluator{
		

		@Override
		public Evaluation getAccuracy(DataSet dataSet, ClassifierMaster classifier, TreeNode tree, Processor processor, AccuracyType accuracyType) {
			try {
				throw new NotImplementedException();
			} catch (NotImplementedException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@SuppressWarnings("unused")
		private static void generateConfusionMatrix(){
			try {
				throw new NotImplementedException();
			} catch (NotImplementedException e) {
				e.printStackTrace();
			}
		}
		
		@SuppressWarnings("unused")
		private static void getClassifierPrecission(){
			try {
				throw new NotImplementedException();
			} catch (NotImplementedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private static List<Tuple> getDataToEvaluate(DataSet dataSet, AccuracyType accuracyType){
		
		switch(accuracyType){
		case OVERALL:
			return dataSet.getTuples();
		case TESTING_ACCURACY:
			return dataSet.getDataSplit().getTestData().getTuples();
		case TRAINING_ACCUARCY:
			return dataSet.getDataSplit().getTrainingData().getTuples();
		default:
			return dataSet.getTuples();	
		}
	}

	public static class Evaluation{
		
		public static final Evaluation DEFAULT = getDefault();

		private double successRatio;	
		private double failureRatio;
		
		private int hitCount;
		private int missCount;	
		
		private int tupleCount;
		
		public Evaluation(int hitCount, int missCount, int tupleCount) {
			this.hitCount = hitCount;
			this.missCount = missCount;
			this.tupleCount = tupleCount;
			this.successRatio = (double)hitCount * 100.0d / (double)tupleCount;
			this.failureRatio = (double)missCount * 100.0d / (double)tupleCount;
		}
		
		public static Evaluation getDefault(){
			return new Evaluation(0, 0, 0);
		}

		public double getSuccessRatio() {
			return successRatio;
		}

		public double getFailureRatio() {
			return failureRatio;
		}

		public int getHitCount() {
			return hitCount;
		}

		public int getMissCount() {
			return missCount;
		}

		public int getTupleCount() {
			return tupleCount;
		}
	}
}
