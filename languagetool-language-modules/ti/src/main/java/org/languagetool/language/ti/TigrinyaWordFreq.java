package org.languagetool.language.ti;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * A convenience class to sort Tigrinya dictionary words
 *
 * @author Biniam Gebremichael
 */

public class TigrinyaWordFreq implements Comparable<TigrinyaWordFreq> {
  final String word;
  final Integer freq;

  public TigrinyaWordFreq(String word, Integer freq) {
    this.word = word;
    this.freq = freq;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TigrinyaWordFreq wordFreq = (TigrinyaWordFreq) o;
    return Objects.equals(word, wordFreq.word);
  }

  @Override
  public int hashCode() {
    return Objects.hash(word);
  }

  public String getWord() {
    return word;
  }


  @Override
  public int compareTo(@NotNull TigrinyaWordFreq o) {
    return equals(o) ? 0 : (Objects.equals(o.freq, freq) ? 1 : o.freq - freq);
  }

  @Override
  public String toString() {
    return "<w f=\"" + freq + "\" flags=\"\">" + word + "</w>";
  }


  public static void main(String[] args) {
    TigrinyaWordFreq t1 = new TigrinyaWordFreq("ሃም", 5);
    TigrinyaWordFreq t2 = new TigrinyaWordFreq("ሃምም", 5);
    TigrinyaWordFreq t3 = new TigrinyaWordFreq("ሃም", 7);
    SortedSet<TigrinyaWordFreq> s = new TreeSet<>();
    s.add(t1);
    s.add(t2);
    s.add(t3);
    for (TigrinyaWordFreq tigrinyaWordFreq : s) {
      System.out.println(tigrinyaWordFreq);
    }
  }

}



