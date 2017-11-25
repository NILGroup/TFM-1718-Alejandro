package AnalisisFrase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.coref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.ParagraphAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;


public class StanfordService {

	public ArrayList<String> parser(String text){
		ArrayList<String> result = new ArrayList<>();
		StanfordCoreNLP pipeline = new StanfordCoreNLP(
				PropertiesUtils.asProperties(
					"annotators", "tokenize, ssplit, pos, parse",
					"ssplit.isOneSentence", "true",
					"depparse.model" ,"edu / stanford / nlp / models / parser / nndep / UD_Spanish.gz",
					"depparse.language", "español",
					"pos.model", "edu/stanford/nlp/models/pos-tagger/spanish/spanish-distsim.tagger",
					"parse.model", "edu/stanford/nlp/models/lexparser/spanishPCFG.ser.gz",
					"tokenize.language", "es"));
		
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for(CoreMap sentence: sentences) {
			  // traversing the words in the current sentence
			  // a CoreLabel is a CoreMap with additional token-specific methods
			  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
			    // this is the text of the token
			    String word = token.get(TextAnnotation.class);
			    // this is the POS tag of the token
			    String pos = token.get(PartOfSpeechAnnotation.class);
			    // this is the NER label of the token
			    //String ne = token.get(NamedEntityTagAnnotation.class);
			    result.add(word+" "+pos);
			  }
		}
		return result;
		
	}
	
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		StanfordCoreNLP pipeline = new StanfordCoreNLP(
				PropertiesUtils.asProperties(
					"annotators", "tokenize, ssplit, pos, parse",
					"ssplit.isOneSentence", "true",
					"depparse.model" ,"edu / stanford / nlp / models / parser / nndep / UD_Spanish.gz",
					"depparse.language", "español",
					"pos.model", "edu/stanford/nlp/models/pos-tagger/spanish/spanish-distsim.tagger",
					"parse.model", "edu/stanford/nlp/models/lexparser/spanishPCFG.ser.gz",
					"tokenize.language", "es"));

			// read some text in the text variable
			String text = "La mayoría sabían lo que pasaba pero muchos se negaban a reconocerlo."; // Add your text here!
			//String text=""; -> Controlar cuando da error
			/*String text = "En un lugar de la Mancha, de cuyo nombre no quiero acordarme, "
					+ "no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, "
					+ "adarga antigua, rocín flaco y galgo corredor. Una olla de algo más vaca que carnero, "
					+ "salpicón las más noches, duelos y quebrantos los sábados, lentejas los viernes, "
					+ "algún palomino de añadidura los domingos, consumían las tres partes de su hacienda. "
					+ "El resto della concluían sayo de velarte, calzas de velludo para las fiestas con sus "
					+ "pantuflos de lo mismo, los días de entre semana se honraba con su vellori de lo más fino. "
					+ "Tenía en su casa una ama que pasaba de los cuarenta, y una sobrina que no llegaba a los "
					+ "veinte, y un mozo de campo y plaza, que así ensillaba el rocín como tomaba la podadera. "
					+ "Frisaba la edad de nuestro hidalgo con los cincuenta años, era de complexión recia, "
					+ "seco de carnes, enjuto de rostro; gran madrugador y amigo de la caza. "
					+ "Quieren decir que tenía el sobrenombre de Quijada o Quesada "
					+ "(que en esto hay alguna diferencia en los autores que deste caso escriben), "
					+ "aunque por conjeturas verosímiles se deja entender que se llama Quijana; "
					+ "pero esto importa poco a nuestro cuento; basta que en la narración dél no "
					+ "se salga un punto de la verdad.";*/
			
			Annotation document = new Annotation(text);
			// run all Annotators on this text
			pipeline.annotate(document);
		
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);
			
			for(CoreMap sentence: sentences) {
			  // traversing the words in the current sentence
			  // a CoreLabel is a CoreMap with additional token-specific methods
			  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
			    // this is the text of the token
			    String word = token.get(TextAnnotation.class);
			    // this is the POS tag of the token
			    String pos = token.get(PartOfSpeechAnnotation.class);
			    // this is the NER label of the token
			    //String ne = token.get(NamedEntityTagAnnotation.class);
			    System.out.println(word+" " +pos);
			    
			  }
			  
			  // this is the parse tree of the current sentence
			 Tree tree = sentence.get(TreeAnnotation.class);
			 ArrayList<String> resultado = new ArrayList<>();
			 //resultado = parserTree(resultado,tree);
			 /*for(int i=0; i<resultado.size();i++){
				 System.out.println(resultado.get(i));
			 }*/
			 System.out.println(tree);
	
			
			}
			
			long endTime = System.nanoTime();
			System.out.println("Duración: " + (endTime-startTime)/1e6 + " ms");

	}
	
	/*public static ArrayList<String> parserTree(ArrayList<String> res, Tree t){
		if(t.depth()!=0){
			for(Tree child: t.children()){
				if(child.depth()==1){
					res.add(child.toString());
				}else{
					parserTree(res,child);
				}
			}
		}
		
		return res;
	}*/
}
