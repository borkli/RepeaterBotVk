package model;

public class VkRequest {

    private String type;
    private VkObject object;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VkObject getObject() {
        return object;
    }

    public void setObject(VkObject object) {
        this.object = object;
    }

    public class VkObject {
        private VkMessage message;

        public VkMessage getMessage() {
            return message;
        }

        public void setMessage(VkMessage message) {
            this.message = message;
        }
    }
}
