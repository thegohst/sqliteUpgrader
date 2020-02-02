package com.mlika.sqlupgrader;

/**
 * Created by mohamed mlika on 28/06/2018.
 * mohamedmlikaa@gmail.com
 */
public class ItemEntity  {
    private int id;
    private String name;
    private float cleanPrice;
    private String dirtyPrice;
    private String url;
    private String pictureUrl;
    private long timeStamp;
    private String avaibility;
    private String category;
    private String companyName;
    private String asinUrl;
    private boolean isPreviousRequestFailed;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCleanPrice() {
        return cleanPrice;
    }

    public void setCleanPrice(float cleanPrice) {
        this.cleanPrice = cleanPrice;
    }

    public String getDirtyPrice() {
        return dirtyPrice;
    }

    public void setDirtyPrice(String dirtyPrice) {
        this.dirtyPrice = dirtyPrice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }


    public String getAvaibility() {
        return avaibility;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAvaibility(String avaibility) {
        this.avaibility = avaibility;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAsinUrl() {
        return asinUrl;
    }

    public void setAsinUrl(String asinUrl) {
        this.asinUrl = asinUrl;
    }


    public boolean isPreviousRequestFailed() {
        return isPreviousRequestFailed;
    }

    public void setPreviousRequestFailed(boolean previousRequestFailed) {
        isPreviousRequestFailed = previousRequestFailed;
    }

    public ItemEntity() {
    }



}
