package edu.brockport.localization.utilities.database;

import edu.brockport.localization.interfaces.IQueryBuilder;

public abstract class AbstractQueryBuilder implements IQueryBuilder {

    /**
     * Generates a query which selects all information relevant for the "translations.jsp" page
     * @return A formatted sql which selects A translation's key, locale, translation, status and resource name
     */
    public String selectJoinQueryMain(){
        String query = "SELECT translationkeys.TransKey, translations.Locale, translations.Translation, translations.Status, sourceresource.ResourceName " +
                "FROM translationkeys, translations, sourceresource " +
                "WHERE (translationkeys.ID = translations.TransKeyFK AND sourceresource.ID = translationkeys.SourceResourceKeyFK) " +
                "ORDER BY translationkeys.TransKey;";
        return query;
    }

    public String selectJoinQueryFlagged(){
        String query = "SELECT translationkeys.TransKey, translations.Locale, translations.Translation, translations.Status, sourceresource.ResourceName, " +
                "translationtracking.DateFlagged, translationtracking.DateResolved, translationtracking.Notes " +
                "FROM translationkeys, translations, sourceresource, translationtracking " +
                "WHERE (translationkeys.ID = translations.TransKeyFK AND sourceresource.ID = translationkeys.SourceResourceKeyFK AND " +
                "translationtracking.TranslationKeyFK = translations.ID) " +
                "ORDER BY translationtracking.DateResolved;";

        return query;
    }

}
