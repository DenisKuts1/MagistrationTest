#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 vertexUV;
layout (location = 2) in vec3 normal;

out vec2 fragmentUV;
out vec4 fragmentColor;
uniform mat4 mvp;
uniform vec4 color;

void main()
{
    vec4 pos = mvp * vec4(position, 1);
    //pos.w = 1;
    gl_Position = pos;
    fragmentUV = vertexUV;
    fragmentColor = color;
}