#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 vertexUV;
layout (location = 0) in vec3 normal;

out vec2 fragmentUV;

void main()
{
    gl_Position = vec4(position, 0);
    fragmentUV = vertexUV;
}