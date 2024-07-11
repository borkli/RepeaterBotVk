package model;

import com.google.gson.annotations.SerializedName;

public class VkMessage {
    private Integer id;
    @SerializedName("peer_id")
    private Integer peerId;
    @SerializedName("from_id")
    private Integer fromId;
    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPeerId() {
        return peerId;
    }

    public void setPeerId(Integer peerId) {
        this.peerId = peerId;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
