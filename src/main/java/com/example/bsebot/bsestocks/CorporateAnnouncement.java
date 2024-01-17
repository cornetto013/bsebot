package com.example.bsebot.bsestocks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorporateAnnouncement {


    public String  displayName;
    public String  categoryUrl;
    public String  subCategoryUrl;
    public Integer count;

    public CorporateAnnouncement(String displayName, String categoryUrl, String subCategoryUrl, Integer count) {
        this.displayName = displayName;
        this.categoryUrl = categoryUrl;
        this.subCategoryUrl = subCategoryUrl;
        this.count = count;
    }

    
}
