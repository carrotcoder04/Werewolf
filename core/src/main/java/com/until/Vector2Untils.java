package com.until;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Vector2Untils {
    public static Vector2 lerp(Vector2 start, Vector2 end, float t) {
        t = MathUtils.clamp(t, 0, 1);
        float x = start.x + (end.x - start.x) * t;
        float y = start.y + (end.y - start.y) * t;
        return new Vector2(x, y);
    }
}
