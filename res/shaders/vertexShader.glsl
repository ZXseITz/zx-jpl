#version 400 core

in vec3 pos;
in vec4 color; //rgba

uniform mat4 P; //projection
uniform mat4 T; //transformation

out vec4 f_color;

void main()
{
  f_color = color;
  gl_Position = P * T * vec4(pos, 1);;
}