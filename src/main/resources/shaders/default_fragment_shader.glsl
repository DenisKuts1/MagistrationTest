#version 330

in vec2 fragmentUV;

out vec4 outputColor;

uniform sampler2D diffuseTexture;
uniform sampler2D normalTexture;

void main() {
    outputColor = texture(diffuseTexture, fragmentUV).rgba;
}
