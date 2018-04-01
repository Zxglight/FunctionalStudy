package com.xg.util;

import java.util.Objects;
import lombok.Data;

/**
 * @author xg.zhao   2018 03 31 22:41
 */
@Data
public class Tuple<V1, V2> {

    private V1 v1;
    private V2 v2;

    public Tuple(V1 v1, V2 v2) {
        Objects.requireNonNull(v1);
        Objects.requireNonNull(v2);
        this.v1 = v1;
        this.v2 = v2;
    }
}
