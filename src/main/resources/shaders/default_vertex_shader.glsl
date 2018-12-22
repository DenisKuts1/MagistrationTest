#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 vertexUV;
layout (location = 0) in vec3 normal;

out vec2 fragmentUV;

uniform mat4 transform;

void main()
{
    gl_Position = transform * vec4(position, 1);
    fragmentUV = vertexUV;
}