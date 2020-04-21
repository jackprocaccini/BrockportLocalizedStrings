package edu.brockport.localization.utilities.database;

import edu.brockport.localization.interfaces.IQueryBuilder;

public abstract class AbstractQueryBuilder implements IQueryBuilder {

    public String selectJoinQuery(){
        String query = "SELECT translationkeys.TransKey, translations.Locale, translations.Translation, translations.Status, sourceresource.ResourceName " +
                "FROM translationkeys, translations, sourceresource " +
                "WHERE (translationkeys.ID = translations.TransKeyFK AND sourceresource.ID = translationkeys.SourceResourceKeyFK) " +
                "ORDER BY translationkeys.TransKey;";
        return query;
    }

}
