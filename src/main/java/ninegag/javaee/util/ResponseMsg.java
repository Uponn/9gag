package ninegag.javaee.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ResponseMsg {

    private String msg;
    private int status;
    private LocalDateTime time;

}
