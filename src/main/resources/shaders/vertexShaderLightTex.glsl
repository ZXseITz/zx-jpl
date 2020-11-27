#version 400 core

in vec3 pos;
in vec3 normal;
in vec4 color; //rgba
in vec2 uv;

uniform mat4 P; //projection
uniform mat4 T; //transformation

out vec3 f_pos;
out vec3 f_normal;
out vec4 f_color;
out vec2 f_uv;

void main()
{
  vec4 p4 =  T * vec4(pos, 1);
  f_pos = p4.xyz;
  f_normal = (T * vec4(normal, 0)).xyz;
  f_color = color;
  f_uv = uv;

  gl_Position = P * p4;
}