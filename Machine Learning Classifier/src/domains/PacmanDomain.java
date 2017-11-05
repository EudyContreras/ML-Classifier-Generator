//package domains;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import dataRecording.DataSaverLoader;
//import dataRecording.DataTuple;
//import dataRecording.DataTuple.DiscreteTag;
//import pacman.entries.pacman.MyClassifierGenerator.Attribute;
//import pacman.entries.pacman.MyClassifierGenerator.Classification;
//import pacman.entries.pacman.MyClassifierGenerator.Classifier;
//import pacman.entries.pacman.MyClassifierGenerator.Processor;
//import pacman.entries.pacman.MyClassifierGenerator.Tuple;
//import pacman.game.Constants.MOVE;
//import pacman.game.Game;
//
//public class PacmanDomain{
//
//	private static final Map<String, MOVE> MOVE_MAP;
//	
//	private static final Set<Integer> SKIP_INDEX = new HashSet<>(Arrays.asList(0,1,2,4,6,22,23,24,25)); 
//	
//	private static final DataTuple[] TUPLES = DataSaverLoader.LoadPacManData();
//	private Game game;
//	
//	static {
//        Map<String, MOVE> map = new HashMap<String, MOVE>();
//        
//        map.put(MOVE.UP.name(), MOVE.UP);
//        map.put(MOVE.DOWN.name(), MOVE.DOWN);
//        map.put(MOVE.LEFT.name(), MOVE.LEFT);
//        map.put(MOVE.RIGHT.name(), MOVE.RIGHT);
//        map.put(MOVE.NEUTRAL.name(), MOVE.NEUTRAL);
//        
//        MOVE_MAP = Collections.unmodifiableMap(map);
//    }
//
//	private Processor processor = new Processor(){
//		
//		@Override
//		public String[] getClasses() {		
//			return MOVE_MAP.keySet().stream().collect(Collectors.toList()).toArray(new String[MOVE_MAP.size()]);
//		}
//		
//		@Override
//		public String getRowData(int index) {		
//			if(index == -1){
//				return MyUtilities.getDiscreteData(game, new DataTuple(game, MOVE.NEUTRAL));		
//			}			
//			return MyUtilities.getDiscreteData(game, TUPLES[index]);		
//		}
//
//		@Override
//		public String[] getDataMatrix() {
//			String[] matrix = new String[TUPLES.length];
//			
//			for(int i = 0; i < TUPLES.length; i++) {
//				matrix[i] = getRowData(i);
//			}
//			return matrix;
//		}
//		
//		@Override
//		public Map<String, Attribute> getRowAttributeData(int index) {
//
//			Map<String, Attribute> attributes = new HashMap<>();
//						
//			List<String> names = MyUtilities.retrieveAttributeNames(new DataTuple(game,MOVE.NEUTRAL));
//			
//			String[] values = getRowData(index).split(";");
//			
//			for(int i = 0; i<values.length; i++){
//		
//				String[] attrValues = MyUtilities.extractPossibleAttributes(values[i]);
//
//				Attribute attribute = new Attribute(values[i],attrValues, names.get(i), i);
//				
//				attributes.put(attribute.getAttributeName(), attribute);
//			}
//			
//			return attributes;
//		}
//		
//		@Override
//		public List<Map<String, Attribute>> getRowAttributeDataCollection() {
//			
//			List<Map<String, Attribute>> matrix = new ArrayList<>();
//			
//			for(int i = 0; i < TUPLES.length; i++) {
//				matrix.add(getRowAttributeData(i));
//			}
//			return matrix;
//		}
//
//		@Override
//		public Map<String, Attribute> getRowAttributeData() {
//			return getTuple().getData();
//		}
//		
//		@Override
//		public Tuple getTuple() {		
//			return getTuple(-1);
//		}
//		
//		@Override
//		public Tuple getTuple(int index) {
//			
//			Map<String, Attribute> attributeData = getRowAttributeData(index);
//			
//			Tuple tuple = new Tuple(attributeData);
//			
//			Classification classification = new Classification(Classification.NO_CLASS,tuple);
//			
//			if(index  != -1){
//				classification = MyUtilities.getClassification(game, TUPLES[index], tuple);
//			}
//
//			tuple.setClassification(classification);
//			
//			return tuple;
//		}
//
//		@Override
//		public List<Tuple> getCollectedTuples() {
//			
//			List<Tuple> tuples = new ArrayList<>();
//			
//			for(int i = 0; i<TUPLES.length; i++){
//				tuples.add(getTuple(i)	);
//			}
//			
//			return tuples;
//		}
//
//		@Override
//		public boolean equalClasses(String classA, String classB) {
//			return MOVE.valueOf(classA) == (MOVE.valueOf(classB));
//		}
//
//		@Override
//		public boolean equalClasses(Classification classA, Classification classB) {
//			return MOVE.valueOf(classA.getRepresentation()) == (MOVE.valueOf(classB.getRepresentation()));
//		}
//	};
//	
//	public MOVE retrieveClassification(Game game, long timeDue) {
//		this.game = game;
//		
//		Classifier classifier = ClassifierManager.DECISION_TREE;
//
//		return getMove(classifier.retrieveClassification(processor, timeDue));
//		
//	}
//	
//	public static MOVE getMove(Classification classification){
//		return MOVE_MAP.get(classification.getRepresentation());
//	}
//
//	public static enum DataType {BOOLEAN, INTEGER, MOVE, DISCRETE_TAG, OTHER};
//		
//	public static class MyUtilities {
//
//		public static DataType getType(String value){
//			try{
//				Integer.parseInt(value);				
//			}catch(Exception e2){
//				try{
//					MOVE.valueOf(value);					
//				}catch(Exception e3){
//					try{
//						DiscreteTag.valueOf(value);					
//					}catch(Exception e4){
//						try{
//							Boolean.parseBoolean(value);			
//						}catch(Exception e5){
//							return DataType.OTHER;
//						}
//						return DataType.BOOLEAN; 
//					}
//					return DataType.DISCRETE_TAG;
//				}
//				return DataType.MOVE;
//			}			
//			return DataType.INTEGER;	
//		}
//		
//		public static final List<String> retrieveAttributeNames(DataTuple tuple) {
//			List<String> names = new ArrayList<>();
//			
//		    for(int i = 0; i<tuple.getClass().getFields().length; i++) {
//		    	
//				if(SKIP_INDEX.contains(i)) continue;
//				
//		        names.add(tuple.getClass().getFields()[i].getName());
//		    }
//		    
//		    return names;
//		}
//		
//		public static String[] extractPossibleAttributes(String value) {
//			DataType type = getType(value);
//			
//			switch(type){
//			case BOOLEAN:			
//				return new String[]{"true","false"};				
//			case DISCRETE_TAG:
//				List<String> tagsTemp = Stream.of(DiscreteTag.values()).map(Enum::name).collect(Collectors.toList());
//				String[] tags = new String[tagsTemp.size()];
//				return tagsTemp.toArray(tags);
//			case MOVE:
//				List<String> movesTemp = Stream.of(MOVE.values()).map(Enum::name).collect(Collectors.toList());
//				String[] moves = new String[movesTemp.size()];
//				return movesTemp.toArray(moves);
//			case OTHER:
//				break;
//			default:
//				break;
//			
//			}
//			return null;
//		}
//		
//		//<----------------------------------------------------------------------------------->
//		
//		public static DiscreteTag discretizeEdibleTime(Game game, int time) {
//			return DiscreteTag.NONE;
//		}
//		
//		public static int calcRemainingEdibleTime(Game game, int edibleTime) {
//			return 0;
//		}
//
//		public static Classification getClassification(Game game, DataTuple dataTuple, Tuple tuple){
//			
//			String value = String.valueOf(dataTuple.DirectionChosen);
//			
//			return new Classification(value == null ? Classification.NO_CLASS : value,tuple);
//		}
//		
//		/**
//		 * In order to make the 
//		 * @param game
//		 * @param tuple
//		 * @return
//		 */
//		public static String getDiscreteData(Game game, DataTuple tuple){
////			
////			int blinkyEdibleTime = game.getGhostEdibleTime(GHOST.BLINKY);
////			int inkyEdibleTime = game.getGhostEdibleTime(GHOST.INKY);
////			int pinkyEdibleTime = game.getGhostEdibleTime(GHOST.PINKY);
////			int sueEdibleTime = game.getGhostEdibleTime(GHOST.SUE);
////			
////			int[] remainingEdibleTime = new int[4];
////			
////			for(int i = 0; i < remainingEdibleTime.length; i++) {
////				//for every ghost set time in remainingEdibleTime
////			}
//			
//			StringBuilder stringbuilder = new StringBuilder();
//
//			//stringbuilder.append(tuple.DirectionChosen + ";");
//			//stringbuilder.append(tuple.mazeIndex + ";");
//			//stringbuilder.append(tuple.currentLevel + ";");
//			stringbuilder.append(tuple.discretizePosition(tuple.pacmanPosition) + ";");
////			stringbuilder.append(tuple.pacmanLivesLeft + ";");
//			stringbuilder.append(tuple.discretizeCurrentScore(tuple.currentScore) + ";");
//			//stringbuilder.append(tuple.totalGameTime + ";");
//			stringbuilder.append(tuple.discretizeCurrentLevelTime(tuple.currentLevelTime) + ";");
//			stringbuilder.append(tuple.discretizeNumberOfPills(tuple.numOfPillsLeft)  + ";");
//			stringbuilder.append(tuple.discretizeNumberOfPowerPills(tuple.numOfPowerPillsLeft)  + ";");
//			stringbuilder.append(tuple.isBlinkyEdible + ";");
//			stringbuilder.append(tuple.isInkyEdible + ";");
//			stringbuilder.append(tuple.isPinkyEdible + ";");
//			stringbuilder.append(tuple.isSueEdible + ";");
//			stringbuilder.append(tuple.discretizeDistance(tuple.blinkyDist) + ";");
//			stringbuilder.append(tuple.discretizeDistance(tuple.inkyDist) + ";");
//			stringbuilder.append(tuple.discretizeDistance(tuple.pinkyDist) + ";");
//			stringbuilder.append(tuple.discretizeDistance(tuple.sueDist) + ";");
//			stringbuilder.append(tuple.blinkyDir + ";");
//			stringbuilder.append(tuple.inkyDir + ";");
//			stringbuilder.append(tuple.pinkyDir + ";");
//			stringbuilder.append(tuple.sueDir + ";");	
//
//			return stringbuilder.toString();
//		}
//	}
//}
