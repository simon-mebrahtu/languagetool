package org.languagetool;

import org.junit.Test;
import org.languagetool.language.ti.TigrinyaWordTokenizer;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.*;
public class TigrinyaWordTokenizerTest {

  @Test
  public void punctuationTest(){
    List<String> punc = Arrays.asList("፡","።","፣","፤","፥","፦","፧","፨");
    TigrinyaWordTokenizer tokenizer = new TigrinyaWordTokenizer();
    for (String s : punc) {
      String word = "ዘይተረ"+s;
      assertEquals("ዘይተረ",tokenizer.tokenize(word).get(0));
    }
  }
}
