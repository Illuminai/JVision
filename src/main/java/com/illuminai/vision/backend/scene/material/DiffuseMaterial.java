package com.illuminai.vision.backend.scene.material;

import com.illuminai.vision.backend.math.Vector3d;
import com.illuminai.vision.backend.render.Color;
import com.illuminai.vision.backend.render.Intersection;
import com.illuminai.vision.backend.scene.brdf.LambertianBRDF;
import com.illuminai.vision.backend.scene.light.Light;

public class DiffuseMaterial extends Material {

    private LambertianBRDF lambertian = new LambertianBRDF();

    @Override
    public Color shade(Intersection intersection, Light light) {
        Color radiosity = new Color(0, 0, 0);
        Vector3d wo = intersection.getRay().getDirection();
        Vector3d wi = light.getDirection(intersection.getPoint()).scale(-1.0);
        double ndotwi = intersection.getNormal().dot(wi);
        if (ndotwi > 0.0) {
            radiosity = radiosity.add(lambertian.f(wi, wo)
                    .multiply(light.getLightIntensity(intersection.getPoint()))
                    .multiply(ndotwi));
        }

        return radiosity;
    }
}
