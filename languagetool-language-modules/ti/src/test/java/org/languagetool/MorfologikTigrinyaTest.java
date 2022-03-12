package org.languagetool;

import morfologik.speller.Speller;
import morfologik.stemming.Dictionary;
import org.junit.Ignore;
import org.junit.Test;
import org.languagetool.rules.spelling.morfologik.MorfologikMultiSpeller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static junit.framework.TestCase.*;

//https://www.programcreek.com/java-api-examples/?api=morfologik.stemming.Dictionary

public class MorfologikTigrinyaTest {

  @Test
  public void testDictionaryBuilder() throws IOException {
//    SpellDictionaryBuilder builder = new SpellDictionaryBuilder(new File("."));
    final URL url1 = new File ("D:\\projects\\languagetool\\languagetool-language-modules\\ti\\src\\main\\resources\\org\\languagetool\\resource\\ti",
      "ti_ER.dict").toURI().toURL();
    final Speller spell1 = new Speller(Dictionary.read(url1),1);
    assertTrue(spell1.isInDictionary("ዘይተረፈ"));
    assertEquals(0,spell1.replaceRunOnWords("ዘይተረፈ").size() );
    assertEquals(1,  spell1.replaceRunOnWords("ዘይተረ").size() );
    assertEquals(18,  spell1.findReplacements("ዘይተረ").size() );

  }

  @Test
  public void morfologikMultiSpellerTest() throws IOException {
    final String url1 = new File ("D:\\projects\\languagetool\\languagetool-language-modules\\ti\\src\\main\\resources\\org\\languagetool\\resource\\ti",
      "ti_ER.dict").getAbsolutePath();
    MorfologikMultiSpeller speller = new MorfologikMultiSpeller(url1,new ArrayList<String>(),null,1 );
    assertEquals(19,  speller.getSuggestionsFromDefaultDicts("ዘይተረ").size());
    assertTrue(speller.isMisspelled("ዘይተረ"));
    assertFalse( speller.isMisspelled("ዝረኸብና"));
    assertTrue(speller.isMisspelled("ዝረኸብና፡"));
    assertEquals(8, speller.getSuggestionsFromDefaultDicts("ዝረኸብና፡").size());
  }
}
