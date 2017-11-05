package classifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import classifier.ClassifierManager.DataSet;
import classifier.ClassifierManager.Tuple;

public class DataSetSplitter { 

	public static enum DataSplittingMethod{ HOLDOUT, RANDOM, BOOTSTRAP, K_FOLD_CROSS_VALIDATION, NAIVE};
	
	public static final Splitter<DataSet> NAIVE = new NaiveSplitter();
	
	public static final Splitter<DataSet> RANDOM = new RadomSplitter();
	
	public static final Splitter<DataSet> HOLDOUT = new HoldOutSplitter();
	
	public static final Splitter<DataSet> BOOTSTRAP = new BootStrapSplitter();

	public static final Splitter<DataSet> K_FOLD_CROSS_VALIDATION = new KFoldSplitter();
	
	public static final int SPLIT_ITERATIONS = 20;
	
	private DataSetSplitter(){};
	
	public static int getRandom(Random rand, int maxValue) {
		return rand.nextInt(maxValue);
	}
	
	/**
	 * Interface which handles the splitting of data into training and testing data.
	 * 
	 * @author Eudy Contreras & Pierre Leidbring
	 *
	 * @param <DATASET>
	 */
	public static interface Splitter<DATASET>{
		
		public List<DataSplit<DATASET>> getDataSplit(DATASET dataSet);
	}
	
	public static class NaiveSplitter implements Splitter<DataSet>{

		@Override
		public List<DataSplit<DataSet>> getDataSplit(DataSet dataSet) {

			int ratio = (int) (dataSet.getTuples().size() * 0.7);
			
			List<Tuple> trainingSet = dataSet.getTuples().stream().limit(ratio).collect(Collectors.toList());
			
			List<Tuple> testingSet =  dataSet.getTuples().stream().skip(ratio).collect(Collectors.toList());
			
			List<DataSplit<DataSet>> splits = new ArrayList<>();
			
			splits.add(new DataSplit<DataSet>(new DataSet(trainingSet),new DataSet(testingSet)));
			
			return splits;
		}
	}
	
	public static class RadomSplitter implements Splitter<DataSet>{

		@Override
		public List<DataSplit<DataSet>> getDataSplit(DataSet dataSet) {

			List<DataSplit<DataSet>> splits = new ArrayList<>();
			
			for(int i = 0; i<SPLIT_ITERATIONS; i++){
				
				List<Tuple> mainSet = new LinkedList<>(dataSet.getTuples());

				Collections.shuffle(mainSet,new Random());
				
				int ratio = (int) (mainSet.size() * 0.7);
				
				List<Tuple> trainingSet = mainSet.stream().limit(ratio).collect(Collectors.toList());
				
				List<Tuple> testingSet =  mainSet.stream().skip(ratio).collect(Collectors.toList());
				
				splits.add(new DataSplit<DataSet>(new DataSet(trainingSet),new DataSet(testingSet)));			
			}		
			return splits;
		}
	}
	
	public static class BootStrapSplitter implements Splitter<DataSet>{

		@Override
		public List<DataSplit<DataSet>> getDataSplit(DataSet dataSet) {

			List<DataSplit<DataSet>> dataSplits = new ArrayList<>();
					
			Random random = new Random();

			for(int i = 0; i<SPLIT_ITERATIONS; i++){
		
				List<Tuple> trainingSet = new ArrayList<>();
				
				List<Tuple> testingSet = new ArrayList<>();
				
				Set<Integer> indexesChosen = new HashSet<>();
				
				for(int r = 0; r<dataSet.getTuples().size(); r++){
					
					int index = getRandom(random, dataSet.getTuples().size());
					
					trainingSet.add(dataSet.getTuples().get(index));
					
					indexesChosen.add(index);
				}
				
				for(int r = 0; r<dataSet.getTuples().size(); r++){
					
					Tuple tuple = dataSet.getTuples().get(r);
					
					if(!indexesChosen.contains(r)){
						testingSet.add(tuple);
					}
				}		
				
				dataSplits.add(new DataSplit<DataSet>(new DataSet(trainingSet),new DataSet(testingSet)));
			}
			
			return dataSplits;
		}
	}
	
	public static class HoldOutSplitter implements Splitter<DataSet>{

		@Override
		public List<DataSplit<DataSet>> getDataSplit(DataSet dataSet) {
			// TODO Auto-generated method stub
			try {
				throw new NotImplementedException();
			} catch (NotImplementedException e) {
				e.printStackTrace();
			}
			return null;
		}	
	}
	
	public static class KFoldSplitter implements Splitter<DataSet>{

		@Override
		public List<DataSplit<DataSet>> getDataSplit(DataSet dataSet) {
			// TODO Auto-generated method stub
			try {
				throw new NotImplementedException();
			} catch (NotImplementedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public static class DataSplit<DATASET>{
		
		public DATASET trainingData;
		
		public DATASET testData;

		public DataSplit(DATASET trainingData, DATASET testData) {
			super();
			this.trainingData = trainingData;
			this.testData = testData;
		}

		public DATASET getTrainingData() {
			return trainingData;
		}

		public void setTrainingData(DATASET trainingData) {
			this.trainingData = trainingData;
		}

		public DATASET getTestData() {
			return testData;
		}

		public void setTestData(DATASET testData) {
			this.testData = testData;
		}
	}
}
