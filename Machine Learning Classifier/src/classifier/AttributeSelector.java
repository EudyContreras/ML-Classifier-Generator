package classifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import classifier.ClassifierManager.Attribute;
import classifier.ClassifierManager.DataSet;
import classifier.ClassifierManager.Processor;
import classifier.ClassifierManager.Tuple;


public class AttributeSelector {
	
	public static enum AttributeSelectionMethod{ C45, CART, ID3 }

	public static final Selector ATTRIBUTE_SELECTOR_C45 = new AttributeSelectorC45();
	
	public static final Selector ATTRIBUTE_SELECTOR_CART = new AttributeSelectorCART();
	
	public static final Selector ATTRIBUTE_SELECTOR_ID3 = new AttributeSelectorID3();

	private static final double BASE_2_LOGARITHM = Math.log10(2);
	
	private AttributeSelector(){};
	
	private static final double log2(double value){
		return Math.log10(value) / BASE_2_LOGARITHM;
	}
	
	public static interface Selector{
		
		public Attribute choseBestAttribute(DataSet  dataSet, Processor processor, Map<String, Attribute> attributes);
	}
	
	public static class AttributeSelectorID3 implements Selector{

		@Override
		public Attribute choseBestAttribute(DataSet dataSet, Processor processor, Map<String, Attribute> attributes) {
	
			double wholeInfoGain = infoGain(dataSet, processor);

			InfoGain<Attribute> infoGain = null;
			
			for(Entry<String, Attribute> entry : attributes.entrySet()){
				
				Attribute attribute = entry.getValue();

				InfoGain<Attribute> currentGain = new InfoGain<>(attribute,getAttributeGain(dataSet, processor, wholeInfoGain, attribute));	

				if(infoGain == null){
					infoGain = currentGain;
				}else{
					
					if(currentGain.getInfoGain() > infoGain.getInfoGain()){
						infoGain = currentGain;
					}
				}
			}
	
			return infoGain.getAttribute();		
		}
		
		private static double getAttributeGain(DataSet dataSet, Processor processor, double wholeInfoGain, Attribute attribute) {

			double gainAccumulation = 0d;
							
			for(String value : attribute.getPossibleValues()){
				
				List<Tuple> subTuples = new ArrayList<>();
				
				for(Tuple tuple : dataSet.getTuples()){				
				
					if(value.contentEquals(tuple.getData().get(attribute.getAttributeName()).getAttributeValue())){
						
						subTuples.add(tuple);
					}
				}
				
				if(subTuples.isEmpty()) continue;

				gainAccumulation += infoGain(new DataSet(subTuples), processor);
			}		
			
			return wholeInfoGain - (gainAccumulation/(double)attribute.getPossibleValues().length);
		}
	}
	
	public static class AttributeSelectorC45 implements Selector{

		@Override
		public Attribute choseBestAttribute(DataSet dataSet, Processor processor, Map<String, Attribute> attributes) {
			// TODO Auto-generated method stub
			try {
				throw new NotImplementedException();
			} catch (NotImplementedException e) {
				e.printStackTrace();
			}
			return null;
		}

	}
	
	public static class AttributeSelectorCART implements Selector{

		@Override
		public Attribute choseBestAttribute(DataSet dataSet, Processor processor, Map<String, Attribute> attributes) {
			// TODO Auto-generated method stub
			try {
				throw new NotImplementedException();
			} catch (NotImplementedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private static double infoGain(DataSet dataSet, Processor processor){
		
		double infoGain = 0d;
		
		for(int i = 0; i<processor.getClasses().length; i++){
			
			String classification = processor.getClasses()[i];
			
			double probability = getProbability(dataSet, classification, processor);
			
			double gain = 0d;
			
			if(probability > 0.0d) {
				gain = (double)(-probability * log2(probability));
			}
			
			infoGain += gain;
		}
		
		return infoGain;
	}
	
	private static double getProbability(DataSet dataSet, String classification, Processor processor){
		
		int counter = 0;
		
		int totalCount = dataSet.getTuples().size();
		
		if(totalCount == 0){
			return 0d;
		}
		
		for(int i = 0; i<totalCount; i++){
			if(processor.equalClasses(dataSet.getTuples().get(i).getClassification().getRepresentation(),classification)){
				counter++;
			}
		}	
		
		return ((double)counter/(double)totalCount);
	}
	
	private static class InfoGain<ATTRIBUTE>{
		
		private double infoGain;
		
		private ATTRIBUTE attribute;

		public InfoGain(ATTRIBUTE attribute, double infoGain) {
			super();
			this.infoGain = infoGain;
			this.attribute = attribute;
		}

		public double getInfoGain() {
			return infoGain;
		}

		public ATTRIBUTE getAttribute() {
			return attribute;
		}		
	}
}
