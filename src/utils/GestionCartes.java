package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.Iterator;

public class GestionCartes {

	private static Random seed = new Random();

	public static <E> E extraire1 (List<E> list) {
		return list.remove(seed.nextInt(list.size()));
	}
	
	public static <E> E extraire2 (List<E> list) {
		int index = seed.nextInt(list.size());
		ListIterator<E> iterator = list.listIterator();
		E element = null;
		
		for (; iterator.hasNext() && index >= 0; index--) {
			element = iterator.next();
		}
		
		iterator.remove();
		return element;
	}
	
	public static <E> List<E> melanger (List<E> listNonMelange) {
		int longueur = listNonMelange.size();
		List<E> listMelange = new ArrayList<>(longueur);
		
		for (;longueur >0;longueur--) {
			listMelange.add(extraire2(listNonMelange));
			
		}
		return listMelange;
	}
	
	public static <E> boolean verifierMelange(List<E> list1, List<E> list2) {
	    if (list1.size() != list2.size()) {
	        return false;
	    }

	    for (E e : list1) {
	        if (Collections.frequency(list1, e) != Collections.frequency(list2, e)) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public static <E> List<E> rassembler(List<E> list) {
	    List<E> remaining = new ArrayList<>(list);
	    List<E> result = new ArrayList<>(list.size());

	    while (!remaining.isEmpty()) {
	        E current = remaining.get(0);
	        ListIterator<E> it = remaining.listIterator();
	        while (it.hasNext()) {
	            if (it.next().equals(current)) {
	                result.add(current);
	                it.remove();
	            }
	        }
	    }
	    return result;
	}
	
	public static <E> boolean verifierRassemblement(List<E> list) {
	    if (list.isEmpty()) return true;

	    ListIterator<E> it = list.listIterator();
	    E previous = it.next();

	    while (it.hasNext()) {
	        E current = it.next();

	        if (!current.equals(previous)) {
	            ListIterator<E> it2 = list.listIterator(it.nextIndex());
	            while (it2.hasNext()) {
	                if (previous.equals(it2.next())) {
	                    return false;
	                }
	            }
	        }
	        previous = current;
	    }
	    return true;
	}

}
