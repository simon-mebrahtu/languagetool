package org.languagetool;

import morfologik.speller.Speller;
import morfologik.stemming.Dictionary;
import org.junit.Test;
import org.languagetool.rules.spelling.morfologik.MorfologikMultiSpeller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

import static junit.framework.TestCase.*;

//https://www.programcreek.com/java-api-examples/?api=morfologik.stemming.Dictionary

public class MorfologikTigrinyaTest {

  static final URL TI_DICT = JLanguageTool.getDataBroker().getFromResourceDirAsUrl("ti/ti_ER.dict");

  @Test
  public void testTiHunspellDictionary() throws IOException {
    final Speller spell1 = new Speller(Dictionary.read(TI_DICT),1);
    assertTrue(spell1.isInDictionary("ዘይተረፈ"));
    assertFalse(spell1.isInDictionary("ዘይተረፈ፡"));
    assertEquals(0,spell1.replaceRunOnWords("ዘይተረፈ").size() );
    assertEquals(0,spell1.replaceRunOnWords("ዘይተረፈ፡").size() );
    assertEquals(1,  spell1.replaceRunOnWords("ዘይተረ").size() );
    assertEquals(spell1.findReplacements("ዘይተረ").size(),  spell1.findReplacements("ዘይተረ").size() );

  }

  @Test
  public void testTiPuctuation() throws IOException, URISyntaxException {
    final String file = Paths.get(TI_DICT.toURI()).toFile().getAbsolutePath();
    MorfologikMultiSpeller speller = new MorfologikMultiSpeller(file,new ArrayList<String>(),null,1 );
    assertTrue(speller.getSuggestionsFromDefaultDicts("ዘይተረ").size()>0);
    assertTrue(speller.isMisspelled("ዘይተረ"));
    assertFalse( speller.isMisspelled("ዝረኸብና"));
    assertTrue(speller.isMisspelled("ዝረኸብና፡"));
    assertTrue(speller.getSuggestionsFromDefaultDicts("ዝረኸብና፡").size()>0);
  }
}
