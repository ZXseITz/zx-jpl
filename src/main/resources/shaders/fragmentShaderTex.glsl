#version 400 core

in vec4 f_color;
in vec2 f_uv;

uniform sampler2D tex;

out vec4 color;

void main()
{
  vec4 textureColor = texture(tex, f_uv);
  color = f_color * textureColor;
}