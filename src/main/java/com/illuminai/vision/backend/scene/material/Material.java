package com.illuminai.vision.backend.scene.material;

import com.illuminai.vision.backend.render.Color;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.scene.light.Light;

public abstract class Material {

    public abstract Color shade(Intersection intersection, Light light);
}
