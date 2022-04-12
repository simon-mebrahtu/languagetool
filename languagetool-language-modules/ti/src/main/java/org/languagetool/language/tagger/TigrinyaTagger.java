package org.languagetool.language.tagger;
import org.languagetool.tagging.BaseTagger;

import java.util.Locale;

/**
 * Tigrinya Tagger
 *
 * @author Biniam Gebremichael
 */
public class TigrinyaTagger extends BaseTagger {

    public TigrinyaTagger() {
        super("/ti/ti_ER.dict", new Locale("ti"));
    }

}