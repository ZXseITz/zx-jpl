#version 400 core

in vec3 pos;
in vec4 color; //rgba
in vec2 uv;

uniform mat4 P; //projection
uniform mat4 T; //transformation

out vec4 f_color;
out vec2 f_uv;

void main()
{
  f_color = color;
  f_uv = uv;
  gl_Position = P * T * vec4(pos, 1);
}