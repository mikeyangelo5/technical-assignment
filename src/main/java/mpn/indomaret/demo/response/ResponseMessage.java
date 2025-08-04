package mpn.indomaret.demo.response;

import lombok.Data;

@Data
public class ResponseMessage {

    private int status;
    private String message;
    private Object data;

    public ResponseMessage(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
