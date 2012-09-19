package com.befasoft.common.tools;

public class MailMessageImages {
    String imageId;
    String imageFile;

    public MailMessageImages(String imageId, String imageFile) {
        this.imageId = imageId;
        this.imageFile = imageFile;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }
}
