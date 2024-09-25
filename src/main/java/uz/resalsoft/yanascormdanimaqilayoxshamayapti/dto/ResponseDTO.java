package uz.resalsoft.yanascormdanimaqilayoxshamayapti.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.resalsoft.yanascormdanimaqilayoxshamayapti.enums.ResponseStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    @JsonProperty("success")
    private boolean success;

    public ResponseDTO(String message, T data, boolean success) {
        this.message = message;
        this.data = data;
        this.success = success;
    }

    // Getters va setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", success=" + success +
                '}';
    }


}
