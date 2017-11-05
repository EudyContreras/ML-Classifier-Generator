package domains;

import java.util.List;
import java.util.Map;

import classifier.ClassifierManager;
import classifier.ClassifierManager.Attribute;
import classifier.ClassifierManager.Classification;
import classifier.ClassifierManager.Classifier;
import classifier.ClassifierManager.Processor;
import classifier.ClassifierManager.Tuple;


public class ExampleDomain<T>{
	
	/**
	 * The classifier instance which in this case is a decision tree.
	 * The classifier the classifier contains on method with different
	 * overloads. The method attempts to retrieve a classification for 
	 * given tuple using a specified processor, along with a tuple or
	 * tree node to use.
	 **/
	@SuppressWarnings("unused")
	private Classifier classifier = ClassifierManager.DECISION_TREE;

	/**
	 * Processor instance used by the classifier. The processor is 
	 * the bridge between the domain specific data and the classifier.
	 * In order for the classifier to work as intended the below methods
	 * must be correctly implemented.
	 **/
	@SuppressWarnings("unused")
	private Processor processor = new Processor(){

		@Override
		public String getRowData(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] getDataMatrix() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] getClasses() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, Attribute> getRowAttributeData() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, Attribute> getRowAttributeData(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Map<String, Attribute>> getRowAttributeDataCollection() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Tuple getTuple() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Tuple getTuple(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Tuple> getCollectedTuples() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean equalClasses(String classA, String classB) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean equalClasses(Classification classA, Classification classB) {
			// TODO Auto-generated method stub
			return false;
		}
		
	};
	
}
