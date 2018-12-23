#version 330

in vec2 fragmentUV;
in vec4 fragmentColor;
out vec4 outColor;

uniform sampler2D diffuseTexture;
uniform sampler2D normalTexture;

void main() {
    vec4 color = fragmentColor;//texture(diffuseTexture, fragmentUV).rgba;
    vec4 ambient = 0.1 * color;
    vec4 normal = texture(normalTexture, fragmentUV).rgba;
    normal = normalize(normal * 2.0 - 1.0);
    vec4 lightDir = normalize(vec4(0.0, 0.0, -1.0, 0.0) - gl_FragCoord);
    float diff = max(dot(lightDir, normal), 0.0);
    vec4 diffuse = diff * color;
    outColor = ambient + diffuse;

}
