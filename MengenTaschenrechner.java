import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
public class MengenTaschenrechner {

	public static void main(String[] args) {
		
		Scanner scr = new Scanner(System.in);
		boolean check = false;
		String choice = "";
		
		// Auswhal von Benutzer 
		System.out.println("1- Test Beispiele ausführen\n"
						  +"2-  Eingabe vom Benutzer\n\n"
						  + "Bitte geben Sie die Nummer Ihres Wahles ein?");
		choice = scr.nextLine();
		
		while(!choice.equals("1") && !choice.equals("2")) {
			System.out.println("Ihre Eingabe ist weder 1 noch 2, bitte geben Sie nochmal ein?");
			choice = scr.nextLine();
		}
		
		// 5 Teste des Programms
		if(choice.equals("1")) {
			
			String[] tests = {"","","","",""};
			tests[0] = "[1, 2, 3] * [4, 6 , 2,1 ]";
			tests[1] = "[1,1,2,22,7]+[4,6,2,1]";
			tests[2] = "[1 , 1 , 2 , 22 , 7 ]- [1 , 2 , 3]";
			tests[3] = "[25, 50, 75] +[20, 25, 50, 75, 100]";
			tests[4] = "[]*[1, 2, 3]";
			
			for(int i= 0; i< tests.length;i++) {
			
			check = checkmengen(tests[i]);
			
			// bei richtiger Überprufung wird der Ausdruck getrennt und später berechnet 
			if(check) 
				splitMengen(tests[i]);
			else
				System.out.println("Ungultiger Ausdruck wurde eingegeben!");
			}
		}			
		
		// Programm mit der Eingabe vom Nutzer!!
		else if(choice.equals("2")) {
		
		while(true) {
		
		// Ausdruck vom Nutzer	
		System.out.println("Bitte tragen Sie einen Ausdruck mit deren Beziehung ein? oder leere Zeile zum Beenden.");
		String userMengen=scr.nextLine();
		
		// endet das Programm bei der Eingabe einer leere Zeile 
		if(userMengen.equals(""))
			break;
		
		// Überprufung der eingegebene Ausdruck
		check = checkmengen(userMengen);
		
		// bei richtiger Überprufung wird der eingegebene Ausdruck getrennt und später berechnet 
		if(check) 
			splitMengen(userMengen);
		else
			System.out.println("Ungultiger Ausdruck wurde eingegeben!");
		}
		}
		
		System.out.println("Das Programm ist schon beendet!");
		scr.close();
	}
	
	public static boolean checkmengen(String userMengen) {
			
				//Regulär Ausdruck der Erste Menge
		String regex = "(\\[((((\\s|)\\d{0,}(\\s|)[,](\\s|)\\d{1,}){0,}(\\s|))|((\\d{0,})(\\s|)))\\])" 
				// die Bezihung zwischen die Mengen
				+"((\\s|)(\\+|\\*|\\-)(\\s|))" 
				// Regulär Ausdruck der zweites Menge
				+"(\\[((((\\s|)\\d{0,}(\\s|)[,](\\s|)\\d{1,}){0,}(\\s|))|((\\d{0,})(\\s|)))\\])";
		
		// Überprüfung der eingegebe Ausdruck mit der reguläre Ausdruck
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(userMengen);
		boolean result = mt.matches();
		
		// gibt true zurück, falls es matchet
		return result;	
	}
	
	public static void splitMengen(String userMengen) {
		
		Set<Integer> setA = new HashSet<Integer>();
		Set<Integer> setB = new HashSet<Integer>();
		
		int counter = 0;
		String operation ="";
		
		// Lösche die zusätzliche Zeichen '[' ']' ',' ' '
		String[] arrOfStr = userMengen.split("[, \\[\\]]+");
		
		// Trennung des Ausdruck in zwei Gruppen, Erkennung durch der Opertaion   
        for (int i=1;i<arrOfStr.length;i++ ) { 
        	
        	if(arrOfStr[i].equals("+") || arrOfStr[i].equals("-") || arrOfStr[i].equals("*")) {
        		operation = arrOfStr[i];
        		counter++;
        		continue;
        	}
        	if(counter == 0) {
        		setA.add(Integer.parseInt(arrOfStr[i]));
        	}
        	else
        		setB.add(Integer.parseInt(arrOfStr[i]));
        }
		// Berechnung der Mengen, jenach Operation
        System.out.println(userMengen+" = " + mengenRechner(setA, setB, operation));        	
	}
	
	private static <TElement> Set<TElement> mengenRechner(Set<TElement> setA,
			Set<TElement> setB, String operation) {
		Set<TElement> set = new HashSet<TElement>(setA);
		
		// Durchschnitt Mengen
		if(operation.equals("*"))
			set.retainAll(setB);
		// Differenz Mengen
		else if(operation.equals("-"))
			set.removeAll(setB);
		// Vereinigung Mengen
		else if(operation.equals("+"))
			set.addAll(setB);
		
		return set;
	}	
}
