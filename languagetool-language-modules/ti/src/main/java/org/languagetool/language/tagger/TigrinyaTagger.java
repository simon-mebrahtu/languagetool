package org.languagetool.language.tagger;
import org.languagetool.tagging.BaseTagger;

import java.util.Locale;

/**
 * Created by biniamg on 3/6/2022.
 */
public class TigrinyaTagger extends BaseTagger {

    public TigrinyaTagger() {
        super("/ti/ti_ER.dict", new Locale("ti"));
    }

}